/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * @author vian
 */
public class ElectricalRequest extends MaintenanceRequest {

    public ElectricalRequest(String requestId, String description, String priority, Room room) {
        super(requestId, description, priority, room);
    }

    @Override
    public void processRequest() {
        System.out.println("Processing Electrical Request [ID: " + getRequestId() + "]");
        System.out.println("Issue: " + getDescription());
        System.out.println("Priority: " + getPriority());
        System.out.println("Action: Dispatching electrician to inspect and resolve the issue.");
        System.out.println("Status updated to: " + getStatus());
    }
}
