package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {

    private List<Book> books;
    private InputHandler input = new InputHandler();

    public BookManager() {

        books = new ArrayList<>();
        books = DatabaseManager.loadBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {

        books.add(book);
        DatabaseManager.saveBooks(books);
    }

    public List<Book> searchBooks(String bookTitle, String author, String year) {
        return books.stream()
                .filter(book -> (bookTitle == null || bookTitle.isEmpty() ||
                        book.getBookTitle().toLowerCase().contains(bookTitle.toLowerCase())))
                .filter(book -> (author == null || author.isEmpty() ||
                        book.getAuthor().toLowerCase().contains(author.toLowerCase())))
                .filter(book -> (year == null || year.isEmpty() || book.getYear().equals(year)))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getBookTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void editBook(Book book) {
        System.out.println("Editing book: " +book.getBookTitle());

        String newBookTitle = input.getString("Please enter new title or skip it: ");
        if (!newBookTitle.trim().isEmpty())
            book.setBookTitle(newBookTitle);

        String newAuthor = input.getString("Please enter new author or skip it: ");
        if (!newAuthor.trim().isEmpty())
            book.setAuthor(newAuthor);

        String newYear = input.getString("Please enter new year or skip it: ");
        if (!newYear.trim().isEmpty())
            book.setYear(newYear);

        System.out.println("Book information updated successfully!");
        DatabaseManager.saveBooks(books);
    }

    public void printAllBooks() {
        for (Book book : books) {         //for test code
            System.out.println(book);
        }
    }
}
