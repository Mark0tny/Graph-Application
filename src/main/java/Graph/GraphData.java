package Graph;

import lombok.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GraphData {
    private String dataFilePath;
    private String graphFileName;
    private String title;
    private String xLabel;
    private String yLabel;
    private GraphStyle style;
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;
    private Map<String,List<Point>> graphCourse;
}


