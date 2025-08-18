package ap.projects.finalproject;

public class LibrarySystem {

    private StudentManager studentManager;
    private MenuHandler menuHandler;
    private BookManager bookManager;

    public LibrarySystem() {
        this.studentManager = new StudentManager();
        this.bookManager = new BookManager();
        this.menuHandler = new MenuHandler(this, this.bookManager);
    }

    public int getStudentCount() {
        return this.studentManager.getStudentCount();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
    }

    public Student authenticateStudent(String username, String password) {
        return studentManager.authenticateStudent(username, password);
    }

    public void editStudentInformation(Student student) {
        System.out.println("Not implemented.");
    }

    public void borrowBook(Student student) {
        System.out.println("Not implemented.");
    }

    public void returnBook(Student student) {
        System.out.println("Not implemented.");
    }

    public void displayAvailableBooks() {
        System.out.println("Not implemented.");
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();


        system.bookManager.addBook(new Book("Java programming", "Mr.Smith", "2020", true));
        system.bookManager.addBook(new Book("Big Java", "Ali Ahmadi", "2012", true));
        system.bookManager.addBook(new Book("python programming", "Ali Rad","2006", true ));

        system.start();
    }
}
