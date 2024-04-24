package validation;

import com.google.gson.JsonObject;
import models.CompareResult;
import org.testng.Assert;
import java.text.ParseException;


public class GetRatesService {
    public static void validateRatesRange(JsonObject actualResponse, JsonObject expectedResponse) throws ParseException {
        Assert.assertEquals(actualResponse.get("result"), expectedResponse.get("result"));
        Assert.assertEquals(actualResponse.get("base_code"), expectedResponse.get("base_code"));

        JsonObject actualRates = (JsonObject)actualResponse.get("rates");
        JsonObject expectedRatesRange = (JsonObject)expectedResponse.get("rates-range");

        Assert.assertEquals(actualRates.entrySet().size(),expectedRatesRange.entrySet().size());
        CompareResult compareResult =  compareCurrencyRange(expectedRatesRange,actualRates);
        Assert.assertEquals(compareResult.resultFlag,true,compareResult.errorMessage);
    }

    private static CompareResult compareCurrencyRange(JsonObject obj1, JsonObject obj2) throws ParseException {
        Boolean validationFailed = false;
        CompareResult compareResult = new CompareResult();
        String errMsg = "| ";
        for (String key : obj1.keySet()) {
            if(!obj2.has(key)){
                errMsg += "Currency "+key+" not found in actual response. | \n";
                validationFailed = true;
                continue;
            }

            String[] value1Range = obj1.get(key).getAsString().split("-");
            double value2 = Double.parseDouble(obj2.get(key).toString());
            if (value1Range.length > 1) {
                double minValue1 = Double.parseDouble(value1Range[0]);
                double maxValue1 = Double.parseDouble(value1Range[1]);
                if (!(value2 >= minValue1 && maxValue1 >= value2)){
                    validationFailed = true;
                    errMsg += "Currency rate for " + key + " is not in defined range. | \n";
                }
            } else if(value1Range.length == 1) {
                if (Double.parseDouble(value1Range[0]) != value2) {
                    validationFailed = true;
                    errMsg += "Currency rate for " + key + " does not match with expected. | \n";
                }
            }
        }
        compareResult.resultFlag = validationFailed ? false : true;
        compareResult.errorMessage = errMsg;
        return compareResult;
    }

    public static void validateErrorResponse(JsonObject actualResponse, JsonObject expectedResponse) throws ParseException {
        Assert.assertEquals(actualResponse, expectedResponse);
    }
}