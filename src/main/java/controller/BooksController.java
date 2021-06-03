package controller;

import com.fasterxml.jackson.core.JsonParseException;
import controller.exception.JsonFileNotFound;
import controller.exception.WrongValue;
import model.ServiceBooks;
import model.SourceBooks;
import model.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.BooksView;
import view.InputDataView;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class BooksController {


    private static Logger logger;

    private BooksView printBooks;
    private ServiceBooks serviceBooks;
    private InputDataView dataView;
    public Validator validator = new Validator();

    public class Validator {

        public int validatePages(String string) throws WrongValue {

            try {
                int i = Integer.parseInt(string);
                if (i > 0) return i;
                else {

                    throw new WrongValue("\"" + string + " \"" + "is wrong PAGES value! Only positive integer numbers!");
                }
            } catch (NumberFormatException e) {


                throw new WrongValue("\"" + string + " \"" + "is wrong PAGES value! Only positive integer numbers!");

            }
        }

        public double validatePrice(String string) throws WrongValue {
            try {
                double d = Double.parseDouble(string);
                if (d > 0) return d;
                else {

                    throw new WrongValue("\"" + string + " \"" + "is wrong PRICE value! Only positive numbers!");
                }
            } catch (Exception e) {


                throw new WrongValue("\"" + string + " \"" + "is wrong PRICE value! Only positive numbers!");

            }
        }

        public double validatePercent(String string) throws WrongValue {
            try {
                double d = Double.parseDouble(string);
                return d;

            } catch (Exception e) {

                throw new WrongValue("\"" + string + " \"" + "is wrong CHANGE PERCENT value. Must be numbers only!");

            }
        }

        public int validateYear(String string) throws WrongValue {

            try {

                int year = Calendar.getInstance().get(Calendar.YEAR);
                int i = Integer.parseInt(string);
                if ((i > 1445 && i <= year) || (i == 0)) return i;
                else {

                    throw new WrongValue("\"" + string + " \"" + "is wrong input value! Only positive integer numbers (1445 <YEAR < NOW)!");
                }
            } catch (NumberFormatException e) {
                logger.trace(e.getMessage());
                logger.error(e, e.fillInStackTrace());

                throw new WrongValue("\"" + string + " \"" + "is wrong input value! Only positive integer numbers (1445 <YEAR < NOW)!");

            }

        }

        public String validateString(String string) {
            if (string.trim().isEmpty()) return "*";

            else return string.trim();

        }


    }

    public BooksController() {

        try {
            System.setProperty("log4j2.configurationFile", ".\\log4j2-test.xml");
            logger = LogManager.getLogger(BooksController.class);


            logger.trace("App starting");
            printBooks = new BooksView();
            serviceBooks = new ServiceBooks();

            dataView = new InputDataView(printBooks);
        } catch (JsonFileNotFound e) {
            logger.error(e, e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage(e.getMessage());
            logger.trace("Close app with error");
            System.exit(-1);
        } catch (JsonParseException e) {
            logger.error(e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage("\nSomething is wrong! Data at JSON file is corrupt!\n" + e.getMessage());
            logger.trace("Close app with error");
            System.exit(-1);

        } catch (IOException e) {
            logger.error(e, e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage("Something is wrong!" + e.getMessage());
            logger.trace("Close app with error");
            System.exit(-1);
        } catch (Exception e) {
            logger.error(e, e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage("Something is very wrong!" + e.getMessage());
            logger.trace("Close app with error");
            System.exit(-1);
        }
    }

    public void execute() {

        try {
            while (true) {
                System.out.println();
                int menuItem = dataView.inputMenuItem();
                switch (menuItem) {
                    case 0:
                        logger.trace("Selecting exit from app");
                        SourceBooks.saveFile(serviceBooks.getBookList());
                        logger.trace("Close app...Ok");
                        System.exit(0);
                    case 1:
                        logger.trace("Selecting getting Books list");
                        printBooks.printListBooks(serviceBooks.getBookList());
                        break;
                    case 2:
                        logger.trace("Selecting adding new book");
                        inputAddBook();
                        break;
                    case 3:
                        logger.trace("Selecting changing price");
                        inputChangePrice();
                        break;
                    case 4:
                        logger.trace("Selecting searching");
                        inputSearchBook();
                        break;
                    default:
                        logger.error("Invalid main menu item selected");
                        printBooks.printMessage(BooksView.ERROR_ENTER);
                }
            }
        } catch (JsonFileNotFound e) {
            logger.error(e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage(e.getMessage());
            logger.trace("Close app with error");
        } catch (IOException e) {
            logger.error(e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage(e.getMessage());
        }
    }

    private void inputAddBook() {

        try {
            String title = validator.validateString(dataView.inputString(BooksView.ENTER_TITLE));
            String author = validator.validateString(dataView.inputString(BooksView.ENTER_AUTHOR));
            String publisher = validator.validateString(dataView.inputString(BooksView.ENTER_PUBLISHER));
            String year = dataView.inputString(BooksView.ENTER_YEAR);

            int y = validator.validateYear(year);
            String pages = dataView.inputString(BooksView.ENTER_PAGES);
            int p = validator.validatePages(pages);
            String cost = dataView.inputString(BooksView.ENTER_COST);
            double c = validator.validatePrice(cost);
//
            serviceBooks.addBook(title, author, publisher, y,
                    p, c);
            printBooks.printMessage("\nBook added successfully\n");
            logger.trace("Added book. Title: " + title + " Author " + author + " Publisher:" + publisher + " Year:" + y + " Pages:" + p + " Price:" + c);
        } catch (WrongValue e) {
            logger.error(e, e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage(e.getMessage());

        }

    }

    private void inputChangePrice() {
        String percent = dataView.inputString(BooksView.ENTER_CHANGE_PERCENT);
        try {
            double change = validator.validatePercent(percent);
            if (Math.abs(change) > 0d) serviceBooks.changePrice(change);

            printBooks.printMessage(BooksView.ENTER_DONE);

        } catch (WrongValue e) {
            logger.error(e, e.fillInStackTrace());
            logger.trace(e.getMessage());
            printBooks.printMessage(e.getMessage() + "\n");

        }


    }

    private void inputSearchBook() {
        try {
            while (true) {
                System.out.println();
                String menuItem = dataView.inputSearchItem();
                List<Book> list = null;

                switch (menuItem) {
                    case "0":
                        logger.trace("exit from search menu");
                        return;
                    case "1":

                        String author = dataView.inputString(BooksView.ENTER_AUTHOR);
                        logger.trace("Searching by Author:" + author);
                        list = serviceBooks.searchByAuthor(validator.validateString( author));

                        break;
                    case "2":
                        String inputYear = dataView.inputString(BooksView.ENTER_YEAR);
                        logger.trace("Searching by Year:" + inputYear);
                        int convertedYear = validator.validateYear(inputYear);

                        list = serviceBooks.searchByYear(convertedYear);

                        break;
                    case "3":
                        String publisher = dataView.inputString(BooksView.ENTER_PUBLISHER);
                        logger.trace("Searching by publisher:" + publisher);
                        list = serviceBooks.searchByPublisher( validator.validateString(publisher));
                        break;

                    default:
                        logger.trace(BooksView.ERROR_ENTER);
                        printBooks.printMessage(BooksView.ERROR_ENTER);
                }
                if (list == null) continue;
                if (list.isEmpty()) {
                    printBooks.printMessage("\nBooks not found\n");

                } else {
                    logger.trace(list.size() + " records found");
                    printBooks.printMessage(list.size() + " records found\n");
                    printBooks.printListBooks(list);
                }

            }
        } catch (WrongValue e) {
            logger.trace(e.getMessage());
            logger.error(e, e.fillInStackTrace());
            printBooks.printMessage(e.getMessage());
        } catch (Exception e) {
            logger.trace(e.getMessage());
            logger.error(e, e.fillInStackTrace());
            printBooks.printMessage(e.getMessage());
        }
    }


}
