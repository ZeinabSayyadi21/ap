package ap.projects.finalproject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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

    public void printStudentLoanStatistics(Student student, List<Loan> loans) {
        System.out.println("\n=== Loan Statistics for " + student.getName() + " ===");


        List<Loan> studentLoans = loans.stream()
                .filter(l -> l.getStudent().getStudentId().equals(student.getStudentId()))
                .collect(Collectors.toList());

        long totalLoans = studentLoans.size();


        long lateReturns = studentLoans.stream()
                .filter(Loan::isReturned)
                .filter(l -> {
                    if (l.getReturnDate() != null) {
                        LocalDate returnDate = LocalDate.parse(l.getReturnDate());
                        LocalDate endDate = l.getEndDateAsDate();
                        return returnDate.isAfter(endDate);
                    }
                    return false;
                })
                .count();


        long activeLoans = studentLoans.stream()
                .filter(Loan::isReceived)
                .filter(l -> !l.isReturned())
                .count();

        System.out.println("Total Loans: " + totalLoans);
        System.out.println("Active Loans: " + activeLoans);
        System.out.println("Late Returns: " + lateReturns);


        if (!studentLoans.isEmpty()) {
            System.out.println("\n=== Loan Details ===");
            studentLoans.forEach(loan -> {
                System.out.println("Book title: " + loan.getBook().getBookTitle() +
                        " | Start date: " + loan.getStartDate() +
                        " | End date: " + loan.getEndDate() +
                        " | Returned: " + (loan.isReturned() ? "Yes" : "No") +
                        " | Approved: " + (loan.isApproved() ? "Yes" : "No"));
            });
        }
    }

    public void showEmployeePerformance(Employee employee) {
        System.out.println("\n=== Employee Performance: " + employee.getName() + " ===");
        System.out.println("Books Registered: " + employee.getAddBookCount());
        System.out.println("Loans Approved: " + employee.getLoanApproveCount());
        System.out.println("Loans Returned: " + employee.getLoanReturnedCount());
    }

    public void printLoanStatistics(List<Loan> loans) {
        System.out.println("\n=== Overall Loan Statistics ===");

        long totalRequests = loans.size();

        long totalApproved = loans.stream()
                .filter(Loan::isApproved)
                .count();

        List<Loan> completedLoans = loans.stream()
                .filter(l -> l.getReturnDate() != null)
                .collect(Collectors.toList());

        double avgLoanDays = 0;
        if (!completedLoans.isEmpty()) {
            avgLoanDays = completedLoans.stream()
                    .mapToLong(l -> {
                        LocalDate received = LocalDate.parse(l.getReceivedDate());
                        LocalDate returned = LocalDate.parse(l.getReturnDate());
                        return ChronoUnit.DAYS.between(received, returned);
                    })
                    .average()
                    .orElse(0);
        }

        System.out.println("Total Loan Requests: " + totalRequests);
        System.out.println("Total Approved Loans: " + totalApproved);
        System.out.println("Average Loan Duration (days): " + avgLoanDays);
    }
}
