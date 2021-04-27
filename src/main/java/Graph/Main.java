package Graph;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();
        CommandLineArgsParser commandLineParser = new CommandLineArgsParser();
        graph.generateSVGLineGraph(commandLineParser.createGraphData(args));
    }
}
