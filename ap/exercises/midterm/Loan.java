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

    public Loan(Book book, Student student, Librarian deliveredBy, LocalDate deliveredDate, LocalDate dueDate) {

      this.book = book;
      this.student = student;
      this.deliveredBy = deliveredBy;
      this.deliveredDate = deliveredDate;
      this.dueDate = dueDate;
    }

    public void registerReturn(LocalDate returnDate, Librarian receiveBy){
        if (this.returnDate == null) {
            this.returnDate = returnDate;
            this.receiveBy = receiveBy;
        } else {
            System.out.println("This book has already been returned");
        }
    }

    public Book getBook(){
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
    public LocalDate getDueDate(){
        return dueDate;
    }
    public LocalDate getReturnDate(){
        return returnDate;
    }
    public Librarian getReceiveBy(){
        return receiveBy;
    }

    public boolean isOverdue() {
        if (returnDate == null) {
            return LocalDate.now().isAfter(dueDate);     //The book has not been returned yet
        } else {
            return  returnDate.isAfter(dueDate);
        }
    }

    public long getDaysLate() {
        if (returnDate == null) {
            return 0;
        } else {
            return returnDate.isAfter(dueDate) ? ChronoUnit.DAYS.between(dueDate,returnDate) : 0;
        }
    }

    public String toString() {
        return "Book: " +book.getBookTitle()+ ", Student: " +student.getFirstName()+ "," +student.getLastName()+
                ", Delivered: " +deliveredDate+ ", Due: " +dueDate+ ", Returned: " + (returnDate == null ? "not yet" : returnDate)+
                ", Receive by: " +(receiveBy == null ? "not yet" : receiveBy.getFirstName() + receiveBy.getLastName());
    }
}
