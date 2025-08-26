package ap.projects.finalproject;

import java.util.List;

public class LibrarySystem {

    private StudentManager studentManager;
    private MenuHandler menuHandler;
    private BookManager bookManager;
    private LoanManager loanManager;
    private StatisticsManager statisticsManager;
    private EmployeeManager employeeManager;

    InputHandler input = new InputHandler();


    public LibrarySystem() {
        this.studentManager = new StudentManager();
        this.bookManager = new BookManager();
        this.loanManager = new LoanManager();
        this.menuHandler = new MenuHandler(this, this.bookManager);
        this.statisticsManager = new StatisticsManager(studentManager.getStudents(), bookManager.getBooks(),
                loanManager.getLoans());
        this.employeeManager = new EmployeeManager();
    }

    public void showStatistics() {
        statisticsManager.showStatistics();
        loanManager.showLastLoans(5);
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
        System.out.println("Not implemented.");
    }

    public void searchingBook() {
        String bookTitle = input.getString("Please enter book title or skip it: ");
        String author = input.getString("Please enter author or skip it: ");
        String year = input.getString("Please enter year or skip it: ");

        List<Book> result = bookManager.searchBooks (
                bookTitle.isEmpty() ? null : bookTitle,
                author.isEmpty() ? null : author,
                year.isEmpty() ? null : year );
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
                                "\nYear: " + book.getYear() +
                                "\nAvailable: " + (book.isAvailable() ? "Yes" : "No") +
                                "\n-----------------------"
                );
            }
        }
    }

    public void borrowBook(Student student) {

        String bookTitle = input.getString("Please enter book title or skip it: ");
        String author = input.getString("Please enter author or skip it: ");
        String year = input.getString("Please enter year or skip it: ");

        List<Book> foundBooks = bookManager.searchBooks(bookTitle,author,year);

        if (foundBooks.isEmpty()) {
            System.out.println("Not found any books!");
        } else {
            System.out.println("\n=== Found Books ===");
            for (int i = 0; i < foundBooks.size(); i++) {
                Book book = foundBooks.get(i);
                System.out.println((i + 1) + ". " + book.getBookTitle() + " by " + book.getAuthor() +
                        " (" + book.getYear() + ") - Available: " + (book.isAvailable() ? "Yes" : "No"));
            }
            int choice = input.getInt("\nEnter the number of the book you want to borrow: ");
            if (choice < 1 || choice > foundBooks.size()) {
                System.out.println("Invalid selection!");
                return;
            }

            Book borrowBook = foundBooks.get(choice - 1);

            if (!borrowBook.isAvailable()) {
                System.out.println("This book is not available for borrowing!");
                return;
            }
            String startDate = input.getString("Please enter start date(like yyyy-mm-dd): ");
            String endDate = input.getString("Please enter end date(like yyyy-mm-dd): ");
            loanManager.studentRequestLoan(student,borrowBook,startDate,endDate);
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
        System.out.println("Employee removed successfully!");
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
        System.out.println("Not implemented.");
    }

    public void displayAvailableBooks() {
        System.out.println("Not implemented.");
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();


        system.bookManager.addBook(new Book("Java programming", "Mr.Smith", "2020", true));
        system.bookManager.addBook(new Book("Big Java", "Ali Ahmadi", "2012", true));
        system.bookManager.addBook(new Book("python programming", "Ali Rad","2006", true ));

        system.start();
    }
}
