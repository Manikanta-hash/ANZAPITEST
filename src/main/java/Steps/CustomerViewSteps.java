package Steps;

import helpers.CustomFilterableRequestSpecification;
import helpers.PropertyUtility;
import helpers.RequestOperationsHelper;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomerViewSteps {
    private final CustomFilterableRequestSpecification requestSpecification=new CustomFilterableRequestSpecification();
    private final Map<String,String> pathParams=new HashMap<>();

    public CustomerViewSteps (){
        Properties props = (new PropertyUtility()).loadProperty("config.properties");
        requestSpecification.addBaseURI(props.getProperty("baseURI"));
    }

    public Response getCustomerDetails(String customerId){
        requestSpecification.addBasePath("{customerId}/CustomerView");

        pathParams.put("customerId",customerId);
        requestSpecification.addPathParams(pathParams);

        return new RequestOperationsHelper().sendGetRequest(requestSpecification.getFilterableRequestSpecification());
    }

    public Response getAllCustomerDetails(){
        requestSpecification.addBasePath("Customers");

        return new RequestOperationsHelper().sendGetRequest(requestSpecification.getFilterableRequestSpecification());
    }
}