package ap.projects.finalproject;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private List<Employee> employees;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
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


    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Employee added successfully: " +employee.getName());
    }

    public void removeEmployee(String username) {
        employees.removeIf(employee -> employee.getUsername().equals(username));
    }
}
