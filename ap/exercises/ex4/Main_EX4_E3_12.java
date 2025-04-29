package ap.exercises.ex4;


public class Main_EX4_E3_12 {

    private String name;
    private double salary;

    public Main_EX4_E3_12( String employeeName , double currentSalary) {
        this.name = employeeName;
        this.salary = currentSalary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void raiseSalary(double percent) {
        double raise = (percent * salary) / 100;
        salary += raise;
    }
}
