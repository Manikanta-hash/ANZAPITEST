package helpers.listeners;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.ExtentReports;
import helpers.PropertyUtility;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.io.*;
import org.testng.ITestResult;

import java.io.File;
import java.util.Properties;

public class ExtentRepotSetup {

    private static String reportFileName = "Test-Automaton-Extent-Report" + ".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") + "/build" + fileSeperator + "extentreport";
    private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;
    private static ExtentReports reports;
    private static Properties props;
    private static String parameter;

    public String getParameter(ITestResult result) {
        StringBuilder params = new StringBuilder();

        Object[] var = result.getParameters();
        int var1 = var.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            Object param = var[var2];
            params.append(param).append(' ');
        }
        return parameter = params.toString().trim();
    }

    public static ExtentReports getInstance() {
        ExtentRepotSetup setUp = new ExtentRepotSetup();
        if (reports == null)
            setUp.createInstance();
        return reports;
    }


    public void enableLogging() {
        String logEnabled;
        logEnabled = System.getProperty("isLogEnabled");
        props = (new PropertyUtility()).loadProperty("config.properties");
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


    public static ExtentReports createInstance() {

        String fileName = getReportFilePath(reportFilepath);

        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(fileName);
        extentHtmlReporter.config().setDocumentTitle(reportFileName);
        extentHtmlReporter.config().setEncoding("utf-8");
        extentHtmlReporter.config().setReportName(reportFileName);
        extentHtmlReporter.config().setTimeStampFormat("MMMM dd, yyyy, hh:mm a '('zzz')'");
        extentHtmlReporter.config().setTheme(Theme.STANDARD);
        extentHtmlReporter.setAnalysisStrategy(AnalysisStrategy.CLASS);
        extentHtmlReporter.setAnalysisStrategy(AnalysisStrategy.TEST);

        RestAssured.baseURI = System.getProperty("baseURI");
        Properties props = (new PropertyUtility()).loadProperty("config.properties");
        if (RestAssured.baseURI == null || RestAssured.baseURI.isEmpty()) {
            RestAssured.baseURI = props.getProperty("baseURI");
        }

        reports = new ExtentReports();
        reports.attachReporter(extentHtmlReporter);

        reports.getStartedReporters();
        reports.setSystemInfo("Test User", System.getProperty("user.name"));
        reports.setSystemInfo("Operating System Type", System.getProperty("os.name"));

            try {
                String domainName = RestAssured.baseURI.split("\\.")[0].split("//")[1].toUpperCase();
                if (domainName.equalsIgnoreCase("WEB")) {
                    reports.setSystemInfo("Application Name", (RestAssured.baseURI.split("\\."))[1].toUpperCase() + "_Domain_Service");
                } else if (!domainName.equalsIgnoreCase("WEB")) {
                    reports.setSystemInfo("Application Name", domainName + "_Domain_Service");

                } else {
                    reports.setSystemInfo("Application Name", "Check the BaseUri");

                }
                reports.setSystemInfo("baseURI", RestAssured.baseURI);
            } catch (Exception e) {
                e.getMessage();
            }

        return reports;
    }

    private static String getReportFilePath(String path) {
        File testDirectory = new File(FilenameUtils.getName(path));
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("DIRECTORY: " + path + " IS CREATED!");
                return reportFileLocation;
            } else {
                System.out.println("FAILED TO CREATE DIRECTORY " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("DIRECTORY ALREADY EXISTS: " + path);
        }
        return reportFileLocation;
    }
}
