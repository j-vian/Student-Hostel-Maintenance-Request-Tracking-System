package studenthostelmaintenancerequest.trackingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import studenthostelmaintenancerequest.trackingsystem.gui.common.StatusBadgeLabel;

/**
 * Shared display formatting for request rows loaded from the database.
 */
public final class RequestDisplayFormatter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

    private RequestDisplayFormatter() {
    }

    public static String formatFullName(String firstName, String lastName) {
        return ((firstName == null ? "" : firstName.trim()) + " "
                + (lastName == null ? "" : lastName.trim())).trim();
    }

    public static String formatRequestType(String requestType) {
        if (requestType == null || requestType.isBlank()) {
            return "";
        }
        return switch (requestType.trim().toLowerCase()) {
            case "electrical" -> "Electrical";
            case "plumbing" -> "Plumbing";
            case "furniture" -> "Furniture";
            default -> requestType.substring(0, 1).toUpperCase() + requestType.substring(1);
        };
    }

    public static String formatDisplayStatus(String dbStatus) {
        return StatusBadgeLabel.formatStatus(dbStatus);
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return DATE_FORMAT.format(dateTime);
    }

    public static String formatPriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return "";
        }
        return switch (priority.trim().toLowerCase()) {
            case "high" -> "High";
            case "medium" -> "Medium";
            case "low" -> "Low";
            default -> priority.substring(0, 1).toUpperCase() + priority.substring(1);
        };
    }
}
