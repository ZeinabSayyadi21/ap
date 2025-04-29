package ap.exercises.ex4;

import java.util.Scanner;

public class Main_EX4_E3_12_tester {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the Name:");
        String name = scanner.nextLine();

        System.out.println("Please enter the current salary:");
        double salary = scanner.nextDouble();

        Main_EX4_E3_12 employee = new Main_EX4_E3_12(name,salary);

        System.out.println("Please enter the raise percentage:");
        double percent = scanner.nextDouble();

        System.out.println("Employee name:" +employee.getName());
        System.out.println("Current salary:" +employee.getSalary());

        employee.raiseSalary(percent);

        System.out.println("After raise:");
        System.out.println("Salary:" +employee.getSalary());
        scanner.close();
    }
}
