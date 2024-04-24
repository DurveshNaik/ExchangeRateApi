package utilities;

import java.io.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Configs {
    public static String getConfig(String env, String urlKey) throws FileNotFoundException {
        String relativeFilePath = "src/main/resources/Configurations.json";
        String projectRootPath = System.getProperty("user.dir"); // Get the project's root directory
        String absoluteFilePath = projectRootPath + File.separator + relativeFilePath;
        FileReader fileReader = new FileReader(absoluteFilePath);
        JsonObject object = (JsonObject) JsonParser.parseReader(fileReader);

        if (StringHelper.isNotEmptyOrNull(env) && StringHelper.isNotEmptyOrNull(urlKey)) {
            JsonObject allConfigs = object.get(env).getAsJsonObject();
            return allConfigs.get(urlKey).getAsString();
        }
        return null;
    }
}
