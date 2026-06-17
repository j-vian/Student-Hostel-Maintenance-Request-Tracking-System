package studenthostelmaintenancerequest.trackingsystem;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import studenthostelmaintenancerequest.trackingsystem.gui.auth.LoginFrame;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 * Backend operations for student GUI screens.
 */
public final class StudentService {

    // constants used by this class
    private static final DataAccess dataAccess = DatabaseHandler.getInstance();
    private static final MaintenanceFactory factory = new MaintenanceFactory();
    private static final MaintenanceManager manager = new MaintenanceManager();

    // construct frame and initialize UI
    private StudentService() {
    }

    public static MaintenanceManager getManager() {
        return manager;
    }

    public static Student requireStudent(JFrame frame) {
        if (!SessionManager.isLoggedIn()
                || SessionManager.getCurrentUser().getRole() != UserRole.STUDENT) {
            JOptionPane.showMessageDialog(frame,
                    "Please log in with a student account to continue.",
                    "Session Required",
                    JOptionPane.WARNING_MESSAGE);
            UIHelper.navigateTo(frame, new LoginFrame());
            return null;
        }
        return (Student) SessionManager.getCurrentUser();
    }

    public static void refreshStudentRequests(Student student) throws DatabaseException {
        MaintenanceRequest[] requests = dataAccess.fetchRequestsByStudent(student.getUserId());
        student.loadRequests(requests);
        manager.loadRequests(requests);
    }

    public static Object[][] getProfileRows(Student student) {
        String roomNumber = student.getRoom() != null ? student.getRoomNumber() : "";
        return new Object[][]{
            {"Name", student.getFullName()},
            {"Student ID", student.getUserId()},
            {"Room No.", roomNumber}
        };
    }

    public static Object[][] getActiveRequestRows(Student student) throws DatabaseException {
        refreshStudentRequests(student);
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < student.getCount(); i++) {
            MaintenanceRequest request = student.getRequestAt(i);
            if (request == null) {
                continue;
            }
            Status status = request.getStatus();
            if (status == Status.SUBMITTED || status == Status.IN_PROGRESS) {
                rows.add(toActiveRow(request));
            }
        }
        ManagerService.sortRowsByRecency(rows, 0, 2);
        return rows.toArray(new Object[0][]);
    }

    public static Object[][] getHistoryRequestRows(Student student) throws DatabaseException {
        refreshStudentRequests(student);
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < student.getCount(); i++) {
            MaintenanceRequest request = student.getRequestAt(i);
            if (request != null) {
                rows.add(toHistoryRow(request));
            }
        }
        ManagerService.sortRowsByRecency(rows, 0, 2);
        return rows.toArray(new Object[0][]);
    }

    public static MaintenanceRequest submitRequest(Student student, String requestType,
            String description, String priority, String placeName) throws DatabaseException {
        Room studentRoom = student.getRoom();
        if (studentRoom == null || studentRoom.getRoomNumber() == null
                || studentRoom.getRoomNumber().isBlank()) {
            throw new DatabaseException("Student room is not linked to the database.");
        }
        if (placeName == null || placeName.isBlank()) {
            throw new DatabaseException("Place name is required.");
        }

        String roomNumber = studentRoom.getRoomNumber().trim();
        String normalizedPlace = placeName.trim();
        int roomId = dataAccess.findOrCreateRoom(roomNumber, normalizedPlace);
        Room room = new Room(roomId, roomNumber, normalizedPlace);

        String requestId = dataAccess.generateNextRequestId();
        MaintenanceRequest request = factory.createRequest(
                requestType, requestId, description, priority, room);
        if (request == null) {
            throw new DatabaseException("Unsupported request type: " + requestType);
        }

        dataAccess.insertRequest(request, student.getUserId(), roomId, student.getUserId());
        student.submitRequest(request);
        manager.addRequest(request);
        request.processRequest();
        return request;
    }

    public static String normalizeRequestType(String displayType) {
        if (displayType == null) {
            return "";
        }
        return displayType.trim().toLowerCase();
    }

    private static Object[] toActiveRow(MaintenanceRequest request) {
        return new Object[]{
            request.getRequestId(),
            request.getRequestTypeLabel(),
            ManagerService.formatDate(request.getDateRaised()),
            ManagerService.formatDisplayStatus(request.getStatus().name())
        };
    }

    private static Object[] toHistoryRow(MaintenanceRequest request) {
        return new Object[]{
            request.getRequestId(),
            request.getRequestTypeLabel(),
            ManagerService.formatDate(request.getDateRaised()),
            request.getPriority(),
            ManagerService.formatDisplayStatus(request.getStatus().name())
        };
    }
}
