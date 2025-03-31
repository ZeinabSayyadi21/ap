package ap.exercises.ex2;
import java.io.*;
import java.util.Scanner;
import java.util.Random;


public class Main_EX2_PM_2_3 {
    public static final String SAVE_FILE = "game.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        GameState saveState = loadGame();

        int k , c , score , uneatenDots;
        char[][] hollowSquare;

        if (saveState != null) {
            System.out.println("Loaded save Game!!");
            k = saveState.k;
            c = saveState.c;
            score = saveState.score;
            uneatenDots = saveState.uneatenDots;
            hollowSquare = saveState.hollowSquare;
            startTime = saveState.startTime;

            for (int i = 0; i < k + 2; i++) {
                for (int j = 0; j < k + 2; j++) {
                    System.out.print(hollowSquare[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("\nYour current score:" + score + "\nUneaten dots:" + uneatenDots);

        } else {
            score = 0;
            System.out.println("New game started!");
            startTime = System.currentTimeMillis();



        System.out.println("Please enter side size(k):");
        k = scanner.nextInt();
        hollowSquare = new char[k + 2][k + 2];

        for (int i = 0; i < k + 2; i++) {
            for (int j = 0; j < k + 2; j++) {
                hollowSquare[i][j] = ' ';
            }
        }

        for ( int i = 0; i < k + 2; i++) {
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

        int emptySpaces = (k*k) - 1;


        do {
            System.out.println("Please enter a number(c) for the number of dots in game:");
            c = scanner.nextInt();
            if (c > emptySpaces) {
                System.out.println("Error! Please enter another number.");
            }
        } while (c > emptySpaces);

        uneatenDots = c;
        int dotsPlace = 0;
        while ( dotsPlace < c) {
            int i = random.nextInt(k) + 1;
            int j = random.nextInt(k) + 1;
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
            }

        while (true) {

            System.out.println("Please Select a direction for movement(w=Up , a=Left , s=Down , d=Right , q=Exit , h=Save):");
            char direction = scanner.next().charAt(0);
            if (direction == 'q') {
                System.out.println("Do you want to save the game before exiting? Enter y or n." );
                char choice = scanner.next().charAt(0);

                if (choice == 'y') {
                    saveGame(k, c, score, uneatenDots, hollowSquare, startTime );
                }
                finishGame( score , startTime);
                break;
            }

            if ( direction == 'h') {
                saveGame(k, c, score, uneatenDots, hollowSquare, startTime);
                System.out.println("Game saved successfully!");
                continue;
            }


            if (uneatenDots == 0) {
                finishGame(score , startTime);
                new File(SAVE_FILE).delete();
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

                if (hollowSquare[newRow][newCol] == '.') {
                    score++;
                    uneatenDots--;
                    System.out.println("\nYour current score:" + score + "\nUneaten dots:" + uneatenDots);
                }


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

    private static class GameState {
        int k, c, score , uneatenDots;
        char[][] hollowSquare;
        long startTime;
    }

    private static GameState loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            GameState state = new GameState();
            state.k = ois.readInt();
            state.c = ois.readInt();
            state.score = ois.readInt();
            state.uneatenDots = ois.readInt();
            state.startTime = ois.readLong();

            int size = ois.readInt();
            state.hollowSquare = new char[size][size];
            for (int i=0 ; i<size ; i++) {
                for (int j=0 ; j<size ; j++) {
                    state.hollowSquare[i][j] = ois.readChar();

                }
            }
            return state;
        } catch (Exception e) {
            return null;
        }
    }
    private static void saveGame(int k , int c , int score , int uneatenDots , char[][] hollowSquare , long startTime) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeInt(k);
            oos.writeInt(c);
            oos.writeInt(score);
            oos.writeInt(uneatenDots);
            oos.writeLong(startTime);

            oos.writeInt(hollowSquare.length);
            for (int i=0 ; i< hollowSquare.length ; i++) {
                for (int j=0 ; j< hollowSquare[i].length ; j++) {
                    oos.writeChar(hollowSquare[i][j]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error in saving the game!!");
        }


    }
    private static void finishGame (int score , long startTime) {
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - startTime;

        System.out.println("End of the game!");
        System.out.println("Your final score in this game is:" + score);
        System.out.println("Your playing time in this game:" + timeElapsed + " Milli seconds");
    }

}

