/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryborrowingandreservationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BookDatabaseManager {

    // DESIGN PATTERN: Uses the Singleton connection instead of creating its own
    private Connection myConn;

    public BookDatabaseManager() {
        this.myConn = DatabaseConnection.getInstance().getConnection();
    }
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM Book");
            
            while (myRs.next()) {
                // Assuming your Book class has a constructor: Book(id, title, genre, status)
                Book b = new Book(
                    myRs.getString("bookID"),
                    myRs.getString("title"),
                    myRs.getString("genre"),
                    myRs.getString("status")
                );
                bookList.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // ==========================================
    // C - CREATE (Insert Book)
    // ==========================================
    public void insertBook() {
        Scanner scan = new Scanner(System.in);
        String id = generateBookID();
        System.out.println(">> Assigning New Book ID: " + id);
        System.out.print("Enter Book Title: ");
        String title = scan.nextLine().trim(); // STRING METHOD: trim() removes accidental whitespace
        System.out.print("Enter Book Genre: ");
        String genre = scan.nextLine().trim(); // STRING METHOD: trim()
        String status = "Available";

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
        String id = scan.nextLine().trim();
        System.out.print("Enter New Status (Available / Borrowed / Reserved): ");
        String newStatus = scan.nextLine().trim();

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
        String id = scan.nextLine().trim();

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

    // ==========================================
    // INITIALIZATION (Hard-coding books)
    // ==========================================
    public void insertBookIfNotExists(Book book) {
        try {
            PreparedStatement checkStmt = myConn.prepareStatement("SELECT * FROM Book WHERE bookID = ?");
            checkStmt.setString(1, book.getBookID());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                PreparedStatement insertStmt = myConn.prepareStatement("INSERT INTO Book VALUES (?, ?, ?, ?)");
                insertStmt.setString(1, book.getBookID());
                insertStmt.setString(2, book.getTitle());
                insertStmt.setString(3, book.getGenre());
                insertStmt.setString(4, book.getStatus());
                insertStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // RESERVATIONS (Student features)
    // ==========================================
    public void reserveBook(String bookID, String userID, String userName) {
        try {
            PreparedStatement checkStmt = myConn.prepareStatement("SELECT status FROM Book WHERE bookID = ?");
            checkStmt.setString(1, bookID.trim()); // STRING METHOD: trim()
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getString("status").equals("Available")) {
                PreparedStatement updateStmt = myConn.prepareStatement("UPDATE Book SET status = 'Reserved' WHERE bookID = ?");
                updateStmt.setString(1, bookID);
                updateStmt.executeUpdate();

                String resID = "RES-" + System.currentTimeMillis();
                PreparedStatement resStmt = myConn.prepareStatement("INSERT INTO Reservations VALUES (?, ?, ?, ?)");
                resStmt.setString(1, resID);
                resStmt.setString(2, bookID);
                resStmt.setString(3, userID);
                resStmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
                resStmt.executeUpdate();

                System.out.println(">> Success! Book reserved for " + userName.toUpperCase() + "."); // STRING METHOD: toUpperCase()
            } else {
                System.out.println(">> Book is not available for reservation.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(String bookID, String userID) {
        try {
            PreparedStatement delStmt = myConn.prepareStatement("DELETE FROM Reservations WHERE bookID = ? AND userID = ?");
            delStmt.setString(1, bookID);
            delStmt.setString(2, userID);
            int rows = delStmt.executeUpdate();

            if (rows > 0) {
                PreparedStatement updateStmt = myConn.prepareStatement("UPDATE Book SET status = 'Available' WHERE bookID = ?");
                updateStmt.setString(1, bookID);
                updateStmt.executeUpdate();
                System.out.println(">> Reservation cancelled successfully.");
            } else {
                System.out.println(">> No active reservation found for this book.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean displayStudentReservations(String userID) {
        boolean hasReservations = false;
        String sql = "SELECT r.bookID, b.title, r.holdDate FROM Reservations r " +
                     "JOIN Book b ON r.bookID = b.bookID WHERE r.userID = ?";
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Your Active Reservations ---");
            while (rs.next()) {
                hasReservations = true;
                System.out.println("Book ID: " + rs.getString("bookID") +
                                   " | Title: " + rs.getString("title") +
                                   " | Reserved On: " + rs.getDate("holdDate"));
            }
            if (!hasReservations) {
                System.out.println("You do not have any active reservations.");
            }
            System.out.println("--------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasReservations;
    }

    // ==========================================
    // TRANSACTIONS/BORROW (Librarian features)
    // ==========================================
    public void processBorrow(String bookID, String userID) {
        try {
            PreparedStatement updateStmt = myConn.prepareStatement("UPDATE Book SET status = 'Borrowed' WHERE bookID = ?");
            updateStmt.setString(1, bookID);
            updateStmt.executeUpdate();

            PreparedStatement delRes = myConn.prepareStatement("DELETE FROM Reservations WHERE bookID = ?");
            delRes.setString(1, bookID);
            delRes.executeUpdate();

            String transID = "TXN-" + System.currentTimeMillis();
            java.time.LocalDate today = java.time.LocalDate.now();
            java.time.LocalDate due = today.plusMonths(1);

            PreparedStatement transStmt = myConn.prepareStatement("INSERT INTO Transactions VALUES (?, ?, ?, ?, ?)");
            transStmt.setString(1, transID);
            transStmt.setString(2, bookID);
            transStmt.setString(3, userID);
            transStmt.setDate(4, java.sql.Date.valueOf(today));
            transStmt.setDate(5, java.sql.Date.valueOf(due));
            transStmt.executeUpdate();

            System.out.println(">> Borrow processed! Due date: " + due);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processReturn(String bookID) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDateStr = scan.nextLine().trim(); // STRING METHOD: trim()

        try {
            PreparedStatement getTxn = myConn.prepareStatement("SELECT * FROM Transactions WHERE bookID = ?");
            getTxn.setString(1, bookID);
            ResultSet rs = getTxn.executeQuery();

            if (rs.next()) {
                java.time.LocalDate dueDate = rs.getDate("dueDate").toLocalDate();
                java.time.LocalDate returnDate = java.time.LocalDate.parse(returnDateStr);

                if (returnDate.isAfter(dueDate)) {
                    long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
                    System.out.println(">> BOOK IS LATE! Penalty: RM " + (daysLate * 1.0));
                } else {
                    System.out.println(">> Book returned on time. No fines.");
                }

                PreparedStatement delTxn = myConn.prepareStatement("DELETE FROM Transactions WHERE bookID = ?");
                delTxn.setString(1, bookID);
                delTxn.executeUpdate();

                PreparedStatement updateStmt = myConn.prepareStatement("UPDATE Book SET status = 'Available' WHERE bookID = ?");
                updateStmt.setString(1, bookID);
                updateStmt.executeUpdate();

                System.out.println(">> Return processed successfully.");
            } else {
                System.out.println(">> Error: No active borrow transaction found for this book.");
            }
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println(">> Error: Invalid date format. You must use YYYY-MM-DD.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateBookID() {
        String sql = "SELECT COUNT(*) FROM Book";
        try {
            Statement stmt = myConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int nextNum = rs.getInt(1) + 1;
                return "B-" + String.format("%03d", nextNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "B-999";
    }
    
    // Get search result for JFRAME 
    public ArrayList<Book> getSearchedBooks(String keyword) {
        ArrayList<Book> bookList = new ArrayList<>();
        // Make search case-insensitive for title and genre, or exact match for ID
        String sql = "SELECT * FROM Book WHERE LOWER(title) LIKE ? OR LOWER(genre) LIKE ? OR bookID = ?";
        
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            String searchTerm = "%" + keyword.toLowerCase() + "%";
            
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, keyword); // Exact match for ID

            ResultSet myRs = pstmt.executeQuery();

            while (myRs.next()) {
                Book b = new Book(
                    myRs.getString("bookID"),
                    myRs.getString("title"),
                    myRs.getString("genre"),
                    myRs.getString("status")
                );
                bookList.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // ==========================================
    // S - SEARCH (Search Catalog)
    // ==========================================
    public void searchBook() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter search keyword (Title, Genre, or ID): ");
        String keyword = scan.nextLine().trim(); // STRING METHOD: trim()

        // STRING METHOD: toLowerCase() makes search case-insensitive
        String sql = "SELECT * FROM Book WHERE LOWER(title) LIKE ? OR LOWER(genre) LIKE ? OR bookID = ?";
        try {
            PreparedStatement pstmt = myConn.prepareStatement(sql);
            String searchTerm = "%" + keyword.toLowerCase() + "%"; // STRING METHOD: toLowerCase()
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, keyword);

            ResultSet myRs = pstmt.executeQuery();

            System.out.println("\n--- Search Results ---");
            boolean found = false;
            while (myRs.next()) {
                found = true;
                System.out.println("ID: " + myRs.getString("bookID") +
                        " | Title: " + myRs.getString("title") +
                        " | Genre: " + myRs.getString("genre") +
                        " | Status: " + myRs.getString("status"));
            }
            if (!found) {
                System.out.println(">> No books found matching '" + keyword + "'.");
            }
            System.out.println("----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}