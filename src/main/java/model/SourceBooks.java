package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.BooksController;
import controller.exception.JsonFileNotFound;
import model.entity.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceBooks {
    private static final String JSON_FILE = ".\\books.json";
    private static int count = 0;
    public static boolean flag = false;

    public static List<Book> generateBooks() throws JsonFileNotFound, IOException {
        List<Book> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if (!Files.isReadable(Path.of(SourceBooks.JSON_FILE))) {

            throw new JsonFileNotFound("\nFile " + JSON_FILE + " not found or corrupt!  Bye!");
        }
        List<String> sourse = Files.readAllLines(Path.of(SourceBooks.JSON_FILE));

        for (String item : sourse) {
            list.add(objectMapper.readValue(item, Book.class));
        }
        count = list.size();

        return list;

    }

    public static void saveFile(List<Book> list) throws IOException, JsonFileNotFound {

        ObjectMapper objectMapper = new ObjectMapper();

        if (!flag) {

            if (list.size() > SourceBooks.count) {
                if (!Files.isWritable(Path.of(SourceBooks.JSON_FILE))) throw new JsonFileNotFound("\nJson file"+ JSON_FILE + " not found or corrupt! Changes is not saving!");
                for (Book x : list.stream().skip(count).collect(Collectors.toList())) {
                    Files.writeString(Path.of(JSON_FILE), objectMapper.writeValueAsString(x) + "\n", StandardOpenOption.APPEND);
                }
            }
        } else {
            if (!Files.isWritable(Path.of(SourceBooks.JSON_FILE))) throw new JsonFileNotFound("Json file not found! Changes is not saving!");
            Files.deleteIfExists(Path.of(JSON_FILE));
            Files.createFile(Path.of(JSON_FILE));

            for (Book x : list) {
                Files.writeString(Path.of(JSON_FILE), objectMapper.writeValueAsString(x) + "\n", StandardOpenOption.APPEND);

            }


        }
    }

}



