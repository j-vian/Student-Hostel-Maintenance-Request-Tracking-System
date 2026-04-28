/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public class FurnitureRequest extends MaintenanceRequest {
    //constructor
    public FurnitureRequest(String requestId, String description, String priority) {
        super(requestId, description, priority);
    }
    
    //processRequest method - Override
    @Override
    public void processRequest() {
        System.out.println("Processing Furniture Request [ID: " + getRequestId() + "]");
        System.out.println("Issue: " + getDescription());
        System.out.println("Priority: " + getPriority());
        System.out.println("Action: Dispatching maintenance staff to repair or replace furniture.");
        System.out.println("Status updated to: " + getStatus());
    }
}
