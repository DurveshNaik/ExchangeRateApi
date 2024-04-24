package validation;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.util.List;


public class BaseValidationService {
    public static void validateSuccessStatus(ResponseOptions<Response> response) {
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
    }

    public static void validateFailureStatus(ResponseOptions<Response> response) {
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 404 Not Found");
    }

    public static void validateJsonSchema(JsonObject response, JsonObject schema){
        JSONTokener schemaTokener = new JSONTokener(schema.toString());
        JSONObject jsonSchema = new JSONObject(schemaTokener);
        Schema schemaValidator = SchemaLoader.load(jsonSchema);

        JSONTokener responseTokener = new JSONTokener(response.toString());
        JSONObject jsonResponse = new JSONObject(responseTokener);

        try {
            schemaValidator.validate(jsonResponse);
        } catch (ValidationException e) {
            List<String> validationMessages = e.getAllMessages();
            String errorMessage = String.join(",", validationMessages);
            Assert.assertFalse(true,errorMessage);
        }
    }
}
