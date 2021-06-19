import helpers.PropertyUtility;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void initSuite(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Properties props=(new PropertyUtility()).loadProperty("config.properties");

        String logEnabled = System.getProperty("isLogEnabled");
        if (logEnabled == null) {
            logEnabled = props.getProperty("isLogEnabled");
        }

        boolean isLogEnabled = Boolean.parseBoolean(logEnabled);
        if (isLogEnabled) {
            RestAssured.replaceFiltersWith(new RequestLoggingFilter(LogDetail.ALL), new Filter[]{new ResponseLoggingFilter(LogDetail.ALL)});
        } else {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }
}
