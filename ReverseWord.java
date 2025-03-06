import java.util.Scanner;

public class ReverseWord {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a word:");
        String word = scanner.next();

        System.out.println("Reverse of the word is:");
        for ( int i=word.length() - 1 ; i>=0 ; i--) {
            System.out.print(word.charAt(i));
        }
        scanner.close();
    }
}
