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

    // application entry point
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MaintenanceFactory factory = new MaintenanceFactory();
        MaintenanceManager manager = new MaintenanceManager();

        Room room1 = new Room("A-10-05", "DHUAM");
        Room room2 = new Room("B-10-05", "KK5");

        Student student1 = new Student("CB25174", "John Vianney Albert", "john123@gmail.com", room1);
        Student student2 = new Student("CB25153", "Chai Chuan Yi", "chai456@gmail.com", room2);

        Staff staff1 = new Staff("ST001", "Ku Farouq", "farouq001@gmail.com", "Electrician");
        Staff staff2 = new Staff("ST002", "Wazif Faiz", "faiz002@gmail.com", "Plumber");

        User studentUser1 = student1;
        User studentUser2 = student2;
        User staffUser1 = staff1;
        User staffUser2 = staff2;
        User[] systemUsers = {studentUser1, studentUser2, staffUser1, staffUser2};

        int requestCounter = 1;

        System.out.println("=====================================================");
        System.out.println("  STUDENT HOSTEL MAINTENANCE REQUEST & TRACKING SYSTEM");
        System.out.println("=====================================================");

        boolean running = true;
        while (running) {
            printSessionStatus();

            System.out.println("\n=================== MAIN MENU ===================");
            System.out.println("  --- Maintenance (In-Memory Demo) ---");
            System.out.println("  1. Submit a Maintenance Request     (User: Student)");
            System.out.println("  2. View All Requests                (User: Manager)");
            System.out.println("  3. Search Request by ID             (User: Manager, Student, Staff)");
            System.out.println("  4. Update Request Status            (User: Manager, Staff)");
            System.out.println("  5. Assign Staff to Request          (User: Manager)");
            System.out.println("  6. View Room Details                (User: Manager, Student, Staff)");
            System.out.println("  7. View Request History             (User: Student, Staff)");
            System.out.println("  --- Account & Database ---");
            System.out.println("  8. Login");
            System.out.println("  9. Sign Up");
            System.out.println(" 10. Delete Request                   (User: Manager, Database)");
            System.out.println(" 11. Logout");
            System.out.println("  0. Exit");
            System.out.println("=================================================");
            System.out.print("  Enter your choice: ");

            String choiceInput = sc.nextLine().trim();

            switch (choiceInput) {
                case "1":
                    System.out.println("\n--- SUBMIT A MAINTENANCE REQUEST ---");

                    System.out.println("Select Student:");
                    System.out.println("  1. " + systemUsers[0].getName() + " (Room: " + student1.getRoomNumber() + ")");
                    System.out.println("  2. " + systemUsers[1].getName() + " (Room: " + student2.getRoomNumber() + ")");
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
                    System.out.println("  1. " + systemUsers[2].getName() + " (" + staff1.getStaffRole() + ")");
                    System.out.println("  2. " + systemUsers[3].getName() + " (" + staff2.getStaffRole() + ")");
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

                case "8":
                    runConsoleLogin(sc);
                    break;

                case "9":
                    runConsoleSignUp(sc);
                    break;

                case "10":
                    runConsoleDeleteRequest(sc);
                    break;

                case "11":
                    runConsoleLogout();
                    break;

                case "0":
                    if (SessionManager.isLoggedIn()) {
                        AuthService.logoutUser();
                    }
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

    private static void printSessionStatus() {
        if (SessionManager.isLoggedIn()) {
            User currentUser = SessionManager.getCurrentUser();
            System.out.println("\nLogged in as: " + currentUser.getFullName()
                    + " (" + currentUser.getRole() + ")");
        } else {
            System.out.println("\nLogged in as: Not logged in");
        }
    }

    private static void runConsoleLogin(Scanner sc) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine();

        try {
            User user = AuthService.loginUser(email, password);
            System.out.println("Login successful. Welcome, " + user.getFullName()
                    + " (" + user.getRole() + ").");
        } catch (DatabaseException ex) {
            System.out.println("Login failed: " + ex.getMessage());
        }
    }

    private static void runConsoleSignUp(Scanner sc) {
        System.out.println("\n--- SIGN UP ---");
        System.out.println("Select Role:");
        System.out.println("  1. Student");
        System.out.println("  2. Staff");
        System.out.print("  Enter choice (1 or 2): ");
        String roleChoice = sc.nextLine().trim();

        UserRole role;
        if (roleChoice.equals("1")) {
            role = UserRole.STUDENT;
        } else if (roleChoice.equals("2")) {
            role = UserRole.STAFF;
        } else {
            System.out.println("Invalid role choice.");
            return;
        }

        System.out.print("User ID: ");
        String userId = sc.nextLine().trim();
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("First Name: ");
        String firstName = sc.nextLine().trim();
        System.out.print("Last Name: ");
        String lastName = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPassword = sc.nextLine();

        String roomNumber = "";
        String staffRole = null;
        String otherExpertise = null;

        if (role == UserRole.STUDENT) {
            System.out.print("Room Number (e.g. A-10-05): ");
            roomNumber = sc.nextLine().trim();
        } else {
            System.out.println("Select Staff Expertise:");
            System.out.println("  1. Electrician");
            System.out.println("  2. Plumber");
            System.out.println("  3. Furniture Tech");
            System.out.println("  4. Other");
            System.out.print("  Enter choice (1 to 4): ");
            String expertiseChoice = sc.nextLine().trim();
            switch (expertiseChoice) {
                case "1":
                    staffRole = "Electrician";
                    break;
                case "2":
                    staffRole = "Plumber";
                    break;
                case "3":
                    staffRole = "Furniture Tech";
                    break;
                case "4":
                    System.out.print("Enter expertise: ");
                    otherExpertise = sc.nextLine().trim();
                    staffRole = otherExpertise.isEmpty() ? null : otherExpertise;
                    break;
                default:
                    System.out.println("Invalid expertise choice.");
                    return;
            }
        }

        SignUpData data = new SignUpData(
                userId, username, firstName, lastName, email, password,
                role, roomNumber, staffRole, otherExpertise);

        try {
            AuthService.registerUser(data, confirmPassword);
            System.out.println("Account created successfully. You can now log in with option 8.");
        } catch (DatabaseException ex) {
            System.out.println("Sign up failed: " + ex.getMessage());
        }
    }

    private static void runConsoleDeleteRequest(Scanner sc) {
        System.out.println("\n--- DELETE REQUEST (DATABASE) ---");

        if (!SessionManager.isLoggedIn()) {
            System.out.println("Please log in as a manager first (option 8).");
            return;
        }
        if (SessionManager.getCurrentUser().getRole() != UserRole.MANAGER) {
            System.out.println("Only managers can delete requests from the database.");
            return;
        }

        Object[][] rows;
        try {
            rows = ManagerService.getHistoryRows();
        } catch (DatabaseException ex) {
            System.out.println("Unable to load requests: " + ex.getMessage());
            return;
        }

        String requestId = ManagerService.promptConsoleDeleteRequest(
                sc, rows, ManagerService.HISTORY_STATUS_COLUMN);
        if (requestId == null) {
            return;
        }

        System.out.print("Delete " + requestId + " permanently? (yes/no): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes") && !confirm.equalsIgnoreCase("y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        try {
            ManagerService.deleteRequest(requestId);
            System.out.println("Request " + requestId + " was deleted from the database.");
        } catch (DatabaseException ex) {
            System.out.println("Delete failed: " + ex.getMessage());
        }
    }

    private static void runConsoleLogout() {
        if (!SessionManager.isLoggedIn()) {
            System.out.println("\nYou are not logged in.");
            return;
        }

        AuthService.logoutUser();
        System.out.println("\nYou have been logged out.");
    }
}
