/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

public class Students extends User {

    // ADDED: UML fields that were missing from original code
    private int borrowLimit;
    private double totalFines;

    // Constructor mapping to the superclass
    public Students(String userID, String name, String email, String password) {
        super(userID, name, email, password);
        this.borrowLimit = 3;    // Default borrow limit for students
        this.totalFines = 0.0;   // Start with no fines
    }

    // Getters & Setters (ADDED: was completely missing)
    public int getBorrowLimit() { return borrowLimit; }
    public void setBorrowLimit(int borrowLimit) { this.borrowLimit = borrowLimit; }

    public double getTotalFines() { return totalFines; }
    public void setTotalFines(double totalFines) { this.totalFines = totalFines; }

    // POLYMORPHISM: Overrides the parent logout() to show role-specific message
    @Override
    public void logout() {
        System.out.println("Student " + getName() + " has logged out. Outstanding fines: RM " + totalFines);
    }

    // UML Method: searchCatalog()
    public void searchCatalog(BookDatabaseManager dbManager) {
        dbManager.searchBook();
    }

    // ADDED: UML Method requestReserve() - was missing from original
    public void requestReserve(BookDatabaseManager dbManager, String bookID) {
        System.out.println(">> [Student] " + getName() + " is requesting reservation for Book: " + bookID);
        dbManager.reserveBook(bookID, getUserID(), getName());
    }
}