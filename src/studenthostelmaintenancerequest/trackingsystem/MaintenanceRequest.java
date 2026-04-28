/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public abstract class MaintenanceRequest { //ABSTRACT CLASS - MaintenanceRequest
    //data variable (ENCAPSULATION)
    private String requestId;
    private String description;
    private String priority;
    private Status status;
    
    //constructor
    public MaintenanceRequest(String requestId, String description, String priority) {
        this.requestId = requestId;
        this.description = description;
        this.priority = priority;
        this.status = Status.SUBMITTED; //set request status to SUBMITTED by default
    }
    
    //getProcessRequest method - No body
    public abstract void processRequest();
    
    //displayDetails method - output
    public void displayDetails() {
        System.out.println("=== Maintenance Request Details ===");
        System.out.println("Request ID : " + requestId);
        System.out.println("Description: " + description);
        System.out.println("Priority   : " + priority);
        System.out.println("Status     : " + status);
    }
    
    //setters and getters
    public void setRequestId(String requestId) { this.requestId = requestId;}
    public String getRequestId() { return requestId; }
    public void setDescription(String description) { this.description = description;}
    public String getDescription() { return description; }
    public void setPriority(String priority) { this.priority = priority;}
    public String getPriority() { return priority; }
    public void setStatus(Status status) { this.status = status; }
    public Status getStatus() { return status; }
}
