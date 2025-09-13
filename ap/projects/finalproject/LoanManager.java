package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanManager {

    private List<Loan> loans;
    private StudentManager studentManager;
    private BookManager bookManager;

    public LoanManager(StudentManager studentManager, BookManager bookManager) {
        this.studentManager = studentManager;
        this.bookManager = bookManager;
        this.loans = FileManager.loadLoans(studentManager.getStudents(), bookManager.getBooks());
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
        FileManager.saveLoans(loans);
    }

    public void studentRequestLoan(Student student, Book book, String startDate, String endDate) {
        if (!book.isAvailable()) {
            System.out.println("This book is already on loan. Please check back later.");
            return;
        }
        Loan newLoan = new Loan(student, book, startDate, endDate);
        loans.add(newLoan);
        book.setAvailable(false);
        System.out.println("Loan request registered successfully for book: " +book.getBookTitle());
    }

    public void showLastLoans(int k) {

        if (loans.isEmpty()) {
            System.out.println("There is no loans yet!");
            return;
        }

        System.out.println("=== Last " + k + " loans ===");

        loans.stream()
                .skip(Math.max(0, loans.size() - k))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public void showLoanHistory(Student student) {
        System.out.println("\n=== Loan History for " + student.getName() + " ===");

        List<Loan> studentLoans = loans.stream()
                .filter(l -> l.getStudent().getStudentId().equals(student.getStudentId()))
                .collect(Collectors.toList());

        if (studentLoans.isEmpty()) {
            System.out.println("No loans found for this student.");
            return;
        }

        studentLoans.forEach(loan -> {
            System.out.println("Loan ID: " + loan.getLoanId());
            System.out.println("Book title: " + loan.getBook().getBookTitle());
            System.out.println("Start date: " + loan.getStartDate());
            System.out.println("End date: " + loan.getEndDate());
            System.out.println("Receive date: " + (loan.isReceived() ? loan.getReceivedDate() : "Not received yet"));
            System.out.println("-------------------------");
        });
    }

    public void printAllLoans() {
        if (loans.isEmpty()) {
            System.out.println("There is no loans yet!");
        } else {
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }

    public void printSpecialStudentLoans(Student student) {
        for (Loan loan : loans) {
            if (loan.getStudent().equals(student)) {
                System.out.println(loan);
            }
        }
    }
}
