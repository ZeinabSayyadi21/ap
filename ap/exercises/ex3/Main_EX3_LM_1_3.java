package ap.exercises.ex3;

import java.io.*;
import java.util.ArrayList;

public class Main_EX3_LM_1_3 {
    public static void main(String[] args) {

        // save the first data
        saveInitialData();


        Main_EX3_LM_1_2_B.Book[] loadedBooks = loadBooksFromFile("book.txt");
        System.out.println("\nLoaded books:");
        for (Main_EX3_LM_1_2_B.Book book : loadedBooks) {
            book.showList();
        }


        Main_EX3_LM_1_2_B.Student[] loadedStudents = loadStudentsFromFile("student.txt");
        System.out.println("\nLoaded students:");
        for (Main_EX3_LM_1_2_B.Student student : loadedStudents) {
            student.showList();
        }
    }


    private static void saveInitialData() {
        Main_EX3_LM_1_2_B.Book[] books = {
                new Main_EX3_LM_1_2_B.Book("Big Java", "Cay S.Horstmann", 1377, 2016),
                new Main_EX3_LM_1_2_B.Book("Computer Networks", "Larry Petterson", 884, 2011),
                new Main_EX3_LM_1_2_B.Book("Operating Systems", "Abraham Silberschatz", 758, 1378),
                new Main_EX3_LM_1_2_B.Book("Database", "Raghu Ramakrishnan", 1098, 2008)
        };

        Main_EX3_LM_1_2_B.Student[] students = {
                new Main_EX3_LM_1_2_B.Student("Zeinab", "Sayyadi", "463132", "Computer Engineering"),
                new Main_EX3_LM_1_2_B.Student("Mohaddeseh", "Sheykh", "463133", "Humanities"),
                new Main_EX3_LM_1_2_B.Student("Ala", "Fateh", "463134", "Art")
        };

        int search = searchStudent(students , "Mohaddeseh");
        if (search != -1) {
            System.out.println("The searched student was found:" +students[search].firstName);
        } else {
            System.out.println("Student not founded!");
        }

        saveBooksToFile(books, "book.txt");
        saveStudentsToFile(students, "student.txt");
    }


    private static Main_EX3_LM_1_2_B.Book[] loadBooksFromFile(String filename) {
        ArrayList<Main_EX3_LM_1_2_B.Book> bookList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Main_EX3_LM_1_2_B.Book book = new Main_EX3_LM_1_2_B.Book(
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

        return bookList.toArray(new Main_EX3_LM_1_2_B.Book[0]);
    }

    private static Main_EX3_LM_1_2_B.Student[] loadStudentsFromFile(String filename) {
        ArrayList<Main_EX3_LM_1_2_B.Student> studentList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Main_EX3_LM_1_2_B.Student student = new Main_EX3_LM_1_2_B.Student(
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

        return studentList.toArray(new Main_EX3_LM_1_2_B.Student[0]);
    }


    private static void saveBooksToFile(Main_EX3_LM_1_2_B.Book[] books, String fileName) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Main_EX3_LM_1_2_B.Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Cannot save books to file!");
        }
    }

    private static void saveStudentsToFile(Main_EX3_LM_1_2_B.Student[] students, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Main_EX3_LM_1_2_B.Student student : students) {
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
    public static int searchStudent (Main_EX3_LM_1_2_B.Student[] students , String firstName ) {
        for (int i=0 ; i<students.length ; i++) {
            if (students[i].firstName.equalsIgnoreCase(firstName)) {
                return i;
            }
        }
        return -1;
    }


}

