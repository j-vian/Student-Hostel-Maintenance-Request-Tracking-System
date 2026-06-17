package studenthostelmaintenancerequest.trackingsystem;

/**
 * Checked exception for database operations.
 */
public class DatabaseException extends Exception {

    // construct object with initial state
    public DatabaseException(String message) {
        super(message);
    }

    // construct object with initial state
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
