package com.cyclonercm.files.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.cyclonercm.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    private static ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        // Step 1: Initialize browser and open target URL
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("baseUrl"));

        // Step 2: Wait for full page load
        WaitUtils.waitUntilPageIsFullyLoaded(driver);

        // Step 3: Wait extra 5 seconds for animations/components to settle
        WaitUtils.sleep(10000);

        // Step 4: Capture full-page screenshot before test execution
        ScreenshotUtil.captureFullPageScreenshot(driver, method.getName());

        // Step 5: Initialize Extent Report if null
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }

        // Step 6: Create test log
        test = extent.createTest(method.getName());
        ExtentTestManager.setTest(test);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        try {
            // ✅ Capture screenshot for every test
            String screenshotPath = ScreenshotUtil.captureFullPageScreenshot(driver, testName);

            if (result.getStatus() == ITestResult.FAILURE) {
                test.fail("❌ Test Failed: " + result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("⚠️ Test Skipped: " + result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                test.pass("✅ Test Passed",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }

        } catch (Exception e) {
            test.warning("Could not attach screenshot to report: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
            ExtentTestManager.removeTest();
            extent.flush();
        }
    }



    protected void click(By locator) {
        driver.findElement(locator).click();
    }


    protected boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

