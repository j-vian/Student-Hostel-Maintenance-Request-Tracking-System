/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public class MaintenanceFactory {
    //createRequest method
    public MaintenanceRequest createRequest(String type, String requestId, String description, String priority) {
        String normalizedType = type.toLowerCase();
        
        switch (normalizedType) {
            case "electrical":
                System.out.println("Factory: Creating ElectricalRequest [" + requestId + "]...");
                return new ElectricalRequest(requestId, description, priority);

            case "plumbing":
                System.out.println("Factory: Creating PlumbingRequest [" + requestId + "]...");
                return new PlumbingRequest(requestId, description, priority);

            case "furniture":
                System.out.println("Factory: Creating FurnitureRequest [" + requestId + "]...");
                return new FurnitureRequest(requestId, description, priority);

            default:
                System.out.println("Factory: Unknown request type [" + type + "]. Returning null.");
                return null;
        }
    }
}
