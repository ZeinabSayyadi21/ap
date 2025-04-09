package ap.exercises.ex3;

import java.io.*;
import java.util.ArrayList;

public class Main_EX3_LM_1_2_B {
    public static void main(String[] args) {

        // save the first data
        saveInitialData();


        Book[] loadedBooks = loadBooksFromFile("book.txt");
        System.out.println("\nLoaded books:");
        for (Book book : loadedBooks) {
            book.showList();
        }


        Student[] loadedStudents = loadStudentsFromFile("student.txt");
        System.out.println("\nLoaded students:");
        for (Student student : loadedStudents) {
            student.showList();
        }
    }


    private static void saveInitialData() {
        Book[] books = {
                new Book("Big Java", "Cay S.Horstmann", 1377, 2016),
                new Book("Computer Networks", "Larry Petterson", 884, 2011),
                new Book("Operating Systems", "Abraham Silberschatz", 758, 1378),
                new Book("Database", "Raghu Ramakrishnan", 1098, 2008)
        };

        Student[] students = {
                new Student("Zeinab", "Sayyadi", "463132", "Computer Engineering"),
                new Student("Mohadeseh", "Sheykh", "463133", "Humanities"),
                new Student("Ala", "Fateh", "463134", "Art")
        };

        saveBooksToFile(books, "book.txt");
        saveStudentsToFile(students, "student.txt");
    }


    private static Book[] loadBooksFromFile(String filename) {
        ArrayList<Book> bookList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Book book = new Book(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim()),
                            Integer.parseInt(parts[3].trim())
                    );
                    bookList.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books from file: " + e.getMessage());
        }

        return bookList.toArray(new Book[0]);
    }

    private static Student[] loadStudentsFromFile(String filename) {
        ArrayList<Student> studentList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Student student = new Student(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim()
                    );
                    studentList.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading students from file: " + e.getMessage());
        }

        return studentList.toArray(new Student[0]);
    }


    private static void saveBooksToFile(Book[] books, String fileName) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Cannot save books to file!");
        }
    }

    private static void saveStudentsToFile(Student[] students, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.println(student.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Cannot save students to file!");
        }
    }

    public static class Book {
        String bookTitle;
        String author;
        int pages;
        int publicationYear;

        Book(String t, String a, int p, int y) {
            bookTitle = t;
            author = a;
            pages = p;
            publicationYear = y;
        }

        void showList() {
            System.out.println(bookTitle + " -> " + author + " -> " + pages + " -> " + publicationYear);
        }

        String toFileString() {
            return bookTitle + "," + author + "," + pages + "," + publicationYear;
        }
    }

    public static class Student {
        String firstName;
        String lastName;
        String id;
        String major;

        Student(String f, String l, String i, String m) {
            firstName = f;
            lastName = l;
            id = i;
            major = m;
        }

        void showList() {
            System.out.println(firstName + " -> " + lastName + " -> " + id + " -> " + major);
        }

        String toFileString() {
            return firstName + "," + lastName + "," + id + "," + major;
        }
    }
}