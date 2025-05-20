package ap.exercises.midtermexam;

import java.util.ArrayList;

public class Shop {

    private ArrayList<Laptop> laptops;
    private ArrayList<Case> cases;

    public Shop() {
        this.laptops = new ArrayList<>();
        this.cases = new ArrayList<>();
    }

    public ArrayList<Laptop> getLaptops() {
        return laptops;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setLaptops(ArrayList<Laptop> laptops) {
        this.laptops = laptops;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    public void addLaptop(Laptop laptop) {
        laptops.add(laptop);
    }

    public void addCase(Case cas) {
        cases.add(cas);
    }

    public void showLaptopList() {
        if (laptops.isEmpty()) {
            System.out.println("There is no laptop to show!");
        } else {
            System.out.println("Laptops list: ");
            for (Laptop laptop : laptops) {
                System.out.print(laptop);
            }
        }
    }

    public void showCaseList() {

        if (cases.isEmpty()) {
            System.out.println("There is no case to show!");
        } else {
            System.out.println("Cases list: ");
            for (Case cas : cases) {
                System.out.print(cas);
            }
        }
    }
}
