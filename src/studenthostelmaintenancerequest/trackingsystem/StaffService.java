package studenthostelmaintenancerequest.trackingsystem;

/**
 * Backend operations for staff accounts (used by staff GUI when ready).
 */
public final class StaffService {

    private static final DataAccess dataAccess = DatabaseHandler.getInstance();
    private static final MaintenanceManager manager = new MaintenanceManager();

    private StaffService() {
    }

    public static MaintenanceManager getManager() {
        return manager;
    }

    public static void refreshAssignedRequests(Staff staff) throws DatabaseException {
        MaintenanceRequest[] requests = dataAccess.fetchRequestsByStaff(staff.getUserId());
        staff.loadAssignedRequests(requests);
        manager.loadRequests(requests);
    }

    public static void updateAssignedRequestStatus(Staff staff, String requestId, Status status)
            throws DatabaseException {
        staff.updateRequestStatus(requestId, status);
        dataAccess.updateRequestStatus(requestId, status, staff.getUserId());
        manager.updateRequestStatus(requestId, status);
    }
}
