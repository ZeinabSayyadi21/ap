package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private List<Student> students;

    public StudentManager() {
        students = FileManager.loadStudents();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        if (isUsernameTaken(username)) {
            System.out.println("This username already exists. Please choose a different username.");
            return;
        }

        if (isStudentIdTaken(studentId)) {
            System.out.println("This student id already exists. Please check again.");
            return;
        }

        Student newStudent = new Student(name, studentId, username, password);
        students.add(newStudent);
        System.out.println("Student registration completed successfully.");
        FileManager.saveStudents(students);
    }

   public Student authenticateStudent(String username, String password) {
        return students.stream()
                .filter(s -> s.getUsername().equals(username) && s.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void toggleStudentStatus(String studentId) {
        Student student = students.stream()
                .filter(student1 -> student1.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (student != null) {
            student.setActive(!student.isActive());
            System.out.println("Student: " +student.getName()+ " is " +  (student.isActive() ? " Active " : " Inactive "));

            FileManager.saveStudents(students);
        } else {
            System.out.println("Student not found!");
        }
    }

    public void displayStudents() {
        System.out.println("\n--- List of Registered Students ---");

        if (students.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }

        for (Student student : students) {
            System.out.println(student.getUserInfo() + " , Status: " +
                    (student.isActive() ? "Active" : "Inactive"));
        }
    }


    private boolean isUsernameTaken(String username) {
        return students.stream().anyMatch(s -> s.getUsername().equals(username));
    }

    private boolean isStudentIdTaken(String studentId) {
        return students.stream().anyMatch(s -> s.getStudentId().equals(studentId));
    }

    public int getStudentCount() {
        return students.size();
    }
}
