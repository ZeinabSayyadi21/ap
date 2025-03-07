import java.util.Scanner;

public class E6_2_d {  //Adjacent duplicates
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers do you want the sequence to have?");
        int count = scanner.nextInt();

        System.out.println("Please enter the numbers:");
        int previousNumber = scanner.nextInt();
        for (int i = 1; i < count; i++) {
            int currentNumber = scanner.nextInt();

            if (currentNumber == previousNumber) {
                System.out.println("Adjacent duplicates:" + currentNumber);
            }
            previousNumber = currentNumber;
        }

    }
}