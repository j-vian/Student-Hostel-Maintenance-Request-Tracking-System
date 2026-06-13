/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Concrete class representing a maintenance staff member.
 * Aggregation: staff references assigned requests without owning them.
 *
 * @author chaic
 */
public class Staff extends User {

    private String staffRole;
    private MaintenanceRequest[] assignedRequests;
    private int count;

    public Staff(String userId, String name, String email, String staffRole) {
        super(userId, name, email);
        this.staffRole = staffRole;
        this.assignedRequests = new MaintenanceRequest[100];
        this.count = 0;
    }

    @Override
    public void login() {
        System.out.println("Staff " + getName() + " (ID: " + getUserId() + ", Role: "
                + staffRole + ") logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Staff " + getName() + " (ID: " + getUserId() + ") logged out.");
    }

    public void updateRequestStatus(String requestId, Status status) {
        for (int i = 0; i < count; i++) {
            if (assignedRequests[i].getRequestId().equals(requestId)) {
                assignedRequests[i].recordStatusChange(status);
                System.out.println("Request " + requestId + " status updated to " + status + ".");
                return;
            }
        }
        System.out.println("Request " + requestId + " not found in " + getName() + "'s assigned list.");
    }

    public void viewAssignedRequests() {
        if (count == 0) {
            System.out.println("No requests assigned to " + getName() + ".");
            return;
        }

        System.out.println("Requests assigned to " + getName() + ":");
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
}
