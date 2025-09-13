package ap.projects.finalproject;

import java.util.List;
import java.util.Scanner;

public class MenuHandler {

    private Scanner scanner;
    private LibrarySystem librarySystem;
    private Student currentUser;
    private BookManager bookManager;
    private EmployeeManager employeeManager;
    private StudentManager studentManager;
    private LoanManager loanManager;
    private StatisticsManager statisticsManager;

    InputHandler input = new InputHandler();

    public MenuHandler(LibrarySystem librarySystem, BookManager bookManager, EmployeeManager employeeManager,
                       LoanManager loanManager, StatisticsManager statisticsManager) {
        this.scanner = new Scanner(System.in);
        this.librarySystem = librarySystem;
        this.currentUser = null;
        this.bookManager = bookManager;
        this.employeeManager = employeeManager;
        this.loanManager = loanManager;
        this.statisticsManager = statisticsManager;
        this.studentManager = new StudentManager();
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
                        "Book id: " + book.getBookId() +
                        "\nBook title: " + book.getBookTitle() +
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
        System.out.println("\n=== Student Login ===");

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
            System.out.println("5. Receive Loan By Student");
            System.out.println("6. Return a Book");
            System.out.println("7. View Available Books");
            System.out.println("8. show loan's statistics");
            System.out.println("9. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 9);

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
                case 5 :
                    librarySystem.receiveLoanByStudent(currentUser);
                    break;
                case 6 :
                    librarySystem.returnBook(currentUser);
                    break;
                case 7 :
                    librarySystem.displayAvailableBooks();
                    break;
                case 8:
                    if (loanManager != null && statisticsManager != null) {
                        loanManager.showLoanHistory(currentUser);
                        statisticsManager.printStudentLoanStatistics(currentUser, loanManager.getLoans());
                    } else {
                        System.out.println("Loan manager or statistics manager is not initialized!");
                    }
                    break;
                case 9 :
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
            System.out.println("4. View employee performance");
            System.out.println("5. View loan statistics");
            System.out.println("6. Logout");

            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 6);

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
                    String employeeId = input.getString("Please enter Employee ID that you want to look performance: ");
                    Employee employee = employeeManager.getEmployeeById(employeeId);
                    if (employee != null) {
                        statisticsManager.showEmployeePerformance(employee);
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;
                case 5 :
                    statisticsManager.printLoanStatistics(loanManager.getLoans());
                    break;
                case 6 :
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
            System.out.println("2. Change password");
            System.out.println("3. Add new book");
            System.out.println("4. Search and edit books");
            System.out.println("5. Active or Inactive student");
            System.out.println("6. Approve loan requests");
            System.out.println("7. Return Book From Student");
            System.out.println("8. Logout");

            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 8);

            switch (choice) {
                case 1 :
                    System.out.println(employee.toString());
                    break;
                case 2 :
                    String oldPass = input.getString("Please enter your current password: ");
                    String newPass = input.getString("Please enter your new password: ");

                    employeeManager.changePassword(employee, oldPass, newPass);
                    break;
                case 3 :
                    bookRegistration(employee);
                    break;
                case 4 :
                    librarySystem.editBookInformation();
                    break;
                case 5 :
                    String studentId = input.getString("Please enter the student id: ");
                    studentManager.toggleStudentStatus(studentId);
                    break;
                case 6 :
                    librarySystem.approveLoans(employee);
                    break;
                case 7 :
                    String id = input.getString("Please enter student ID to return book: ");
                    Student student = studentManager.getStudents().stream()
                            .filter(s -> s.getStudentId().equals(id))
                            .findFirst()
                            .orElse(null);

                    if (student != null) {
                        librarySystem.returnBookFromStudent(employee, student);
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;
                case 8 :
                    System.out.println("Return to main menu");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void bookRegistration(Employee employee) {
        String bookTitle = input.getString("Please enter book title: ");
        String author = input.getString("Please enter author: ");
        String year = input.getString("Please enter year: ");

        Book book = new Book(bookTitle, author, year, true);
        bookManager.addBook(book);
        FileManager.saveBooks(bookManager.getBooks());
        employee.incrementBooksRegistered(employeeManager);
        FileManager.saveEmployees(employeeManager.getEmployees());

        System.out.println("Book added successfully!");
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
