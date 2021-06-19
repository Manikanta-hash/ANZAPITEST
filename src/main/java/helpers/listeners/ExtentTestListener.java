package helpers.listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class ExtentTestListener implements ITestListener {

    private ConsoleOutputStream consoleOutput = new ConsoleOutputStream();
    private ExtentRepotSetup extentRepotSetup = new ExtentRepotSetup();
    private ExtentTestReport extentTestReport = new ExtentTestReport();
    private String consoleSeparator = "&&&&";
    private String testMethodName = "Method name=:  ";
    private String testClassName = "   ,Class name=: ";
    private String testParamUsed = "Parameter =:  ";

    public String methodName(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    public String className(ITestResult result) {
        return result.getMethod().getTestClass().getName();
    }

    public void writeStatusToReport(Status status, String info) {
        extentTestReport.getTest().log(status, info);
    }

    public void onStart(ITestContext context) {
        System.out.println("*** TEST SUITE " + context.getName() + " started ***");
        extentTestReport.getExtent().getStats();
    }

    public void onFinish(ITestContext context) {
        System.out.println(("*** TEST SUITE " + context.getName() + " ending ***"));
        extentTestReport.endTest();
    }

    public void onTestStart(ITestResult result) {
        System.out.println(("*** RUNNING TEST METHOD " + methodName(result) + "..."));
        consoleOutput.start();
        System.out.println("\r\n");
        System.out.println(consoleSeparator);

        extentRepotSetup.enableLogging();

        extentTestReport.startTest((className(result) + " :: " + methodName(result)).concat(" [").concat(extentRepotSetup.getParameter(result)).concat("]"));
        extentTestReport.getTest().assignCategory(className(result));


    }

    public void onTestFailure(ITestResult result) {
        extentTestReport.getTest().fail(MarkupHelper.createLabel(" - Test Case Failed", ExtentColor.RED));
        writeStatusToReport(Status.FAIL, testMethodName + methodName(result) + testClassName + className(result));
        writeStatusToReport(Status.FAIL, testParamUsed + extentRepotSetup.getParameter(result));


        System.out.println(" : Test execution failed " + methodName(result));
        System.out.println(consoleSeparator);

        try {
            String[] outPutData = consoleOutput.stop().split(consoleSeparator);
            for (String outMessage : outPutData) {
                if (outMessage.contains("Test execution failed")) {
                    extentTestReport.getTest().fail(new AssertionError("\n" + outMessage + "\n" + result.getThrowable()));
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void onTestSuccess(ITestResult result) {
        extentTestReport.getTest().pass(MarkupHelper.createLabel(" - Test Case Passed", ExtentColor.GREEN));
        writeStatusToReport(Status.PASS, testMethodName + methodName(result) + testClassName + className(result));
        writeStatusToReport(Status.PASS, testParamUsed + extentRepotSetup.getParameter(result));


        System.out.println(String.format("NUMBER OF SUCCESSFULLY EXECUTED TEST CASES: %d. TEST CASE: %s IN %s",
                result.getTestContext().getPassedTests().size() + 1,
                result.getName(),
                result.getTestClass().getName()));
    }


    public void onTestSkipped(ITestResult result) {
        extentTestReport.getTest().warning(MarkupHelper.createLabel(" - Test Case Skipped", ExtentColor.ORANGE));
        writeStatusToReport(Status.SKIP, testMethodName + methodName(result) + testClassName + className(result));
        writeStatusToReport(Status.SKIP, result.getThrowable().getMessage());

        System.out.println("TEST CASE SKIPPED: NUMBER OF SKIPPED TEST CASES");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("TEST FAILED BUT WITHIN PERCENTAGE % ");

    }
}