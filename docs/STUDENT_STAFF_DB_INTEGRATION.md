# Student & Staff GUI — Database Integration Guide

Use this document in a **new context window** after your teammates finish the **Student** and **Staff** GUI screens (`.java` / `.form` files only, still using mock/hardcoded data).

**Manager/admin work is considered complete.** This guide covers wiring those new screens to the existing database layer, then the final project-wide cleanup and full end-to-end testing.

---

## 1. Current project state

### Already done (do not redo)

| Area | Status |
|------|--------|
| MySQL schema + seed data | `database/hostel_maintenance_schema.sql` |
| JDBC layer | `DatabaseHandler.java`, `DataAccess.java`, `DatabaseConfig.java` |
| Auth (login / sign-up) | `AuthService.java`, `SessionManager.java` |
| Manager GUI ↔ DB | All 5 manager frames via `ManagerService.java` |
| Student backend | `StudentService.java` |
| Staff backend | `StaffService.java` |
| Phase II OOP / console demo | `Main.java`, domain classes, `MaintenanceFactory` |

### Not done yet (this guide)

- Student GUI frames wired to `StudentService` + DB
- Staff GUI frames wired to `StaffService` + DB
- `AuthService` navigation for student/staff after login (currently shows a JOptionPane only)
- Remove leftover mock/sample code from `UIHelper.java`
- Full lifecycle testing with real DB data (no mock rows)

### Expected GUI locations (created by teammates)

Teammates should add frames under something like:

```
src/studenthostelmaintenancerequest/trackingsystem/gui/student/
src/studenthostelmaintenancerequest/trackingsystem/gui/staff/
```

Confirm exact class names and screen list with your team before wiring.

---

## 2. Architecture pattern to follow

Mirror what was done for **manager** screens:

```
GUI Frame  →  StudentService / StaffService  →  DatabaseHandler  →  MySQL
                ↑
         SessionManager (logged-in user)
```

**Rules:**

1. GUI frames should **not** call JDBC directly — use service classes.
2. On screen open, **load from DB** (replace hardcoded table rows / labels).
3. On save/submit, call service method → show `UIHelper.showDatabaseError(...)` on failure → **reload** table/data on success.
4. Use `SessionManager` for username, full name, role, and logout.
5. Use `UIHelper.navigateTo(currentFrame, nextFrame)` for navigation between screens.

**Manager reference files:**

- Service: `ManagerService.java`
- Frame example: `ManagerManageRequestFrame.java` (load on open, save on confirm, reload after save)
- Session header: `UIHelper.installManagerSession(...)` in manager frames

You will likely add `UIHelper.installStudentSession(...)` and `UIHelper.installStaffSession(...)` following the same pattern as `installManagerSession`.

---

## 3. Shared setup (both roles)

### 3.1 Database must be running

1. Start XAMPP MySQL (port `3306`).
2. Run `database/hostel_maintenance_schema.sql` once in phpMyAdmin.
3. JDBC settings: `DatabaseConfig.java` (`hostel_maintenance_db`, user `root`, empty password).

### 3.2 Session after login

Login already works and sets the session:

```java
// AuthService.login(...) already does:
SessionManager.setCurrentUser(user);
```

From any student/staff frame:

```java
User user = SessionManager.getCurrentUser();
Student student = (Student) user;   // after STUDENT login
Staff staff = (Staff) user;        // after STAFF login

String userId = user.getUserId();
String displayName = SessionManager.getFullName();
String username = SessionManager.getDisplayUsername();
```

**Student room (for submit request):**

```java
Student student = (Student) SessionManager.getCurrentUser();
Room room = student.getRoom();           // loaded at login from DB
room.getRoomNumber();                    // e.g. A-10-05
room.getPlaceName();                     // e.g. DHUAM
room.getRoomId();                        // must be > 0 for DB insert
```

Students **must not** pick a different room on submit — always use `student.getRoom()` from registration.

### 3.3 Logout

On logout (user menu or button):

```java
SessionManager.clear();
UIHelper.navigateTo(this, new LoginFrame());
```

### 3.4 Error handling

```java
try {
    // service call
} catch (DatabaseException ex) {
    UIHelper.showDatabaseError(this, ex);
}
```

---

## 4. AuthService — wire login navigation

**File:** `AuthService.java` → method `navigateAfterLogin(...)`

**Current behaviour:** Student/staff login shows an info popup and stays on the login screen.

