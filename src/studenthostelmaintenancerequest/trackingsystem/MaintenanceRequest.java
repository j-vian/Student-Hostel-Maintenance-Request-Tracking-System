/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

import java.time.LocalDateTime;

/**
 * Abstract base class for all maintenance request types.
 *
 * @author vian
 */
public abstract class MaintenanceRequest {

    // instance fields for class state
    private String requestId;
    private String description;
    private String priority;
    private Status status;
    private Room room;
    private RequestHistory history;
    private LocalDateTime dateRaised;

    // construct object with initial state
    public MaintenanceRequest(String requestId, String description, String priority, Room room) {
        this.requestId = requestId;
        this.description = description;
        this.priority = priority;
        this.status = Status.SUBMITTED;
        this.room = room;
        this.dateRaised = LocalDateTime.now();
        this.history = new RequestHistory("H" + requestId, "SUBMITTED", this);
        if (room != null) {
            room.linkMaintenanceRequest(this);
        }
        history.recordStatus(Status.SUBMITTED);
    }

    // instance fields for class state
    public abstract void processRequest();

    public void displayDetails() {
        System.out.println("=== Maintenance Request Details ===");
        System.out.println("Request ID : " + requestId);
        System.out.println("Description: " + description);
        System.out.println("Priority   : " + priority);
        System.out.println("Status     : " + status);
        if (room != null) {
            System.out.println("Room       : " + room.getRoomDetails());
        }
    }

    public void recordStatusChange(Status newStatus) {
        this.status = newStatus;
        history.recordStatus(newStatus);
    }

    public void viewHistory() {
        history.viewHistory();
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Room getRoom() {
        return room;
    }

    public RequestHistory getHistory() {
        return history;
    }

    public LocalDateTime getDateRaised() {
        return dateRaised;
    }

    public void setDateRaised(LocalDateTime dateRaised) {
        this.dateRaised = dateRaised;
    }

    public String getRequestTypeLabel() {
        if (this instanceof ElectricalRequest) {
            return "Electrical";
        }
        if (this instanceof PlumbingRequest) {
            return "Plumbing";
        }
        if (this instanceof FurnitureRequest) {
            return "Furniture";
        }
        return "";
    }
}
