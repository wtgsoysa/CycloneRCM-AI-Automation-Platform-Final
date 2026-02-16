package com.cyclonercm.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.*;

import java.util.Arrays;

/**
 * TestNG Listener for Centralized Reporting
 * Ensures all test modules write to the same consolidated report
 */
public class CentralizedReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        // Initialize centralized report once for all suites
        if (extent == null) {
            extent = CentralizedExtentManager.getInstance();
            System.out.println("📊 Centralized Report Initialized for Suite: " + suite.getName());
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("✅ Suite Completed: " + suite.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        String moduleName = getModuleName(className);

        // Create test node with module information
        ExtentTest extentTest = extent.createTest(testName)
            .assignCategory(moduleName)
            .assignAuthor("CycloneRCM QA Team");

        test.set(extentTest);

        extentTest.info("📋 Test Class: " + className);
        extentTest.info("📦 Module: " + moduleName);
        extentTest.info("🚀 Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.log(Status.PASS, MarkupHelper.createLabel("✅ TEST PASSED", ExtentColor.GREEN));
        extentTest.pass("Test Duration: " + getExecutionTime(result) + " ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.log(Status.FAIL, MarkupHelper.createLabel("❌ TEST FAILED", ExtentColor.RED));
        extentTest.fail(result.getThrowable());
        extentTest.fail("Test Duration: " + getExecutionTime(result) + " ms");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.log(Status.SKIP, MarkupHelper.createLabel("⚠️ TEST SKIPPED", ExtentColor.YELLOW));
        extentTest.skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush report after each test context
        if (extent != null) {
            extent.flush();
            System.out.println("📊 Report Updated: " + context.getName());
        }
    }

    /**
     * Extract module name from class package
     */
    private String getModuleName(String className) {
        if (className.contains(".auth.")) {
            return "Authentication Service";
        } else if (className.contains(".files.")) {
            return "File Management Service";
        } else if (className.contains(".billing.")) {
            return "Billing Service";
        } else if (className.contains(".petition.")) {
            return "Petition Service";
        }
        return "Common Module";
    }

    /**
     * Calculate test execution time
     */
    private long getExecutionTime(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }

    /**
     * Get current test instance
     */
    public static ExtentTest getCurrentTest() {
        return test.get();
    }
}
