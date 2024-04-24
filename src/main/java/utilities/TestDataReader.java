package utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestDataReader {
    public static JsonObject getFeatureData(String featureName) throws FileNotFoundException {
        if (StringHelper.isNotEmptyOrNull(featureName)) {
            String relativeFilePath = "src/test/resources/expectedResponse/" + featureName + ".json";
            String projectRootPath = System.getProperty("user.dir"); // Get the project's root directory
            String absoluteFilePath = projectRootPath + File.separator + relativeFilePath;
            File file = new File(absoluteFilePath);
            if (file.exists()) {
            FileReader fileReader = new FileReader(absoluteFilePath);
            JsonObject object = (JsonObject) JsonParser.parseReader(fileReader);
            return object;
            }
        }
        return null;
    }

    public static JsonObject getScenarioData(JsonObject featureData, String scenarioTag) {
        if (featureData != null && !featureData.isJsonNull() && StringHelper.isNotEmptyOrNull(scenarioTag)) {
            JsonElement scenarioElement = featureData.get(scenarioTag);
            if (scenarioElement != null && !scenarioElement.isJsonNull() && scenarioElement.isJsonObject()) {
                return scenarioElement.getAsJsonObject();
            }
        }
        return null;
    }

    public static JsonObject getJsonSchema(String featureName) throws FileNotFoundException {
        if (StringHelper.isNotEmptyOrNull(featureName)) {
            String relativeFilePath = "src/test/resources/jsonSchema/" + featureName + ".Schema.json";
            String projectRootPath = System.getProperty("user.dir"); // Get the project's root directory
            String absoluteFilePath = projectRootPath + File.separator + relativeFilePath;
            File file = new File(absoluteFilePath);
            if (file.exists()) {
                FileReader fileReader = new FileReader(absoluteFilePath);
                JsonObject object = (JsonObject) JsonParser.parseReader(fileReader);
                return object;
            }
        }
        return null;
    }
}
