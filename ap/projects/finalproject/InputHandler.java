package ap.projects.finalproject;

import java.util.Scanner;

public class InputHandler {

    private Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    public String getString(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

    public int getInt(String input) {
        System.out.print(input);
        return Integer.parseInt(scanner.nextLine());
    }
}
