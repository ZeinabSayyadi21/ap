package ap.exercises.finalexam;

import java.util.ArrayList;
import java.util.List;

import static ap.exercises.finalexam.ProductTools.expensivePen;
import static ap.exercises.finalexam.ProductTools.sortProductWithPrice;

public class Main {
    public static void main(String[] args) {


        List<Product> products = new ArrayList<>();
        products.add(new Product("Book" , 22.500));
        products.add(new Product("Pen" , 22.500));
        products.add(new Product("Paper" , 5.500));
        products.add(new Product("Pen" , 32.25));

        products.add(new Pen("blue pen", 5.500, "Blue"));
        products.add(new Pen("Green pen", 6.500, "Green"));
        products.add(new Pen("Black pen", 5.500, "Black"));

        products.add(new Bookk("Book", 32.500, "Big Java", "Mr.Smith"));
        products.add(new Bookk("Book", 65.500, "Python world", "Mr.Johnson"));
        products.add(new Bookk("Book", 35.500, "Java programming", "Mr.Adams"));

        //sortProductWithPrice(products);

        Pen expensivePen = ProductTools.expensivePen(products);
        System.out.println("The most expensive pen: " +expensivePen);
        Bookk expensiveBook = ProductTools.expensiveBook(products);
        System.out.println("The most expensive Book: " +expensiveBook);
    }

}
