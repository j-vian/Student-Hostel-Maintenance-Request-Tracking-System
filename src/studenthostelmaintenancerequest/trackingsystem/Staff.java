/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Concrete class representing a maintenance staff member.
 *
 * @author chaic
 */
public class Staff extends User {

    // instance fields for class state
    private String staffRole;
    private MaintenanceRequest[] assignedRequests;
    private int count;

    // construct object with initial state
    public Staff(String userId, String username, String firstName, String lastName,
            String email, String password, String staffRole) {
        super(userId, username, firstName, lastName, email, password, UserRole.STAFF);
        this.staffRole = staffRole;
        this.assignedRequests = new MaintenanceRequest[100];
        this.count = 0;
    }

    /**
     * Legacy constructor for the Phase II console application.
     */
    // construct object with initial state
    public Staff(String userId, String fullName, String email, String staffRole) {
        this(userId, fullName.toLowerCase().replace(" ", ""), fullName, "",
                email, "", staffRole);
    }

    @Override
    // process business logic
    public void login() {
        System.out.println("Staff " + getFullName() + " (ID: " + getUserId() + ", Role: "
                + staffRole + ") logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Staff " + getFullName() + " (ID: " + getUserId() + ") logged out.");
    }

    public void updateRequestStatus(String requestId, Status status) {
        for (int i = 0; i < count; i++) {
            if (assignedRequests[i].getRequestId().equals(requestId)) {
                assignedRequests[i].recordStatusChange(status);
                System.out.println("Request " + requestId + " status updated to " + status + ".");
                return;
            }
        }
        System.out.println("Request " + requestId + " not found in " + getFullName() + "'s assigned list.");
    }

    public void viewAssignedRequests() {
        if (count == 0) {
            System.out.println("No requests assigned to " + getFullName() + ".");
            return;
        }

        System.out.println("Requests assigned to " + getFullName() + ":");
        for (int i = 0; i < count; i++) {
            assignedRequests[i].displayDetails();
            System.out.println();
        }
    }

    public String getStaffRole() {
        return staffRole;
    }

    public int getCount() {
        return count;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public void addAssignedRequest(MaintenanceRequest request) {
        if (count < assignedRequests.length) {
            assignedRequests[count] = request;
            count++;
        }
    }

    public MaintenanceRequest getAssignedRequestAt(int index) {
        if (index < 0 || index >= count) {
            return null;
        }
        return assignedRequests[index];
    }

    public void loadAssignedRequests(MaintenanceRequest[] loadedRequests) {
        count = 0;
        if (loadedRequests == null) {
            return;
        }
        for (MaintenanceRequest request : loadedRequests) {
            if (request != null && count < assignedRequests.length) {
                assignedRequests[count++] = request;
            }
        }
    }
}
