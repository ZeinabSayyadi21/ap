import java.util.Scanner;

public class E6_18 {   //Filled diamond
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter an odd number:");
        int number = scanner.nextInt();
        int space = number / 2;

        for (int i = 1; i <= number; i += 2) {   //top part of diamond
            for (int j = 1; j <= space; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
            space--;

        }
        space = 1;  //bottom part of diamond
        for (int i = number - 2; i >= 1; i -= 2) {
            for (int j = 1; j <= space; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
            space++;
        }
        scanner.close();

    }
}
