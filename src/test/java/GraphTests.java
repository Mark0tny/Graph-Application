import Graph.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTests {

    private Graph graph = new Graph();
    private CommandLineArgsParser commandLineArgsParser = new CommandLineArgsParser();
    private GraphData preparedGraphData = new GraphData();


    @Test
    void testOutputFileSVGExist() {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data.csv"), "-svg", "plik1.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        checkFileExist(preparedGraphData.getDataFilePath());
    }

    @Test
    void testOutputFileSVGIsNotEmpty() {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data2.csv"), "-svg", "plik2.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        checkFileContent(preparedGraphData.getDataFilePath());
    }

    @Test
    void checkNumberOfPointsInGeneratedSVGFile() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data2.csv"), "-svg", "plik3.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        assertEquals(getNumberOfAllPoints(checkRangeValuesInMap(preparedGraphData)), numberOfPointsSVGFile(preparedGraphData));
    }


    @Test
    void checkNumberOfPointsInGeneratedSVGFileOtherFile() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data.csv"), "-svg", "plik4.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        assertEquals(getNumberOfAllPoints(checkRangeValuesInMap(preparedGraphData)), numberOfPointsSVGFile(preparedGraphData));
    }

    @Test
    void checkIfPointsAreConnectedWithLines() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data2.csv"), "-svg", "plik5.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        assertEquals(preparedGraphData.getGraphCourse().keySet().size(), numberOfPolylineSVGFile(preparedGraphData));
    }

    @Test
    void checkIfPointsAreConnectedWithLinesOtherFile() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data.csv"), "-svg", "plik6.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        assertEquals(preparedGraphData.getGraphCourse().keySet().size(), numberOfPolylineSVGFile(preparedGraphData));
    }

    @Test
    void checkIfPointsAreConnectedDotStyle() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data2.csv"), "-svg", "plik7.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        assertEquals(0, numberOfPolylineSVGFile(preparedGraphData));
    }

    @Test
    void checkIfCorrectnessOfElement() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data2.csv"), "-svg", "plik8.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        checkSvgElementsCorrectness(preparedGraphData, "<", ">");
        checkSvgElementsCorrectness(preparedGraphData, "<text", "</text>");
        checkSvgElementsCorrectness(preparedGraphData, "<g", "</g>");
    }

    @Test
    void checkIfCorrectnessOfElementOtherFile() throws IOException {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data.csv"), "-svg", "plik9.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"});
        graph.generateSVGLineGraph(preparedGraphData);
        checkSvgElementsCorrectness(preparedGraphData, "<", ">");
        checkSvgElementsCorrectness(preparedGraphData, "<svg", "</svg>");
        checkSvgElementsCorrectness(preparedGraphData, "<line", "</line>");
        checkSvgElementsCorrectness(preparedGraphData, "<path", "</path>");
    }

    private void checkSvgElementsCorrectness(GraphData preparedGraphData, String s1, String s2) throws IOException {
        assertEquals(numberOfOpenElementsSvg(preparedGraphData, s1), numberOfOpenElementsSvg(preparedGraphData, s2));
    }


    private int numberOfPolylineSVGFile(GraphData graphData) throws IOException {
        List<String> listOfLines = Files.readAllLines(Paths.get(graphData.getGraphFileName()));
        return (int) listOfLines.stream().filter(element -> element.contains("<polyline")).count();
    }

    private int getNumberOfAllPoints(Map<String, List<Point>> graphData) {
        int count = 0;
        for (String name : graphData.keySet()) {
            count += graphData.get(name).size();
        }
        return count;
    }

    private int numberOfPointsSVGFile(GraphData graphData) throws IOException {
        List<String> listOfLines = Files.readAllLines(Paths.get(graphData.getGraphFileName()));
        return (int) listOfLines.stream().filter(element -> element.contains("<circle")).count() - graphData.getGraphCourse().keySet().size();
    }

    private void checkFileContent(String dataFileName) {
        File file = new File(Utils.getPath(dataFileName));
        assertTrue(Utils.isFileEmpty(file));
    }

    private void checkFileExist(String fileName) {
        File file = new File(Utils.getPath(fileName));
        assertTrue(file.exists());
    }

    private Map<String, List<Point>> checkRangeValuesInMap(GraphData graphData) {
        Map<String, List<Point>> tempMap = graphData.getGraphCourse();
        List<Point> tempList;
        for (String name : tempMap.keySet()) {
            tempList = tempMap.get(name).stream().filter(
                    point -> point.getX() > graphData.getXMin()
            ).filter(
                    point -> point.getX() < graphData.getXMax()
            ).filter(
                    point -> point.getY() > graphData.getYMin()
            ).filter(
                    point -> point.getY() < graphData.getYMax()
            ).collect(Collectors.toList());
            tempMap.put(name, tempList);
        }
        return tempMap;
    }

    private int numberOfOpenElementsSvg(GraphData graphData, String operator) throws IOException {
        List<String> listOfLines = Files.readAllLines(Paths.get(graphData.getGraphFileName()));
        return (int) listOfLines.stream().filter(element -> element.contains(operator)).count();
    }


}
