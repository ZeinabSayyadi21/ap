package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanManager {

    private List<Loan> loans;

    public LoanManager() {
        loans = new ArrayList<>();
    }

    public List<Loan> getLoans() {
        return loans;
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
