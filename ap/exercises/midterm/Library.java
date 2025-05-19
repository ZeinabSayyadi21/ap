package ap.exercises.midterm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Library {

    private String name;
    private ArrayList<Book> books;
    private ArrayList<Student> students;
    private ArrayList<Librarian> librarians;
    private ArrayList<Loan> loans = new ArrayList<>();
    private Manager manager;
    private ArrayList<Loan> pendingLoans;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.students = new ArrayList<>();
        this.librarians = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }


    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public void removeLibrarian(String employeeId) {
        for (int i = 0; i < librarians.size(); i++) {
            Librarian librarian = librarians.get(i);
            if (librarian.getEmployeeId().equals(employeeId)) {
                librarians.remove(i);
                System.out.println("Librarian removed successfully!");
                return;
            }
        }
        System.out.println("No librarian with this employee ID was found.");
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Book> searchBook(String title) {
        ArrayList<Book> desiredBook = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookTitle().toLowerCase().contains(title.toLowerCase())) {
                desiredBook.add(book);
            }
        }
        return desiredBook;
    }

    public Student loginStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public Librarian loginLibrarian(String employeeId) {
        for (Librarian librarian : librarians) {
            if (librarian.getEmployeeId().equals(employeeId)) {
                return librarian;
            }
        }
        return null;
    }

    public boolean loginManager(String fullName) {
        return manager != null &&
                (manager.getFirstName() + " " + manager.getLastName()).equalsIgnoreCase(fullName);

    }

    public ArrayList<Book> searchBookByTitle(String bookTitle) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookTitle().toLowerCase().contains(bookTitle.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public Librarian getRandomLibrarian() {
        if (librarians.isEmpty())
            return null;
        Random random = new Random();
        return librarians.get(random.nextInt(librarians.size()));

    }

    public void addLoans(Loan loan) {
        loans.add(loan);
    }

    public void addPendingLoans(Loan loan) {
        pendingLoans.add(loan);
    }

    public ArrayList<Loan> getLateReturnBooks() {
        ArrayList<Loan> lateLoanBooks = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.isReturned()) {
                LocalDate dueDate = loan.getDeliveredDate().plusDays(21);
                if (loan.getReturnDate().isAfter(dueDate)) {
                    lateLoanBooks.add(loan);
                }
            }
        }
        return lateLoanBooks;
    }

    public Book findBookByTitle(String bookTitle) {
        for (Book book : books) {
            if (book.getBookTitle().equalsIgnoreCase(bookTitle)) {
                return book;
            }
        }
        return null;
    }

    public Student findStudentByFullName(String fullName) {
        for (Student student : students) {
            String studentFullName = student.getFirstName() +" "+ student.getLastName();
            if (studentFullName.equalsIgnoreCase(fullName.trim())) {
                return student;
            }
        }
        return null;
    }

    public Librarian findLibrarianByFullName(String fullName) {
        for (Librarian librarian : librarians) {
            String librarianFullName = librarian.getFirstName() +" "+ librarian.getLastName();
            if (librarianFullName.equalsIgnoreCase(fullName.trim())) {
                return librarian;
            }
        }
        return null;
    }
}










