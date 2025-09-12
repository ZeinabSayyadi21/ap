package ap.projects.finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private static final String STUDENT_FILE = "students.txt";
    private static final String BOOK_FILE = "books.txt";
    private static final String EMPLOYEE_FILE = "employees.txt";
    private static final String LOAN_FILE = "loan.txt";

    public static void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            for (Student student : students) {
                writer.println(student.getName() +","+ student.getStudentId() +","+ student.getUsername()
                +","+ student.getPassword() +","+ student.isActive());
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " +e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENT_FILE);

        if (!file.exists()) {
            return students;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String name = parts[0];
                    String studentId = parts[1];
                    String username = parts[2];
                    String password = parts[3];

                    boolean active = true;

                    if (parts.length >= 5) {
                        active = Boolean.parseBoolean(parts[4]);
                    }

                    Student student = new Student(name, studentId, username, password);
                    student.setActive(active);
                    students.add(student);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no students file!");
        }
        return students;
    }

    public static void saveEmployees(List<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPLOYEE_FILE))) {
            for (Employee employee : employees) {
                writer.println(employee.getName() +","+ employee.getEmployeeId() +","+ employee.getUsername()
                        +","+ employee.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error saving employees: " +e.getMessage());
        }
    }

    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(EMPLOYEE_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    employees.add(new Employee(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no employees file!");
        }
        return employees;
    }

    public static void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (Book book : books) {
                writer.println(book.getBookId() +","+book.getBookTitle() +","+ book.getAuthor() +","+ book.getYear()
                        +","+ book.isAvailable());
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " +e.getMessage());
        }
    }

    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        int maxId = 0;

        try (Scanner scanner = new Scanner(new File(BOOK_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 5) {
                    int bookId = Integer.parseInt(parts[0]);
                    String bookTitle = parts[1];
                    String author = parts[2];
                    String year = parts[3];
                    boolean available = Boolean.parseBoolean(parts[4]);

                    Book book = new Book(bookTitle, author, year, available);
                    book.setBookId(bookId);
                    books.add(book);

                    if (bookId > maxId)
                        maxId = bookId;
                }
            }
            Book.setCounter(maxId + 1);

        } catch (FileNotFoundException e) {
            System.out.println("There is no students file!");
        }
        return books;
    }

    public static void saveLoans(List<Loan> loans) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOAN_FILE))) {
            for (Loan loan : loans) {
                writer.println(loan.getLoanId() +","+loan.getStudent().getStudentId() +","+
                        loan.getBook().getBookId() +","+ loan.getStartDate()
                        +","+loan.getEndDate()  +","+loan.getReturnDate() +","+loan.isReturned() +","+loan.isApproved()
                        +","+loan.isReceived());
            }
        } catch (IOException e) {
            System.out.println("Error saving loans: " +e.getMessage());
        }
    }

    public static List<Loan> loadLoans(List<Student> students, List<Book> books) {
        List<Loan> loans = new ArrayList<>();
        File file = new File(LOAN_FILE);

        if (!file.exists())
            return loans;

        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 9) {
                    int loanId = Integer.parseInt(parts[0]);
                    String studentId = parts[1];
                    int bookId = Integer.parseInt(parts[2]);
                    String startDate = parts[3];
                    String endDate = parts[4];
                    String returnDate = parts[5];
                    boolean returned = Boolean.parseBoolean(parts[6]);
                    boolean approved = Boolean.parseBoolean(parts[7]);
                    boolean received = Boolean.parseBoolean(parts[8]);

                    Student student = students.stream()
                            .filter( student1 -> student1.getStudentId().equals(studentId))
                            .findFirst()
                            .orElse(null);

                   Book book = books.stream()
                            .filter( book1 -> book1.getBookId() == bookId)
                            .findFirst()
                            .orElse(null);

                   if (student != null && book != null) {
                       Loan loan = new Loan(loanId, student, book, startDate, endDate);
                       loan.setReturned(returned);
                       loan.setApproved(approved);
                       loan.setReceived(received);
                       loans.add(loan);
                   }
                }
            }
        } catch (IOException e ) {
            System.out.println("Error loading loans: " + e.getMessage());
        }
        return loans;
    }
}
