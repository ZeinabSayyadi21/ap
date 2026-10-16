package ap.projects.finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Persists all library data in a SQLite database file (library.db),
 * created in the working directory of the application.
 *
 * The legacy text files (students.txt, employees.txt, books.txt, loan.txt)
 * are read only once, to import existing data into an empty database.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:library.db";

    private static final boolean driverLoaded;

    static {
        boolean loaded;
        try {
            Class.forName("org.sqlite.JDBC");
            loaded = true;
        } catch (ClassNotFoundException e) {
            loaded = false;
            System.out.println("===========================================================");
            System.out.println("WARNING: SQLite JDBC driver not found on the classpath!");
            System.out.println("Nothing can be saved to or read from the database.");
            System.out.println("Fix: add lib/sqlite-jdbc-3.42.0.0.jar to the module");
            System.out.println("(IntelliJ: File > Project Structure > Modules >");
            System.out.println("Dependencies > + > JARs or directories).");
            System.out.println("===========================================================");
        }
        driverLoaded = loaded;
    }

    private DatabaseManager() {
    }

    public static void initDatabase() {
        if (!driverLoaded) {
            return;
        }

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS students (" +
                            "student_id TEXT PRIMARY KEY," +
                            "name       TEXT NOT NULL," +
                            "username   TEXT NOT NULL UNIQUE," +
                            "password   TEXT NOT NULL," +
                            "active     INTEGER NOT NULL DEFAULT 1)");

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS employees (" +
                            "employee_id         TEXT PRIMARY KEY," +
                            "name                TEXT NOT NULL," +
                            "username            TEXT NOT NULL UNIQUE," +
                            "password            TEXT NOT NULL," +
                            "add_book_count      INTEGER NOT NULL DEFAULT 0," +
                            "loan_approve_count  INTEGER NOT NULL DEFAULT 0," +
                            "loan_returned_count INTEGER NOT NULL DEFAULT 0)");

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS books (" +
                            "book_id   INTEGER PRIMARY KEY," +
                            "title     TEXT NOT NULL," +
                            "author    TEXT NOT NULL," +
                            "year      TEXT," +
                            "available INTEGER NOT NULL DEFAULT 1)");

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS loans (" +
                            "loan_id      INTEGER PRIMARY KEY," +
                            "student_id   TEXT NOT NULL REFERENCES students(student_id)," +
                            "book_id      INTEGER NOT NULL REFERENCES books(book_id)," +
                            "start_date   TEXT NOT NULL," +
                            "end_date     TEXT NOT NULL," +
                            "return_date  TEXT," +
                            "returned     INTEGER NOT NULL DEFAULT 0," +
                            "approved     INTEGER NOT NULL DEFAULT 0," +
                            "received     INTEGER NOT NULL DEFAULT 0," +
                            "receive_date TEXT)");

        } catch (SQLException e) {
            System.out.println("Error creating database tables: " + e.getMessage());
            return;
        }

        importLegacyFilesIfNeeded();
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static int countRows(String table) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table)) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * One-time migration: if a table is still empty and the matching legacy
     * text file exists, its records are imported into the database.
     */
    private static void importLegacyFilesIfNeeded() {
        if (countRows("students") == 0) {
            List<Student> legacyStudents = FileManager.loadStudents();
            if (!legacyStudents.isEmpty()) {
                saveStudents(legacyStudents);
                System.out.println("Imported " + legacyStudents.size() + " student(s) from students.txt into the database.");
            }
        }

        if (countRows("employees") == 0) {
            List<Employee> legacyEmployees = FileManager.loadEmployees();
            if (!legacyEmployees.isEmpty()) {
                saveEmployees(legacyEmployees);
                System.out.println("Imported " + legacyEmployees.size() + " employee(s) from employees.txt into the database.");
            }
        }

        if (countRows("books") == 0) {
            List<Book> legacyBooks = FileManager.loadBooks();
            if (!legacyBooks.isEmpty()) {
                saveBooks(legacyBooks);
                System.out.println("Imported " + legacyBooks.size() + " book(s) from books.txt into the database.");
            }
        }

        if (countRows("loans") == 0) {
            List<Loan> legacyLoans = FileManager.loadLoans(loadStudents(), loadBooks());
            if (!legacyLoans.isEmpty()) {
                saveLoans(legacyLoans);
                System.out.println("Imported " + legacyLoans.size() + " loan(s) from loan.txt into the database.");
            }
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT name, student_id, username, password, active FROM students";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString("name"),
                        resultSet.getString("student_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
                student.setActive(resultSet.getInt("active") == 1);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    public static void saveStudents(List<Student> students) {
        try (Connection connection = connect()) {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM students");
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO students(name, student_id, username, password, active) VALUES (?,?,?,?,?)")) {
                for (Student student : students) {
                    statement.setString(1, student.getName());
                    statement.setString(2, student.getStudentId());
                    statement.setString(3, student.getUsername());
                    statement.setString(4, student.getPassword());
                    statement.setInt(5, student.isActive() ? 1 : 0);
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT name, employee_id, username, password, add_book_count," +
                "loan_approve_count, loan_returned_count FROM employees";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getString("name"),
                        resultSet.getString("employee_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("add_book_count"),
                        resultSet.getInt("loan_approve_count"),
                        resultSet.getInt("loan_returned_count")));
            }
        } catch (SQLException e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
        return employees;
    }

    public static void saveEmployees(List<Employee> employees) {
        try (Connection connection = connect()) {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM employees");
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO employees(employee_id, name, username, password," +
                            "add_book_count, loan_approve_count, loan_returned_count) VALUES (?,?,?,?,?,?,?)")) {
                for (Employee employee : employees) {
                    statement.setString(1, employee.getEmployeeId());
                    statement.setString(2, employee.getName());
                    statement.setString(3, employee.getUsername());
                    statement.setString(4, employee.getPassword());
                    statement.setInt(5, employee.getAddBookCount());
                    statement.setInt(6, employee.getLoanApproveCount());
                    statement.setInt(7, employee.getLoanReturnedCount());
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        int maxId = 0;
        String sql = "SELECT book_id, title, author, year, available FROM books";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                Book book = new Book(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("year"),
                        resultSet.getInt("available") == 1);
                book.setBookId(bookId);
                books.add(book);

                if (bookId > maxId)
                    maxId = bookId;
            }
            Book.setCounter(maxId + 1);
        } catch (SQLException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    public static void saveBooks(List<Book> books) {
        try (Connection connection = connect()) {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM books");
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO books(book_id, title, author, year, available) VALUES (?,?,?,?,?)")) {
                for (Book book : books) {
                    statement.setInt(1, book.getBookId());
                    statement.setString(2, book.getBookTitle());
                    statement.setString(3, book.getAuthor());
                    statement.setString(4, book.getYear());
                    statement.setInt(5, book.isAvailable() ? 1 : 0);
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public static List<Loan> loadLoans(List<Student> students, List<Book> books) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT loan_id, student_id, book_id, start_date, end_date, return_date," +
                "returned, approved, received, receive_date FROM loans";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int loanId = resultSet.getInt("loan_id");
                String studentId = resultSet.getString("student_id");
                int bookId = resultSet.getInt("book_id");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");
                String returnDate = resultSet.getString("return_date");
                boolean returned = resultSet.getInt("returned") == 1;
                boolean approved = resultSet.getInt("approved") == 1;
                boolean received = resultSet.getInt("received") == 1;
                String receiveDate = resultSet.getString("receive_date");

                Student student = students.stream()
                        .filter(student1 -> student1.getStudentId().equals(studentId))
                        .findFirst()
                        .orElse(null);

                Book book = books.stream()
                        .filter(book1 -> book1.getBookId() == bookId)
                        .findFirst()
                        .orElse(null);

                if (student != null && book != null) {
                    Loan loan = new Loan(loanId, student, book, startDate, endDate);
                    loan.setReturned(returned);
                    loan.setApproved(approved);
                    loan.setReceived(received);
                    loan.setReceiveDate(receiveDate);
                    loan.setReturnDate(returnDate);
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading loans: " + e.getMessage());
        }
        return loans;
    }

    public static void saveLoans(List<Loan> loans) {
        try (Connection connection = connect()) {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM loans");
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO loans(loan_id, student_id, book_id, start_date, end_date, return_date," +
                            "returned, approved, received, receive_date) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
                for (Loan loan : loans) {
                    statement.setInt(1, loan.getLoanId());
                    statement.setString(2, loan.getStudent().getStudentId());
                    statement.setInt(3, loan.getBook().getBookId());
                    statement.setString(4, loan.getStartDate());
                    statement.setString(5, loan.getEndDate());
                    statement.setString(6, loan.getReturnDate());
                    statement.setInt(7, loan.isReturned() ? 1 : 0);
                    statement.setInt(8, loan.isApproved() ? 1 : 0);
                    statement.setInt(9, loan.isReceived() ? 1 : 0);
                    statement.setString(10, loan.getReceivedDate());
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error saving loans: " + e.getMessage());
        }
    }
}
