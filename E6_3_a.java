import java.util.Scanner;

public class E6_3_a {   //Uppercase letters
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a sentence:");
        String sentence = scanner.nextLine();

        for (int i=0 ; i<sentence.length() ; i++) {
            char upperCase = sentence.charAt(i);

            if (Character.isUpperCase(upperCase)) {
                System.out.println(upperCase);
            }
        }
    }
}
