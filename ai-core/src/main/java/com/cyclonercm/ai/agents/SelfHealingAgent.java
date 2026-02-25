package com.cyclonercm.ai.agents;

import com.cyclonercm.ai.healing.FallbackLocatorChain;
import com.cyclonercm.ai.healing.HealingReport;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SelfHealingAgent {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 15;

    public SelfHealingAgent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Main entry point — finds element with automatic healing on failure.
     * Usage: Replace driver.findElement(by) with agent.findElement(by, fallbacks...)
     */
    public WebElement findElement(By primaryLocator, By... fallbacks) {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(primaryLocator)
            );
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println("⚠️ [SelfHealingAgent] Primary locator failed: " + primaryLocator);
            return heal(primaryLocator, fallbacks);
        }
    }

    /**
     * Click with healing — most common action in CycloneRCM tests
     */
    public void click(By primaryLocator, By... fallbacks) {
        findElement(primaryLocator, fallbacks).click();
    }

    /**
     * Type into field with healing
     */
    public void type(By primaryLocator, String text, By... fallbacks) {
        WebElement element = findElement(primaryLocator, fallbacks);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text with healing
     */
    public String getText(By primaryLocator, By... fallbacks) {
        return findElement(primaryLocator, fallbacks).getText().trim();
    }

    /**
     * Healing engine — tries fallback locators in order
     */
    private WebElement heal(By failedLocator, By[] fallbacks) {
        // First try provided fallbacks
        for (By fallback : fallbacks) {
            try {
                WebElement healed = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(fallback)
                );
                HealingReport.log(failedLocator, fallback, "Fallback locator succeeded");
                System.out.println("✅ [SelfHealingAgent] Healed with: " + fallback);
                return healed;
            } catch (Exception ex) {
                System.out.println("❌ Fallback failed: " + fallback);
            }
        }

        // Then try auto-generated strategies from FallbackLocatorChain
        List<By> autoStrategies = FallbackLocatorChain.generateFor(driver, failedLocator);
        for (By strategy : autoStrategies) {
            try {
                WebElement healed = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(strategy)
                );
                HealingReport.log(failedLocator, strategy, "Auto-generated strategy succeeded");
                System.out.println("✅ [SelfHealingAgent] Auto-healed with: " + strategy);
                return healed;
            } catch (Exception ex) {
                // continue
            }
        }

        // All strategies exhausted — capture screenshot and throw
        captureFailureScreenshot(failedLocator);
        throw new RuntimeException(
                "🔴 [SelfHealingAgent] HEALING FAILED — All strategies exhausted.\n" +
                        "Failed locator: " + failedLocator + "\n" +
                        "Check: logs/healing-report.log"
        );
    }

    private void captureFailureScreenshot(By locator) {
        try {
            if (driver instanceof TakesScreenshot) {
                java.io.File src = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);
                String destPath = "logs/healing-failure-" +
                        System.currentTimeMillis() + ".png";
                java.nio.file.Files.copy(
                        src.toPath(),
                        java.nio.file.Paths.get(destPath)
                );
                System.out.println("📸 Screenshot saved: " + destPath);
            }
        } catch (Exception e) {
            System.out.println("Could not capture screenshot: " + e.getMessage());
        }
    }
}
