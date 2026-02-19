package com.cyclonercm.billing.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.cyclonercm.utils.DriverFactory;
import com.cyclonercm.utils.ConfigReader;
import com.cyclonercm.utils.WaitUtils;
import com.cyclonercm.utils.ScreenshotUtil;
import com.cyclonercm.utils.ExtentManager;
import com.cyclonercm.utils.ExtentTestManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class SmokeBaseTest {
    protected WebDriver driver;
    private static ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        // Step 1: Initialize browser and open target URL with retry
        int maxRetries = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                DriverFactory.initDriver();
                driver = DriverFactory.getDriver();
                driver.get(ConfigReader.get("SmokeTestBaseUrl"));

                // Step 2: Wait for full page load
                WaitUtils.waitUntilPageIsFullyLoaded(driver);
                success = true;
            } catch (Exception e) {
                attempt++;
                System.out.println("Browser initialization attempt " + attempt + " failed: " + e.getMessage());

                // Clean up failed driver
                try {
                    DriverFactory.quitDriver();
                } catch (Exception ignored) {}

                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to initialize browser after " + maxRetries + " attempts", e);
                }

                // Wait before retry
                WaitUtils.sleep(3000);
            }
        }

        // Step 3: Wait extra time for animations/components to settle
        WaitUtils.sleep(5000);

        // Step 4: Capture full-page screenshot before test execution
        try {
            ScreenshotUtil.captureFullPageScreenshot(driver, method.getName());
        } catch (Exception e) {
            System.out.println("Warning: Failed to capture screenshot: " + e.getMessage());
        }

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
