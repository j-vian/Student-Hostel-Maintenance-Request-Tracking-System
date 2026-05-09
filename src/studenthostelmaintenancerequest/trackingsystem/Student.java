/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author farouq
 */
public class Student extends User {

    private String roomNumber;
    private MaintenanceRequest[] requests;
    private int count = 0;

    public Student(String userId, String name, String email, String roomNumber) {
        super(userId, name, email);
        this.roomNumber = roomNumber;
        this.requests = new MaintenanceRequest[100];
    }

    @Override
    public void login() {
        System.out.println("Student " + getName() + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Student " + getName() + " logged out.");
    }

    public void submitRequest(MaintenanceRequest request) {
        if (count < getRequests().length) {
            getRequests()[count] = request;
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
            getRequests()[i].displayDetails();
            System.out.println();
        }
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public MaintenanceRequest[] getRequests() {
        return requests;
    }

    public int getCount() {
        return count;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
