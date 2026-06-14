# 5-Step Project Plan — Handoff Summary

Use this document in a **new context window** to execute **Step 5** (repolishing the codebase). Steps 1–4 are complete.

For detailed cleanup and test instructions, also see `docs/STUDENT_STAFF_DB_INTEGRATION.md` (Sections 8 and 9).

---

## Architecture (all roles)

```
GUI Frame → StudentService / StaffService / ManagerService → DatabaseHandler → MySQL
                              ↑ SessionManager
```

**Rules:**
- GUI must **not** call JDBC directly.
- Load on open, save via service, reload on success.
- Use `UIHelper.showDatabaseError(...)` on failure.
- Use `UIHelper.navigateTo(currentFrame, nextFrame)` for navigation.

---

## Step 1 — Understand integration doc ✅ Done

Read and align with `docs/STUDENT_STAFF_DB_INTEGRATION.md`.

Manager screens were the reference implementation. Student and staff wiring followed the same service-layer pattern.

---

## Step 2 — Merge teammate GUIs ✅ Done

Teammate `.java` / `.form` files were merged and moved into proper packages. Old root-package copies were removed.

| Role | Package | Frames |
|------|---------|--------|
| **Student** | `gui/student/` | `stdDashboard`, `SubmitMRequest`, `studRHistory` |
| **Staff** | `gui/staff/` | `staffDashboard`, `ManageRequestPage`, `ViewRoomDetails`, `viewRequestHistoryPage` |
| **Manager** | `gui/manager/` | Dashboard, Manage Active Requests, Assign Staff, View Room Details, View Request History |

---

## Step 3 — GUI optimization (hi-fi consistency) ✅ Done

All three roles share the same shell and styling via `UIHelper.java`.

| Pattern | Details |
|---------|---------|
| **Shell** | Header (logo + title + user menu), sidebar (`ManagerNavButton`), white page header, cream `pnlMain` |
| **Session** | `installStudentSession`, `installStaffSession`, `installManagerSession` + logout |
| **Tables** | Badge renderers, pagination, dynamic height, shared filter dialog (`showRequestFilterDialog`) |
| **Shared components** | `AppColors`, `StatusBadgeLabel`, `PriorityBadgeLabel`, `NotAssignedBadgeLabel`, etc. |

**Recent sync tweaks (also done):**
- Block display in View Room Details: `"Block A, Dhuam"` from room number + place name
- Priority / Date Raised columns on manager and staff tables
- Filter dialogs per role (see filter matrix below)
- `NOT ASSIGNED` badge for unassigned `SUBMITTED` requests in manager tables

### Filter matrix

| Screen | Filters |
|--------|---------|
| Student — View Request History | Status, Request Type |
| Staff — Manage Request | Priority |
| Staff — View Request History | Priority |
| Manager — Manage Active Requests | Request Type, Assigned Staff (from DB), Priority |
| Manager — Assign Staff | Request Type, Priority |
| Manager — View Request History | Status, Priority, Request Type |

---

## Step 4 — DB integration + business logic ✅ Done

All roles are wired to real MySQL (XAMPP). Full lifecycle works across student ↔ manager ↔ staff.

### Status lifecycle

```
Student submit → SUBMITTED
Manager assign staff → IN_PROGRESS
Staff / Manager update → COMPLETED or CANCELLED
```

### Business rules enforced

| Role | Screen | Data rule |
|------|--------|-----------|
| **Student** | Dashboard | Own requests: `SUBMITTED` + `IN_PROGRESS` only; sorted by recent activity |
| **Student** | Submit | Uses registered room + form place name; generates `REQ00X` |
| **Student** | History | All own requests, all statuses |
| **Staff** | Dashboard | Assigned `IN_PROGRESS` only |
| **Staff** | Manage Request | Assigned `IN_PROGRESS` → update to `COMPLETED` / `CANCELLED` |
| **Staff** | Room Details / History | Assigned requests only |
| **Manager** | Dashboard | `SUBMITTED` + `IN_PROGRESS` recent requests |
| **Manager** | Assign Staff | Unassigned `SUBMITTED` only |
| **Manager** | Manage Active | `IN_PROGRESS` only |
| **Manager** | View History | All requests |

All list queries order by latest `request_status_history.changed_at` (fallback: `date_raised`).

### Key service / backend files

