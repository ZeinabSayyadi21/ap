import java.util.Scanner;

public class FloatingPointNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the number:");
        double number = scanner.nextDouble();

        if (number == 0) {
            System.out.println("zero");
        }  else {
            if ( number > 0) {
                System.out.println("Positive");
            }   else {
                System.out.println("Negative");
            }

            if ( number > 0 && number < 1  ||  number < 0 && number > -1 ) {
                System.out.println("Small");
            }  else if ( Math.abs(number) > 1000000) {
                System.out.println("Large");
            }  else {
                System.out.println("(Not large and not small!)");
            }

        }
        scanner.close();
    }
}
