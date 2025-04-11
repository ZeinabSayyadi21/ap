package ap.exercises.ex2;

import java.util.Random;

public class PacmanEngine {

    private char[][] hollowSquare;
    private int k;
    private int c;
    private int score;
    private int uneatenDots;
    private long startTime;
    private Random random;


    public PacmanEngine(int k, int c) {
        this.k = k;
        this.c = c;
        this.random = new Random();
        this.startTime = System.currentTimeMillis();
        initializeGame();
    }

    public void initializeGame() {

        this.hollowSquare = new char[k + 2][k + 2];

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
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= k; j++) {
                if (hollowSquare[i][j] == ' ') {
                    emptySpaces++;
                }
            }
        }

        this.c = Math.min( c, emptySpaces );
        this.uneatenDots = c;

        int dotsPlace = 0;
        while (c > dotsPlace) {
            int i = random.nextInt(k) + 1;
            int j = random.nextInt(k) + 1;

            if (hollowSquare[i][j] == ' ') {
                hollowSquare[i][j] = '.';
                dotsPlace++;
            }
        }
    }

    public void move(int direction) {

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
            case 0 :
                newRow--;
                break;  //up
            case 1 :
                newCol--;
                break;  //left
            case 2 :
                newRow++;
                break;  //down
            case 3 :
                newCol++;
                break;  //right
            default:
                System.out.println("WARNING!!");
                return;

        }

        if (hollowSquare[newRow][newCol] == '*') {
            System.out.println("Hitting the game wall!");
            return;
        }

        hollowSquare[xRow][xCol] = ' ';
        if (hollowSquare[newRow][newCol] == '.') {
            score++;
            uneatenDots--;
        }
            hollowSquare[newRow][newCol] = 'X';
            System.out.println("Moved successfully!");
        }

        public void printMatrix() {

            for (int i = 0; i < k + 2; i++) {
                for (int j = 0; j < k + 2; j++) {
                    System.out.print(hollowSquare[i][j] + " ");
                }
                System.out.println();
            }
    }

    public void printScore() {
        System.out.println("\nYour current score:" + score );
    }

    public void printRemainTime() {

        long currentTime  = System.currentTimeMillis();
        long timeElapsed = currentTime  - startTime;
        System.out.println("Your playing time in this game:" + timeElapsed + " Milli seconds");
    }

}
