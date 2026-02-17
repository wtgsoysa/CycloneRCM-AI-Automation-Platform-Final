package com.cyclonercm.billing.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.cyclonercm.utils.ConfigReader;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base Test Class for Billing Service Test Automation
 * Provides WebDriver setup, reporting, and common utilities
 */
public class BaseTest {

    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected static Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void beforeSuite() {
        setupExtentReports();
        logger.info("========== Billing Service Test Suite Started ==========");
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser, ITestResult result) {
        driver = initializeDriver(browser);
        test = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        String url = ConfigReader.get("baseURL");
        driver.get(url);
        driver.manage().window().maximize();

        logger.info("Test Started: " + result.getMethod().getMethodName());
        test.info("Browser: " + browser);
        test.info("URL: " + url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result.getMethod().getMethodName());
            test.fail("Test Failed: " + result.getThrowable());
            logger.error("Test Failed: " + result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed Successfully");
            logger.info("Test Passed: " + result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Skipped: " + result.getThrowable());
            logger.warn("Test Skipped: " + result.getMethod().getMethodName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
        logger.info("========== Billing Service Test Suite Completed ==========");
    }

    /**
     * Initialize WebDriver based on browser parameter
     */
    private WebDriver initializeDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--start-maximized");
                webDriver = new ChromeDriver(chromeOptions);
                logger.info("Chrome browser initialized");
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-notifications");
                webDriver = new FirefoxDriver(firefoxOptions);
                logger.info("Firefox browser initialized");
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-notifications");
                webDriver = new EdgeDriver(edgeOptions);
                logger.info("Edge browser initialized");
                break;

            default:
                ChromeOptions defaultOptions = new ChromeOptions();
                defaultOptions.addArguments("--disable-notifications");
                webDriver = new ChromeDriver(defaultOptions);
                logger.warn("Unknown browser: " + browser + ". Using Chrome as default");
        }

        return webDriver;
    }

    /**
     * Setup Extent Reports
     */
    private void setupExtentReports() {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = System.getProperty("user.dir") +
                "/consolidated-reports/BillingService_Test_Report_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Billing Service Test Automation Report");
        sparkReporter.config().setReportName("Billing Service - Smoke & Regression Test Report");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "CycloneRCM - Billing Service");
        extent.setSystemInfo("Environment", ConfigReader.get("environment"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        logger.info("Extent Report initialized: " + reportPath);
    }

    /**
     * Capture screenshot for failed tests
     */
    private void captureScreenshot(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // For Extent Reports
            String base64Screenshot = screenshot.getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(base64Screenshot, testName);

            // For Allure Reports
            Allure.addAttachment(testName, new ByteArrayInputStream(screenshotBytes));

            logger.info("Screenshot captured for: " + testName);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Get WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Get ExtentTest instance
     */
    public ExtentTest getTest() {
        return test;
    }
}
