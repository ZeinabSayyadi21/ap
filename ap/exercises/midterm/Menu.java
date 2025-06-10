package ap.exercises.midterm;

import java.util.Scanner;

public class Menu {

   // private final Scanner scanner = new Scanner(System.in);
    private final InputProcessor input;

    public Menu(InputProcessor input) {
        this.input = input;
    }


    public void mainMenu() {
        while (true) {
            System.out.println("Hello.Welcome to the Znu library manager system.");
            System.out.println("Please choose your role:");
            System.out.println("1.Manager");
            System.out.println("2.Librarian");
            System.out.println("3.Student");
            System.out.println("4.Exit");

            int choice = input.readInt("Choose your option:");
            switch (choice) {
                case 1:
                    if (input.loginManager()) {
                        managerMenu();
                    } else {
                        System.out.println("Manager not found!");
                    }
                    break;
                case 2 :
                    Librarian librarian = input.loginLibrarian();
                    if (librarian != null) {
                        librarianMenu(librarian);
                    } else {
                        System.out.println("Librarian not found!");
                    }
                    break;

                case 3:
                    Student student = input.loginOrRegisterStudent();
                    studentMenu(student);
                    break;

                case 4: {
                    System.out.println("Have a nice day!Bye.");
                    return;
                }
                default:
                    System.out.println("Invalid option!Try again.");

            }
        }
    }

    public void managerMenu() {
        while (true) {
            System.out.println("Manager menu:");
            System.out.println("1.Add librarian");
            System.out.println("2.List of available books:");
            System.out.println("3.List of librarians:");
            System.out.println("4.List of students:");
            System.out.println("5.List of late return books:");
            System.out.println("6.List of loans:");
            System.out.println("7.Top 10 books:");
            System.out.println("8.View Librarian Statistics:");
            System.out.println("9.Back to main menu");

            int choice = input.readInt("Please choose your option:");
            switch (choice) {
                case 1 :
                    input.addLibrarian();
                    break;
                case 2 :
                    input.showAllBooks();
                    break;
                case 3 :
                    input.showAllLibrarians();
                    break;
                case 4 :
                    input.showAllStudents();
                    break;
                case 5 :
                    input.showLateReturnBooks();
                    break;
                case 6 :
                    input.showAllLoans();
                    break;
                case 7 :
                    input.top10BooksLastYear();
                    break;
                case 8 :
                    input.viewLibrarianStatistics();
                    break;
                case 9 : {
                    return;
                }
                default:
                    System.out.println("Invalid option!Try again.");
            }
        }
    }

        public void librarianMenu(Librarian librarian){
            while (true) {
                System.out.println("Librarian menu:");
                System.out.println("1.Add book");
                System.out.println("2.Edit info");
                System.out.println("3.List of available books:");
                System.out.println("4.List of students:");
                System.out.println("5.List of loans:");
                System.out.println("6.Confirm loan book request:");
                System.out.println("7.Confirm return book request:");
                System.out.println("8.Back to main menu");

                int choice = input.readInt("Please choose your option:");
                switch (choice) {
                    case 1 :
                        input.addBook();
                        break;
                    case 2 :
                        input.editLibrarianInfo(librarian);
                        break;
                    case 3 :
                        input.showAllBooks();
                        break;
                    case 4 :
                        input.showAllStudents();
                        break;
                    case 5 :
                        input.showAllLoans();
                        break;
                    case 6 :
                        input.confirmBookRequest(librarian);
                        break;
                    case 7 :
                        input.confirmReturnBookRequest(librarian);
                    case 8 :
                        return;

                    default:
                        System.out.println("Invalid option!Try again.");
                }


            }
        }

        public void studentMenu(Student student) {
            while (true) {
                System.out.println("Student menu:");
                System.out.println("1.Search book");
                System.out.println("2.Borrowing a book");
                System.out.println("3.List of books borrowed and not returned:");
                System.out.println("4.Returning a book:");
                System.out.println("5.Back to main menu");

                int choice = input.readInt("Please choose your option:");
                switch (choice) {
                    case 1 :
                        input.searchBookByTitle();
                        break;
                    case 2 :
                        input.requestForLoanBook(student);
                        break;
                    case 3 :
                        input.unReturnBooks(student);
                        break;
                    case 4 :
                        input.requestForReturnBooks(student);
                        break;
                    case 5 : {
                        return;
                    }
                    default:
                        System.out.println("Invalid option!Try again.");
                }


            }
        }
    }
