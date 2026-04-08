package libraryborrowingandreservationsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Acer
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDatabaseManager {
    private Connection myConn;

    public UserDatabaseManager() {
        try {
            String url = "jdbc:mysql://localhost:3306/library_db";
            String user = "root";
            String pass = "";
            myConn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println(">> Database Connection Failed for Users!");
            e.printStackTrace();
        }
    }

    // Save a Librarian to the database
    public boolean registerLibrarian(Librarian lib) {
        String sql = "INSERT INTO Users (userID, name, email, password, userType, staffID) VALUES (?, ?, ?, ?, 'Librarian', ?)";
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, lib.getUserID());
            pstmt.setString(2, lib.getName());
            pstmt.setString(3, lib.getEmail());
            pstmt.setString(4, lib.getPassword());
            pstmt.setString(5, lib.getStaffID());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error saving Librarian. ID might already exist.");
            return false;
        }
    }

    // Save a Student to the database
    public boolean registerStudent(Students student) {
        String sql = "INSERT INTO Users (userID, name, email, password, userType, staffID) VALUES (?, ?, ?, ?, 'Student', NULL)";
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, student.getUserID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPassword());
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error saving Student. ID might already exist.");
            return false;
        }
    }

    // Check login credentials and return the correct User object
    public User authenticateUser(String userID, String password) {
        String sql = "SELECT * FROM Users WHERE userID = ? AND password = ?";
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("userType");
                String name = rs.getString("name");
                String email = rs.getString("email");

                // Reconstruct the specific user object based on their database type
                if ("Librarian".equals(type)) {
                    String staffID = rs.getString("staffID");
                    return new Librarian(userID, name, email, password, staffID);
                } else if ("Student".equals(type)) {
                    return new Students(userID, name, email, password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Returns null if ID/Password don't match
    }
}
