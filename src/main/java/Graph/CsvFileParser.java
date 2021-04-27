package Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileParser {


    public Map<String, List<Point>> getGraphCourseFormFile(String dataFilePath) {
        Map<String, List<Point>> tempMap = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(dataFilePath))) {
            final Pattern DELIMITER = Pattern.compile(",");
            List<String> points = stream.filter(line -> line.matches(".*[0-9].*")).collect(Collectors.toList());
            checkFileData(points);
            tempMap = points.stream()
                    .map(DELIMITER::split)
                    .collect(Collectors.groupingBy(a -> a[0],
                            Collectors.mapping(a -> new Point(Double.parseDouble(a[1]), Double.parseDouble(a[2])), Collectors.toList())));
        } catch (IOException e) {
            e.getMessage();
        }
        return tempMap;
    }

    private void checkFileData(List<String> points) {
        String[] tempTable;

        for (String point : points) {
            tempTable = point.split(",");
            if (tempTable.length > 3) {
                throw new IllegalArgumentException("Incorrect quantity data in file: " + tempTable.length);
            }
            if (tempTable.length < 2) {
                throw new IndexOutOfBoundsException("Incorrect quantity data in file: " + tempTable.length);
            }
        }
    }

}
