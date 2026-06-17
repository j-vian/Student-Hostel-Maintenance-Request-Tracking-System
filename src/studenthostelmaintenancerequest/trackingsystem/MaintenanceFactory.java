/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Factory for creating concrete maintenance request objects.
 *
 * @author vian
 */
public class MaintenanceFactory {

    public MaintenanceRequest createRequest(String type, String requestId,
            String description, String priority, Room room) {
        String normalizedType = type.toLowerCase().trim();

        switch (normalizedType) {
            case "electrical":
                System.out.println("Factory: Creating ElectricalRequest [" + requestId + "]...");
                return new ElectricalRequest(requestId, description, priority, room);

            case "plumbing":
                System.out.println("Factory: Creating PlumbingRequest [" + requestId + "]...");
                return new PlumbingRequest(requestId, description, priority, room);

            case "furniture":
                System.out.println("Factory: Creating FurnitureRequest [" + requestId + "]...");
                return new FurnitureRequest(requestId, description, priority, room);

            default:
                System.out.println("Factory: Unknown request type [" + type + "]. Returning null.");
                return null;
        }
    }
}
