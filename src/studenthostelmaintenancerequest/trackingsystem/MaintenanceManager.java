/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Central controller for managing maintenance requests.
 * Aggregation: the manager references requests stored elsewhere in the system.
 *
 * @author vian
 */
public class MaintenanceManager {

    private MaintenanceRequest[] requests;
    private int count;

    public MaintenanceManager() {
        this.requests = new MaintenanceRequest[100];
        this.count = 0;
    }

    public void addRequest(MaintenanceRequest request) {
        if (count < requests.length) {
            requests[count] = request;
            count++;
            System.out.println("Request [" + request.getRequestId() + "] added successfully.");
        }
    }

    public void assignStaff(String requestId, Staff staff) {
        MaintenanceRequest request = findRequestById(requestId);
        if (request == null) {
            System.out.println("Request [" + requestId + "] not found. Cannot assign staff.");
            return;
        }

        request.recordStatusChange(Status.IN_PROGRESS);
        staff.addAssignedRequest(request);
        System.out.println("Staff [" + staff.getName() + "] assigned to Request [" + requestId
                + "]. Status updated to IN_PROGRESS.");
    }

    public void updateRequestStatus(String requestId, Status newStatus) {
        MaintenanceRequest request = findRequestById(requestId);
        if (request == null) {
            System.out.println("Request [" + requestId + "] not found. Status not updated.");
            return;
        }

        request.recordStatusChange(newStatus);
        System.out.println("Request [" + requestId + "] status updated to: " + newStatus);
    }

    public void searchRequest(String requestId) {
        MaintenanceRequest request = findRequestById(requestId);
        if (request == null) {
            System.out.println("Request [" + requestId + "] not found in the system.");
            return;
        }

        System.out.println("Request found:");
        request.displayDetails();
    }

    public void displayAllRequests() {
        if (count == 0) {
            System.out.println("No requests have been submitted yet.");
            return;
        }

        for (int i = 0; i < count; i++) {
            requests[i].displayDetails();
            System.out.println();
        }
    }

    public MaintenanceRequest findRequestById(String requestId) {
        for (int i = 0; i < count; i++) {
            if (requests[i].getRequestId().equals(requestId)) {
                return requests[i];
            }
        }
        return null;
    }

    public int getCount() {
        return count;
    }

    public MaintenanceRequest getRequestAt(int index) {
        if (index < 0 || index >= count) {
            return null;
        }
        return requests[index];
    }

    /**
     * Loads database results into the in-memory array (Phase II + Phase III bridge).
     */
    public void loadRequests(MaintenanceRequest[] loadedRequests) {
        count = 0;
        if (loadedRequests == null) {
            return;
        }
        for (MaintenanceRequest request : loadedRequests) {
            if (request != null && count < requests.length) {
                requests[count++] = request;
            }
        }
    }
}
