/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author chaic
 */


/**
 * Concrete class representing a maintenance staff member.
 * Extends User and can view and update assigned maintenance requests.
 */
public class Staff extends User {

    // Private attributes - access through getters/setters only
    private String staffRole;
    private MaintenanceRequest[] assignedRequests;
    private int count; // tracks how many requests have been assigned

    /**
     * Constructor initialises all Staff attributes including inherited ones.
     */
    public Staff(String userId, String name, String email, String staffRole) {
        super(userId, name, email); // Call parent User constructor
        this.staffRole        = staffRole;
        this.assignedRequests = new MaintenanceRequest[100]; // max 100 requests
        this.count = 0; //set count to 0 by default
    }

    //Updates the status of a given request if it belongs to this staff member's assigned list.
    public void updateRequestStatus(String requestId, Status status) {
        // Loop only through filled slots using count, not the full 100
        for (int i = 0; i < count; i++) {
            if (assignedRequests[i].getRequestId().equals(requestId)) {
                assignedRequests[i].setStatus(status);
                System.out.println("Request " + requestId + " status updated to " + status + ".");
                return;
            }
        }
        // If no match was found, notify the user
        System.out.println("Request " + requestId + " not found in " + getName() + "'s assigned list.");
    }

    //Displays all maintenance requests assigned to this staff member.
    public void viewAssignedRequests() {
        // Use count to check if any requests have been assigned
        if (count == 0) {
            System.out.println("No requests assigned to " + getName() + ".");
            return;
        }
        System.out.println("Requests assigned to " + getName() + ":");
        // Loop only through filled slots using count, not the full 100
        for (int i = 0; i < count; i++) {
            assignedRequests[i].displayDetails();
        }
    }

    // Getters/Setter

    public String getStaffRole() {
        return staffRole;
    }

    //Returns the array of maintenance requests assigned to this staff member.
    public MaintenanceRequest[] getAssignedRequests() {
        return assignedRequests;
    }

    //Returns the number of requests currently assigned to this staff member. 
    public int getCount() {
        return count;
    }

   

    //Updates the staff member's role. 
    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }
    
    //addAssignedRequest method - To add the request to the Staff object's assignedRequests array
    public void addAssignedRequest(MaintenanceRequest request) {
        if (count < assignedRequests.length) {
            assignedRequests[count] = request;
            count++;
        }
    }
}