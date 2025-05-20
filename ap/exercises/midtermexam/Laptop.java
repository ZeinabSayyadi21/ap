package ap.exercises.midtermexam;

public class Laptop {

    private String brand;
    private String color;
    private double price;

    public Laptop(String brand , String color , double price) {
        this.brand = brand;
        this.color = color;
        if (price > 0) {
            this.price = price;
        } else {
            System.out.println("Price can not be negative!");
        }
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Laptop [Brand: " + brand + ", Color: " + color + ", Price: " + price + "]\n";
    }
}
