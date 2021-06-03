package view;



import model.entity.Book;
import java.util.List;

public class BooksView {
    public static final String ENTER_COMMAND = "\nMake your choice: ";
    public static final String ERROR_ENTER = "Enter error. Repeat!\n";
    public static final String ENTER_TITLE = "Enter title: ";
    public static final String ENTER_AUTHOR = "Enter author: ";
    public static final String ENTER_PUBLISHER = "Enter publisher: ";
    public static final String ENTER_YEAR = "Enter year: ";
    public static final String ENTER_PAGES = "Enter number pages: ";
    public static final String ENTER_COST = "Enter cost: ";
    public static final String ENTER_CHANGE_PERCENT = "Enter percent of change: ";
    public static final String ENTER_DONE = "Done.\n";
    public static final String ENTER_SEARCH = "Search:\n";
    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printListBooks(List<Book> books) {
        for (Book elem : books) {
            System.out.println(convertBookToString(elem));
        }
    }
    private String convertBookToString(Book book) {
        return String.format("%-20s| %-30s| %-15s| %-5d| %-5d| %8.2f", book.getAuthor(), book.getName(),
                book.getPublish(), book.getYear(), book.getCountPages(), book.getCost());
    }

    public void printMenu() {
        System.out.println( "1.View all books\n" +
                "2.Add a new book\n" +
                "3.Change price of the books\n" +
                "4.Search the book (by author,year or publisher)\n" +
                "0.Close the app "
        );
    }
    public void printSearchMenu() {

        System.out.println( "1.by Author\n" +
                "2.by year\n" +
                "3.by publisher\n" +
                "0.Go to main menu. "
        );
    }
}

