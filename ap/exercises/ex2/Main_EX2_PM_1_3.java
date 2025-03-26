package ap.exercises.ex2;

import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_1_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

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

        int emptySpaces = 0;
        for (int i=0 ; i<k ; i++) {
            for (int j=0 ; j<k ; j++) {
                if ( hollowSquare[i][j] == ' '){
                    emptySpaces++ ;
                }
            }
        }

        int c ;
        do {
            System.out.println("Please enter a number(c) for the number of dots in game:");
            c = scanner.nextInt();
            if ( c > emptySpaces) {
                System.out.println("Error! Please enter another number.");
            }
        } while ( c > emptySpaces);

        int dotsPlace = 0;
        while (c > dotsPlace){
            int i = random.nextInt(k);
            int j = random.nextInt(k);

            if ( hollowSquare[i][j] == ' ') {
                hollowSquare[i][j] = '.';
                dotsPlace++;
            }
        }



        for (int i=0 ; i<k+2 ; i++) {
            for (int j=0 ; j<k+2 ; j++) {
                System.out.print(hollowSquare[i][j] );
            }
            System.out.println();
        }

    }
}



