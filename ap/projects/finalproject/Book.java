package ap.projects.finalproject;

public class Book {

    private static int counter = 1;

    private int bookId;
    private String bookTitle;
    private String author;
    private String year;
    private boolean isAvailable;

    public Book( String bookTitle, String author, String year, boolean isAvailable) {
        this.bookId = counter++ ;
        this.bookTitle = bookTitle;
        this.author = author;
        this.year = year;
        this.isAvailable = true;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public static void setCounter(int counter) {
        Book.counter = counter;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book id: " + bookId +
                "Book title: " + bookTitle +
                " , Author: " + author +
                " , Year: " + year +
                " , Inventory status: " + isAvailable;
    }
}
