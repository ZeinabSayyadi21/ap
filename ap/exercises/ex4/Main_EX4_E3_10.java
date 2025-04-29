package ap.exercises.ex4;

import java.util.ArrayList;

public class Main_EX4_E3_10 {

    private ArrayList<Double> itemPrices;
    private double total;

    public Main_EX4_E3_10() {
        total =0;
        itemPrices = new ArrayList<>();
    }

    public void addItem(double price) {
        itemPrices.add(price);
        total += price;
    }

    public void printReceipt() {
        String receipt = "";
        for (double price : itemPrices) {
            receipt = receipt.concat("Item: $" +String.valueOf(price) + "\n");
        }
        receipt = receipt.concat("Total: $" + total);
        System.out.println(receipt);
    }
}
