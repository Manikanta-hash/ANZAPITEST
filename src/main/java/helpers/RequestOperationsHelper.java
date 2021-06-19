package helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.InputStream;
import java.util.Map;
import org.testng.Reporter;

public class RequestOperationsHelper {
    public RequestOperationsHelper() {
    }

    public Response sendRequest(RequestSpecification requestSpecification, ResponseSpecification responseSpecification, Method method) {
        Response response = RestAssured.given().spec(requestSpecification).when().request(method);
        response.then().spec(responseSpecification);
        RestAssuredResponseImpl restAssuredResponse = (RestAssuredResponseImpl)response;
        Reporter.log(restAssuredResponse.getLogRepository().getRequestLog());
        Reporter.log(restAssuredResponse.getLogRepository().getResponseLog());
        return response;
    }

    public Response sendRequest(RequestSpecification requestSpecification, Method method) {
        return this.sendRequest(requestSpecification, RestAssured.given().then(), method);
    }

    public Response sendGetRequest(RequestSpecification requestSpecification) {
        return this.sendRequest(requestSpecification, Method.GET);
    }

}