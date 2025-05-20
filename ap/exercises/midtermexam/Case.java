package ap.exercises.midtermexam;

public class Case {

    private String brand;
    private double price;
    private double weigh;

    public Case(String brand, double price , double weigh) {
        this.brand = brand;
        if (price > 0) {
            this.price = price;
        } else {
            System.out.println("Price can not be negative!");
        }
        if (weigh > 0) {
            this.weigh = weigh;
        } else {
            System.out.println("Weigh can not be negative!");
        }
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public double getWeigh() {
        return weigh;
    }

    @Override
    public String toString() {
        return "Case [Brand: " + brand + ", Price: " + price + ", Weight: " + weigh + "]";
    }
}
