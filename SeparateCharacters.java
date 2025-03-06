import java.util.Scanner;

public class SeparateCharacters {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a word:");
        String word = scanner.next();

        System.out.println("Separated characters:");
        for (int i = 0; i < word.length(); i++) {
            System.out.println(word.charAt(i));

        }
        scanner.close();
    }
}
