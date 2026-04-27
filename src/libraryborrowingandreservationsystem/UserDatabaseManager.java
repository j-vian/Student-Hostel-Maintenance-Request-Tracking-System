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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDatabaseManager {

    // DESIGN PATTERN: Uses the Singleton connection instead of creating its own
    private Connection myConn;

    public UserDatabaseManager() {
        this.myConn = DatabaseConnection.getInstance().getConnection();
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

                // POLYMORPHISM: Returns different subclass objects based on userType
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

    public String generateUserID(int type) {
        String prefix = (type == 1) ? "STAFF-" : "STU-";
        String userType = (type == 1) ? "Librarian" : "Student";
        String sql = "SELECT COUNT(*) FROM Users WHERE userType = ?";

        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, userType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int nextNum = rs.getInt(1) + 1;
                return prefix + String.format("%03d", nextNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + System.currentTimeMillis(); // Safe fallback
    }
}