/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 * Represents a student who submits maintenance requests.
 * Composition: each student owns a Room and their submitted request references.
 *
 * @author farouq
 */
public class Student extends User {

    private Room room;
    private MaintenanceRequest[] requests;
    private int count;

    public Student(String userId, String name, String email, Room room) {
        super(userId, name, email);
        this.room = room;
        this.requests = new MaintenanceRequest[100];
        this.count = 0;
    }

    @Override
    public void login() {
        System.out.println("Student " + getName() + " (ID: " + getUserId() + ") logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Student " + getName() + " (ID: " + getUserId() + ") logged out.");
    }

    public void submitRequest(MaintenanceRequest request) {
        if (count < requests.length) {
            requests[count] = request;
            count++;
            System.out.println("Request " + request.getRequestId()
                    + " submitted by " + getName() + ".");
        } else {
            System.out.println("Cannot submit request. Request list is full.");
        }
    }

    public void viewRequests() {
        if (count == 0) {
            System.out.println("No requests submitted by " + getName() + ".");
            return;
        }

        System.out.println("Requests submitted by " + getName() + ":");
        for (int i = 0; i < count; i++) {
            requests[i].displayDetails();
            System.out.println();
        }
    }

    public String getRoomNumber() {
        return room.getRoomNumber();
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
}
