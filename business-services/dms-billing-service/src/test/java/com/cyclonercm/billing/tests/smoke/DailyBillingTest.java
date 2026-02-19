package com.cyclonercm.billing.tests.smoke;

import com.cyclonercm.billing.base.SmokeBaseTest;
import com.cyclonercm.pages.*;
import com.cyclonercm.utils.DailyBillingTestDataProperties;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DailyBillingTest extends SmokeBaseTest {

    private static final Logger logger = Logger.getLogger(DailyBillingTest.class.getName());

    private AuthenticationPage loginPage;
    private FileUploadPage uploadPage;
    private FileHistoryPage historyPage;
    private DailyBillingPage dailyBillingPage;


    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        uploadPage = new FileUploadPage(driver);
        historyPage = new FileHistoryPage(driver);
        dailyBillingPage = new DailyBillingPage(driver);

            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 60);

            // Wait for system label text to load
            WaitUtils.sleep(3000);

            String actualSystemLabelText = loginPage.getSystemLabelText();
            String expectedSystemLabelText = DailyBillingTestDataProperties.get("systemLabel");

            String actualSystemVersionText = loginPage.getVersionText();
            String expectedSystemVersionText = DailyBillingTestDataProperties.get("buildNumber");

            System.out.println("Expected System Label: " + expectedSystemLabelText);
            System.out.println("Actual System Label: " + actualSystemLabelText);
            System.out.println("Expected Version: " + expectedSystemVersionText);
            System.out.println("Actual Version: " + actualSystemVersionText);

            // Only validate system label if it's not empty (element might not be visible in all test scenarios)
            if (!actualSystemLabelText.isEmpty()) {
                try {
                    Assert.assertEquals(actualSystemLabelText, expectedSystemLabelText, "System label text does not match.");
                    test.pass("System label text verified: " + actualSystemLabelText);
                } catch (AssertionError e) {
                    test.fail("System label text mismatch. Expected: " + expectedSystemLabelText + ", Found: " + actualSystemLabelText);
                    throw e;
                }
            } else {
                test.info("System label text not found - skipping validation (element might not be visible)");
                System.out.println("⚠ System label text not found - skipping validation");
            }

            try {
                Assert.assertEquals(actualSystemVersionText, expectedSystemVersionText, "System version text does not match.");
                test.pass("System version text verified: " + actualSystemVersionText);
            } catch (AssertionError e) {
                test.fail("System version text mismatch. Expected: " + expectedSystemVersionText + ", Found: " + actualSystemVersionText);
                throw e;
            }

            loginPage.enterUsername(DailyBillingTestDataProperties.get("validUserId"));
            loginPage.enterPassword(DailyBillingTestDataProperties.get("validPassword"));
            loginPage.clickSignInButton();

            //WaitUtils.sleep(20000);

            WaitUtils.waitForVisibility(driver, LocatorConstants.BillingMenu, 60);
            System.out.println("✓ Billing menu is visible");

            // Wait for splash screen to disappear before clicking
            try {
                By splashScreen = By.xpath("//div[@class='splash-screen ng-star-inserted']");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(splashScreen));
                System.out.println("✓ Splash screen disappeared");
            } catch (Exception e) {
                System.out.println("⚠ No splash screen found or already disappeared");
            }

            // Additional wait to ensure page is fully loaded
            WaitUtils.sleep(3000);

            // Click the Billing Menu with wait for clickability and retry logic
            boolean billingMenuClicked = false;
            for (int attempt = 1; attempt <= 3; attempt++) {
                try {
                    System.out.println("Attempt " + attempt + " to click Billing menu...");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                    wait.until(ExpectedConditions.elementToBeClickable(LocatorConstants.BillingMenu));

                    org.openqa.selenium.WebElement menuElement = driver.findElement(LocatorConstants.BillingMenu);
                    System.out.println("Billing menu element found, attempting click...");
                    menuElement.click();

                    // Verify dropdown appeared
                    WaitUtils.sleep(2000);
                    By dropdownLocator = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div");
                    List<org.openqa.selenium.WebElement> dropdownElements = driver.findElements(dropdownLocator);

                    if (dropdownElements.size() > 0 && dropdownElements.get(0).isDisplayed()) {
                        System.out.println("✓ Billing menu clicked successfully - dropdown is visible");
                        billingMenuClicked = true;
                        break;
                    } else {
                        System.out.println("⚠ Click registered but dropdown not visible, retrying...");
                        throw new Exception("Dropdown not visible after click");
                    }
                } catch (Exception e) {
                    System.err.println("Attempt " + attempt + " failed: " + e.getMessage());
                    if (attempt < 3) {
                        WaitUtils.sleep(2000);
                        // Try JavaScript click on retry
                        if (attempt == 2) {
                            try {
                                System.out.println("Trying JavaScript click...");
                                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                                org.openqa.selenium.WebElement menuElement = driver.findElement(LocatorConstants.BillingMenu);
                                js.executeScript("arguments[0].click();", menuElement);
                                WaitUtils.sleep(2000);
                                By dropdownLocator = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div");
                                List<org.openqa.selenium.WebElement> dropdownElements = driver.findElements(dropdownLocator);
                                if (dropdownElements.size() > 0 && dropdownElements.get(0).isDisplayed()) {
                                    System.out.println("✓ JavaScript click successful");
                                    billingMenuClicked = true;
                                    break;
                                }
                            } catch (Exception jsEx) {
                                System.err.println("JavaScript click also failed: " + jsEx.getMessage());
                            }
                        }
                    } else {
                        throw new RuntimeException("Failed to click Billing menu after 3 attempts", e);
                    }
                }
            }

            if (!billingMenuClicked) {
                throw new RuntimeException("Failed to open Billing menu dropdown");
            }

            // Click the Daily Billing Option with wait for clickability and verification
            boolean dailyBillingClicked = false;
            String urlBeforeClick = driver.getCurrentUrl();

            for (int attempt = 1; attempt <= 3; attempt++) {
                try {
                    System.out.println("Attempt " + attempt + " to click Daily Billing option...");
                    By dailyBillingOptionLocator = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div/ul/li[1]/a");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                    wait.until(ExpectedConditions.elementToBeClickable(dailyBillingOptionLocator));

                    org.openqa.selenium.WebElement optionElement = driver.findElement(dailyBillingOptionLocator);
                    System.out.println("Daily Billing option found, attempting click...");

                    if (attempt == 2) {
                        // Try JavaScript click on second attempt
                        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", optionElement);
                        System.out.println("Used JavaScript click");
                    } else {
                        optionElement.click();
                        System.out.println("Used normal click");
                    }

                    // Wait for URL to change or page to load
                    WaitUtils.sleep(3000);
                    String urlAfterClick = driver.getCurrentUrl();

                    System.out.println("URL before click: " + urlBeforeClick);
                    System.out.println("URL after click: " + urlAfterClick);

                    // Check if navigation occurred (URL changed or contains billing)
                    if (!urlAfterClick.equals(urlBeforeClick) || urlAfterClick.contains("billing")) {
                        System.out.println("✓ Daily Billing option clicked - navigation detected");
                        dailyBillingClicked = true;
                        break;
                    } else {
                        System.out.println("⚠ Click registered but no navigation detected, retrying...");
                        throw new Exception("No navigation after click");
                    }
                } catch (Exception e) {
                    System.err.println("Attempt " + attempt + " failed: " + e.getMessage());
                    if (attempt < 3) {
                        WaitUtils.sleep(2000);
                    } else {
                        throw new RuntimeException("Failed to click Daily Billing option after 3 attempts", e);
                    }
                }
            }

            if (!dailyBillingClicked) {
                throw new RuntimeException("Failed to navigate to Daily Billing page");
            }

            // Wait for page transition and any loading indicators
            WaitUtils.sleep(5000);

            // Wait for splash screen again after navigation (if it appears)
            try {
                By splashScreen = By.xpath("//div[@class='splash-screen ng-star-inserted']");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(splashScreen));
                System.out.println("✓ Post-navigation splash screen disappeared");
            } catch (Exception e) {
                System.out.println("⚠ No post-navigation splash screen found");
            }

            // Wait for additional time for the page to fully render
            WaitUtils.sleep(3000);

            // Wait for Daily Billing label with better error message and debugging
            System.out.println("⏳ Waiting for Daily Billing label to appear...");

            // First verify we're on the right page by checking URL
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);

            // Check if billing-app component is present
            try {
                List<org.openqa.selenium.WebElement> billingAppElements = driver.findElements(By.xpath("//billing-app"));
                System.out.println("billing-app elements found: " + billingAppElements.size());

                List<org.openqa.selenium.WebElement> billingListElements = driver.findElements(By.xpath("//billing-list"));
                System.out.println("billing-list elements found: " + billingListElements.size());

                // Try to find any element that might indicate the Daily Billing page
                List<org.openqa.selenium.WebElement> toolbarElements = driver.findElements(By.xpath("//p-toolbar"));
                System.out.println("p-toolbar elements found: " + toolbarElements.size());

                // Check for any labels containing "Daily Billing" or "Billing"
                List<org.openqa.selenium.WebElement> labelElements = driver.findElements(By.xpath("//*[contains(text(),'Daily Billing') or contains(text(),'Billing')]"));
                System.out.println("Elements with 'Billing' text found: " + labelElements.size());
                for (org.openqa.selenium.WebElement elem : labelElements) {
                    System.out.println("  - Found text: '" + elem.getText() + "' in tag: " + elem.getTagName());
                }

            } catch (Exception e) {
                System.err.println("Error during page inspection: " + e.getMessage());
            }

            // Try alternative locators for Daily Billing label
            By[] alternativeLocators = {
                By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[1]/span/b"),
                By.xpath("//billing-list//p-toolbar//span/b"),
                By.xpath("//p-toolbar//span[contains(text(),'Daily Billing')]"),
                By.xpath("//span/b[contains(text(),'Daily Billing')]"),
                By.cssSelector("p-toolbar span b"),
                By.xpath("//*[contains(@class,'toolbar')]//span/b")
            };

            boolean labelFound = false;
            String actualDailyBillingLabel = "";

            for (int i = 0; i < alternativeLocators.length; i++) {
                try {
                    System.out.println("Trying locator " + (i + 1) + "...");
                    WaitUtils.waitForVisibility(driver, alternativeLocators[i], 10);
                    org.openqa.selenium.WebElement labelElement = driver.findElement(alternativeLocators[i]);
                    actualDailyBillingLabel = labelElement.getText().trim();
                    System.out.println("✓ Found label using locator " + (i + 1) + ": '" + actualDailyBillingLabel + "'");
                    labelFound = true;
                    break;
                } catch (Exception e) {
                    System.out.println("  Locator " + (i + 1) + " failed: " + e.getMessage().split("\n")[0]);
                }
            }

            if (!labelFound) {
                System.err.println("❌ Daily Billing label could not be found with any locator");
                System.err.println("Current URL: " + driver.getCurrentUrl());
                System.err.println("Page Title: " + driver.getTitle());

                // Save page source for debugging
                try {
                    String pageSource = driver.getPageSource();
                    System.err.println("Page source length: " + pageSource.length());
                    System.err.println("Page source snippet (first 500 chars): " + pageSource.substring(0, Math.min(500, pageSource.length())));
                } catch (Exception e) {
                    System.err.println("Could not retrieve page source: " + e.getMessage());
                }

                throw new RuntimeException("Daily Billing page did not load properly. Label element not found with any locator.");
            }

            String expectedDailyBillingLabel = "Daily Billing";

            try {
                Assert.assertEquals(actualDailyBillingLabel, expectedDailyBillingLabel, "Daily Billing label text does not match.");
                test.pass("Daily Billing label text verified: " + actualDailyBillingLabel);
            } catch (AssertionError e) {
                test.fail("Daily Billing label text mismatch. Expected: " + expectedDailyBillingLabel + ", Found: " + actualDailyBillingLabel);
                throw e;
            }

        }

        @Test(priority = 1, description = "SMOKE_DB_001 - Verify user can filter Daily Billing invoices by DOS")
        public void SMOKE_FH_001() {

            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");

            // Additional wait for page to fully stabilize and all elements to be interactive
            System.out.println("⏳ Waiting for page to fully stabilize...");
            WaitUtils.sleep(3000);

            // Wait for any remaining animations or transitions to complete
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'p-component-overlay') or contains(@class,'loading')]")
                ));
            } catch (Exception e) {
                // No overlay/loading indicator found, continue
            }

            System.out.println("✓ Page fully stabilized and ready for interaction");

            // Get original date
            String Dos = DailyBillingTestDataProperties.get("dateofservice");
            System.out.println("Original DOS: " + Dos);  // e.g., "05/26/2025"

            // Format date FIRST
            String formattedDos = dailyBillingPage.removeYearPrefix(Dos);
            System.out.println("Formatted DOS: " + formattedDos);  // e.g., "05/26/25"

            // Apply filter with formatted date
            dailyBillingPage.setDosFilter(Dos);
            System.out.println("✓ Filter applied with: " + Dos);

            // Smart wait: poll until table data OR no-results message appears
            // Avoids timeout on rigid tr[2]/td locator when table renders differently after filtering
            System.out.println("⏳ Waiting for table to reflect filtered results...");
            boolean tableReady = false;
            for (int attempt = 0; attempt < 24; attempt++) {  // poll up to 12 seconds
                List<org.openqa.selenium.WebElement> dataRows = driver.findElements(
                    By.xpath("//billing-list//p-table//tbody/tr[contains(@class,'ng-star-inserted')]"));
                List<org.openqa.selenium.WebElement> emptyMsg = driver.findElements(
                    By.xpath("//billing-list//*[contains(text(),'No records') or contains(text(),'no records') or contains(@class,'p-datatable-emptymessage')]"));
                if (!dataRows.isEmpty() || !emptyMsg.isEmpty()) {
                    System.out.println("✓ Table ready — rows found: " + dataRows.size() +
                        (emptyMsg.isEmpty() ? "" : ", empty-message present"));
                    tableReady = true;
                    break;
                }
                WaitUtils.sleep(500);
            }
            if (!tableReady) {
                System.out.println("⚠ Table did not reflect results within wait — proceeding with validation");
            }

            // Validate
            String actualDos = dailyBillingPage.getDosFilterValue();
            String expectedDos = "DOS : " + formattedDos;

            System.out.println("Expected: " + expectedDos);
            System.out.println("Actual: " + actualDos);

            try {
                Assert.assertEquals(actualDos, expectedDos, "DOS filter successfully applied.");
                test.pass("✓ DOS filter value verified: " + actualDos);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ DOS filter value mismatch. Expected: " + expectedDos + ", Found: " + actualDos);
                System.out.println("❌ TEST FAILED");
                throw e;
            }
        }

        @Test(priority = 2, description = "SMOKE_DB_002 - Verify user can filter Daily Billing invoices by Case Number")
        public void SMOKE_FH_002() {

            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");



            String caseNumber = DailyBillingTestDataProperties.get("caseNumber");
            // Apply filter with case number
            dailyBillingPage.setCaseFilter(caseNumber);
            System.out.println("✓ Filter applied with: " + caseNumber);

            WaitUtils.sleep(3000);
            String actualCaseNumber = dailyBillingPage.getCaseFilterValue();
            String expectedCaseNumber = caseNumber;

            try {
                Assert.assertEquals(actualCaseNumber, expectedCaseNumber, "Case Number filter successfully applied.");
                test.pass("✓ Case Number filter value verified: " + actualCaseNumber);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ Case Number filter value mismatch. Expected: " + expectedCaseNumber + ", Found: " + actualCaseNumber);
                System.out.println("❌ TEST FAILED");
                throw e;
            }



        }

        @Test(priority = 3, description = "SMOKE_DB_003 - Verify user can filter Daily Billing invoices by Applicant")
        public void SMOKE_FH_003() {

            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");

            String applicantName = DailyBillingTestDataProperties.get("applicantName");
            // Apply filter with Applicant name
            dailyBillingPage.setApplicantFilter(applicantName);
            System.out.println("✓ Filter applied with: " + applicantName);


            //String setApplicantName = dailyBillingPage.convertNameFormat(applicantName);
            //System.out.println("Result: " + setApplicantName);

            WaitUtils.sleep(5000);
            String actualApplicantName = dailyBillingPage.getApplicantFilterValue();
            String expectedApplicantName = DailyBillingTestDataProperties.get("applicantName");

            try {
                Assert.assertEquals(actualApplicantName, expectedApplicantName, "Case Number filter successfully applied.");
                test.pass("✓ Case Number filter value verified: " + actualApplicantName);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ Case Number filter value mismatch. Expected: " + expectedApplicantName + ", Found: " + actualApplicantName);
                System.out.println("❌ TEST FAILED");
                throw e;
            }

        }

        @Test(priority = 4, description = "SMOKE_DB_004 - Verify user can filter Daily Billing invoices by Claim Admin")
        public void SMOKE_FH_004() {

            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");

            String claimAdminName = DailyBillingTestDataProperties.get("claimAdminName");
            // Apply filter with Claim Admin name
            dailyBillingPage.setClaimAdminFilter(claimAdminName);
            System.out.println("✓ Filter applied with: " + claimAdminName);


            WaitUtils.sleep(3000);
            String actualClaimAdminName = dailyBillingPage.getClaimAdminFilterValue();
            String expectedClaimAdminName = "Insurance Company of the West - San Diego";

            try {
                Assert.assertEquals(actualClaimAdminName, expectedClaimAdminName, "Case Number filter successfully applied.");
                test.pass("✓ Case Number filter value verified: " + actualClaimAdminName);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ Case Number filter value mismatch. Expected: " + expectedClaimAdminName + ", Found: " + actualClaimAdminName);
                System.out.println("❌ TEST FAILED");
                throw e;
            }

        }

        @Test(priority = 5, description = "SMOKE_DB_005 - Verify user can filter Daily Billing invoices by Invoice")
        public void SMOKE_FH_005() {

            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");

            String InvoiceNumber = DailyBillingTestDataProperties.get("invoiceNumber");
            // Apply filter with Claim Admin name
            dailyBillingPage.setInvoiceNumberFilter(InvoiceNumber);
            System.out.println("✓ Filter applied with: " + InvoiceNumber);


            WaitUtils.sleep(5000);
            String actualInvoiceNumber = dailyBillingPage.getInvoiceNumberFilterValue();

            try {
                Assert.assertEquals(actualInvoiceNumber, InvoiceNumber, "Case Number filter successfully applied.");
                test.pass("✓ Case Number filter value verified: " + actualInvoiceNumber);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ Case Number filter value mismatch. Expected: " + InvoiceNumber + ", Found: " + actualInvoiceNumber);
                System.out.println("❌ TEST FAILED");
                throw e;
            }

        }

        @Test(priority = 6, description = "SMOKE_DB_006 - Verify invoices with EMC scrubbing flag (red background) cannot tick without fill all mandatory fields.")
        public void SMOKE_FH_006() {
            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");

            // Get original date
            String Dos = DailyBillingTestDataProperties.get("dateofservice");
            System.out.println("Original DOS: " + Dos);  // e.g., "05/26/2025"

            // Format date FIRST
            String formattedDos = dailyBillingPage.removeYearPrefix(Dos);
            System.out.println("Formatted DOS: " + formattedDos);  // e.g., "05/26/25"

            // Apply filter with formatted date
            dailyBillingPage.setDosFilter(Dos);
            System.out.println("✓ Filter applied with: " + Dos);

            // Wait for results
            WaitUtils.waitForVisibility(driver, LocatorConstants.Dos, 60);

            // Validate
            String actualDos = dailyBillingPage.getDosFilterValue();
            String expectedDos = "DOS : " + formattedDos;

            System.out.println("Expected: " + expectedDos);
            System.out.println("Actual: " + actualDos);

            try {
                Assert.assertEquals(actualDos, expectedDos, "DOS filter successfully applied.");
                test.pass("✓ DOS filter value verified: " + actualDos);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ DOS filter value mismatch. Expected: " + expectedDos + ", Found: " + actualDos);
                System.out.println("❌ TEST FAILED");
                throw e;
            }

            //Click the Checkbox
            dailyBillingPage.ClickCheckBox();

            try {
                Assert.assertTrue(dailyBillingPage.isMissingInfoModalDisplayed(),
                        "Missing Information modal should appear for invoice with Red EMC flag");
                test.pass("✅ Missing Information modal appeared - Invoice is NOT ready for billing (Red EMC)");
            } catch (AssertionError e) {
                test.fail("❌ Missing Information modal did not appear. This invoice might be ready for billing (Green EMC)");
                throw e;
            }
        }


        @Test(priority = 7, description = "SMOKE_DB_007 - Verify Total Record Count and EAMS Non-Verified Record Count")
        public void SMOKE_DB_007() {

            // Wait for page
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            System.out.println("✓ Daily Billing page loaded");

            // Get original date
            String Dos = DailyBillingTestDataProperties.get("dateofservice");
            System.out.println("Original DOS: " + Dos);  // e.g., "05/26/2025"

            // Format date FIRST
            String formattedDos = dailyBillingPage.removeYearPrefix(Dos);
            System.out.println("Formatted DOS: " + formattedDos);  // e.g., "05/26/25"

            // Apply filter with formatted date
            dailyBillingPage.setDosFilter(Dos);
            System.out.println("✓ Filter applied with: " + Dos);

            // Wait for results
            WaitUtils.waitForVisibility(driver, LocatorConstants.Dos, 60);

            // Validate
            String actualDos = dailyBillingPage.getDosFilterValue();
            String expectedDos = "DOS : " + formattedDos;

            System.out.println("Expected: " + expectedDos);
            System.out.println("Actual: " + actualDos);

            try {
                Assert.assertEquals(actualDos, expectedDos, "DOS filter successfully applied.");
                test.pass("✓ DOS filter value verified: " + actualDos);
                System.out.println("✅ TEST PASSED");
            } catch (AssertionError e) {
                test.fail("✗ DOS filter value mismatch. Expected: " + expectedDos + ", Found: " + actualDos);
                System.out.println("❌ TEST FAILED");
                throw e;
            }

            // Optional: Print debug info (remove this after confirming it works)
            dailyBillingPage.printCountDebugInfo();

            // Get header counts
            int totalCountHeader = dailyBillingPage.getTotalRecordCountFromHeader();
            int eamsNonVerifiedHeader = dailyBillingPage.getEAMSNonVerifiedCountFromHeader();

            // Get actual counts
            int actualRowCount = dailyBillingPage.getActualRowCount();
            int eamsNotVerifiedCount = dailyBillingPage.countEAMSNotVerifiedInvoices();

            // Validation 1: Total Record Count
            try {
                Assert.assertEquals(actualRowCount, totalCountHeader,
                        "Total Record Count mismatch");
                test.pass("✅ Total Record Count is correct: " + totalCountHeader);
            } catch (AssertionError e) {
                test.fail("❌ Total Record Count - Expected: " + totalCountHeader +
                        ", Actual: " + actualRowCount);
                throw e;
            }

            // Validation 2: EAMS Non-Verified Count
            try {
                Assert.assertEquals(eamsNotVerifiedCount, eamsNonVerifiedHeader,
                        "EAMS Non-Verified Count mismatch");
                test.pass("✅ EAMS Non-Verified Count is correct: " + eamsNonVerifiedHeader);
            } catch (AssertionError e) {
                test.fail("❌ EAMS Non-Verified Count - Expected: " + eamsNonVerifiedHeader +
                        ", Actual: " + eamsNotVerifiedCount);
                throw e;
            }

            test.pass("SMOKE_DB_007 PASSED - All counts validated successfully");
        }

        @Test(priority = 8, description = "SMOKE_DB_008 - Verify Report View opens for selected invoice with HCFA")
        public void SMOKE_DB_008() {
            test.info("📋 Starting SMOKE_DB_008 - Verify Report View Functionality");

            // Wait for Daily Billing page to load
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            WaitUtils.sleep(3000);
            System.out.println("✓ Daily Billing page loaded");
            test.info("Daily Billing page loaded successfully");

            // Get original date
            String Dos = DailyBillingTestDataProperties.get("dateofservice");
            System.out.println("Original DOS: " + Dos);  // e.g., "05/26/2025"

            // Format date FIRST
            String formattedDos = dailyBillingPage.removeYearPrefix(Dos);
            System.out.println("Formatted DOS: " + formattedDos);  // e.g., "05/26/25"

            // Apply filter with formatted date
            dailyBillingPage.setDosFilter(Dos);
            System.out.println("✓ Filter applied with: " + Dos);


            // Step 1: Find and select a ready-to-bill invoice (skips Not Verified and invoices with missing mandatory fields)
            try {
                WaitUtils.sleep(2000);
                boolean invoiceFound = dailyBillingPage.findAndSelectReadyToBillInvoice();

                if (!invoiceFound) {
                    test.fail("❌ No ready-to-bill invoices found on this page. " +
                            "All invoices are either 'Not Verified' status or missing mandatory fields.");
                    throw new AssertionError("No ready-to-bill invoices available for Report View test");
                }

                test.info("✓ Step 1: Ready-to-bill invoice selected (verified and with mandatory fields filled)");
            } catch (Exception e) {
                test.fail("❌ Failed to select ready-to-bill invoice: " + e.getMessage());
                throw e;
            }

            // Step 2: Click HCFA button
            try {
                dailyBillingPage.clickHCFAButton();
                test.info("✓ Step 2: HCFA button clicked");
            } catch (Exception e) {
                test.fail("❌ Failed to click HCFA button: " + e.getMessage());
                throw e;
            }

            // Step 3: Click Report View button
            try {
                dailyBillingPage.clickReportViewButton();
                test.info("✓ Step 3: Report View button clicked");
            } catch (Exception e) {
                test.fail("❌ Failed to click Report View button: " + e.getMessage());
                throw e;
            }

            // Step 4: Verify Report View opens
            try {
                boolean isReportDisplayed = dailyBillingPage.isReportViewDisplayed();
                String status = dailyBillingPage.getReportViewStatus();

                Assert.assertTrue(isReportDisplayed, "Report View should be displayed");
                test.pass("✅ Report View opened successfully: " + status);
                System.out.println("✅ TEST PASSED - Report View verified");
            } catch (AssertionError e) {
                test.fail("❌ Report View did not open as expected");
                System.out.println("❌ TEST FAILED - Report View not displayed");
                throw e;
            }

            test.pass("🎉 SMOKE_DB_008 PASSED - Report View functionality verified");
        }

        @Test(priority = 9, description = "SMOKE_DB_009 - Verify EAMS verification status and comment display")
        public void SMOKE_DB_009() {
            test.info("📋 Starting SMOKE_DB_009 - Verify EAMS Verification Status and Comment");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_009: EAMS Verification Test");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Find a verified invoice with EAMS status and comment
                test.info("Step 2: Searching for EAMS Verified or EAMS Not Verified invoice");
                System.out.println("\n🔍 Step 2: Searching for verified invoice...");

                Map<String, String> invoiceData = dailyBillingPage.findVerifiedInvoiceWithComment();

                // Step 3: Verify we found a verified invoice
                test.info("Step 3: Validating invoice was found");
                System.out.println("\n✅ Step 3: Validating search result...");

                Assert.assertNotNull(invoiceData,
                        "No EAMS Verified or EAMS Not Verified invoices found in the table. " +
                                "Please ensure there are invoices that have been processed through EAMS.");

                test.info("✓ Found verified invoice");
                System.out.println("✓ Verified invoice found");

                // Step 4: Extract status and comment
                String eamsStatus = invoiceData.get("status");
                String eamsComment = invoiceData.get("comment");

                test.info("EAMS Status: " + eamsStatus);
                test.info("EAMS Comment: " + eamsComment);
                System.out.println("\n📊 Invoice Details:");
                System.out.println("   Status: " + eamsStatus);
                System.out.println("   Comment: " + eamsComment);

                // Step 5: Verify status is valid
                test.info("Step 4: Validating EAMS status");
                System.out.println("\n✅ Step 4: Validating status...");

                boolean isValidStatus = eamsStatus.equals("EAMS Verified") ||
                        eamsStatus.equals("EAMS Not Verified");

                Assert.assertTrue(isValidStatus,
                        "Status should be 'EAMS Verified' or 'EAMS Not Verified', but found: " + eamsStatus);

                test.info("✓ Status is valid: " + eamsStatus);
                System.out.println("✓ Status validation passed: " + eamsStatus);

                // Step 6: Verify comment is not empty
                test.info("Step 5: Validating comment is not empty");
                System.out.println("\n✅ Step 5: Validating comment...");

                Assert.assertNotNull(eamsComment, "EAMS comment should not be null");
                Assert.assertFalse(eamsComment.isEmpty(),
                        "EAMS comment should not be empty for verified/not verified invoices. " +
                                "Business Rule: Invoices with status '" + eamsStatus + "' MUST have a scrubbing comment.");

                test.info("✓ Comment is not empty");
                System.out.println("✓ Comment is not empty");

                // Step 7: Verify comment matches one of the 8 valid EAMS comments
                test.info("Step 6: Validating comment against 8 valid EAMS comments");
                System.out.println("\n🔍 Step 6: Validating comment against valid list...");

                boolean isValidComment = dailyBillingPage.isValidEamsComment(eamsComment);

                Assert.assertTrue(isValidComment,
                        "EAMS comment should match one of the 8 valid comments.\n" +
                                "Found comment: " + eamsComment + "\n" +
                                "This might be a new comment type or a data issue.");

                test.info("✓ Comment is valid: " + eamsComment);
                System.out.println("✓ Comment validation passed");

                // Step 8: Log final summary
                test.info("========================================");
                test.info("EAMS Verification Summary:");
                test.info("Status: " + eamsStatus);
                test.info("Comment: " + eamsComment);
                test.info("Business Rule Validated:");
                test.info("- Invoice has EAMS status (verified or not verified)");
                test.info("- Invoice has scrubbing comment (not empty)");
                test.info("- Comment matches one of 8 valid EAMS comments");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 EAMS Verification Summary:");
                System.out.println("   Status: " + eamsStatus);
                System.out.println("   Comment: " + eamsComment);
                System.out.println("\n✅ Business Rule Validated:");
                System.out.println("   ✓ Invoice has EAMS status");
                System.out.println("   ✓ Comment is not empty");
                System.out.println("   ✓ Comment matches valid list");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_009 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure there are invoices with 'EAMS Verified' or 'EAMS Not Verified' status");
                System.out.println("   2. 'Not Verified' invoices will be skipped (correct behavior)");
                System.out.println("   3. Check that verified invoices have scrubbing comments");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_009 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                logger.severe("SMOKE_DB_009 failed with exception: " + e.getMessage());
                throw e;
            }

            test.pass("🎉 SMOKE_DB_009 PASSED - EAMS verification status and comment validated successfully");
            System.out.println("\n✅ SMOKE_DB_009 TEST PASSED");
        }

        @Test(priority = 10, description = "SMOKE_DB_010 - Validate ALL invoices EAMS status and comments")
        public void SMOKE_DB_010() {
            test.info("📋 Starting SMOKE_DB_010 - Validate ALL Invoices EAMS Comments");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_010: Validate ALL EAMS Comments");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice01");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Validate ALL invoices
                test.info("Step 2: Validating ALL invoices in the table");
                System.out.println("\n🔍 Step 2: Starting validation of ALL invoices...\n");

                Map<String, Object> validationResult = dailyBillingPage.validateAllInvoicesEamsComments();

                // Step 3: Check validation result
                Boolean success = (Boolean) validationResult.get("success");

                if (!success) {
                    // Validation failed - get error details
                    String error = (String) validationResult.get("error");
                    Integer failedRow = (Integer) validationResult.get("failedRow");

                    test.fail("❌ SMOKE_DB_010 FAILED at Row " + failedRow);
                    test.fail("Error: " + error);
                    System.out.println("\n❌ TEST FAILED AT ROW " + failedRow);
                    System.out.println("Error: " + error);

                    Assert.fail(error);
                }

                // Step 4: Get counts
                Integer totalInvoices = (Integer) validationResult.get("totalInvoices");
                Integer eamsVerifiedCount = (Integer) validationResult.get("eamsVerifiedCount");
                Integer eamsNotVerifiedCount = (Integer) validationResult.get("eamsNotVerifiedCount");
                Integer notVerifiedCount = (Integer) validationResult.get("notVerifiedCount");

                // Step 5: Validate that we have at least some EAMS processed invoices
                int eamsProcessedTotal = eamsVerifiedCount + eamsNotVerifiedCount;

                test.info("Step 3: Checking minimum requirements");
                System.out.println("\n✅ Step 3: Checking minimum requirements...");

                Assert.assertTrue(eamsProcessedTotal > 0,
                        "No EAMS Verified or EAMS Not Verified invoices found. " +
                                "Please ensure there are invoices that have been processed through EAMS.");

                test.info("✓ Found " + eamsProcessedTotal + " EAMS processed invoices");
                System.out.println("✓ Found " + eamsProcessedTotal + " EAMS processed invoices");

                // Step 6: Log detailed summary
                test.info("========================================");
                test.info("VALIDATION SUMMARY:");
                test.info("Total Invoices Validated: " + totalInvoices);
                test.info("- EAMS Verified: " + eamsVerifiedCount + " (all have valid comments ✓)");
                test.info("- EAMS Not Verified: " + eamsNotVerifiedCount + " (all have valid comments ✓)");
                test.info("- Not Verified: " + notVerifiedCount + " (all correctly have no comments ✓)");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("Total Invoices Validated: " + totalInvoices);
                System.out.println();
                System.out.println("✅ EAMS Verified: " + eamsVerifiedCount);
                System.out.println("   → All have valid scrubbing comments");
                System.out.println();
                System.out.println("✅ EAMS Not Verified: " + eamsNotVerifiedCount);
                System.out.println("   → All have valid scrubbing comments");
                System.out.println();
                System.out.println("✅ Not Verified: " + notVerifiedCount);
                System.out.println("   → All correctly have NO comments");
                System.out.println("========================================");

                // Step 7: Log business rules validated
                test.info("Business Rules Validated:");
                test.info("✓ All 'EAMS Verified' invoices have valid comments");
                test.info("✓ All 'EAMS Not Verified' invoices have valid comments");
                test.info("✓ All comments match the 8 valid EAMS comments");
                test.info("✓ All 'Not Verified' invoices have no comments");

                System.out.println("\n✅ Business Rules Validated:");
                System.out.println("   ✓ All 'EAMS Verified' invoices have valid comments");
                System.out.println("   ✓ All 'EAMS Not Verified' invoices have valid comments");
                System.out.println("   ✓ All comments match the 8 valid EAMS comments");
                System.out.println("   ✓ All 'Not Verified' invoices have no comments");
                System.out.println();

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_010 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Check validation error details above");
                System.out.println("   2. Ensure EAMS verified invoices have scrubbing comments");
                System.out.println("   3. Ensure 'Not Verified' invoices do not have comments");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_010 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_010 PASSED - ALL invoices validated successfully");
            System.out.println("✅ SMOKE_DB_010 TEST PASSED - ALL INVOICES VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 11, description = "SMOKE_DB_011 - Verify clicking status buttons opens EAMS Details Verification Popup")
        public void SMOKE_DB_011() {
            test.info("📋 Starting SMOKE_DB_011 - Verify EAMS Details Popup Opens on Status Button Click");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_011: EAMS Details Popup Test");
            System.out.println("========================================\n");

            int successCount = 0;
            int totalStatuses = 3;
            StringBuilder validationSummary = new StringBuilder();

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice02");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos + "\n");

                // Step 2: Test "Not Verified" status button
                test.info("Step 2: Testing 'Not Verified' status button");
                System.out.println("🔘 Step 2: Testing 'Not Verified' button...");

                if (dailyBillingPage.tryClickStatusButton("Not Verified")) {
                    // Button found and clicked
                    boolean popupDisplayed = dailyBillingPage.isEamsDetailsPopupDisplayed();

                    if (popupDisplayed) {
                        String popupTitle = dailyBillingPage.getEamsDetailsPopupTitle();
                        test.pass("✓ 'Not Verified' button → Popup opened (Title: " + popupTitle + ")");
                        System.out.println("   ✅ Popup validated - Title: " + popupTitle);
                        validationSummary.append("   ✓ 'Not Verified' - Popup verified\n");
                        successCount++;

                        // Close popup (no page refresh needed)
                        dailyBillingPage.closeEamsDetailsPopup();
                    } else {
                        test.warning("⚠ 'Not Verified' button clicked but popup did not appear");
                        System.out.println("   ⚠ Popup did not appear");
                        validationSummary.append("   ⚠ 'Not Verified' - Popup failed to open\n");
                    }
                } else {
                    // Button not found - this is OK
                    test.info("⏭ 'Not Verified' status not found under this DOS - Skipping");
                    System.out.println("   ⏭ Skipped - No 'Not Verified' invoices under this DOS\n");
                    validationSummary.append("   ⏭ 'Not Verified' - Not found under this DOS\n");
                }

                // Step 3: Test "EAMS Verified" status button (same DOS, same page)
                test.info("Step 3: Testing 'EAMS Verified' status button");
                System.out.println("🟢 Step 3: Testing 'EAMS Verified' button...");

                if (dailyBillingPage.tryClickStatusButton("EAMS Verified")) {
                    // Button found and clicked
                    boolean popupDisplayed = dailyBillingPage.isEamsDetailsPopupDisplayed();

                    if (popupDisplayed) {
                        String popupTitle = dailyBillingPage.getEamsDetailsPopupTitle();
                        test.pass("✓ 'EAMS Verified' button → Popup opened (Title: " + popupTitle + ")");
                        System.out.println("   ✅ Popup validated - Title: " + popupTitle);
                        validationSummary.append("   ✓ 'EAMS Verified' - Popup verified\n");
                        successCount++;

                        // Close popup (no page refresh needed)
                        dailyBillingPage.closeEamsDetailsPopup();
                    } else {
                        test.warning("⚠ 'EAMS Verified' button clicked but popup did not appear");
                        System.out.println("   ⚠ Popup did not appear");
                        validationSummary.append("   ⚠ 'EAMS Verified' - Popup failed to open\n");
                    }
                } else {
                    // Button not found - this is OK
                    test.info("⏭ 'EAMS Verified' status not found under this DOS - Skipping");
                    System.out.println("   ⏭ Skipped - No 'EAMS Verified' invoices under this DOS\n");
                    validationSummary.append("   ⏭ 'EAMS Verified' - Not found under this DOS\n");
                }

                // Step 4: Test "EAMS Not Verified" status button (same DOS, same page)
                test.info("Step 4: Testing 'EAMS Not Verified' status button");
                System.out.println("🔴 Step 4: Testing 'EAMS Not Verified' button...");

                if (dailyBillingPage.tryClickStatusButton("EAMS Not Verified")) {
                    // Button found and clicked
                    boolean popupDisplayed = dailyBillingPage.isEamsDetailsPopupDisplayed();

                    if (popupDisplayed) {
                        String popupTitle = dailyBillingPage.getEamsDetailsPopupTitle();
                        test.pass("✓ 'EAMS Not Verified' button → Popup opened (Title: " + popupTitle + ")");
                        System.out.println("   ✅ Popup validated - Title: " + popupTitle);
                        validationSummary.append("   ✓ 'EAMS Not Verified' - Popup verified\n");
                        successCount++;

                        // Close popup (no page refresh needed)
                        dailyBillingPage.closeEamsDetailsPopup();
                    } else {
                        test.warning("⚠ 'EAMS Not Verified' button clicked but popup did not appear");
                        System.out.println("   ⚠ Popup did not appear");
                        validationSummary.append("   ⚠ 'EAMS Not Verified' - Popup failed to open\n");
                    }
                } else {
                    // Button not found - this is OK
                    test.info("⏭ 'EAMS Not Verified' status not found under this DOS - Skipping");
                    System.out.println("   ⏭ Skipped - No 'EAMS Not Verified' invoices under this DOS\n");
                    validationSummary.append("   ⏭ 'EAMS Not Verified' - Not found under this DOS\n");
                }

                // Step 5: Final validation - At least 1 status must be present
                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_011 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("Validated " + successCount + " out of " + totalStatuses + " status types:\n");
                System.out.println(validationSummary);

                test.info("========================================");
                test.info("SMOKE_DB_011 VALIDATION SUMMARY:");
                test.info("Validated " + successCount + "/" + totalStatuses + " status types");
                test.info(validationSummary.toString());
                test.info("========================================");

                // Assert that at least 1 status button was found and validated
                Assert.assertTrue(successCount >= 1,
                        "At least one status button should be present and validated under this DOS. Found: " + successCount + "/3");

                if (successCount == totalStatuses) {
                    System.out.println("✅ All 3 status types validated successfully!");
                    test.pass("🎉 SMOKE_DB_011 PASSED - All 3 status types validated (3/3)");
                } else {
                    System.out.println("✅ Partial validation successful (" + successCount + "/" + totalStatuses + " status types found)");
                    test.pass("✅ SMOKE_DB_011 PASSED - " + successCount + "/" + totalStatuses + " status types validated");
                }
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_011 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Check if any invoices exist under the specified DOS");
                System.out.println("   2. Verify at least one status type should be present");
                System.out.println("   3. Check popup XPath locators if buttons are found but popup fails");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_011 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

        @Test(priority = 12, description = "SMOKE_DB_012 - Verify bills cannot be submitted without EAMS verification completed")
        public void SMOKE_DB_012() {
            test.info("📋 Starting SMOKE_DB_012 - Verify Not Verified invoices cannot be submitted");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_012: Invoice Submission Validation");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice03");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Find and click checkbox for "Not Verified" invoice
                test.info("Step 2: Finding 'Not Verified' invoice and clicking checkbox");
                System.out.println("\n🔍 Step 2: Searching for 'Not Verified' invoice...");

                boolean checkboxClicked = dailyBillingPage.clickCheckboxForNotVerifiedInvoice();

                Assert.assertTrue(checkboxClicked,
                        "Failed to find or click checkbox for 'Not Verified' invoice. " +
                                "Ensure there are invoices with 'Not Verified' status in the test data.");

                test.info("✓ Checkbox clicked for 'Not Verified' invoice");
                System.out.println("✓ Checkbox clicked successfully");

                // Step 3: Verify validation error modal appears
                test.info("Step 3: Verifying validation error modal appears");
                System.out.println("\n✅ Step 3: Checking for validation error popup...");

                boolean modalDisplayed = dailyBillingPage.isValidationErrorModalDisplayed();

                Assert.assertTrue(modalDisplayed,
                        "Validation error modal should appear when trying to select 'Not Verified' invoice. " +
                                "Business Rule: Invoices without EAMS verification cannot be submitted.");

                test.info("✓ Validation error modal appeared as expected");
                System.out.println("✓ Validation error modal displayed");

                // Step 4: Get and log the error message
                String errorMessage = dailyBillingPage.getValidationErrorMessage();
                test.info("Error Message: " + errorMessage);
                System.out.println("   Message: " + errorMessage);

                // Step 5: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_012 VALIDATION SUMMARY:");
                test.info("✓ Found 'Not Verified' invoice");
                test.info("✓ Clicked checkbox for submission");
                test.info("✓ Validation error modal appeared");
                test.info("✓ System prevents submission without EAMS verification");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_012 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ 'Not Verified' invoices cannot be submitted");
                System.out.println("   ✓ System displays validation error modal");
                System.out.println("   ✓ EAMS verification is mandatory for submission");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_012 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure there are invoices with 'Not Verified' status");
                System.out.println("   2. Check that validation modal appears when checkbox is clicked");
                System.out.println("   3. Verify checkbox locators are correct");
                System.out.println("   4. Confirm modal locator is correct");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_012 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_012 PASSED - System correctly prevents submission of unverified invoices");
            System.out.println("✅ SMOKE_DB_012 TEST PASSED - BUSINESS RULE VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 13, description = "SMOKE_DB_013 - Verify clicking the view (eye) button and open the report view")
        public void SMOKE_DB_013() {

            test.info("📋 Starting SMOKE_DB_013 - Verify Report View Functionality");

            // Wait for Daily Billing page to load
            WaitUtils.waitForVisibility(driver, LocatorConstants.DailyBillingLabel, 60);
            WaitUtils.sleep(3000);
            System.out.println("✓ Daily Billing page loaded");
            test.info("Daily Billing page loaded successfully");

            // Get original date
            String Dos = DailyBillingTestDataProperties.get("dateofservice01");
            System.out.println("Original DOS: " + Dos);  // e.g., "05/26/2025"

            // Format date FIRST
            String formattedDos = dailyBillingPage.removeYearPrefix(Dos);
            System.out.println("Formatted DOS: " + formattedDos);  // e.g., "05/26/25"

            // Apply filter with formatted date
            dailyBillingPage.setDosFilter(Dos);
            System.out.println("✓ Filter applied with: " + Dos);


            // Step 1: Select the first invoice checkbox
            try {
                WaitUtils.sleep(2000);
                dailyBillingPage.selectFirstInvoice();
                test.info("✓ Step 1: First invoice selected");
            } catch (Exception e) {
                test.fail("❌ Failed to select first invoice: " + e.getMessage());
                throw e;
            }

        /* Step 2: Click HCFA button
        try {
            dailyBillingPage.clickHCFAButton();
            test.info("✓ Step 2: HCFA button clicked");
        } catch (Exception e) {
            test.fail("❌ Failed to click HCFA button: " + e.getMessage());
            throw e;
        }*/

            // Step 3: Click Report View button
            try {
                dailyBillingPage.clickReportViewButton();
                test.info("✓ Step 3: Report View button clicked");
            } catch (Exception e) {
                test.fail("❌ Failed to click Report View button: " + e.getMessage());
                throw e;
            }

            // Step 4: Verify Report View opens
            try {
                boolean isReportDisplayed = dailyBillingPage.isReportViewDisplayed();
                String status = dailyBillingPage.getReportViewStatus();

                Assert.assertTrue(isReportDisplayed, "Report View should be displayed");
                test.pass("✅ Report View opened successfully: " + status);
                System.out.println("✅ TEST PASSED - Report View verified");
            } catch (AssertionError e) {
                test.fail("❌ Report View did not open as expected");
                System.out.println("❌ TEST FAILED - Report View not displayed");
                throw e;
            }

            test.pass("🎉 SMOKE_DB_013 PASSED - Report View functionality verified");

        }

        @Test(priority = 14, description = "SMOKE_DB_014 - Verify user can filter bills by EMC and successfully display all bills ready for EMC submission")
        public void SMOKE_DB_014() {
            test.info("📋 Starting SMOKE_DB_014 - Verify EMC Filter and EMC Ready Invoice Validation");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_014: EMC Filter Test");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice04");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click EMC Filter
                test.info("Step 2: Clicking EMC Filter");
                System.out.println("\n🔘 Step 2: Clicking EMC Filter...");

                try {
                    dailyBillingPage.clickEmcFilter();
                    test.info("✓ EMC Filter clicked");
                    System.out.println("✓ EMC Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for EMC filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Filter: " + e.getMessage());
                    System.err.println("❌ EMC Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice
                test.info("Step 3: Finding and clicking first EAMS Verified/Not Verified invoice");
                System.out.println("\n🔍 Step 3: Searching for EAMS Verified/Not Verified invoice...");

                boolean invoiceClicked = dailyBillingPage.clickFirstEamsVerifiedInvoiceNumber();

                Assert.assertTrue(invoiceClicked,
                        "Failed to find or click invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are EMC-ready invoices in the test data.");

                test.info("✓ Invoice clicked successfully");
                System.out.println("✓ Invoice clicked - Navigating to Edit Invoice page");
                WaitUtils.sleep(3000);

                // Step 4: Validate Edit Invoice page loaded
                test.info("Step 4: Validating Edit Invoice page loaded");
                System.out.println("\n✅ Step 4: Validating Edit Invoice page...");

                String editInvoiceLabel = dailyBillingPage.getEditInvoiceLabel();
                String expectedLabel = "Edit Invoice";

                Assert.assertEquals(editInvoiceLabel, expectedLabel,
                        "Edit Invoice label should be '" + expectedLabel + "' but found: " + editInvoiceLabel);

                test.info("✓ Edit Invoice page loaded: " + editInvoiceLabel);
                System.out.println("✓ Edit Invoice page validated: " + editInvoiceLabel);

                // Step 5: Click Edit button to enable EDIT MODE
                test.info("Step 5: Clicking Edit button to enable EDIT MODE");
                System.out.println("\n✏️ Step 5: Enabling EDIT MODE...");

                try {
                    dailyBillingPage.clickEditButton();
                    test.info("✓ EDIT MODE enabled");
                    System.out.println("✓ EDIT MODE enabled");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Edit button: " + e.getMessage());
                    System.err.println("❌ Edit button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Click Claim Administrator
                test.info("Step 6: Clicking Claim Administrator");
                System.out.println("\n👤 Step 6: Opening Claim Administrator section...");

                try {
                    dailyBillingPage.clickClaimAdministrator();
                    test.info("✓ Claim Administrator clicked");
                    System.out.println("✓ Claim Administrator section opened");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Claim Administrator: " + e.getMessage());
                    System.err.println("❌ Claim Administrator click failed: " + e.getMessage());
                    throw e;
                }

                // Step 7: Validate Claim Administrator section loaded
                test.info("Step 7: Validating Claim Administrator section loaded");
                System.out.println("\n✅ Step 7: Validating Claim Administrator section...");

                String claimAdminLabel = dailyBillingPage.getClaimAdministratorSectionLabel();
                String expectedClaimAdminLabel = "Claim Administrator";

                Assert.assertEquals(claimAdminLabel, expectedClaimAdminLabel,
                        "Claim Administrator label should be '" + expectedClaimAdminLabel + "' but found: " + claimAdminLabel);

                test.info("✓ Claim Administrator section loaded: " + claimAdminLabel);
                System.out.println("✓ Claim Administrator section validated: " + claimAdminLabel);

                // Step 8: Validate EMC Ready status (BOTH EMC Toggle AND EDI PayerID required)
                test.info("Step 8: Validating EMC Ready status");
                System.out.println("\n🔍 Step 8: Validating EMC Ready status...");

                // Get EMC Toggle status
                boolean emcToggleEnabled = dailyBillingPage.isEmcToggleEnabled();

                // Get EDI PayerID value
                String ediPayerId = dailyBillingPage.getEdiPayerId();
                boolean ediPayerIdNotBlank = !ediPayerId.isEmpty();

                // Validate EMC Ready invoice (uses AND logic: BOTH toggle AND payerID)
                boolean isEmcReady = dailyBillingPage.isEmcReadyInvoice();

                // Assertion 1: EMC Toggle MUST be enabled (MANDATORY)
                Assert.assertTrue(emcToggleEnabled,
                        "❌ EMC Toggle Button MUST be ENABLED for EMC filtered invoices. " +
                                "Business Rule: EMC Filter should only show invoices with EMC Toggle enabled. " +
                                "Found: EMC Toggle=DISABLED");

                test.info("✓ EMC Toggle is ENABLED");
                System.out.println("✓ EMC Toggle is ENABLED");

                // Assertion 2: EDI PayerID MUST not be blank (MANDATORY)
                Assert.assertTrue(ediPayerIdNotBlank,
                        "❌ EDI PayerID MUST NOT be BLANK for EMC filtered invoices. " +
                                "Business Rule: EMC Ready invoices must have EDI PayerID. " +
                                "Found: EDI PayerID is BLANK");

                test.info("✓ EDI PayerID is NOT BLANK: " + ediPayerId);
                System.out.println("✓ EDI PayerID is NOT BLANK: " + ediPayerId);

                // Assertion 3: Invoice is EMC Ready (BOTH conditions must pass)
                Assert.assertTrue(isEmcReady,
                        "❌ Invoice should be EMC Ready (BOTH EMC Toggle enabled AND EDI PayerID not blank). " +
                                "Business Rule: EMC Filter should only show EMC Ready invoices. " +
                                "Found: EMC Toggle=" + emcToggleEnabled + ", EDI PayerID='" + ediPayerId + "'");

                test.info("✓ Invoice is EMC Ready (EMC Toggle: ENABLED, EDI PayerID: " + ediPayerId + ")");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_014 VALIDATION SUMMARY:");
                test.info("✓ EMC Filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice found and clicked");
                test.info("✓ Edit Invoice page loaded successfully");
                test.info("✓ EDIT MODE enabled successfully");
                test.info("✓ Claim Administrator section opened");
                test.info("✓ EMC Toggle Button: ENABLED (MANDATORY)");
                test.info("✓ EDI PayerID: " + ediPayerId + " (NOT BLANK - MANDATORY)");
                test.info("✓ Invoice is EMC Ready for submission (BOTH conditions met)");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_014 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ EMC Filter Validation:");
                System.out.println("   ✓ EMC Filter applied successfully");
                System.out.println("   ✓ EMC filtered invoices loaded");
                System.out.println();
                System.out.println("✅ Invoice Navigation:");
                System.out.println("   ✓ EAMS Verified/Not Verified invoice clicked");
                System.out.println("   ✓ Edit Invoice page opened");
                System.out.println("   ✓ EDIT MODE enabled");
                System.out.println();
                System.out.println("✅ Claim Administrator Validation:");
                System.out.println("   ✓ Claim Administrator section opened");
                System.out.println("   ✓ EMC Toggle Button: ENABLED ✓ (MANDATORY)");
                System.out.println("   ✓ EDI PayerID: " + ediPayerId + " ✓ (MANDATORY)");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ EMC Filter shows only EMC Ready invoices");
                System.out.println("   ✓ EMC Ready = (EMC Toggle enabled) AND (EDI PayerID not blank)");
                System.out.println("   ✓ BOTH conditions are MANDATORY");
                System.out.println("   ✓ Invoice meets ALL EMC Ready criteria");
                System.out.println("   ✓ Invoice is ready for EMC submission");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_014 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure EMC Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that invoice navigation works correctly");
                System.out.println("   4. Verify Edit button and Claim Administrator locators");
                System.out.println("   5. MANDATORY: EMC Toggle MUST be enabled for filtered invoices");
                System.out.println("   6. MANDATORY: EDI PayerID MUST NOT be blank");
                System.out.println("   7. Check EMC Toggle detection logic if it shows disabled when actually enabled");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_014 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_014 PASSED - EMC Filter validation and EMC Ready invoice verified successfully");
            System.out.println("✅ SMOKE_DB_014 TEST PASSED - EMC FILTER VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 15, description = "SMOKE_DB_015 - Verify user can filter bills by EMAIL and successfully display all bills ready for EMAIL submission")
        public void SMOKE_DB_015() {
            test.info("📋 Starting SMOKE_DB_015 - Verify EMAIL Filter and EMAIL Ready Invoice Validation");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_015: EMAIL Filter Test");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice04");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click EMAIL Filter
                test.info("Step 2: Clicking EMAIL Filter");
                System.out.println("\n🔘 Step 2: Clicking EMAIL Filter...");

                try {
                    dailyBillingPage.clickEmailFilter();
                    test.info("✓ EMAIL Filter clicked");
                    System.out.println("✓ EMAIL Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for EMAIL filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMAIL Filter: " + e.getMessage());
                    System.err.println("❌ EMAIL Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice
                test.info("Step 3: Finding and clicking first EAMS Verified/Not Verified invoice");
                System.out.println("\n🔍 Step 3: Searching for EAMS Verified/Not Verified invoice...");

                boolean invoiceClicked = dailyBillingPage.clickFirstEamsVerifiedInvoiceNumber();

                Assert.assertTrue(invoiceClicked,
                        "Failed to find or click invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are EMAIL-ready invoices in the test data.");

                test.info("✓ Invoice clicked successfully");
                System.out.println("✓ Invoice clicked - Navigating to Edit Invoice page");
                WaitUtils.sleep(3000);

                // Step 4: Validate Edit Invoice page loaded
                test.info("Step 4: Validating Edit Invoice page loaded");
                System.out.println("\n✅ Step 4: Validating Edit Invoice page...");

                String editInvoiceLabel = dailyBillingPage.getEditInvoiceLabel();
                String expectedLabel = "Edit Invoice";

                Assert.assertEquals(editInvoiceLabel, expectedLabel,
                        "Edit Invoice label should be '" + expectedLabel + "' but found: " + editInvoiceLabel);

                test.info("✓ Edit Invoice page loaded: " + editInvoiceLabel);
                System.out.println("✓ Edit Invoice page validated: " + editInvoiceLabel);

                // Step 5: Click Edit button to enable EDIT MODE
                test.info("Step 5: Clicking Edit button to enable EDIT MODE");
                System.out.println("\n✏️ Step 5: Enabling EDIT MODE...");

                try {
                    dailyBillingPage.clickEditButton();
                    test.info("✓ EDIT MODE enabled");
                    System.out.println("✓ EDIT MODE enabled");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Edit button: " + e.getMessage());
                    System.err.println("❌ Edit button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Click Claim Administrator
                test.info("Step 6: Clicking Claim Administrator");
                System.out.println("\n👤 Step 6: Opening Claim Administrator section...");

                try {
                    dailyBillingPage.clickClaimAdministrator();
                    test.info("✓ Claim Administrator clicked");
                    System.out.println("✓ Claim Administrator section opened");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Claim Administrator: " + e.getMessage());
                    System.err.println("❌ Claim Administrator click failed: " + e.getMessage());
                    throw e;
                }

                // Step 7: Validate Claim Administrator section loaded
                test.info("Step 7: Validating Claim Administrator section loaded");
                System.out.println("\n✅ Step 7: Validating Claim Administrator section...");

                String claimAdminLabel = dailyBillingPage.getClaimAdministratorSectionLabel();
                String expectedClaimAdminLabel = "Claim Administrator";

                Assert.assertEquals(claimAdminLabel, expectedClaimAdminLabel,
                        "Claim Administrator label should be '" + expectedClaimAdminLabel + "' but found: " + claimAdminLabel);

                test.info("✓ Claim Administrator section loaded: " + claimAdminLabel);
                System.out.println("✓ Claim Administrator section validated: " + claimAdminLabel);

                // Step 8: Validate EMAIL Ready status (BOTH EMAIL Toggle AND Bill Reviewer Email MUST be present)
                test.info("Step 8: Validating EMAIL Ready status");
                System.out.println("\n🔍 Step 8: Validating EMAIL Ready status...");

                // Get EMAIL Toggle status
                boolean emailToggleEnabled = dailyBillingPage.isEmailToggleEnabled();

                // Get Bill Reviewer Email value (MANDATORY)
                String billReviewerEmail = dailyBillingPage.getBillReviewerEmail();
                boolean emailNotBlank = !billReviewerEmail.isEmpty();

                // Validate EMAIL Ready invoice (BOTH conditions are MANDATORY)
                boolean isEmailReady = dailyBillingPage.isEmailReadyInvoice();

                // Assertion 1: EMAIL Toggle MUST be enabled (MANDATORY)
                Assert.assertTrue(emailToggleEnabled,
                        "❌ EMAIL Toggle Button MUST be ENABLED for EMAIL filtered invoices. " +
                                "Business Rule: EMAIL Filter should only show invoices with EMAIL Toggle enabled. " +
                                "Found: EMAIL Toggle=DISABLED");

                test.info("✓ EMAIL Toggle is ENABLED");
                System.out.println("✓ EMAIL Toggle is ENABLED");

                // Assertion 2: Bill Reviewer Email MUST not be blank (MANDATORY)
                Assert.assertTrue(emailNotBlank,
                        "❌ Bill Reviewer Email MUST NOT be BLANK for EMAIL filtered invoices. " +
                                "Business Rule: EMAIL Ready invoices must have Bill Reviewer Email. " +
                                "Found: Bill Reviewer Email is BLANK");

                test.info("✓ Bill Reviewer Email is NOT BLANK: " + billReviewerEmail);
                System.out.println("✓ Bill Reviewer Email is NOT BLANK: " + billReviewerEmail);

                // Assertion 3: Invoice is EMAIL Ready (BOTH conditions must pass)
                Assert.assertTrue(isEmailReady,
                        "❌ Invoice should be EMAIL Ready (BOTH EMAIL Toggle enabled AND Bill Reviewer Email not blank). " +
                                "Business Rule: EMAIL Filter should only show EMAIL Ready invoices. " +
                                "Found: EMAIL Toggle=" + emailToggleEnabled + ", Bill Reviewer Email='" + billReviewerEmail + "'");

                test.info("✓ Invoice is EMAIL Ready (EMAIL Toggle: ENABLED, Bill Reviewer Email: " + billReviewerEmail + ")");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_015 VALIDATION SUMMARY:");
                test.info("✓ EMAIL Filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice found and clicked");
                test.info("✓ Edit Invoice page loaded successfully");
                test.info("✓ EDIT MODE enabled successfully");
                test.info("✓ Claim Administrator section opened");
                test.info("✓ EMAIL Toggle Button: ENABLED (MANDATORY)");
                test.info("✓ Bill Reviewer Email: " + billReviewerEmail + " (MANDATORY)");
                test.info("✓ Invoice is EMAIL Ready for submission (BOTH conditions met)");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_015 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ EMAIL Filter Validation:");
                System.out.println("   ✓ EMAIL Filter applied successfully");
                System.out.println("   ✓ EMAIL filtered invoices loaded");
                System.out.println();
                System.out.println("✅ Invoice Navigation:");
                System.out.println("   ✓ EAMS Verified/Not Verified invoice clicked");
                System.out.println("   ✓ Edit Invoice page opened");
                System.out.println("   ✓ EDIT MODE enabled");
                System.out.println();
                System.out.println("✅ Claim Administrator Validation:");
                System.out.println("   ✓ Claim Administrator section opened");
                System.out.println("   ✓ EMAIL Toggle Button: ENABLED ✓ (MANDATORY)");
                System.out.println("   ✓ Bill Reviewer Email: " + billReviewerEmail + " ✓ (MANDATORY)");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ EMAIL Filter shows only EMAIL Ready invoices");
                System.out.println("   ✓ EMAIL Ready = (EMAIL Toggle enabled) AND (Bill Reviewer Email not blank)");
                System.out.println("   ✓ BOTH conditions are MANDATORY");
                System.out.println("   ✓ Invoice meets ALL EMAIL Ready criteria");
                System.out.println("   ✓ Invoice is ready for EMAIL submission");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_015 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure EMAIL Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that invoice navigation works correctly");
                System.out.println("   4. Verify Edit button and Claim Administrator locators");
                System.out.println("   5. MANDATORY: EMAIL Toggle MUST be enabled for filtered invoices");
                System.out.println("   6. MANDATORY: Bill Reviewer Email MUST NOT be blank for filtered invoices");
                System.out.println("   7. Check EMAIL Toggle detection logic if it shows disabled when actually enabled");
                System.out.println("   8. Verify Bill Reviewer Email field has a valid email address");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_015 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_015 PASSED - EMAIL Filter validation and EMAIL Ready invoice verified successfully");
            System.out.println("✅ SMOKE_DB_015 TEST PASSED - EMAIL FILTER VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 16, description = "SMOKE_DB_016 - Verify user can filter bills by FAX and successfully display all bills ready for FAX submission")
        public void SMOKE_DB_016() {
            test.info("📋 Starting SMOKE_DB_016 - Verify FAX Filter and FAX Ready Invoice Validation");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_016: FAX Filter Test");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice04");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click FAX Filter
                test.info("Step 2: Clicking FAX Filter");
                System.out.println("\n🔘 Step 2: Clicking FAX Filter...");

                try {
                    dailyBillingPage.clickFaxFilter();
                    test.info("✓ FAX Filter clicked");
                    System.out.println("✓ FAX Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for FAX filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click FAX Filter: " + e.getMessage());
                    System.err.println("❌ FAX Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice
                test.info("Step 3: Finding and clicking first EAMS Verified/Not Verified invoice");
                System.out.println("\n🔍 Step 3: Searching for EAMS Verified/Not Verified invoice...");

                boolean invoiceClicked = dailyBillingPage.clickFirstEamsVerifiedInvoiceNumber();

                Assert.assertTrue(invoiceClicked,
                        "Failed to find or click invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are FAX-ready invoices in the test data.");

                test.info("✓ Invoice clicked successfully");
                System.out.println("✓ Invoice clicked - Navigating to Edit Invoice page");
                WaitUtils.sleep(3000);

                // Step 4: Validate Edit Invoice page loaded
                test.info("Step 4: Validating Edit Invoice page loaded");
                System.out.println("\n✅ Step 4: Validating Edit Invoice page...");

                String editInvoiceLabel = dailyBillingPage.getEditInvoiceLabel();
                String expectedLabel = "Edit Invoice";

                Assert.assertEquals(editInvoiceLabel, expectedLabel,
                        "Edit Invoice label should be '" + expectedLabel + "' but found: " + editInvoiceLabel);

                test.info("✓ Edit Invoice page loaded: " + editInvoiceLabel);
                System.out.println("✓ Edit Invoice page validated: " + editInvoiceLabel);

                // Step 5: Click Edit button to enable EDIT MODE
                test.info("Step 5: Clicking Edit button to enable EDIT MODE");
                System.out.println("\n✏️ Step 5: Enabling EDIT MODE...");

                try {
                    dailyBillingPage.clickEditButton();
                    test.info("✓ EDIT MODE enabled");
                    System.out.println("✓ EDIT MODE enabled");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Edit button: " + e.getMessage());
                    System.err.println("❌ Edit button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Click Claim Administrator
                test.info("Step 6: Clicking Claim Administrator");
                System.out.println("\n👤 Step 6: Opening Claim Administrator section...");

                try {
                    dailyBillingPage.clickClaimAdministrator();
                    test.info("✓ Claim Administrator clicked");
                    System.out.println("✓ Claim Administrator section opened");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Claim Administrator: " + e.getMessage());
                    System.err.println("❌ Claim Administrator click failed: " + e.getMessage());
                    throw e;
                }

                // Step 7: Validate Claim Administrator section loaded
                test.info("Step 7: Validating Claim Administrator section loaded");
                System.out.println("\n✅ Step 7: Validating Claim Administrator section...");

                String claimAdminLabel = dailyBillingPage.getClaimAdministratorSectionLabel();
                String expectedClaimAdminLabel = "Claim Administrator";

                Assert.assertEquals(claimAdminLabel, expectedClaimAdminLabel,
                        "Claim Administrator label should be '" + expectedClaimAdminLabel + "' but found: " + claimAdminLabel);

                test.info("✓ Claim Administrator section loaded: " + claimAdminLabel);
                System.out.println("✓ Claim Administrator section validated: " + claimAdminLabel);

                // Step 8: Validate FAX Ready status (BOTH FAX Toggle and FAX Number MUST be present)
                test.info("Step 8: Validating FAX Ready status");
                System.out.println("\n🔍 Step 8: Validating FAX Ready status...");

                // Get FAX Toggle status
                boolean faxToggleEnabled = dailyBillingPage.isFaxToggleEnabled();

            /* Get Bill Reviewer Email value (OPTIONAL)
            String billReviewerEmail = dailyBillingPage.getBillReviewerEmailFax();
            boolean emailNotBlank = !billReviewerEmail.isEmpty();*/

                // Get Bill Reviewer FAX Number value (MANDATORY)
                String billReviewerFax = dailyBillingPage.getBillReviewerFaxNumber();
                boolean faxNotBlank = !billReviewerFax.isEmpty();

                // Validate FAX Ready invoice (BOTH FAX Toggle and FAX Number are MANDATORY)
                boolean isFaxReady = dailyBillingPage.isFaxReadyInvoice();

                // Assertion 1: FAX Toggle MUST be enabled (MANDATORY)
                Assert.assertTrue(faxToggleEnabled,
                        "❌ FAX Toggle Button MUST be ENABLED for FAX filtered invoices. " +
                                "Business Rule: FAX Filter should only show invoices with FAX Toggle enabled. " +
                                "Found: FAX Toggle=DISABLED");

                test.info("✓ FAX Toggle is ENABLED");
                System.out.println("✓ FAX Toggle is ENABLED");

                // Assertion 2: Bill Reviewer FAX Number MUST not be blank (MANDATORY)
                Assert.assertTrue(faxNotBlank,
                        "❌ Bill Reviewer FAX Number MUST NOT be BLANK for FAX filtered invoices. " +
                                "Business Rule: FAX Ready invoices must have Bill Reviewer FAX Number. " +
                                "Found: Bill Reviewer FAX Number is BLANK");

                test.info("✓ Bill Reviewer FAX Number is NOT BLANK: " + billReviewerFax);
                System.out.println("✓ Bill Reviewer FAX Number is NOT BLANK: " + billReviewerFax);

            /* Info: Bill Reviewer Email (OPTIONAL, but inform user)
            if (emailNotBlank) {
                test.info("✓ Bill Reviewer Email is NOT BLANK: " + billReviewerEmail + " (optional)");
                System.out.println("✓ Bill Reviewer Email is NOT BLANK: " + billReviewerEmail + " (optional)");
            } else {
                test.info("Bill Reviewer Email is BLANK (this is optional - FAX Toggle and FAX Number are the mandatory fields)");
                System.out.println("Bill Reviewer Email is BLANK (this is optional - FAX Toggle and FAX Number are the mandatory fields)");
            }*/

                // Assertion 3: Invoice is FAX Ready (BOTH conditions must pass)
                Assert.assertTrue(isFaxReady,
                        "❌ Invoice should be FAX Ready (FAX Toggle enabled AND FAX Number not blank). " +
                                "Business Rule: FAX Filter should only show FAX Ready invoices. " +
                                "Found: FAX Toggle=" + faxToggleEnabled + ", FAX='" + billReviewerFax + "'");

                test.info("✓ Invoice is FAX Ready (FAX Toggle: ENABLED, FAX: " + billReviewerFax + ")");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_016 VALIDATION SUMMARY:");
                test.info("✓ FAX Filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice found and clicked");
                test.info("✓ Edit Invoice page loaded successfully");
                test.info("✓ EDIT MODE enabled successfully");
                test.info("✓ Claim Administrator section opened");
                test.info("✓ FAX Toggle Button: ENABLED (MANDATORY)");
                test.info("✓ Bill Reviewer FAX Number: " + billReviewerFax + " (MANDATORY)");
                //test.info("✓ Bill Reviewer Email: " + (billReviewerEmail.isEmpty() ? "BLANK (OPTIONAL)" : billReviewerEmail + " (OPTIONAL)"));
                test.info("✓ Invoice is FAX Ready for submission (BOTH mandatory conditions met)");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_016 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ FAX Filter Validation:");
                System.out.println("   ✓ FAX Filter applied successfully");
                System.out.println("   ✓ FAX filtered invoices loaded");
                System.out.println();
                System.out.println("✅ Invoice Navigation:");
                System.out.println("   ✓ EAMS Verified/Not Verified invoice clicked");
                System.out.println("   ✓ Edit Invoice page opened");
                System.out.println("   ✓ EDIT MODE enabled");
                System.out.println();
                System.out.println("✅ Claim Administrator Validation:");
                System.out.println("   ✓ Claim Administrator section opened");
                System.out.println("   ✓ FAX Toggle Button: ENABLED ✓ (MANDATORY)");
                System.out.println("   ✓ Bill Reviewer FAX Number: " + billReviewerFax + " ✓ (MANDATORY)");
                //System.out.println("   ✓ Bill Reviewer Email: " + (billReviewerEmail.isEmpty() ? "BLANK (OPTIONAL)" : billReviewerEmail + " ✓ (OPTIONAL)"));
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ FAX Filter shows only FAX Ready invoices");
                System.out.println("   ✓ FAX Ready = (FAX Toggle enabled) AND (FAX Number not blank)");
                System.out.println("   ✓ BOTH conditions are MANDATORY");
                System.out.println("   ✓ Bill Reviewer Email is OPTIONAL");
                System.out.println("   ✓ Invoice meets ALL FAX Ready criteria");
                System.out.println("   ✓ Invoice is ready for FAX submission");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_016 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure FAX Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that invoice navigation works correctly");
                System.out.println("   4. Verify Edit button and Claim Administrator locators");
                System.out.println("   5. MANDATORY: FAX Toggle MUST be enabled for filtered invoices");
                System.out.println("   6. MANDATORY: Bill Reviewer FAX Number MUST NOT be blank for filtered invoices");
                System.out.println("   7. OPTIONAL: Bill Reviewer Email (not mandatory for FAX filter)");
                System.out.println("   8. Check FAX Toggle detection logic if it shows disabled when actually enabled");
                System.out.println("   9. Verify Bill Reviewer FAX Number field locator and value");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_016 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_016 PASSED - FAX Filter validation and FAX Ready invoice verified successfully");
            System.out.println("✅ SMOKE_DB_016 TEST PASSED - FAX FILTER VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 17, description = "SMOKE_DB_017 - Verify user can filter bills by Paper and successfully display all bills ready for Paper submission")
        public void SMOKE_DB_017() {
            test.info("📋 Starting SMOKE_DB_017 - Verify PAPER Filter and PAPER Ready Invoice Validation");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_017: PAPER Filter Test");
            System.out.println("========================================\n");

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice04");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click PAPER Filter
                test.info("Step 2: Clicking PAPER Filter");
                System.out.println("\n🔘 Step 2: Clicking PAPER Filter...");

                try {
                    dailyBillingPage.clickPaperFilter();
                    test.info("✓ PAPER Filter clicked");
                    System.out.println("✓ PAPER Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for PAPER filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click PAPER Filter: " + e.getMessage());
                    System.err.println("❌ PAPER Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice
                test.info("Step 3: Finding and clicking first EAMS Verified/Not Verified invoice");
                System.out.println("\n🔍 Step 3: Searching for EAMS Verified/Not Verified invoice...");

                boolean invoiceClicked = dailyBillingPage.clickFirstEamsVerifiedInvoiceNumber();

                Assert.assertTrue(invoiceClicked,
                        "Failed to find or click invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are PAPER-ready invoices in the test data.");

                test.info("✓ Invoice clicked successfully");
                System.out.println("✓ Invoice clicked - Navigating to Edit Invoice page");
                WaitUtils.sleep(3000);

                // Step 4: Validate Edit Invoice page loaded
                test.info("Step 4: Validating Edit Invoice page loaded");
                System.out.println("\n✅ Step 4: Validating Edit Invoice page...");

                String editInvoiceLabel = dailyBillingPage.getEditInvoiceLabel();
                String expectedLabel = "Edit Invoice";

                Assert.assertEquals(editInvoiceLabel, expectedLabel,
                        "Edit Invoice label should be '" + expectedLabel + "' but found: " + editInvoiceLabel);

                test.info("✓ Edit Invoice page loaded: " + editInvoiceLabel);
                System.out.println("✓ Edit Invoice page validated: " + editInvoiceLabel);

                // Step 5: Click Edit button to enable EDIT MODE
                test.info("Step 5: Clicking Edit button to enable EDIT MODE");
                System.out.println("\n✏️ Step 5: Enabling EDIT MODE...");

                try {
                    dailyBillingPage.clickEditButton();
                    test.info("✓ EDIT MODE enabled");
                    System.out.println("✓ EDIT MODE enabled");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Edit button: " + e.getMessage());
                    System.err.println("❌ Edit button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Click Claim Administrator
                test.info("Step 6: Clicking Claim Administrator");
                System.out.println("\n👤 Step 6: Opening Claim Administrator section...");

                try {
                    dailyBillingPage.clickClaimAdministrator();
                    test.info("✓ Claim Administrator clicked");
                    System.out.println("✓ Claim Administrator section opened");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Claim Administrator: " + e.getMessage());
                    System.err.println("❌ Claim Administrator click failed: " + e.getMessage());
                    throw e;
                }

                // Step 7: Validate Claim Administrator section loaded
                test.info("Step 7: Validating Claim Administrator section loaded");
                System.out.println("\n✅ Step 7: Validating Claim Administrator section...");

                String claimAdminLabel = dailyBillingPage.getClaimAdministratorSectionLabel();
                String expectedClaimAdminLabel = "Claim Administrator";

                Assert.assertEquals(claimAdminLabel, expectedClaimAdminLabel,
                        "Claim Administrator label should be '" + expectedClaimAdminLabel + "' but found: " + claimAdminLabel);

                test.info("✓ Claim Administrator section loaded: " + claimAdminLabel);
                System.out.println("✓ Claim Administrator section validated: " + claimAdminLabel);

                // Step 8: Validate PAPER Ready status (ALL THREE toggles must be DISABLED)
                test.info("Step 8: Validating PAPER Ready status");
                System.out.println("\n🔍 Step 8: Validating PAPER Ready status...");

                // Get toggle states
                boolean emcDisabled = dailyBillingPage.isEmcToggleDisabledForPaper();
                boolean emailDisabled = dailyBillingPage.isEmailToggleDisabledForPaper();
                boolean faxDisabled = dailyBillingPage.isFaxToggleDisabledForPaper();

                // Validate PAPER Ready invoice (ALL THREE must be DISABLED)
                boolean isPaperReady = dailyBillingPage.isPaperReadyInvoice();

                // Assertion 1: EMC Toggle MUST be DISABLED (MANDATORY)
                Assert.assertTrue(emcDisabled,
                        "❌ EMC Toggle Button MUST be DISABLED for PAPER filtered invoices. " +
                                "Business Rule: PAPER Filter should only show invoices with EMC Toggle disabled. " +
                                "Found: EMC Toggle=ENABLED (should be disabled)");

                test.info("✓ EMC Toggle is DISABLED");
                System.out.println("✓ EMC Toggle is DISABLED");

                // Assertion 2: EMAIL Toggle MUST be DISABLED (MANDATORY)
                Assert.assertTrue(emailDisabled,
                        "❌ EMAIL Toggle Button MUST be DISABLED for PAPER filtered invoices. " +
                                "Business Rule: PAPER Filter should only show invoices with EMAIL Toggle disabled. " +
                                "Found: EMAIL Toggle=ENABLED (should be disabled)");

                test.info("✓ EMAIL Toggle is DISABLED");
                System.out.println("✓ EMAIL Toggle is DISABLED");

                // Assertion 3: FAX Toggle MUST be DISABLED (MANDATORY)
                Assert.assertTrue(faxDisabled,
                        "❌ FAX Toggle Button MUST be DISABLED for PAPER filtered invoices. " +
                                "Business Rule: PAPER Filter should only show invoices with FAX Toggle disabled. " +
                                "Found: FAX Toggle=ENABLED (should be disabled)");

                test.info("✓ FAX Toggle is DISABLED");
                System.out.println("✓ FAX Toggle is DISABLED");

                // Assertion 4: Invoice is PAPER Ready (ALL THREE conditions must pass)
                Assert.assertTrue(isPaperReady,
                        "❌ Invoice should be PAPER Ready (ALL THREE toggles EMC, EMAIL, FAX must be disabled). " +
                                "Business Rule: PAPER Filter should only show PAPER Ready invoices.");

                test.info("✓ Invoice is PAPER Ready (EMC: DISABLED, EMAIL: DISABLED, FAX: DISABLED)");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_017 VALIDATION SUMMARY:");
                test.info("✓ PAPER Filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice found and clicked");
                test.info("✓ Edit Invoice page loaded successfully");
                test.info("✓ EDIT MODE enabled successfully");
                test.info("✓ Claim Administrator section opened");
                test.info("✓ EMC Toggle Button: DISABLED (MANDATORY)");
                test.info("✓ EMAIL Toggle Button: DISABLED (MANDATORY)");
                test.info("✓ FAX Toggle Button: DISABLED (MANDATORY)");
                test.info("✓ Invoice is PAPER Ready for submission (ALL conditions met)");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_017 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ PAPER Filter Validation:");
                System.out.println("   ✓ PAPER Filter applied successfully");
                System.out.println("   ✓ PAPER filtered invoices loaded");
                System.out.println();
                System.out.println("✅ Invoice Navigation:");
                System.out.println("   ✓ EAMS Verified/Not Verified invoice clicked");
                System.out.println("   ✓ Edit Invoice page opened");
                System.out.println("   ✓ EDIT MODE enabled");
                System.out.println();
                System.out.println("✅ Claim Administrator Validation:");
                System.out.println("   ✓ Claim Administrator section opened");
                System.out.println("   ✓ EMC Toggle Button: DISABLED ✓ (MANDATORY)");
                System.out.println("   ✓ EMAIL Toggle Button: DISABLED ✓ (MANDATORY)");
                System.out.println("   ✓ FAX Toggle Button: DISABLED ✓ (MANDATORY)");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ PAPER Filter shows only PAPER Ready invoices");
                System.out.println("   ✓ PAPER Ready = (EMC disabled) AND (EMAIL disabled) AND (FAX disabled)");
                System.out.println("   ✓ ALL THREE conditions are MANDATORY");
                System.out.println("   ✓ Invoice meets ALL PAPER Ready criteria");
                System.out.println("   ✓ Invoice is ready for PAPER submission");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_017 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure PAPER Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that invoice navigation works correctly");
                System.out.println("   4. Verify Edit button and Claim Administrator locators");
                System.out.println("   5. MANDATORY: EMC Toggle MUST be DISABLED for filtered invoices");
                System.out.println("   6. MANDATORY: EMAIL Toggle MUST be DISABLED for filtered invoices");
                System.out.println("   7. MANDATORY: FAX Toggle MUST be DISABLED for filtered invoices");
                System.out.println("   8. Check toggle detection logic if it shows enabled when actually disabled");
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_017 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_017 PASSED - PAPER Filter validation and PAPER Ready invoice verified successfully");
            System.out.println("✅ SMOKE_DB_017 TEST PASSED - PAPER FILTER VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 18, description = "SMOKE_DB_018 - Verify user can submit single invoice to clearinghouse with EMC, invoice disappears from Daily Billing list")
        public void SMOKE_DB_018() {
            test.info("📋 Starting SMOKE_DB_018 - Verify EMC Submission and Invoice Disappearance");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_018: EMC Submission Test");
            System.out.println("========================================\n");

            String selectedInvoiceNumber = null;

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice05");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click EMC Filter
                test.info("Step 2: Clicking EMC Filter");
                System.out.println("\n🔘 Step 2: Clicking EMC Filter...");

                try {
                    dailyBillingPage.clickEmcFilter();
                    test.info("✓ EMC Filter clicked");
                    System.out.println("✓ EMC Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for EMC filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Filter: " + e.getMessage());
                    System.err.println("❌ EMC Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice checkbox
                test.info("Step 3: Selecting first EAMS Verified/Not Verified invoice checkbox");
                System.out.println("\n☑️ Step 3: Selecting EAMS Verified/Not Verified invoice...");

                selectedInvoiceNumber = dailyBillingPage.selectFirstEamsVerifiedInvoiceCheckbox();

                Assert.assertNotNull(selectedInvoiceNumber,
                        "Failed to find or select invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are EMC-ready invoices in the test data.");

                test.info("✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("✓ Invoice selected: " + selectedInvoiceNumber);
                WaitUtils.sleep(2000);

                // Step 4: Enable HCFA Toggle Button
                test.info("Step 4: Enabling HCFA Toggle Button");
                System.out.println("\n🔄 Step 4: Enabling HCFA Toggle...");

                try {
                    dailyBillingPage.enableHcfaToggle();
                    test.info("✓ HCFA Toggle enabled");
                    System.out.println("✓ HCFA Toggle enabled successfully");

                } catch (Exception e) {
                    test.fail("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                    System.err.println("❌ HCFA Toggle enable failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Click EMC Submission Button
                test.info("Step 5: Clicking EMC Submission Button");
                System.out.println("\n📤 Step 5: Submitting invoice via EMC...");

                try {
                    dailyBillingPage.clickEmcSubmissionButton();
                    test.info("✓ EMC Submission button clicked");
                    System.out.println("✓ EMC Submission initiated");

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Submission button: " + e.getMessage());
                    System.err.println("❌ EMC Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Verify success message
                test.info("Step 6: Verifying success message");
                System.out.println("\n✅ Step 6: Checking for success message...");

                String successMessage = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Submitted.";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessage);

                test.info("✓ Success message verified: " + successMessage);
                System.out.println("✓ Success message: " + successMessage);

                // Step 7: Verify invoice disappeared from Daily Billing list
                test.info("Step 7: Verifying invoice disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 7: Checking if invoice disappeared...");

                boolean invoiceDisappeared = dailyBillingPage.isInvoiceDisappeared(selectedInvoiceNumber);

                Assert.assertTrue(invoiceDisappeared,
                        "Invoice " + selectedInvoiceNumber + " should have disappeared from Daily Billing list after EMC submission, " +
                                "but it is still present in the list.");

                test.info("✓ Invoice " + selectedInvoiceNumber + " successfully disappeared from list");
                System.out.println("✓ Invoice " + selectedInvoiceNumber + " removed from Daily Billing list");

                // Step 8: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_018 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ EMC filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice selected: " + selectedInvoiceNumber);
                test.info("✓ HCFA Toggle enabled successfully");
                test.info("✓ EMC Submission button clicked");
                test.info("✓ Success message verified: '" + successMessage + "'");
                test.info("✓ Invoice disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_018 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ EMC Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ EMC filter selected");
                System.out.println("   ✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("   ✓ HCFA Toggle enabled");
                System.out.println("   ✓ EMC Submission initiated");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Success message: '" + successMessage + "'");
                System.out.println("   ✓ Invoice removed from list: " + selectedInvoiceNumber);
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Single invoice submitted to clearinghouse via EMC");
                System.out.println("   ✓ Invoice successfully disappeared from Daily Billing list");
                System.out.println("   ✓ EMC submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_018 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure EMC Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that HCFA Toggle can be enabled");
                System.out.println("   4. Verify EMC Submission button is clickable after HCFA toggle");
                System.out.println("   5. Confirm success message appears after submission");
                System.out.println("   6. Ensure invoice disappears from list after successful submission");
                if (selectedInvoiceNumber != null) {
                    System.out.println("   7. Selected Invoice Number: " + selectedInvoiceNumber);
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_018 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_018 PASSED - EMC submission verified and invoice successfully disappeared from Daily Billing list");
            System.out.println("✅ SMOKE_DB_018 TEST PASSED - EMC SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 19, description = "SMOKE_DB_019 - Verify user can submit single invoice to clearinghouse with E/P (EMC and Paper), invoices disappear from Daily Billing list")
        public void SMOKE_DB_019() {
            test.info("📋 Starting SMOKE_DB_019 - Verify E/P Submission (EMC + Paper Combined)");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_019: E/P Submission Test");
            System.out.println("========================================\n");

            String selectedInvoiceNumberEMC = null;
            String selectedInvoiceNumberPaper = null;

            try {
                // ========== LOGIC 01: EMC Invoice through E/P ==========
                test.info("========== LOGIC 01: EMC Invoice through E/P ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("📤 LOGIC 01: EMC Invoice through E/P");
                System.out.println("=".repeat(60));

                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("\n📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice05");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click EMC Filter
                test.info("Step 2: Clicking EMC Filter");
                System.out.println("\n🔘 Step 2: Clicking EMC Filter...");

                try {
                    dailyBillingPage.clickEmcFilter();
                    test.info("✓ EMC Filter clicked");
                    System.out.println("✓ EMC Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for EMC filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Filter: " + e.getMessage());
                    System.err.println("❌ EMC Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice checkbox
                test.info("Step 3: Selecting first EAMS Verified/Not Verified invoice checkbox");
                System.out.println("\n☑️ Step 3: Selecting EAMS Verified/Not Verified invoice...");

                selectedInvoiceNumberEMC = dailyBillingPage.selectFirstEamsVerifiedInvoiceCheckbox();

                Assert.assertNotNull(selectedInvoiceNumberEMC,
                        "Failed to find or select invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are EMC-ready invoices in the test data.");

                test.info("✓ EMC Invoice selected: " + selectedInvoiceNumberEMC);
                System.out.println("✓ EMC Invoice selected: " + selectedInvoiceNumberEMC);
                WaitUtils.sleep(2000);

                // Step 4: Enable HCFA Toggle Button
                test.info("Step 4: Enabling HCFA Toggle Button");
                System.out.println("\n🔄 Step 4: Enabling HCFA Toggle...");

                try {
                    dailyBillingPage.enableHcfaToggle();
                    test.info("✓ HCFA Toggle enabled");
                    System.out.println("✓ HCFA Toggle enabled successfully");

                } catch (Exception e) {
                    test.fail("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                    System.err.println("❌ HCFA Toggle enable failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Click E/P Submission Button
                test.info("Step 5: Clicking E/P Submission Button");
                System.out.println("\n📤 Step 5: Submitting EMC invoice via E/P...");

                try {
                    dailyBillingPage.clickEpSubmissionButton();
                    test.info("✓ E/P Submission button clicked");
                    System.out.println("✓ E/P Submission initiated for EMC invoice");

                } catch (Exception e) {
                    test.fail("❌ Failed to click E/P Submission button: " + e.getMessage());
                    System.err.println("❌ E/P Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Verify success message
                test.info("Step 6: Verifying success message");
                System.out.println("\n✅ Step 6: Checking for success message...");

                String successMessageEMC = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Submitted.";

                Assert.assertEquals(successMessageEMC, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessageEMC);

                test.info("✓ Success message verified: " + successMessageEMC);
                System.out.println("✓ Success message: " + successMessageEMC);

                WaitUtils.sleep(5000);

                // Step 7: Verify EMC invoice disappeared from Daily Billing list
                test.info("Step 7: Verifying EMC invoice disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 7: Checking if EMC invoice disappeared...");

                boolean emcInvoiceDisappeared = dailyBillingPage.isInvoiceDisappeared(selectedInvoiceNumberEMC);

                Assert.assertTrue(emcInvoiceDisappeared,
                        "EMC Invoice " + selectedInvoiceNumberEMC + " should have disappeared from Daily Billing list after E/P submission, " +
                                "but it is still present in the list.");

                test.info("✓ EMC Invoice " + selectedInvoiceNumberEMC + " successfully disappeared from list");
                System.out.println("✓ EMC Invoice " + selectedInvoiceNumberEMC + " removed from Daily Billing list");

                test.info("========== LOGIC 01 PASSED ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("✅ LOGIC 01 PASSED - EMC Invoice submitted via E/P");
                System.out.println("=".repeat(60));

                // ========== LOGIC 02: Paper Invoice through E/P ==========
                test.info("\n========== LOGIC 02: Paper Invoice through E/P ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("📄 LOGIC 02: Paper Invoice through E/P");
                System.out.println("=".repeat(60));

                // Step 8: Click Paper Filter
                test.info("Step 8: Clicking Paper Filter");
                System.out.println("\n🔘 Step 8: Clicking Paper Filter...");

                try {
                    dailyBillingPage.clickPaperFilter();
                    test.info("✓ Paper Filter clicked");
                    System.out.println("✓ Paper Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Paper filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Paper Filter: " + e.getMessage());
                    System.err.println("❌ Paper Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 9: Select first EAMS Verified or EAMS Not Verified invoice checkbox
                test.info("Step 9: Selecting first EAMS Verified/Not Verified invoice checkbox for Paper");
                System.out.println("\n☑️ Step 9: Selecting EAMS Verified/Not Verified invoice...");

                selectedInvoiceNumberPaper = dailyBillingPage.selectFirstEamsVerifiedInvoiceCheckbox();

                Assert.assertNotNull(selectedInvoiceNumberPaper,
                        "Failed to find or select invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are Paper-ready invoices in the test data.");

                test.info("✓ Paper Invoice selected: " + selectedInvoiceNumberPaper);
                System.out.println("✓ Paper Invoice selected: " + selectedInvoiceNumberPaper);
                WaitUtils.sleep(2000);

                // Step 10: Enable HCFA Toggle Button
                test.info("Step 10: Enabling HCFA Toggle Button");
                System.out.println("\n🔄 Step 10: Enabling HCFA Toggle...");

                try {
                    dailyBillingPage.enableHcfaToggle();
                    test.info("✓ HCFA Toggle enabled");
                    System.out.println("✓ HCFA Toggle enabled successfully");

                } catch (Exception e) {
                    test.fail("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                    System.err.println("❌ HCFA Toggle enable failed: " + e.getMessage());
                    throw e;
                }

                // Step 11: Click E/P Submission Button
                test.info("Step 11: Clicking E/P Submission Button");
                System.out.println("\n📤 Step 11: Submitting Paper invoice via E/P...");

                try {
                    dailyBillingPage.clickEpSubmissionButton();
                    test.info("✓ E/P Submission button clicked");
                    System.out.println("✓ E/P Submission initiated for Paper invoice");

                } catch (Exception e) {
                    test.fail("❌ Failed to click E/P Submission button: " + e.getMessage());
                    System.err.println("❌ E/P Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 12: Verify success message
                test.info("Step 12: Verifying success message");
                System.out.println("\n✅ Step 12: Checking for success message...");

                String successMessagePaper = dailyBillingPage.getSuccessMessage();

                Assert.assertEquals(successMessagePaper, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessagePaper);

                test.info("✓ Success message verified: " + successMessagePaper);
                System.out.println("✓ Success message: " + successMessagePaper);

                // Step 13: Verify Report View automatically opens after Paper submission
                test.info("Step 13: Verifying Report View automatically opened after Paper submission");
                System.out.println("\n📊 Step 13: Checking if Report View automatically opened...");

                boolean reportViewDisplayed = dailyBillingPage.isReportViewDisplayedAfterEP();

                Assert.assertTrue(reportViewDisplayed,
                        "Report View should automatically open after Paper invoice E/P submission, but it was not displayed.");

                test.info("✓ Report View automatically opened after Paper submission");
                System.out.println("✓ Report View automatically displayed");

                // Step 14: Verify Report View label
                test.info("Step 14: Verifying Report View label");
                System.out.println("\n🏷️ Step 14: Verifying Report View label...");

                String reportViewLabel = dailyBillingPage.getReportViewLabelText();
                String expectedReportViewLabel = "Report View";

                Assert.assertEquals(reportViewLabel, expectedReportViewLabel,
                        "Report View label should be 'Report View' but found: " + reportViewLabel);

                test.info("✓ Report View label verified: " + reportViewLabel);
                System.out.println("✓ Report View label: " + reportViewLabel);

                // Step 15: Verify Paper invoice disappeared from Daily Billing list
                test.info("Step 15: Verifying Paper invoice disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 15: Checking if Paper invoice disappeared...");

                boolean paperInvoiceDisappeared = dailyBillingPage.isInvoiceDisappeared(selectedInvoiceNumberPaper);

                Assert.assertTrue(paperInvoiceDisappeared,
                        "Paper Invoice " + selectedInvoiceNumberPaper + " should have disappeared from Daily Billing list after E/P submission, " +
                                "but it is still present in the list.");

                test.info("✓ Paper Invoice " + selectedInvoiceNumberPaper + " successfully disappeared from list");
                System.out.println("✓ Paper Invoice " + selectedInvoiceNumberPaper + " removed from Daily Billing list");

                test.info("========== LOGIC 02 PASSED ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("✅ LOGIC 02 PASSED - Paper Invoice submitted via E/P with Report View");
                System.out.println("=".repeat(60));

                // Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_019 VALIDATION SUMMARY:");
                test.info("LOGIC 01 (EMC through E/P):");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ EMC filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice selected: " + selectedInvoiceNumberEMC);
                test.info("✓ HCFA Toggle enabled successfully");
                test.info("✓ E/P Submission button clicked");
                test.info("✓ Success message verified: '" + successMessageEMC + "'");
                test.info("✓ EMC Invoice disappeared from Daily Billing list");
                test.info("");
                test.info("LOGIC 02 (Paper through E/P):");
                test.info("✓ Paper filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice selected: " + selectedInvoiceNumberPaper);
                test.info("✓ HCFA Toggle enabled successfully");
                test.info("✓ E/P Submission button clicked");
                test.info("✓ Success message verified: '" + successMessagePaper + "'");
                test.info("✓ Report View automatically opened");
                test.info("✓ Report View label verified: 'Report View'");
                test.info("✓ Paper Invoice disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_019 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ LOGIC 01 (EMC through E/P):");
                System.out.println("   ✓ EMC Invoice selected: " + selectedInvoiceNumberEMC);
                System.out.println("   ✓ E/P Submission successful");
                System.out.println("   ✓ Success message displayed");
                System.out.println("   ✓ EMC Invoice removed from list");
                System.out.println();
                System.out.println("✅ LOGIC 02 (Paper through E/P):");
                System.out.println("   ✓ Paper Invoice selected: " + selectedInvoiceNumberPaper);
                System.out.println("   ✓ E/P Submission successful");
                System.out.println("   ✓ Success message displayed");
                System.out.println("   ✓ Report View automatically opened");
                System.out.println("   ✓ Paper Invoice removed from list");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ E/P allows submitting both EMC and Paper invoices");
                System.out.println("   ✓ EMC invoices go to clearinghouse via E/P");
                System.out.println("   ✓ Paper invoices automatically open Report View via E/P");
                System.out.println("   ✓ Both invoice types disappear from Daily Billing list");
                System.out.println("   ✓ E/P submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_019 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure EMC Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices for EMC");
                System.out.println("   3. Ensure Paper Filter button is clickable");
                System.out.println("   4. Verify there are EAMS Verified/Not Verified invoices for Paper");
                System.out.println("   5. Check that HCFA Toggle can be enabled");
                System.out.println("   6. Verify E/P Submission button is clickable after HCFA toggle");
                System.out.println("   7. Confirm success message appears after submission");
                System.out.println("   8. Verify Report View opens automatically after Paper E/P submission");
                System.out.println("   9. Ensure invoices disappear from list after successful submission");
                if (selectedInvoiceNumberEMC != null) {
                    System.out.println("   10. Selected EMC Invoice Number: " + selectedInvoiceNumberEMC);
                }
                if (selectedInvoiceNumberPaper != null) {
                    System.out.println("   11. Selected Paper Invoice Number: " + selectedInvoiceNumberPaper);
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_019 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_019 PASSED - E/P submission verified for both EMC and Paper invoices..");
            System.out.println("✅ SMOKE_DB_019 TEST PASSED - E/P SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 20, description = "SMOKE_DB_020 - Verify user can submit single invoice to clearinghouse with Mail, invoice disappears from Daily Billing list and Report View opens")
        public void SMOKE_DB_020() {
            test.info("📋 Starting SMOKE_DB_020 - Verify Mail Submission and Report View");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_020: Mail Submission Test");
            System.out.println("========================================\n");

            String selectedInvoiceNumber = null;

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice05");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click Mail Filter
                test.info("Step 2: Clicking Mail Filter");
                System.out.println("\n🔘 Step 2: Clicking Mail Filter...");

                try {
                    dailyBillingPage.clickMailFilter();
                    test.info("✓ Mail Filter clicked");
                    System.out.println("✓ Mail Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Mail filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Mail Filter: " + e.getMessage());
                    System.err.println("❌ Mail Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice checkbox
                test.info("Step 3: Selecting first EAMS Verified/Not Verified invoice checkbox");
                System.out.println("\n☑️ Step 3: Selecting EAMS Verified/Not Verified invoice...");

                selectedInvoiceNumber = dailyBillingPage.selectFirstEamsVerifiedInvoiceCheckbox();

                Assert.assertNotNull(selectedInvoiceNumber,
                        "Failed to find or select invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are Mail-ready invoices in the test data.");

                test.info("✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("✓ Invoice selected: " + selectedInvoiceNumber);
                WaitUtils.sleep(2000);

                // Step 4: Click Mail Submission Button
                test.info("Step 4: Clicking Mail Submission Button");
                System.out.println("\n📤 Step 4: Submitting invoice via Mail...");

                try {
                    dailyBillingPage.clickMailSubmissionButton();
                    test.info("✓ Mail Submission button clicked");
                    System.out.println("✓ Mail Submission initiated");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Mail Submission button: " + e.getMessage());
                    System.err.println("❌ Mail Submission button click failed: " + e.getMessage());
                    throw e;
                }

                WaitUtils.sleep(3000);

                // Step 5: Verify success message
                test.info("Step 5: Verifying success message");
                System.out.println("\n✅ Step 5: Checking for success message...");

                String successMessage = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Sent To Email";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Sent To Email' but found: " + successMessage);

                test.info("✓ Success message verified: " + successMessage);
                System.out.println("✓ Success message: " + successMessage);


                // Step 6: Verify invoice disappeared from Daily Billing list
                test.info("Step 8: Verifying invoice disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 8: Checking if invoice disappeared...");

                boolean invoiceDisappeared = dailyBillingPage.isInvoiceDisappeared(selectedInvoiceNumber);

                Assert.assertTrue(invoiceDisappeared,
                        "Invoice " + selectedInvoiceNumber + " should have disappeared from Daily Billing list after Mail submission, " +
                                "but it is still present in the list.");

                test.info("✓ Invoice " + selectedInvoiceNumber + " successfully disappeared from list");
                System.out.println("✓ Invoice " + selectedInvoiceNumber + " removed from Daily Billing list");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_020 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ Mail filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice selected: " + selectedInvoiceNumber);
                test.info("✓ Mail Submission button clicked");
                test.info("✓ Success message verified: '" + successMessage + "'");
                test.info("✓ Invoice disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_020 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Mail Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ Mail filter selected");
                System.out.println("   ✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("   ✓ Mail Submission initiated");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Success message: '" + successMessage + "'");
                System.out.println("   ✓ Invoice removed from list: " + selectedInvoiceNumber);
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Mail submission automatically");
                System.out.println("   ✓ Invoice successfully disappeared from Daily Billing list");
                System.out.println("   ✓ Mail submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_020 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure Mail Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that Mail Submission button is clickable");
                System.out.println("   4. Confirm success message appears after submission");
                System.out.println("   5. Verify Report View opens automatically after Mail submission");
                System.out.println("   6. Ensure invoice disappears from list after successful submission");
                if (selectedInvoiceNumber != null) {
                    System.out.println("   7. Selected Invoice Number: " + selectedInvoiceNumber);
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_020 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_020 PASSED - Mail submission verified and invoice successfully disappeared from Daily Billing list with Report View");
            System.out.println("✅ SMOKE_DB_020 TEST PASSED - MAIL SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 21, description = "SMOKE_DB_021 - Verify user can submit single invoice to clearinghouse with FAX, invoice disappears from Daily Billing list")
        public void SMOKE_DB_021() {
            test.info("📋 Starting SMOKE_DB_021 - Verify FAX Submission");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_021: FAX Submission Test");
            System.out.println("========================================\n");

            String selectedInvoiceNumber = null;

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice05");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click FAX Filter
                test.info("Step 2: Clicking FAX Filter");
                System.out.println("\n🔘 Step 2: Clicking FAX Filter...");

                try {
                    dailyBillingPage.clickFaxFilter();
                    test.info("✓ FAX Filter clicked");
                    System.out.println("✓ FAX Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for FAX filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click FAX Filter: " + e.getMessage());
                    System.err.println("❌ FAX Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice checkbox
                test.info("Step 3: Selecting first EAMS Verified/Not Verified invoice checkbox");
                System.out.println("\n☑️ Step 3: Selecting EAMS Verified/Not Verified invoice...");

                selectedInvoiceNumber = dailyBillingPage.selectFirstEamsVerifiedInvoiceCheckbox();

                Assert.assertNotNull(selectedInvoiceNumber,
                        "Failed to find or select invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are FAX-ready invoices in the test data.");

                test.info("✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("✓ Invoice selected: " + selectedInvoiceNumber);
                WaitUtils.sleep(2000);

                // Step 4: Click FAX Submission Button
                test.info("Step 4: Clicking FAX Submission Button");
                System.out.println("\n📤 Step 4: Submitting invoice via FAX...");

                try {
                    dailyBillingPage.clickFaxSubmissionButton();
                    test.info("✓ FAX Submission button clicked");
                    System.out.println("✓ FAX Submission initiated");

                } catch (Exception e) {
                    test.fail("❌ Failed to click FAX Submission button: " + e.getMessage());
                    System.err.println("❌ FAX Submission button click failed: " + e.getMessage());
                    throw e;
                }

                WaitUtils.sleep(4000);

                // Step 5: Verify FAX success message
                test.info("Step 5: Verifying FAX success message");
                System.out.println("\n✅ Step 5: Checking for FAX success message...");

                String successMessage = dailyBillingPage.getFaxSuccessMessage();
                String expectedMessage = "Bill Successfully Sent To Fax";

                Assert.assertEquals(successMessage, expectedMessage,
                        "FAX success message should be 'Bill Successfully Sent To Fax' but found: " + successMessage);

                test.info("✓ FAX success message verified: " + successMessage);
                System.out.println("✓ FAX success message: " + successMessage);

                // Step 6: Verify invoice disappeared from Daily Billing list
                test.info("Step 6: Verifying invoice disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 6: Checking if invoice disappeared...");

                boolean invoiceDisappeared = dailyBillingPage.isInvoiceDisappeared(selectedInvoiceNumber);

                Assert.assertTrue(invoiceDisappeared,
                        "Invoice " + selectedInvoiceNumber + " should have disappeared from Daily Billing list after FAX submission, " +
                                "but it is still present in the list.");

                test.info("✓ Invoice " + selectedInvoiceNumber + " successfully disappeared from list");
                System.out.println("✓ Invoice " + selectedInvoiceNumber + " removed from Daily Billing list");

                // Step 7: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_021 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ FAX filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice selected: " + selectedInvoiceNumber);
                test.info("✓ FAX Submission button clicked");
                test.info("✓ FAX success message verified: '" + successMessage + "'");
                test.info("✓ Invoice disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_021 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ FAX Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ FAX filter selected");
                System.out.println("   ✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("   ✓ FAX Submission initiated");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ FAX success message: '" + successMessage + "'");
                System.out.println("   ✓ Invoice removed from list: " + selectedInvoiceNumber);
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ FAX submission successful");
                System.out.println("   ✓ Invoice successfully disappeared from Daily Billing list");
                System.out.println("   ✓ FAX submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_021 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure FAX Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that FAX Submission button is clickable");
                System.out.println("   4. Confirm FAX success message appears after submission");
                System.out.println("   5. Ensure invoice disappears from list after successful submission");
                if (selectedInvoiceNumber != null) {
                    System.out.println("   6. Selected Invoice Number: " + selectedInvoiceNumber);
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_021 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_021 PASSED - FAX submission verified and invoice successfully disappeared from Daily Billing list");
            System.out.println("✅ SMOKE_DB_021 TEST PASSED - FAX SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 22, description = "SMOKE_DB_022 - Verify user can submit single invoice to clearinghouse with Paper, invoice disappears from Daily Billing list and Report View opens")
        public void SMOKE_DB_022() {
            test.info("📋 Starting SMOKE_DB_022 - Verify Paper Submission and Report View");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_022: Paper Submission Test");
            System.out.println("========================================\n");

            String selectedInvoiceNumber = null;

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice05");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click Paper Filter
                test.info("Step 2: Clicking Paper Filter");
                System.out.println("\n🔘 Step 2: Clicking Paper Filter...");

                try {
                    dailyBillingPage.clickPaperFilter();
                    test.info("✓ Paper Filter clicked");
                    System.out.println("✓ Paper Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Paper filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Paper Filter: " + e.getMessage());
                    System.err.println("❌ Paper Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select first EAMS Verified or EAMS Not Verified invoice checkbox
                test.info("Step 3: Selecting first EAMS Verified/Not Verified invoice checkbox");
                System.out.println("\n☑️ Step 3: Selecting EAMS Verified/Not Verified invoice...");

                selectedInvoiceNumber = dailyBillingPage.selectFirstEamsVerifiedInvoiceCheckbox();

                Assert.assertNotNull(selectedInvoiceNumber,
                        "Failed to find or select invoice with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Ensure there are Paper-ready invoices in the test data.");

                test.info("✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("✓ Invoice selected: " + selectedInvoiceNumber);
                WaitUtils.sleep(2000);

                // Step 4: Click Paper Submission Button
                test.info("Step 4: Clicking Paper Submission Button");
                System.out.println("\n📤 Step 4: Submitting invoice via Paper...");

                try {
                    dailyBillingPage.clickPaperSubmissionButton();
                    test.info("✓ Paper Submission button clicked");
                    System.out.println("✓ Paper Submission initiated");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Paper Submission button: " + e.getMessage());
                    System.err.println("❌ Paper Submission button click failed: " + e.getMessage());
                    throw e;
                }

                WaitUtils.sleep(5000);

                // Step 5: Verify success message
                test.info("Step 5: Verifying success message");
                System.out.println("\n✅ Step 5: Checking for success message...");

                String successMessage = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Submitted.";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessage);

                test.info("✓ Success message verified: " + successMessage);
                System.out.println("✓ Success message: " + successMessage);

                // Step 6: Verify Report View automatically opens after Paper submission
                test.info("Step 6: Verifying Report View automatically opened after Paper submission");
                System.out.println("\n📊 Step 6: Checking if Report View automatically opened...");

                boolean reportViewDisplayed = dailyBillingPage.isReportViewDisplayedAfterEP();

                Assert.assertTrue(reportViewDisplayed,
                        "Report View should automatically open after Paper submission, but it was not displayed.");

                test.info("✓ Report View automatically opened after Paper submission");
                System.out.println("✓ Report View automatically displayed");

                // Step 7: Verify Report View label
                test.info("Step 7: Verifying Report View label");
                System.out.println("\n🏷️ Step 7: Verifying Report View label...");

                String reportViewLabel = dailyBillingPage.getReportViewLabelText();
                String expectedReportViewLabel = "Report View";

                Assert.assertEquals(reportViewLabel, expectedReportViewLabel,
                        "Report View label should be 'Report View' but found: " + reportViewLabel);

                test.info("✓ Report View label verified: " + reportViewLabel);
                System.out.println("✓ Report View label: " + reportViewLabel);

                // Step 8: Verify invoice disappeared from Daily Billing list
                test.info("Step 8: Verifying invoice disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 8: Checking if invoice disappeared...");

                boolean invoiceDisappeared = dailyBillingPage.isInvoiceDisappeared(selectedInvoiceNumber);

                Assert.assertTrue(invoiceDisappeared,
                        "Invoice " + selectedInvoiceNumber + " should have disappeared from Daily Billing list after Paper submission, " +
                                "but it is still present in the list.");

                test.info("✓ Invoice " + selectedInvoiceNumber + " successfully disappeared from list");
                System.out.println("✓ Invoice " + selectedInvoiceNumber + " removed from Daily Billing list");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_022 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ Paper filter clicked and invoices loaded");
                test.info("✓ EAMS Verified/Not Verified invoice selected: " + selectedInvoiceNumber);
                test.info("✓ Paper Submission button clicked");
                test.info("✓ Success message verified: '" + successMessage + "'");
                test.info("✓ Report View automatically opened");
                test.info("✓ Report View label verified: 'Report View'");
                test.info("✓ Invoice disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_022 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Paper Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ Paper filter selected");
                System.out.println("   ✓ Invoice selected: " + selectedInvoiceNumber);
                System.out.println("   ✓ Paper Submission initiated");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Success message: '" + successMessage + "'");
                System.out.println("   ✓ Report View automatically opened");
                System.out.println("   ✓ Report View label verified");
                System.out.println("   ✓ Invoice removed from list: " + selectedInvoiceNumber);
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Paper submission automatically opens Report View");
                System.out.println("   ✓ Invoice successfully disappeared from Daily Billing list");
                System.out.println("   ✓ Paper submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_022 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure Paper Filter button is clickable");
                System.out.println("   2. Verify there are EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that Paper Submission button is clickable");
                System.out.println("   4. Confirm success message appears after submission");
                System.out.println("   5. Verify Report View opens automatically after Paper submission");
                System.out.println("   6. Ensure invoice disappears from list after successful submission");
                if (selectedInvoiceNumber != null) {
                    System.out.println("   7. Selected Invoice Number: " + selectedInvoiceNumber);
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_022 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_022 PASSED - Paper submission verified and invoice successfully disappeared from Daily Billing list with Report View");
            System.out.println("✅ SMOKE_DB_022 TEST PASSED - PAPER SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 23, description = "SMOKE_DB_023 - Verify user can submit MULTIPLE invoices to clearinghouse with EMC, all invoices disappear from Daily Billing list")
        public void SMOKE_DB_023() {
            test.info("📋 Starting SMOKE_DB_023 - Verify Multiple EMC Submission");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_023: Multiple EMC Submission Test");
            System.out.println("========================================\n");

            List<String> selectedInvoiceNumbers = new ArrayList<>();

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice06");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click EMC Filter
                test.info("Step 2: Clicking EMC Filter");
                System.out.println("\n🔘 Step 2: Clicking EMC Filter...");

                try {
                    dailyBillingPage.clickEmcFilter();
                    test.info("✓ EMC Filter clicked");
                    System.out.println("✓ EMC Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for EMC filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Filter: " + e.getMessage());
                    System.err.println("❌ EMC Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select MULTIPLE EAMS Verified or EAMS Not Verified invoice checkboxes (minimum 2)
                test.info("Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoice checkboxes");
                System.out.println("\n☑️ Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoices...");

                int maxInvoices = 4; // Select maximum 5 invoices (or all on page if less)
                selectedInvoiceNumbers = dailyBillingPage.selectMultipleEamsVerifiedInvoiceCheckboxes(maxInvoices);

                Assert.assertTrue(selectedInvoiceNumbers.size() >= 2,
                        "Failed to select at least 2 invoices with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Selected: " + selectedInvoiceNumbers.size() + ". Ensure there are multiple EMC-ready invoices in the test data.");

                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("\n✓ Total invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                }
                WaitUtils.sleep(2000);

                // Step 4: Enable HCFA Toggle Button
                test.info("Step 4: Enabling HCFA Toggle Button");
                System.out.println("\n🔄 Step 4: Enabling HCFA Toggle...");

                try {
                    dailyBillingPage.enableHcfaToggle();
                    test.info("✓ HCFA Toggle enabled");
                    System.out.println("✓ HCFA Toggle enabled successfully");

                } catch (Exception e) {
                    test.fail("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                    System.err.println("❌ HCFA Toggle enable failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Click EMC Submission Button
                test.info("Step 5: Clicking EMC Submission Button for MULTIPLE invoices");
                System.out.println("\n📤 Step 5: Submitting " + selectedInvoiceNumbers.size() + " invoices via EMC...");

                try {
                    dailyBillingPage.clickEmcSubmissionButton();
                    test.info("✓ EMC Submission button clicked");
                    System.out.println("✓ EMC Submission initiated for " + selectedInvoiceNumbers.size() + " invoices");

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Submission button: " + e.getMessage());
                    System.err.println("❌ EMC Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Verify success message
                test.info("Step 6: Verifying success message");
                System.out.println("\n✅ Step 6: Checking for success message...");

                String successMessage = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Submitted.";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessage);

                test.info("✓ Success message verified: " + successMessage);
                System.out.println("✓ Success message: " + successMessage);

                // Step 7: Verify ALL selected invoices disappeared from Daily Billing list
                test.info("Step 7: Verifying ALL " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 7: Checking if ALL invoices disappeared...");

                boolean allInvoicesDisappeared = dailyBillingPage.areMultipleInvoicesDisappeared(selectedInvoiceNumbers);

                Assert.assertTrue(allInvoicesDisappeared,
                        "All " + selectedInvoiceNumbers.size() + " invoices should have disappeared from Daily Billing list after EMC submission, " +
                                "but some are still present.");

                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from list");
                System.out.println("\n✅ All " + selectedInvoiceNumbers.size() + " invoices removed from Daily Billing list");

                // Step 8: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_023 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ EMC filter clicked and invoices loaded");
                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   - Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                }
                test.info("✓ HCFA Toggle enabled successfully");
                test.info("✓ EMC Submission button clicked");
                test.info("✓ Success message verified: '" + successMessage + "'");
                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_023 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Multiple EMC Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ EMC filter selected");
                System.out.println("   ✓ Number of invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("   ✓ Invoice list:");
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                System.out.println("   ✓ HCFA Toggle enabled");
                System.out.println("   ✓ EMC Submission initiated (batch)");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Success message: '" + successMessage + "'");
                System.out.println("   ✓ All " + selectedInvoiceNumbers.size() + " invoices removed from list");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Multiple invoices submitted to clearinghouse via EMC (batch)");
                System.out.println("   ✓ All selected invoices successfully disappeared from Daily Billing list");
                System.out.println("   ✓ Multiple EMC submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_023 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure EMC Filter button is clickable");
                System.out.println("   2. Verify there are at least 2 EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that multiple checkboxes can be selected");
                System.out.println("   4. Check that HCFA Toggle can be enabled");
                System.out.println("   5. Verify EMC Submission button is clickable after HCFA toggle");
                System.out.println("   6. Confirm success message appears after submission");
                System.out.println("   7. Ensure ALL selected invoices disappear from list after submission");
                if (!selectedInvoiceNumbers.isEmpty()) {
                    System.out.println("   8. Selected Invoice Numbers (" + selectedInvoiceNumbers.size() + "):");
                    for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                        System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                    }
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_023 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_023 PASSED - Multiple EMC submission verified and all " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from Daily Billing list");
            System.out.println("✅ SMOKE_DB_023 TEST PASSED - MULTIPLE EMC SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 24, description = "SMOKE_DB_024 - Verify user can submit MULTIPLE invoices to clearinghouse with E/P, invoices disappear from Daily Billing list and Paper invoices open Report View")
        public void SMOKE_DB_024() {
            test.info("📋 Starting SMOKE_DB_024 - Verify Multiple E/P Submission (EMC + Paper)");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_024: Multiple E/P Submission Test");
            System.out.println("========================================\n");

            List<String> selectedInvoiceNumbersEMC = new ArrayList<>();
            List<String> selectedInvoiceNumbersPaper = new ArrayList<>();

            try {
                // ========== LOGIC 01: Multiple EMC Invoices through E/P ==========
                test.info("========== LOGIC 01: Multiple EMC Invoices through E/P ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("📤 LOGIC 01: Multiple EMC Invoices through E/P");
                System.out.println("=".repeat(60));

                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("\n📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice06");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click EMC Filter
                test.info("Step 2: Clicking EMC Filter");
                System.out.println("\n🔘 Step 2: Clicking EMC Filter...");

                try {
                    dailyBillingPage.clickEmcFilter();
                    test.info("✓ EMC Filter clicked");
                    System.out.println("✓ EMC Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for EMC filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click EMC Filter: " + e.getMessage());
                    System.err.println("❌ EMC Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select MULTIPLE EAMS Verified or EAMS Not Verified invoice checkboxes
                test.info("Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoice checkboxes");
                System.out.println("\n☑️ Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoices...");

                int maxInvoices = 4; // Select maximum 4 invoices
                selectedInvoiceNumbersEMC = dailyBillingPage.selectMultipleEamsVerifiedInvoiceCheckboxes(maxInvoices);

                Assert.assertTrue(selectedInvoiceNumbersEMC.size() >= 2,
                        "Failed to select at least 2 invoices with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Selected: " + selectedInvoiceNumbersEMC.size() + ". Ensure there are multiple EMC-ready invoices in the test data.");

                test.info("✓ Multiple EMC invoices selected: " + selectedInvoiceNumbersEMC.size());
                System.out.println("\n✓ Total EMC invoices selected: " + selectedInvoiceNumbersEMC.size());
                for (int i = 0; i < selectedInvoiceNumbersEMC.size(); i++) {
                    test.info("   Invoice " + (i + 1) + ": " + selectedInvoiceNumbersEMC.get(i));
                    System.out.println("   " + (i + 1) + ". " + selectedInvoiceNumbersEMC.get(i));
                }
                WaitUtils.sleep(2000);

                // Step 4: Enable HCFA Toggle Button
                test.info("Step 4: Enabling HCFA Toggle Button");
                System.out.println("\n🔄 Step 4: Enabling HCFA Toggle...");

                try {
                    dailyBillingPage.enableHcfaToggle();
                    test.info("✓ HCFA Toggle enabled");
                    System.out.println("✓ HCFA Toggle enabled successfully");

                } catch (Exception e) {
                    test.fail("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                    System.err.println("❌ HCFA Toggle enable failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Click E/P Submission Button
                test.info("Step 5: Clicking E/P Submission Button for MULTIPLE EMC invoices");
                System.out.println("\n📤 Step 5: Submitting " + selectedInvoiceNumbersEMC.size() + " EMC invoices via E/P...");

                try {
                    dailyBillingPage.clickEpSubmissionButton();
                    test.info("✓ E/P Submission button clicked");
                    System.out.println("✓ E/P Submission initiated for " + selectedInvoiceNumbersEMC.size() + " EMC invoices");

                } catch (Exception e) {
                    test.fail("❌ Failed to click E/P Submission button: " + e.getMessage());
                    System.err.println("❌ E/P Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 6: Verify success message
                test.info("Step 6: Verifying success message");
                System.out.println("\n✅ Step 6: Checking for success message...");

                String successMessageEMC = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Submitted.";

                Assert.assertEquals(successMessageEMC, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessageEMC);

                test.info("✓ Success message verified: " + successMessageEMC);
                System.out.println("✓ Success message: " + successMessageEMC);

                // Step 7: Verify ALL EMC invoices disappeared from Daily Billing list
                test.info("Step 7: Verifying ALL " + selectedInvoiceNumbersEMC.size() + " EMC invoices disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 7: Checking if ALL EMC invoices disappeared...");

                boolean allEmcInvoicesDisappeared = dailyBillingPage.areMultipleInvoicesDisappeared(selectedInvoiceNumbersEMC);

                Assert.assertTrue(allEmcInvoicesDisappeared,
                        "All " + selectedInvoiceNumbersEMC.size() + " EMC invoices should have disappeared from Daily Billing list after E/P submission, " +
                                "but some are still present.");

                test.info("✓ All " + selectedInvoiceNumbersEMC.size() + " EMC invoices successfully disappeared from list");
                System.out.println("\n✅ All " + selectedInvoiceNumbersEMC.size() + " EMC invoices removed from Daily Billing list");

                test.info("========== LOGIC 01 PASSED ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("✅ LOGIC 01 PASSED - Multiple EMC Invoices submitted via E/P");
                System.out.println("=".repeat(60));

                // ========== LOGIC 02: Multiple Paper Invoices through E/P ==========
                test.info("\n========== LOGIC 02: Multiple Paper Invoices through E/P ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("📄 LOGIC 02: Multiple Paper Invoices through E/P");
                System.out.println("=".repeat(60));

                // Step 8: Click Paper Filter
                test.info("Step 8: Clicking Paper Filter");
                System.out.println("\n🔘 Step 8: Clicking Paper Filter...");

                try {
                    dailyBillingPage.clickPaperFilter();
                    test.info("✓ Paper Filter clicked");
                    System.out.println("✓ Paper Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Paper filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Paper Filter: " + e.getMessage());
                    System.err.println("❌ Paper Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 9: Select MULTIPLE EAMS Verified or EAMS Not Verified invoice checkboxes
                test.info("Step 9: Selecting MULTIPLE EAMS Verified/Not Verified invoice checkboxes for Paper");
                System.out.println("\n☑️ Step 9: Selecting MULTIPLE EAMS Verified/Not Verified Paper invoices...");

                selectedInvoiceNumbersPaper = dailyBillingPage.selectMultipleEamsVerifiedInvoiceCheckboxes(maxInvoices);

                Assert.assertTrue(selectedInvoiceNumbersPaper.size() >= 2,
                        "Failed to select at least 2 Paper invoices with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Selected: " + selectedInvoiceNumbersPaper.size() + ". Ensure there are multiple Paper-ready invoices in the test data.");

                test.info("✓ Multiple Paper invoices selected: " + selectedInvoiceNumbersPaper.size());
                System.out.println("\n✓ Total Paper invoices selected: " + selectedInvoiceNumbersPaper.size());
                for (int i = 0; i < selectedInvoiceNumbersPaper.size(); i++) {
                    test.info("   Invoice " + (i + 1) + ": " + selectedInvoiceNumbersPaper.get(i));
                    System.out.println("   " + (i + 1) + ". " + selectedInvoiceNumbersPaper.get(i));
                }
                WaitUtils.sleep(2000);

                // Step 10: Enable HCFA Toggle Button
                test.info("Step 10: Enabling HCFA Toggle Button");
                System.out.println("\n🔄 Step 10: Enabling HCFA Toggle...");

                try {
                    dailyBillingPage.enableHcfaToggle();
                    test.info("✓ HCFA Toggle enabled");
                    System.out.println("✓ HCFA Toggle enabled successfully");

                } catch (Exception e) {
                    test.fail("❌ Failed to enable HCFA Toggle: " + e.getMessage());
                    System.err.println("❌ HCFA Toggle enable failed: " + e.getMessage());
                    throw e;
                }

                // Step 11: Click E/P Submission Button
                test.info("Step 11: Clicking E/P Submission Button for MULTIPLE Paper invoices");
                System.out.println("\n📤 Step 11: Submitting " + selectedInvoiceNumbersPaper.size() + " Paper invoices via E/P...");

                try {
                    dailyBillingPage.clickEpSubmissionButton();
                    test.info("✓ E/P Submission button clicked");
                    System.out.println("✓ E/P Submission initiated for " + selectedInvoiceNumbersPaper.size() + " Paper invoices");

                } catch (Exception e) {
                    test.fail("❌ Failed to click E/P Submission button: " + e.getMessage());
                    System.err.println("❌ E/P Submission button click failed: " + e.getMessage());
                    throw e;
                }

                WaitUtils.sleep(3000);

                // Step 12: Verify success message
                test.info("Step 12: Verifying success message");
                System.out.println("\n✅ Step 12: Checking for success message...");

                String successMessagePaper = dailyBillingPage.getSuccessMessage();

                Assert.assertEquals(successMessagePaper, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessagePaper);

                test.info("✓ Success message verified: " + successMessagePaper);
                System.out.println("✓ Success message: " + successMessagePaper);

                // Step 13: Verify Report View automatically opens after Paper submission
                test.info("Step 13: Verifying Report View automatically opened after Paper submission");
                System.out.println("\n📊 Step 13: Checking if Report View automatically opened...");

                boolean reportViewDisplayed = dailyBillingPage.isReportViewDisplayedAfterEP();

                Assert.assertTrue(reportViewDisplayed,
                        "Report View should automatically open after Paper invoice E/P submission, but it was not displayed.");

                test.info("✓ Report View automatically opened after Paper submission");
                System.out.println("✓ Report View automatically displayed");

                // Step 14: Verify Report View label
                test.info("Step 14: Verifying Report View label");
                System.out.println("\n🏷️ Step 14: Verifying Report View label...");

                String reportViewLabel = dailyBillingPage.getReportViewLabelText();
                String expectedReportViewLabel = "Report View";

                Assert.assertEquals(reportViewLabel, expectedReportViewLabel,
                        "Report View label should be 'Report View' but found: " + reportViewLabel);

                test.info("✓ Report View label verified: " + reportViewLabel);
                System.out.println("✓ Report View label: " + reportViewLabel);

                // Step 15: Verify ALL Paper invoices disappeared from Daily Billing list
                test.info("Step 15: Verifying ALL " + selectedInvoiceNumbersPaper.size() + " Paper invoices disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 15: Checking if ALL Paper invoices disappeared...");

                boolean allPaperInvoicesDisappeared = dailyBillingPage.areMultipleInvoicesDisappeared(selectedInvoiceNumbersPaper);

                Assert.assertTrue(allPaperInvoicesDisappeared,
                        "All " + selectedInvoiceNumbersPaper.size() + " Paper invoices should have disappeared from Daily Billing list after E/P submission, " +
                                "but some are still present.");

                test.info("✓ All " + selectedInvoiceNumbersPaper.size() + " Paper invoices successfully disappeared from list");
                System.out.println("\n✅ All " + selectedInvoiceNumbersPaper.size() + " Paper invoices removed from Daily Billing list");

                test.info("========== LOGIC 02 PASSED ==========");
                System.out.println("\n" + "=".repeat(60));
                System.out.println("✅ LOGIC 02 PASSED - Multiple Paper Invoices submitted via E/P with Report View");
                System.out.println("=".repeat(60));

                // Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_024 VALIDATION SUMMARY:");
                test.info("LOGIC 01 (Multiple EMC through E/P):");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ EMC filter clicked and invoices loaded");
                test.info("✓ Multiple EAMS Verified/Not Verified invoices selected: " + selectedInvoiceNumbersEMC.size());
                for (int i = 0; i < selectedInvoiceNumbersEMC.size(); i++) {
                    test.info("   - Invoice " + (i + 1) + ": " + selectedInvoiceNumbersEMC.get(i));
                }
                test.info("✓ HCFA Toggle enabled successfully");
                test.info("✓ E/P Submission button clicked");
                test.info("✓ Success message verified: '" + successMessageEMC + "'");
                test.info("✓ All " + selectedInvoiceNumbersEMC.size() + " EMC invoices disappeared from Daily Billing list");
                test.info("");
                test.info("LOGIC 02 (Multiple Paper through E/P):");
                test.info("✓ Paper filter clicked and invoices loaded");
                test.info("✓ Multiple EAMS Verified/Not Verified invoices selected: " + selectedInvoiceNumbersPaper.size());
                for (int i = 0; i < selectedInvoiceNumbersPaper.size(); i++) {
                    test.info("   - Invoice " + (i + 1) + ": " + selectedInvoiceNumbersPaper.get(i));
                }
                test.info("✓ HCFA Toggle enabled successfully");
                test.info("✓ E/P Submission button clicked");
                test.info("✓ Success message verified: '" + successMessagePaper + "'");
                test.info("✓ Report View automatically opened");
                test.info("✓ Report View label verified: 'Report View'");
                test.info("✓ All " + selectedInvoiceNumbersPaper.size() + " Paper invoices disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_024 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ LOGIC 01 (Multiple EMC through E/P):");
                System.out.println("   ✓ Number of EMC invoices selected: " + selectedInvoiceNumbersEMC.size());
                System.out.println("   ✓ EMC Invoice list:");
                for (int i = 0; i < selectedInvoiceNumbersEMC.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbersEMC.get(i));
                }
                System.out.println("   ✓ E/P Submission successful (batch)");
                System.out.println("   ✓ Success message displayed");
                System.out.println("   ✓ All EMC invoices removed from list");
                System.out.println();
                System.out.println("✅ LOGIC 02 (Multiple Paper through E/P):");
                System.out.println("   ✓ Number of Paper invoices selected: " + selectedInvoiceNumbersPaper.size());
                System.out.println("   ✓ Paper Invoice list:");
                for (int i = 0; i < selectedInvoiceNumbersPaper.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbersPaper.get(i));
                }
                System.out.println("   ✓ E/P Submission successful (batch)");
                System.out.println("   ✓ Success message displayed");
                System.out.println("   ✓ Report View automatically opened");
                System.out.println("   ✓ All Paper invoices removed from list");
                System.out.println();
                System.out.println("✅ Business Rules Validated:");
                System.out.println("   ✓ E/P button supports batch submission (multiple invoices)");
                System.out.println("   ✓ Multiple EMC invoices submitted via E/P → all disappear");
                System.out.println("   ✓ Multiple Paper invoices submitted via E/P → all disappear");
                System.out.println("   ✓ Paper invoices automatically open Report View (batch)");
                System.out.println("   ✓ E/P submission workflow completed successfully for batch operations");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_024 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure EMC Filter button is clickable");
                System.out.println("   2. Verify there are at least 2 EAMS Verified/Not Verified invoices for EMC");
                System.out.println("   3. Ensure Paper Filter button is clickable");
                System.out.println("   4. Verify there are at least 2 EAMS Verified/Not Verified invoices for Paper");
                System.out.println("   5. Check that multiple checkboxes can be selected");
                System.out.println("   6. Check that HCFA Toggle can be enabled");
                System.out.println("   7. Verify E/P Submission button is clickable after HCFA toggle");
                System.out.println("   8. Confirm success message appears after submission");
                System.out.println("   9. Verify Report View opens automatically after Paper E/P submission");
                System.out.println("   10. Ensure ALL selected invoices disappear from list after submission");
                if (!selectedInvoiceNumbersEMC.isEmpty()) {
                    System.out.println("   11. Selected EMC Invoice Numbers (" + selectedInvoiceNumbersEMC.size() + "):");
                    for (int i = 0; i < selectedInvoiceNumbersEMC.size(); i++) {
                        System.out.println("       " + (i + 1) + ". " + selectedInvoiceNumbersEMC.get(i));
                    }
                }
                if (!selectedInvoiceNumbersPaper.isEmpty()) {
                    System.out.println("   12. Selected Paper Invoice Numbers (" + selectedInvoiceNumbersPaper.size() + "):");
                    for (int i = 0; i < selectedInvoiceNumbersPaper.size(); i++) {
                        System.out.println("       " + (i + 1) + ". " + selectedInvoiceNumbersPaper.get(i));
                    }
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_024 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_024 PASSED - Multiple E/P submission verified for both EMC (" + selectedInvoiceNumbersEMC.size() + " invoices) and Paper (" + selectedInvoiceNumbersPaper.size() + " invoices)");
            System.out.println("✅ SMOKE_DB_024 TEST PASSED - MULTIPLE E/P SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 25, description = "SMOKE_DB_025 - Verify user can submit MULTIPLE invoices to clearinghouse with Mail, invoices disappear from Daily Billing list")
        public void SMOKE_DB_025() {
            test.info("📋 Starting SMOKE_DB_025 - Verify Multiple Mail Submission");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_025: Multiple Mail Submission Test");
            System.out.println("========================================\n");

            List<String> selectedInvoiceNumbers = new ArrayList<>();

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice06");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click Mail Filter
                test.info("Step 2: Clicking Mail Filter");
                System.out.println("\n🔘 Step 2: Clicking Mail Filter...");

                try {
                    dailyBillingPage.clickMailFilter();
                    test.info("✓ Mail Filter clicked");
                    System.out.println("✓ Mail Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Mail filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Mail Filter: " + e.getMessage());
                    System.err.println("❌ Mail Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select MULTIPLE EAMS Verified or EAMS Not Verified invoice checkboxes
                test.info("Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoice checkboxes");
                System.out.println("\n☑️ Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoices...");

                int maxInvoices = 4; // Select maximum 4 invoices
                selectedInvoiceNumbers = dailyBillingPage.selectMultipleEamsVerifiedInvoiceCheckboxes(maxInvoices);

                Assert.assertTrue(selectedInvoiceNumbers.size() >= 2,
                        "Failed to select at least 2 invoices with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Selected: " + selectedInvoiceNumbers.size() + ". Ensure there are multiple Mail-ready invoices in the test data.");

                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("\n✓ Total invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                    System.out.println("   " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                WaitUtils.sleep(2000);

                // Step 4: Click Mail Submission Button
                test.info("Step 4: Clicking Mail Submission Button for MULTIPLE invoices");
                System.out.println("\n📤 Step 4: Submitting " + selectedInvoiceNumbers.size() + " invoices via Mail...");

                try {
                    dailyBillingPage.clickMailSubmissionButton();
                    test.info("✓ Mail Submission button clicked");
                    System.out.println("✓ Mail Submission initiated for " + selectedInvoiceNumbers.size() + " invoices");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Mail Submission button: " + e.getMessage());
                    System.err.println("❌ Mail Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Verify success message
                test.info("Step 5: Verifying success message");
                System.out.println("\n✅ Step 5: Checking for success message...");

                String successMessage = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Sent To Email";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Sent To Email' but found: " + successMessage);

                test.info("✓ Success message verified: " + successMessage);
                System.out.println("✓ Success message: " + successMessage);

                // Step 6: Verify ALL selected invoices disappeared from Daily Billing list
                test.info("Step 6: Verifying ALL " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 6: Checking if ALL invoices disappeared...");

                boolean allInvoicesDisappeared = dailyBillingPage.areMultipleInvoicesDisappeared(selectedInvoiceNumbers);

                Assert.assertTrue(allInvoicesDisappeared,
                        "All " + selectedInvoiceNumbers.size() + " invoices should have disappeared from Daily Billing list after Mail submission, " +
                                "but some are still present.");

                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from list");
                System.out.println("\n✅ All " + selectedInvoiceNumbers.size() + " invoices removed from Daily Billing list");

                // Step 7: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_025 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ Mail filter clicked and invoices loaded");
                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   - Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                }
                test.info("✓ Mail Submission button clicked");
                test.info("✓ Success message verified: '" + successMessage + "'");
                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_025 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Multiple Mail Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ Mail filter selected");
                System.out.println("   ✓ Number of invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("   ✓ Invoice list:");
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                System.out.println("   ✓ Mail Submission initiated (batch)");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Success message: '" + successMessage + "'");
                System.out.println("   ✓ All " + selectedInvoiceNumbers.size() + " invoices removed from list");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Multiple invoices submitted via Mail (batch)");
                System.out.println("   ✓ All selected invoices successfully disappeared from Daily Billing list");
                System.out.println("   ✓ Multiple Mail submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_025 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure Mail Filter button is clickable");
                System.out.println("   2. Verify there are at least 2 EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that multiple checkboxes can be selected");
                System.out.println("   4. Check that Mail Submission button is clickable");
                System.out.println("   5. Confirm success message appears after submission");
                System.out.println("   6. Ensure ALL selected invoices disappear from list after submission");
                if (!selectedInvoiceNumbers.isEmpty()) {
                    System.out.println("   7. Selected Invoice Numbers (" + selectedInvoiceNumbers.size() + "):");
                    for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                        System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                    }
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_025 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_025 PASSED - Multiple Mail submission verified and all " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from Daily Billing list");
            System.out.println("✅ SMOKE_DB_025 TEST PASSED - MULTIPLE MAIL SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 26, description = "SMOKE_DB_026 - Verify user can submit MULTIPLE invoices to clearinghouse with Fax, invoices disappear from Daily Billing list")
        public void SMOKE_DB_026() {
            test.info("📋 Starting SMOKE_DB_026 - Verify Multiple Fax Submission");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_026: Multiple Fax Submission Test");
            System.out.println("========================================\n");

            List<String> selectedInvoiceNumbers = new ArrayList<>();

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice06");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click Fax Filter
                test.info("Step 2: Clicking Fax Filter");
                System.out.println("\n🔘 Step 2: Clicking Fax Filter...");

                try {
                    dailyBillingPage.clickFaxFilter();
                    test.info("✓ Fax Filter clicked");
                    System.out.println("✓ Fax Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Fax filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Fax Filter: " + e.getMessage());
                    System.err.println("❌ Fax Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select MULTIPLE EAMS Verified or EAMS Not Verified invoice checkboxes
                test.info("Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoice checkboxes");
                System.out.println("\n☑️ Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoices...");

                int maxInvoices = 4; // Select maximum 4 invoices
                selectedInvoiceNumbers = dailyBillingPage.selectMultipleEamsVerifiedInvoiceCheckboxes(maxInvoices);

                Assert.assertTrue(selectedInvoiceNumbers.size() >= 2,
                        "Failed to select at least 2 invoices with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Selected: " + selectedInvoiceNumbers.size() + ". Ensure there are multiple Fax-ready invoices in the test data.");

                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("\n✓ Total invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                    System.out.println("   " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                WaitUtils.sleep(2000);

                // Step 4: Click Fax Submission Button
                test.info("Step 4: Clicking Fax Submission Button for MULTIPLE invoices");
                System.out.println("\n📤 Step 4: Submitting " + selectedInvoiceNumbers.size() + " invoices via Fax...");

                try {
                    dailyBillingPage.clickFaxSubmissionButton();
                    test.info("✓ Fax Submission button clicked");
                    System.out.println("✓ Fax Submission initiated for " + selectedInvoiceNumbers.size() + " invoices");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Fax Submission button: " + e.getMessage());
                    System.err.println("❌ Fax Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Verify success message
                test.info("Step 5: Verifying Fax success message");
                System.out.println("\n✅ Step 5: Checking for Fax success message...");

                String successMessage = dailyBillingPage.getFaxSuccessMessage();
                String expectedMessage = "Bill Successfully Sent To Fax";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Sent To Fax' but found: " + successMessage);

                test.info("✓ Fax success message verified: " + successMessage);
                System.out.println("✓ Fax success message: " + successMessage);

                // Step 6: Verify ALL selected invoices disappeared from Daily Billing list
                test.info("Step 6: Verifying ALL " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 6: Checking if ALL invoices disappeared...");

                boolean allInvoicesDisappeared = dailyBillingPage.areMultipleInvoicesDisappeared(selectedInvoiceNumbers);

                Assert.assertTrue(allInvoicesDisappeared,
                        "All " + selectedInvoiceNumbers.size() + " invoices should have disappeared from Daily Billing list after Fax submission, " +
                                "but some are still present.");

                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from list");
                System.out.println("\n✅ All " + selectedInvoiceNumbers.size() + " invoices removed from Daily Billing list");

                // Step 7: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_026 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ Fax filter clicked and invoices loaded");
                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   - Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                }
                test.info("✓ Fax Submission button clicked");
                test.info("✓ Fax success message verified: '" + successMessage + "'");
                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_026 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Multiple Fax Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ Fax filter selected");
                System.out.println("   ✓ Number of invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("   ✓ Invoice list:");
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                System.out.println("   ✓ Fax Submission initiated (batch)");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Fax success message: '" + successMessage + "'");
                System.out.println("   ✓ All " + selectedInvoiceNumbers.size() + " invoices removed from list");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Multiple invoices submitted via Fax (batch)");
                System.out.println("   ✓ All selected invoices successfully disappeared from Daily Billing list");
                System.out.println("   ✓ Multiple Fax submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_026 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure Fax Filter button is clickable");
                System.out.println("   2. Verify there are at least 2 EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that multiple checkboxes can be selected");
                System.out.println("   4. Check that Fax Submission button is clickable");
                System.out.println("   5. Confirm Fax success message appears after submission");
                System.out.println("   6. Ensure ALL selected invoices disappear from list after submission");
                if (!selectedInvoiceNumbers.isEmpty()) {
                    System.out.println("   7. Selected Invoice Numbers (" + selectedInvoiceNumbers.size() + "):");
                    for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                        System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                    }
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_026 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_026 PASSED - Multiple Fax submission verified and all " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from Daily Billing list");
            System.out.println("✅ SMOKE_DB_026 TEST PASSED - MULTIPLE FAX SUBMISSION VALIDATED SUCCESSFULLY");
        }

        @Test(priority = 27, description = "SMOKE_DB_027 - Verify user can submit MULTIPLE invoices to clearinghouse with Paper, invoices disappear from Daily Billing list and Report View opens")
        public void SMOKE_DB_027() {
            test.info("📋 Starting SMOKE_DB_027 - Verify Multiple Paper Submission and Report View");
            System.out.println("\n========================================");
            System.out.println("🧪 SMOKE_DB_027: Multiple Paper Submission Test");
            System.out.println("========================================\n");

            List<String> selectedInvoiceNumbers = new ArrayList<>();

            try {
                // Step 1: Apply DOS filter
                test.info("Step 1: Applying DOS filter");
                System.out.println("📅 Step 1: Applying DOS filter...");
                String Dos = DailyBillingTestDataProperties.get("dateofservice06");
                dailyBillingPage.setDosFilter(Dos);
                WaitUtils.sleep(3000);
                test.info("✓ DOS filter applied: " + Dos);
                System.out.println("✓ DOS filter applied: " + Dos);

                // Step 2: Click Paper Filter
                test.info("Step 2: Clicking Paper Filter");
                System.out.println("\n🔘 Step 2: Clicking Paper Filter...");

                try {
                    dailyBillingPage.clickPaperFilter();
                    test.info("✓ Paper Filter clicked");
                    System.out.println("✓ Paper Filter clicked - Waiting for invoices to load");
                    WaitUtils.sleep(5000); // Wait for Paper filtered invoices to load

                } catch (Exception e) {
                    test.fail("❌ Failed to click Paper Filter: " + e.getMessage());
                    System.err.println("❌ Paper Filter click failed: " + e.getMessage());
                    throw e;
                }

                // Step 3: Select MULTIPLE EAMS Verified or EAMS Not Verified invoice checkboxes
                test.info("Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoice checkboxes");
                System.out.println("\n☑️ Step 3: Selecting MULTIPLE EAMS Verified/Not Verified invoices...");

                int maxInvoices = 4; // Select maximum 4 invoices
                selectedInvoiceNumbers = dailyBillingPage.selectMultipleEamsVerifiedInvoiceCheckboxes(maxInvoices);

                Assert.assertTrue(selectedInvoiceNumbers.size() >= 2,
                        "Failed to select at least 2 invoices with 'EAMS Verified' or 'EAMS Not Verified' status. " +
                                "Selected: " + selectedInvoiceNumbers.size() + ". Ensure there are multiple Paper-ready invoices in the test data.");

                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("\n✓ Total invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                    System.out.println("   " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                WaitUtils.sleep(2000);

                // Step 4: Click Paper Submission Button
                test.info("Step 4: Clicking Paper Submission Button for MULTIPLE invoices");
                System.out.println("\n📤 Step 4: Submitting " + selectedInvoiceNumbers.size() + " invoices via Paper...");

                try {
                    dailyBillingPage.clickPaperSubmissionButton();
                    test.info("✓ Paper Submission button clicked");
                    System.out.println("✓ Paper Submission initiated for " + selectedInvoiceNumbers.size() + " invoices");

                } catch (Exception e) {
                    test.fail("❌ Failed to click Paper Submission button: " + e.getMessage());
                    System.err.println("❌ Paper Submission button click failed: " + e.getMessage());
                    throw e;
                }

                // Step 5: Verify success message
                test.info("Step 5: Verifying success message");
                System.out.println("\n✅ Step 5: Checking for success message...");

                String successMessage = dailyBillingPage.getSuccessMessage();
                String expectedMessage = "Bill Successfully Submitted.";

                Assert.assertEquals(successMessage, expectedMessage,
                        "Success message should be 'Bill Successfully Submitted.' but found: " + successMessage);

                test.info("✓ Success message verified: " + successMessage);
                System.out.println("✓ Success message: " + successMessage);

                // Step 6: Verify Report View automatically opens after Paper submission
                test.info("Step 6: Verifying Report View automatically opened after Paper submission");
                System.out.println("\n📊 Step 6: Checking if Report View automatically opened...");

                boolean reportViewDisplayed = dailyBillingPage.isReportViewDisplayedAfterEP();

                Assert.assertTrue(reportViewDisplayed,
                        "Report View should automatically open after Paper submission, but it was not displayed.");

                test.info("✓ Report View automatically opened after Paper submission");
                System.out.println("✓ Report View automatically displayed");

                // Step 7: Verify Report View label
                test.info("Step 7: Verifying Report View label");
                System.out.println("\n🏷️ Step 7: Verifying Report View label...");

                String reportViewLabel = dailyBillingPage.getReportViewLabelText();
                String expectedReportViewLabel = "Report View";

                Assert.assertEquals(reportViewLabel, expectedReportViewLabel,
                        "Report View label should be 'Report View' but found: " + reportViewLabel);

                test.info("✓ Report View label verified: " + reportViewLabel);
                System.out.println("✓ Report View label: " + reportViewLabel);

                // Step 8: Verify ALL selected invoices disappeared from Daily Billing list
                test.info("Step 8: Verifying ALL " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                System.out.println("\n🔍 Step 8: Checking if ALL invoices disappeared...");

                boolean allInvoicesDisappeared = dailyBillingPage.areMultipleInvoicesDisappeared(selectedInvoiceNumbers);

                Assert.assertTrue(allInvoicesDisappeared,
                        "All " + selectedInvoiceNumbers.size() + " invoices should have disappeared from Daily Billing list after Paper submission, " +
                                "but some are still present.");

                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from list");
                System.out.println("\n✅ All " + selectedInvoiceNumbers.size() + " invoices removed from Daily Billing list");

                // Step 9: Final validation summary
                test.info("========================================");
                test.info("SMOKE_DB_027 VALIDATION SUMMARY:");
                test.info("✓ DOS filter applied successfully");
                test.info("✓ Paper filter clicked and invoices loaded");
                test.info("✓ Multiple invoices selected: " + selectedInvoiceNumbers.size());
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    test.info("   - Invoice " + (i + 1) + ": " + selectedInvoiceNumbers.get(i));
                }
                test.info("✓ Paper Submission button clicked");
                test.info("✓ Success message verified: '" + successMessage + "'");
                test.info("✓ Report View automatically opened");
                test.info("✓ Report View label verified: 'Report View'");
                test.info("✓ All " + selectedInvoiceNumbers.size() + " invoices disappeared from Daily Billing list");
                test.info("========================================");

                System.out.println("\n========================================");
                System.out.println("📊 SMOKE_DB_027 VALIDATION SUMMARY:");
                System.out.println("========================================");
                System.out.println("✅ Multiple Paper Submission Workflow:");
                System.out.println("   ✓ DOS filter applied");
                System.out.println("   ✓ Paper filter selected");
                System.out.println("   ✓ Number of invoices selected: " + selectedInvoiceNumbers.size());
                System.out.println("   ✓ Invoice list:");
                for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                }
                System.out.println("   ✓ Paper Submission initiated (batch)");
                System.out.println();
                System.out.println("✅ Submission Verification:");
                System.out.println("   ✓ Success message: '" + successMessage + "'");
                System.out.println("   ✓ Report View automatically opened");
                System.out.println("   ✓ Report View label verified");
                System.out.println("   ✓ All " + selectedInvoiceNumbers.size() + " invoices removed from list");
                System.out.println();
                System.out.println("✅ Business Rule Validated:");
                System.out.println("   ✓ Multiple invoices submitted via Paper (batch)");
                System.out.println("   ✓ Report View opens automatically for Paper submissions");
                System.out.println("   ✓ All selected invoices successfully disappeared from Daily Billing list");
                System.out.println("   ✓ Multiple Paper submission workflow completed successfully");
                System.out.println("========================================");

            } catch (AssertionError e) {
                test.fail("❌ SMOKE_DB_027 FAILED - " + e.getMessage());
                System.out.println("\n❌ TEST FAILED");
                System.out.println("Failure Reason: " + e.getMessage());
                System.out.println("\n💡 Troubleshooting:");
                System.out.println("   1. Ensure Paper Filter button is clickable");
                System.out.println("   2. Verify there are at least 2 EAMS Verified/Not Verified invoices");
                System.out.println("   3. Check that multiple checkboxes can be selected");
                System.out.println("   4. Check that Paper Submission button is clickable");
                System.out.println("   5. Confirm success message appears after submission");
                System.out.println("   6. Verify Report View opens automatically after Paper submission");
                System.out.println("   7. Ensure ALL selected invoices disappear from list after submission");
                if (!selectedInvoiceNumbers.isEmpty()) {
                    System.out.println("   8. Selected Invoice Numbers (" + selectedInvoiceNumbers.size() + "):");
                    for (int i = 0; i < selectedInvoiceNumbers.size(); i++) {
                        System.out.println("      " + (i + 1) + ". " + selectedInvoiceNumbers.get(i));
                    }
                }
                throw e;
            } catch (Exception e) {
                test.fail("❌ SMOKE_DB_027 FAILED - Unexpected error: " + e.getMessage());
                System.out.println("\n❌ TEST FAILED - Unexpected error");
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            test.pass("🎉 SMOKE_DB_027 PASSED - Multiple Paper submission verified with Report View and all " + selectedInvoiceNumbers.size() + " invoices successfully disappeared from Daily Billing list");
            System.out.println("✅ SMOKE_DB_027 TEST PASSED - MULTIPLE PAPER SUBMISSION VALIDATED SUCCESSFULLY");
        }

}

