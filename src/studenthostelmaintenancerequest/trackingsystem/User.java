/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Abstract base class representing a system user.
 *
 * @author chaic
 */
public abstract class User implements Authenticatable {

    // instance fields for class state
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;

    // construct object with initial state
    public User(String userId, String username, String firstName, String lastName,
            String email, String password, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Legacy constructor for the Phase II console application.
     */
    // construct object with initial state
    protected User(String userId, String fullName, String email) {
        this(userId, fullName.toLowerCase().replace(" ", ""), fullName, "", email, "", UserRole.STUDENT);
    }

    // process business logic
    public void login() {
        System.out.println(getFullName() + " (ID: " + userId + ") has logged in.");
    }

    public void logout() {
        System.out.println(getFullName() + " (ID: " + userId + ") has logged out.");
    }

    @Override
    public boolean checkPassword(String plainPassword) {
        return password != null && password.equals(plainPassword);
    }

    public String getFullName() {
        String first = firstName == null ? "" : firstName.trim();
        String last = lastName == null ? "" : lastName.trim();
        if (first.isEmpty()) {
            return last;
        }
        if (last.isEmpty()) {
            return first;
        }
        return first + " " + last;
    }

    /**
     * Backward-compatible alias used by Phase II console code.
     */
    public String getName() {
        return getFullName();
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setRole(UserRole role) {
        this.role = role;
    }
}
