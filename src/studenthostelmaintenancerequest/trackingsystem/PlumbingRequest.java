/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public class PlumbingRequest extends MaintenanceRequest {
    //constructor
    public PlumbingRequest(String requestId, String description, String priority) {
        super(requestId, description, priority);
    }
    
    //processRequest method - Override
    @Override
    public void processRequest() {
        System.out.println("Processing Plumbing Request [ID: " + getRequestId() + "]");
        System.out.println("Description: " + getDescription());
        System.out.println("Priority: " + getPriority());
        System.out.println("Action: Dispatcing plumber to inspect and resolve the issue.");
        System.out.println("Statust updated to: " + getStatus());
    }
}
