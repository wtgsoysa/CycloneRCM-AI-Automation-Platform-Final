package com.cyclonercm.pages;

import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for Daily Billing page
 * Supports SMOKE_FH_002 and related Daily Billing test cases
 */
public class DailyBillingPage {

    private WebDriver driver;

    public DailyBillingPage(WebDriver driver) {
        this.driver = driver;
    }

    // ========== NAVIGATION LOCATORS ==========

    private final By billingMenu = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[1]/button");
    private final By billingMenuButton = By.xpath("//button[contains(text(),'Billing') or .//span[contains(text(),'Billing')]]");
    private final By dailyBillingOption = By.xpath("//a[contains(text(),'Daily Billing') or .//span[contains(text(),'Daily Billing')]]");
    private final By splashScreen = By.xpath("//div[contains(@class,'splash') or contains(@class,'loading') or contains(@class,'p-progress')]");
    private final By loadingOverlay = By.xpath("//div[contains(@class,'p-component-overlay') or contains(@class,'loading-overlay')]");

    // Daily Billing label — two locators attempted in order
    private final By dailyBillingLabelLocator1 = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[1]/span/b");
    private final By dailyBillingLabelLocator2 = By.xpath("//billing-list//b[contains(text(),'Daily Billing')] | //b[contains(text(),'Daily Billing')]");

    // ========== TOOLBAR / FILTER LOCATORS ==========

    // DOS (Date of Service) filter
    private final By dosDropDownButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[2]/p-dropdown/div/div[2]");
    private final By dosSearchBar = By.xpath("/html/body/div[2]/div[1]/div/input");
    private final By dosSelector = By.xpath("/html/body/div[2]/ul/p-dropdownitem/li");

    // Case filter
    private final By caseFilter = By.xpath("/html/body/div[2]/div[1]/div/input");
    private final By caseFilterClearButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[3]/p-dropdown/div/div[3]/span");
    private final By caseDropDownButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[3]/p-dropdown/div/div[2]");
    private final By caseSearchBar = By.xpath("/html/body/div[3]/div[1]/div/input");
    private final By caseSelector = By.xpath("/html/body/div[3]/div[2]/ul/p-dropdownitem/li");

    // Search / Clear buttons
    private final By searchButton = By.xpath("//button[@ptooltip='Search' or contains(@class,'search-btn')] | /html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/button[1]");
    private final By clearFiltersButton = By.xpath("//button[@ptooltip='Clear' or contains(@class,'clear-btn')] | /html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/button[2]");

    // ========== TABLE / RESULTS LOCATORS ==========

    private final By billingTable = By.xpath("//billing-list//p-table | //billing-list//table");
    private final By billingTableRows = By.xpath("//billing-list//p-table//tbody//tr[contains(@class,'p-selectable-row')]");
    private final By noRecordsMessage = By.xpath("//billing-list//*[contains(text(),'No records') or contains(text(),'No data')]");

    // ========== NAVIGATION METHODS ==========

