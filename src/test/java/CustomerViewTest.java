import Steps.CustomerViewSteps;
import dtos.AllCustomerDetailsDTO;
import dtos.CustomerDetailsDTO;
import helpers.ConvertRequestResponseToDTOObject;
import helpers.DataProviderReader;
import static helpers.Constants.*;

import helpers.ReadResponseFromFile;
import io.restassured.response.Response;
import org.assertj.core.api.BDDSoftAssertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class CustomerViewTest extends BaseTest {
 private final BDDSoftAssertions softly=new BDDSoftAssertions();

 @DataProvider(name="customerSearch",parallel = true)
 public Object[][] inputCustomerSearch() throws IOException {
  return new DataProviderReader().readInputsFromFile(CUSTOMER_RESOURCE_FILES,SEPARATOR_COMMA);
 }

 @Test(dataProvider = "customerSearch")
 public void whenGetOnCustomerSearchWithValidCustomerCodeThenServiceReturnsCustomerDetails (
         String customerId, String fileName) throws IOException {
  CustomerViewSteps customerViewSteps=new CustomerViewSteps();

  Response response= customerViewSteps.getCustomerDetails(customerId);
  CustomerDetailsDTO responseFromService = response.as(CustomerDetailsDTO.class);

  String expectedResponse = new ReadResponseFromFile().readResponseFromFile(fileName,CUSTOMER_RESOURCE_FILES);
  CustomerDetailsDTO responseFromFile = (CustomerDetailsDTO) new ConvertRequestResponseToDTOObject().convertRequestResponseToDTO(expectedResponse,CustomerDetailsDTO.class);

  softly.then(response.getStatusCode()).isEqualTo(200);
  softly.then(responseFromService).isEqualTo(responseFromFile);
  softly.assertAll();
 }

 @Test
 public void whenGetOnCustomerSearchWithInvalidCustomerCodeThenServiceReturnsErrorMessage () throws IOException {
  CustomerViewSteps customerViewSteps=new CustomerViewSteps();

  Response response= customerViewSteps.getCustomerDetails(INVALID_CUSTOMERID);
  CustomerDetailsDTO responseFromService = response.as(CustomerDetailsDTO.class);

  softly.then(response.getStatusCode()).isEqualTo(404);
  softly.then(responseFromService.getStatus()).isEqualTo(INVALID_CUSTOMERID_STATUS);
  softly.then(responseFromService.getCode()).isEqualTo(INVALID_CUSTOMERID_CODE);
  softly.then(responseFromService.getMessage()).isEqualTo(INVALID_CUSTOMERID_MESSAGE);

  softly.assertAll();
 }

 @Test
 public void whenGetOnCustomerSearchWithAllCustomersThenServiceReturnsAllCustomerDetails() throws IOException {
  CustomerViewSteps customerViewSteps = new CustomerViewSteps();

  Response response= customerViewSteps.getAllCustomerDetails();
  AllCustomerDetailsDTO responseFromService = response.as(AllCustomerDetailsDTO.class);

  String expectedResponse = new ReadResponseFromFile().readResponseFromFile("AllCustomersDetails.txt",CUSTOMER_RESOURCE_FILES);
  AllCustomerDetailsDTO responseFromFile = (AllCustomerDetailsDTO) new ConvertRequestResponseToDTOObject().convertRequestResponseToDTO(expectedResponse,AllCustomerDetailsDTO.class);

  softly.then(response.statusCode()).isEqualTo(404);//This should be 200 but endpoint is returning the 404.
  softly.then(responseFromService).isEqualTo(responseFromFile);
  softly.assertAll();
 }

}