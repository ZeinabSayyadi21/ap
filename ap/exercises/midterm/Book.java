package ap.exercises.midterm;

public class Book {

   private String bookTitle;
   private String author;
   private int pages;
   private int publicationYear;
   private boolean isBorrowed;

    public Book(String bookTitle, String author, int pages, int publicationYear) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publicationYear = publicationYear;
        if (pages > 0) {
            this.pages = pages;
        } else {
            System.out.println("Pages can not be negative!");
        }
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }
    public int getPages() {
        return pages;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public String toString() {
        return "Book: " +bookTitle+ "," +author+ "," +pages+ "," +publicationYear;
    }

   /*public boolean matchTitle(String query) {
        return bookTitle.toLowerCase().contains(query.toLowerCase());
    }

    public void borrow() {
        if (! isBorrowed) {
            isBorrowed = true;
        } else {
            System.out.println("This book is already borrowed!");
        }
    }

    public void returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
        } else {
            System.out.println("This book wasn't borrowed!");
        }
    }*/


}
