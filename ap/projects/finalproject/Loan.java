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
    private String receiveDate;
    private String returnDate;
    private boolean returned;
    private boolean approved;
    private boolean received;

    public Loan(Student student, Book book, String startDate, String endDate) {
        this.loanId = counter++;
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
        this.approved = false;
        this.received = false;
        this.returnDate = null;
        this.receiveDate = null;
    }

    public Loan(int loanId , Student student, Book book, String startDate, String endDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
        this.approved = false;
        this.received = false;
        this.returnDate = null;
        this.receiveDate = null;
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

    public String getReceivedDate() {
        return receiveDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public boolean isReceived() {
        return received;
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

    public void setReceived(boolean received) {
        this.received = received;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return  "Loan id: " + loanId +
                " , Student name: " + student.getName() +
                " , Book name: " + book.getBookTitle() +
                " , Loan start date: " + startDate +
                " , Loan end date: " + endDate +
                " , Loan return date: " + returnDate +
                " , Is Returned: " + returned +
                " , Is Approved: " + approved;
    }
}
