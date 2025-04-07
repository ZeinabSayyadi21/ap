package ap.exercises.ex3;

import java.io.*;

public class Main_EX3_LM_1_2_A {
    public static void main(String[] args) {
        Book[] books = {
                new Book("Big Java", "Cay S.Horstmann", 1377, 2016),
                new Book("Computer Networks", "Larry Petterson", 884, 2011),
                new Book("Operating Systems", "Abraham Silberschatz", 758, 1378),
                new Book("Database" , "Raghu Ramakrishnan" , 1098 , 2008),
        };



        Student[] students = {
                new Student("Zeinab", "Sayyadi", "463132", "Computer engineering"),
                new Student("Mohadeseh", "Sheykh", "463133", "Humanities"),
                new Student("Ala" , "Fateh" , "463134" , "Art"),
        };


        System.out.println("List of available books:");
        for (Book book : books) {
            book.showList();
        }

        System.out.println("Students:");
        for (Student student : students) {
            student.showList();
        }

        saveBooksToFile(books , "book.txt");
        saveStudentsToFile(students , "student.txt");

        System.out.println("Saved to file Successfully!");
    }

    private static void saveBooksToFile(Book[] books , String fileName) {
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

        Book (String t , String a , int p , int y) {
            bookTitle = t;
            author = a;
            pages = p;
            publicationYear = y;
        }

        void showList() {
            System.out.println(bookTitle + " -> " + author + " -> " + pages + " -> " + publicationYear );
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

        Student( String f , String l , String i , String m) {
            firstName = f;
            lastName = l;
            id = i;
            major = m;
        }
        void showList() {
            System.out.println(firstName + " -> " + lastName + " -> " + id + " -> " + major );
        }
        String toFileString() {
            return firstName + "," + lastName + "," + id + "," + major;
        }
    }
}

