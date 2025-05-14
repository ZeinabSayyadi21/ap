package ap.exercises.midterm;

public class Librarian {

   private String firstName;
   private String lastName;
   private String employeeId;

    public Librarian(String firstName, String lastName, String employeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmployeeId(){
        return employeeId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String toString() {
        return "Librarian: " +firstName+ "," +lastName+ "," +employeeId;
    }
}
