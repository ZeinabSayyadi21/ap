package ap.exercises.midterm;

import java.time.LocalDate;

public class Student {

   private String firstName;
   private String lastName;
   private String id;
   private String major;
   private LocalDate membershipDate;

    public Student(String firstName, String lastName, String id, String major, LocalDate membershipDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.major = major;
        this.membershipDate = membershipDate;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getId() {
        return id;
    }
    public String getMajor() {
        return major;
    }
    public LocalDate getMembershipDate() {
        return membershipDate;
    }


    public String toString() {
        return "Student: " +firstName+ "," +lastName+ "," +id+ "," +major+ "," +membershipDate;
    }
}
