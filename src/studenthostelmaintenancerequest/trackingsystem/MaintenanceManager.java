/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public class MaintenanceManager {
    //data variable
    private MaintenanceRequest[] requests;
    private int count; 
    
    //constructor
    public MaintenanceManager() {
        this.requests = new MaintenanceRequest[100];
        this.count = 0;
    }
    
    //addRequest method
    public void addRequest(MaintenanceRequest request) {
        if (count < requests.length) {
            requests[count] = request;
            count++;
            System.out.println("Request [" + request.getRequestId() + "] added successfully.");
        }
    }
    
    //assignStaff method
    public void assignStaff(String requestId, Staff staff) {
        for (int i = 0; i < count; i++) {
            if (requests[i].getRequestId().equals(requestId)) {
                requests[i].setStatus(Status.IN_PROGRESS);
                System.out.println("Staff [" + staff.getName() + "] assigned to Request " + requestId + "]. Status updated to IN_PROGRESS.");
                return;
            }
        }
        System.out.println("Request [" + requestId + "] not founc. Cannot assign staff.");
    }
    
    //updateRequestStatus method
    public void updateRequestStatus(String requestId, Status newStatus) {
        for (int i = 0; i < count; i++) {
            if (requests[i].getRequest().equals(requestId)) {
                requests[i].setStatus(newStatus);
                System.out.println("Request [" + requestId + "] status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Request [" + requestId + "] not found. Status not updated.");
    }
    
    //searchRequest method
    public void searchRequest(String requestId) {
        for (int i = 0; i < count; i++) {
            if (requests[i].getRequestId().equals(requestId)) {
                System.out.println("Request found:");
                requests[i].displayDetails();
                return;
            }
        }
        System.out.println("Request [" + requestId + "] not found in the system.");
    }
    
    //getter
    public MaintenanceRequest[] getRequests() { return requests; }
    public int getCount() { return count; }
}
