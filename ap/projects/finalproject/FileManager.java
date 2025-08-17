package ap.projects.finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private static final String STUDENT_FILE = "students.txt";

    public static void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE))) {
            for (Student student : students) {
                writer.println(student.getName() +","+ student.getStudentId() +","+ student.getUsername()
                +","+ student.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " +e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(STUDENT_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 4) {
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no students file!");
        }
        return students;
    }
}
