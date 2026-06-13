package studenthostelmaintenancerequest.trackingsystem;

/**
 * Application roles stored in the users table.
 */
public enum UserRole {
    STUDENT,
    STAFF,
    MANAGER;

    public static UserRole fromDatabaseValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Role is required.");
        }
        return UserRole.valueOf(value.trim().toUpperCase());
    }
}
