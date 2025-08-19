package ap.projects.finalproject;

public class Loan {

    private Student student;
    private Book book;
    private String startDate;
    private String endDate;
    private boolean returned;

    public Loan(Student student, Book book, String startDate, String endDate, boolean returned) {
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = returned;
    }

    public Loan(Student student, Book book, String startDate, String endDate) {
        this.student = student;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
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

    @Override
    public String toString() {
        return "Student name: " + student.getName() +
                " , Book name: " + book.getBookTitle() +
                " , Loan start date: " + startDate +
                " , Loan end date: " + endDate +
                " , Is Returned: " + returned;
    }
}
