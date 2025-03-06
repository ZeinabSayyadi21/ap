import java.util.Scanner;

public class SumOddDigits {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a number:");
        int number = scanner.nextInt();
        int sum = 0;
        int digit;
        while ( number != 0) {
            digit = number % 10;  //Separating digits
            if ( number % 2 ==1) {  //Checking the numbers are odd or not
                sum += digit;
            }
            number /= 10;   //Separate the last digit from number
        }
        System.out.println("Sum odd digits:" +sum);
        scanner.close();
    }
}
