import Graph.CommandLineArgsParser;
import Graph.GraphData;
import Graph.GraphStyle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandLineArgTests {

    CommandLineArgsParser commandLineArgsParser = new CommandLineArgsParser();
    GraphData parsedGraphData = new GraphData();

    private static final String MSG_X_RANGE = "Wrong X range values";
    private static final String MSG_Y_RANGE = "Wrong Y range values";
    private static final String MSG_XY_NEG = "Wrong X or Y value - it cant be less than zero";
    private static final String MSG_NOTITLE = "Title must contain at least one letter";
    private static final String MSG_NOLABELS = "Labels must contain at least one letter";
    private static final String MSG_NOOUTPUTFILENAME = "Output file must have a name";
    private static final String MSG_FILENAME = "No given file";
    private static final String MSG_NO_CMD_CSV_PARAMETER = "Missing argument: csv";

    @Test
    void checkNoCmdCsvParameter() {
        checkGraphDataParameters(new String[]{"", "data1.csv", "-svg", "plik1.svg", "-title", "tytul", "-xLabel", "labelX", "-yLabel", "labelY", "-style", "DOTTED_LINE", "-xMin", "1", "-xMax", "12", "-yMin", "2", "-yMax", " 20"}, MSG_NO_CMD_CSV_PARAMETER);
    }

    @Test
    void checkInputArguments() {
        checkArguments(getPreparedGraphData(), new String[]{"-csv", "data1.csv", "-svg", "plik1.svg", "-title", "tytul", "-xLabel", "labelX", "-yLabel", "labelY", "-style", "DOTTED_LINE", "-xMin", "1", "-xMax", "12", "-yMin", "2", "-yMax", " 20"});
    }

    @Test
    void checkInputArgumentsOtherData() {
        checkArguments(getPreparedGraphDataOtherFile(), new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "12", "-xMax", "28", "-yMin", "7", "-yMax", " 45"});
    }

    @Test
    void checkInputArgumentsOptionalParameters() {
        checkArguments(getPreparedGraphDataOptionalArguments(), new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMax", "28", "-yMin", "7"});
    }

    @Test
    void checkInputArgumentsNoOptionalParameters() {
        checkArguments(getPreparedGraphDataNoOptionalArguments(), new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS"});
    }

    @Test
    void checkEmptyOutputFileName() {
        checkGraphDataParameters(new String[]{"-csv", "data2.csv", "-svg", "", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"}, MSG_NOOUTPUTFILENAME);
    }

    @Test
    void checkEmptyInputFileName() {
        checkGraphDataParameters(new String[]{"-csv", "", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"}, MSG_FILENAME);
    }

    @Test
    void checkEmptyLabels() {
        checkGraphDataParameters(new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"}, MSG_NOLABELS);
    }

    @Test
    void checkEmptyTitle() {
        checkGraphDataParameters(new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "1", "-xMax", "10", "-yMin", "3", "-yMax", "22"}, MSG_NOTITLE);
    }

    @Test
    void checkNegativeRangeValues() {
        checkGraphDataParameters(new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "2", "-xMax", "-1", "-yMin", "12", "-yMax", "-2"}, MSG_XY_NEG);
    }

    @Test
    void checkIncorrectXRangeValues() {
        checkGraphDataParameters(new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "28", "-xMax", "11", "-yMin", "25", "-yMax", "22"}, MSG_X_RANGE);
    }

    @Test
    void checkIncorrectYRangeValues() {
        checkGraphDataParameters(new String[]{"-csv", "data2.csv", "-svg", "plik2.svg", "-title", "innytytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE", "-xMin", "12", "-xMax", "28", "-yMin", "25", "-yMax", "22"}, MSG_Y_RANGE);
    }

    private void checkArguments(GraphData graphData, String[] args) {
        parsedGraphData = commandLineArgsParser.createGraphData(args);
        assertEquals(graphData.getDataFilePath(), parsedGraphData.getDataFilePath());
        assertEquals(graphData.getGraphFileName(), parsedGraphData.getGraphFileName());
        assertEquals(graphData.getTitle(), parsedGraphData.getTitle());
        assertEquals(graphData.getXLabel(), parsedGraphData.getXLabel());
        assertEquals(graphData.getYLabel(), parsedGraphData.getYLabel());
        assertEquals(graphData.getXMax(), parsedGraphData.getXMax());
        assertEquals(graphData.getYMax(), parsedGraphData.getYMax());
        assertEquals(graphData.getXMin(), parsedGraphData.getXMin());
        assertEquals(graphData.getYMin(), parsedGraphData.getYMin());
        assertEquals(graphData.getStyle(), parsedGraphData.getStyle());


    }

    private GraphData getPreparedGraphData() {
        GraphData graphData = new GraphData();
        graphData.setDataFilePath("data1.csv");
        graphData.setGraphFileName("plik1.svg");
        graphData.setTitle("tytul");
        graphData.setXLabel("labelX");
        graphData.setYLabel("labelY");
        graphData.setXMax(12);
        graphData.setYMax(20);
        graphData.setXMin(1);
        graphData.setYMin(2);
        graphData.setStyle(GraphStyle.DOTTED_LINE);
        return graphData;
    }

    private GraphData getPreparedGraphDataOtherFile() {
        GraphData graphData = new GraphData();
        graphData.setDataFilePath("data2.csv");
        graphData.setGraphFileName("plik2.svg");
        graphData.setTitle("innytytul");
        graphData.setXLabel("osX");
        graphData.setYLabel("osY");
        graphData.setXMax(28);
        graphData.setYMax(45);
        graphData.setXMin(12);
        graphData.setYMin(7);
        graphData.setStyle(GraphStyle.DOTS);
        return graphData;
    }

    private GraphData getPreparedGraphDataOptionalArguments() {
        GraphData graphData = new GraphData();
        graphData.setDataFilePath("data2.csv");
        graphData.setGraphFileName("plik2.svg");
        graphData.setTitle("innytytul");
        graphData.setXLabel("osX");
        graphData.setYLabel("osY");
        graphData.setXMax(28);
        graphData.setYMax(Double.NaN);
        graphData.setXMin(Double.NaN);
        graphData.setYMin(7);
        graphData.setStyle(GraphStyle.DOTS);
        return graphData;
    }

    private GraphData getPreparedGraphDataNoOptionalArguments() {
        GraphData graphData = new GraphData();
        graphData.setDataFilePath("data2.csv");
        graphData.setGraphFileName("plik2.svg");
        graphData.setTitle("innytytul");
        graphData.setXLabel("osX");
        graphData.setYLabel("osY");
        graphData.setXMax(Double.NaN);
        graphData.setYMax(Double.NaN);
        graphData.setXMin(Double.NaN);
        graphData.setYMin(Double.NaN);
        graphData.setStyle(GraphStyle.DOTS);
        return graphData;
    }

    private GraphData getPreparedGraphData(String[] args) {
        return commandLineArgsParser.createGraphData(args);
    }

    private void checkGraphDataParameters(String[] args, String message) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                getPreparedGraphData(args));
        assertEquals(message, exception.getMessage());
    }

}
