package ap.exercises.ex3;

public class Main_EX3_LM_1_1 {

    public static void main(String[] args) {
        Book book1 = new Book("Big Java" , "Cay S.Horstmann" , 1377 , 2016 );
        Book book2 = new Book("Computer Networks" , "Larry Petterson" , 884 , 2011);

        Student student1 = new Student("Zeinab" , "Sayyadi" , "463132" , "Computer engineering");
        Student student2 = new Student("Mohadeseh" , "Sheykh" , "463133" , "Humanities");

        System.out.println("List of available books:");
        book1.showList();
        book2.showList();

        System.out.println("Students:");
        student1.showList();
        student2.showList();
    }


    public static class Book {
        String bookTitle;
        String author;
        int pages;
        int publicationYear;

        Book (String t , String a , int p , int y) {
            bookTitle = t;
            author = a;
            pages = p;
            publicationYear = y;
        }

        void showList() {
            System.out.println(bookTitle + " -> " + author + " -> " + pages + " -> " + publicationYear );
        }
    }

    public static class Student {
        String firstName;
        String lastName;
        String id;
        String major;

        Student( String f , String l , String i , String m) {
            firstName = f;
            lastName = l;
            id = i;
            major = m;
        }
        void showList() {
            System.out.println(firstName + " -> " + lastName + " -> " + id + " -> " + major );
        }
    }
}
