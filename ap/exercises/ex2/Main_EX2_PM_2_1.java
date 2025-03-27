package ap.exercises.ex2;
import java.util.Scanner;
import java.util.Random;

public class Main_EX2_PM_2_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Please enter side size(k):");
        int k = scanner.nextInt();
        char[][] hollowSquare = new char[k + 2][k + 2];

        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                hollowSquare[i][j] = ' ';
            }
        }

        for (int i = 0; i < k + 2; i++) {
            hollowSquare[0][i] = '*'; //up line
            hollowSquare[k + 1][i] = '*'; //down line
            hollowSquare[i][0] = '*'; //left line
            hollowSquare[i][k + 1] = '*'; //right line

        }

        int dotEater = random.nextInt(4);
        switch (dotEater) {
            case 0:
                hollowSquare[1][1] = 'X';
                break;
            case 1:
                hollowSquare[1][k] = 'X';
                break;
            case 2:
                hollowSquare[k][1] = 'X';
                break;
            case 3:
                hollowSquare[k][k] = 'X';
                break;
        }

        int emptySpaces = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (hollowSquare[i][j] == ' ') {
                    emptySpaces++;
                }
            }
        }

        int c;
        do {
            System.out.println("Please enter a number(c) for the number of dots in game:");
            c = scanner.nextInt();
            if (c > emptySpaces) {
                System.out.println("Error! Please enter another number.");
            }
        } while (c > emptySpaces);

        int dotsPlace = 0;
        while (c > dotsPlace) {
            int i = random.nextInt(k);
            int j = random.nextInt(k);

            if (hollowSquare[i][j] == ' ') {
                hollowSquare[i][j] = '.';
                dotsPlace++;
            }
        }


        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                System.out.print(hollowSquare[i][j] + " ");
            }
            System.out.println();
        }

        while (true) {

            System.out.println("Please Select a direction for movement(w=Up , a=Left , s=Down , d=Right , q=Exit):");
            char direction = scanner.next().charAt(0);
            if (direction == 'q') {
                System.out.println("Exit");
                break;
            }


            //find X current position
            int xRow = -1;
            int xCol = -1;
            outerLoop:
            for (int i = 0; i < k + 2; i++) {
                for (int j = 0; j < k + 2; j++) {
                    if (hollowSquare[i][j] == 'X') {
                        xRow = i;
                        xCol = j;
                        break outerLoop;
                    }
                }
            }


            int newRow = xRow;
            int newCol = xCol;

            switch (direction) {
                case 'w':
                    newRow--;
                    break;  //up
                case 'a':
                    newCol--;
                    break;  //left
                case 's':
                    newRow++;
                    break;  //down
                case 'd':
                    newCol++;
                    break;  //right
                default:
                    System.out.println("WARNING!!");
                    continue;

            }


            if (hollowSquare[newRow][newCol] == '*') {
                System.out.println("Hitting the game wall!");
            } else {
                hollowSquare[xRow][xCol] = ' ';
                hollowSquare[newRow][newCol] = 'X';
                System.out.println("Moved successfully!");
                for (int i = 0; i < k + 2; i++) {
                    for (int j = 0; j < k + 2; j++) {
                        System.out.print(hollowSquare[i][j] + " ");
                    }
                    System.out.println();
                }
            }

        }

    }
}

