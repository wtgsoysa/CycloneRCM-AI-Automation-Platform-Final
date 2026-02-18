package com.cyclonercm.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opts = new ChromeOptions();

            // Window settings
            opts.addArguments("--start-maximized");

            // Stability settings to prevent crashes
            opts.addArguments("--disable-blink-features=AutomationControlled");
            opts.addArguments("--disable-dev-shm-usage");
            opts.addArguments("--no-sandbox");
            opts.addArguments("--disable-gpu");
            opts.addArguments("--disable-extensions");
            opts.addArguments("--disable-infobars");
            opts.addArguments("--disable-notifications");
            opts.addArguments("--remote-allow-origins=*");

            // Performance settings
            opts.addArguments("--disable-software-rasterizer");
            opts.addArguments("--disable-background-networking");
            opts.addArguments("--disable-default-apps");
            opts.addArguments("--disable-sync");

            // Prevent detection
            opts.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            opts.setExperimentalOption("useAutomationExtension", false);

            driver.set(new ChromeDriver(opts));
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver wd = driver.get();
        if (wd != null) {
            try { wd.quit(); } catch (Exception ignored) {}
            driver.remove();
        }
    }
}
