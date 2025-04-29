package ap.exercises.ex4;

public class CircuitTester {

    public static void main(String[] args) {

        for (int first=0 ; first<=1; first++ ) {
            for (int second=0 ; second<=1 ; second++) {

                HallwayLight light = new HallwayLight();
                if (first == 1) {
                    light.toggleFirstSwitch();
                }
                if (second == 1) {
                    light.toggleSecondSwitch();
                }
                int expectedLamp = (first + second) % 2;
                int actualLamp = light.getLampState();

                String expectedLampState = (expectedLamp == 1) ? "ON" : "OFF" ;
                String actualLampState = (actualLamp == 1) ? "ON" : "OFF" ;

                System.out.println("First:" +first+ "\tSecond:" +second);
                System.out.println("Expected lamp:" +expectedLampState);
                System.out.println("Actual lamp:" +actualLampState);
            }
        }
    }
}