- `StudentService.java`
- `StaffService.java`
- `ManagerService.java`
- `DatabaseHandler.java`
- `AuthService.java`
- `SessionManager.java`

### Test credentials (seed data)

| Role | Email | Password |
|------|-------|----------|
| Student | `john123@gmail.com` | `student123` |
| Manager | `shmrts-admin@admin.com.my` / `umpsa-admin@admin.com.my` / `hostelcare-admin@admin.com.my` / `youradminusername@admin.com.my` | `umpsaADMINs3cr37` |
| Staff | `farouq001@gmail.com` / `faiz002@gmail.com` | `staff123` |

---

## Step 5 — Repolish codebase + full real-system test ⏳ Next

**Goal:** Remove leftover mock/dead code, tighten structure, then run end-to-end tests with **no hardcoded table data**.

### A. Cleanup targets (`UIHelper.java` + orphans)

Search and remove if unused:

- `buildManageRequestSampleData()`
- `buildAssignStaffSampleData()`
- `buildViewHistorySampleData()`
- `lookupManagerRoomDetails()` + `MANAGER_ROOM_DETAILS` map
- Staff mock builders: `buildStaffProfileMockRows`, `buildStaffDashboardActiveMockRows`, `buildStaffManageRequestMockRows`, `buildStaffHistoryMockRows`
- Inner class `UIHelper.ManagerRoomDetails` if superseded by root-package `ManagerRoomDetails.java`
- `RequestHistoryFrame.java` in `gui/manager/` if still present (replaced by `ManagerViewHistoryFrame`)

Verify **every frame** loads via service methods in `customizeForm()` — no `new Object[][] { ... }` sample rows.

Optional: trim hardcoded defaults in `.form` files (manager dashboard stat cards, etc.).

**Keep:** `Main.java` (Phase II console demo), schema SQL, domain classes, `MaintenanceFactory`.

### B. Code quality pass

- Remove unused imports/methods after mock deletion
- Review `UIHelper.java` size — split only if clearly warranted (e.g. table models vs shell helpers)
- Consistent naming applied (`stdDashboard`, `SetNewPasswordFrame`)
- Align student history filter with shared `showRequestFilterDialog` (staff/manager already use it)
- **Clean and Build** in NetBeans must succeed

### C. Full end-to-end test plan

**Scenario A — Full pipeline**
1. Student submits request → appears on manager Dashboard + Assign Staff as `SUBMITTED`
2. Manager assigns staff → status becomes `IN_PROGRESS`
3. Staff sees it on Dashboard / Manage Request
4. Staff marks `COMPLETED` → appears in manager History; removed from active lists

**Scenario B — Manager cancels** an active request from Manage Active Requests

**Scenario C — Student isolation** — each student sees only their own requests

**Scenario D — Staff isolation** — staff see only assigned work; unassigned `SUBMITTED` hidden from staff

---

## Prompt for new context window

Copy this when starting Step 5:

> **Step 5: Repolish the Student Hostel Maintenance Request & Tracking System codebase.**
>
> Steps 1–4 are complete: manager, student, and staff GUIs are optimized and fully DB-wired. Follow `docs/STUDENT_STAFF_DB_INTEGRATION.md` Section 9 (cleanup) and Section 8 (end-to-end tests). Also read `docs/PROJECT_5_STEP_PLAN.md` for context.
>
> Tasks:
> 1. Remove all unused mock/sample data from `UIHelper.java` and any dead code
> 2. Verify no GUI frame uses hardcoded table rows
> 3. Run Clean and Build; fix compile issues
> 4. Execute full lifecycle tests across student → manager → staff with real MySQL data
>
> Architecture: `GUI → Service → DatabaseHandler → MySQL` with `SessionManager`. Reference manager frames as the pattern.

---

## Key file reference

```
database/
  hostel_maintenance_schema.sql
  sample_requests.sql

docs/
  STUDENT_STAFF_DB_INTEGRATION.md   ← detailed wiring + cleanup guide
  PROJECT_5_STEP_PLAN.md            ← this file

src/.../trackingsystem/
  AuthService.java
  SessionManager.java
  StudentService.java
  StaffService.java
  ManagerService.java
  DatabaseHandler.java

src/.../gui/
  auth/LoginFrame.java
  manager/                          ← reference implementation
  student/
  staff/
  common/UIHelper.java              ← primary cleanup target
```
