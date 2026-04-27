/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

import java.time.LocalDate;

public class Reservation {
    private String reservationID;
    private Book reservedBook; // AGGREGATION (Has-a): Reservation has a Book, but Book exists independently
    private String userID;
    private LocalDate holdDate;

    public Reservation(String reservationID, Book reservedBook, String userID, LocalDate holdDate) {
        this.reservationID = reservationID;
        this.reservedBook = reservedBook;
        this.userID = userID;
        this.holdDate = holdDate;
    }

    // Getters
    public String getReservationID() { return reservationID; }
    public Book getReservedBook() { return reservedBook; }
    public String getUserID() { return userID; }
    public LocalDate getHoldDate() { return holdDate; }

    // Setters (ADDED: was completely missing all setters)
    public void setReservationID(String reservationID) { this.reservationID = reservationID; }
    public void setReservedBook(Book reservedBook) { this.reservedBook = reservedBook; }
    public void setUserID(String userID) { this.userID = userID; }
    public void setHoldDate(LocalDate holdDate) { this.holdDate = holdDate; }

    // UML Method: A reservation expires after 3 days
    public boolean checkExpiry() {
        LocalDate expiryDate = holdDate.plusDays(3);
        return LocalDate.now().isAfter(expiryDate);
    }
}