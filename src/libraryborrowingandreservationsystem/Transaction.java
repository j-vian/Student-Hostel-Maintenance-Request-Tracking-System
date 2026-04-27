/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private String transactionID;
    private Book borrowedBook; // AGGREGATION (Has-a): Transaction has a Book, but Book exists independently
    private String userID;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public Transaction(String transactionID, Book borrowedBook, String userID, LocalDate borrowDate) {
        this.transactionID = transactionID;
        this.borrowedBook = borrowedBook;
        this.userID = userID;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusMonths(1); // Due 1 month from borrow date
    }

    // Getters
    public String getTransactionID() { return transactionID; }
    public Book getBorrowedBook() { return borrowedBook; }
    public String getUserID() { return userID; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }

    // Setters (ADDED: was completely missing all setters)
    public void setTransactionID(String transactionID) { this.transactionID = transactionID; }
    public void setBorrowedBook(Book borrowedBook) { this.borrowedBook = borrowedBook; }
    public void setUserID(String userID) { this.userID = userID; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    // UML Method: Calculate fine (RM 1 per late day)
    public double calculateFine() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, today);
            return daysLate * 1.0; // RM 1 per day
        }
        return 0.0;
    }

    // UML Method: completeTransaction()
    public void completeTransaction() {
        System.out.println(">> Transaction " + transactionID + " completed. Book: " + borrowedBook.getTitle());
    }
}