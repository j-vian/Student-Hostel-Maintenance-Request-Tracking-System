/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Represents a student who submits maintenance requests.
 *
 * @author farouq
 */
public class Student extends User {

    // instance fields for class state
    private Room room;
    private MaintenanceRequest[] requests;
    private int count;

    // construct object with initial state
    public Student(String userId, String username, String firstName, String lastName,
            String email, String password, Room room) {
        super(userId, username, firstName, lastName, email, password, UserRole.STUDENT);
        this.room = room;
        this.requests = new MaintenanceRequest[100];
        this.count = 0;
    }

    /**
     * Legacy constructor for the Phase II console application.
     */
    // construct object with initial state
    public Student(String userId, String fullName, String email, Room room) {
        this(userId, fullName.toLowerCase().replace(" ", ""), fullName, "",
                email, "", room);
    }

    @Override
    // process business logic
    public void login() {
        System.out.println("Student " + getFullName() + " (ID: " + getUserId() + ") logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Student " + getFullName() + " (ID: " + getUserId() + ") logged out.");
    }

    public void submitRequest(MaintenanceRequest request) {
        if (count < requests.length) {
            requests[count] = request;
            count++;
            System.out.println("Request " + request.getRequestId()
                    + " submitted by " + getFullName() + ".");
        } else {
            System.out.println("Cannot submit request. Request list is full.");
        }
    }

    public void viewRequests() {
        if (count == 0) {
            System.out.println("No requests submitted by " + getFullName() + ".");
            return;
        }

        System.out.println("Requests submitted by " + getFullName() + ":");
        for (int i = 0; i < count; i++) {
            requests[i].displayDetails();
            System.out.println();
        }
    }

    public String getRoomNumber() {
        return room.getRoomNumber();
    }

    public String getPlaceName() {
        return room.getPlaceName();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
