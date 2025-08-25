package ap.projects.finalproject;

public class Employee {

    private String name;
    private String employeeId;
    private String username;
    private String password;

    public Employee(String name, String employeeId, String username, String password) {
        this.name = name;
        this.employeeId = employeeId;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " , Employee ID: " + employeeId +
                " , Username: " + username;
    }
}
