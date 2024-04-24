package cucumber;

import io.cucumber.java.Scenario;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScenarioHelper {
    public static List<String> getTags(Scenario scenario) {
        return scenario.getSourceTagNames().stream().collect(Collectors.toList());
    }

    public static String getFeatureFileName(Scenario scenario){
        String featureUri = scenario.getUri().toString();
        String[] featureUriSplitBySlash = featureUri.toString().split("/");
        String featureFileName = featureUriSplitBySlash[featureUriSplitBySlash.length - 1];
//        String featureFileName = Arrays.stream(featureUriSplitBySlash).toList().getLast();
        return featureFileName.split("\\.")[0];
    }
}
