import java.util.Scanner;

public class E6_2_c {   //cumulative totals
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers do you want the sequence to have?");
        int count = scanner.nextInt();
        int cumulativeTotal = 0;

        System.out.println("Please enter the numbers:");
        for (int i = 0; i < count; i++) {
            int number = scanner.nextInt();

            cumulativeTotal += number;
            System.out.println("Cumulative total:" +cumulativeTotal);


        }
    }
}