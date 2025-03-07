import java.util.Scanner;

public class E6_2_a {    //sequence of integer (a)
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers do you want the sequence to have?");
        int count = scanner.nextInt();
        int[] numbers = new int[count];

        System.out.println("Please enter the numbers:");
        for (int i=0 ; i<count ; i++) {
            numbers[i] = scanner.nextInt();
        }

        int smallestNumber = numbers[0];
        int largestNumber = numbers[0];

        for (int number : numbers ) {
            if ( number < smallestNumber) {
                smallestNumber = number;
            }


            if (number > largestNumber) {
                largestNumber = number;
            }
        }
        System.out.println("The smallest number is:" + smallestNumber);
        System.out.println("The largest number is:" + largestNumber);
    }
}
