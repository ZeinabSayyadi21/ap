package ap.exercises.ex4;

public class HallwayLight {

    private int firstSwitch;
    private int secondSwitch;

    public HallwayLight ( int firstSwitch , int secondSwitch) {

        this.firstSwitch = firstSwitch;
        this.secondSwitch = secondSwitch;
    }

    public HallwayLight() {
        this.firstSwitch = 0;
        this.secondSwitch = 0;
    }

    public int getFirstSwitchState() {
        return firstSwitch;
    }

    public int getSecondSwitchState() {
        return secondSwitch;
    }

    public void toggleFirstSwitch() {
        if (firstSwitch == 0) {
            firstSwitch = 1;
        } else {
            firstSwitch = 0;
        }
    }

    public void toggleSecondSwitch() {
        secondSwitch = 1 - secondSwitch ;
    }

    public int getLampState() {
        if (firstSwitch != secondSwitch) {
            return 1;
        } else {
            return 0;
        }
    }
}
