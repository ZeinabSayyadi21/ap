import java.util.Scanner;

public class E6_2_b {   //the number of even and odd inputs
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers do you want the sequence to have?");
        int count = scanner.nextInt();
        int evenNumber = 0;
        int oddNumber = 0;

        System.out.println("Please enter the numbers:");
        for (int i=0 ; i<count ; i++) {
           int number = scanner.nextInt();

           if (number %2 ==  0) {
               evenNumber++;
           } else {
               oddNumber++;
           }
        }
        System.out.println("The number of even number is:" + evenNumber);
        System.out.println("The number of odd number is:" + oddNumber);
    }
}
