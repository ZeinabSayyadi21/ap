package ap.exercises.midterm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class InputProcessor {

    private final Scanner scanner;
    private final Library library;

    public InputProcessor(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);

    }

    public int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public boolean loginManager() {
        String fullName = readString("Please enter your full name:");
        return library.loginManager(fullName);
    }

    public Librarian loginLibrarian() {
        String employeeId = readString("Please enter your employee id:");
        return library.loginLibrarian(employeeId);
    }

    public Student loginOrRegisterStudent() {
        Student student = loginStudent();
        if (student == null) {
            String answer = readString("Student not found! Do you want to register?(y/n)");
            if (answer.equalsIgnoreCase("y")) {
                return registerStudent();
            }
        }
        return student;

    }

    public Student loginStudent() {
        String id = readString("Please enter your id:");
        Student student = library.loginStudent(id);
        if (student != null) {
            System.out.println("Welcome to Znu Central Library!");
        } else {
            System.out.println("Student not found!");
        }
        return student;
    }

    public Student registerStudent() {
        String firstName = readString("Enter your first name:");
        String lastName = readString("Enter your last name:");
        String id = readString("Enter your id:");
        String major = readString("Enter your major:");
        LocalDate membershipDate = LocalDate.now();

        Student student = new Student(firstName, lastName, id, major, membershipDate);
        library.registerStudent(student);
        System.out.println("Student registered successfully!");
        return student;
    }

    public void addLibrarian() {
        String firstName = readString("Enter your first name:");
        String lastName = readString("Enter your last name:");
        String employeeId = readString("Enter your employee id:");

        Librarian librarian = new Librarian(firstName, lastName, employeeId);
        library.addLibrarian(librarian);
        System.out.println("Librarian added successfully!");
    }

    public void addBook() {
        String bookTitle = readString("Enter book title:");
        String author = readString("Enter author:");
        int pages = readInt("Enter number of pages:");
        int publicationYear = readInt("Enter publication Year:");

        Book book = new Book(bookTitle, author, pages, publicationYear);
        library.addBook(book);
        System.out.println("Book added successfully!");
    }

    public void searchBookByTitle() {
        String search = readString("Please enter book title for search:");
        ArrayList<Book> result = library.searchBookByTitle(search);

        if (result.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Founded books:");
            for (Book book : result) {
                System.out.println(book);
            }
        }
    }

    public void editLibrarianInfo(Librarian librarian) {

        System.out.println("Current first name:" + librarian.getFirstName());
        String newFirstName = readString("Please enter new first name:");
        if (!newFirstName.trim().isEmpty()) {
            librarian.setFirstName(newFirstName);
        }

        System.out.println("Current last name:" + librarian.getLastName());
        String newLastName = readString("Please enter new last name:");
        if (!newLastName.trim().isEmpty()) {
            librarian.setLastName(newLastName);
        }

        System.out.println("Current employee id:" + librarian.getEmployeeId());
        String newEmployeeId = readString("Please enter new employee id:");
        if (!newEmployeeId.trim().isEmpty()) {
            librarian.setEmployeeId(newEmployeeId);
        }

        System.out.println("Librarian's personal information updated!");
    }

    public void showAllBooks() {
        ArrayList<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("Books not found!");
        } else {
            System.out.println("Available books list:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public void showAllStudents() {
        ArrayList<Student> students = library.getStudents();
        if (students.isEmpty()) {
            System.out.println("Students not found!");
        } else {
            System.out.println("Registered students list:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public void showAllLibrarians() {
        ArrayList<Librarian> librarians = library.getLibrarians();
        if (librarians.isEmpty()) {
            System.out.println("Librarians not found!");
        } else {
            System.out.println("Librarians list:");
            for (Librarian librarian : librarians) {
                System.out.println(librarian);
            }
        }
    }

    public void showAllLoans() {
        ArrayList<Loan> loans = library.getLoans();
        if (loans.isEmpty()) {
            System.out.println("Loans not found!");
        } else {
            System.out.println("Loans list:");
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }

    public void requestForLoanBook(Student student) {
        String bookTitle = readString("Please enter the title of book that you want borrow:");
        ArrayList<Book> books = library.searchBookByTitle(bookTitle);
        if (books.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            Book book = books.get(0);
            Librarian randomLibrarian = library.getRandomLibrarian();
            if (randomLibrarian == null) {
                System.out.println("Librarian isn't available!");
                return;
            }
            LocalDate now = LocalDate.now();
            LocalDate dueDate = now.plusDays(21);
            Loan loan = new Loan(book, student, null, now, dueDate, false);
            library.addLoans(loan);
            System.out.println("Loans in system:" + library.getLoans().size());
            System.out.println("Your request has been submitted. Please wait for the librarian's approval.");

        }
    }

    public void confirmBookRequest(Librarian librarian) {
        ArrayList<Loan> pendingLoans = new ArrayList<>();

        for (Loan loan : library.getLoans()) {
            if (loan.getDeliveredBy() == null) {
                pendingLoans.add(loan);
            }
        }
        if (pendingLoans.isEmpty()) {
            System.out.println("There is no book for confirm!");
            return;
        }
        for (Loan loan : pendingLoans) {
            System.out.println("Loan request for book:" + loan.getBook().getBookTitle() + "By:"
                    + loan.getStudent().getFirstName() + " " + loan.getStudent().getLastName());
            String confirm = readString("Do you want to confirm this request?(yes/no)");
            if (confirm.equalsIgnoreCase("yes")) {
            Librarian randomLibrarian = library.getRandomLibrarian();
            loan.setDeliveredBy(randomLibrarian);
            System.out.println("Book request confirmed successfully by:" + randomLibrarian.getFirstName()
                    + " " + randomLibrarian.getLastName());
        }
    }
                }



    public void unReturnBooks(Student student) {
        for (Loan loan : library.getLoans()) {
            if (loan.getStudent().equals(student) && !loan.isReturnDate()) {
                System.out.println("You haven't returned the book " + loan.getBook().getBookTitle() + " yet.");
            }
        }
    }

    public void requestForReturnBooks(Student student) {
        ArrayList<Loan> unreturnedBooks = new ArrayList<>();
        int index = 1;
        System.out.println("Total loans in library: " + library.getLoans().size());
        for (Loan loan : library.getLoans()) {
            if (loan.getStudent().equals(student) && !loan.isReturned() && loan.isApproved()) {
                System.out.println(index + "." + loan.getBook().getBookTitle());
                unreturnedBooks.add(loan);
                index++;
            }
        }
        if (unreturnedBooks.isEmpty()) {
            System.out.println("You haven't any unreturned book.");
            return;
        }
        int choice = readInt("Please enter the number of book that you want to return:");
        if (choice < 1 || choice > unreturnedBooks.size()) {
            System.out.println("Invalid input!Try again.");
            return;
        }
        Loan input = unreturnedBooks.get(choice - 1);
        input.setReturned(true);
        input.setReturnDate(LocalDate.now());
        System.out.println("Your return request has been submitted. Please wait for the librarian's approval.");
    }

    public void confirmReturnBookRequest(Librarian librarian) {
        ArrayList<Loan> loans = library.getLoans();
        ArrayList<Loan> pendingBooks = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getReturnDate() != null && loan.getReturnConfirmedBy() == null) {
                pendingBooks.add(loan);
            }
        }

        if (pendingBooks.isEmpty()) {
            System.out.println("You haven't any unconfirmed book.");
            return;
        }
        int index = 1;
        for (Loan loan : pendingBooks) {
            System.out.println(index + "." + loan.getBook().getBookTitle() + "By: " + loan.getStudent().getFirstName() +
                    " " + loan.getStudent().getLastName());
            index++;
        }
        int choice = readInt("Please enter the number of book that you want to confirm:");
        if (choice < 1 || choice > pendingBooks.size()) {
            System.out.println("Invalid input!Try again.");
            return;
        }
        Loan input = pendingBooks.get(choice - 1);
        Librarian randomLibrarian = library.getRandomLibrarian();
        input.setReturnConfirmedBy(randomLibrarian);
        input.setReceiveBy(randomLibrarian);
        System.out.println("Return book confirmed by " +randomLibrarian.getFirstName() +" "+ randomLibrarian.getLastName()+
        " successfully!");
    }

    public void showLateReturnBooks() {
        ArrayList<Loan> lateLoanBooks = library.getLateReturnBooks();
        if (lateLoanBooks.isEmpty()) {
            System.out.println("There is no late return book to show.");
            return;
        }
        for (Loan loan : lateLoanBooks) {
            System.out.println("Name of the book that was delivered late: " +loan.getBook().getBookTitle());
            System.out.println("By: " +loan.getStudent().getFirstName() +" "+ loan.getStudent().getLastName());
            System.out.println("Return date: " +loan.getReturnDate());
            System.out.println("Number of days the student returned the book late: " +loan.getDaysLate());
        }
    }

    public void viewLibrarianStatistics() {
        for (Librarian librarian : library.getLibrarians()) {
            int deliveredCount = 0;
            int receivedCount = 0;
            for (Loan loan : library.getLoans()) {
                if (loan.getDeliveredBy() != null && loan.getDeliveredBy().equals(librarian)) {
                    deliveredCount++;
                }
                if (loan.getReceiveBy() != null && loan.getReceiveBy().equals(librarian)) {
                    receivedCount++;
                }
            }
            System.out.println("Librarian name: " +librarian.getFirstName() +" "+ librarian.getLastName());
            System.out.println("number of delivers: " +deliveredCount);
            System.out.println("number of receives: " +receivedCount);
            System.out.println();
        }
    }

    public void top10BooksLastYear() {
        LocalDate year = LocalDate.now().minusYears(1);
        ArrayList<Book> books = library.getBooks();
        ArrayList<Loan> loans = library.getLoans();

        ArrayList<BookCount> bookCounts = new ArrayList<>();

        for (Book book : books) {
            int count = 0;
            for (Loan loan : loans) {
                if (loan.getBook().equals(book) && loan.getDeliveredDate().isAfter(year)) {
                    count++;
                }
            }
            if (count > 0) {
                bookCounts.add(new BookCount(book,count));
            }
        }
        bookCounts.sort((b1 , b2) -> b2.count - b1.count);
        System.out.println("Top 10 loan books since last year: ");
        for (int i = 0; i < Math.min(10 , bookCounts.size()) ; i++) {
            BookCount bookCount = bookCounts.get(i);
            System.out.println((i+1) + "." + bookCount.book.getBookTitle() + "\n Number of loans: " +bookCount.count);
        }
    }

    class BookCount {
        Book book;
        int count;

        public BookCount(Book book, int count) {
            this.book = book;
            this.count = count;
        }
    }
}








