/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

/**
 *
 * @author Acer
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BookDatabaseManager {
    
    private Connection myConn;

    // Connect to the database when the manager is created
    public BookDatabaseManager() {
        try {
            // Update URL to match your XAMPP setup (localhost:3306) and database name
            String url = "jdbc:mysql://localhost:3306/library_db";
            String user = "root"; // Default XAMPP username
            String pass = "";     // Default XAMPP password is blank
            
            myConn = DriverManager.getConnection(url, user, pass);
            System.out.println(">> Database Connection Successful!");
            
        } catch (Exception e) {
            System.out.println(">> Database Connection Failed!");
            e.printStackTrace();
        }
    }

    // ==========================================
    // C - CREATE (Insert Book)
    // ==========================================
    public void insertBook() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter new Book ID: ");
        String id = scan.nextLine();
        scan.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scan.nextLine();
        System.out.print("Enter Book Genre: ");
        String genre = scan.nextLine();
        String status = "Available"; // Default status when a new book is registered

        String sql = "INSERT INTO Book VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, genre);
            pstmt.setString(4, status);
            
            int i = pstmt.executeUpdate();
            if (i > 0) {
                System.out.println(">> Book inserted successfully!");
            } else {
                System.out.println(">> Insert failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // R - READ (Query All Books)
    // ==========================================
    public void queryAllBooks() {
        try {
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM Book");
            
            System.out.println("\n--- Library Catalog ---");
            while (myRs.next()) {
                System.out.println("ID: " + myRs.getString("bookID") +
                        " | Title: " + myRs.getString("title") +
                        " | Genre: " + myRs.getString("genre") +
                        " | Status: " + myRs.getString("status"));
            }
            System.out.println("-----------------------");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // U - UPDATE (Update Book Status)
    // ==========================================
    public void updateBookStatus() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Book ID to update: ");
        String id = scan.nextLine();
        System.out.print("Enter New Status (Available / Borrowed / Reserved): ");
        String newStatus = scan.nextLine();

        String sql = "UPDATE Book SET status=? WHERE bookID=?";
        
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setString(2, id);
            
            int i = pstmt.executeUpdate();
            if (i > 0) {
                System.out.println(">> Book status updated successfully!");
            } else {
                System.out.println(">> Update failed. Book ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // D - DELETE (Remove Book)
    // ==========================================
    public void deleteBook() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Book ID to delete: ");
        String id = scan.nextLine();

        String sql = "DELETE FROM Book WHERE bookID=?";
        
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, id);
            
            int i = pstmt.executeUpdate();
            if (i > 0) {
                System.out.println(">> Book deleted successfully!");
            } else {
                System.out.println(">> Delete failed. Book ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}