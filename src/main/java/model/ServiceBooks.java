package model;


import controller.exception.JsonFileNotFound;
import model.entity.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceBooks {
    private List<Book> bookList;

    public ServiceBooks() throws IOException, JsonFileNotFound {
        bookList = SourceBooks.generateBooks();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(String name, String author, String publish, int year, int countPages, double cost) {
        bookList.add(new Book(name, author, publish, year, countPages, cost));
    }

    public void changePrice(double percent) {

        bookList.stream().forEach(x -> x.setCost(x.getCost() + x.getCost() * percent / 100));
        SourceBooks.flag = true;
    }

    public List<Book> searchByAuthor(String author) {
        return bookList.stream().filter(x -> x.getAuthor().toUpperCase().
                contains(author.toUpperCase())).collect(Collectors.toList());
    }

    public List<Book> searchByPublisher(String publisher) {

        return bookList.stream().filter(x -> x.getPublish().toUpperCase().
                contains(publisher.toUpperCase())).collect(Collectors.toList());

    }

    public List<Book> searchByYear(int year) {
        return bookList.stream().filter(x -> x.getYear()
                == year).collect(Collectors.toList());
    }

}
