/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

import java.util.Scanner;

/**
 * Console entry point for the Student Hostel Maintenance Request & Tracking System.
 *
 * @author vian
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MaintenanceFactory factory = new MaintenanceFactory();
        MaintenanceManager manager = new MaintenanceManager();

        Room room1 = new Room("A-10-05", "Block A");
        Room room2 = new Room("B-10-05", "Block B");

        Student student1 = new Student("CB25174", "John Vianney Albert", "john123@gmail.com", room1);
        Student student2 = new Student("CB25153", "Chai Chuan Yi", "chai456@gmail.com", room2);

        Staff staff1 = new Staff("ST001", "Ku Farouq", "farouq001@gmail.com", "Electrician");
        Staff staff2 = new Staff("ST002", "Wazif Faiz", "faiz002@gmail.com", "Plumber");

        int requestCounter = 1;

        System.out.println("=====================================================");
        System.out.println("  STUDENT HOSTEL MAINTENANCE REQUEST & TRACKING SYSTEM");
        System.out.println("=====================================================");

        boolean running = true;
        while (running) {
            System.out.println("\n=================== MAIN MENU ===================");
            System.out.println("  1. Submit a Maintenance Request     (User: Student)");
            System.out.println("  2. View All Requests                (User: Manager)");
            System.out.println("  3. Search Request by ID             (User: Manager, Student, Staff)");
            System.out.println("  4. Update Request Status            (User: Manager, Staff)");
            System.out.println("  5. Assign Staff to Request          (User: Manager)");
            System.out.println("  6. View Room Details                (User: Manager, Student, Staff)");
            System.out.println("  7. View Request History             (User: Student, Staff)");
            System.out.println("  0. Exit");
            System.out.println("=================================================");
            System.out.print("  Enter your choice: ");

            String choiceInput = sc.nextLine().trim();

            switch (choiceInput) {
                case "1":
                    System.out.println("\n--- SUBMIT A MAINTENANCE REQUEST ---");

                    System.out.println("Select Student:");
                    System.out.println("  1. " + student1.getName() + " (Room: " + student1.getRoomNumber() + ")");
                    System.out.println("  2. " + student2.getName() + " (Room: " + student2.getRoomNumber() + ")");
                    System.out.print("  Enter choice (1 or 2): ");
                    String studentChoice = sc.nextLine().trim();

                    Student selectedStudent;
                    if (studentChoice.equals("1")) {
                        selectedStudent = student1;
                    } else if (studentChoice.equals("2")) {
                        selectedStudent = student2;
                    } else {
                        System.out.println("Invalid choice. Returning to main menu.");
                        break;
                    }

                    System.out.println("\nSelect Request Type:");
                    System.out.println("  1. Electrical");
                    System.out.println("  2. Plumbing");
                    System.out.println("  3. Furniture");
                    System.out.print("  Enter choice (1, 2, or 3): ");
                    String typeChoice = sc.nextLine().trim();

                    String requestType;
                    switch (typeChoice) {
                        case "1":
                            requestType = "electrical";
                            break;
                        case "2":
                            requestType = "plumbing";
                            break;
                        case "3":
                            requestType = "furniture";
                            break;
                        default:
                            System.out.println("Invalid choice. Returning to main menu.");
                            requestType = null;
                            break;
                    }

                    if (requestType == null) {
                        break;
                    }

                    System.out.println("\nEnter issue description: ");
                    String description = sc.nextLine().trim();
                    if (description.isEmpty()) {
                        System.out.println("Description cannot be empty. Returning to main menu.");
                        break;
                    }

                    System.out.println("Select Priority:");
                    System.out.println("  1. High");
                    System.out.println("  2. Medium");
                    System.out.println("  3. Low");
                    System.out.print("  Enter choice (1, 2, or 3): ");
                    String priorityChoice = sc.nextLine().trim();

                    String priority;
                    switch (priorityChoice) {
                        case "1":
                            priority = "High";
                            break;
                        case "2":
                            priority = "Medium";
                            break;
                        case "3":
                            priority = "Low";
                            break;
                        default:
                            System.out.println("Invalid choice. Returning to main menu.");
                            priority = null;
                            break;
                    }

                    if (priority == null) {
                        break;
                    }

                    String requestId = "REQ" + String.format("%03d", requestCounter);
                    requestCounter++;

                    MaintenanceRequest newRequest = factory.createRequest(
                            requestType, requestId, description, priority, selectedStudent.getRoom());

                    if (newRequest != null) {
                        manager.addRequest(newRequest);
                        selectedStudent.submitRequest(newRequest);
                        newRequest.processRequest();
                        System.out.println("\nRequest submitted successfully by " + selectedStudent.getName() + ".");
                    }
                    break;

                case "2":
                    System.out.println("\n--- ALL MAINTENANCE REQUESTS ---");
                    manager.displayAllRequests();
                    break;

                case "3":
                    System.out.println("\n--- SEARCH REQUEST ---");
                    System.out.print("Enter Request ID to search (e.g. REQ001): ");
                    String searchId = sc.nextLine().trim().toUpperCase();
                    manager.searchRequest(searchId);
                    break;

                case "4":
                    System.out.println("\n--- UPDATE REQUEST STATUS ---");
                    System.out.print("Enter Request ID to update (e.g. REQ001): ");
                    String updateId = sc.nextLine().trim().toUpperCase();

                    System.out.println("Select New Status:");
                    System.out.println("  1. SUBMITTED");
                    System.out.println("  2. IN_PROGRESS");
                    System.out.println("  3. COMPLETED");
                    System.out.println("  4. CANCELLED");
                    System.out.print("  Enter choice (1, 2, 3, or 4): ");
                    String statusChoice = sc.nextLine().trim();

                    Status newStatus;
                    switch (statusChoice) {
                        case "1":
                            newStatus = Status.SUBMITTED;
                            break;
                        case "2":
                            newStatus = Status.IN_PROGRESS;
                            break;
                        case "3":
                            newStatus = Status.COMPLETED;
                            break;
                        case "4":
                            newStatus = Status.CANCELLED;
                            break;
                        default:
                            System.out.println("Invalid status choice. Returning to main menu.");
                            newStatus = null;
                            break;
                    }

                    if (newStatus != null) {
                        manager.updateRequestStatus(updateId, newStatus);
                    }
                    break;

                case "5":
                    System.out.println("\n--- ASSIGN STAFF TO REQUEST ---");
                    System.out.print("Enter Request ID to assign staff to (e.g. REQ001): ");
                    String assignId = sc.nextLine().trim().toUpperCase();

                    System.out.println("Select Staff Member:");
                    System.out.println("  1. " + staff1.getName() + " (" + staff1.getStaffRole() + ")");
                    System.out.println("  2. " + staff2.getName() + " (" + staff2.getStaffRole() + ")");
                    System.out.print("  Enter choice (1 or 2): ");
                    String staffChoice = sc.nextLine().trim();

                    Staff selectedStaff;
                    if (staffChoice.equals("1")) {
                        selectedStaff = staff1;
                    } else if (staffChoice.equals("2")) {
                        selectedStaff = staff2;
                    } else {
                        System.out.println("Invalid choice. Returning to main menu.");
                        break;
                    }

                    manager.assignStaff(assignId, selectedStaff);
                    selectedStaff.viewAssignedRequests();
                    break;

                case "6":
                    System.out.println("\n--- ROOM DETAILS ---");
                    System.out.println(room1.getRoomDetails());
                    room1.viewLinkedRequests();
                    System.out.println();
                    System.out.println(room2.getRoomDetails());
                    room2.viewLinkedRequests();
                    break;

                case "7":
                    System.out.println("\n--- VIEW REQUEST HISTORY ---");
                    System.out.print("Enter Request ID for history (e.g. REQ001): ");
                    String historyId = sc.nextLine().trim().toUpperCase();

                    MaintenanceRequest foundRequest = manager.findRequestById(historyId);
                    if (foundRequest == null) {
                        System.out.println("Request [" + historyId + "] not found.");
                    } else {
                        foundRequest.viewHistory();
                    }
                    break;

                case "0":
                    System.out.println("\nThank you for using the Hostel Maintenance System.");
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from the menu.");
                    break;
            }
        }

        sc.close();
    }
}
