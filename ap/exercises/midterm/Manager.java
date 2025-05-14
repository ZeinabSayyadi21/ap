package ap.exercises.midterm;

public class Manager {

   private String firstName;
   private String lastName;
   private String educationLevel;

    public Manager(String firstName, String lastName, String educationLevel) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.educationLevel = educationLevel;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEducationLevel() {
        return educationLevel;
    }

    public void addBookToLibrary(Library library, Book book) {
        library.addBook(book);
    }

    public String toString() {
        return "Manager: " +firstName+ "," +lastName+ "," +educationLevel;
    }
}
