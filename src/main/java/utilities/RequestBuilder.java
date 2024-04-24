package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.ResponseOptions;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import java.util.*;

public class RequestBuilder {

    private RequestSpecBuilder builder = new RequestSpecBuilder();
    private String method;
    private String url;

    public RequestBuilder(String method) {
        this.method = method;
    }

    public RequestBuilder WithUrl(String baseUrl,String uriPath) {
        this.url = baseUrl + uriPath;
        return this;
    }

    public RequestBuilder WithBody(Object body) {
        this.builder.setBody(body);
        return this;
    }

    public RequestBuilder WithQueryParams(Map<String, String> queryPath) {
        this.builder.addQueryParams(queryPath);
        return this;
    }

    public RequestBuilder WithPathParams(Map<String, String> queryPath) {
        this.builder.addPathParams(queryPath);
        return this;
    }

    public RequestBuilder WithHeaders(Map<String, String> headers) {
        this.builder.addHeaders(headers);
        return this;
    }

    public ResponseOptions<Response> Execute() {
        RequestSpecification requestSpec = this.builder.build();
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpec);

        if (this.method.equalsIgnoreCase("POST")) {
            return request.post(this.url);
        } else if (this.method.equalsIgnoreCase("GET")) {
            return request.get(this.url);
        }
        return null;
    }
}
