package Graph;

import org.apache.commons.cli.*;

public class CommandLineArgsParser {

    private final static String ERROR_MESSAGE = "Missing argument: ";
    private CsvFileParser csvFileParser = new CsvFileParser();
    private Options options = new Options();
    private CommandLineParser parser = new DefaultParser();
    private HelpFormatter formatter = new HelpFormatter();
    private CommandLine cmd;

    private void parseCommandLineArgs(String[] args) {
        options.addOption("csv", true, "CSV file with data");
        options.addOption("svg", true, "SVG output filename");
        options.addOption("title", true, "Graph title");
        options.addOption("xLabel", true, "Graph X label");
        options.addOption("yLabel", true, "Graph Y label");
        options.addOption("style", true, "Graph style type DOTS or DOTTED_LINE");
        Option optionXMin = new Option("xMin", true, "Label x min value");
        optionXMin.setRequired(false);
        Option optionYMin = new Option("yMin", true, "Label y min value");
        optionYMin.setRequired(false);
        Option optionXMax = new Option("xMax", true, "Label x max value");
        optionXMax.setRequired(false);
        Option optionYMax = new Option("yMax", true, "Label y max value");
        optionYMax.setRequired(false);
        options.addOption(optionXMin);
        options.addOption(optionYMin);
        options.addOption(optionXMax);
        options.addOption(optionYMax);

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Command Line Helper", options);
        }

    }

    public GraphData createGraphData(String[] args) {
        parseCommandLineArgs(args);
        GraphData graphData = new GraphData();

        if (cmd.hasOption("csv")) {
            graphData.setDataFilePath(cmd.getOptionValue("csv"));
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE + "csv");
        }
        if (cmd.hasOption("svg")) {
            graphData.setGraphFileName(cmd.getOptionValue("svg"));
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE + "svg");
        }
        if (cmd.hasOption("title")) {
            graphData.setTitle(cmd.getOptionValue("title"));
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE + "title");
        }
        if (cmd.hasOption("xLabel")) {
            graphData.setXLabel(cmd.getOptionValue("xLabel"));
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE + "xLabel");
        }
        if (cmd.hasOption("yLabel")) {
            graphData.setYLabel(cmd.getOptionValue("yLabel"));
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE + "yLabel");
        }
        if (cmd.hasOption("style")) {
            if (cmd.getOptionValue("style").equals("DOTS")) {
                graphData.setStyle(GraphStyle.DOTS);
            } else if (cmd.getOptionValue("style").equals("DOTTED_LINE")) {
                graphData.setStyle(GraphStyle.DOTTED_LINE);
            } else {
                formatter.printHelp("Command Line Helper", options);
                throw new IllegalArgumentException(ERROR_MESSAGE + "wrong style");
            }
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE + "style");
        }

        if (cmd.hasOption("xMin")) {
            graphData.setXMin(Double.parseDouble(cmd.getOptionValue("xMin")));
        } else {
            graphData.setXMin(Double.NaN);
        }
        if (cmd.hasOption("xMax")) {
            graphData.setXMax(Double.parseDouble(cmd.getOptionValue("xMax")));
        } else {
            graphData.setXMax(Double.NaN);
        }
        if (cmd.hasOption("yMin")) {
            graphData.setYMin(Double.parseDouble(cmd.getOptionValue("yMin")));
        } else {
            graphData.setYMin(Double.NaN);
        }
        if (cmd.hasOption("yMax")) {
            graphData.setYMax(Double.parseDouble(cmd.getOptionValue("yMax")));
        } else {
            graphData.setYMax(Double.NaN);
        }
        graphData.setGraphCourse(csvFileParser.getGraphCourseFormFile(cmd.getOptionValue("csv")));
        checkIsGraphDataIsCorrect(graphData);
        return graphData;
    }

    private void checkIsGraphDataIsCorrect(GraphData graphData) {
        if (graphData.getYMin() < 0 || graphData.getYMax() < 0 || graphData.getXMin() < 0 || graphData.getXMax() < 0) {
            throw new IllegalArgumentException("Wrong X or Y value - it cant be less than zero");
        }
        if (graphData.getXMax() < graphData.getXMin()) {
            throw new IllegalArgumentException("Wrong X range values");
        }
        if (graphData.getYMax() < graphData.getYMin()) {
            throw new IllegalArgumentException("Wrong Y range values");
        }
        if (graphData.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title must contain at least one letter");
        }
        if(graphData.getXLabel().isEmpty() || graphData.getYLabel().isEmpty()){
            throw new IllegalArgumentException("Labels must contain at least one letter");
        }
        if (graphData.getGraphFileName().isEmpty()) {
            throw new IllegalArgumentException("Output file must have a name");
        }
        if (graphData.getDataFilePath().isEmpty()) {
            throw new IllegalArgumentException("No given file");
        }
    }
}
