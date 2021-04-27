package Graph;

import java.util.Comparator;

public class SmartAxisRangeRoundUp {

    public GraphData checkAxisRangeValues(GraphData graphData) {
        if (Double.isNaN(graphData.getXMin())) {
            graphData.setXMin(roundDown(findMinX(graphData)));
        }
        if (Double.isNaN(graphData.getXMax())) {
            graphData.setXMax(roundUp(findMaxX(graphData)));
        }
        if (Double.isNaN(graphData.getYMin())) {
            graphData.setYMin(roundDown(findMinY(graphData)));
        }
        if (Double.isNaN(graphData.getYMax())) {
            graphData.setYMax(roundUp(findMaxY(graphData)));
        }
        return graphData;
    }

    public double roundUp(double value) {
        if (value % 10 > 5) {
            value += (10 - value % 10);
        } else if (value % 10 > 0) {
            value += (5 - value % 5);
        }
        return value;
    }

    public double roundDown(double value) {
        if (value % 10 > 5) {
            value -= (value % 5);
        } else if (value % 10 > 0) {
            value -= (value % 10);
        }
        if(value < 0){
            return 0;
        }
        return value;
    }

    private double findMinX(GraphData graphData) {
        return graphData.getGraphCourse().values().stream().mapToDouble(a -> a.stream().min(Comparator.comparing(Point::getX)).get().getX()).min().getAsDouble();
    }

    private double findMaxX(GraphData graphData) {
        return graphData.getGraphCourse().values().stream().mapToDouble(a -> a.stream().max(Comparator.comparing(Point::getX)).get().getX()).max().getAsDouble();
    }

    private double findMinY(GraphData graphData) {
        return graphData.getGraphCourse().values().stream().mapToDouble(a -> a.stream().min(Comparator.comparing(Point::getY)).get().getY()).min().getAsDouble();
    }

    private double findMaxY(GraphData graphData) {
        return graphData.getGraphCourse().values().stream().mapToDouble(a -> a.stream().max(Comparator.comparing(Point::getY)).get().getY()).max().getAsDouble();
    }
}
