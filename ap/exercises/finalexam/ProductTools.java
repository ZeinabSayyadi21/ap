package ap.exercises.finalexam;

import com.sun.javafx.collections.MappingChange;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductTools {

    private Pen pen;

    public static void sortProductWithPrice(List<Product> products) {

        List<Product> uniqueProducts = products.stream()
                .distinct()
                .sorted(Comparator.comparingDouble(Product::getPrice)
                        .thenComparing(ProductTools::getPriority)
                        .thenComparing(Product::getProductName))
                .collect(Collectors.toList());

        uniqueProducts.forEach(System.out::println);
    }

    private static int getPriority(Product product) {
        String name = product.getProductName();
        if ("Book".equalsIgnoreCase(name)) {
            return 1;
        } else if ("Pen".equalsIgnoreCase(name)) {
            return 2;
        } else {
            return 3;
        }
    }

    public static Pen expensivePen(List<Product> products) {
        Pen mostExpensivePen = null;
        double maxPrice = -1;

        for (Product product : products) {
            if (product instanceof Pen) {
                Pen pen1 = (Pen) product;
                if (pen1.getPrice() > maxPrice) {
                    maxPrice = pen1.getPrice();
                    mostExpensivePen = pen1;
                }
            }
        }
        return mostExpensivePen;
    }

    public static Bookk expensiveBook(List<Product> products) {
        Bookk mostExpensiveBook = null;
        double maxPrice = -1;

        for (Product product : products) {
            if (product instanceof Bookk) {
                Bookk book1 = (Bookk) product;
                if (book1.getPrice() > maxPrice) {
                    maxPrice = book1.getPrice();
                    mostExpensiveBook = book1;
                }
            }
        }
        return mostExpensiveBook;
    }

   /* public static Map<String, Integer> countPens(List<Pen> pens) {
        Map<String, Integer> colorCount = new HashMap<>();

        for (Pen pen : pens){
            String color = pen.getColor();

        }

    }*/
}
