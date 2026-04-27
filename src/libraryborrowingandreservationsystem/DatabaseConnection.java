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
import java.sql.SQLException;

public class DatabaseConnection {

    // SINGLETON: static instance - only one ever exists
    private static DatabaseConnection instance;
    private Connection myConn;

    // SINGLETON: private constructor prevents external instantiation
    private DatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/library_db";
            String user = "root";
            String pass = "";
            myConn = DriverManager.getConnection(url, user, pass);
            System.out.println(">> [Singleton] Database Connection Established.");
        } catch (SQLException e) {
            System.out.println(">> [Singleton] Database Connection Failed!");
        }
    }

    // SINGLETON: static getInstance() - returns the one and only instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Returns the raw JDBC connection for use by manager classes
    public Connection getConnection() {
        return myConn;
    }

    // executeUpdate() as per UML diagram
    public int executeUpdate(String sql, String... params) {
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }
}
