package studenthostelmaintenancerequest.trackingsystem;

/**
 * Immutable sign-up payload from the registration form.
 */
public final class SignUpData {

    private final String userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final UserRole role;
    private final String roomNumber;
    private final String staffRole;
    private final String otherExpertise;

    public SignUpData(String userId, String username, String firstName, String lastName,
            String email, String password, UserRole role, String roomNumber,
            String staffRole, String otherExpertise) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.roomNumber = roomNumber;
        this.staffRole = staffRole;
        this.otherExpertise = otherExpertise;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public String getOtherExpertise() {
        return otherExpertise;
    }
}
