package studenthostelmaintenancerequest.trackingsystem;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;
import studenthostelmaintenancerequest.trackingsystem.gui.manager.ManagerDashboardFrame;
import studenthostelmaintenancerequest.trackingsystem.gui.staff.staffDashboard;
import studenthostelmaintenancerequest.trackingsystem.gui.student.stdDarshboard;

/**
 * Handles login, sign-up validation, and role-based navigation.
 */
public final class AuthService {

    private static final DataAccess dataAccess = DatabaseHandler.getInstance();

    private AuthService() {
    }

    public static boolean isDatabaseReady() {
        return dataAccess.testConnection();
    }

    public static void login(JFrame currentFrame, String email, String password) {
        if (!isDatabaseReady()) {
            showError(currentFrame,
                    "Cannot connect to MySQL.\nStart XAMPP MySQL and run database/hostel_maintenance_schema.sql.");
            return;
        }

        try {
            User user = dataAccess.authenticate(email, password);
            SessionManager.setCurrentUser(user);
            user.login();
            navigateAfterLogin(currentFrame, user);
        } catch (DatabaseException ex) {
            showError(currentFrame, ex.getMessage());
        }
    }

    public static void register(JFrame currentFrame, SignUpData data, String confirmPassword) {
        if (!isDatabaseReady()) {
            showError(currentFrame,
                    "Cannot connect to MySQL.\nStart XAMPP MySQL and run database/hostel_maintenance_schema.sql.");
            return;
        }

        if (data.getPassword() == null || !data.getPassword().equals(confirmPassword)) {
            showError(currentFrame, "Password and confirm password do not match.");
            return;
        }

        try {
            if (data.getRole() == UserRole.STUDENT) {
                dataAccess.registerStudent(data);
            } else if (data.getRole() == UserRole.STAFF) {
                dataAccess.registerStaff(data);
            } else {
                showError(currentFrame, "Only Student and Staff accounts can be created here.");
                return;
            }

            JOptionPane.showMessageDialog(currentFrame,
                    "Account created successfully. You can now log in.",
                    "Sign Up",
                    JOptionPane.INFORMATION_MESSAGE);
            UIHelper.navigateTo(currentFrame, new studenthostelmaintenancerequest.trackingsystem.gui.auth.LoginFrame());
        } catch (DatabaseException ex) {
            showError(currentFrame, ex.getMessage());
        }
    }

    private static void navigateAfterLogin(JFrame currentFrame, User user) {
        switch (user.getRole()) {
            case MANAGER:
                UIHelper.navigateTo(currentFrame, new ManagerDashboardFrame());
                break;
            case STUDENT:
                UIHelper.navigateTo(currentFrame, new stdDarshboard());
                break;
            case STAFF:
                UIHelper.navigateTo(currentFrame, new staffDashboard());
                break;
            default:
                showError(currentFrame, "Unsupported account role.");
                SessionManager.clear();
                break;
        }
    }

    private static void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Authentication", JOptionPane.ERROR_MESSAGE);
    }
}
