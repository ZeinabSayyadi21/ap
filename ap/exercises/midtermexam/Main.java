package ap.exercises.midtermexam;

public class Main {

    public static void main(String[] args) {

        Shop shop = new Shop();

        Laptop laptop = new Laptop("Asus" , "Black" , 50000);
        shop.addLaptop(laptop);

        Case cas = new Case("Acer" , 10000 , 5);
        shop.addCase(cas);

        shop.showLaptopList();
        shop.showCaseList();
    }
}
