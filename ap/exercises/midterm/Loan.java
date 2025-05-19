package ap.exercises.midterm;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private Book book;
    private Student student;
    private Librarian deliveredBy;
    private LocalDate deliveredDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Librarian receiveBy;
    private boolean isReturned;
    private Librarian returnConfirmedBy;

    public Loan(Book book, Student student, Librarian deliveredBy,LocalDate deliveredDate, LocalDate dueDate,
                boolean isReturned) {

        this.book = book;
        this.student = student;
        this.deliveredBy = deliveredBy;
        this.deliveredDate = deliveredDate;
        this.dueDate = dueDate;
        this.isReturned = false;
    }

    public Loan(Book book, Student student, LocalDate deliveredDate, Librarian deliveredBy,
                LocalDate loanReturnDate, Librarian librarian) {
    }

    public void registerReturn(LocalDate returnDate, Librarian receiveBy) {
        if (this.returnDate == null) {
            this.returnDate = returnDate;
            this.receiveBy = receiveBy;
        } else {
            System.out.println("This book has already been returned");
        }
    }

    public void setReceiveBy(Librarian librarian) {
       this.receiveBy = librarian;
    }

    public void setDeliveredBy(Librarian deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        this.isReturned = returned;
    }

    public void setReturnConfirmedBy(Librarian librarian) {
        this.returnConfirmedBy = librarian;
    }

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public Librarian getDeliveredBy() {
        return deliveredBy;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Librarian getReceiveBy() {
        return receiveBy;
    }

    public Librarian getReturnConfirmedBy() {
        return returnConfirmedBy;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public boolean isReturnDate() {
        return returnDate != null;
    }

    public boolean isApproved() {
        return deliveredBy != null;
    }

    public boolean isReturnConfirmedBy() {
        return returnConfirmedBy != null;
    }

    public boolean isOverdue() {
        if (returnDate == null) {
            return LocalDate.now().isAfter(dueDate);     //The book has not been returned yet
        } else {
            return returnDate.isAfter(dueDate);
        }
    }

    public long getDaysLate() {
        if (returnDate == null) {
            return 0;
        } else {
            return returnDate.isAfter(dueDate) ? ChronoUnit.DAYS.between(dueDate, returnDate) : 0;
        }
    }

    public String toString() {
        return "Book: " + book.getBookTitle() + ", Student: " + student.getFirstName() + "," + student.getLastName() +
                ", Delivered: " + deliveredDate + ", Due: " + dueDate + ", Returned: " + (returnDate == null ? "not yet" : returnDate) +
                ", Receive by: " + (receiveBy == null ? "not yet" : receiveBy.getFirstName() + receiveBy.getLastName());
    }
}
