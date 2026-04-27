/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

public class Book {
    private String bookID;
    private String title;
    private String genre;
    private String status; // (Available, Borrowed, Reserved)

    // Constructor
    public Book(String bookID, String title, String genre, String status) {
        this.bookID = bookID;
        this.title = title;
        this.genre = genre;
        this.status = status;
    }

    // Getters
    public String getBookID() { return bookID; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getStatus() { return status; }

    // Setters (ADDED: was missing setBookID, setTitle, setGenre)
    public void setBookID(String bookID) { this.bookID = bookID; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setStatus(String newStatus) { this.status = newStatus; }

    // UML Method: displayDetails()
    public void displayDetails() {
        System.out.println("Book ID: " + bookID + " | Title: " + title +
                           " | Genre: " + genre + " | Status: " + status);
    }
}