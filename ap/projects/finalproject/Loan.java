package ap.projects.finalproject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {

    private static int counter = 1;

    private int loanId;
    private Student student;
    private Book book;
    private String startDate;
    private String endDate;
    private boolean returned;
    private boolean approved;

    public Loan(Student student, Book book, String startDate, String endDate) {
        this.loanId = counter++;
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
        this.approved = false;
    }

    public Loan(int loanId , Student student, Book book, String startDate, String endDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
        this.approved = false;
        if (loanId >= counter) {
            counter = loanId +1;
        }
    }



    public LocalDate getStartDateAsDate() {
        return LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getEndDateAsDate() {
        return LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public int getLoanId() {
        return loanId;
    }

    public Student getStudent() {
        return student;
    }

    public Book getBook() {
        return book;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return  "Loan id: " + loanId +
                " , Student name: " + student.getName() +
                " , Book name: " + book.getBookTitle() +
                " , Loan start date: " + startDate +
                " , Loan end date: " + endDate +
                " , Is Returned: " + returned +
                " , Is Approved: " + approved;
    }
}
