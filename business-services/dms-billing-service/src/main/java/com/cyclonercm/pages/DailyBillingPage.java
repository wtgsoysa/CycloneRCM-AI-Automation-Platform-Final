package com.cyclonercm.pages;

import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cyclonercm.ai.agents.SelfHealingAgent; // ← ADD THIS IMPORT

public class DailyBillingPage {


        //region ======================== DRIVER & CONSTRUCTOR ========================
        private WebDriver driver;
        private WebDriverWait wait;
        private SelfHealingAgent healer;

    public DailyBillingPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.healer = new SelfHealingAgent(driver);
        }

        //-------- Helper Methods ---------
        /**
         * Wait for Angular and page to finish loading
         */
        private void waitForPageToLoad() {
            try {
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

                // Wait for document ready state
                for (int i = 0; i < 30; i++) {
                    String readyState = js.executeScript("return document.readyState").toString();
                    if ("complete".equals(readyState)) {
                        break;
                    }
                    WaitUtils.sleep(500);
                }

                // Wait for Angular (if present)
                for (int i = 0; i < 30; i++) {
                    try {
                        Boolean angularReady = (Boolean) js.executeScript(
                            "return window.getAllAngularTestabilities ? " +
                            "window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1 : true"
                        );
                        if (Boolean.TRUE.equals(angularReady)) {
                            break;
                        }
                    } catch (Exception e) {
                        // Angular might not be present, that's okay
                        break;
                    }
                    WaitUtils.sleep(500);
                }

                WaitUtils.sleep(1000); // Additional buffer
            } catch (Exception e) {
                System.out.println("⚠ Could not verify page load state: " + e.getMessage());
            }
        }

        //--------- Navigation Locators & Methods ---------
        private final By billingMenu = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/button[4]");
        private final By billingDropdown = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div");
        private final By dailyBillingOption = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div/ul/li[1]/a");
        private final By dailyBillingLogo = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[1]/span/b");

        //-------- Navigation Methods ---------
        public Boolean isBillingMenuDisplayed() {
            return driver.findElement(billingMenu).isDisplayed();
        }

        public void clickBillingMenu() {
            try {
                WebElement menuElement = driver.findElement(billingMenu);
                menuElement.click();
            } catch (Exception e) {
                // Fallback to JavaScript click if normal click fails
                System.out.println("⚠ Normal click failed, trying JavaScript click");
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                WebElement menuElement = driver.findElement(billingMenu);
                js.executeScript("arguments[0].click();", menuElement);
            }
        }

        public void clickDailyBillingOption() {
            try {
                WaitUtils.sleep(1000); // Wait for dropdown to fully appear
                WebElement optionElement = driver.findElement(dailyBillingOption);
                optionElement.click();
                waitForPageToLoad(); // Wait for page to fully load after navigation
            } catch (Exception e) {
                // Fallback to JavaScript click if normal click fails
                System.out.println("⚠ Normal click failed, trying JavaScript click for Daily Billing option");
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                WebElement optionElement = driver.findElement(dailyBillingOption);
                js.executeScript("arguments[0].click();", optionElement);
                waitForPageToLoad(); // Wait for page to fully load after navigation
            }
        }

        public String getDailyBillingLabel() {
            return driver.findElement(dailyBillingLogo).getText().trim();
        }

        //========== FILTER LOCATORS ==========
        private final By dosFilter = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[1]/p-dropdown/div/input");
        private final By dosClearButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[1]/p-dropdown/div/i");
        private final By dosDropDownButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[2]/p-dropdown/div/div[2]");
        // Flexible: targets the currently-visible p-dropdown-panel (works regardless of body div index)
        private final By activeDropdownSearchBar = By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'display: none'))]//div[contains(@class,'p-dropdown-filter-container')]//input");
        private final By activeDropdownFirstItem = By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'display: none'))]//ul[contains(@class,'p-dropdown-items')]/p-dropdownitem/li");
        // Keep legacy aliases for DOS (use flexible panel locators in methods instead)
        private final By dosSearchBar = By.xpath("/html/body/div[2]/div[1]/div/input");
        private final By dosSelector = By.xpath("/html/body/div[2]/div[2]/ul/p-dropdownitem/li");

        private final By caseFilter = By.xpath("/html/body/div[2]/div[1]/div/input");
        private final By caseFilterClearButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[2]/p-dropdown/div/i");
        private final By caseDropDownButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[3]/p-dropdown/div/div[2]");
        private final By caseSearchBar = By.xpath("/html/body/div[3]/div[1]/div/input");
        private final By caseSelector  = By.xpath("/html/body/div[3]/div[2]/ul/p-dropdownitem/li");

        // Applicant filter — use flexible visible-panel locators (body div index is unreliable)
        private final By applicantDropdown = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[4]/p-dropdown/div/div[2]");
        // applicantFilter and applicantDropdownlist resolved dynamically via activeDropdownSearchBar / activeDropdownFirstItem
        private final By applicantFilter = By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'display: none'))]//div[contains(@class,'p-dropdown-filter-container')]//input");
        private final By applicantDropdownlist = By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'display: none'))]//ul[contains(@class,'p-dropdown-items')]/p-dropdownitem/li");
        // Claim Admin is the 5th p-dropdown in the toolbar (div[5])
        private final By claimAdminDropDownButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[5]/p-dropdown/div/div[2]");
        private final By claimAdminFilter = By.xpath("//p-dropdown[@placeholder='Claim Admin']//input[@type='text']");
        private final By claimAdminClearButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[5]/p-dropdown/div/i");
        // Invoice number search — div[6] in the toolbar is the "Search by inv #" text input
        private final By invoiceNumberSearchInput = By.xpath(
            "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[6]/div/div/span/input");
        private final By invoiceNumberFilter = By.xpath("//input[@placeholder='Search by inv #']");

        //Checkbox
        private final By checkbox = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[1]/div/p-checkbox");
        private final By informationMessageModal = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/p-dialog/div/div/div[2]/div");

        //========== FILTER METHODS ==========
        public void setDosFilter(String dos) {
            try {
                System.out.println("Setting DOS filter to: " + dos);

                // Wait for page to fully stabilize after navigation
                WaitUtils.sleep(2000);

                // Wait for any overlays or loading indicators to disappear
                try {
                    org.openqa.selenium.support.ui.WebDriverWait overlayWait =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    overlayWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-component-overlay')]")
                    ));
                } catch (Exception e) {
                    // No overlay found, continue
                }

                // Find the DOS dropdown button
                WebElement dosDropdown = driver.findElement(dosDropDownButton);

                // Scroll element into center of viewport
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", dosDropdown);
                WaitUtils.sleep(500);

                // Wait for element to be clickable
                org.openqa.selenium.support.ui.WebDriverWait clickWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                clickWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(dosDropDownButton));

                // Try normal click first; fall back to JavaScript click if intercepted
                try {
                    dosDropdown.click();
                    System.out.println("✓ DOS dropdown clicked (normal click)");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("⚠ Normal click intercepted, trying JavaScript click...");
                    js.executeScript("arguments[0].click();", dosDropdown);
                    System.out.println("✓ DOS dropdown clicked (JavaScript click)");
                }

                // Wait for the currently-visible dropdown panel's search bar to appear
                // Uses flexible class-based XPath — immune to absolute body div[N] index changes
                org.openqa.selenium.support.ui.WebDriverWait searchWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
                searchWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(activeDropdownSearchBar));

                // Type in the search bar
                WebElement searchBar = driver.findElement(activeDropdownSearchBar);
                searchBar.clear();
                searchBar.sendKeys(dos);
                System.out.println("✓ Entered DOS value: " + dos);

                // Wait for search results to load
                WaitUtils.sleep(3000);

                // Wait for the first option to be clickable
                org.openqa.selenium.support.ui.WebDriverWait selectorWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                selectorWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(activeDropdownFirstItem));

                // Click the first matching option
                WebElement option = driver.findElement(activeDropdownFirstItem);
                try {
                    option.click();
                    System.out.println("✓ DOS option selected");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    js.executeScript("arguments[0].click();", option);
                    System.out.println("✓ DOS option selected (JavaScript click)");
                }

                // Wait for filter to be applied
                WaitUtils.sleep(2000);
                System.out.println("✓ DOS filter applied successfully");

            } catch (Exception e) {
                System.err.println("❌ Failed to set DOS filter: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to set DOS filter", e);
            }
        }

        public void clearDosFilter() {
            driver.findElement(dosClearButton).click();
        }

        public void setCaseFilter(String caseNumber) {
            try {
                System.out.println("Setting Case filter to: " + caseNumber);

                // Wait for page to fully stabilize after navigation
                WaitUtils.sleep(2000);

                // Wait for any overlays or loading indicators to disappear
                try {
                    org.openqa.selenium.support.ui.WebDriverWait overlayWait =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    overlayWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-component-overlay')]")
                    ));
                } catch (Exception e) {
                    // No overlay found, continue
                }

                // Target the parent div of the dropdown trigger (not the inner span)
                WebElement caseDropdown = driver.findElement(caseDropDownButton);

                // Scroll element into center of viewport to avoid sticky header interception
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", caseDropdown);
                WaitUtils.sleep(500);

                // Wait for element to be clickable
                org.openqa.selenium.support.ui.WebDriverWait clickWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                clickWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(caseDropDownButton));

                // Try normal click first; fall back to JavaScript click if intercepted
                try {
                    caseDropdown.click();
                    System.out.println("✓ Case dropdown clicked (normal click)");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("⚠ Normal click intercepted, trying JavaScript click...");
                    js.executeScript("arguments[0].click();", caseDropdown);
                    System.out.println("✓ Case dropdown clicked (JavaScript click)");
                }

                // Wait for the visible dropdown panel's search bar to appear (flexible - no absolute body div index)
                org.openqa.selenium.support.ui.WebDriverWait searchWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
                searchWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(activeDropdownSearchBar));

                // Type in the search bar
                WebElement searchBar = driver.findElement(activeDropdownSearchBar);
                searchBar.clear();
                searchBar.sendKeys(caseNumber);
                System.out.println("✓ Entered Case value: " + caseNumber);

                // Wait for search results to load
                WaitUtils.sleep(2000);

                // Wait for the first option to be clickable
                org.openqa.selenium.support.ui.WebDriverWait selectorWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                selectorWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(activeDropdownFirstItem));

                // Click the first matching option
                WebElement option = driver.findElement(activeDropdownFirstItem);
                try {
                    option.click();
                    System.out.println("✓ Case option selected");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    js.executeScript("arguments[0].click();", option);
                    System.out.println("✓ Case option selected (JavaScript click)");
                }

                // Wait for filter to be applied
                WaitUtils.sleep(2000);
                System.out.println("✓ Case filter applied successfully");

            } catch (Exception e) {
                System.err.println("❌ Failed to set Case filter: " + e.getMessage());
                throw new RuntimeException("Failed to set Case filter", e);
            }
        }



        public void setApplicantFilter(String applicantName) {
            try {
                System.out.println("Setting Applicant filter to: " + applicantName);

                // Wait for page to fully stabilize
                WaitUtils.sleep(2000);

                // Wait for any overlays or loading indicators to disappear
                try {
                    org.openqa.selenium.support.ui.WebDriverWait overlayWait =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    overlayWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-component-overlay')]")
                    ));
                } catch (Exception e) {
                    // No overlay found, continue
                }

                // Find the Applicant dropdown trigger div and scroll into center of viewport
                WebElement applicantDropdownEl = driver.findElement(applicantDropdown);
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", applicantDropdownEl);
                WaitUtils.sleep(500);

                // Wait for element to be clickable
                org.openqa.selenium.support.ui.WebDriverWait clickWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                clickWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(applicantDropdown));

                // Try normal click first; fall back to JavaScript click if intercepted
                try {
                    applicantDropdownEl.click();
                    System.out.println("✓ Applicant dropdown clicked (normal click)");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("⚠ Normal click intercepted, trying JavaScript click...");
                    js.executeScript("arguments[0].click();", applicantDropdownEl);
                    System.out.println("✓ Applicant dropdown clicked (JavaScript click)");
                }

                // Wait for the currently-visible dropdown panel's search bar to appear
                // Uses flexible class-based XPath — immune to absolute body div[N] index changes
                org.openqa.selenium.support.ui.WebDriverWait searchWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
                searchWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(activeDropdownSearchBar));

                // Type in the search bar
                WebElement searchBar = driver.findElement(activeDropdownSearchBar);
                searchBar.clear();
                searchBar.sendKeys(applicantName);
                System.out.println("✓ Entered Applicant value: " + applicantName);

                // Wait for search results to load
                WaitUtils.sleep(2000);

                // Wait for the first option to be clickable
                org.openqa.selenium.support.ui.WebDriverWait selectorWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                selectorWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(activeDropdownFirstItem));

                // Click the first matching option
                WebElement option = driver.findElement(activeDropdownFirstItem);
                try {
                    option.click();
                    System.out.println("✓ Applicant option selected");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    js.executeScript("arguments[0].click();", option);
                    System.out.println("✓ Applicant option selected (JavaScript click)");
                }

                // Wait for filter to be applied
                WaitUtils.sleep(2000);
                System.out.println("✓ Applicant filter applied successfully");

            } catch (Exception e) {
                System.err.println("❌ Failed to set Applicant filter: " + e.getMessage());
                throw new RuntimeException("Failed to set Applicant filter", e);
            }
        }

        public void setClaimAdminFilter(String claimAdmin) {
            try {
                System.out.println("Setting Claim Admin filter to: " + claimAdmin);

                // Wait for page to fully stabilize
                WaitUtils.sleep(2000);

                // Wait for any overlays or loading indicators to disappear
                try {
                    org.openqa.selenium.support.ui.WebDriverWait overlayWait =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    overlayWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'p-component-overlay')]")
                    ));
                } catch (Exception e) {
                    // No overlay found, continue
                }

                // Find the Claim Admin dropdown trigger div and scroll into center of viewport
                WebElement claimAdminDropdownEl = driver.findElement(claimAdminDropDownButton);
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", claimAdminDropdownEl);
                WaitUtils.sleep(500);

                // Wait for element to be clickable
                org.openqa.selenium.support.ui.WebDriverWait clickWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                clickWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(claimAdminDropDownButton));

                // Try normal click first; fall back to JavaScript click if intercepted
                try {
                    claimAdminDropdownEl.click();
                    System.out.println("✓ Claim Admin dropdown clicked (normal click)");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("⚠ Normal click intercepted, trying JavaScript click...");
                    js.executeScript("arguments[0].click();", claimAdminDropdownEl);
                    System.out.println("✓ Claim Admin dropdown clicked (JavaScript click)");
                }

                // Wait for the currently-visible dropdown panel's search bar to appear
                // Uses flexible class-based XPath — immune to absolute body div[N] index changes
                org.openqa.selenium.support.ui.WebDriverWait searchWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
                searchWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(activeDropdownSearchBar));

                // Type in the search bar
                WebElement searchBar = driver.findElement(activeDropdownSearchBar);
                searchBar.clear();
                searchBar.sendKeys(claimAdmin);
                System.out.println("✓ Entered Claim Admin value: " + claimAdmin);

                // Wait for search results to load
                WaitUtils.sleep(2000);

                // Wait for the first option to be clickable
                org.openqa.selenium.support.ui.WebDriverWait selectorWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                selectorWait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(activeDropdownFirstItem));

                // Click the first matching option
                WebElement option = driver.findElement(activeDropdownFirstItem);
                try {
                    option.click();
                    System.out.println("✓ Claim Admin option selected");
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    js.executeScript("arguments[0].click();", option);
                    System.out.println("✓ Claim Admin option selected (JavaScript click)");
                }

                // Wait for filter to be applied
                WaitUtils.sleep(2000);
                System.out.println("✓ Claim Admin filter applied successfully");

            } catch (Exception e) {
                System.err.println("❌ Failed to set Claim Admin filter: " + e.getMessage());
                throw new RuntimeException("Failed to set Claim Admin filter", e);
            }
        }

        public void setInvoiceNumberFilter(String invoiceNumber) {
            try {
                System.out.println("Searching invoice number: " + invoiceNumber);

                // Wait for page to stabilize
                WaitUtils.sleep(1500);

                // Try the exact XPath first, fall back to placeholder-based locator
                By searchLocator;
                java.util.List<WebElement> exactInputs = driver.findElements(invoiceNumberSearchInput);
                if (!exactInputs.isEmpty()) {
                    searchLocator = invoiceNumberSearchInput;
                } else {
                    searchLocator = invoiceNumberFilter;
                }

                // Wait for the search input to be visible
                org.openqa.selenium.support.ui.WebDriverWait w =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                w.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(searchLocator));

                WebElement searchInput = driver.findElement(searchLocator);

                // Scroll into view
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", searchInput);
                WaitUtils.sleep(300);

                // Click to focus
                try {
                    searchInput.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    js.executeScript("arguments[0].click();", searchInput);
                }

                // Clear any existing value and type the invoice number
                searchInput.clear();
                searchInput.sendKeys(invoiceNumber);
                System.out.println("✓ Invoice number entered: " + invoiceNumber);

                // Wait for auto-load: Angular re-renders the table as you type, which causes
                // StaleElementReferenceException if we hold a reference to the old element.
                // Use WebDriverWait with ignoring StaleElementReferenceException so retries are
                // handled automatically — never call isDisplayed() on a cached element reference.
                System.out.println("⏳ Waiting for search results to auto-load...");
                By resultRow = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[2]/span/u");
                By resultRowFlex = By.xpath(
                    "//billing-list//p-table//tbody/tr/td[2]/span/u");

                boolean resultFound = false;

                // Primary wait — exact XPath, up to 10 s, stale refs retried automatically
                try {
                    org.openqa.selenium.support.ui.WebDriverWait resultWait =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    resultWait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
                    resultWait.until(org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(resultRow));
                    System.out.println("✓ Search result row appeared (exact XPath)");
                    resultFound = true;
                } catch (Exception ignored) {}

                // Fallback wait — flexible XPath
                if (!resultFound) {
                    try {
                        org.openqa.selenium.support.ui.WebDriverWait resultWait =
                            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));
                        resultWait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
                        resultWait.until(org.openqa.selenium.support.ui.ExpectedConditions
                            .visibilityOfElementLocated(resultRowFlex));
                        System.out.println("✓ Search result row appeared (flex XPath)");
                        resultFound = true;
                    } catch (Exception ignored) {}
                }

                if (!resultFound) {
                    System.out.println("⚠ Search result row did not appear within wait — proceeding with validation");
                }

            } catch (Exception e) {
                System.err.println("❌ Failed to search invoice number: " + e.getMessage());
                throw new RuntimeException("Failed to search invoice number", e);
            }
        }

        //========== VALIDATION LOCATORS ==========
        private final By dosFilterValue = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[2]/td/b");
        private final By caseFilterValue = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[1]/td/b/span[4]/span");
        private final By applicantFilterValue = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[1]/td/b/span[1]");
        private final By claimAdminFilterValue = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[1]/td/b/span[3]");
        private final By invoiceNumberFilterValue = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[2]/span/u");

        //========== VALIDATION METHODS ==========
        public String getDosFilterValue() {
            try {
                org.openqa.selenium.support.ui.WebDriverWait w =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                w.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(dosFilterValue));
                return driver.findElement(dosFilterValue).getText().trim();
            } catch (Exception e) {
                System.err.println("❌ Could not retrieve DOS filter value: " + e.getMessage());
                return "";
            }
        }

        public String getCaseFilterValue() {
            try {
                // Strategy 1: Read case number from the table result header row.
                // After applying the Case filter, the first group-header row contains the case number
                // in span[4]/span  e.g. "ADJ20701219"
                By caseFromTableResult = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[1]/td/b/span[4]/span");
                try {
                    org.openqa.selenium.support.ui.WebDriverWait w =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    w.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(caseFromTableResult));
                    String val = driver.findElement(caseFromTableResult).getText().trim();
                    if (!val.isEmpty()) {
                        System.out.println("✓ Case filter value from table result span[4]/span: " + val);
                        return val;
                    }
                } catch (Exception ignored) {}

                // Strategy 2: Broader — any span[4]/span in the first group-header td/b
                By caseFromTableFlex = By.xpath(
                    "//billing-list//p-table//tbody/tr[1]/td/b/span[4]/span");
                try {
                    java.util.List<WebElement> spans = driver.findElements(caseFromTableFlex);
                    if (!spans.isEmpty()) {
                        String val = spans.get(0).getText().trim();
                        if (!val.isEmpty()) {
                            System.out.println("✓ Case filter value from table flex span[4]/span: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                // Strategy 3: Read all span[4] text (without inner span) as fallback
                By caseFromTableSpan4 = By.xpath(
                    "//billing-list//p-table//tbody/tr[1]/td/b/span[last()]");
                try {
                    java.util.List<WebElement> spans = driver.findElements(caseFromTableSpan4);
                    for (WebElement span : spans) {
                        String val = span.getText().trim();
                        // Case numbers typically start with ADJ, WC, HOWC etc.
                        if (!val.isEmpty() && (val.startsWith("ADJ") || val.startsWith("WC") || val.startsWith("HOWC") || val.matches("[A-Z]{2,}\\d+.*"))) {
                            System.out.println("✓ Case filter value from table last span: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                // Strategy 4: Read from the Case p-dropdown selected label in the toolbar (div[3])
                By caseDropdownSelectedLabel = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[3]/p-dropdown//span[contains(@class,'p-dropdown-label') and not(contains(@class,'p-placeholder'))]");
                try {
                    java.util.List<WebElement> labels = driver.findElements(caseDropdownSelectedLabel);
                    if (!labels.isEmpty()) {
                        String val = labels.get(0).getText().trim();
                        if (!val.isEmpty() && !val.equals("Select") && !val.equalsIgnoreCase("Case#")) {
                            System.out.println("✓ Case filter value from dropdown label: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                System.err.println("❌ Could not retrieve Case filter value from any strategy");
                return "";
            } catch (Exception e) {
                System.err.println("❌ Could not retrieve Case filter value: " + e.getMessage());
                return "";
            }
        }

        public String getApplicantFilterValue() {
            try {
                // Strategy 1: Read applicant name from table result header row.
                // After applying the Applicant filter, the first group-header row's span[1]
                // contains the applicant name  e.g. "LEWIS CASTRO"
                By applicantFromTable = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[1]/td/b/span[1]");
                try {
                    org.openqa.selenium.support.ui.WebDriverWait w =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    w.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(applicantFromTable));
                    String val = driver.findElement(applicantFromTable).getText().trim();
                    if (!val.isEmpty()) {
                        System.out.println("✓ Applicant filter value from table result span[1]: " + val);
                        return val;
                    }
                } catch (Exception ignored) {}

                // Strategy 2: Flexible — any first span in first header row's bold text
                By applicantFromTableFlex = By.xpath(
                    "//billing-list//p-table//tbody/tr[1]/td/b/span[1]");
                try {
                    java.util.List<WebElement> spans = driver.findElements(applicantFromTableFlex);
                    if (!spans.isEmpty()) {
                        String val = spans.get(0).getText().trim();
                        if (!val.isEmpty()) {
                            System.out.println("✓ Applicant filter value from table flex span[1]: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                // Strategy 3: Read from the Applicant p-dropdown selected label in the toolbar (div[4])
                By applicantDropdownLabel = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[4]/p-dropdown//span[contains(@class,'p-dropdown-label') and not(contains(@class,'p-placeholder'))]");
                try {
                    java.util.List<WebElement> labels = driver.findElements(applicantDropdownLabel);
                    if (!labels.isEmpty()) {
                        String val = labels.get(0).getText().trim();
                        if (!val.isEmpty() && !val.equals("Select") && !val.equalsIgnoreCase("Applicant")) {
                            System.out.println("✓ Applicant filter value from dropdown label: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                System.err.println("❌ Could not retrieve Applicant filter value from any strategy");
                return "";
            } catch (Exception e) {
                System.err.println("❌ Could not retrieve Applicant filter value: " + e.getMessage());
                return "";
            }
        }

        public String getClaimAdminFilterValue() {
            try {
                // Wait for the table to refresh after filter is applied
                WaitUtils.sleep(2000);

                // Strategy 1: Read claim admin name from table result header row (div[3] table).
                // After applying Claim Admin filter, the first group-header row's span[3]
                // contains the claim admin name  e.g. "OHIO CASUALTY"
                By claimAdminFromTable3 = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[1]/td/b/span[3]");
                try {
                    org.openqa.selenium.support.ui.WebDriverWait w =
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                    w.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(claimAdminFromTable3));
                    String val = driver.findElement(claimAdminFromTable3).getText().trim();
                    if (!val.isEmpty()) {
                        System.out.println("✓ Claim Admin filter value from table div[3] span[3]: " + val);
                        return val;
                    }
                } catch (Exception ignored) {}

                // Strategy 2: Fallback to div[2] table span[3]
                By claimAdminFromTable2 = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[1]/td/b/span[3]");
                try {
                    java.util.List<WebElement> spans = driver.findElements(claimAdminFromTable2);
                    if (!spans.isEmpty() && spans.get(0).isDisplayed()) {
                        String val = spans.get(0).getText().trim();
                        if (!val.isEmpty()) {
                            System.out.println("✓ Claim Admin filter value from table div[2] span[3]: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                // Strategy 3: Flexible — any span[3] in first group-header row
                By claimAdminFromTableFlex = By.xpath(
                    "//billing-list//p-table//tbody/tr[1]/td/b/span[3]");
                try {
                    java.util.List<WebElement> spans = driver.findElements(claimAdminFromTableFlex);
                    if (!spans.isEmpty()) {
                        String val = spans.get(0).getText().trim();
                        if (!val.isEmpty()) {
                            System.out.println("✓ Claim Admin filter value from table flex span[3]: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                // Strategy 4: Read from the Claim Admin p-dropdown selected label in the toolbar (div[5])
                By claimAdminDropdownLabel = By.xpath(
                    "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[5]/p-dropdown//span[contains(@class,'p-dropdown-label') and not(contains(@class,'p-placeholder'))]");
                try {
                    java.util.List<WebElement> labels = driver.findElements(claimAdminDropdownLabel);
                    if (!labels.isEmpty()) {
                        String val = labels.get(0).getText().trim();
                        if (!val.isEmpty() && !val.equalsIgnoreCase("Claim Admin")) {
                            System.out.println("✓ Claim Admin filter value from dropdown label: " + val);
                            return val;
                        }
                    }
                } catch (Exception ignored) {}

                System.err.println("❌ Could not retrieve Claim Admin filter value from any strategy");
                return "";
            } catch (Exception e) {
                System.err.println("❌ Could not retrieve Claim Admin filter value: " + e.getMessage());
                return "";
            }
        }

        public String getInvoiceNumberFilterValue() {
            try {
                // Primary: exact XPath — div[3] table, tr[3]/td[2]/span/u (underlined invoice # link)
                // Ignore StaleElementReferenceException — Angular re-renders the table after search
                org.openqa.selenium.support.ui.WebDriverWait w =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                w.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
                w.until(org.openqa.selenium.support.ui.ExpectedConditions
                    .visibilityOfElementLocated(invoiceNumberFilterValue));
                String val = driver.findElement(invoiceNumberFilterValue).getText().trim();
                if (!val.isEmpty()) {
                    System.out.println("✓ Invoice number from table result: " + val);
                    return val;
                }

                // Fallback: flexible — any underlined span inside td[2] in any result row
                By flexLocator = By.xpath(
                    "//billing-list//p-table//tbody/tr/td[2]/span/u");
                java.util.List<WebElement> links = driver.findElements(flexLocator);
                if (!links.isEmpty()) {
                    String flex = links.get(0).getText().trim();
                    if (!flex.isEmpty()) {
                        System.out.println("✓ Invoice number from flex table result: " + flex);
                        return flex;
                    }
                }

                System.err.println("❌ Could not retrieve invoice number from search result");
                return "";
            } catch (Exception e) {
                System.err.println("❌ Could not retrieve invoice number filter value: " + e.getMessage());
                return "";
            }
        }

        //========== UTILITY METHODS ==========
        public String removeYearPrefix(String fullDate) {
            return fullDate.replace("/20", "/");
        }

        public String convertNameFormat(String fullName) {
            try {
                String[] nameParts = fullName.trim().split(" ");
                if (nameParts.length < 2) {
                    System.out.println("⚠ Name must have at least 2 parts");
                    return fullName;
                }

                String lastName = nameParts[0];
                StringBuilder firstAndMiddle = new StringBuilder();
                for (int i = 1; i < nameParts.length; i++) {
                    firstAndMiddle.append(nameParts[i]);
                    if (i < nameParts.length - 1) {
                        firstAndMiddle.append(" ");
                    }
                }

                String convertedName = firstAndMiddle.toString() + " " + lastName;
                System.out.println("Original: " + fullName);
                System.out.println("Converted: " + convertedName);
                return convertedName;

            } catch (Exception e) {
                System.out.println("✗ Error converting name: " + e.getMessage());
                return fullName;
            }
        }

        //========== COUNT VALIDATION LOCATORS & METHODS ==========

        // Locators for header counts
        private final By totalRecordCountLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[2]/div/div/span[1]/strong");
        private final By eamsNonVerifiedCountLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[2]/div/div/span[3]/strong");

        // Locators for table rows and statuses
        //private final By allTableRows = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[3]");
        private final By eamsVerifiedStatus = By.xpath(
                "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[12]/td[5]/div/div[1]/p-badge/span"
        );


        private final By eamsNotVerifiedStatus = By.xpath(
                "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[3]/td[5]/div/div[1]/p-badge/span"
        );


        private final By notVerifiedStatus = By.xpath(
                "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[15]/td[5]/div/div[1]/p-badge/span"
        );

        // SMOKE_DB_009: EAMS Scrubbing Comment locator
        private final By eamsSrubbingComment = By.xpath(
                "/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[3]/td[5]/div/div[2]"
        );

        /// ========== COUNT VALIDATION LOCATORS & METHODS (FIXED) ==========



        /**
         * Get Total Record Count from header
         * Example: "Total Record Count: 4" → returns 4
         */
        public int getTotalRecordCountFromHeader() {
            try {
                String headerText = driver.findElement(totalRecordCountLabel).getText().trim();
                String numberOnly = headerText.replaceAll("[^0-9]", "");
                return Integer.parseInt(numberOnly);
            } catch (Exception e) {
                System.err.println("❌ Failed to get total record count: " + e.getMessage());
                return -1;
            }
        }

        /**
         * Get EAMS Non-Verified Record Count from header
         * Example: "EAMS Non-Verified Record Count: 3" → returns 3
         */
        public int getEAMSNonVerifiedCountFromHeader() {
            try {
                String headerText = driver.findElement(eamsNonVerifiedCountLabel).getText().trim();
                String numberOnly = headerText.replaceAll("[^0-9]", "");
                return Integer.parseInt(numberOnly);
            } catch (Exception e) {
                System.err.println("❌ Failed to get EAMS non-verified count: " + e.getMessage());
                return -1;
            }
        }

        /**
         * CORRECT: Count actual invoice rows in the table
         * Counts only rows that have invoice data (not case headers or DOS rows)
         */
        public int getActualRowCount() {
            try {
                // Method 1: Count rows with invoice links (most reliable)
                By invoiceLinks = By.xpath("//p-table//tbody/tr//td[@class='text-center']//a");
                List<WebElement> links = driver.findElements(invoiceLinks);

                if (links.size() > 0) {
                    System.out.println("📊 Invoice Rows Found: " + links.size());
                    return links.size();
                }

                // Method 2: Count rows with "Verify" buttons
                By verifyButtons = By.xpath("//p-table//tbody/tr//button[contains(text(),'Verify')]");
                List<WebElement> buttons = driver.findElements(verifyButtons);

                if (buttons.size() > 0) {
                    System.out.println("📊 Invoice Rows Found (via Verify buttons): " + buttons.size());
                    return buttons.size();
                }

                // Method 3: Count status badges (each invoice has one status)
                By statusBadges = By.xpath("//span[contains(@class,'badge') and (contains(text(),'EAMS') or contains(text(),'Not Verified'))]");
                List<WebElement> badges = driver.findElements(statusBadges);

                System.out.println("📊 Invoice Rows Found (via badges): " + badges.size());
                return badges.size();

            } catch (Exception e) {
                System.err.println("❌ Failed to count invoice rows: " + e.getMessage());
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * Count invoices with "EAMS Verified" status
         */
   /* public int countEAMSVerifiedInvoices() {
        try {
            return driver.findElements(eamsVerifiedStatus).size();
        } catch (Exception e) {
            return 0;
        }
    } */
        public int countEAMSVerifiedInvoices() {
            try {
                // Method 1: Count rows with invoice links (most reliable)
                By invoiceLinks = By.xpath("//p-table//tbody/tr//td[@class='text-center']//a");
                List<WebElement> links = driver.findElements(invoiceLinks);

                if (links.size() > 0) {
                    System.out.println("📊 Invoice Rows Found: " + links.size());
                    return links.size();
                }

                // Method 2: Count rows with "Verify" buttons
                By verifyButtons = By.xpath("//p-table//tbody/tr//button[contains(text(),'Verify')]");
                List<WebElement> buttons = driver.findElements(verifyButtons);

                if (buttons.size() > 0) {
                    System.out.println("📊 Invoice Rows Found (via Verify buttons): " + buttons.size());
                    return buttons.size();
                }

                // Method 3: Count status badges (each invoice has one status)
                By statusBadges = By.xpath("//span[contains(@class,'badge') and (contains(text(),'EAMS Verified'))]");
                List<WebElement> badges = driver.findElements(statusBadges);

                System.out.println("📊 Invoice Rows Found (via badges): " + badges.size());
                return badges.size();

            } catch (Exception e) {
                System.err.println("❌ Failed to count invoice rows: " + e.getMessage());
                e.printStackTrace();
                return 0;
            }
        }


        public int countEAMSNotVerifiedInvoices() {
            try {
                // Method 1: Count rows with invoice links (most reliable)
                By invoiceLinks = By.xpath("//p-table//tbody/tr//td[@class='text-center']//a");
                List<WebElement> links = driver.findElements(invoiceLinks);

                if (links.size() > 0) {
                    System.out.println("📊 Invoice Rows Found: " + links.size());
                    return links.size();
                }

                // Method 2: Count rows with "Verify" buttons
                By verifyButtons = By.xpath("//p-table//tbody/tr//button[contains(text(),'Verify')]");
                List<WebElement> buttons = driver.findElements(verifyButtons);

                if (buttons.size() > 0) {
                    System.out.println("📊 Invoice Rows Found (via Verify buttons): " + buttons.size());
                    return buttons.size();
                }

                // Method 3: Count status badges (each invoice has one status)
                By statusBadges = By.xpath("//span[contains(@class,'badge') and (contains(text(),'EAMS Not Verified'))]");
                List<WebElement> badges = driver.findElements(statusBadges);

                System.out.println("📊 Invoice Rows Found (via badges): " + badges.size());
                return badges.size();

            } catch (Exception e) {
                System.err.println("❌ Failed to count invoice rows: " + e.getMessage());
                e.printStackTrace();
                return 0;
            }
        }


        /**
         * Count invoices with "EAMS Not Verified" status
         */
    /*
    public int countEAMSNotVerifiedInvoices() {
        try {
            return driver.findElements(eamsNotVerifiedStatus).size();
        } catch (Exception e) {
            return 0;
        }
    }*/

        /**
         * Count invoices with "Not Verified" status
         */
    /*
    public int countNotVerifiedInvoices() {
        try {
            return driver.findElements(notVerifiedStatus).size();
        } catch (Exception e) {
            return 0;
        }
    }*/
        public int countNotVerifiedInvoices() {
            try {
                // Method 1: Count rows with invoice links (most reliable)
                By invoiceLinks = By.xpath("//p-table//tbody/tr//td[@class='text-center']//a");
                List<WebElement> links = driver.findElements(invoiceLinks);

                if (links.size() > 0) {
                    System.out.println("📊 Invoice Rows Found: " + links.size());
                    return links.size();
                }

                // Method 2: Count rows with "Verify" buttons
                By verifyButtons = By.xpath("//p-table//tbody/tr//button[contains(text(),'Verify')]");
                List<WebElement> buttons = driver.findElements(verifyButtons);

                if (buttons.size() > 0) {
                    System.out.println("📊 Invoice Rows Found (via Verify buttons): " + buttons.size());
                    return buttons.size();
                }

                // Method 3: Count status badges (each invoice has one status)
                By statusBadges = By.xpath("//span[contains(@class,'badge') and (contains(text(),'Not Verified'))]");
                List<WebElement> badges = driver.findElements(statusBadges);

                System.out.println("📊 Invoice Rows Found (via badges): " + badges.size());
                return badges.size();

            } catch (Exception e) {
                System.err.println("❌ Failed to count invoice rows: " + e.getMessage());
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * Get total of all statuses (should equal total invoice count)
         */
        public int getTotalStatusCount() {
            return countEAMSVerifiedInvoices() +
                    countEAMSNotVerifiedInvoices() +
                    countNotVerifiedInvoices();
        }

        /**
         * Debug: Print all counts for troubleshooting
         */
        public void printCountDebugInfo() {
            System.out.println("\n🔍 ========== COUNT DEBUG INFO ==========");

            // Header counts
            int totalHeader = getTotalRecordCountFromHeader();
            int eamsHeader = getEAMSNonVerifiedCountFromHeader();

            System.out.println("📊 Header Counts:");
            System.out.println("   Total Record Count: " + totalHeader);
            System.out.println("   EAMS Non-Verified Count: " + eamsHeader);

            // Actual counts
            int actualRows = getActualRowCount();
            int eamsVerified = countEAMSVerifiedInvoices();
            int eamsNotVerified = countEAMSNotVerifiedInvoices();
            int notVerified = countNotVerifiedInvoices();
            int statusTotal = getTotalStatusCount();

            System.out.println("\n📊 Actual Counts:");
            System.out.println("   Invoice Rows: " + actualRows);
            System.out.println("   ✅ EAMS Verified: " + eamsVerified);
            System.out.println("   ❌ EAMS Not Verified: " + eamsNotVerified);
            System.out.println("   ⚠️  Not Verified: " + notVerified);
            System.out.println("   Total (sum of statuses): " + statusTotal);

            // Validation checks
            System.out.println("\n🔍 Validation:");
            System.out.println("   Total Header = Actual Rows? " + (totalHeader == actualRows ? "✅" : "❌"));
            System.out.println("   EAMS Header = EAMS Not Verified? " + (eamsHeader == eamsNotVerified ? "✅" : "❌"));
            System.out.println("   Status Total = Actual Rows? " + (statusTotal == actualRows ? "✅" : "❌"));

            System.out.println("==========================================\n");
        }
        //========== SMOKE_DB_008 - Report View Locators & Methods ==========

        // Locators for report view functionality
        private final By firstInvoiceCheckbox = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[1]/div/p-checkbox");
        private final By hcfaButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/div/div[2]/p-inputswitch/div/span");
        private final By reportViewButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[6]");
        private final By reportViewModal = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/p-dialog/div/div/div[2]/cyclone-reporting-board/div/form/div/div[2]");
        private final By reportViewContent = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/p-dialog/div/div/div[2]/cyclone-reporting-board/div/form/div/div[2]/div[1]/p-toolbar/div/div[1]/span");

        // Locators for Missing Information dialog (appears when invoice is not ready for billing)
        private final By missingInfoDialog = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/p-dialog/div/div/div[2]/div");
        private final By missingInfoCloseButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/p-dialog/div/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button");

        /**
         * Check if Missing Information dialog is displayed
         * This dialog appears when trying to select an invoice that's not ready for billing
         */
        private boolean isMissingInfoDialogDisplayed() {
            try {
                WaitUtils.sleep(1500); // Wait for dialog to appear if it's going to
                return driver.findElements(missingInfoDialog).size() > 0 &&
                        driver.findElement(missingInfoDialog).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Close the Missing Information dialog
         */
        private void closeMissingInfoDialog() {
            try {
                if (isMissingInfoDialogDisplayed()) {
                    WebElement closeButton = driver.findElement(missingInfoCloseButton);
                    closeButton.click();
                    WaitUtils.sleep(1000); // Wait for dialog to close
                    System.out.println("   ✓ Missing Information dialog closed");
                }
            } catch (Exception e) {
                System.err.println("   ⚠ Failed to close Missing Information dialog: " + e.getMessage());
            }
        }

        /**
         * SMOKE_DB_008: Select the first invoice checkbox
         */
        public void selectFirstInvoice() {
            try {
                WebElement checkbox = driver.findElement(firstInvoiceCheckbox);
                WaitUtils.sleep(1000);
                checkbox.click();
                System.out.println("✓ First invoice selected");
            } catch (Exception e) {
                System.err.println("❌ Failed to select first invoice: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_008 Enhanced: Find and select the first ready-to-bill invoice
         * Skips invoices with "Not Verified" status
         * Skips invoices that show "Missing Information" dialog (mandatory fields not filled)
         * Returns true if a ready-to-bill invoice was found and selected
         * Returns false if no ready-to-bill invoices were found on the page
         */
        public boolean findAndSelectReadyToBillInvoice() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("📊 Found " + rows.size() + " rows in the table");
                System.out.println("🔍 Searching for a ready-to-bill invoice...\n");

                // Iterate through each row to find a ready-to-bill invoice
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            System.out.println("   Row " + (i + 1) + " - Status: " + statusText);

                            // Skip invoices with "Not Verified" status - they're not ready for billing
                            if (statusText.equals("Not Verified")) {
                                System.out.println("   ⏭ Skipping - Not Verified status\n");
                                continue;
                            }

                            // Found a potentially ready-to-bill invoice
                            System.out.println("   ✓ Attempting to select invoice with status: " + statusText);

                            // Try to click the checkbox in column 1 (td[1]) for this row
                            try {
                                WebElement checkbox = row.findElement(By.xpath(".//td[1]//div"));
                                checkbox.click();
                                System.out.println("   ✓ Checkbox clicked, checking for errors...");

                                // Check if "Missing Information" dialog appeared
                                if (isMissingInfoDialogDisplayed()) {
                                    System.out.println("   ⚠ Missing Information dialog appeared - Invoice not ready for billing");
                                    closeMissingInfoDialog();
                                    System.out.println("   ⏭ Skipping to next invoice\n");
                                    continue; // Move to next invoice
                                }

                                // No dialog appeared - invoice is successfully selected!
                                System.out.println("   ✅ Invoice selected successfully - No errors!");
                                System.out.println("   📋 Final selection: Row " + (i + 1) + " with status '" + statusText + "'\n");
                                return true;

                            } catch (Exception checkboxError) {
                                System.out.println("   ⏭ Skipping row " + (i + 1) + " - Cannot click checkbox: " + checkboxError.getMessage());
                                System.out.println("      Reason: Element interaction failed\n");
                                // Continue to next invoice
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row
                            // Skip and continue
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                        System.err.println("⚠ Error processing row " + (i + 1) + ": " + e.getMessage());
                    }
                }

                System.err.println("\n❌ No ready-to-bill invoices found on this page");
                System.err.println("   All invoices are either 'Not Verified' or missing mandatory fields");
                return false;

            } catch (Exception e) {
                System.err.println("❌ Error finding ready-to-bill invoice: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_008: Click HCFA button
         */
        public void clickHCFAButton() {
            try {
                WebElement button = driver.findElement(hcfaButton);
                WaitUtils.sleep(1000);
                button.click();
                System.out.println("✓ HCFA button clicked");
            } catch (Exception e) {
                System.err.println("❌ Failed to click HCFA button: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_008: Click Report View button
         */
        public void clickReportViewButton() {
            try {
                WebElement button = driver.findElement(reportViewButton);
                WaitUtils.sleep(2000);
                button.click();
                System.out.println("✓ Report View button clicked");
            } catch (Exception e) {
                System.err.println("❌ Failed to click Report View button: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_008: Check if report view is displayed
         */
        public boolean isReportViewDisplayed() {
            try {
                WaitUtils.sleep(3000);
                // Check if modal or report content is visible
                boolean modalVisible = driver.findElements(reportViewModal).size() > 0;
                boolean contentVisible = driver.findElements(reportViewContent).size() > 0;

                return modalVisible || contentVisible;
            } catch (Exception e) {
                System.err.println("❌ Failed to verify report view: " + e.getMessage());
                return false;
            }
        }

        /**
         * SMOKE_DB_008: Get report view status
         */
        public String getReportViewStatus() {
            try {
                if (isReportViewDisplayed()) {
                    return "Report View Opened Successfully";
                } else {
                    return "Report View Failed to Open";
                }
            } catch (Exception e) {
                return "Error checking report view: " + e.getMessage();
            }
        }

        public void ClickCheckBox(){
            driver.findElement(checkbox).click();
        }

        public boolean isMissingInfoModalDisplayed() {
            try {
                return driver.findElement(informationMessageModal).isDisplayed();
            } catch (Exception e) {
                return false;
            }

        }

        // ========== SMOKE_DB_009: EAMS VERIFICATION METHODS ==========

        /**
         * Find and return the first invoice with EAMS Verified or EAMS Not Verified status
         * Returns a Map with "status" and "comment" keys
         * Returns null if no verified invoice found
         */
        public Map<String, String> findVerifiedInvoiceWithComment() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("📊 Found " + rows.size() + " rows in the table");

                // Iterate through each row
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            System.out.println("   Row " + (i + 1) + " Status: " + statusText);

                            // Check if this invoice is EAMS Verified or EAMS Not Verified
                            if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified") || statusText.equals("Multiple Carrier")) {
                                System.out.println("✓ Found EAMS processed invoice: " + statusText);

                                // Try to get the comment from the same cell (td[5])
                                try {
                                    // The comment is in td[5]/div/div[2] according to user's XPath
                                    // Structure: td[5] -> div -> div[1] (has p-badge) and div[2] (has comment)
                                    WebElement commentDiv = row.findElement(By.xpath(".//td[5]/div/div[2]"));
                                    String commentText = commentDiv.getText().trim();

                                    System.out.println("✓ Found Comment: " + commentText);

                                    // Create result map
                                    Map<String, String> result = new HashMap<>();
                                    result.put("status", statusText);
                                    result.put("comment", commentText);
                                    return result;

                                } catch (Exception commentError) {
                                    System.err.println("❌ Could not find comment for this invoice: " + commentError.getMessage());
                                    // Continue to next invoice
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row
                            // Skip and continue
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                        System.err.println("⚠ Error processing row " + (i + 1) + ": " + e.getMessage());
                    }
                }

                System.err.println("❌ No EAMS Verified or EAMS Not Verified invoices found in the table");
                return null;

            } catch (Exception e) {
                System.err.println("❌ Error finding verified invoice: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /**
         * DEPRECATED: Use findVerifiedInvoiceWithComment() instead
         * Get EAMS verification status from verified invoice
         */
        @Deprecated
        public String getEamsStatus() {
            Map<String, String> result = findVerifiedInvoiceWithComment();
            if (result != null) {
                return result.get("status");
            }
            return "No Status Found";
        }

        /**
         * DEPRECATED: Use findVerifiedInvoiceWithComment() instead
         * Get EAMS scrubbing comment text
         */
        @Deprecated
        public String getEamsSrubbingComment() {
            Map<String, String> result = findVerifiedInvoiceWithComment();
            if (result != null) {
                return result.get("comment");
            }
            return "";
        }

        /**
         * Validate if comment is one of the 8 valid EAMS comments
         * @param comment The comment text to validate
         * @return true if comment matches one of the 8 valid comments
         */
        public boolean isValidEamsComment(String comment) {
            String[] validComments = {
                    "Carrier and Address Match; Billed to Carrier on EAMS",
                    "Name Match, Address Mismatch; Replace Invoice Address with Address at EAMS; Billed to Carrier on EAMS ",
                    "Address Match, Name Mismatch; Replace Invoice Name with Name at EAMS; Billed to Carrier on EAMS",
                    "No Carrier in EAMS",
                    "No Carrier on Invoice; Billed to Carrier on EAMS",
                    "No Carrier on Invoice or EAMS; Billed to Single Employer",
                    "No Carrier on Invoice or EAMS; Billed to Multiple Employers",
                    "Invoice Data and EAMS Data Mismatch; (Billed to EAMS Carrier)",
                    "Invoice Data and EAMS Data Mismatch; Created Multiple Invoices (Billed to EAMS Carrier)"
            };

            for (String validComment : validComments) {
                if (comment.equals(validComment) || comment.contains(validComment)) {
                    System.out.println("✓ Comment is valid: " + comment);
                    return true;
                }
            }

            System.err.println("❌ Comment is NOT valid: " + comment);
            return false;
        }

        // ========== SMOKE_DB_011: EAMS DETAILS POPUP LOCATORS & METHODS ==========

        // EAMS Details Verification Popup locators
        private final By eamsDetailsPopup = By.xpath("/html/body/div[2]/div/div[2]/cyclone-eams-details-view/div");
        private final By eamsDetailsPopupTitle = By.xpath("/html/body/div[2]/div/div[2]/cyclone-eams-details-view/div/div/p-toolbar/div/div[1]/span");
        private final By eamsDetailsCloseButton = By.xpath("//cyclone-eams-details-view//button[contains(@class, 'close') or @aria-label='Close' or .//span[contains(@class, 'pi-times')]]");

        /**
         * SMOKE_DB_011: Close EAMS Details Verification Popup
         */
        public void closeEamsDetailsPopup() {
            try {
                WaitUtils.sleep(1000);

                // Try multiple common close button patterns
                List<By> closeButtonLocators = new java.util.ArrayList<>();
                closeButtonLocators.add(By.xpath("//cyclone-eams-details-view//button[contains(@class, 'close')]"));
                closeButtonLocators.add(By.xpath("//cyclone-eams-details-view//button[@aria-label='Close']"));
                closeButtonLocators.add(By.xpath("//cyclone-eams-details-view//button[.//span[contains(@class, 'pi-times')]]"));
                closeButtonLocators.add(By.xpath("//div[contains(@class, 'modal-header')]//button[@type='button']"));
                closeButtonLocators.add(By.xpath("//cyclone-eams-details-view//button[contains(@class, 'btn-close')]"));

                boolean closed = false;
                for (By locator : closeButtonLocators) {
                    try {
                        List<WebElement> buttons = driver.findElements(locator);
                        if (buttons.size() > 0 && buttons.get(0).isDisplayed()) {
                            buttons.get(0).click();
                            WaitUtils.sleep(1500);
                            System.out.println("   ✓ EAMS Details popup closed");
                            closed = true;
                            break;
                        }
                    } catch (Exception e) {
                        // Try next locator
                    }
                }

                if (!closed) {
                    // Fallback: Press ESC key
                    System.out.println("   ⚠ Close button not found, trying ESC key");
                    driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                    WaitUtils.sleep(1500);
                    System.out.println("   ✓ EAMS Details popup closed via ESC key");
                }

            } catch (Exception e) {
                System.err.println("   ⚠ Failed to close EAMS Details popup: " + e.getMessage());
            }
        }

        /**
         * SMOKE_DB_011: Find and click status button by status text (dynamic locator)
         * @param statusText The status text to search for ("Not Verified", "EAMS Verified", "EAMS Not Verified")
         */
        private void clickStatusButtonByText(String statusText) {
            try {
                WaitUtils.sleep(2000);

                // Find all status badges in the table
                By statusBadges = By.xpath("//p-table//tbody/tr//td[5]//p-badge/span[contains(@class, 'badge')]");
                List<WebElement> badges = driver.findElements(statusBadges);

                System.out.println("🔍 Searching for status button with text: '" + statusText + "'");
                System.out.println("   Found " + badges.size() + " status badges in table");

                // Find the badge with matching text
                boolean found = false;
                for (int i = 0; i < badges.size(); i++) {
                    WebElement badge = badges.get(i);
                    String badgeText = badge.getText().trim();

                    if (badgeText.equals(statusText)) {
                        System.out.println("✓ Found matching status badge at index " + i + ": " + badgeText);
                        badge.click();
                        System.out.println("✓ '" + statusText + "' status button clicked");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    throw new Exception("No status button found with text: '" + statusText + "'");
                }

            } catch (Exception e) {
                System.err.println("❌ Failed to click '" + statusText + "' button: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        /**
         * SMOKE_DB_011 Enhanced: Try to find and click status button by status text
         * Returns true if button found and clicked successfully
         * Returns false if button not found (does not throw exception)
         * @param statusText The status text to search for ("Not Verified", "EAMS Verified", "EAMS Not Verified")
         * @return true if button found and clicked, false if not found
         */
        public boolean tryClickStatusButton(String statusText) {
            try {
                WaitUtils.sleep(2000);

                // Find all status badges in the table
                By statusBadges = By.xpath("//p-table//tbody/tr//td[5]//p-badge/span[contains(@class, 'badge')]");
                List<WebElement> badges = driver.findElements(statusBadges);

                System.out.println("🔍 Searching for '" + statusText + "' status button...");
                System.out.println("   Total status badges found: " + badges.size());

                // Find the badge with matching text
                for (int i = 0; i < badges.size(); i++) {
                    WebElement badge = badges.get(i);
                    String badgeText = badge.getText().trim();

                    if (badgeText.equals(statusText)) {
                        System.out.println("   ✓ Found '" + statusText + "' at index " + i);
                        badge.click();
                        WaitUtils.sleep(1000);
                        System.out.println("   ✓ '" + statusText + "' button clicked successfully");
                        return true;
                    }
                }

                // Not found - return false instead of throwing exception
                System.out.println("   ⚠ '" + statusText + "' status not found under this DOS");
                return false;

            } catch (Exception e) {
                System.err.println("   ❌ Error while trying to click '" + statusText + "' button: " + e.getMessage());
                return false;
            }
        }

        /**
         * SMOKE_DB_011: Click "Not Verified" status button
         */
        public void clickNotVerifiedButton() {
            clickStatusButtonByText("Not Verified");
        }

        /**
         * SMOKE_DB_011: Click "EAMS Verified" status button
         */
        public void clickEamsVerifiedButton() {
            clickStatusButtonByText("EAMS Verified");
        }

        /**
         * SMOKE_DB_011: Click "EAMS Not Verified" status button
         */
        public void clickEamsNotVerifiedButton() {
            clickStatusButtonByText("EAMS Not Verified");
        }

        /**
         * SMOKE_DB_011: Check if EAMS Details Verification Popup is displayed
         */
        public boolean isEamsDetailsPopupDisplayed() {
            try {
                WaitUtils.sleep(3000); // Wait for popup to load
                return driver.findElements(eamsDetailsPopup).size() > 0 &&
                        driver.findElement(eamsDetailsPopup).isDisplayed();
            } catch (Exception e) {
                System.err.println("❌ EAMS Details popup not found: " + e.getMessage());
                return false;
            }
        }

        /**
         * SMOKE_DB_011: Get EAMS Details Popup title text
         */
        public String getEamsDetailsPopupTitle() {
            try {
                WaitUtils.sleep(2000);
                return driver.findElement(eamsDetailsPopupTitle).getText().trim();
            } catch (Exception e) {
                System.err.println("❌ Failed to get popup title: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_011: Verify EAMS Details Popup with expected title
         * @param expectedTitle The expected title text
         * @return true if popup is displayed and title matches
         */
        public boolean verifyEamsDetailsPopup(String expectedTitle) {
            try {
                if (!isEamsDetailsPopupDisplayed()) {
                    System.err.println("❌ EAMS Details popup is not displayed");
                    return false;
                }

                String actualTitle = getEamsDetailsPopupTitle();
                System.out.println("Popup Title: " + actualTitle);

                if (expectedTitle != null && !expectedTitle.isEmpty()) {
                    boolean titleMatches = actualTitle.contains(expectedTitle) || actualTitle.equals(expectedTitle);
                    if (!titleMatches) {
                        System.err.println("❌ Title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
                        return false;
                    }
                }

                System.out.println("✓ EAMS Details popup verified successfully");
                return true;

            } catch (Exception e) {
                System.err.println("❌ Error verifying popup: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_013: VIEW BUTTON FOR EAMS VERIFIED INVOICES ==========

        /**
         * SMOKE_DB_013: Find an invoice with "EAMS Verified" or "EAMS Not Verified" status and click View button
         * Returns true if View button was clicked successfully
         */
        public boolean clickViewButtonForEamsVerifiedInvoice() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for 'EAMS Verified' or 'EAMS Not Verified' invoice in " + rows.size() + " rows");

                // Iterate through each row
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            // Check if this invoice is "EAMS Verified" or "EAMS Not Verified"
                            if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {
                                System.out.println("✓ Found invoice with status: " + statusText + " at row " + (i + 1));

                                // Find and click the View (eye icon) button
                                // Typically in the last column or near the end
                                try {
                                    // Try to find eye icon button - common patterns
                                    WebElement viewButton = null;

                                    // Try pattern 1: Button with eye icon class
                                    try {
                                        viewButton = row.findElement(By.xpath(".//button[contains(@class, 'eye') or contains(@icon, 'eye') or @ptooltip='View']"));
                                    } catch (Exception e1) {
                                        // Try pattern 2: Icon with eye class
                                        try {
                                            viewButton = row.findElement(By.xpath(".//i[contains(@class, 'pi-eye') or contains(@class, 'fa-eye')]"));
                                        } catch (Exception e2) {
                                            // Try pattern 3: Any button in the action column (usually last td)
                                            try {
                                                viewButton = row.findElement(By.xpath(".//td[last()]//button[1]"));
                                            } catch (Exception e3) {
                                                // Try pattern 4: Button with specific tooltip or title
                                                viewButton = row.findElement(By.xpath(".//button[@title='View' or @aria-label='View']"));
                                            }
                                        }
                                    }

                                    if (viewButton != null) {
                                        WaitUtils.sleep(1000);
                                        viewButton.click();
                                        System.out.println("✓ View button clicked for '" + statusText + "' invoice");
                                        return true;
                                    }

                                } catch (Exception viewError) {
                                    System.err.println("❌ Could not find or click View button: " + viewError.getMessage());
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row - skip
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                    }
                }

                System.err.println("❌ No 'EAMS Verified' or 'EAMS Not Verified' invoice found in the table");
                return false;

            } catch (Exception e) {
                System.err.println("❌ Error finding/clicking View button: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public boolean clickViewButtonForEamsVerifiedInvoice1() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for 'EAMS Verified' or 'EAMS Not Verified' invoice in " + rows.size() + " rows");

                // Iterate through each row
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            // Check if this invoice is "EAMS Verified" or "EAMS Not Verified"
                            if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {
                                System.out.println("✓ Found invoice with status: " + statusText + " at row " + (i + 1));

                                // Find and click the View (eye icon) button
                                // Typically in the last column or near the end

                                try {
                                    // Try to find eye icon button - common patterns
                                    WebElement viewButton = null;

                                    // Try pattern 1: Button with eye icon class
                                    try {
                                        viewButton = row.findElement(By.xpath(".//button[contains(@class, 'eye') or contains(@icon, 'eye') or @ptooltip='View']"));
                                    } catch (Exception e1) {
                                        // Try pattern 2: Icon with eye class
                                        try {
                                            viewButton = row.findElement(By.xpath(".//i[contains(@class, 'pi-eye') or contains(@class, 'fa-eye')]"));
                                        } catch (Exception e2) {
                                            // Try pattern 3: Any button in the action column (usually last td)
                                            try {
                                                viewButton = row.findElement(By.xpath(".//td[last()]//button[1]"));
                                            } catch (Exception e3) {
                                                // Try pattern 4: Button with specific tooltip or title
                                                viewButton = row.findElement(By.xpath(".//button[@title='View' or @aria-label='View']"));
                                            }
                                        }
                                    }

                                    if (viewButton != null) {
                                        WaitUtils.sleep(1000);
                                        viewButton.click();
                                        System.out.println("✓ View button clicked for '" + statusText + "' invoice");
                                        return true;
                                    }

                                } catch (Exception viewError) {
                                    System.err.println("❌ Could not find or click View button: " + viewError.getMessage());
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row - skip
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                    }
                }

                System.err.println("❌ No 'EAMS Verified' or 'EAMS Not Verified' invoice found in the table");
                return false;

            } catch (Exception e) {
                System.err.println("❌ Error finding/clicking View button: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_013: Alternative method with specific XPath for View button
         * Use this if you know the exact XPath pattern for the View button
         */
        public boolean clickViewButtonForEamsVerifiedInvoiceByXPath(String viewButtonXPath) {
            try {
                WaitUtils.sleep(3000);

                // Find all table rows
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for EAMS verified invoice (using custom XPath)");

                for (int i = 0; i < rows.size(); i++) {
                    WebElement row = rows.get(i);

                    try {
                        WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                        String statusText = statusBadge.getText().trim();

                        if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {
                            System.out.println("✓ Found invoice: " + statusText);

                            // Click view button using provided XPath pattern
                            WebElement viewButton = row.findElement(By.xpath(viewButtonXPath));
                            WaitUtils.sleep(1000);
                            viewButton.click();
                            System.out.println("✓ View button clicked");
                            return true;
                        }

                    } catch (Exception e) {
                        // Skip this row
                    }
                }

                return false;

            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_012: NOT VERIFIED INVOICE CHECKBOX VALIDATION ==========

        /**
         * SMOKE_DB_012: Find an invoice with "Not Verified" status and click its checkbox
         * Returns true if checkbox was clicked successfully
         */
        public boolean clickCheckboxForNotVerifiedInvoice() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for 'Not Verified' invoice in " + rows.size() + " rows");

                // Iterate through each row
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            // Check if this invoice is "Not Verified"
                            if (statusText.equals("EAMS Not Verified")) {
                                System.out.println("✓ Found 'Not Verified' invoice at row " + (i + 1));

                                // Find and click the checkbox in column 1 (td[1])
                                try {
                                    WebElement checkbox = row.findElement(By.xpath(".//td[1]//div[@role='checkbox' or contains(@class, 'checkbox')]"));
                                    WaitUtils.sleep(1000);
                                    checkbox.click();
                                    System.out.println("✓ Checkbox clicked for 'Not Verified' invoice");
                                    return true;

                                } catch (Exception checkboxError) {
                                    System.err.println("❌ Could not find or click checkbox: " + checkboxError.getMessage());
                                    // Try alternative checkbox locator
                                    try {
                                        WebElement checkboxAlt = row.findElement(By.xpath(".//td[1]/div"));
                                        WaitUtils.sleep(1000);
                                        checkboxAlt.click();
                                        System.out.println("✓ Checkbox clicked (alternative locator) for 'Not Verified' invoice");
                                        return true;
                                    } catch (Exception altError) {
                                        System.err.println("❌ Alternative checkbox locator also failed: " + altError.getMessage());
                                    }
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row - skip
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                    }
                }

                System.err.println("❌ No 'Not Verified' invoice found in the table");
                return false;

            } catch (Exception e) {
                System.err.println("❌ Error finding/clicking checkbox for 'Not Verified' invoice: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_012: Check if the "Missing Information" or validation error modal is displayed
         * This popup appears when trying to select an invoice without EAMS verification
         */
        public boolean isValidationErrorModalDisplayed() {
            try {
                WaitUtils.sleep(2000); // Wait for modal to appear

                // Check if information message modal is displayed
                boolean modalDisplayed = driver.findElements(informationMessageModal).size() > 0 &&
                        driver.findElement(informationMessageModal).isDisplayed();

                if (modalDisplayed) {
                    System.out.println("✓ Validation error modal is displayed");
                    return true;
                } else {
                    System.out.println("❌ Validation error modal is NOT displayed");
                    return false;
                }

            } catch (Exception e) {
                System.err.println("❌ Error checking validation modal: " + e.getMessage());
                return false;
            }
        }

        /**
         * SMOKE_DB_012: Get the validation error modal message text
         */
        public String getValidationErrorMessage() {
            try {
                WaitUtils.sleep(1000);
                return driver.findElement(informationMessageModal).getText().trim();
            } catch (Exception e) {
                System.err.println("❌ Failed to get validation error message: " + e.getMessage());
                return "";
            }
        }

    public Map<String, Object> validateAllInvoicesEamsComments() {

        Map<String, Object> result = new HashMap<>();

        int totalInvoices = 0;
        int eamsVerifiedCount = 0;
        int eamsNotVerifiedCount = 0;
        int multipleCarrierCount = 0;
        int noCarrierInEamsCount = 0;
        int notVerifiedCount = 0;

        try {

            WaitUtils.sleep(3000);

            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class,'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            System.out.println("📊 Validating " + rows.size() + " rows in the table");
            System.out.println("═══════════════════════════════════════════════════════════");

            for (int i = 0; i < rows.size(); i++) {

                try {

                    WebElement row = rows.get(i);
                    WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                    String statusText = statusBadge.getText().trim();

                    totalInvoices++;
                    System.out.println("\n📋 Row " + (i + 1) + " - Status: " + statusText);

                    boolean requiresComment = false;

                    // ======================
                    // STATUS COUNT LOGIC
                    // ======================

                    if (statusText.equals("EAMS Verified")) {
                        eamsVerifiedCount++;
                        requiresComment = true;

                    } else if (statusText.equals("EAMS Not Verified")) {
                        eamsNotVerifiedCount++;
                        requiresComment = true;

                    } else if (statusText.equals("Multiple Carrier")) {
                        multipleCarrierCount++;
                        requiresComment = true;

                    } else if (statusText.equals("No Carrier in EAMS")) {
                        noCarrierInEamsCount++;
                        requiresComment = true;

                    } else if (statusText.equals("Not Verified")) {
                        notVerifiedCount++;
                        requiresComment = false;

                    } else {
                        System.out.println("⚠ Unknown status: " + statusText);
                        continue;
                    }

                    // ======================
                    // COMMENT VALIDATION
                    // ======================

                    if (requiresComment) {

                        try {
                            WebElement commentDiv = row.findElement(By.xpath(".//td[5]/div/div[2]"));
                            String commentText = commentDiv.getText().trim();

                            System.out.println("   💬 Comment: " + commentText);

                            if (commentText.isEmpty()) {
                                result.put("error", "Row " + (i + 1) +
                                        ": Status '" + statusText +
                                        "' requires comment but it is EMPTY.");
                                result.put("failedRow", i + 1);
                                result.put("success", false);
                                return result;
                            }

                            if (!isValidEamsComment(commentText)) {
                                result.put("error", "Row " + (i + 1) +
                                        ": Invalid EAMS comment → " + commentText);
                                result.put("failedRow", i + 1);
                                result.put("success", false);
                                return result;
                            }

                            System.out.println("   ✅ Valid");

                        } catch (Exception e) {
                            result.put("error", "Row " + (i + 1) +
                                    ": Comment element not found for status '" +
                                    statusText + "'");
                            result.put("failedRow", i + 1);
                            result.put("success", false);
                            return result;
                        }

                    } else {

                        // Not Verified must NOT have comment

                        try {
                            WebElement commentDiv = row.findElement(By.xpath(".//td[5]/div/div[2]"));
                            String commentText = commentDiv.getText().trim();

                            if (!commentText.isEmpty()) {
                                result.put("error", "Row " + (i + 1) +
                                        ": 'Not Verified' should NOT have comment → " +
                                        commentText);
                                result.put("failedRow", i + 1);
                                result.put("success", false);
                                return result;
                            }

                            System.out.println("   ✅ No comment (correct)");

                        } catch (Exception ignored) {
                            // Correct scenario – no comment exists
                            System.out.println("   ✅ No comment (correct)");
                        }
                    }

                } catch (Exception ignored) {
                    // Skip grouping rows safely
                }
            }

            System.out.println("\n═══════════════════════════════════════════════════════════");

            result.put("totalInvoices", totalInvoices);
            result.put("eamsVerifiedCount", eamsVerifiedCount);
            result.put("eamsNotVerifiedCount", eamsNotVerifiedCount);
            result.put("multipleCarrierCount", multipleCarrierCount);
            result.put("noCarrierInEamsCount", noCarrierInEamsCount);
            result.put("notVerifiedCount", notVerifiedCount);
            result.put("success", true);

            return result;

        } catch (Exception e) {

            result.put("error", "Unexpected error: " + e.getMessage());
            result.put("success", false);
            return result;
        }
    }

        /* ========== SMOKE_DB_013 ========== */
        private final By ClaimForm = By.xpath("/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/span[2]");

        public boolean isDisplayHCFAForm() {
            driver.findElement(ClaimForm).isDisplayed();
            String ActualForm = driver.findElement(ClaimForm).getText().trim();
            String ExpectedForm = "CLAIM FORM";
            if (!ActualForm.equals(ExpectedForm)) {
                System.err.println("❌ HCFA Form text mismatch. Expected: " + ExpectedForm + ", Actual: " + ActualForm);
                return false;
            } else {
                System.out.println("✓ HCFA Form text matches expected: " + ExpectedForm);
            }
            return true;
        }

        // ========== SMOKE_DB_014: SELECT EAMS VERIFIED INVOICES & TRACK UNIQUE CLAIM ADMINS ==========

        /**
         * SMOKE_DB_014: Select EAMS Verified/Not Verified invoices and track unique Claim Admins
         *
         * Hierarchy:
         *   Claim Admin (Header Row - span[3])
         *     └─ Case Number: ADJxxxx (Sub-header Row - span[4])
         *         └─ Invoice rows (with checkboxes)
         *
         * CORRECT Business Rule: 1 Unique Claim Admin = 1 HCFA Form + 1 SOP Form + All Invoices under that Claim Admin
         *
         * Each Claim Admin gets one set of forms:
         *   - 1 HCFA Form (Health Insurance Claim Form 1500)
         *   - 1 SOP Form (Summary of Payments)
         *   - All selected invoices under that Claim Admin
         *
         * @param maxInvoicesToSelect Maximum invoices to select (0 = select all available)
         * @return Set of unique Claim Admins from selected invoices
         */
        public java.util.Set<String> selectEamsVerifiedInvoicesAndGetClaimAdmins(int maxInvoicesToSelect) {
            java.util.Set<String> uniqueClaimAdmins = new java.util.HashSet<>();
            int selectedCount = 0;

            try {
                // Wait for table to load after filter
                System.out.println("\n⏳ Waiting for table to load after filter...");
                WaitUtils.sleep(5000);

                // Find all table rows - try multiple locator strategies
                By allTableRows = By.xpath("//p-table//tbody/tr");
                List<WebElement> rows = driver.findElements(allTableRows);

                // If no rows found with first locator, try alternative
                if (rows.size() == 0) {
                    System.out.println("   ⚠ No rows found with first locator, trying alternative...");
                    allTableRows = By.xpath("//tbody/tr");
                    rows = driver.findElements(allTableRows);
                }

                System.out.println("\n🔍 Searching for EAMS Verified/Not Verified invoices...");
                System.out.println("   Total rows in table: " + rows.size());

                // If still no rows, print debug info
                if (rows.size() == 0) {
                    System.err.println("   ⚠️ WARNING: No table rows found!");
                    System.err.println("   Possible reasons:");
                    System.err.println("   1. Table is still loading (increase wait time)");
                    System.err.println("   2. DOS filter returned no results");
                    System.err.println("   3. Table structure has changed");

                    // Try to find any p-table element
                    List<WebElement> tables = driver.findElements(By.xpath("//p-table"));
                    System.err.println("   p-table elements found: " + tables.size());
                }

                // Iterate through rows
                for (int i = 0; i < rows.size(); i++) {
                    // Stop if max selection reached
                    if (maxInvoicesToSelect > 0 && selectedCount >= maxInvoicesToSelect) {
                        System.out.println("✓ Reached max selection: " + maxInvoicesToSelect);
                        break;
                    }

                    try {
                        WebElement row = rows.get(i);

                        // Check if this row has a status badge (invoice row)
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            // Only select EAMS Verified or EAMS Not Verified invoices
                            if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {

                                // Find the Claim Admin for this invoice
                                String claimAdmin = findClaimAdminForInvoiceRow(rows, i);

                                if (claimAdmin != null && !claimAdmin.isEmpty()) {
                                    System.out.println("\n   Row " + (i + 1) + ":");
                                    System.out.println("   ├─ Status: " + statusText);
                                    System.out.println("   └─ Claim Admin: " + claimAdmin);

                                    // Click checkbox to select invoice
                                    boolean checkboxClicked = clickCheckboxInRow(row, i + 1);

                                    if (checkboxClicked) {
                                        uniqueClaimAdmins.add(claimAdmin);
                                        selectedCount++;
                                    }
                                }
                            }

                        } catch (Exception e) {
                            // Not an invoice row, skip
                        }

                    } catch (Exception e) {
                        // Skip problematic rows
                    }
                }

                System.out.println("\n" + "=".repeat(60));
                System.out.println("📊 SELECTION SUMMARY");
                System.out.println("=".repeat(60));
                System.out.println("   Total Invoices Selected: " + selectedCount);
                System.out.println("   Unique Claim Admins: " + uniqueClaimAdmins.size());
                System.out.println("   Claim Admin List: " + uniqueClaimAdmins);
                System.out.println("=".repeat(60) + "\n");

                return uniqueClaimAdmins;

            } catch (Exception e) {
                System.err.println("❌ Error selecting invoices: " + e.getMessage());
                e.printStackTrace();
                return uniqueClaimAdmins;
            }
        }

        /**
         * Find Claim Admin for an invoice row by searching backward through rows
         * Claim Admin is in a header row: tr[]/td/b/span[3]
         * XPath example: /html/body/.../tbody/tr[21]/td/b/span[3]
         */
        private String findClaimAdminForInvoiceRow(List<WebElement> allRows, int invoiceRowIndex) {
            try {
                // Search backwards from current invoice row to find Claim Admin header
                for (int j = invoiceRowIndex; j >= 0; j--) {
                    WebElement row = allRows.get(j);

                    try {
                        // Look for Claim Admin pattern: td/b/span[3]
                        WebElement claimAdminElement = row.findElement(By.xpath(".//td/b/span[3]"));
                        String claimAdminText = claimAdminElement.getText().trim();

                        // Claim Admin should not be empty and not start with "ADJ" (that's Case Number in span[4])
                        if (!claimAdminText.isEmpty() && !claimAdminText.startsWith("ADJ")) {
                            return claimAdminText;
                        }
                    } catch (Exception e) {
                        // No Claim Admin in this row, continue
                    }
                }

                System.err.println("   ⚠ Could not find Claim Admin for row " + (invoiceRowIndex + 1));
                return "";

            } catch (Exception e) {
                System.err.println("   ❌ Error finding Claim Admin: " + e.getMessage());
                return "";
            }
        }

        /**
         * Click checkbox in a given row
         */
        private boolean clickCheckboxInRow(WebElement row, int rowNumber) {
            try {
                // Try primary checkbox locator
                WebElement checkbox = row.findElement(By.xpath(".//td[1]//div[@role='checkbox' or contains(@class, 'checkbox')]"));
                WaitUtils.sleep(500);
                checkbox.click();
                System.out.println("   ✓ Checkbox clicked");
                return true;

            } catch (Exception e1) {
                // Try alternative checkbox locator
                try {
                    WebElement checkboxAlt = row.findElement(By.xpath(".//td[1]/div"));
                    WaitUtils.sleep(500);
                    checkboxAlt.click();
                    System.out.println("   ✓ Checkbox clicked (alt)");
                    return true;

                } catch (Exception e2) {
                    System.err.println("   ❌ Failed to click checkbox at row " + rowNumber);
                    return false;
                }
            }
        }

        /**
         * SMOKE_DB_014: Debug helper - Save Report View HTML to file for analysis
         */
        public void debugSaveReportViewHTML() {
            try {
                System.out.println("\n🔍 DEBUG: Saving Report View HTML...");

                String pageSource = driver.getPageSource();
                String filePath = "debug-report-view.html";

                java.nio.file.Files.write(
                        java.nio.file.Paths.get(filePath),
                        pageSource.getBytes()
                );

                System.out.println("   ✓ HTML saved to: " + filePath);
                System.out.println("   📁 File location: " + new java.io.File(filePath).getAbsolutePath());

                // Additional debugging: Print structure information
                System.out.println("\n🔍 DEBUG: Analyzing page structure...");

                // Check for iframes
                List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                System.out.println("   Total iframes on page: " + iframes.size());

                // Check for dialog/modal elements
                List<WebElement> dialogs = driver.findElements(By.tagName("p-dialog"));
                System.out.println("   Total p-dialog elements: " + dialogs.size());

                // Try to find ANY div structure under body
                List<WebElement> bodyDivs = driver.findElements(By.xpath("/html/body/div"));
                System.out.println("   Direct div children of body: " + bodyDivs.size());

                if (!bodyDivs.isEmpty() && bodyDivs.size() >= 1) {
                    try {
                        // Check first body > div
                        List<WebElement> firstDivChildren = bodyDivs.get(0).findElements(By.xpath("./div"));
                        System.out.println("   /html/body/div[1] has " + firstDivChildren.size() + " div children");

                        if (firstDivChildren.size() >= 2) {
                            List<WebElement> secondDivChildren = firstDivChildren.get(1).findElements(By.xpath("./div"));
                            System.out.println("   /html/body/div[1]/div[2] has " + secondDivChildren.size() + " div children");
                        }
                    } catch (Exception e) {
                        System.err.println("   Error analyzing div structure: " + e.getMessage());
                    }
                }

            } catch (Exception e) {
                System.err.println("❌ Error saving HTML: " + e.getMessage());
            }
        }

        // ========== SMOKE_DB_014: EMC FILTER LOCATORS & METHODS ==========

        // EMC Filter Radio Button
        private final By emcFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[3]/div/div/label[2]/p-radiobutton/div/div[2]");

        // ========== SMOKE_DB_015: EMAIL FILTER LOCATORS ==========

        // EMAIL Filter Radio Button
        private final By emailFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[3]/div/div/label[3]/p-radiobutton/div/div[2]");

        // EMAIL Toggle Button (in Claim Administrator popup) - CORRECTED: div[11] instead of div[8]
        private final By emailToggleButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[11]/div/div/div/div/div/div[1]/p-inputswitch");

        // Bill Reviewer Email Field
        private final By billReviewerEmailInput = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[14]/div/div/div/div/input");

        // ========== SMOKE_DB_016: FAX FILTER LOCATORS ==========

        // FAX Filter Radio Button
        private final By faxFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[3]/div/div/label[4]/p-radiobutton/div/div[2]");

        // FAX Toggle Button (in Claim Administrator popup) - div[11]/div[2] for FAX toggle
        private final By faxToggleButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[11]/div/div/div/div/div/div[2]/div/div/p-inputswitch");

        // Bill Reviewer Email Field (same as EMAIL filter - mandatory for FAX too)
        private final By billReviewerEmailInputFax = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[15]/div/div[3]/div/div/p-inputmask/input");

        // Bill Reviewer FAX Number Field (mandatory for FAX)
        private final By billReviewerFaxNumberInput = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[15]/div/div[3]/div/div/p-inputmask/input");

        // ========== SMOKE_DB_017: PAPER FILTER LOCATORS ==========

        // Paper Filter Radio Button
        private final By paperFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[3]/div/div/label[5]/p-radiobutton/div/div[2]");

        // EMC Toggle Button (for Paper filter validation - must be DISABLED)
        private final By emcToggleButtonPaper = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[8]/div/div/div/div");

        // EMAIL Toggle Button (for Paper filter validation - must be DISABLED)
        private final By emailToggleButtonPaper = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[11]/div/div/div/div/div/div[1]");

        // FAX Toggle Button (for Paper filter validation - must be DISABLED)
        private final By faxToggleButtonPaper = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[11]/div/div/div/div/div/div[2]/div/div");

        // ========== SMOKE_DB_018: EMC SUBMISSION LOCATORS ==========

        // HCFA Toggle Button (enable before EMC submission)
        private final By hcfaToggleButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/div/div[2]/p-inputswitch");

        // EMC Submission Button
        private final By emcSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[1]");

        // Success Message Toast
        private final By successMessageToast = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p");

        // ========== SMOKE_DB_019: E/P SUBMISSION LOCATORS ==========

        // E/P Submission Button (EMC and Paper combined)
        private final By epSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[2]");

        // Report View Label (displayed after Paper submission via E/P)
        private final By reportViewLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/p-dialog/div/div/div[2]/cyclone-reporting-board/div/form/div/div[2]/div[1]/p-toolbar/div/div[1]/span");

        // ========== SMOKE_DB_020: MAIL SUBMISSION LOCATORS ==========

        // Mail Filter Radio Button
        private final By mailFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[3]/div/div/label[3]/p-radiobutton/div/div[2]");

        // Mail Submission Button
        private final By mailSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[3]");

        // ========== SMOKE_DB_021: FAX SUBMISSION LOCATORS ==========

        // FAX Submission Button (note: same position as Mail - button[3], context-sensitive)
        private final By faxSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[4]");

        // FAX Success Message Toast
        private final By faxSuccessMessageToast = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p");

        // ========== SMOKE_DB_022: PAPER SUBMISSION LOCATORS ==========

        // Paper Submission Button
        private final By paperSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[5]");

        // Invoice Number link (dynamically find first EAMS Verified/Not Verified)
        private final By firstInvoiceNumberLink = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[3]/td[2]/span");

        // Edit Invoice Label
        private final By editInvoiceLabel = By.xpath("/html/body/div[2]/div/div[2]/div/div[1]/p-toolbar/div/div[1]/span");

        // Edit Button to enable EDIT MODE
        private final By editButton = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[1]/p-toolbar/div/div[2]/button[6]");

        // Claim Administrator Link
        private final By claimAdministratorLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[1]/span[1]/u/b");

        // Claim Administrator Section Label
        private final By claimAdministratorSectionLabel = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/div/p-toolbar/div/div[1]/span");

        // EMC Toggle Button
        private final By emcToggleButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[8]/div/div/div/div/div/div/p-inputswitch");

        // EDI PayerID Input
        private final By ediPayerIdInput = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[10]/div/div/div/div/input");

        /**
         * SMOKE_DB_014: Click EMC Filter Radio Button
         */
        public void clickEmcFilter() {
            try {
                WebElement emcFilter = driver.findElement(emcFilterRadioButton);
                WaitUtils.sleep(1000);
                emcFilter.click();
                System.out.println("✓ EMC Filter clicked");
                WaitUtils.sleep(3000); // Wait for invoices to load
            } catch (Exception e) {
                System.err.println("❌ Failed to click EMC Filter: " + e.getMessage());
                throw e;
            }
        }



        /**
         * SMOKE_DB_014: Find and click first invoice with "EAMS Verified" or "EAMS Not Verified" status
         * Returns true if invoice was clicked successfully
         */
        public boolean clickFirstEamsVerifiedInvoiceNumber() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody

                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for EAMS Verified/Not Verified invoice in " + rows.size() + " rows");


                // Iterate through each row
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));

                            String statusText = statusBadge.getText().trim();

                            // Check if this invoice is "EAMS Verified" or "EAMS Not Verified"
                            if (statusText.equals("EAMS Verified") || statusText.equals("Address requires verification") || statusText.equals("No Carrier in EAMS") || statusText.equals("Multiple Carrier") || statusText.equals("EAMS Carrier") || statusText.equals("No Carrier; Employer Bill") || statusText.equals("EAMS Not Verified") || statusText.equals("Nothing Match")) {
                                System.out.println("✓ Found invoice with status: " + statusText + " at row " + (i + 1));

                                // Find and click the invoice number link in column 2 (td[2])
                                try {
                                    // Try to find clickable invoice link - could be in span/u or just span
                                    WebElement invoiceLink = null;

                                    // Try pattern 1: span/u (underlined link)
                                    try {
                                        invoiceLink = row.findElement(By.xpath(".//td[2]//span/u"));
                                    } catch (Exception e1) {
                                        // Try pattern 2: just span
                                        try {
                                            invoiceLink = row.findElement(By.xpath(".//td[2]//span"));
                                        } catch (Exception e2) {
                                            // Try pattern 3: any anchor tag
                                            invoiceLink = row.findElement(By.xpath(".//td[2]//a"));
                                        }
                                    }

                                    if (invoiceLink != null) {
                                        WaitUtils.sleep(1000);
                                        invoiceLink.click();
                                        System.out.println("✓ Invoice number clicked for '" + statusText + "' invoice");
                                        return true;
                                    }

                                } catch (Exception linkError) {
                                    System.err.println("❌ Could not find or click invoice number: " + linkError.getMessage());
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row - skip
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                    }
                }

                System.err.println("❌ No 'EAMS Verified' or 'EAMS Not Verified' invoice found in the table");
                return false;

            } catch (Exception e) {
                System.err.println("❌ Error finding/clicking invoice number: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_014: Get Edit Invoice label text
         */
        public String getEditInvoiceLabel() {
            try {
                WaitUtils.sleep(2000);
                return driver.findElement(editInvoiceLabel).getText().trim();
            } catch (Exception e) {
                System.err.println("❌ Failed to get Edit Invoice label: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_014: Click Edit button to enable EDIT MODE
         */
        public void clickEditButton() {
            try {
                // Wait for splash screen to disappear before clicking Edit button
                try {
                    By splashScreen = By.xpath("//div[@class='splash-screen ng-star-inserted']");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                    wait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(splashScreen));
                    System.out.println("✓ Splash screen disappeared");
                } catch (Exception e) {
                    System.out.println("⚠ No splash screen found or already disappeared");
                }

                WaitUtils.sleep(2000); // Additional wait for page to settle

                WebElement editBtn = driver.findElement(editButton);
                editBtn.click();
                System.out.println("✓ Edit button clicked - EDIT MODE enabled");
                WaitUtils.sleep(2000);
            } catch (Exception e) {
                System.err.println("❌ Failed to click Edit button: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_014: Click Claim Administrator link
         */
        public void clickClaimAdministrator() {
            try {
                WebElement claimAdminLink = driver.findElement(claimAdministratorLink);
                WaitUtils.sleep(1000);
                claimAdminLink.click();
                System.out.println("✓ Claim Administrator clicked");
                WaitUtils.sleep(2000);
            } catch (Exception e) {
                System.err.println("❌ Failed to click Claim Administrator: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_014: Get Claim Administrator section label text
         */
        public String getClaimAdministratorSectionLabel() {
            try {
                WaitUtils.sleep(2000);
                return driver.findElement(claimAdministratorSectionLabel).getText().trim();
            } catch (Exception e) {
                System.err.println("❌ Failed to get Claim Administrator section label: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_014: Check if EMC Toggle Button is enabled
         * Returns true if the toggle is in "on" or "enabled" state
         */


        public boolean isEmcToggleEnabled() {
            try {
                // Try multiple strategies to detect toggle state
                By toggleLocator = By.xpath("//cyclone-edit-claim-admin//p-inputswitch");

                // Wait for toggle to be visible
                WaitUtils.waitForVisibility(driver, toggleLocator);
                WaitUtils.sleep(1000); // Give toggle time to settle
                WebElement toggle = driver.findElement(toggleLocator);

                // Debug output - print all relevant attributes
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");
                String ngReflectModel = toggle.getAttribute("ng-reflect-model");
                String outerHTML = toggle.getAttribute("outerHTML");

                System.out.println("\n=== EMC Toggle Debug Info ===");
                System.out.println("aria-checked: " + ariaChecked);
                System.out.println("class: " + className);
                System.out.println("ng-reflect-model: " + ngReflectModel);
                System.out.println("outerHTML: " + (outerHTML != null && outerHTML.length() > 200 ? outerHTML.substring(0, 200) + "..." : outerHTML));
                System.out.println("=============================\n");

                // Check multiple indicators (PrimeNG toggle detection)
                boolean enabled = false;

                // Strategy 1: aria-checked attribute
                if ("true".equalsIgnoreCase(ariaChecked)) {
                    enabled = true;
                    System.out.println("✓ Detected via aria-checked='true'");
                }

                // Strategy 2: class contains checked
                if (className != null && className.contains("p-inputswitch-checked")) {
                    enabled = true;
                    System.out.println("✓ Detected via class contains 'p-inputswitch-checked'");
                }

                // Strategy 3: ng-reflect-model (Angular binding)
                if ("true".equalsIgnoreCase(ngReflectModel)) {
                    enabled = true;
                    System.out.println("✓ Detected via ng-reflect-model='true'");
                }

                // Strategy 4: Check the hidden input inside the toggle
                try {
                    WebElement hiddenInput = toggle.findElement(By.xpath(".//input[@type='checkbox']"));
                    if (hiddenInput != null) {
                        boolean checked = hiddenInput.isSelected();
                        System.out.println("Hidden checkbox isSelected: " + checked);
                        if (checked) {
                            enabled = true;
                            System.out.println("✓ Detected via hidden checkbox isSelected()");
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("No hidden checkbox found");
                }

                System.out.println("\nFinal EMC Toggle State: " + (enabled ? "ENABLED ✓" : "DISABLED ✗"));
                return enabled;

            } catch (Exception e) {
                System.err.println("❌ Error detecting EMC toggle: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }





        /**
         * SMOKE_DB_014: Get EDI PayerID value
         * Returns the value of the EDI PayerID input field
         */
        public String getEdiPayerId() {
            try {
                WebElement ediInput = driver.findElement(ediPayerIdInput);
                WaitUtils.sleep(1000);
                String payerId = ediInput.getAttribute("value");

                System.out.println("EDI PayerID: " + (payerId == null || payerId.isEmpty() ? "BLANK ✗" : payerId + " ✓"));
                return payerId == null ? "" : payerId;

            } catch (Exception e) {
                System.err.println("❌ Failed to get EDI PayerID: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_014: Validate EMC Ready invoice
         * Returns true if EMC Toggle is enabled AND EDI PayerID is not blank
         */
        public boolean isEmcReadyInvoice() {
            try {
                boolean emcEnabled = isEmcToggleEnabled();
                String ediPayerId = getEdiPayerId();
                boolean payerIdNotBlank = !ediPayerId.isEmpty();

                // Business Rule: BOTH EMC Toggle must be enabled AND EDI PayerID must be present
                boolean isReady = emcEnabled && payerIdNotBlank;

                System.out.println("\n" + "=".repeat(60));
                System.out.println("📊 EMC READY VALIDATION");
                System.out.println("=".repeat(60));
                System.out.println("   EMC Toggle Enabled: " + (emcEnabled ? "YES ✓" : "NO ✗"));
                System.out.println("   EDI PayerID Not Blank: " + (payerIdNotBlank ? "YES ✓" : "NO ✗"));
                System.out.println("   EDI PayerID Value: " + (ediPayerId.isEmpty() ? "BLANK" : ediPayerId));
                System.out.println("   Invoice EMC Ready: " + (isReady ? "YES ✓" : "NO ✗"));
                System.out.println("   Business Rule: EMC Toggle ENABLED + EDI PayerID NOT BLANK");
                System.out.println("=".repeat(60) + "\n");

                return isReady;

            } catch (Exception e) {
                System.err.println("❌ Error validating EMC Ready status: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_015: EMAIL FILTER & VALIDATION METHODS ==========

        /**
         * SMOKE_DB_015: Click EMAIL Filter Radio Button
         */
        public void clickEmailFilter() {
            try {
                WebElement emailFilter = driver.findElement(emailFilterRadioButton);
                WaitUtils.sleep(1000);
                emailFilter.click();
                System.out.println("✓ EMAIL Filter clicked");
                WaitUtils.sleep(3000); // Wait for invoices to load
            } catch (Exception e) {
                System.err.println("❌ Failed to click EMAIL Filter: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_015: Check if EMAIL Toggle Button is enabled
         * Returns true if the toggle is in "on" or "enabled" state
         */
        public boolean isEmailToggleEnabled() {
            try {
                // Use the specific EMAIL toggle locator (div[11], not the generic p-inputswitch which finds EMC toggle at div[8])
                By toggleLocator = emailToggleButton;

                // Wait for toggle to be visible
                WaitUtils.waitForVisibility(driver, toggleLocator, 10);
                WaitUtils.sleep(1000); // Give toggle time to settle
                WebElement toggle = driver.findElement(toggleLocator);

                // Debug output - print all relevant attributes
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");
                String ngReflectModel = toggle.getAttribute("ng-reflect-model");

                System.out.println("\n=== EMAIL Toggle Debug Info ===");
                System.out.println("aria-checked: " + ariaChecked);
                System.out.println("class: " + className);
                System.out.println("ng-reflect-model: " + ngReflectModel);
                System.out.println("=============================\n");

                // Check multiple indicators (PrimeNG toggle detection)
                boolean enabled = false;

                // Strategy 1: aria-checked attribute
                if ("true".equalsIgnoreCase(ariaChecked)) {
                    enabled = true;
                    System.out.println("✓ Detected via aria-checked='true'");
                }

                // Strategy 2: class contains checked
                if (className != null && className.contains("p-inputswitch-checked")) {
                    enabled = true;
                    System.out.println("✓ Detected via class contains 'p-inputswitch-checked'");
                }

                // Strategy 3: ng-reflect-model (Angular binding)
                if ("true".equalsIgnoreCase(ngReflectModel)) {
                    enabled = true;
                    System.out.println("✓ Detected via ng-reflect-model='true'");
                }

                // Strategy 4: Check the hidden input inside the toggle
                try {
                    WebElement hiddenInput = toggle.findElement(By.xpath(".//input[@type='checkbox']"));
                    if (hiddenInput != null) {
                        boolean checked = hiddenInput.isSelected();
                        System.out.println("Hidden checkbox isSelected: " + checked);
                        if (checked) {
                            enabled = true;
                            System.out.println("✓ Detected via hidden checkbox isSelected()");
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("No hidden checkbox found");
                }

                System.out.println("\nFinal EMAIL Toggle State: " + (enabled ? "ENABLED ✓" : "DISABLED ✗"));
                return enabled;

            } catch (Exception e) {
                System.err.println("❌ Error detecting EMAIL toggle: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_015: Get Bill Reviewer Email value
         * Returns the email address from the Bill Reviewer Email field
         * Note: This field is MANDATORY for EMAIL Ready invoices
         */
        public String getBillReviewerEmail() {
            try {
                WebElement emailInput = driver.findElement(billReviewerEmailInput);
                WaitUtils.sleep(1000);

                // Try to get value from input field or text content
                String email = emailInput.getAttribute("value");
                if (email == null || email.isEmpty()) {
                    email = emailInput.getText().trim();
                }

                System.out.println("Bill Reviewer Email: " + (email == null || email.isEmpty() ? "BLANK ✗ (MANDATORY)" : email + " ✓"));
                return email == null ? "" : email;

            } catch (Exception e) {
                System.err.println("❌ Failed to get Bill Reviewer Email: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_015: Validate EMAIL Ready invoice
         * Returns true if BOTH EMAIL Toggle is enabled AND Bill Reviewer Email is not blank
         * Business Rule: BOTH EMAIL Toggle enabled AND Bill Reviewer Email required for Email submission
         */
        public boolean isEmailReadyInvoice() {
            try {
                boolean emailEnabled = isEmailToggleEnabled();
                String billReviewerEmail = getBillReviewerEmail();
                boolean emailNotBlank = !billReviewerEmail.isEmpty();

                // Business Rule: BOTH EMAIL Toggle must be enabled AND Bill Reviewer Email must not be blank
                boolean isReady = emailEnabled && emailNotBlank;

                System.out.println("\n" + "=".repeat(60));
                System.out.println("📊 EMAIL READY VALIDATION");
                System.out.println("=".repeat(60));
                System.out.println("   EMAIL Toggle Enabled: " + (emailEnabled ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY)"));
                System.out.println("   Bill Reviewer Email Not Blank: " + (emailNotBlank ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY)"));
                System.out.println("   Bill Reviewer Email Value: " + (billReviewerEmail.isEmpty() ? "BLANK" : billReviewerEmail));
                System.out.println("   Invoice EMAIL Ready: " + (isReady ? "YES ✓" : "NO ✗"));
                System.out.println("   Business Rule: EMAIL Toggle ENABLED + Bill Reviewer Email NOT BLANK (BOTH MANDATORY)");
                System.out.println("=".repeat(60) + "\n");

                return isReady;

            } catch (Exception e) {
                System.err.println("❌ Error validating EMAIL Ready status: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_016: FAX FILTER & VALIDATION METHODS ==========

        /**
         * SMOKE_DB_016: Click FAX Filter Radio Button
         */
        public void clickFaxFilter() {
            try {
                WebElement faxFilter = driver.findElement(faxFilterRadioButton);
                WaitUtils.sleep(1000);
                faxFilter.click();
                System.out.println("✓ FAX Filter clicked");
                WaitUtils.sleep(3000); // Wait for invoices to load
            } catch (Exception e) {
                System.err.println("❌ Failed to click FAX Filter: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_016: Check if FAX Toggle Button is enabled
         * Returns true if the toggle is in "on" or "enabled" state
         */
        public boolean isFaxToggleEnabled() {
            try {
                // Use the specific FAX toggle locator (div[11]/div[2])
                By toggleLocator = faxToggleButton;

                // Wait for toggle to be visible
                WaitUtils.waitForVisibility(driver, toggleLocator, 10);
                WaitUtils.sleep(1000); // Give toggle time to settle
                WebElement toggle = driver.findElement(toggleLocator);

                // Debug output - print all relevant attributes
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");
                String ngReflectModel = toggle.getAttribute("ng-reflect-model");

                System.out.println("\n=== FAX Toggle Debug Info ===");
                System.out.println("aria-checked: " + ariaChecked);
                System.out.println("class: " + className);
                System.out.println("ng-reflect-model: " + ngReflectModel);
                System.out.println("=============================\n");

                // Check multiple indicators (PrimeNG toggle detection)
                boolean enabled = false;

                // Strategy 1: aria-checked attribute
                if ("true".equalsIgnoreCase(ariaChecked)) {
                    enabled = true;
                    System.out.println("✓ Detected via aria-checked='true'");
                }

                // Strategy 2: class contains checked
                if (className != null && className.contains("p-inputswitch-checked")) {
                    enabled = true;
                    System.out.println("✓ Detected via class contains 'p-inputswitch-checked'");
                }

                // Strategy 3: ng-reflect-model (Angular binding)
                if ("true".equalsIgnoreCase(ngReflectModel)) {
                    enabled = true;
                    System.out.println("✓ Detected via ng-reflect-model='true'");
                }

                // Strategy 4: Check the hidden input inside the toggle
                try {
                    WebElement hiddenInput = toggle.findElement(By.xpath(".//input[@type='checkbox']"));
                    if (hiddenInput != null) {
                        boolean checked = hiddenInput.isSelected();
                        System.out.println("Hidden checkbox isSelected: " + checked);
                        if (checked) {
                            enabled = true;
                            System.out.println("✓ Detected via hidden checkbox isSelected()");
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("No hidden checkbox found");
                }

                System.out.println("\nFinal FAX Toggle State: " + (enabled ? "ENABLED ✓" : "DISABLED ✗"));
                return enabled;

            } catch (Exception e) {
                System.err.println("❌ Error detecting FAX toggle: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_016: Get Bill Reviewer Email value for FAX filter
         * Returns the email address from the Bill Reviewer Email field
         * Note: This field is MANDATORY for FAX Ready invoices
         */
        public String getBillReviewerFax() {
            try {
                WebElement emailInput = driver.findElement(billReviewerFaxNumberInput);
                WaitUtils.sleep(1000);

                // Try to get value from input field or text content
                String email = emailInput.getAttribute("value");
                if (email == null || email.isEmpty()) {
                    email = emailInput.getText().trim();
                }

                System.out.println("Bill Reviewer Email: " + (email == null || email.isEmpty() ? "BLANK ✗ (MANDATORY)" : email + " ✓"));
                return email == null ? "" : email;

            } catch (Exception e) {
                System.err.println("❌ Failed to get Bill Reviewer Email: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_016: Get Bill Reviewer FAX Number value
         * Returns the FAX number from the Bill Reviewer FAX Number field
         * Note: This field is MANDATORY for FAX Ready invoices
         */
        public String getBillReviewerFaxNumber() {
            try {
                WebElement faxInput = driver.findElement(billReviewerFaxNumberInput);
                WaitUtils.sleep(1000);

                // Try to get value from input field or text content
                String faxNumber = faxInput.getAttribute("value");
                if (faxNumber == null || faxNumber.isEmpty()) {
                    faxNumber = faxInput.getText().trim();
                }

                System.out.println("Bill Reviewer FAX Number: " + (faxNumber == null || faxNumber.isEmpty() ? "BLANK ✗ (MANDATORY)" : faxNumber + " ✓"));
                return faxNumber == null ? "" : faxNumber;

            } catch (Exception e) {
                System.err.println("❌ Failed to get Bill Reviewer FAX Number: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_016: Validate FAX Ready invoice
         * Returns true if BOTH conditions are met:
         * 1. FAX Toggle is enabled (MANDATORY)
         * 2. Bill Reviewer FAX Number is not blank (MANDATORY)
         * Business Rule: FAX Toggle enabled AND FAX Number required for FAX submission
         * Note: Bill Reviewer Email is OPTIONAL
         */
        public boolean isFaxReadyInvoice() {
            try {
                boolean faxEnabled = isFaxToggleEnabled();
                String billReviewerEmail = getBillReviewerFax();

                String billReviewerFax = getBillReviewerFaxNumber();
                boolean faxNotBlank = !billReviewerFax.isEmpty();

                // Business Rule: FAX Toggle enabled AND FAX Number not blank (Email is optional)
                boolean isReady = faxEnabled && faxNotBlank;

                System.out.println("\n" + "=".repeat(60));
                System.out.println("📊 FAX READY VALIDATION");
                System.out.println("=".repeat(60));
                System.out.println("   FAX Toggle Enabled: " + (faxEnabled ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY)"));
                System.out.println("   Bill Reviewer FAX Not Blank: " + (faxNotBlank ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY)"));
                System.out.println("   Bill Reviewer FAX Value: " + (billReviewerFax.isEmpty() ? "BLANK" : billReviewerFax));


                System.out.println("   Invoice FAX Ready: " + (isReady ? "YES ✓" : "NO ✗"));
                System.out.println("   Business Rule: FAX Toggle ENABLED + FAX Number NOT BLANK (BOTH MANDATORY)");

                System.out.println("=".repeat(60) + "\n");

                return isReady;

            } catch (Exception e) {
                System.err.println("❌ Error validating FAX Ready status: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_017: PAPER FILTER & VALIDATION METHODS ==========

        /**
         * SMOKE_DB_017: Click Paper Filter Radio Button
         */
        public void clickPaperFilter() {
            try {
                WebElement paperFilter = driver.findElement(paperFilterRadioButton);
                WaitUtils.sleep(1000);
                paperFilter.click();
                System.out.println("✓ Paper Filter clicked");
                WaitUtils.sleep(3000); // Wait for invoices to load
            } catch (Exception e) {
                System.err.println("❌ Failed to click Paper Filter: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_017: Check if EMC Toggle Button is DISABLED for Paper invoices
         * Returns true if the toggle is DISABLED (off state)
         */
        public boolean isEmcToggleDisabledForPaper() {
            try {
                // Use the Paper-specific EMC toggle locator
                By toggleLocator = emcToggleButtonPaper;

                // Wait for toggle to be visible
                WaitUtils.waitForVisibility(driver, toggleLocator, 10);
                WaitUtils.sleep(1000); // Give toggle time to settle
                WebElement toggle = driver.findElement(toggleLocator);

                // Debug output - print all relevant attributes
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");
                String ngReflectModel = toggle.getAttribute("ng-reflect-model");

                System.out.println("\n=== EMC Toggle Debug Info (Paper Filter) ===");
                System.out.println("aria-checked: " + ariaChecked);
                System.out.println("class: " + className);
                System.out.println("ng-reflect-model: " + ngReflectModel);
                System.out.println("=============================\n");

                // Check if toggle is DISABLED (opposite logic of enabled checks)
                boolean enabled = false;

                // Strategy 1: aria-checked attribute
                if ("true".equalsIgnoreCase(ariaChecked)) {
                    enabled = true;
                }

                // Strategy 2: class contains checked
                if (className != null && className.contains("p-inputswitch-checked")) {
                    enabled = true;
                }

                // Strategy 3: ng-reflect-model (Angular binding)
                if ("true".equalsIgnoreCase(ngReflectModel)) {
                    enabled = true;
                }

                // Strategy 4: Check the hidden input inside the toggle
                try {
                    WebElement hiddenInput = toggle.findElement(By.xpath(".//input[@type='checkbox']"));
                    if (hiddenInput != null && hiddenInput.isSelected()) {
                        enabled = true;
                    }
                } catch (Exception ex) {
                    // No hidden checkbox found
                }

                boolean disabled = !enabled;
                System.out.println("EMC Toggle State: " + (disabled ? "DISABLED ✓ (Required for Paper)" : "ENABLED ✗ (Should be disabled)"));
                return disabled;

            } catch (Exception e) {
                System.err.println("❌ Error detecting EMC toggle: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_017: Check if EMAIL Toggle Button is DISABLED for Paper invoices
         * Returns true if the toggle is DISABLED (off state)
         */
        public boolean isEmailToggleDisabledForPaper() {
            try {
                // Use the Paper-specific EMAIL toggle locator
                By toggleLocator = emailToggleButtonPaper;

                // Wait for toggle to be visible
                WaitUtils.waitForVisibility(driver, toggleLocator, 10);
                WaitUtils.sleep(1000); // Give toggle time to settle
                WebElement toggle = driver.findElement(toggleLocator);

                // Debug output - print all relevant attributes
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");
                String ngReflectModel = toggle.getAttribute("ng-reflect-model");

                System.out.println("\n=== EMAIL Toggle Debug Info (Paper Filter) ===");
                System.out.println("aria-checked: " + ariaChecked);
                System.out.println("class: " + className);
                System.out.println("ng-reflect-model: " + ngReflectModel);
                System.out.println("=============================\n");

                // Check if toggle is DISABLED (opposite logic of enabled checks)
                boolean enabled = false;

                // Strategy 1: aria-checked attribute
                if ("true".equalsIgnoreCase(ariaChecked)) {
                    enabled = true;
                }

                // Strategy 2: class contains checked
                if (className != null && className.contains("p-inputswitch-checked")) {
                    enabled = true;
                }

                // Strategy 3: ng-reflect-model (Angular binding)
                if ("true".equalsIgnoreCase(ngReflectModel)) {
                    enabled = true;
                }

                // Strategy 4: Check the hidden input inside the toggle
                try {
                    WebElement hiddenInput = toggle.findElement(By.xpath(".//input[@type='checkbox']"));
                    if (hiddenInput != null && hiddenInput.isSelected()) {
                        enabled = true;
                    }
                } catch (Exception ex) {
                    // No hidden checkbox found
                }

                boolean disabled = !enabled;
                System.out.println("EMAIL Toggle State: " + (disabled ? "DISABLED ✓ (Required for Paper)" : "ENABLED ✗ (Should be disabled)"));
                return disabled;

            } catch (Exception e) {
                System.err.println("❌ Error detecting EMAIL toggle: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_017: Check if FAX Toggle Button is DISABLED for Paper invoices
         * Returns true if the toggle is DISABLED (off state)
         */
        public boolean isFaxToggleDisabledForPaper() {
            try {
                // Use the Paper-specific FAX toggle locator
                By toggleLocator = faxToggleButtonPaper;

                // Wait for toggle to be visible
                WaitUtils.waitForVisibility(driver, toggleLocator, 10);
                WaitUtils.sleep(1000); // Give toggle time to settle
                WebElement toggle = driver.findElement(toggleLocator);

                // Debug output - print all relevant attributes
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");
                String ngReflectModel = toggle.getAttribute("ng-reflect-model");

                System.out.println("\n=== FAX Toggle Debug Info (Paper Filter) ===");
                System.out.println("aria-checked: " + ariaChecked);
                System.out.println("class: " + className);
                System.out.println("ng-reflect-model: " + ngReflectModel);
                System.out.println("=============================\n");

                // Check if toggle is DISABLED (opposite logic of enabled checks)
                boolean enabled = false;

                // Strategy 1: aria-checked attribute
                if ("true".equalsIgnoreCase(ariaChecked)) {
                    enabled = true;
                }

                // Strategy 2: class contains checked
                if (className != null && className.contains("p-inputswitch-checked")) {
                    enabled = true;
                }

                // Strategy 3: ng-reflect-model (Angular binding)
                if ("true".equalsIgnoreCase(ngReflectModel)) {
                    enabled = true;
                }

                // Strategy 4: Check the hidden input inside the toggle
                try {
                    WebElement hiddenInput = toggle.findElement(By.xpath(".//input[@type='checkbox']"));
                    if (hiddenInput != null && hiddenInput.isSelected()) {
                        enabled = true;
                    }
                } catch (Exception ex) {
                    // No hidden checkbox found
                }

                boolean disabled = !enabled;
                System.out.println("FAX Toggle State: " + (disabled ? "DISABLED ✓ (Required for Paper)" : "ENABLED ✗ (Should be disabled)"));
                return disabled;

            } catch (Exception e) {
                System.err.println("❌ Error detecting FAX toggle: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        /**
         * SMOKE_DB_017: Validate Paper Ready invoice
         * Returns true if ALL THREE conditions are met:
         * 1. EMC Toggle is DISABLED (MANDATORY)
         * 2. EMAIL Toggle is DISABLED (MANDATORY)
         * 3. FAX Toggle is DISABLED (MANDATORY)
         * Business Rule: All electronic submission toggles must be disabled for Paper invoices
         */
        public boolean isPaperReadyInvoice() {
            try {
                boolean emcDisabled = isEmcToggleDisabledForPaper();
                boolean emailDisabled = isEmailToggleDisabledForPaper();
                boolean faxDisabled = isFaxToggleDisabledForPaper();

                // Business Rule: ALL THREE toggles must be DISABLED for Paper submission
                boolean isReady = emcDisabled && emailDisabled && faxDisabled;

                System.out.println("\n" + "=".repeat(60));
                System.out.println("📊 PAPER READY VALIDATION");
                System.out.println("=".repeat(60));
                System.out.println("   EMC Toggle DISABLED: " + (emcDisabled ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY - Should be disabled)"));
                System.out.println("   EMAIL Toggle DISABLED: " + (emailDisabled ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY - Should be disabled)"));
                System.out.println("   FAX Toggle DISABLED: " + (faxDisabled ? "YES ✓ (MANDATORY)" : "NO ✗ (MANDATORY - Should be disabled)"));
                System.out.println("   Invoice Paper Ready: " + (isReady ? "YES ✓" : "NO ✗"));
                System.out.println("   Business Rule: EMC + EMAIL + FAX Toggles ALL DISABLED (ALL MANDATORY)");
                System.out.println("=".repeat(60) + "\n");

                return isReady;

            } catch (Exception e) {
                System.err.println("❌ Error validating Paper Ready status: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_016: EMC SUBMISSION METHODS ==========

        /**
         * SMOKE_DB_018: Select checkbox for first EAMS Verified or EAMS Not Verified invoice
         * Returns the invoice number that was selected
         */
        public String selectFirstEamsVerifiedInvoiceCheckbox() {
            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody (same pattern as clickFirstEamsVerifiedInvoiceNumber)
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for EAMS Verified/Not Verified invoice in " + rows.size() + " rows");

                // Iterate through each row
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            System.out.println("   Row " + (i + 1) + " - Status: '" + statusText + "'");

                            // Check if status is "EAMS Verified" or "EAMS Not Verified"
                            if (statusText.equals("EAMS Verified") || statusText.equals("Multiple Carrier") || statusText.equals("No Carrier In EAMS")) {
                                System.out.println("✓ Found invoice with status: " + statusText + " at row " + (i + 1));

                                // Get invoice number from column 2 (td[2])
                                String invoiceNumber = "";
                                try {
                                    WebElement invoiceElement = row.findElement(By.xpath(".//td[2]//span"));
                                    invoiceNumber = invoiceElement.getText().trim();
                                    System.out.println("✓ Invoice Number: " + invoiceNumber);
                                } catch (Exception e) {
                                    System.err.println("⚠ Could not get invoice number");
                                }

                                // Click checkbox in column 1 (td[1])
                                try {
                                    WebElement checkbox = row.findElement(By.xpath(".//td[1]//div"));
                                    WaitUtils.sleep(1000);
                                    checkbox.click();
                                    System.out.println("✓ Checkbox clicked for invoice: " + invoiceNumber + " (Status: " + statusText + ")");

                                    return invoiceNumber;

                                } catch (Exception checkboxError) {
                                    System.err.println("❌ Could not find or click checkbox: " + checkboxError.getMessage());
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row - skip
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                    }
                }

                System.err.println("❌ No 'EAMS Verified' or 'EAMS Not Verified' invoice found in the table");
                return null;

            } catch (Exception e) {
                System.err.println("❌ Failed to select EAMS Verified invoice checkbox: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /**
         * SMOKE_DB_016: Enable HCFA Toggle Button
         */
        public void enableHcfaToggle() {
            try {
                WebElement toggle = driver.findElement(hcfaToggleButton);
                WaitUtils.sleep(1000);

                // Check if toggle is already enabled
                String ariaChecked = toggle.getAttribute("aria-checked");
                String className = toggle.getAttribute("class");

                boolean isEnabled = "true".equalsIgnoreCase(ariaChecked) ||
                        (className != null && className.contains("p-inputswitch-checked"));

                if (!isEnabled) {
                    toggle.click();
                    System.out.println("✓ HCFA Toggle enabled");
                    WaitUtils.sleep(1000);
                } else {
                    System.out.println("✓ HCFA Toggle already enabled");
                }

            } catch (Exception e) {
                System.err.println("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_018: Click EMC Submission Button
         */
        public void clickEmcSubmissionButton() {
            try {
                WebElement button = driver.findElement(emcSubmissionButton);
                WaitUtils.sleep(1000);
                button.click();
                System.out.println("✓ EMC Submission button clicked");
                WaitUtils.sleep(3000); // Wait for submission to process
            } catch (Exception e) {
                System.err.println("❌ Failed to click EMC Submission button: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_018: Get success message text
         * Returns the success message displayed in the toast notification
         */
        public String getSuccessMessage() {
            try {
                WaitUtils.waitForVisibility(driver, successMessageToast, 10);
                WebElement message = driver.findElement(successMessageToast);
                String messageText = message.getText().trim();
                System.out.println("Success Message: " + messageText);
                return messageText;
            } catch (Exception e) {
                System.err.println("❌ Failed to get success message: " + e.getMessage());
                return "";
            }
        }

        /**
         * SMOKE_DB_018: Verify invoice disappeared from the list
         * Returns true if the invoice is no longer in the Daily Billing list
         */
        public boolean isInvoiceDisappeared(String invoiceNumber) {
            try {
                WaitUtils.sleep(3000); // Wait for page to refresh after submission

                // Try to find the invoice in the table
                List<WebElement> rows = driver.findElements(
                        By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr")
                );

                System.out.println("Checking " + rows.size() + " rows for invoice: " + invoiceNumber);

                for (int i = 0; i < rows.size(); i++) {
                    int rowNum = i + 1;

                    try {
                        WebElement invoiceElement = driver.findElement(
                                By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[" + rowNum + "]/td[2]/span")
                        );
                        String currentInvoice = invoiceElement.getText().trim();

                        if (currentInvoice.equals(invoiceNumber)) {
                            System.out.println("❌ Invoice " + invoiceNumber + " still found in the list");
                            return false;
                        }

                    } catch (Exception e) {
                        // Skip this row if elements not found
                        continue;
                    }
                }

                System.out.println("✓ Invoice " + invoiceNumber + " successfully disappeared from the list");
                return true;

            } catch (Exception e) {
                System.err.println("❌ Error checking if invoice disappeared: " + e.getMessage());
                return false;
            }
        }

        // ========== SMOKE_DB_019: E/P SUBMISSION METHODS ==========

        /**
         * SMOKE_DB_019: Click E/P Submission Button (EMC and Paper combined)
         */
        public void clickEpSubmissionButton() {
            try {
                WebElement button = driver.findElement(epSubmissionButton);
                WaitUtils.sleep(1000);
                button.click();
                System.out.println("✓ E/P Submission button clicked");
                WaitUtils.sleep(3000); // Wait for submission to process
            } catch (Exception e) {
                System.err.println("❌ Failed to click E/P Submission button: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_019: Verify Report View is displayed after Paper submission
         * Returns true if Report View label is visible
         */
        public boolean isReportViewDisplayedAfterEP() {
            try {
                WaitUtils.sleep(3000); // Wait for Report View to open
                WaitUtils.waitForVisibility(driver, reportViewLabel, 15);
                WebElement label = driver.findElement(reportViewLabel);
                String labelText = label.getText().trim();

                System.out.println("✓ Report View displayed with label: " + labelText);

                if (labelText.equals("Report View")) {
                    return true;
                } else {
                    System.err.println("⚠ Report View label mismatch. Expected: 'Report View', Found: '" + labelText + "'");
                    return false;
                }

            } catch (Exception e) {
                System.err.println("❌ Report View not displayed: " + e.getMessage());
                return false;
            }
        }

        /**
         * SMOKE_DB_019: Get Report View label text
         */
        public String getReportViewLabelText() {
            try {
                WaitUtils.waitForVisibility(driver, reportViewLabel, 15);
                WebElement label = driver.findElement(reportViewLabel);
                String labelText = label.getText().trim();
                System.out.println("Report View Label: " + labelText);
                return labelText;
            } catch (Exception e) {
                System.err.println("❌ Failed to get Report View label: " + e.getMessage());
                return "";
            }
        }

        // ========== SMOKE_DB_020: MAIL SUBMISSION METHODS ==========

        /**
         * SMOKE_DB_020: Click Mail Filter Radio Button
         */
        public void clickMailFilter() {
            try {
                WebElement mailFilter = driver.findElement(mailFilterRadioButton);
                WaitUtils.sleep(1000);
                mailFilter.click();
                System.out.println("✓ Mail Filter clicked");
                WaitUtils.sleep(3000); // Wait for invoices to load
            } catch (Exception e) {
                System.err.println("❌ Failed to click Mail Filter: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_020: Click Mail Submission Button
         */
        public void clickMailSubmissionButton() {
            try {
                WebElement button = driver.findElement(mailSubmissionButton);
                WaitUtils.sleep(1000);
                button.click();
                System.out.println("✓ Mail Submission button clicked");
                WaitUtils.sleep(3000); // Wait for submission to process
            } catch (Exception e) {
                System.err.println("❌ Failed to click Mail Submission button: " + e.getMessage());
                throw e;
            }
        }

        // ========== SMOKE_DB_021: FAX SUBMISSION METHODS ==========

        /**
         * SMOKE_DB_021: Click FAX Submission Button
         */
        public void clickFaxSubmissionButton() {
            try {
                WebElement button = driver.findElement(faxSubmissionButton);
                WaitUtils.sleep(1000);
                button.click();
                System.out.println("✓ FAX Submission button clicked");
                WaitUtils.sleep(3000); // Wait for submission to process
            } catch (Exception e) {
                System.err.println("❌ Failed to click FAX Submission button: " + e.getMessage());
                throw e;
            }
        }

        /**
         * SMOKE_DB_021: Get FAX success message text
         * Returns the FAX-specific success message displayed in the toast notification
         */
        public String getFaxSuccessMessage() {
            try {
                WaitUtils.waitForVisibility(driver, faxSuccessMessageToast, 10);
                WebElement message = driver.findElement(faxSuccessMessageToast);
                String messageText = message.getText().trim();
                System.out.println("FAX Success Message: " + messageText);
                return messageText;
            } catch (Exception e) {
                System.err.println("❌ Failed to get FAX success message: " + e.getMessage());
                return "";
            }
        }

        // ========== SMOKE_DB_022: PAPER SUBMISSION METHODS ==========

        /**
         * SMOKE_DB_022: Click Paper Submission Button
         */
        public void clickPaperSubmissionButton() {
            try {
                WebElement button = driver.findElement(paperSubmissionButton);
                WaitUtils.sleep(1000);
                button.click();
                System.out.println("✓ Paper Submission button clicked");
                WaitUtils.sleep(3000); // Wait for submission to process
            } catch (Exception e) {
                System.err.println("❌ Failed to click Paper Submission button: " + e.getMessage());
                throw e;
            }
        }

        // ========== SMOKE_DB_023: MULTIPLE EMC SUBMISSION METHODS ==========

        /**
         * SMOKE_DB_023: Select multiple EAMS Verified or EAMS Not Verified invoice checkboxes
         * Returns a list of invoice numbers that were selected
         * @param maxCount Maximum number of invoices to select (minimum 2)
         */
        public List<String> selectMultipleEamsVerifiedInvoiceCheckboxes(int maxCount) {
            List<String> selectedInvoices = new ArrayList<>();

            try {
                WaitUtils.sleep(3000); // Wait for table to load

                // Find all table rows in tbody
                By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
                List<WebElement> rows = driver.findElements(allTableRows);

                System.out.println("🔍 Searching for multiple EAMS Verified/Not Verified invoices in " + rows.size() + " rows");
                System.out.println("Target: Select minimum 2, maximum " + maxCount + " invoices");

                int selectedCount = 0;

                // Iterate through each row
                for (int i = 0; i < rows.size() && selectedCount < maxCount; i++) {
                    try {
                        WebElement row = rows.get(i);

                        // Try to find status badge in column 5 (td[5])
                        try {
                            WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                            String statusText = statusBadge.getText().trim();

                            // Check if status is "EAMS Verified" or "EAMS Not Verified"
                            if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {
                                System.out.println("   Row " + (i + 1) + " - Status: '" + statusText + "'");

                                // Get invoice number from column 2 (td[2])
                                String invoiceNumber = "";
                                try {
                                    WebElement invoiceElement = row.findElement(By.xpath(".//td[2]//span"));
                                    invoiceNumber = invoiceElement.getText().trim();
                                    System.out.println("   ✓ Invoice Number: " + invoiceNumber);
                                } catch (Exception e) {
                                    System.err.println("   ⚠ Could not get invoice number");
                                    continue;
                                }

                                // Click checkbox in column 1 (td[1])
                                try {
                                    WebElement checkbox = row.findElement(By.xpath(".//td[1]//div"));
                                    WaitUtils.sleep(500);
                                    checkbox.click();
                                    selectedCount++;
                                    selectedInvoices.add(invoiceNumber);
                                    System.out.println("   ✓ Checkbox " + selectedCount + " selected: " + invoiceNumber + " (Status: " + statusText + ")");
                                    WaitUtils.sleep(500); // Small delay between selections

                                } catch (Exception checkboxError) {
                                    System.err.println("   ❌ Could not find or click checkbox: " + checkboxError.getMessage());
                                }
                            }

                        } catch (Exception statusError) {
                            // No status badge in this row, might be a grouping row - skip
                        }

                    } catch (Exception e) {
                        // Skip this row and continue
                    }
                }

                if (selectedInvoices.size() >= 2) {
                    System.out.println("\n✓ Successfully selected " + selectedInvoices.size() + " invoices:");
                    for (int i = 0; i < selectedInvoices.size(); i++) {
                        System.out.println("   " + (i + 1) + ". " + selectedInvoices.get(i));
                    }
                } else {
                    System.err.println("❌ Only selected " + selectedInvoices.size() + " invoice(s). Minimum required: 2");
                }

                return selectedInvoices;

            } catch (Exception e) {
                System.err.println("❌ Failed to select multiple EAMS Verified invoice checkboxes: " + e.getMessage());
                e.printStackTrace();
                return selectedInvoices;
            }
        }

        /**
         * SMOKE_DB_023: Verify multiple invoices disappeared from the list
         * Returns true if ALL invoices are no longer in the Daily Billing list
         */
        public boolean areMultipleInvoicesDisappeared(List<String> invoiceNumbers) {
            try {
                WaitUtils.sleep(3000); // Wait for page to refresh after submission

                System.out.println("\n🔍 Verifying " + invoiceNumbers.size() + " invoices have disappeared...");

                // Try to find the invoices in the table
                List<WebElement> rows = driver.findElements(
                        By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]")
                );

                System.out.println("Checking " + rows.size() + " rows in the table");

                List<String> stillPresentInvoices = new ArrayList<>();

                // Check each invoice
                for (String invoiceNumber : invoiceNumbers) {
                    boolean found = false;

                    for (int i = 0; i < rows.size(); i++) {
                        try {
                            WebElement row = rows.get(i);
                            WebElement invoiceElement = row.findElement(By.xpath(".//td[2]//span"));
                            String currentInvoice = invoiceElement.getText().trim();

                            if (currentInvoice.equals(invoiceNumber)) {
                                found = true;
                                stillPresentInvoices.add(invoiceNumber);
                                System.out.println("   ❌ Invoice " + invoiceNumber + " still found in the list");
                                break;
                            }

                        } catch (Exception e) {
                            // Skip this row if elements not found
                            continue;
                        }
                    }

                    if (!found) {
                        System.out.println("   ✓ Invoice " + invoiceNumber + " successfully disappeared");
                    }
                }

                if (stillPresentInvoices.isEmpty()) {
                    System.out.println("\n✅ All " + invoiceNumbers.size() + " invoices successfully disappeared from the list");
                    return true;
                } else {
                    System.out.println("\n❌ " + stillPresentInvoices.size() + " invoice(s) still present in the list:");
                    for (String invoice : stillPresentInvoices) {
                        System.out.println("   - " + invoice);
                    }
                    return false;
                }

            } catch (Exception e) {
                System.err.println("❌ Error checking if multiple invoices disappeared: " + e.getMessage());
                return false;
            }
        }


}
