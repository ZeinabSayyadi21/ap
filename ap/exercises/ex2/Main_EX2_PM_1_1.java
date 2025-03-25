package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter k:");
        int k = scanner.nextInt();

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= k; j++) {
                if (i == 1 || i == k || j == 1 || j == k) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
