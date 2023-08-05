package testcase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import utility.Utilities;

import java.io.IOException;
import java.util.Random;
public class Testbase {
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    private static String PROJECT_NAME = "RestfulBooker";
    Faker faker=new Faker();
    ObjectMapper mapper=new ObjectMapper();
    JSONObject bookingBody=new JSONObject();
    JSONObject bookingDate=new JSONObject();
    JSONArray data=new JSONArray();
    @BeforeSuite
    public void defineSuiteElements() throws IOException {
        // initialize the HtmlReporter
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //setProjectDetails();

        // initialize test
        test = extent.createTest(PROJECT_NAME + " Test Automation Project");

        //configuration items to change the look and fee add content, manage tests etc
        htmlReporter.config().setDocumentTitle(PROJECT_NAME + " Test Automation Report");
        htmlReporter.config().setReportName(PROJECT_NAME + " Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }
    @BeforeTest
    public void setBaseURL(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
    }



        public static int generateRandomInteger(int min, int max) {
            Random random = new Random();
            return random.nextInt(max - min + 1) + min;
        }

    // extend report
    @AfterSuite
    public void tearDown() throws IOException {
        extent.flush();
        //start html report after test end
        Utilities.startHtmlReport(System.getProperty("user.dir"), "testReport.html");
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getName() + " failed with the following error: " + result.getThrowable());
            Reporter.log("Failed to perform "+result.getName(), 10, true);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getName());
            Reporter.log("Successfully perform "+result.getName(), 10, true);
        } else {
            test.log(Status.SKIP, result.getName());
            Reporter.log("Skip "+result.getName(), 10, true);
        }
    }

}

