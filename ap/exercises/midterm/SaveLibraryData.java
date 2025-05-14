package ap.exercises.midterm;

import ap.exercises.ex3.Main_EX3_LM_1_2_B;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SaveLibraryData {

    private Library library;

    public SaveLibraryData(Library library) {
        this.library = library;
    }

    public void saveAllData() {
        saveBooks();
        saveLibrarians();
        saveStudents();
    }

    public void loadAllData() {
        loadBooks();
        loadLibrarians();
        loadStudents();
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
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    String bookTitle = parts[0];
                    String author = parts[1];
                    int pages = Integer.parseInt(parts[2]);
                    int publicationYear = Integer.parseInt(parts[3]);
                    Book book = new Book(bookTitle,author,pages,publicationYear);
                    library.addBook(book);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error in loading books: " +e.getMessage());
        }
    }

    private void saveLibrarians() {
        try (PrintWriter writer = new PrintWriter("Librarians.txt")){
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
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 3) {
                    Librarian librarian = new Librarian(parts[0], parts[1], parts[2] );
                    library.addLibrarian(librarian);
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading Librarians!Please try again.");
        }
    }

    private void saveStudents() {
        try (PrintWriter writer = new PrintWriter("Students.txt")){
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
        try (Scanner scanner = new Scanner(file)){
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

}



