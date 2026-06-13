package studenthostelmaintenancerequest.trackingsystem;

/**
 * Holds the currently logged-in user for GUI navigation.
 */
public final class SessionManager {

    private static User currentUser;

    private SessionManager() {
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void clear() {
        currentUser = null;
    }

    public static String getDisplayUsername() {
        if (currentUser == null) {
            return "";
        }
        return currentUser.getUsername();
    }

    public static String getFullName() {
        if (currentUser == null) {
            return "";
        }
        return currentUser.getFullName();
    }

    public static UserRole getCurrentRole() {
        if (currentUser == null) {
            return null;
        }
        return currentUser.getRole();
    }
}
