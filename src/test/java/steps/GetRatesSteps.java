package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import io.cucumber.java.en.Given;
import io.cucumber.java.ParameterType;
import validation.BaseValidationService;
import utilities.Configs;
import utilities.RequestBuilder;
import validation.GetRatesService;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class GetRatesSteps {
    public static ResponseOptions<Response> response;
    public static String env = "qa";
    public String baseUrl = "";
    public String uriPath = "";
    public static JsonObject actualResponse;
    public static JsonObject expectedResponse;
    public static JsonObject jsonSchema;

    @ParameterType(".*") // Matches any string
    public String anyString(String value) {
        return value;
    }

    @Given("I perform {anyString} call to {anyString} api for {anyString} currency")
    public void GetOperation(String method, String uriName,String currency) throws FileNotFoundException {
        currency = (Objects.equals(currency, "Empty")) ? "%^" : currency;
        System.out.println(currency);
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put("currency", currency);
        response = new RequestBuilder(method).
                WithUrl(Configs.getConfig(env, "BaseUrl"), Configs.getConfig(env, uriName)).
                WithPathParams(pathMap).
                Execute();
        BaseSteps.testContext.set("getRatesResponse", response);
//        System.out.println(response.getBody().asString());
//        System.out.println(response.getStatusCode());
    }

    @Then("I verify success http status code and status message")
    public void VerifySuccessStatusCodeMessage() {
        //API call is successful and returns valid price.
        response = (ResponseOptions<Response>) BaseSteps.testContext.get("getRatesResponse");
        BaseValidationService.validateSuccessStatus(response);
    }

    @Then("I verify rates retrieved for all currencies")
    public void VerifyRatesForAllCurrencies() throws ParseException {
        //Verify that 162 currency pairs are returned by the API
        //Rates are returned within a currency range
        response = (ResponseOptions<Response>) BaseSteps.testContext.get("getRatesResponse");
        actualResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        expectedResponse = (JsonObject) BaseSteps.testContext.get("ScenarioData");
        GetRatesService.validateRatesRange(actualResponse, expectedResponse);
    }

    @Then("I verify response schema")
    public void VerifyResponseSchema() throws ParseException {
        //Validate Response Schema
        response = (ResponseOptions<Response>) BaseSteps.testContext.get("getRatesResponse");
        actualResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        jsonSchema = (JsonObject) BaseSteps.testContext.get("JsonSchema");

        BaseValidationService.validateJsonSchema(actualResponse, jsonSchema);
    }

    @Then("I verify failure http status code and status message")
    public void VerifyFailureStatusCodeMessage() {
        //API call failed and returned error response
        response = (ResponseOptions<Response>) BaseSteps.testContext.get("getRatesResponse");
        BaseValidationService.validateFailureStatus(response);
    }

    @Then("I verify error response")
    public void VerifErrorResponse() throws ParseException {
        //Error message has returned in response
        response = (ResponseOptions<Response>) BaseSteps.testContext.get("getRatesResponse");
        actualResponse = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        expectedResponse = (JsonObject) BaseSteps.testContext.get("ScenarioData");
        GetRatesService.validateErrorResponse(actualResponse, expectedResponse);
    }
}
