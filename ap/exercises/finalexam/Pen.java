package ap.exercises.finalexam;

import java.util.List;

public class Pen extends Product{

    private String color;

    public Pen(String productName, Double price ,String color) {

        super(productName, price);
        this.color = color;
    }

    public String getColor() {
        return color;
    }


    @Override
    public String toString() {
        return "Color: " +color;
    }
}