**Change to:** Navigate to the teammate’s dashboard frame, same as manager:

```java
case STUDENT:
    UIHelper.navigateTo(currentFrame, new StudentDashboardFrame()); // use real class name
    break;
case STAFF:
    UIHelper.navigateTo(currentFrame, new StaffDashboardFrame());   // use real class name
    break;
```

Remove or replace the temporary `JOptionPane` messages once real frames exist.

---

## 5. Student screens — DB integration

### 5.1 Backend already available

**File:** `StudentService.java`

| Method | Purpose |
|--------|---------|
| `refreshStudentRequests(Student student)` | Loads all requests for this student from DB into the `Student` object |
| `submitRequest(Student student, String requestType, String description, String priority)` | Creates request in DB (`REQ001`, `REQ002`, …), inserts history, returns `MaintenanceRequest` |

**Underlying DB calls:**

- `DatabaseHandler.fetchRequestsByStudent(studentId)`
- `DatabaseHandler.generateNextRequestId()`
- `DatabaseHandler.insertRequest(...)`
- `MaintenanceFactory.createRequest(type, ...)` — types: `electrical`, `plumbing`, `furniture` (case-insensitive)

### 5.2 Typical student screens to wire

Adjust to match your teammates’ actual screens:

| Screen | Suggested wiring |
|--------|------------------|
| **Dashboard / My Requests** | On open: `StudentService.refreshStudentRequests(student)` then populate table from `student.getRequestAt(i)` or add a `StudentService.getRequestRows()` helper |
| **Submit Request** | On submit: validate fields → `StudentService.submitRequest(student, type, description, priority)` → show success → navigate back or reload list |
| **View request detail / history** | Load single request via `DatabaseHandler.findRequestById(id)` or filter from refreshed student list |

### 5.3 Submit request — important business rules

- **Request type:** `electrical`, `plumbing`, or `furniture` (factory validates).
- **Priority:** store as `High`, `Medium`, or `Low` (match DB seed format).
- **Room:** always from logged-in student — **no room override** on the form.
- **Initial status:** `SUBMITTED` (set automatically in domain + DB).
- **Request ID:** generated by DB layer (`REQ001`, `REQ002`, …).

**Example submit handler:**

```java
Student student = (Student) SessionManager.getCurrentUser();
try {
    StudentService.submitRequest(
            student,
            cmbRequestType.getSelectedItem().toString(),
            txtDescription.getText().trim(),
            cmbPriority.getSelectedItem().toString());
    // reload list or navigate
} catch (DatabaseException ex) {
    UIHelper.showDatabaseError(this, ex);
}
```

### 5.4 Displaying requests in a JTable

`MaintenanceRequest` fields useful for tables:

- `getRequestId()`
- `getDescription()`
- `getPriority()`
- `getStatus()` → display with `StatusBadgeLabel.formatStatus(status.name())`
- Room via `request.getRoom()` if exposed on concrete classes / base class

You may add **`StudentService` helper methods** (recommended, same style as `ManagerService`):

```java
// Example additions you might implement:
public static Object[][] getStudentRequestRows(String studentId) throws DatabaseException
public static MaintenanceRequest findRequest(String requestId) throws DatabaseException
```

Convert `MaintenanceRequest[]` → `Object[][]` for existing table models teammates built.

### 5.5 Optional: reactive table height

Manager screens use dynamic table height based on row count (not fixed 7 rows). Reuse:

```java
UIHelper.sizeManagerPaginatedTable(table, scrollPane);
// or sizeManagerOverviewTable for non-paginated tables
```

Apply the same pattern on student tables if they look too tall with few rows.

---

## 6. Staff screens — DB integration

### 6.1 Backend already available

**File:** `StaffService.java`

| Method | Purpose |
|--------|---------|
| `refreshAssignedRequests(Staff staff)` | Loads requests where `assigned_staff_id = staff.userId` |
| `updateAssignedRequestStatus(Staff staff, String requestId, Status status)` | Updates DB status + history + in-memory staff list |

**Underlying DB calls:**

- `DatabaseHandler.fetchRequestsByStaff(staffId)`
- `DatabaseHandler.updateRequestStatus(requestId, status, changedBy)`

### 6.2 Typical staff screens to wire

