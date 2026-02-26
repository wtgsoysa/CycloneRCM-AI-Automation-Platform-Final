package com.cyclonercm.ai.healing;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generates fallback locator strategies when primary fails.
 * Tailored for CycloneRCM's Angular/PrimeNG components.
 */
public class FallbackLocatorChain {

    /**
     * Generates smart fallback locators from broken absolute XPath.
     * Specifically handles CycloneRCM's aeliusmd-* Angular components.
     */
    public static List<By> generateFor(WebDriver driver, By failedLocator) {
        List<By> strategies = new ArrayList<>();
        String locatorString = failedLocator.toString();

        // ─── CycloneRCM Login Page Fallbacks ───────────────────────────

        // User ID / Email input fields
        if (locatorString.contains("userIdField") ||
                locatorString.contains("div[2]/div[2]/div/input")) {
            strategies.add(By.cssSelector("aeliusmd-login input[type='text']"));
            strategies.add(By.cssSelector("input[placeholder*='User']"));
            strategies.add(By.xpath("//input[@type='text' and not(@disabled)]"));
            strategies.add(By.name("userId"));
        }

        // Password field (PrimeNG p-password component)
        if (locatorString.contains("p-password") ||
                locatorString.contains("passwordField")) {
            strategies.add(By.cssSelector("p-password input"));
            strategies.add(By.cssSelector("input[type='password']"));
            strategies.add(By.xpath("//p-password//input"));
        }

        // Sign In / Submit buttons
        if (locatorString.contains("button") && locatorString.contains("div[4]")) {
            strategies.add(By.cssSelector("button[type='submit']"));
            strategies.add(By.xpath("//button[contains(text(),'Sign In')]"));
            strategies.add(By.xpath("//button[contains(text(),'Login')]"));
            strategies.add(By.cssSelector("aeliusmd-login button"));
        }

        // Forgot Password link
        if (locatorString.contains("forgotPassword") ||
                locatorString.contains("div[6]/a")) {
            strategies.add(By.cssSelector("a[href*='forgot']"));
            strategies.add(By.xpath("//a[contains(text(),'Forgot')]"));
            strategies.add(By.cssSelector("aeliusmd-login a"));
        }

        // Toast messages (PrimeNG p-toast)
        if (locatorString.contains("p-toast") ||
                locatorString.contains("p-toastitem")) {
            strategies.add(By.cssSelector("p-toast .p-toast-message"));
            strategies.add(By.cssSelector(".p-toast-detail"));
            strategies.add(By.xpath("//p-toastitem//div[contains(@class,'message')]"));
        }

        // Dashboard logo / system label
        if (locatorString.contains("img[1]") ||
                locatorString.contains("dashboardsystemLabel")) {
            strategies.add(By.cssSelector("nav img"));
            strategies.add(By.cssSelector(".nav-logo img"));
            strategies.add(By.xpath("//img[contains(@src,'logo')]"));
        }

        // Petition module buttons
        if (locatorString.contains("petition") ||
                locatorString.contains("submit-btn")) {
            strategies.add(By.cssSelector("[data-testid*='petition']"));
            strategies.add(By.xpath("//button[contains(@class,'petition')]"));
        }

        // Logout
        if (locatorString.contains("logoutButton") ||
                locatorString.contains("ul/li/div")) {
            strategies.add(By.xpath("//li[contains(., 'Logout')]"));
            strategies.add(By.cssSelector("ul.nav-right li:last-child"));
            strategies.add(By.xpath("//div[contains(text(),'Logout')]"));
        }

        // ─── Generic Angular Fallbacks (last resort) ────────────────────
        strategies.add(By.cssSelector("[ng-reflect-name]"));
        strategies.add(By.cssSelector("[formcontrolname]"));

        return strategies;
    }
}