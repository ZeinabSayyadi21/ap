package ap.projects.finalproject;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class LibrarySystem {

    private StudentManager studentManager;
    private MenuHandler menuHandler;
    private BookManager bookManager;
    private LoanManager loanManager;
    private StatisticsManager statisticsManager;
    private EmployeeManager employeeManager;

    InputHandler input = new InputHandler();


    public LibrarySystem() {
        DatabaseManager.initDatabase();

        this.studentManager = new StudentManager();
        this.bookManager = new BookManager();
        this.loanManager = new LoanManager(studentManager, bookManager);
        this.employeeManager = new EmployeeManager();
        this.statisticsManager = new StatisticsManager(
                studentManager.getStudents(),
                bookManager.getBooks(),
                loanManager.getLoans()
        );


        this.menuHandler = new MenuHandler(
                this,
                this.bookManager,
                this.employeeManager,
                this.loanManager,
                this.statisticsManager,
                this.studentManager
        );
    }

    public void showStatistics() {
        showStatistics(5);
    }

    public void showStatistics(int lastLoansCount) {
        statisticsManager.showStatistics();
        loanManager.showLastLoans(lastLoansCount);
    }


    public int getStudentCount() {
        return this.studentManager.getStudentCount();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
    }

    public Student authenticateStudent(String username, String password) {
        return studentManager.authenticateStudent(username, password);
    }

    public void editStudentInformation(Student student) {
        System.out.println("\n--- Edit My Information (ID and username cannot be changed) ---");

        String newName = input.getString("Please enter new name or skip it: ");
        if (!newName.trim().isEmpty())
            student.setName(newName);

        String newPassword = input.getString("Please enter new password or skip it: ");
        if (!newPassword.trim().isEmpty())
            student.setPassword(newPassword);

        DatabaseManager.saveStudents(studentManager.getStudents());
        System.out.println("Your information updated successfully!");
    }

    public List<Book> searchingBook() {
        String bookTitle = input.getString("Please enter book title or skip it: ");
        String author = input.getString("Please enter author or skip it: ");
        String year = input.getString("Please enter year or skip it: ");

        List<Book> result = bookManager.searchBooks(
                bookTitle.isEmpty() ? null : bookTitle,
                author.isEmpty() ? null : author,
                year.isEmpty() ? null : year);
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
                                "\nYear: " + book.getYear() +
                                "\nAvailable: " + (book.isAvailable() ? "Yes" : "No") +
                                "\n-----------------------"
                );
            }
        }
        return result;
    }

    public void editBookInformation() {
        List<Book> result = searchingBook();

        if (result.isEmpty()) {
            return;
        }


        int id = input.getInt("Please enter the id of book that you want to edit: ");
        Book book = bookManager.getBooks().stream()
                .filter(book1 -> book1.getBookId() == id)
                .findFirst()
                .orElse(null);
        if (book != null) {
            bookManager.editBook(book);
        } else {
            System.out.println("Book not found!");
        }
    }

    public void borrowBook(Student student) {
        if (!student.isActive()) {
            System.out.println("You are not allowed to borrow books. Your account is inactive.");
            return;
        }

        String bookTitle = input.getString("Enter book title to search: ");
        List<Book> results = bookManager.searchBooks(bookTitle, null, null);

        if (results.isEmpty()) {
            System.out.println("No books found with this title.");
            return;
        }

        System.out.println("\n=== Founded Books ===");
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            System.out.println((i + 1) + ". " + book.getBookTitle() +
                    " by " + book.getAuthor() +
                    " (" + book.getYear() + ")" +
                    " | Available: " + (book.isAvailable() ? "Yes" : "No"));
        }

        int choice = input.getInt("Select the number of the book you want to borrow (0 to cancel): ");
        if (choice <= 0 || choice > results.size()) {
            System.out.println("Borrow request canceled.");
            return;
        }

        Book selectedBook = results.get(choice - 1);

        if (!selectedBook.isAvailable()) {
            System.out.println("Sorry, this book is not available.");
            return;
        }

        int days = input.getInt("Enter borrowing duration in days: ");
        if (days <= 0) {
            System.out.println("Invalid borrowing duration. Borrow request canceled.");
            return;
        }

        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().plusDays(days).toString();

        Loan newLoan = new Loan(student, selectedBook, startDate, endDate);
        loanManager.addLoan(newLoan);

        selectedBook.setAvailable(false);
        DatabaseManager.saveBooks(bookManager.getBooks());

        System.out.println("Your borrow request has been registered successfully.");
    }

    public void approveLoans(Employee employee) {
        System.out.println("=== Pending Loan Requests ===");

        List<Loan> loans = loanManager.getLoans();
        List<Loan> pending = loans.stream()
                .filter(l -> !l.isApproved())
                .collect(Collectors.toList());

        if (pending.isEmpty()) {
            System.out.println("There is no pending loan request.");
            return;
        }

        pending.forEach(System.out::println);

        int loanId = input.getInt("Enter Loan ID to approve (0 to cancel): ");
        if (loanId == 0) return;

        Loan loan = pending.stream()
                .filter(l -> l.getLoanId() == loanId)
                .findFirst()
                .orElse(null);

        if (loan != null) {
            loan.setApproved(true);
            loan.getBook().setAvailable(false);
            DatabaseManager.saveLoans(loans);
            DatabaseManager.saveBooks(bookManager.getBooks());
            employee.incrementLoansApproved(employeeManager);

            System.out.println("Loan approved successfully!");
        } else {
            System.out.println("Loan not found.");
        }
    }

    public void cancelLoanRequest() {
        System.out.println("=== Pending Loan Requests ===");

        List<Loan> loans = loanManager.getLoans();
        List<Loan> pending = loans.stream()
                .filter(l -> !l.isApproved())
                .collect(Collectors.toList());

        if (pending.isEmpty()) {
            System.out.println("There is no pending loan request.");
            return;
        }

        pending.forEach(System.out::println);

        int loanId = input.getInt("Enter Loan ID to reject (0 to cancel): ");
        if (loanId == 0) return;

        Loan loan = pending.stream()
                .filter(l -> l.getLoanId() == loanId)
                .findFirst()
                .orElse(null);

        if (loan != null) {
            loans.remove(loan);
            loan.getBook().setAvailable(true);
            DatabaseManager.saveLoans(loans);
            DatabaseManager.saveBooks(bookManager.getBooks());

            System.out.println("Loan request rejected. The book is available again.");
        } else {
            System.out.println("Loan not found.");
        }
    }

    public void receiveLoanByStudent(Student student) {
        System.out.println("\n=== Your Approved Loans ===");

        List<Loan> loans = loanManager.getLoans();
        List<Loan> pendingPickups = loans.stream()
                .filter(l -> l.getStudent().getStudentId().equals(student.getStudentId()))
                .filter(Loan::isApproved)
                .filter(l -> !l.isReceived())
                .collect(Collectors.toList());

        if (pendingPickups.isEmpty()) {
            System.out.println("You have no approved loans to pick up.");
            return;
        }


        pendingPickups.forEach(System.out::println);

        int loanId = input.getInt("Enter Loan ID to receive (0 to cancel): ");
        if (loanId == 0) return;

        Loan loan = pendingPickups.stream()
                .filter(l -> l.getLoanId() == loanId)
                .findFirst()
                .orElse(null);

        if (loan != null) {
            loan.setReceived(true);
            loan.setReceiveDate(LocalDate.now().toString());
            DatabaseManager.saveLoans(loans);
            System.out.println("You have successfully received the book: " + loan.getBook().getBookTitle());
        } else {
            System.out.println("Invalid Loan ID!");
        }
    }

    public void addEmployee() {
        String name = input.getString("Please enter name: ");
        String employeeId = input.getString("Please enter employee id: ");
        String username = input.getString("Please enter username: ");
        String password = input.getString("Please enter password: ");

        Employee employee = new Employee(name, employeeId, username, password);
        employeeManager.addEmployee(employee);
    }

    public void removeEmployee() {
        String username = input.getString("Please enter username of employee you want to remove: ");
        employeeManager.removeEmployee(username);
    }

    public void viewEmployees() {
        System.out.println("\n=== Employees List ===");

        for (Employee employee : employeeManager.getEmployees()) {
            System.out.println(employee);
        }
    }

    public Employee employeeLogin() {
        String username = input.getString("Please enter your username: ");
        String password = input.getString("Please enter your password: ");

        return employeeManager.login(username, password);

    }

    public void returnBook(Student student) {
        System.out.println("\n=== Your Active Loans ===");

        List<Loan> loans = loanManager.getLoans();
        List<Loan> activeLoans = loans.stream()
                .filter(l -> l.getStudent().getStudentId().equals(student.getStudentId()))
                .filter(Loan::isReceived)
                .filter(l -> !l.isReturned())
                .collect(Collectors.toList());

        if (activeLoans.isEmpty()) {
            System.out.println("You have no active loans to return.");
            return;
        }

        activeLoans.forEach(System.out::println);

        int loanId = input.getInt("Enter Loan ID to return (0 to cancel): ");
        if (loanId == 0) return;

        Loan loan = activeLoans.stream()
                .filter(l -> l.getLoanId() == loanId)
                .findFirst()
                .orElse(null);

        if (loan != null) {
            loan.setReturned(true);
            loan.setReturnDate(LocalDate.now().toString());
            loan.getBook().setAvailable(true);

            DatabaseManager.saveLoans(loans);
            DatabaseManager.saveBooks(bookManager.getBooks());

            System.out.println("Book returned successfully: " + loan.getBook().getBookTitle());
        } else {
            System.out.println("Invalid Loan ID!");
        }
    }


    public void returnBookFromStudent(Employee employee, Student student) {

        List<Loan> loans = loanManager.getLoans();
        List<Loan> studentLoans = loans.stream()
                .filter(l -> l.getStudent() != null &&
                        l.getStudent().getId().equals(student.getId()))
                .filter(l -> !l.isReturned())
                .filter(l -> l.getReturnDate() == null ||
                        l.getReturnDate().isEmpty())
                .collect(Collectors.toList());

        if (studentLoans.isEmpty()) {
            System.out.println("This student has no active loans.");
            return;
        }

        studentLoans.forEach(System.out::println);

        int loanId = input.getInt("Enter Loan ID to return (0 to cancel): ");
        if (loanId == 0) return;

        Loan loan = studentLoans.stream()
                .filter(l -> l.getLoanId() == loanId)
                .findFirst()
                .orElse(null);

        if (loan != null) {
            loan.setReturnDate(LocalDate.now().toString());
            loan.setReturned(true);

            loan.getBook().setAvailable(true);
            DatabaseManager.saveLoans(loans);
            DatabaseManager.saveBooks(bookManager.getBooks());

            employee.incrementLoansReturned(employeeManager);

            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Loan not found!");
        }
    }


    public void displayAvailableBooks() {
        System.out.println("\n=== Available books ===");
        List<Book> availableBooks = bookManager.getBooks().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
        if (availableBooks.isEmpty()) {
            System.out.println("There is no book yet!");
        } else {
            availableBooks.forEach(book -> {
                System.out.println(
                        "\nBook id: " + book.getBookId() +
                                "\nBook title: " + book.getBookTitle() +
                                "\nAuthor: " + book.getAuthor() +
                                "\nYear: " + book.getYear() +
                                "\n----------------------"
                );
            });
        }
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();


        system.start();
    }
}