| Screen | Suggested wiring |
|--------|------------------|
| **Dashboard / Assigned Requests** | On open: `StaffService.refreshAssignedRequests(staff)` → fill table from `staff.getAssignedRequestAt(i)` |
| **Update status** | On confirm: map UI status string → `Status` enum → `StaffService.updateAssignedRequestStatus(staff, requestId, status)` → reload table |

### 6.3 Staff status values

Map display text to enum (same as manager):

```java
// Display: "IN PROGRESS" → Status.IN_PROGRESS
String normalized = StatusBadgeLabel.formatStatus(displayStatus).replace(' ', '_');
Status status = Status.valueOf(normalized);
```

Or reuse:

```java
ManagerService.parseDisplayStatus(displayStatus); // already handles this mapping
```

**Typical staff transitions:** `IN_PROGRESS` → `COMPLETED` (and possibly `CANCELLED` if your GUI allows it).

### 6.4 Staff only sees assigned requests

Staff do **not** see unassigned `SUBMITTED` requests. Manager assigns staff first (`ManagerAssignStaffFrame` → status becomes `IN_PROGRESS`). Staff login should then show those requests.

**Example load on screen open:**

```java
Staff staff = (Staff) SessionManager.getCurrentUser();
try {
    StaffService.refreshAssignedRequests(staff);
    // populate table from staff.getCount() / getAssignedRequestAt(i)
} catch (DatabaseException ex) {
    UIHelper.showDatabaseError(this, ex);
}
```

**Example status update:**

```java
try {
    StaffService.updateAssignedRequestStatus(staff, requestId, Status.COMPLETED);
    StaffService.refreshAssignedRequests(staff);
    // refresh table
} catch (DatabaseException ex) {
    UIHelper.showDatabaseError(this, ex);
}
```

### 6.5 Optional StaffService helpers

Same as student — add row-formatting helpers if tables use `Object[][]`:

```java
public static Object[][] getAssignedRequestRows(String staffId) throws DatabaseException
```

---

## 7. Status lifecycle (full system)

Understand this flow for testing:

```
Student submits     → SUBMITTED
Manager assigns     → IN_PROGRESS  (also sets assigned_staff_id)
Staff completes     → COMPLETED    (or manager sets COMPLETED / CANCELLED)
Manager cancels     → CANCELLED
```

| Status | Visible on manager screen |
|--------|---------------------------|
| `SUBMITTED` (unassigned) | Assign Staff |
| `IN_PROGRESS` | Manage Active Requests |
| `COMPLETED` / `CANCELLED` | View Request History only |

---

## 8. End-to-end test plan (after all wiring)

Run **without mock data** (see Section 9). Use fresh sign-ups or seed accounts.

### Test credentials (from schema seed)

| Role | Email | Password |
|------|-------|----------|
| Manager | `youradminusername@admin.com.my` | `admin123` |
| Student | `john123@gmail.com` | `student123` |
| Staff (electrician) | `farouq001@gmail.com` | `staff123` |
| Staff (plumber) | `faiz002@gmail.com` | `staff123` |

### Scenario A — New student request (full pipeline)

1. **Sign up** a new student (or use demo student).
2. **Student:** submit a new electrical request.
3. **Manager → Dashboard:** total count increases; new request in recent list.
4. **Manager → Assign Staff:** new request appears as `SUBMITTED` / unassigned.
5. **Manager:** assign staff → request becomes `IN_PROGRESS`.
6. **Staff:** log in → request appears in assigned list.
7. **Staff:** mark `COMPLETED`.
8. **Manager → View Request History:** shows `COMPLETED`.
9. **Manager → Manage Active Requests:** request no longer listed.

### Scenario B — Manager cancels active request

1. Start with an `IN_PROGRESS` request.
2. **Manager → Manage Active Requests:** change status to `CANCELLED` → Confirm Changes.
3. Verify history and dashboard counts update.

### Scenario C — Student views own requests only

1. Log in as student A → see only A’s requests.
2. Log in as another student → must not see A’s requests.

### Scenario D — Staff sees only assigned work

1. Staff A should not see requests assigned to Staff B.
2. Unassigned `SUBMITTED` requests must not appear on staff screens.

---

## 9. Final cleanup — remove mock data

Do this **after** student and staff DB wiring works. Goal: no hardcoded sample rows/maps left in GUI code.

### 9.1 Files / code to remove or refactor

