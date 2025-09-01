package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookManager {

    private List<Book> books;

    public BookManager() {

        books = new ArrayList<>();
        books = FileManager.loadBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {

        books.add(book);
        FileManager.saveBooks(books);
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Editing book: " +book.getBookTitle());

        System.out.println("Please enter new title or skip it: ");
        String newBookTitle = scanner.nextLine();
        if (!newBookTitle.trim().isEmpty())
            book.setBookTitle(newBookTitle);

        System.out.println("Please enter new author or skip it: ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.trim().isEmpty())
            book.setAuthor(newAuthor);

        System.out.println("Please enter new year or skip it: ");
        String newYear = scanner.nextLine();
        if (!newYear.trim().isEmpty())
            book.setYear(newYear);

        System.out.println("Book information updated successfully!");
        FileManager.saveBooks(books);
    }

    public void printAllBooks() {
        for (Book book : books) {         //for test code
            System.out.println(book);
        }
    }
}
