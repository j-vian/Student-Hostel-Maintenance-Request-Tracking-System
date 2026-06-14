package studenthostelmaintenancerequest.trackingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import studenthostelmaintenancerequest.trackingsystem.gui.common.StatusBadgeLabel;

/**
 * Loads and saves data for manager GUI screens.
 */
public final class ManagerService {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

    private static final DataAccess DATA_ACCESS = DatabaseHandler.getInstance();

    private ManagerService() {
    }

    public static int[] getDashboardCounts() throws DatabaseException {
        return DatabaseHandler.getInstance().fetchDashboardCounts();
    }

    public static Object[][] getDashboardRecentRows(int limit) throws DatabaseException {
        return DatabaseHandler.getInstance().fetchOverviewRequestRows(limit);
    }

    public static Object[][] getManageActiveRows() throws DatabaseException {
        return DatabaseHandler.getInstance().fetchManageActiveRows(null);
    }

    public static Object[][] getManageActiveRows(String requestIdSearch) throws DatabaseException {
        return DatabaseHandler.getInstance().fetchManageActiveRows(requestIdSearch);
    }

    public static Object[][] getAssignStaffRows() throws DatabaseException {
        return DatabaseHandler.getInstance().fetchAssignStaffRows(null);
    }

    public static Object[][] getAssignStaffRows(String requestIdSearch) throws DatabaseException {
        return DatabaseHandler.getInstance().fetchAssignStaffRows(requestIdSearch);
    }

    public static Object[][] getHistoryRows() throws DatabaseException {
        return DatabaseHandler.getInstance().fetchHistoryRows(null);
    }

    public static Object[][] getHistoryRows(String requestIdSearch) throws DatabaseException {
        return DatabaseHandler.getInstance().fetchHistoryRows(requestIdSearch);
    }

    public static ManagerRoomDetails lookupRoomDetails(String requestId) throws DatabaseException {
        return DatabaseHandler.getInstance().fetchRoomDetailsByRequestId(requestId);
    }

    public static String[] getStaffComboOptions() throws DatabaseException {
        User[] staffMembers = DATA_ACCESS.fetchAllStaff();
        String[] options = new String[staffMembers.length];
        for (int i = 0; i < staffMembers.length; i++) {
            Staff staff = (Staff) staffMembers[i];
            options[i] = staff.getFullName() + " (" + staff.getStaffRole() + ")";
        }
        return options;
    }

    public static void saveActiveRequestStatuses(List<Object[]> rows) throws DatabaseException {
        String changedBy = currentManagerId();
        DatabaseHandler db = DatabaseHandler.getInstance();

        for (Object[] row : rows) {
            String requestId = String.valueOf(row[0]);
            Status status = parseDisplayStatus(String.valueOf(row[6]));
            if (status == Status.IN_PROGRESS) {
                continue;
            }
            if (status != Status.COMPLETED && status != Status.CANCELLED) {
                throw new DatabaseException(
                        "Active requests can only be set to COMPLETED or CANCELLED.");
            }
            db.updateRequestStatus(requestId, status, changedBy);
        }
    }

    public static void saveStaffAssignments(List<Object[]> rows) throws DatabaseException {
        String changedBy = currentManagerId();
        DatabaseHandler db = DatabaseHandler.getInstance();

        for (Object[] row : rows) {
            Object assignedStaff = row[6];
            if (assignedStaff == null || String.valueOf(assignedStaff).isBlank()) {
                continue;
            }
            String requestId = String.valueOf(row[0]);
            String staffId = db.findStaffIdByDisplayName(String.valueOf(assignedStaff));
            if (staffId == null) {
                throw new DatabaseException("Unable to match staff member: " + assignedStaff);
            }
            db.assignStaffToRequest(requestId, staffId, changedBy);
        }
    }

    public static Status parseDisplayStatus(String displayStatus) throws DatabaseException {
        String normalized = StatusBadgeLabel.formatStatus(displayStatus).replace(' ', '_');
        try {
            return Status.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new DatabaseException("Unsupported status value: " + displayStatus);
        }
    }

    private static String currentManagerId() {
        if (SessionManager.isLoggedIn()) {
            return SessionManager.getCurrentUser().getUserId();
        }
        return null;
    }

    static String formatFullName(String firstName, String lastName) {
        return (firstName + " " + lastName).trim();
    }

    static String formatRequestType(String requestType) {
        if (requestType == null || requestType.isBlank()) {
            return "";
        }
        String value = requestType.trim().toLowerCase();
        return switch (value) {
            case "electrical" -> "Electrical";
            case "plumbing" -> "Plumbing";
            case "furniture" -> "Furniture";
            default -> requestType.substring(0, 1).toUpperCase() + requestType.substring(1);
        };
    }

    static String formatDisplayStatus(String dbStatus) {
        return StatusBadgeLabel.formatStatus(dbStatus);
    }

    static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return DATE_FORMAT.format(dateTime);
    }

    static String formatPriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return "";
        }
        String value = priority.trim().toLowerCase();
        return switch (value) {
            case "high" -> "High";
            case "medium" -> "Medium";
            case "low" -> "Low";
            default -> priority.substring(0, 1).toUpperCase() + priority.substring(1);
        };
    }

    public static String formatBlockDisplay(String roomNumber, String placeName) {
        String blockLabel = "Block";
        if (roomNumber != null && !roomNumber.isBlank()) {
            int dashIndex = roomNumber.indexOf('-');
            if (dashIndex > 0) {
                String blockLetter = roomNumber.substring(0, dashIndex).trim();
                if (!blockLetter.isEmpty()) {
                    blockLabel = "Block " + blockLetter.toUpperCase();
                }
            }
        }
        String formattedPlace = formatPlaceName(placeName);
        return formattedPlace.isEmpty() ? blockLabel : blockLabel + ", " + formattedPlace;
    }

    private static String formatPlaceName(String placeName) {
        if (placeName == null || placeName.isBlank()) {
            return "";
        }
        String trimmed = placeName.trim().toLowerCase();
        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1);
    }
}
