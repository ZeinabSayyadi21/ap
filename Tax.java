import java.util.Scanner;

public class Tax {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the income:");
        double income = scanner.nextDouble();
        double tax = 0;

        if ( income <= 50000) {
            tax = income * 0.01;
        } else if (income > 50000 && income <= 75000) {
            tax = income * 0.02;
        } else if (income > 75000 && income <= 100000) {
            tax = income * 0.03;
        } else if (income > 100000 && income <= 250000) {
            tax = income * 0.04;
        } else if (income > 250000 && income <= 500000) {
            tax = income * 0.05;
        } else if (income > 500000 ) {
            tax = income * 0.06;
        }
        System.out.printf("your income tax is:$%.2f" , tax );

        scanner.close();
    }
}
