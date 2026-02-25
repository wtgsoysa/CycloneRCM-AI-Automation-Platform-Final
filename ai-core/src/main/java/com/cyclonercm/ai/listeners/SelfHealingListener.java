package com.cyclonercm.ai.listeners;

import com.cyclonercm.ai.healing.HealingReport;
import org.testng.*;

/**
 * TestNG Listener that triggers healing analysis on test failure.
 * Register in testng.xml or BaseTest @Listeners annotation.
 */
public class SelfHealingListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable cause = result.getThrowable();

        System.out.println("\n🔴 [SelfHealingListener] Test Failed: " + testName);

        if (cause != null) {
            String msg = cause.getMessage();

            if (msg != null) {
                if (msg.contains("NoSuchElementException") ||
                        msg.contains("Unable to locate element")) {
                    System.out.println("   → Type: LOCATOR FAILURE — Self-Healing triggered");
                    System.out.println("   → Check: logs/healing-report.log for healed locators");
                    HealingReport.log(null, null,
                            "TestNG Failure: " + testName + " — " + msg.substring(0, Math.min(100, msg.length()))
                    );

                } else if (msg.contains("TimeoutException")) {
                    System.out.println("   → Type: TIMEOUT — Element took too long to appear");

                } else if (msg.contains("StaleElementReferenceException")) {
                    System.out.println("   → Type: STALE ELEMENT — Page reloaded mid-test");
                }
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ [SelfHealingListener] PASSED: " +
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n▶️  [SelfHealingListener] Starting: " +
                result.getMethod().getMethodName());
    }
}