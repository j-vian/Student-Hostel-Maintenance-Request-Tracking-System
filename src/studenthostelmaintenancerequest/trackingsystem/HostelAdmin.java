package studenthostelmaintenancerequest.trackingsystem;

/**
 * Admin user account (pre-seeded in the database, not available via sign-up).
 */
public class HostelAdmin extends User {

    public HostelAdmin(String userId, String username, String firstName, String lastName,
            String email, String password) {
        super(userId, username, firstName, lastName, email, password, UserRole.MANAGER);
    }

    @Override
    public void login() {
        System.out.println("Manager " + getFullName() + " (ID: " + getUserId() + ") logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Manager " + getFullName() + " (ID: " + getUserId() + ") logged out.");
    }
}
