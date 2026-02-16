package com.cyclonercm.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Centralized ExtentReports Manager for Multi-Module Project
 * Creates ONE consolidated report for ALL test modules
 */
public class CentralizedExtentManager {

    private static ExtentReports extent;
    private static final String REPORT_DIR = System.getProperty("user.dir") + "/consolidated-reports/";
    private static final String TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    private static final String REPORT_NAME = "CycloneRCM_Smoke_Test_Report_" + TIMESTAMP + ".html";

    /**
     * Get singleton instance of ExtentReports
     * This ensures ALL modules write to the SAME report
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    /**
     * Create ExtentReports instance with consolidated configuration
     */
    private static ExtentReports createInstance() {
        // Create report directory if it doesn't exist
        File reportDir = new File(REPORT_DIR);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        String reportPath = REPORT_DIR + REPORT_NAME;

        // Configure Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Report Configuration
        sparkReporter.config().setDocumentTitle("CycloneRCM Automation Test Report");
        sparkReporter.config().setReportName("Smoke Test Execution Report - All Modules");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        // Custom CSS for better presentation
        sparkReporter.config().setCss(
            ".brand-logo { background-color: #2196F3; }" +
            ".suite-toggle { color: #2196F3; }" +
            ".test-status { font-weight: bold; }"
        );

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System Information
        extent.setSystemInfo("Project", "CycloneRCM AI Automation Platform");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Test Type", "Smoke Tests");
        extent.setSystemInfo("Execution Mode", "Sequential");
        extent.setSystemInfo("Build Version", "1.0.0");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));

        System.out.println("===============================================");
        System.out.println("📊 Consolidated Report Path: " + reportPath);
        System.out.println("===============================================");

        return extent;
    }

    /**
     * Flush the report to save all test data
     */
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Get the report file path
     */
    public static String getReportPath() {
        return REPORT_DIR + REPORT_NAME;
    }
}
