package ap.projects.finalproject;

import java.util.List;
import java.util.Scanner;

public class MenuHandler {

    private Scanner scanner;
    private LibrarySystem librarySystem;
    private Student currentUser;
    private BookManager bookManager;

    InputHandler input = new InputHandler();

    public MenuHandler(LibrarySystem librarySystem , BookManager bookManager) {
        this.scanner = new Scanner(System.in);
        this.librarySystem = librarySystem;
        this.currentUser = null;
        this.bookManager = bookManager;

    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== University Library Management System ===");
            System.out.println("1. Student Registration");
            System.out.println("2. Student Login");
            System.out.println("3. Library Manager login");
            System.out.println("4. Employee login");
            System.out.println("5. Guest user login");
            System.out.println("6. Exit");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1 :
                    handleStudentRegistration();
                    break;
                case 2 :
                    handleStudentLogin();
                    break;
                case 3 :
                    displayManagerMenu();
                    break;
                case 4 :
                    Employee employee = librarySystem.employeeLogin();
                    if (employee != null) {
                        System.out.println("Welcome " +employee.getName());
                        displayEmployeeMenu(employee);
                    } else {
                        System.out.println("Invalid username or password!");
                    }
                    break;
                case 5 :
                    guestMenu();
                    break;
                case 6 :
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
            System.out.println("___________________________");
        }
    }

    private void guestMenu() {
        while (true) {
            System.out.println("\n=== Guest Dashboard ===");
            System.out.println("1. View Registered Student Count");
            System.out.println("2. Search book by title");
            System.out.println("3. Show library statistics");
            System.out.println("4. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1 , 4);

            switch (choice) {
                case 1 :
                    displayStudentCount();
                    break;
                case 2 :
                    searchBookForGuest();
                    break;
                case 3 :
                    System.out.println("How many recent loans do you want to see?");
                    int k = getIntInput(1 , 100);
                    librarySystem.showStatistics();
                    break;
                case 4 :
                    System.out.println("Return to main menu");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");

            }
        }
    }

    private void searchBookForGuest() {
        String bookTitle = input.getString("Please enter book title: ");
        List<Book> result = bookManager.searchBooksByTitle(bookTitle);

        if (result.isEmpty()) {
            System.out.println("No book found.");
        } else {
            System.out.println("\n=== Search Results ===");
            System.out.println("Found " + result.size() + " book(s):");
            System.out.println("-----------------------");

            for (Book book : result) {
                System.out.println(
                        "Title: " + book.getBookTitle() +
                                "\nAuthor: " + book.getAuthor() +
                                "\nYear: " + book.getYear());
                System.out.println("-------------------");
            }
        }
    }

    private void displayStudentCount() {
        int studentCount = librarySystem.getStudentCount();
        System.out.println("\nTotal registered students: " + studentCount);
    }

    private void handleStudentRegistration() {
        System.out.println("\n--- New Student Registration ---");

        System.out.print("Student name: ");
        String name = scanner.nextLine();

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        librarySystem.registerStudent(name, studentId, username, password);
    }

     private void handleStudentLogin() {
        System.out.println("\n--- Student Login ---");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = librarySystem.authenticateStudent(username, password);

        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            displayLoggedInStudentMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void displayLoggedInStudentMenu() {
        while (currentUser != null) {
            System.out.println("\n=== Student Dashboard ===");
            System.out.println("1. View My Information");
            System.out.println("2. Edit My Information");
            System.out.println("3. Search book");
            System.out.println("4. Borrow a Book");
            System.out.println("5. Return a Book");
            System.out.println("6. View Available Books");
            System.out.println("7. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 7);

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(currentUser);
                    break;
                case 2:
                    librarySystem.editStudentInformation(currentUser);
                    break;
                case 3:
                    librarySystem.searchingBook();
                    break;
                case 4:
                    librarySystem.borrowBook(currentUser);
                    break;
                case 5:
                    librarySystem.returnBook(currentUser);
                    break;
                case 6:
                    librarySystem.displayAvailableBooks();
                    break;
                case 7:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void displayManagerMenu() {
        while (true) {
            System.out.println("\n=== Manager Dashboard ===");
            System.out.println("1. add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. remove Employee");
            System.out.println("4. Logout");

            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1 :
                    librarySystem.addEmployee();
                    break;
                case 2 :
                    librarySystem.viewEmployees();
                    break;
                case 3 :
                    librarySystem.removeEmployee();
                    break;
                case 4 :
                    System.out.println("Return to main menu");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void displayEmployeeMenu(Employee employee) {
        while (true) {
            System.out.println("\n=== Employee Dashboard ===");
            System.out.println("1. View my information");
            System.out.println("2. Logout");

            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 2);

            switch (choice) {
                case 1 :
                    System.out.println(employee.toString());
                    break;
                case 2 :
                    System.out.println("Return to main menu");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }


    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
