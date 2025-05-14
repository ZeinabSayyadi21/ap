package ap.exercises.midterm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class InputProcessor {

    private final Scanner scanner;
    private final Library library;

    public InputProcessor(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);

    }

    public int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public boolean loginManager() {
        String fullName = readString("Please enter your full name:");
        return library.loginManager(fullName);
    }

    public Librarian loginLibrarian() {
        String employeeId = readString("Please enter your employee id:");
        return library.loginLibrarian(employeeId);
    }

    public Student loginOrRegisterStudent() {
        Student student = loginStudent();
        if (student == null) {
            String answer = readString("Student not found! Do you want to register?(y/n)");
            if (answer.equalsIgnoreCase("y")) {
                return registerStudent();
            }
        }
        return student;

    }

    public Student loginStudent() {
        String id = readString("Please enter your id:");
        Student student = library.loginStudent(id);
        if (student != null) {
            System.out.println("Welcome to Znu Central Library!");
        } else {
            System.out.println("Student not found!");
        }
        return student;
    }

    public Student registerStudent() {
        String firstName = readString("Enter your first name:");
        String lastName = readString("Enter your last name:");
        String id = readString("Enter your id:");
        String major = readString("Enter your major:");
        LocalDate membershipDate = LocalDate.now();

        Student student = new Student(firstName, lastName, id, major, membershipDate);
        library.registerStudent(student);
        System.out.println("Student registered successfully!");
        return student;
    }

    public void addLibrarian() {
        String firstName = readString("Enter your first name:");
        String lastName = readString("Enter your last name:");
        String employeeId = readString("Enter your employee id:");

        Librarian librarian = new Librarian(firstName, lastName, employeeId);
        library.addLibrarian(librarian);
        System.out.println("Librarian added successfully!");
    }

    public void addBook() {
        String bookTitle = readString("Enter book title:");
        String author = readString("Enter author:");
        int pages = readInt("Enter number of pages:");
        int publicationYear = readInt("Enter publication Year:");

        Book book = new Book(bookTitle, author, pages, publicationYear);
        library.addBook(book);
        System.out.println("Book added successfully!");
    }

    public void searchBookByTitle() {
        String search = readString("Please enter book title for search:");
        ArrayList<Book> result = library.searchBookByTitle(search);

        if (result.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Founded books:");
            for (Book book : result) {
                System.out.println(book);
            }
        }
    }

    public void editLibrarianInfo(Librarian librarian) {

        System.out.println("Current first name:" + librarian.getFirstName());
        String newFirstName = readString("Please enter new first name:");
        if (!newFirstName.trim().isEmpty()) {
            librarian.setFirstName(newFirstName);
        }

        System.out.println("Current last name:" + librarian.getLastName());
        String newLastName = readString("Please enter new last name:");
        if (!newLastName.trim().isEmpty()) {
            librarian.setLastName(newLastName);
        }

        System.out.println("Current employee id:" + librarian.getEmployeeId());
        String newEmployeeId = readString("Please enter new employee id:");
        if (!newEmployeeId.trim().isEmpty()) {
            librarian.setEmployeeId(newEmployeeId);
        }

        System.out.println("Librarian's personal information updated!");
    }

    public void showAllBooks() {
        ArrayList<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("Books not found!");
        } else {
            System.out.println("Available books list:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public void showAllStudents() {
        ArrayList<Student> students = library.getStudents();
        if (students.isEmpty()) {
            System.out.println("Students not found!");
        } else {
            System.out.println("Registered students list:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public void showAllLibrarians() {
        ArrayList<Librarian> librarians = library.getLibrarians();
        if (librarians.isEmpty()) {
            System.out.println("Librarians not found!");
        } else {
            System.out.println("Librarians list:");
            for (Librarian librarian : librarians) {
                System.out.println(librarian);
            }
        }
    }
}

