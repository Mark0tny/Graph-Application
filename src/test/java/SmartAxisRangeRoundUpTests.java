import Graph.CommandLineArgsParser;
import Graph.GraphData;
import Graph.SmartAxisRangeRoundUp;
import Graph.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmartAxisRangeRoundUpTests {
    private SmartAxisRangeRoundUp smartAxisRangeRoundUp = new SmartAxisRangeRoundUp();
    private CommandLineArgsParser commandLineArgsParser = new CommandLineArgsParser();
    private GraphData preparedGraphData = new GraphData();

    @Test
    void checkValueRoundingNoParameters() {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data.csv"), "-svg", "plik7.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE"});
        GraphData graphDataRoundedValues = smartAxisRangeRoundUp.checkAxisRangeValues(preparedGraphData);
        assertEquals(graphDataRoundedValues.getXMax(), 20);
        assertEquals(graphDataRoundedValues.getYMax(), 20);
        assertEquals(graphDataRoundedValues.getXMin(), 0);
        assertEquals(graphDataRoundedValues.getYMin(), 0);
    }

    @Test
    void checkValueRoundingNoParametersOtherValues() {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data_axis_round.csv"), "-svg", "plik.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE"});
        GraphData graphDataRoundedValues = smartAxisRangeRoundUp.checkAxisRangeValues(preparedGraphData);
        assertEquals(graphDataRoundedValues.getXMax(), 20);
        assertEquals(graphDataRoundedValues.getYMax(), 15);
        assertEquals(graphDataRoundedValues.getXMin(), 5);
        assertEquals(graphDataRoundedValues.getYMin(), 0);
    }

    @Test
    void checkValueRoundingTwoParameters() {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data2.csv"), "-svg", "plik.svg", "-title", "tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTTED_LINE", "-xMin", "1", "-yMax", "22"});
        GraphData graphDataRoundedValues = smartAxisRangeRoundUp.checkAxisRangeValues(preparedGraphData);
        assertEquals(graphDataRoundedValues.getXMax(), 60);
        assertEquals(graphDataRoundedValues.getYMax(), 22);
        assertEquals(graphDataRoundedValues.getXMin(), 1);
        assertEquals(graphDataRoundedValues.getYMin(), 0);
    }


    @Test
    void checkValueRoundingThreeParameters() {
        preparedGraphData = commandLineArgsParser.createGraphData(new String[]{"-csv", Utils.getPath("data_axis_round.csv"), "-svg", "plik2.svg", "-title", "Tytul", "-xLabel", "osX", "-yLabel", "osY", "-style", "DOTS", "-xMin", "6.60", "-xMax", "31.2", "-yMax", "24.5"});
        GraphData graphDataRoundedValues = smartAxisRangeRoundUp.checkAxisRangeValues(preparedGraphData);
        assertEquals(graphDataRoundedValues.getXMax(), 31.2);
        assertEquals(graphDataRoundedValues.getYMax(), 24.5);
        assertEquals(graphDataRoundedValues.getXMin(), 6.60);
        assertEquals(graphDataRoundedValues.getYMin(), 0);
    }

}
