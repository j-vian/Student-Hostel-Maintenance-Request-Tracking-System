package studenthostelmaintenancerequest.trackingsystem;

/**
 * Backend operations for student accounts (used by student GUI when ready).
 */
public final class StudentService {

    private static final DataAccess dataAccess = DatabaseHandler.getInstance();
    private static final MaintenanceFactory factory = new MaintenanceFactory();
    private static final MaintenanceManager manager = new MaintenanceManager();

    private StudentService() {
    }

    public static MaintenanceManager getManager() {
        return manager;
    }

    public static void refreshStudentRequests(Student student) throws DatabaseException {
        MaintenanceRequest[] requests = dataAccess.fetchRequestsByStudent(student.getUserId());
        student.loadRequests(requests);
        manager.loadRequests(requests);
    }

    public static MaintenanceRequest submitRequest(Student student, String requestType,
            String description, String priority) throws DatabaseException {
        Room room = student.getRoom();
        if (room == null || room.getRoomId() <= 0) {
            throw new DatabaseException("Student room is not linked to the database.");
        }

        String requestId = dataAccess.generateNextRequestId();
        MaintenanceRequest request = factory.createRequest(
                requestType, requestId, description, priority, room);

        dataAccess.insertRequest(request, student.getUserId(), room.getRoomId(), student.getUserId());
        student.submitRequest(request);
        manager.addRequest(request);
        request.processRequest();
        return request;
    }
}
