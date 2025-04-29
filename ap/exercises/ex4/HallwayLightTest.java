package ap.exercises.ex4;

import java.util.Scanner;

public class HallwayLightTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the initial state for first switch(1 for up and 0 for down): ");
        int firstSwitch = scanner.nextInt();
        System.out.println("Please enter the initial state for second switch(1 for up and 0 for down): ");
        int secondSwitch = scanner.nextInt();
        HallwayLight light = new HallwayLight(firstSwitch,secondSwitch);


       // boolean running = true;
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Toggle first switch");
            System.out.println("2. Toggle second switch");
            System.out.println("3. Show lamp state");
            System.out.println("4. Exit");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 :
                    light.toggleFirstSwitch();
                    break;
                case 2 :
                    light.toggleSecondSwitch();
                    break;
                case 3 :
                    light.getLampState();
                    if (light.getLampState() == 1) {
                        System.out.println("Lamp is ON");
                    } else {
                        System.out.println("Lamp is OFF");
                    }
                    break;
                case 4 :
                    System.out.println("Exit!");
                    scanner.close();
                   // running = false;
                    return;
                default: {
                    System.out.println("Invalid option!");
                }
            }
        }


    }
}
