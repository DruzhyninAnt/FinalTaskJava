package testingValidator;

import controller.BooksController;
import controller.exception.WrongValue;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidatorTest {
    private BooksController controller;

    @BeforeEach
     void setup() {
        controller = new BooksController();

    }


    @ParameterizedTest
    @ValueSource(strings = {"-100", "0", "klok", "", "5.0"})
    @DisplayName("Testing input numbers of pages with exception")
    void validatePagesTest1_wException(String x) {
        WrongValue ex = Assertions.assertThrows(WrongValue.class, () -> controller.validator.validatePages(x));
        Assertions.assertEquals(ex.getMessage(), "\"" + x + " \"" + "is wrong PAGES value! Only positive integer numbers!", "invalid message");
    }

    @ParameterizedTest
    @CsvSource({"100", "1"})
    @DisplayName("Testing input numbers of pages without exception")
    void validatePagesTest1_notException2(String x) throws WrongValue {
        Assertions.assertEquals(Integer.parseInt(x), controller.validator.validatePages(x), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-100", "0", "klok", ""})
    @DisplayName("Testing input price with exception")
    void validatePriceTest1_wException(String x) {
        WrongValue ex = Assertions.assertThrows(WrongValue.class, () -> controller.validator.validatePrice(x));
        Assertions.assertEquals(ex.getMessage(), "\"" + x + " \"" + "is wrong PRICE value! Only positive numbers!", "invalid message");
    }

    @ParameterizedTest
    @CsvSource({"100.5", "1"})
    @DisplayName("Testing input price with no exception")
    void validatePriceTest1_notException2(String x) throws WrongValue {
        Assertions.assertEquals(Double.parseDouble(x), controller.validator.validatePrice(x), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(strings = {"klok", ""})
    @DisplayName("Testing input change percent with exception")
    void validatePercentTest1_wException(String x) {
        WrongValue ex = Assertions.assertThrows(WrongValue.class, () -> controller.validator.validatePercent(x));
        Assertions.assertEquals(ex.getMessage(), "\"" + x + " \"" + "is wrong CHANGE PERCENT value. Must be numbers only!", "invalid message");
    }

    @ParameterizedTest
    @CsvSource({"10.5", "1", "0.5", "-5.00", "0"})
    @DisplayName("Testing input change percent with no exception")
    void validatePercentTest1_notException2(String x) throws WrongValue {
        Assertions.assertEquals(Double.parseDouble(x), controller.validator.validatePercent(x), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-100", "1", "klok", "", "2005.0","1440","5635"})
    @DisplayName("Testing input Year with exception")
    void validateYearTest1_wException(String x) {
        WrongValue ex = Assertions.assertThrows(WrongValue.class, () -> controller.validator.validateYear(x));
        Assertions.assertEquals(ex.getMessage(), "\"" + x + " \"" + "is wrong input value! Only positive integer numbers (1445 <YEAR < NOW)!", "invalid message");
    }

    @ParameterizedTest
    @CsvSource({"0", "1446","2021","2020"})
    @DisplayName("Testing input Year without exception")
    void validateYearTest1_notException2(String x) throws WrongValue {
        Assertions.assertEquals(Integer.parseInt(x), controller.validator.validateYear(x), 0.0001);
    }
    @ParameterizedTest
    @ValueSource(strings = {"   "," ", ""})
    @DisplayName("Testing if string is empty(*)")
    void validateStringTest1_wException(String x) {

        Assertions.assertEquals("*", controller.validator.validateString(x));
    }

    @ParameterizedTest
    @ValueSource(strings = {" fish", "fish  ","fish"})
    @DisplayName("Testing if string is empty without exception")
    void validateStringTest1_notException2(String x)  {
        Assertions.assertEquals("fish", controller.validator.validateString(x));
    }
}
