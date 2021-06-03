package view;



import java.util.Scanner;

public class InputDataView {
    private Scanner menuScanner = new Scanner(System.in);
    private BooksView booksView;

    public InputDataView(BooksView booksView) {
        this.booksView = booksView;
    }

    public int inputMenuItem() {
        booksView.printMenu();
        booksView.printMessage(BooksView.ENTER_COMMAND);
        while ( !menuScanner.hasNextInt()) {
            menuScanner.next();
            booksView.printMessage(BooksView.ERROR_ENTER);
        }
        return menuScanner.nextInt();
    }

    public String inputString(String message) {

        Scanner scanner = new Scanner(System.in) ;
        booksView.printMessage(message);

        return  scanner.nextLine();

    }
    public String inputSearchItem() {
        booksView.printMessage(BooksView.ENTER_SEARCH);
        booksView.printSearchMenu();
        booksView.printMessage(BooksView.ENTER_COMMAND);
        return menuScanner.next();
    }

}

