package ap.projects.finalproject;

import java.util.List;

public class StatisticsManager {

    private List<Student> students;
    private List<Book> books;
    private List<Loan> loans;

    public StatisticsManager(List<Student> students, List<Book> books, List<Loan> loans) {
        this.students = students;
        this.books = books;
        this.loans = loans;
    }

    public void showStatistics() {
        System.out.println("=== Library Statistics ===");

        int totalStudents = (int) students.stream().count();
        int totalBooks = (int) books.stream().count();
        int totalLoans = (int) loans.stream().count();

        System.out.println("Total students: " +totalStudents);
        System.out.println("Total books: " +totalBooks);
        System.out.println("Total loans: " +totalLoans);
    }
}
