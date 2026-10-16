package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private List<Employee> employees;

    public EmployeeManager() {
       employees = DatabaseManager.loadEmployees();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findByUsername(String username) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return employee;
            }
        }
        return null;
    }

    public Employee login(String username, String password) {

        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }


    public void changePassword(Employee employee, String oldPass, String newPass) {
        if (! employee.getPassword().equals(oldPass)) {
            System.out.println("Your password is invalid! Try again.");
            return;
        }
        employee.setPassword(newPass);
        DatabaseManager.saveEmployees(employees);
        System.out.println("Password changed successfully!");
    }


    public void addEmployee(Employee employee) {
        employees.add(employee);
        DatabaseManager.saveEmployees(employees);
        System.out.println("Employee added successfully: " +employee.getName());
    }

    public void removeEmployee(String username) {
        boolean removed = employees.removeIf(employee -> employee.getUsername().equals(username));
        if (removed) {
            DatabaseManager.saveEmployees(employees);
            System.out.println("Employee removed successfully!");
        } else {
            System.out.println("No employee found with this username.");
        }
    }

    public Employee getEmployeeById(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }
}
