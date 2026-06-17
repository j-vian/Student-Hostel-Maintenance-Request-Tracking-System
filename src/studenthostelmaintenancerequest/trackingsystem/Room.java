/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Represents a hostel room and its linked maintenance requests (aggregation).
 *
 * @author vian
 */
public class Room {

    // instance fields for class state
    private int roomId;
    private String roomNumber;
    private String placeName;
    private MaintenanceRequest[] linkedRequests;
    private int requestCount;

    // construct object with initial state
    public Room(String roomNumber, String placeName) {
        this(0, roomNumber, placeName);
    }

    // construct object with initial state
    public Room(int roomId, String roomNumber, String placeName) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.placeName = placeName;
        this.linkedRequests = new MaintenanceRequest[100];
        this.requestCount = 0;
    }

    // return requested value
    public String getRoomDetails() {
        return "Room Number: " + roomNumber + ", Place: " + placeName;
    }

    public void linkMaintenanceRequest(MaintenanceRequest request) {
        if (requestCount < linkedRequests.length) {
            linkedRequests[requestCount] = request;
            requestCount++;
        }
    }

    public void viewLinkedRequests() {
        if (requestCount == 0) {
            System.out.println("No maintenance requests linked to " + getRoomDetails() + ".");
            return;
        }

        System.out.println("Maintenance requests for " + getRoomDetails() + ":");
        for (int i = 0; i < requestCount; i++) {
            linkedRequests[i].displayDetails();
            System.out.println();
        }
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /**
     * Backward-compatible alias for older code that used block.
     */
    public String getBlock() {
        return placeName;
    }

    public void setBlock(String placeName) {
        this.placeName = placeName;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
