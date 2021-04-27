import Graph.CsvFileParser;
import Graph.Point;
import Graph.Utils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InputFileTests {

    private CsvFileParser csvFileParser = new CsvFileParser();

    private static final String DATA_CSV = "data.csv";
    private static final String DATA_CSV2 = "data2.csv";
    private static final String DATA_CSV_EMPTY = "data_empty.csv";
    private static final String DATA_CSV_HEADER = "data_header.csv";
    private static final String DATA_INCORRECT_CSV = "incorrect_data_format.csv";
    private static final String DATA_LESS_NUMBER_CSV = "incorrect_data_less_point_number.csv";
    private static final String DATA_MORE_NUMBER_CSV = "incorrect_data_more_point_number.csv";

    @Test
    void testDataFileExist() {
        checkFileExist(DATA_CSV);
        checkFileExist(DATA_CSV2);
        checkFileExist(DATA_CSV_HEADER);
        checkFileExist(DATA_INCORRECT_CSV);
    }

    @Test
    void testDataFileExtension() {
        assertEquals("csv", Utils.getExtension(DATA_CSV));
        assertEquals("csv", Utils.getExtension(DATA_CSV2));
        assertEquals("csv", Utils.getExtension(DATA_CSV_HEADER));
    }

    @Test
    void testDataFileIsNotEmpty() {
        File file = new File(Utils.getPath(DATA_CSV_EMPTY));
        assertFalse(Utils.isFileEmpty(file));
    }

    @Test
    void testDataFromFile() {
        getDataFromFile(getPreparedData(), DATA_CSV);
    }

    @Test
    void testDataFromOtherFile() {
        getDataFromFile(getPreparedDataOtherFile(), DATA_CSV2);
    }

    @Test
    void testDataHeader() {
        getDataFromFile(getPreparedDataHeader(), DATA_CSV_HEADER);
    }

    @Test
    void testIncorrectData() {
        checkIncorrectDataInFile("For input string: \"x\"");
    }

    @Test
    void testIncorrectDataLessNumber() {
        checkIncorrectDataLessQuantity("Index 2 out of bounds for length 2", DATA_LESS_NUMBER_CSV);
    }

    @Test
    void testIncorrectDataMoreNumber() {
        checkIncorrectDataMoreQuantity("4", DATA_MORE_NUMBER_CSV);
    }

    private void checkIncorrectDataInFile(String message) {
        Exception exception = assertThrows(NumberFormatException.class, () ->
                csvFileParser.getGraphCourseFormFile(Utils.getPath(DATA_INCORRECT_CSV)));
        assertEquals(message, exception.getMessage());
    }

    private void checkIncorrectDataLessQuantity(String message, String fileName) {
        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                csvFileParser.getGraphCourseFormFile(Utils.getPath(fileName)));
        assertEquals(message, exception.getMessage());
    }

    private void checkIncorrectDataMoreQuantity(String message, String fileName) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                csvFileParser.getGraphCourseFormFile(Utils.getPath(fileName)));
        assertEquals("Incorrect quantity data in file: " + message, exception.getMessage());
    }

    private Map<String, List<Point>> getPreparedData() {
        Map<String, List<Point>> graphData = new HashMap<>();
        graphData.put("x", Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(5, 5),
                new Point(6, 6),
                new Point(7, 7),
                new Point(8, 8),
                new Point(9, 9),
                new Point(10, 10)
        ));
        graphData.put("y", Arrays.asList(
                new Point(1.50, 1.50),
                new Point(2.25, 3.50),
                new Point(3.50, 5.50),
                new Point(4.50, 3.50),
                new Point(5.50, 5.50),
                new Point(6.50, 3.50),
                new Point(7.50, 5.50),
                new Point(8.50, 3.50),
                new Point(9.50, 5.50),
                new Point(10.50, 3.50),
                new Point(13.9, 6.9),
                new Point(14.50, 17.90),
                new Point(16.00, 17.90)
        ));
        graphData.put("d", Arrays.asList(
                new Point(7.50, 12.50),
                new Point(8.50, 12.50),
                new Point(9.50, 12.50),
                new Point(19, 18.23)
        ));
        return graphData;
    }

    private Map<String, List<Point>> getPreparedDataOtherFile() {
        Map<String, List<Point>> graphData = new HashMap<>();
        graphData.put("x", Arrays.asList(
                new Point(1.00, 2.22),
                new Point(2.15, 5.60),
                new Point(3.50, 3.57),
                new Point(4.86, 7.3),
                new Point(6.11, 7.91),
                new Point(7.40, 9.21),
                new Point(8.70, 10.51)
        ));
        graphData.put("y", Arrays.asList(
                new Point(9.99, 11.81),
                new Point(10.28, 13.11),
                new Point(12.58, 14.42),
                new Point(13.87, 15.72),
                new Point(14.67, 21.98),
                new Point(18.67, 25.98),
                new Point(34.67, 35.98),
                new Point(45.67, 80.98),
                new Point(56.67, 90.98)
        ));
        return graphData;
    }

    private Map<String, List<Point>> getPreparedDataHeader() {
        Map<String, List<Point>> graphData = new HashMap<>();
        graphData.put("x", Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(5, 5),
                new Point(6, 6)
        ));
        graphData.put("y", Arrays.asList(
                new Point(1.50, 1.50),
                new Point(2.25, 3.50),
                new Point(3.50, 5.50),
                new Point(4.50, 3.50),
                new Point(5.50, 5.50),
                new Point(6.50, 3.50),
                new Point(7.50, 5.50)

        ));
        return graphData;
    }

    private void checkFileExist(String fileName) {
        File file = new File(Utils.getPath(fileName));
        assertTrue(file.exists());
    }

    private void getDataFromFile(Map<String, List<Point>> preparedData, String fileName) {
        assertEquals(preparedData, csvFileParser.getGraphCourseFormFile(Utils.getPath(fileName)));
    }

}
