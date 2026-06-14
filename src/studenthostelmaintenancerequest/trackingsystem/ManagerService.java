package studenthostelmaintenancerequest.trackingsystem;

import java.time.LocalDate;
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
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchOverviewRequestRows(limit), 0, 4);
    }

    public static Object[][] sortRowsByRecency(Object[][] rows, int requestIdColumn, int dateRaisedColumn) {
        if (rows == null || rows.length == 0) {
            return rows;
        }
        java.util.List<Object[]> sorted = new java.util.ArrayList<>();
        for (Object[] row : rows) {
            sorted.add(row.clone());
        }
        sortRowsByRecency(sorted, requestIdColumn, dateRaisedColumn);
        return sorted.toArray(new Object[0][]);
    }

    public static void sortRowsByRecency(java.util.List<Object[]> rows, int requestIdColumn, int dateRaisedColumn) {
        if (rows == null || rows.size() < 2) {
            return;
        }
        rows.sort((left, right) -> {
            int byDate = compareDateValues(
                    valueAt(left, dateRaisedColumn),
                    valueAt(right, dateRaisedColumn));
            if (byDate != 0) {
                return byDate;
            }
            return compareRequestIds(
                    valueAt(left, requestIdColumn),
                    valueAt(right, requestIdColumn));
        });
    }

    private static String valueAt(Object[] row, int column) {
        if (row == null || column < 0 || column >= row.length || row[column] == null) {
            return "";
        }
        return String.valueOf(row[column]);
    }

    private static int compareDateValues(String left, String right) {
        LocalDate leftDate = parseDisplayDate(left);
        LocalDate rightDate = parseDisplayDate(right);
        return rightDate.compareTo(leftDate);
    }

    private static LocalDate parseDisplayDate(String value) {
        if (value == null || value.isBlank()) {
            return LocalDate.MIN;
        }
        try {
            return LocalDate.parse(value.trim(), DATE_FORMAT);
        } catch (java.time.format.DateTimeParseException ex) {
            return LocalDate.MIN;
        }
    }

    private static int compareRequestIds(String left, String right) {
        return Integer.compare(parseRequestNumber(right), parseRequestNumber(left));
    }

    private static int parseRequestNumber(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            return 0;
        }
        String digits = requestId.replaceAll("\\D+", "");
        if (digits.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public static Object[][] getManageActiveRows() throws DatabaseException {
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchManageActiveRows(null), 0, 4);
    }

    public static Object[][] getManageActiveRows(String requestIdSearch) throws DatabaseException {
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchManageActiveRows(requestIdSearch), 0, 4);
    }

    public static Object[][] getAssignStaffRows() throws DatabaseException {
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchAssignStaffRows(null), 0, 3);
    }

    public static Object[][] getAssignStaffRows(String requestIdSearch) throws DatabaseException {
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchAssignStaffRows(requestIdSearch), 0, 3);
    }

    public static Object[][] getHistoryRows() throws DatabaseException {
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchHistoryRows(null), 0, 5);
    }

    public static Object[][] getHistoryRows(String requestIdSearch) throws DatabaseException {
        return sortRowsByRecency(
                DatabaseHandler.getInstance().fetchHistoryRows(requestIdSearch), 0, 5);
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
        return RequestDisplayFormatter.formatFullName(firstName, lastName);
    }

    static String formatRequestType(String requestType) {
        return RequestDisplayFormatter.formatRequestType(requestType);
    }

    static String formatDisplayStatus(String dbStatus) {
        return RequestDisplayFormatter.formatDisplayStatus(dbStatus);
    }

    static String formatDate(LocalDateTime dateTime) {
        return RequestDisplayFormatter.formatDate(dateTime);
    }

    static String formatPriority(String priority) {
        return RequestDisplayFormatter.formatPriority(priority);
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
