import java.util.Scanner;
import java.util.Arrays;

public class SortLexicographically {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter three strings:");
        String string1 = scanner.next();
        String string2 = scanner.next();
        String string3 = scanner.next();

        String[] strings = {string1, string2, string3};

        Arrays.sort(strings);

        System.out.println("The sorted strings are:");

        System.out.println(strings[0]);
        System.out.println(strings[1]);
        System.out.println(strings[2]);

        scanner.close();
    }
}
