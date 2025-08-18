package ap.projects.finalproject;

public class Book {

    private String bookTitle;
    private String author;
    private String year;
    private boolean isAvailable;

    public Book(String bookTitle, String author, String year, boolean isAvailable) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.year = year;
        this.isAvailable = true;
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

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book title: " + bookTitle +
                " | Author: " + author +
                " | Year: " + year +
                " | Inventory status: " + isAvailable;
    }
}
