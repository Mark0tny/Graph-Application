package Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static Graph.GraphConstants.*;

public class Graph {

    private double yAxisScalingFactor;
    private FileWriter SVGout;
    private SmartAxisRangeRoundUp smartAxisRangeRoundUp = new SmartAxisRangeRoundUp();

    public void generateSVGLineGraph(GraphData graphData) {
        graphData = smartAxisRangeRoundUp.checkAxisRangeValues(graphData);
        Map<String, List<Point>> mapNewRange = checkRangeValuesInMap(graphData);
        yAxisScalingFactor = calculateYAxisScalingFactor(graphData);
        try {
            File SVGOutputFile = new File(graphData.getGraphFileName());
            SVGout = new FileWriter(SVGOutputFile);
            setGraphColorsAndStyle(graphData, mapNewRange);
            closeGraphSVGFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareGraphLegend(GraphData graphData, List<String> colors) throws IOException {
        int colorIterator = 0;
        SVGout.write("\n<g>");
        SVGout.write("\n<text x=\"" + HEIGHT_AT_THE_START_OF_XAXIS * 1.15 + "\" y=\"" + ((END_OF_YAXIS / 2) - TITLE_FONTSIZE) + "\" font-size=\"" + TITLE_FONTSIZE + "\">" + LEGEND + "</text>\"");
        for (String name : graphData.getGraphCourse().keySet()) {
            SVGout.write("\n<text x=\"" + HEIGHT_AT_THE_START_OF_XAXIS * 1.2 + "\" y=\"" + ((END_OF_YAXIS / 2) + (colorIterator * (TITLE_FONTSIZE)) + Y_AXIS_MARGIN) + "\" font-size=\"" + LABEL_FONTSIZE + "\">" + name + "</text>\"");
            SVGout.write("\n<circle cx=\"" + ((HEIGHT_AT_THE_START_OF_XAXIS * 1.2) - (X_AXIS_MARGIN * 2)) + "\" cy=\"" + ((END_OF_YAXIS / 2) + (colorIterator * TITLE_FONTSIZE)) + "\" r=\"" + CIRCLE_RADIUS + "\" stroke=\"" + colors.get(colorIterator) + "\" stroke-width=\"3\" fill=\"" + colors.get(colorIterator) + "\"></circle>");
            colorIterator++;
        }
        SVGout.write("\n</g>");
    }

    private void setGraphColorsAndStyle(GraphData graphData, Map<String, List<Point>> mapNewRange) throws IOException {
        prepareGraphAxis(graphData);
        List<String> colors = new ArrayList<>();
        for (String name : mapNewRange.keySet()) {
            String color = getRandomColor();
            drawPoints(mapNewRange.get(name), color, graphData);
            if (graphData.getStyle() == GraphStyle.DOTTED_LINE) {
                drawPointsWithLines(mapNewRange.get(name), color, graphData);
            }
            colors.add(color);
        }
        prepareGraphLegend(graphData, colors);
    }

    private void drawPoints(List<Point> points, String color, GraphData graphData) throws IOException {
        double XValue;
        double YValue;
        SVGout.write("\n<g>");
        for (Point point : points) {
            XValue = (((point.getX()) / graphData.getXMax()) * SCALING_FACTOR);
            YValue = point.getY() * yAxisScalingFactor;
            YValue = 1000 - YValue;
            SVGout.write("\n<circle cx=\"" + XValue + "\" cy=\"" + YValue + "\" r=\"" + CIRCLE_RADIUS + "\" stroke=\"" + color + "\" stroke-width=\"1\" fill=\"" + color + "\"></circle>");
        }
        SVGout.write("\n</g>");
    }

    private void drawPointsWithLines(List<Point> points, String color, GraphData graphData) throws IOException {
        double XValue;
        double YValue;
        SVGout.write("\n<polyline points=\"");
        for (Point point : points) {
            XValue = (((point.getX()) / graphData.getXMax()) * SCALING_FACTOR);
            YValue = point.getY() * yAxisScalingFactor;
            YValue = 1000 - YValue;
            SVGout.write(" " + XValue + " " + YValue + ",\n");
        }
        SVGout.write("\"\nstyle=\"stroke:" + color + "; stroke-width:3; stroke-linecap:round; stroke-dasharray:1,1;fill:none;\"/>");
    }

    private void closeGraphSVGFile() throws IOException {
        SVGout.write("\n</g>");
        SVGout.write("\n</svg>");
        SVGout.close();
    }

    private void prepareGraphAxis(GraphData graphData) throws IOException {
        SVGout.write("<?xml version=\"1.0\"?>");
        SVGout.write("\n<svg width=\"" + SVG_FILE_WIDTH + "\" height=\"" + SVG_FILE_HEIGHT + "\">");
        SVGout.write("\n<g transform=\"translate(100,100) scale(0.8)\">");
        drawCoordinateAxes();
        drawGraphLabelsAndTitle(graphData);
    }

    private void drawCoordinateAxes() throws IOException {
        SVGout.write("\n<g style=\"stroke-width:5; stroke:black\">");
        //        X Axis
        SVGout.write("\n<path d=\"M " + START_OF_XAXIS + " " + HEIGHT_AT_THE_START_OF_XAXIS + " L " + END_OF_XAXIS + " " + HEIGHT_AT_THE_END_OF_XAXIS + " Z\"></path>");
        //        Y Axis
        SVGout.write("\n<path d=\"M " + HEIGHT_AT_THE_START_OF_YAXIS + " " + START_OF_YAXIS + " L " + HEIGHT_AT_THE_END_OF_YAXIS + " " + END_OF_YAXIS + " Z\"></path>");
        SVGout.write("\n</g>");
    }

    private void drawGraphLabelsAndTitle(GraphData graphData) throws IOException {
        divisionOfCoordinateAxes(graphData);
        SVGout.write("\n<text x=\"" + HEIGHT_AT_THE_START_OF_XAXIS / 2 + "\" y=\"" + (END_OF_XAXIS + LABEL_XAXIS_MARGIN) + "\" font-size=\"" + LABEL_FONTSIZE + "\">" + graphData.getXLabel() + "</text>\"");
        SVGout.write("\n<g transform=\"translate(" + (-(HEIGHT_AT_THE_END_OF_XAXIS / 2) - LABEL_YAXIS_MARGIN) + ", 500) scale(1, 1) rotate(-90)\">");
        SVGout.write("\n<text x=\"" + (HEIGHT_AT_THE_START_OF_YAXIS - LABEL_YAXIS_MARGIN) + "\" y=\"500\" font-size=\"" + LABEL_FONTSIZE + "\">" + graphData.getYLabel() + "</text>\"");
        SVGout.write("\n</g>");
        SVGout.write("\n<text x=\"" + HEIGHT_AT_THE_START_OF_XAXIS / 2 + "\" y=\"" + (START_OF_XAXIS - TITLE_MARGIN) + "\" font-size=\"" + TITLE_FONTSIZE + "\">" + graphData.getTitle() + "</text>");

    }

    private void divisionOfCoordinateAxes(GraphData graphData) throws IOException {
        List<Double> listX = new ArrayList<>();
        List<Double> listY = new ArrayList<>();
        double xAxisScaleValue = graphData.getXMax() / NUMBER_OF_COORDINATE_AXIS;
        double xAxisMaxValue = graphData.getXMax();
        double yAxisScaleValue = graphData.getYMax() / NUMBER_OF_COORDINATE_AXIS;
        double yAxisMaxValue = graphData.getYMax();
        double tempSum;
        listX.add(xAxisMaxValue);
        listY.add(yAxisMaxValue);
        for (int i = 0; i < NUMBER_OF_COORDINATE_AXIS; i++) {
            tempSum = xAxisMaxValue - (xAxisScaleValue);
            xAxisMaxValue = tempSum;
            listX.add(Math.round(xAxisMaxValue * 100) / 100.d);
            tempSum = yAxisMaxValue - (yAxisScaleValue);
            yAxisMaxValue = tempSum;
            listY.add(Math.round(yAxisMaxValue * 100) / 100.d);

        }

        for (int i = listY.size() - 1; i >= 0; i--) {
            SVGout.write("\n <text style=\"fill:black; stroke:none\" x=\"-40\" y=\"" + roundDouble((((listY.get((listY.size() - 1) - i) / graphData.getYMax()) * SCALING_FACTOR)) + Y_AXIS_MARGIN) + "\" >" + roundDouble(((listY.get(i) / graphData.getYMax()) * graphData.getYMax())) + "</text>");
            SVGout.write(" <line x1=\"-10\" y1=\"" + roundDouble((((listY.get(i) / graphData.getYMax()) * SCALING_FACTOR))) + "\" x2=\"10\" y2=\"" + roundDouble(((listY.get(i) / graphData.getYMax()) * SCALING_FACTOR)) + "\" style=\"stroke:black;stroke-width:3;fill:black\"></line>");

        }
        for (int i = 0; i < listX.size() - 1; i++) {
            SVGout.write("\n <text style=\"fill:black; stroke:none\" x=\"" + roundDouble((((listX.get(i) / graphData.getXMax()) * SCALING_FACTOR)) - X_AXIS_MARGIN) + "\" y=\"1030\" >" + roundDouble((listX.get(i) / graphData.getXMax()) * graphData.getXMax()) + "</text>");
            SVGout.write(" <line x1=\"" + roundDouble(((listX.get(i) / graphData.getXMax()) * SCALING_FACTOR)) + "\" y1=\"990\" x2=\"" + roundDouble(((listX.get(i) / graphData.getXMax()) * SCALING_FACTOR)) + "\" y2=\"1010\" style=\"stroke:black;stroke-width:3;fill:black\"></line>");

        }
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

    private double roundDouble(double value) {
        return Math.round(value * 100) / 100d;
    }

    private double calculateYAxisScalingFactor(GraphData graphData) {
        return SCALING_FACTOR / graphData.getYMax();
    }

    public String getRandomColor() {
        final Random random = new Random();
        final String[] letters = "0123456789ABCDEF".split("");
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            color.append(letters[Math.round(random.nextFloat() * 15)]);
        }
        return color.toString();
    }
}
