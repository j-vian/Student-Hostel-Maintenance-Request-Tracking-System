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

    private String roomNumber;
    private String block;
    private MaintenanceRequest[] linkedRequests;
    private int requestCount;

    public Room(String roomNumber, String block) {
        this.roomNumber = roomNumber;
        this.block = block;
        this.linkedRequests = new MaintenanceRequest[100];
        this.requestCount = 0;
    }

    public String getRoomDetails() {
        return "Room Number: " + roomNumber + ", Block: " + block;
    }

    /**
     * Aggregation: a room references requests without owning their lifecycle.
     */
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
