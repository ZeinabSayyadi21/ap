import java.util.Scanner;

public class E6_13 {  //Print binary digits

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the number:");
        int number = scanner.nextInt();

        System.out.println("Binary digits of the number are:" );
        while (number != 0) {
            int remainder = number % 2;
            System.out.println(remainder);
            number /= 2;
        }
    }
}
