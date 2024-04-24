package steps;

import com.google.gson.JsonObject;
import cucumber.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import cucumber.ScenarioHelper;
import utilities.TestDataReader;

import java.io.FileNotFoundException;

public class BaseSteps {
    public static TestContext testContext;

    @Before
    public void setUp(Scenario scenario) throws FileNotFoundException {
        testContext = new TestContext();
        String scenarioTag = ScenarioHelper.getTags(scenario).getFirst().substring(1);
        String featureName = ScenarioHelper.getFeatureFileName(scenario);
        JsonObject featureData = TestDataReader.getFeatureData(featureName);
        JsonObject scenarioData = TestDataReader.getScenarioData(featureData, scenarioTag);
        JsonObject jsonSchema = TestDataReader.getJsonSchema(featureName);
        testContext.set("ScenarioData", scenarioData);
        testContext.set("JsonSchema", jsonSchema);
    }
}