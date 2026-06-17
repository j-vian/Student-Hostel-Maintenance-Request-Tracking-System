package studenthostelmaintenancerequest.trackingsystem;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import studenthostelmaintenancerequest.trackingsystem.gui.auth.LoginFrame;
import studenthostelmaintenancerequest.trackingsystem.gui.common.StatusBadgeLabel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 * Backend operations for staff GUI screens.
 */
public final class StaffService {

    // constants used by this class
    private static final DataAccess dataAccess = DatabaseHandler.getInstance();
    private static final MaintenanceManager manager = new MaintenanceManager();

    // construct frame and initialize UI
    private StaffService() {
    }

    public static MaintenanceManager getManager() {
        return manager;
    }

    public static Staff requireStaff(JFrame frame) {
        if (!SessionManager.isLoggedIn()
                || SessionManager.getCurrentUser().getRole() != UserRole.STAFF) {
            JOptionPane.showMessageDialog(frame,
                    "Please log in with a staff account to continue.",
                    "Session Required",
                    JOptionPane.WARNING_MESSAGE);
            UIHelper.navigateTo(frame, new LoginFrame());
            return null;
        }
        return (Staff) SessionManager.getCurrentUser();
    }

    public static void refreshAssignedRequests(Staff staff) throws DatabaseException {
        MaintenanceRequest[] requests = dataAccess.fetchRequestsByStaff(staff.getUserId());
        staff.loadAssignedRequests(requests);
        manager.loadRequests(requests);
    }

    public static Object[][] getProfileRows(Staff staff) {
        return new Object[][]{
            {"Name", staff.getFullName()},
            {"Staff ID", staff.getUserId()}
        };
    }

    /** Assigned requests currently in progress (staff dashboard). */
    public static Object[][] getDashboardActiveRows(Staff staff) throws DatabaseException {
        refreshAssignedRequests(staff);
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < staff.getCount(); i++) {
            MaintenanceRequest request = staff.getAssignedRequestAt(i);
            if (request != null && request.getStatus() == Status.IN_PROGRESS) {
                rows.add(toActiveRow(request));
            }
        }
        ManagerService.sortRowsByRecency(rows, 0, 2);
        return rows.toArray(new Object[0][]);
    }

    /** Assigned in-progress requests the staff member can update. */
    public static Object[][] getManageActiveRows(Staff staff, String requestIdSearch)
            throws DatabaseException {
        refreshAssignedRequests(staff);
        String search = requestIdSearch == null ? "" : requestIdSearch.trim().toUpperCase();
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < staff.getCount(); i++) {
            MaintenanceRequest request = staff.getAssignedRequestAt(i);
            if (request == null || request.getStatus() != Status.IN_PROGRESS) {
                continue;
            }
            if (!search.isEmpty() && !request.getRequestId().toUpperCase().contains(search)) {
                continue;
            }
            rows.add(toManageRow(request));
        }
        ManagerService.sortRowsByRecency(rows, 0, 2);
        return rows.toArray(new Object[0][]);
    }

    /** All requests assigned to this staff member, every status. */
    public static Object[][] getHistoryRequestRows(Staff staff) throws DatabaseException {
        refreshAssignedRequests(staff);
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < staff.getCount(); i++) {
            MaintenanceRequest request = staff.getAssignedRequestAt(i);
            if (request != null) {
                rows.add(toHistoryRow(request));
            }
        }
        ManagerService.sortRowsByRecency(rows, 0, 2);
        return rows.toArray(new Object[0][]);
    }

    public static ManagerRoomDetails lookupRoomDetails(Staff staff, String requestId)
            throws DatabaseException {
        return DatabaseHandler.getInstance().fetchRoomDetailsForAssignedStaff(
                requestId, staff.getUserId());
    }

    public static void saveManageRequestStatuses(Staff staff, List<Object[]> rows)
            throws DatabaseException {
        for (Object[] row : rows) {
            String requestId = String.valueOf(row[0]);
            Status newStatus = parseDisplayStatus(String.valueOf(row[4]));
            if (newStatus == Status.IN_PROGRESS) {
                continue;
            }
            if (newStatus != Status.COMPLETED && newStatus != Status.CANCELLED) {
                throw new DatabaseException(
                        "Staff can only change status to COMPLETED or CANCELLED.");
            }
            updateAssignedRequestStatus(staff, requestId, newStatus);
        }
    }

    public static void updateAssignedRequestStatus(Staff staff, String requestId, Status status)
            throws DatabaseException {
        MaintenanceRequest request = findAssignedRequest(staff, requestId);
        if (request == null) {
            throw new DatabaseException("Request [" + requestId + "] is not assigned to you.");
        }
        if (request.getStatus() != Status.IN_PROGRESS) {
            throw new DatabaseException("Only in-progress requests can be updated.");
        }
        if (status != Status.COMPLETED && status != Status.CANCELLED) {
            throw new DatabaseException(
                    "Staff can only change status to COMPLETED or CANCELLED.");
        }
        staff.updateRequestStatus(requestId, status);
        dataAccess.updateRequestStatus(requestId, status, staff.getUserId());
        manager.updateRequestStatus(requestId, status);
    }

    private static MaintenanceRequest findAssignedRequest(Staff staff, String requestId) {
        if (requestId == null) {
            return null;
        }
        String normalized = requestId.trim().toUpperCase();
        for (int i = 0; i < staff.getCount(); i++) {
            MaintenanceRequest request = staff.getAssignedRequestAt(i);
            if (request != null && request.getRequestId().equalsIgnoreCase(normalized)) {
                return request;
            }
        }
        return null;
    }

    private static Status parseDisplayStatus(String displayStatus) throws DatabaseException {
        String normalized = StatusBadgeLabel.formatStatus(displayStatus).replace(' ', '_');
        try {
            return Status.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new DatabaseException("Unsupported status value: " + displayStatus);
        }
    }

    private static Object[] toActiveRow(MaintenanceRequest request) {
        return new Object[]{
            request.getRequestId(),
            request.getRequestTypeLabel(),
            ManagerService.formatDate(request.getDateRaised()),
            ManagerService.formatPriority(request.getPriority()),
            ManagerService.formatDisplayStatus(request.getStatus().name())
        };
    }

    private static Object[] toManageRow(MaintenanceRequest request) {
        return new Object[]{
            request.getRequestId(),
            request.getRequestTypeLabel(),
            ManagerService.formatDate(request.getDateRaised()),
            ManagerService.formatPriority(request.getPriority()),
            ManagerService.formatDisplayStatus(request.getStatus().name())
        };
    }

    private static Object[] toHistoryRow(MaintenanceRequest request) {
        return new Object[]{
            request.getRequestId(),
            request.getRequestTypeLabel(),
            ManagerService.formatDate(request.getDateRaised()),
            ManagerService.formatPriority(request.getPriority()),
            ManagerService.formatDisplayStatus(request.getStatus().name())
        };
    }
}
