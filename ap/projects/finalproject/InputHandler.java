package ap.projects.finalproject;

import java.util.Scanner;

public class InputHandler {

    // One shared reader for the whole application: multiple Scanners on
    // System.in would steal each other's buffered input.
    private static final Scanner SCANNER = new Scanner(System.in);

    public String getString(String input) {
        System.out.print(input);
        return SCANNER.nextLine();
    }

    public int getInt(String input) {
        while (true) {
            System.out.print(input);
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
