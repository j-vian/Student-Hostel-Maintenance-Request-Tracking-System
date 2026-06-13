package studenthostelmaintenancerequest.trackingsystem;

/**
 * Data-access contract implemented by {@link DatabaseHandler}.
 */
public interface DataAccess {

    boolean testConnection();

    User authenticate(String email, String plainPassword) throws DatabaseException;

    void registerStudent(SignUpData data) throws DatabaseException;

    void registerStaff(SignUpData data) throws DatabaseException;

    String generateNextRequestId() throws DatabaseException;

    void insertRequest(MaintenanceRequest request, String studentId, int roomId, String changedBy)
            throws DatabaseException;

    void updateRequestStatus(String requestId, Status newStatus, String changedBy)
            throws DatabaseException;

    void assignStaffToRequest(String requestId, String staffId, String changedBy)
            throws DatabaseException;

    void deleteRequest(String requestId) throws DatabaseException;

    MaintenanceRequest findRequestById(String requestId) throws DatabaseException;

    MaintenanceRequest[] fetchAllRequests() throws DatabaseException;

    MaintenanceRequest[] fetchRequestsByStudent(String studentId) throws DatabaseException;

    MaintenanceRequest[] fetchRequestsByStaff(String staffId) throws DatabaseException;

    User[] fetchAllStaff() throws DatabaseException;
}
