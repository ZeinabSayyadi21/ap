package ap.exercises.midterm;

public class Main {

    public static void main(String[] args) {

        Library library = new Library("Znu Central library");
        library.setManager(new Manager("Reza" , "Ahmadi" , "Master's degree"));
        library.addLibrarian(new Librarian("Ali" , "Rad" , "1234"));
        library.addLibrarian(new Librarian("Sara" , "Karimi" , "1235"));

        SaveLibraryData libraryData = new SaveLibraryData(library);
        libraryData.loadAllData();

        InputProcessor input = new InputProcessor(library);
        Menu menu = new Menu(input);
        menu.mainMenu();
        libraryData.saveAllData();
    }
}