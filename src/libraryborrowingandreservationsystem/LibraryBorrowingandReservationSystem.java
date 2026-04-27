package libraryborrowingandreservationsystem;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class LibraryBorrowingandReservationSystem {

    // COMPOSITION: The System "owns" the bookList.
    // When this System object is destroyed, the bookList is destroyed with it.
    // This is true Composition (whole-part relationship).
    private static ArrayList<Book> bookList = new ArrayList<>();

    public static void bookInput() {
        // COMPOSITION: Books are created and owned entirely by this system
        bookList.add(new Book("B001", "Introduction to Java", "Education", "Available"));
        bookList.add(new Book("B002", "Advanced Data Structures", "Education", "Available"));
        bookList.add(new Book("B003", "Software Engineering Guide", "Technology", "Available"));

        // DESIGN PATTERN (Singleton): Uses the shared connection via BookDatabaseManager
        BookDatabaseManager dbManager = new BookDatabaseManager();
        for (int i = 0; i < bookList.size(); i++) {
            dbManager.insertBookIfNotExists(bookList.get(i));
        }
        System.out.println(">> System Initialized: Array of Object(Hardcoded) Books loaded.\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        
       

        // COMPOSITION: initializeSystem() must be called first — the system owns the book list
        bookInput();

        // DESIGN PATTERN (Singleton): Both managers share the same DB connection
        UserDatabaseManager userDb = new UserDatabaseManager();
        BookDatabaseManager dbManager = new BookDatabaseManager();

        boolean running = true;

        while (running) {
            System.out.println("\n=== Library System Menu ===");
            System.out.println("1. Register Account");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // --- REGISTRATION PHASE ---
                System.out.println("Registering as: 1. Librarian | 2. Student");
                System.out.print("Choose user type: ");
                int type = scanner.nextInt();
                scanner.nextLine();

                String id = userDb.generateUserID(type);
                System.out.println(">> Automatically assigned User ID: " + id);

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (type == 1) {
                    // INHERITANCE: Librarian extends User
                    Librarian newLib = new Librarian(id, name, email, password, id);
                    if (userDb.registerLibrarian(newLib)) {
                        System.out.println(">> Librarian saved to database successfully!");
                    }
                } else if (type == 2) {
                    // INHERITANCE: Students extends User
                    Students newStudent = new Students(id, name, email, password);
                    if (userDb.registerStudent(newStudent)) {
                        System.out.println(">> Student saved to database successfully!");
                    }
                } else {
                    System.out.println("Invalid user type selected.");
                }

            } else if (choice == 2) {
                // --- SIGN IN PHASE ---
                System.out.print("\nEnter User ID to Login: ");
                String loginId = scanner.nextLine();
                System.out.print("Enter Password: ");
                String loginPass = scanner.nextLine();

                // POLYMORPHISM: authenticateUser returns a User reference
                // that could be a Librarian or Students at runtime
                User currentUser = userDb.authenticateUser(loginId, loginPass);

                if (currentUser != null) {
                    System.out.println("\n>> Login successful! Welcome, " + currentUser.getName());

                    // ==========================================
                    // LIBRARIAN MENU
                    // ==========================================
                    if (currentUser instanceof Librarian) {
                        // POLYMORPHISM: Cast to Librarian to use subclass-specific methods
                        Librarian libUser = (Librarian) currentUser;
                        boolean staffMenu = true;

                        while (staffMenu) {
                            System.out.println("\n--- Librarian Menu ---");
                            System.out.println("1. View Catalog");
                            System.out.println("2. Add New Book");
                            System.out.println("3. Process Borrow");
                            System.out.println("4. Process Return");
                            System.out.println("5. Search Catalog");
                            System.out.println("6. Logout");
                            System.out.print("Option: ");
                            int crudChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (crudChoice == 1) {
                                dbManager.queryAllBooks();
                            } else if (crudChoice == 2) {
                                // INHERITANCE: calls Librarian's registerBook() which delegates to dbManager
                                libUser.registerBook(dbManager);
                            } else if (crudChoice == 3) {
                                System.out.print("Enter Book ID to borrow: ");
                                String bId = scanner.nextLine();
                                System.out.print("Enter Student User ID: ");
                                String sId = scanner.nextLine();
                                // INHERITANCE: calls Librarian's processBorrow() method
                                libUser.processBorrow(dbManager, bId, sId);
                            } else if (crudChoice == 4) {
                                System.out.print("Enter Book ID to return: ");
                                String bId = scanner.nextLine();
                                // INHERITANCE: calls Librarian's processReturn() method
                                libUser.processReturn(dbManager, bId);
                            } else if (crudChoice == 5) {
                                dbManager.searchBook();
                            } else if (crudChoice == 6) {
                                // POLYMORPHISM: calls overridden logout() in Librarian subclass
                                libUser.logout();
                                staffMenu = false;
                            }
                        }

                    // ==========================================
                    // STUDENT MENU
                    // ==========================================
                    } else if (currentUser instanceof Students) {
                        // POLYMORPHISM: Cast to Students to use subclass-specific methods
                        Students stuUser = (Students) currentUser;
                        boolean studentMenu = true;

                        while (studentMenu) {
                            System.out.println("\n--- Student Menu ---");
                            System.out.println("1. View Catalog");
                            System.out.println("2. Reserve Book");
                            System.out.println("3. Cancel Reservation");
                            System.out.println("4. Search Catalog");
                            System.out.println("5. Logout");
                            System.out.print("Option: ");
                            int studentChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (studentChoice == 1) {
                                dbManager.queryAllBooks();
                            } else if (studentChoice == 2) {
                                System.out.print("Enter Book ID to reserve: ");
                                String bId = scanner.nextLine();
                                // INHERITANCE: calls Students' requestReserve() method
                                stuUser.requestReserve(dbManager, bId);
                            } else if (studentChoice == 3) {
                                boolean hasRes = dbManager.displayStudentReservations(currentUser.getUserID());
                                if (hasRes) {
                                    System.out.print("Enter Book ID to cancel reservation (or type '0' to go back): ");
                                    String bId = scanner.nextLine();
                                    if (!bId.equals("0")) {
                                        dbManager.cancelReservation(bId, currentUser.getUserID());
                                    }
                                }
                            } else if (studentChoice == 4) {
                                // INHERITANCE: calls Students' searchCatalog() method
                                stuUser.searchCatalog(dbManager);
                            } else if (studentChoice == 5) {
                                // POLYMORPHISM: calls overridden logout() in Students subclass
                                stuUser.logout();
                                studentMenu = false;
                            }
                        }
                    }
                } else {
                    System.out.println(">> Incorrect ID or Password.");
                }

            } else if (choice == 3) {
                running = false;
                System.out.println("Goodbye!");
            }
        }

        scanner.close();
    }
}