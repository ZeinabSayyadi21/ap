package ap.exercises.midterm;

//import ap.exercises.ex3.Main_EX3_LM_1_2_B;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import ap.exercises.midterm.ConfigReader;
import ap.exercises.midterm.ConfigReader.StorageType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SaveLibraryData {
    private final Library library;
    private final String storageMode;

    public SaveLibraryData(Library library) {
        this.library = library;
        this.storageMode = readStorageMode();

        StorageType type = ConfigReader.getStorageType();
        if (type == StorageType.SQLITE) {
            initializeDatabase();
        }
    }

    private String readStorageMode() {
        try (Scanner scanner = new Scanner(new File("config.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("storage=")) {
                    return line.substring(8).toLowerCase(); // tabsplit, json, sqlite
                }
            }
        } catch (IOException e) {
            System.out.println("Config file not found. Default tabsplit mode will be used.");
        }
        return "tabsplit";
    }

    public void saveAll() {
        switch (storageMode) {
            case "tabsplit":
                saveBooksTabSplit();
                saveStudentsTabSplit();
                saveLibrariansTabSplit();
                saveLoansTabSplit();
                break;
            case "json":
                saveJson();
                break;
            case "sqlite":
                saveSQLite();
                break;
        }
    }

    public void loadAll() {
        switch (storageMode) {
            case "tabsplit":
                loadBooksTabSplit();
                loadStudentsTabSplit();
                loadLibrariansTabSplit();
                loadLoansTabSplit();
                break;
            case "json":
                loadJson();
                break;
            case "sqlite":
                loadSQLite();
                break;
        }
    }


    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            Statement stmt = conn.createStatement();

            // loans
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS loans (" +
                    "book_title TEXT, " +
                    "student_fullname TEXT, " +
                    "borrow_date TEXT, " +
                    "delivered_by TEXT, " +
                    "return_date TEXT, " +
                    "receive_by TEXT)");

            // books
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS books (" +
                    "title TEXT PRIMARY KEY)");

            // students
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (" +
                    "fullname TEXT PRIMARY KEY)");

            // librarians
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS librarians (" +
                    "fullname TEXT PRIMARY KEY)");

        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }


   /* public void saveAllData() {
        saveBooks();
        saveLibrarians();
        saveStudents();
        saveLoans();
    }

    public void loadAllData() {
        loadBooks();
        loadLibrarians();
        loadStudents();
        // loadLoans();
    } */

    private void saveTabSplit() {
        saveBooksTabSplit();
        saveStudentsTabSplit();
        saveLibrariansTabSplit();
        saveLoansTabSplit();
    }

    private void loadTabSplit() {
        loadBooksTabSplit();
        loadStudentsTabSplit();
        loadLibrariansTabSplit();
        loadLoansTabSplit();
    }


    private void saveBooks() {
        try (PrintWriter writer = new PrintWriter("Books.txt")) {
            for (Book book : library.getBooks()) {
                writer.println(book.getBookTitle() + ","
                        + book.getAuthor() + ","
                        + book.getPages() + ","
                        + book.getPublicationYear());

            }
        } catch (IOException e) {
            System.out.println("Error in saving books!Please try again.");
        }
    }

    private void loadBooks() {
        File file = new File("Books.txt");
        if (!file.exists()) {
            System.out.println("Book not found!");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    String bookTitle = parts[0];
                    String author = parts[1];
                    int pages = Integer.parseInt(parts[2]);
                    int publicationYear = Integer.parseInt(parts[3]);
                    Book book = new Book(bookTitle, author, pages, publicationYear);
                    library.addBook(book);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error in loading books: " + e.getMessage());
        }
    }

    private void saveLibrarians() {
        try (PrintWriter writer = new PrintWriter("Librarians.txt")) {
            for (Librarian librarian : library.getLibrarians()) {
                writer.println(librarian.getFirstName() + ","
                        + librarian.getLastName() + ","
                        + librarian.getEmployeeId());
            }
        } catch (IOException e) {
            System.out.println("Error in saving librarians!Please try again.");
        }
    }

    private void loadLibrarians() {
        library.getLibrarians().clear();
        File file = new File("Librarians.txt");
        if (!file.exists()) {
            System.out.println("Librarian not found!");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 3) {
                    Librarian librarian = new Librarian(parts[0], parts[1], parts[2]);
                    library.addLibrarian(librarian);
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading Librarians!Please try again.");
        }
    }

    private void saveStudents() {
        try (PrintWriter writer = new PrintWriter("Students.txt")) {
            for (Student student : library.getStudents()) {
                writer.println(student.getFirstName() + ","
                        + student.getLastName() + ","
                        + student.getId() + ","
                        + student.getMajor() + ","
                        + student.getMembershipDate());
            }
        } catch (IOException e) {
            System.out.println("Error in saving students!Please try again.");
        }
    }

    private void loadStudents() {
        File file = new File("Students.txt");
        if (!file.exists()) {
            System.out.println("Student not found!");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 5) {
                    Student student = new Student(parts[0], parts[1], parts[2],
                            parts[3], java.time.LocalDate.parse(parts[4]));
                    library.registerStudent(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading students!Please try again.");
        }
    }

    private void saveLoans() {
        try (PrintWriter writer = new PrintWriter("Loans.txt");) {
            for (Loan loan : library.getLoans()) {
                writer.println(loan.getBook().getBookTitle() + "," +
                        loan.getStudent().getFirstName() + " " + loan.getStudent().getLastName() + "," +
                        loan.getDeliveredDate() + "," +
                        (loan.getDeliveredBy() != null ? loan.getDeliveredBy().getFirstName() + " " +
                                loan.getDeliveredBy().getLastName() : "null") + "," +
                        (loan.getReturnDate() != null ? loan.getReturnDate() : "null") + "," +
                        (loan.getReceiveBy() != null ? loan.getReceiveBy().getFirstName() + " " +
                                loan.getReceiveBy().getLastName() : "null"));
            }
        } catch (IOException e) {
            System.out.println("Error in saving loans!Please try again.");
        }
    }

    private void loadLoans() {
        File file = new File("Loans.txt");
        if (!file.exists()) {
            System.out.println("Loan not found!");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",", 6);
                if (parts.length == 6) {
                    String bookTitle = parts[0].trim();
                    String studentFullName = parts[1].trim();
                    LocalDate deliveredDate = LocalDate.parse(parts[2].trim());
                    String deliveredByName = parts[3].trim();
                    String returnDate = parts[4].trim();
                    String receiveByName = parts[5].trim();

                    Book book = library.findBookByTitle(bookTitle);
                    Student student = library.findStudentByFullName(studentFullName);
                    Librarian deliveredBy = deliveredByName.equals("null") ? null :
                            library.findLibrarianByFullName(deliveredByName);
                    LocalDate loanReturnDate = returnDate.equals("null") ? null :
                            LocalDate.parse(returnDate);
                    Librarian receiveBy = receiveByName.equals("null") ? null :
                            library.findLibrarianByFullName(receiveByName);

                    Loan loan = new Loan(book, student, deliveredDate, deliveredBy, loanReturnDate, receiveBy);

                    library.addLoans(loan);
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading loans!Please try again.");
        }
    }


    public void saveBooksTabSplit() {
        try (PrintWriter writer = new PrintWriter("Books.txt")) {
            for (Book book : library.getBooks()) {
                writer.println(book.getBookTitle() + "\t" + book.getAuthor() + "\t" + book.getPages() + "\t" +
                        book.getPublicationYear());
            }
        } catch (IOException e) {
            System.out.println("Error saving books!");
        }
    }

    public void saveStudentsTabSplit() {
        try (PrintWriter writer = new PrintWriter("Students.txt")) {
            for (Student student : library.getStudents()) {
                writer.println(student.getFirstName() + "\t" + student.getLastName() + "\t" + student.getId()
                        + "\t" + student.getMajor() + "\t" + student.getMembershipDate());
            }
        } catch (IOException e) {
            System.out.println("Error saving students!");
        }
    }

    public void saveLibrariansTabSplit() {
        try (PrintWriter writer = new PrintWriter("Librarians.txt")) {
            for (Librarian librarian : library.getLibrarians()) {
                writer.println(librarian.getFirstName() + "\t" + librarian.getLastName()
                        + "\t" + librarian.getEmployeeId());
            }
        } catch (IOException e) {
            System.out.println("Error saving librarians!");
        }
    }


    private void saveLoansTabSplit() {
        try (PrintWriter writer = new PrintWriter("Loans.txt")) {
            for (Loan loan : library.getLoans()) {
                writer.println(
                        loan.getBook().getBookTitle() + "\t" +
                                loan.getStudent().getFirstName() + "\t" + loan.getStudent().getLastName() + "\t" +
                                loan.getDeliveredDate() + "\t" +
                                (loan.getDeliveredBy() != null ? loan.getDeliveredBy().getFirstName() : "null") + "\t" +
                                (loan.getDeliveredBy() != null ? loan.getDeliveredBy().getLastName() : "null") + "\t" +
                                (loan.getReturnDate() != null ? loan.getReturnDate() : "null") + "\t" +
                                (loan.getReceiveBy() != null ? loan.getReceiveBy().getFirstName() : "null") + "\t" +
                                (loan.getReceiveBy() != null ? loan.getReceiveBy().getLastName() : "null")

                );
            }
        } catch (IOException e) {
            System.out.println("Error saving Loans!");
        }
    }

    public void loadBooksTabSplit() {
        File file = new File("Books.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\t");
                if (parts.length == 4) {
                    String bookTitle = parts[0];
                    String author = parts[1];
                    int pages = Integer.parseInt(parts[2]);
                    int publicationYear = Integer.parseInt(parts[3]);
                    Book book = new Book(bookTitle, author, pages, publicationYear);
                    library.addBook(book);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    public void loadStudentsTabSplit() {
        File file = new File("Students.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\t");
                if (parts.length == 5) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String id = parts[2];
                    String major = parts[3];
                    LocalDate membershipDate = LocalDate.parse(parts[4]);
                    Student student = new Student(firstName, lastName, id, major, membershipDate);
                    library.registerStudent(student);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    public void loadLibrariansTabSplit() {
        File file = new File("Librarians.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\t");
                if (parts.length == 3) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String employeeId = parts[2];
                    Librarian librarian = new Librarian(firstName, lastName, employeeId);
                    library.addLibrarian(librarian);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading librarians: " + e.getMessage());
        }
    }

    private void loadLoansTabSplit() {
        File file = new File("Loans.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\t");
                if (parts.length == 9) {
                    String bookTitle = parts[0].trim();
                    String studentFirstName = parts[1].trim();
                    String studentLastName = parts[2].trim();
                    LocalDate deliveredDate = LocalDate.parse(parts[3].trim());
                    String deliveredByFirstName = parts[4].trim();
                    String deliveredByLastName = parts[5].trim();
                    String returnDateStr = parts[6].trim();
                    String receiveByFirstName = parts[7].trim();
                    String receiveByLastName = parts[8].trim();

                    Book book = library.findBookByTitle(bookTitle);
                    Student student = library.findStudentByFullName(studentFirstName);
                    Librarian deliveredBy = deliveredByFirstName.equals("null") ? null :
                            library.findLibrarianByFullName(deliveredByFirstName);
                    LocalDate returnDate = returnDateStr.equals("null") ? null : LocalDate.parse(returnDateStr);
                    Librarian receiveBy = receiveByFirstName.equals("null") ? null :
                            library.findLibrarianByFullName(receiveByFirstName);

                    if (book != null && student != null) {
                        Loan loan = new Loan(book, student, deliveredDate, deliveredBy, returnDate, receiveBy);
                        library.addLoans(loan);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading Loans.txt: " + e.getMessage());
        }
    }


    private void saveJson() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try {

            try (FileWriter writer = new FileWriter("Books.json")) {
                gson.toJson(library.getBooks(), writer);
            }

            try (FileWriter writer = new FileWriter("Students.json")) {
                gson.toJson(library.getStudents(), writer);
            }

            try (FileWriter writer = new FileWriter("Librarians.json")) {
                gson.toJson(library.getLibrarians(), writer);
            }

            try (FileWriter writer = new FileWriter("Loans.json")) {
                gson.toJson(library.getLoans(), writer);
            }

        } catch (IOException e) {
            System.out.println("Error saving JSON files: " + e.getMessage());
        }
    }

    class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.toString());
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }

    private void loadJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try {

            File booksFile = new File("Books.json");
            if (booksFile.exists()) {
                try (Reader reader = new FileReader(booksFile)) {
                    Type bookListType = new TypeToken<ArrayList<Book>>() {
                    }.getType();
                    ArrayList<Book> books = gson.fromJson(reader, bookListType);
                    if (books != null) {
                        library.getBooks().clear();
                        library.getBooks().addAll(books);
                    }
                }
            }

            File studentsFile = new File("Students.json");
            if (studentsFile.exists()) {
                try (Reader reader = new FileReader(studentsFile)) {
                    Type studentListType = new TypeToken<ArrayList<Student>>() {
                    }.getType();
                    ArrayList<Student> students = gson.fromJson(reader, studentListType);
                    if (students != null) {
                        library.getStudents().clear();
                        library.getStudents().addAll(students);
                    }
                }
            }

            File librariansFile = new File("Librarians.json");
            if (librariansFile.exists()) {
                try (Reader reader = new FileReader(librariansFile)) {
                    Type librarianListType = new TypeToken<ArrayList<Librarian>>() {
                    }.getType();
                    ArrayList<Librarian> librarians = gson.fromJson(reader, librarianListType);
                    if (librarians != null) {
                        library.getLibrarians().clear();
                        library.getLibrarians().addAll(librarians);
                    }
                }
            }


            File loansFile = new File("Loans.json");
            if (loansFile.exists()) {
                try (Reader reader = new FileReader(loansFile)) {
                    Type loanListType = new TypeToken<ArrayList<Loan>>() {
                    }.getType();
                    ArrayList<Loan> loans = gson.fromJson(reader, loanListType);
                    if (loans != null) {
                        library.getLoans().clear();
                        library.getLoans().addAll(loans);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading JSON files: " + e.getMessage());
        }
    }




    private void saveSQLite() {
        initializeDatabase();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            // Clear old data
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM loans");
                stmt.executeUpdate("DELETE FROM books");
                stmt.executeUpdate("DELETE FROM students");
                stmt.executeUpdate("DELETE FROM librarians");
            }

            // Save Books
            String bookSql = "INSERT INTO books (title) VALUES (?)";
            try (PreparedStatement bookStmt = conn.prepareStatement(bookSql)) {
                for (Book book : library.getBooks()) {
                    bookStmt.setString(1, book.getBookTitle());
                    bookStmt.addBatch();
                }
                bookStmt.executeBatch();
            }

            // Save Students
            String studentSql = "INSERT INTO students (fullname) VALUES (?)";
            try (PreparedStatement studentStmt = conn.prepareStatement(studentSql)) {
                for (Student student : library.getStudents()) {
                    studentStmt.setString(1, student.getFirstName() + " " + student.getLastName());
                    studentStmt.addBatch();
                }
                studentStmt.executeBatch();
            }

            // Save Librarians
            String librarianSql = "INSERT INTO librarians (fullname) VALUES (?)";
            try (PreparedStatement librarianStmt = conn.prepareStatement(librarianSql)) {
                for (Librarian librarian : library.getLibrarians()) {
                    librarianStmt.setString(1, librarian.getFirstName() + " " + librarian.getLastName());
                    librarianStmt.addBatch();
                }
                librarianStmt.executeBatch();
            }

            // Save Loans
            String loanSql = "INSERT INTO loans (book_title, student_fullname, borrow_date, delivered_by, return_date, receive_by) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement loanStmt = conn.prepareStatement(loanSql)) {
                for (Loan loan : library.getLoans()) {
                    loanStmt.setString(1, loan.getBook().getBookTitle());
                    loanStmt.setString(2, loan.getStudent().getFirstName() + " " + loan.getStudent().getLastName());
                    loanStmt.setString(3, loan.getDeliveredDate().toString());
                    loanStmt.setString(4, loan.getDeliveredBy() != null ?
                            loan.getDeliveredBy().getFirstName() + " " + loan.getDeliveredBy().getLastName() : null);
                    loanStmt.setString(5, loan.getReturnDate() != null ? loan.getReturnDate().toString() : null);
                    loanStmt.setString(6, loan.getReceiveBy() != null ?
                            loan.getReceiveBy().getFirstName() + " " + loan.getReceiveBy().getLastName() : null);
                    loanStmt.addBatch();
                }
                loanStmt.executeBatch();
            }

        } catch (SQLException e) {
            System.out.println("Error saving to SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSQLite() {
        initializeDatabase();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            // Load Books
            try (Statement stmt = conn.createStatement();
                 ResultSet bookRS = stmt.executeQuery("SELECT * FROM books")) {
                while (bookRS.next()) {
                    String title = bookRS.getString("title");
                    // باید اطلاعات کامل کتاب را از جایی دیگر دریافت کنید یا ساختار دیتابیس را تغییر دهید
                    // فعلاً با constructor پیش‌فرض ایجاد می‌کنیم
                    Book book = new Book(title, "Unknown", 0, 0);
                    library.addBook(book);
                }
            }

            // Load Students
            try (Statement stmt = conn.createStatement();
                 ResultSet studentRS = stmt.executeQuery("SELECT * FROM students")) {
                while (studentRS.next()) {
                    String fullname = studentRS.getString("fullname");
                    String[] nameParts = fullname.split(" ");
                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";

                    Student student = new Student(firstName, lastName, "ID", "Major", LocalDate.now());
                    library.registerStudent(student);
                }
            }

            // Load Librarians
            try (Statement stmt = conn.createStatement();
                 ResultSet librarianRS = stmt.executeQuery("SELECT * FROM librarians")) {
                while (librarianRS.next()) {
                    String fullname = librarianRS.getString("fullname");
                    String[] nameParts = fullname.split(" ");
                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";
                    Librarian librarian = new Librarian(firstName, lastName, "EMP_ID");
                    library.addLibrarian(librarian);
                }
            }

            // Load Loans
            try (Statement stmt = conn.createStatement();
                 ResultSet loanRS = stmt.executeQuery("SELECT * FROM loans")) {
                while (loanRS.next()) {
                    String bookTitle = loanRS.getString("book_title");
                    String studentFullName = loanRS.getString("student_fullname");
                    LocalDate borrowDate = LocalDate.parse(loanRS.getString("borrow_date"));
                    String deliveredByStr = loanRS.getString("delivered_by");
                    String returnDateStr = loanRS.getString("return_date");
                    String receiveByStr = loanRS.getString("receive_by");

                    Book book = library.findBookByTitle(bookTitle);
                    if (book == null) {
                        System.out.println("Book not found: " + bookTitle);
                        continue;
                    }

                    String[] studentNameParts = studentFullName.split(" ");
                    String studentFirstName = studentNameParts[0];
                    String studentLastName = studentNameParts.length > 1 ? studentNameParts[1] : "";
                    Student student = library.findStudentByFullName(studentFirstName);
                    if (student == null) {
                        System.out.println("Student not found: " + studentFullName);
                        continue;
                    }

                    Librarian deliveredBy = null;
                    if (deliveredByStr != null && !deliveredByStr.equals("null")) {
                        String[] libNameParts = deliveredByStr.split(" ");
                        String libFirstName = libNameParts[0];
                        String libLastName = libNameParts.length > 1 ? libNameParts[1] : "";
                        deliveredBy = library.findLibrarianByFullName(libFirstName);
                    }

                    LocalDate returnDate = null;
                    if (returnDateStr != null && !returnDateStr.equals("null")) {
                        returnDate = LocalDate.parse(returnDateStr);
                    }

                    Librarian receiveBy = null;
                    if (receiveByStr != null && !receiveByStr.equals("null")) {
                        String[] libNameParts = receiveByStr.split(" ");
                        String libFirstName = libNameParts[0];
                        String libLastName = libNameParts.length > 1 ? libNameParts[1] : "";
                        receiveBy = library.findLibrarianByFullName(libFirstName);
                    }

                    Loan loan = new Loan(book, student, borrowDate, deliveredBy, returnDate, receiveBy);
                    library.addLoans(loan);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading from SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


