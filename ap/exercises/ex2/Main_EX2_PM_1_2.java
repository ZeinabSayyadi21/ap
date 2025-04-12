package ap.exercises.ex2;

import java.util.Scanner;

public class Main_EX2_PM_1_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter side size(k):");
        int k = scanner.nextInt();
        char[][] hollowSquare = new char[k+2][k+2];

        for (int i=0 ; i<k+2 ; i++){
            for (int j=0 ; j<k+2 ; j++){
                hollowSquare[i][j] = ' ';
            }
        }

        for (int i=0 ; i<k+2 ; i++) {
            hollowSquare[0][i] = '*'; //up line
            hollowSquare[k+1][i] = '*'; //down line
            hollowSquare[i][0] = '*'; //left line
            hollowSquare[i][k+1] = '*'; //right line

        }


        for (int i=0 ; i<k+2 ; i++) {
            for (int j=0 ; j<k+2 ; j++) {
                System.out.print(hollowSquare[i][j] );
            }
            System.out.println();
        }

    }
}
