import java.util.Scanner;

public class E6_17 {   //filled and hollow square
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a number for side length:");
        int sideLength = scanner.nextInt();

        for (int i = 1; i <= sideLength; i++) {    //filled square
            for (int j = 1; j <= sideLength; j++) {
                System.out.print("*");
            }
            System.out.print(" ");  //space

            for (int j = 1; j <= sideLength; j++) {   //hollow square
                if (i == 1 || i == sideLength || j == 1 || j == sideLength) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        scanner.close();
    }
}
