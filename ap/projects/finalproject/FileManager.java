package ap.projects.finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private static final String STUDENT_FILE = "students.txt";
    private static final String BOOK_FILE = "books.txt";
    private static final String EMPLOYEE_FILE = "employees.txt";

    public static void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            for (Student student : students) {
                writer.println(student.getName() +","+ student.getStudentId() +","+ student.getUsername()
                +","+ student.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " +e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(STUDENT_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3]));
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
}