| Item | Location | Action |
|------|----------|--------|
| `buildManageRequestSampleData()` | `UIHelper.java` | Delete if unused |
| `buildAssignStaffSampleData()` | `UIHelper.java` | Delete if unused |
| `buildViewHistorySampleData()` | `UIHelper.java` | Delete if unused |
| `lookupManagerRoomDetails()` + `MANAGER_ROOM_DETAILS` map | `UIHelper.java` | Delete — replaced by `ManagerService.lookupRoomDetails()` |
| Inner class `UIHelper.ManagerRoomDetails` | `UIHelper.java` | Delete if unused — use `ManagerRoomDetails.java` in root package |
| `RequestHistoryFrame.java` | `gui/manager/` | Delete — placeholder replaced by `ManagerViewHistoryFrame` |
| Hardcoded stat defaults in `.form` files | Manager dashboard `.form` | Optional — values overwritten on load anyway |
| Demo SQL seed rows | `hostel_maintenance_schema.sql` | Optional — keep for demo or remove for “production” test |

Search before deleting:

```
buildManageRequestSampleData
buildAssignStaffSampleData
buildViewHistorySampleData
lookupManagerRoomDetails
MANAGER_ROOM_DETAILS
RequestHistoryFrame
```

### 9.2 Verify no GUI frame uses hardcoded tables

Each frame’s `customizeForm()` should call a **service load method**, not `new Object[][] { ... }` sample arrays.

### 9.3 Keep these (do not delete)

- `Main.java` — Phase II console evidence for rubric
- `database/hostel_maintenance_schema.sql` — schema definition
- `database/sample_requests.sql` — optional re-seed script for demos
- Domain classes, factory, interfaces

---

## 10. Optional / out of scope (unless rubric requires)

| Feature | Current state |
|---------|---------------|
| Forgot password | GUI only — `setNewPasswordFrame` has no DB logic |
| Password hashing | Plain text in DB (academic project default) |
| Delete request | `DatabaseHandler.deleteRequest()` exists but no GUI wired |

---

## 11. Checklist for new context window

Copy this checklist when starting integration:

### Student

- [ ] Teammate GUI merged; class names confirmed
- [ ] `AuthService` navigates to student dashboard on login
- [ ] Session header + logout wired (`SessionManager`)
- [ ] My Requests screen loads from `StudentService.refreshStudentRequests(...)`
- [ ] Submit Request calls `StudentService.submitRequest(...)` using registered room only
- [ ] Errors shown via `UIHelper.showDatabaseError(...)`
- [ ] Table refreshes after submit

### Staff

- [ ] Teammate GUI merged; class names confirmed
- [ ] `AuthService` navigates to staff dashboard on login
- [ ] Session header + logout wired
- [ ] Assigned requests load from `StaffService.refreshAssignedRequests(...)`
- [ ] Status update calls `StaffService.updateAssignedRequestStatus(...)`
- [ ] Table reloads after update

### Final

- [ ] Full end-to-end test (Section 8) passes
- [ ] Mock/sample code removed (Section 9)
- [ ] Clean and Build succeeds
- [ ] All three roles demo-ready from `LoginFrame`

---

## 12. Key file reference

```
database/
  hostel_maintenance_schema.sql
  sample_requests.sql
  README.txt

src/.../trackingsystem/
  AuthService.java              ← login routing
  SessionManager.java           ← current user
  StudentService.java           ← student DB operations
  StaffService.java             ← staff DB operations
  ManagerService.java           ← reference pattern
  DatabaseHandler.java          ← JDBC implementation
  DatabaseConfig.java           ← connection settings
  MaintenanceFactory.java       ← request types
  Status.java                   ← SUBMITTED, IN_PROGRESS, COMPLETED, CANCELLED

src/.../gui/
  auth/LoginFrame.java
  auth/SignUpFrame.java         ← already DB-backed
  manager/                      ← complete reference implementation
  student/                      ← teammates add here
  staff/                        ← teammates add here
  common/UIHelper.java          ← shared UI + cleanup targets
```

---

## 13. Prompt snippet for a new AI context window

Paste this when you start the integration session:

> Manager DB integration is complete. Teammates have finished student/staff GUI frames at `[paths]`. Wire them to the existing `StudentService`, `StaffService`, `AuthService`, and `SessionManager` following the same pattern as `ManagerService` and the manager frames. Follow `docs/STUDENT_STAFF_DB_INTEGRATION.md`. After wiring, remove mock data from `UIHelper.java` and run full end-to-end tests.

Replace `[paths]` with the actual package/class names from your teammates.
