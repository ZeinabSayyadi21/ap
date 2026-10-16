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

    private LocalDate parseDate(String date) {
        try {
            return date == null ? null : LocalDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public void showStatistics() {
        System.out.println("=== Library Statistics ===");

        int totalStudents = (int) students.stream().count();
        int totalBooks = (int) books.stream().count();
        int totalLoans = (int) loans.stream().count();
        long availableBooks = books.stream().filter(Book::isAvailable).count();

        System.out.println("Total students: " +totalStudents);
        System.out.println("Total books: " +totalBooks);
        System.out.println("Available books: " +availableBooks);
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
                    LocalDate returnDate = parseDate(l.getReturnDate());
                    LocalDate endDate = parseDate(l.getEndDate());
                    return returnDate != null && endDate != null && returnDate.isAfter(endDate);
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
                .filter(Loan::isReturned)
                .filter(l -> parseDate(l.getReturnDate()) != null)
                .collect(Collectors.toList());

        double avgLoanDays = 0;
        if (!completedLoans.isEmpty()) {
            avgLoanDays = completedLoans.stream()
                    .mapToLong(l -> {
                        LocalDate received = parseDate(l.getReceivedDate());
                        if (received == null)
                            received = parseDate(l.getStartDate());
                        LocalDate returned = parseDate(l.getReturnDate());
                        if (received == null || returned == null)
                            return 0;
                        return Math.max(ChronoUnit.DAYS.between(received, returned), 0);
                    })
                    .average()
                    .orElse(0);
        }

        System.out.println("Total Loan Requests: " + totalRequests);
        System.out.println("Total Approved Loans: " + totalApproved);
        System.out.println("Average Loan Duration (days): " + avgLoanDays);
    }


    public void printAllStudentsStatistics() {
        System.out.println("\n=== All Students Loan Statistics ===");


        List<StudentStats> studentStatsList = students.stream()
                .map(student -> {
                    List<Loan> studentLoans = loans.stream()
                            .filter(l -> l.getStudent().getStudentId().equals(student.getStudentId()))
                            .collect(Collectors.toList());

                    long totalLoans = studentLoans.size();
                    long notReturned = studentLoans.stream()
                            .filter(l -> !l.isReturned() && l.isReceived())
                            .count();
                    long lateReturns = studentLoans.stream()
                            .filter(Loan::isReturned)
                            .filter(l -> {
                                LocalDate returnDate = parseDate(l.getReturnDate());
                                LocalDate endDate = parseDate(l.getEndDate());
                                return returnDate != null && endDate != null && returnDate.isAfter(endDate);
                            })
                            .count();

                    return new StudentStats(student, totalLoans, notReturned, lateReturns);
                })
                .collect(Collectors.toList());

        long totalStudents = studentStatsList.size();
        long totalLoans = studentStatsList.stream().mapToLong(StudentStats::getTotalLoans).sum();
        long totalNotReturned = studentStatsList.stream().mapToLong(StudentStats::getNotReturned).sum();
        long totalLateReturns = studentStatsList.stream().mapToLong(StudentStats::getLateReturns).sum();

        System.out.println("Total Students: " + totalStudents);
        System.out.println("Total Loans: " + totalLoans);
        System.out.println("Total Not Returned: " + totalNotReturned);
        System.out.println("Total Late Returns: " + totalLateReturns);

        System.out.println("\n=== Top 10 Students with Most Late Returns ===");
        studentStatsList.stream()
                .filter(stats -> stats.getLateReturns() > 0)
                .sorted((s1, s2) -> Long.compare(s2.getLateReturns(), s1.getLateReturns()))
                .limit(10)
                .forEach(stats -> {
                    System.out.println("Student: " + stats.getStudent().getName() +
                            " | Late Returns: " + stats.getLateReturns() +
                            " | Total Loans: " + stats.getTotalLoans() +
                            " | Not Returned: " + stats.getNotReturned());
                });

        System.out.println("\n=== Students with Not Returned Books ===");
        studentStatsList.stream()
                .filter(stats -> stats.getNotReturned() > 0)
                .forEach(stats -> {
                    System.out.println("Student: " + stats.getStudent().getName() +
                            " | Not Returned: " + stats.getNotReturned() +
                            " | Total Loans: " + stats.getTotalLoans());
                });
    }

    private static class StudentStats {
        private Student student;
        private long totalLoans;
        private long notReturned;
        private long lateReturns;

        public StudentStats(Student student, long totalLoans, long notReturned, long lateReturns) {
            this.student = student;
            this.totalLoans = totalLoans;
            this.notReturned = notReturned;
            this.lateReturns = lateReturns;
        }

        public Student getStudent() { return student; }
        public long getTotalLoans() { return totalLoans; }
        public long getNotReturned() { return notReturned; }
        public long getLateReturns() { return lateReturns; }
    }
}