    public boolean isBillingMenuVisible() {
        try {
            List<WebElement> elements = driver.findElements(billingMenuButton);
            if (elements.isEmpty()) {
                elements = driver.findElements(billingMenu);
            }
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickBillingMenu() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.println("Attempt " + attempt + " to click Billing menu...");
            try {
                List<WebElement> elements = driver.findElements(billingMenuButton);
                if (elements.isEmpty()) {
                    elements = driver.findElements(billingMenu);
                }
                if (!elements.isEmpty()) {
                    WebElement menuEl = elements.get(0);
                    System.out.println("Billing menu element found, attempting click...");
                    try {
                        menuEl.click();
                    } catch (ElementClickInterceptedException e) {
                        js.executeScript("arguments[0].click();", menuEl);
                    }
                    WaitUtils.sleep(1000);
                    // Verify dropdown appeared
                    List<WebElement> dropdown = driver.findElements(dailyBillingOption);
                    if (!dropdown.isEmpty() && dropdown.get(0).isDisplayed()) {
                        System.out.println("✓ Billing menu clicked successfully - dropdown is visible");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
            }
            WaitUtils.sleep(1000);
        }
        throw new RuntimeException("Failed to click Billing menu after " + maxAttempts + " attempts");
    }

    public void clickDailyBillingOption() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int maxAttempts = 3;
        String urlBefore = driver.getCurrentUrl();
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.println("Attempt " + attempt + " to click Daily Billing option...");
            try {
                List<WebElement> options = driver.findElements(dailyBillingOption);
                if (!options.isEmpty()) {
                    WebElement option = options.get(0);
                    System.out.println("Daily Billing option found, attempting click...");
                    try {
                        option.click();
                        System.out.println("Used normal click");
                    } catch (ElementClickInterceptedException e) {
                        js.executeScript("arguments[0].click();", option);
                        System.out.println("Used JavaScript click");
                    }
                    WaitUtils.sleep(1500);
                    String urlAfter = driver.getCurrentUrl();
                    System.out.println("URL before click: " + urlBefore);
                    System.out.println("URL after click: " + urlAfter);
                    if (!urlAfter.equals(urlBefore)) {
                        System.out.println("✓ Daily Billing option clicked - navigation detected");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
            }
            WaitUtils.sleep(1000);
        }
        throw new RuntimeException("Failed to navigate to Daily Billing after " + maxAttempts + " attempts");
    }

    public void waitForSplashScreenToDisappear() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOfElementLocated(splashScreen));
            System.out.println("✓ Splash screen disappeared");
        } catch (Exception e) {
            System.out.println("✓ Splash screen disappeared (or was not present)");
        }
    }

    public boolean isDailyBillingPageLoaded() {
        System.out.println("⏳ Waiting for Daily Billing label to appear...");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        // Debug info
        try {
            List<WebElement> billingApps = driver.findElements(By.tagName("billing-app"));
            System.out.println("billing-app elements found: " + billingApps.size());
            List<WebElement> billingLists = driver.findElements(By.tagName("billing-list"));
            System.out.println("billing-list elements found: " + billingLists.size());
            List<WebElement> toolbars = driver.findElements(By.tagName("p-toolbar"));
            System.out.println("p-toolbar elements found: " + toolbars.size());
        } catch (Exception ignored) { }

        // Try locator 1
        try {
            System.out.println("Trying locator 1...");
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(dailyBillingLabelLocator1));
            String text = driver.findElement(dailyBillingLabelLocator1).getText().trim();
            System.out.println("✓ Found label using locator 1: '" + text + "'");
            System.out.println("✓ Daily Billing page loaded");
            return true;
        } catch (Exception e) {
            System.out.println("  Locator 1 failed: " + e.getMessage());
        }

        // Try locator 2
        try {
            System.out.println("Trying locator 2...");
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(dailyBillingLabelLocator2));
            String text = driver.findElement(dailyBillingLabelLocator2).getText().trim();
            System.out.println("✓ Found label using locator 2: '" + text + "'");
            System.out.println("✓ Daily Billing page loaded");
            return true;
        } catch (Exception e) {
            System.out.println("  Locator 2 failed: " + e.getMessage());
        }

        return false;
    }

    // ========== FILTER METHODS ==========

    public void setDosFilter(String dosValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            System.out.println("Setting DOS filter to: " + dosValue);
            WaitUtils.sleep(2000);

            // Wait for overlays to disappear
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-component-overlay')]")));
            } catch (Exception e) { }

            // Scroll and click dosDropDownButton
            WebElement dosDropdown = driver.findElement(dosDropDownButton);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", dosDropdown);
            WaitUtils.sleep(500);

            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(dosDropDownButton));

            try {
                new Actions(driver).moveToElement(dosDropdown).click().perform();
                System.out.println("✓ DOS dropdown clicked (Actions click)");
            } catch (Exception e) {
                System.out.println("⚠ Actions click failed: " + e.getMessage() + ", falling back to JS click");
                js.executeScript("arguments[0].click();", dosDropdown);
                System.out.println("✓ DOS dropdown clicked (JavaScript click fallback)");
            }

            WaitUtils.sleep(2000);

            // Wait for dosSearchBar visibility
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(dosSearchBar));

            // Type DOS value
            WebElement searchBar = driver.findElement(dosSearchBar);
            searchBar.clear();
            searchBar.sendKeys(dosValue);
            WaitUtils.sleep(3000);

            // Wait for and click result
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(dosSelector));
            WebElement option = driver.findElement(dosSelector);
            try {
                option.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", option);
            }

            WaitUtils.sleep(2000);
            System.out.println("✓ DOS filter applied successfully");

        } catch (Exception e) {
            System.err.println("❌ Failed to set DOS filter: " + e.getMessage());
            throw new RuntimeException("Failed to set DOS filter", e);
        }
    }

    public void setCaseFilter(String caseNumber) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            System.out.println("Setting Case filter to: " + caseNumber);
            WaitUtils.sleep(2000);

            // Wait for overlays to disappear
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-component-overlay')]")));
            } catch (Exception e) { }

            // Scroll and click caseDropDownButton
            WebElement caseDropdown = driver.findElement(caseDropDownButton);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", caseDropdown);
            WaitUtils.sleep(500);

            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(caseDropDownButton));

            // Use Actions click to properly trigger Angular/PrimeNG event binding.
            // JS click does not fire native browser events (mousedown/mouseup/click)
            // which PrimeNG requires to open the dropdown panel.
            try {
                new Actions(driver).moveToElement(caseDropdown).click().perform();
                System.out.println("✓ Case dropdown clicked (Actions click)");
            } catch (Exception e) {
                System.out.println("⚠ Actions click failed: " + e.getMessage() + ", falling back to JS click");
                js.executeScript("arguments[0].click();", caseDropdown);
                System.out.println("✓ Case dropdown clicked (JavaScript click fallback)");
            }

            WaitUtils.sleep(3000);

            // Wait for caseSearchBar to become visible after panel opens
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(caseSearchBar));

            // Type case number into search bar
            WebElement searchBar = driver.findElement(caseSearchBar);
            searchBar.clear();
            searchBar.sendKeys(caseNumber);
            WaitUtils.sleep(3000);

            // Wait for and click the matching result
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(caseSelector));
            WebElement option = driver.findElement(caseSelector);
            try {
                option.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", option);
            }

            WaitUtils.sleep(2000);
            System.out.println("✓ Case filter applied successfully");

        } catch (Exception e) {
            System.err.println("❌ Failed to set Case filter: " + e.getMessage());
            throw new RuntimeException("Failed to set Case filter", e);
        }
    }

    public void clickSearchButton() {
        try {
            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(searchButton));
            btn.click();
            System.out.println("✓ Search button clicked");
        } catch (Exception e) {
            System.err.println("❌ Failed to click Search button: " + e.getMessage());
            throw new RuntimeException("Failed to click Search button", e);
        }
    }

    public void clickClearFiltersButton() {
        try {
            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(clearFiltersButton));
            btn.click();
            System.out.println("✓ Clear filters button clicked");
        } catch (Exception e) {
            System.err.println("❌ Failed to click Clear filters button: " + e.getMessage());
            throw new RuntimeException("Failed to click Clear filters button", e);
        }
    }

    // ========== RESULTS METHODS ==========

    public boolean isTableDisplayed() {
        try {
            return driver.findElement(billingTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getTableRowCount() {
        try {
            List<WebElement> rows = driver.findElements(billingTableRows);
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isNoRecordsMessageDisplayed() {
        try {
            return driver.findElement(noRecordsMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
