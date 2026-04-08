/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

/**
 *
 * @author Acer
 */
public class Librarian extends User {
    private String staffID;

    // Constructor mapping to the superclass
    public Librarian(String userID, String name, String email, String password, String staffID) {
        super(userID, name, email, password); // Calls the User constructor
        this.staffID = staffID;
    }

    public String getStaffID() {
        return staffID; 
    }
    
    
    public void setStaffID(String staffID) {
        this.staffID = staffID; 
    }

    // UML Methods
    public void registerBook() {
        
    }

    public void processBorrow() {
        
    }

    public void processReturn() {
       
    }
}