package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {

    private List<Book> books;

    public BookManager() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
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
                .filter(book -> book.getBookTitle().contains(title))
                .collect(Collectors.toList());
    }

    public void printAllBooks() {
        for (Book book : books) {         //for test code
            System.out.println(book);
        }
    }
}
