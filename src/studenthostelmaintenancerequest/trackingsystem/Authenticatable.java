package studenthostelmaintenancerequest.trackingsystem;

/**
 * Interface for classes that support authentication (Phase III requirement).
 */
public interface Authenticatable {

    // process class operations
    String getEmail();

    boolean checkPassword(String plainPassword);

    UserRole getRole();
}
