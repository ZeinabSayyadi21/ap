package ap.exercises.finalexam;

public class Bookk extends Product {

    private String title;
    private String author;

    public Bookk(String productName, Double price, String title, String author) {

        super(productName, price);
        this.title = title;
        this.author = author;
}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Title: " + productName +
                " , Author: " + price ;
    }
}
