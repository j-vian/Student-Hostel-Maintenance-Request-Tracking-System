/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

public class Librarian extends User {
    private String staffID;

    // Constructor mapping to the superclass
    public Librarian(String userID, String name, String email, String password, String staffID) {
        super(userID, name, email, password); // Calls the User constructor
        this.staffID = staffID;
    }

    // Getter & Setter
    public String getStaffID() { return staffID; }
    public void setStaffID(String staffID) { this.staffID = staffID; }

    // POLYMORPHISM: Overrides the parent logout() to show role-specific message
    @Override
    public void logout() {
        System.out.println("Librarian " + getName() + " (Staff ID: " + staffID + ") has logged out.");
    }

    // UML Methods - delegate to BookDatabaseManager
    // INHERITANCE: These methods use the staffID from this subclass
    public void registerBook(BookDatabaseManager dbManager) {
        System.out.println(">> [Librarian] Registering new book...");
        dbManager.insertBook();
    }

    public void processBorrow(BookDatabaseManager dbManager, String bookID, String studentID) {
        System.out.println(">> [Librarian] Processing borrow for Book: " + bookID);
        dbManager.processBorrow(bookID, studentID);
    }

    public void processReturn(BookDatabaseManager dbManager, String bookID) {
        System.out.println(">> [Librarian] Processing return for Book: " + bookID);
        dbManager.processReturn(bookID);
    }
}