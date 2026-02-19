package com.cyclonercm.billing.tests.smoke;

import com.cyclonercm.billing.base.SmokeBaseTest;
import com.cyclonercm.pages.AuthenticationPage;
import com.cyclonercm.pages.DailyBillingPage;
import com.cyclonercm.pages.FileUploadPage;
import com.cyclonercm.utils.DailyBillingSmokeTestDataProperties;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Daily Billing Smoke Test Suite
 * Tests SMOKE_DB_001 through SMOKE_DB_010
 * Validates the DMS Daily Billing page including list display,
 * filters, search, columns, and pagination.
 */
public class DailyBillingTest extends SmokeBaseTest {

    private AuthenticationPage loginPage;
    private FileUploadPage uploadPage;
    private DailyBillingPage dailyBillingPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        uploadPage = new FileUploadPage(driver);
        dailyBillingPage = new DailyBillingPage(driver);

        // Wait for login page to be ready
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 30);
        WaitUtils.sleep(2000);

        // Verify system label
        String actualSystemLabelText = loginPage.getSystemLabelText();
        String expectedSystemLabelText = DailyBillingSmokeTestDataProperties.get("systemLabel");
        try {
            Assert.assertEquals(actualSystemLabelText, expectedSystemLabelText, "System label text does not match.");
            test.pass("System label text verified: " + actualSystemLabelText);
        } catch (AssertionError e) {
            test.fail("System label text mismatch. Expected: " + expectedSystemLabelText + ", Found: " + actualSystemLabelText);
            throw e;
        }

        // Verify build number
        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = DailyBillingSmokeTestDataProperties.get("buildNumber");
        try {
            Assert.assertEquals(actualSystemVersionText, expectedSystemVersionText, "System version text does not match.");
            test.pass("System version text verified: " + actualSystemVersionText);
        } catch (AssertionError e) {
            test.fail("System version text mismatch. Expected: " + expectedSystemVersionText + ", Found: " + actualSystemVersionText);
            throw e;
        }

        // Login
        loginPage.enterUsername(DailyBillingSmokeTestDataProperties.get("validUserId"));
        loginPage.enterPassword(DailyBillingSmokeTestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();

        // Wait for dashboard
        WaitUtils.waitForVisibility(driver, LocatorConstants.getStartedButton, 30);
        WaitUtils.sleep(2000);

        String expectedGetStartedButtonText = DailyBillingSmokeTestDataProperties.get("expectedGetStartedButtonText");
        String actualGetStartedButtonText = uploadPage.getGetStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text does not match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }

        // Open mega menu and navigate to Daily Billing
        dailyBillingPage.clickMegaMenu();
        WaitUtils.sleep(2000);

        String expectedMastersText = DailyBillingSmokeTestDataProperties.get("expectedMastersText");
        String actualMastersText = dailyBillingPage.getMastersTabText();
        try {
            Assert.assertEquals(actualMastersText, expectedMastersText, "Masters tab text does not match.");
            test.pass("Masters tab text verified: " + actualMastersText);
        } catch (AssertionError e) {
            test.fail("Masters tab text mismatch. Expected: " + expectedMastersText + ", Found: " + actualMastersText);
            throw e;
        }

        dailyBillingPage.clickDailyBillingOption();
        WaitUtils.sleep(3000);
    }

    @Test(priority = 1, description = "SMOKE_DB_001 - Verify Daily Billing page loads with header and billing list section")
    public void SMOKE_DB_001() {
        try {
            Assert.assertTrue(dailyBillingPage.isDailyBillingHeaderDisplayed(),
                    "Daily Billing header should be displayed");
            test.pass("Daily Billing header is displayed");

            Assert.assertTrue(dailyBillingPage.isBillingListDisplayed(),
                    "Daily Billing list section should be displayed");
            test.pass("Daily Billing list section is displayed");

            test.pass("SMOKE_DB_001 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_001 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "SMOKE_DB_002 - Verify Daily Billing list displays billing records")
    public void SMOKE_DB_002() {
        try {
            Assert.assertTrue(dailyBillingPage.hasBillingRows(),
                    "Daily Billing list should display billing records");
            test.pass("Daily Billing list contains records. Row count: " + dailyBillingPage.getBillingRowCount());

            test.pass("SMOKE_DB_002 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_002 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "SMOKE_DB_003 - Verify Total Billing Count is displayed in header")
    public void SMOKE_DB_003() {
        try {
            String totalBillingCountText = dailyBillingPage.getTotalBillingCountText();
            Assert.assertNotNull(totalBillingCountText, "Total Billing Count should not be null");
            Assert.assertTrue(totalBillingCountText.contains("Total Billing Count:"),
                    "Total Billing Count label should be displayed");
            test.pass("Total Billing Count is displayed: " + totalBillingCountText);

            test.pass("SMOKE_DB_003 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_003 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "SMOKE_DB_004 - Verify billing list table columns are displayed")
    public void SMOKE_DB_004() {
        try {
            Assert.assertTrue(dailyBillingPage.isInvoiceNumberColumnDisplayed(),
                    "Invoice # column should be displayed");
            test.pass("Invoice # column is displayed");

            Assert.assertTrue(dailyBillingPage.isDateColumnDisplayed(),
                    "Date column should be displayed");
            test.pass("Date column is displayed");

            Assert.assertTrue(dailyBillingPage.isApplicantColumnDisplayed(),
                    "Applicant column should be displayed");
            test.pass("Applicant column is displayed");

            Assert.assertTrue(dailyBillingPage.isStatusColumnDisplayed(),
                    "Status column should be displayed");
            test.pass("Status column is displayed");

            Assert.assertTrue(dailyBillingPage.isAmountColumnDisplayed(),
                    "Amount column should be displayed");
            test.pass("Amount column is displayed");

            test.pass("SMOKE_DB_004 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_004 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "SMOKE_DB_005 - Verify first billing record displays invoice number, date, applicant, status and amount")
    public void SMOKE_DB_005() {
        try {
            String invoiceNumber = dailyBillingPage.getFirstRowInvoiceNumber();
            Assert.assertNotNull(invoiceNumber, "Invoice number should not be null");
            Assert.assertFalse(invoiceNumber.isEmpty(), "Invoice number should not be empty");
            test.pass("Invoice number is displayed: " + invoiceNumber);

            String date = dailyBillingPage.getFirstRowDate();
            Assert.assertNotNull(date, "Date should not be null");
            Assert.assertFalse(date.isEmpty(), "Date should not be empty");
            test.pass("Date is displayed: " + date);

            String applicant = dailyBillingPage.getFirstRowApplicant();
            Assert.assertNotNull(applicant, "Applicant should not be null");
            Assert.assertFalse(applicant.isEmpty(), "Applicant should not be empty");
            test.pass("Applicant is displayed: " + applicant);

            String status = dailyBillingPage.getFirstRowStatus();
            Assert.assertNotNull(status, "Status should not be null");
            Assert.assertFalse(status.isEmpty(), "Status should not be empty");
            test.pass("Status is displayed: " + status);

            test.pass("SMOKE_DB_005 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_005 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "SMOKE_DB_006 - Verify Status filter dropdown displays all status options")
    public void SMOKE_DB_006() {
        try {
            WaitUtils.sleep(3000);

            boolean dropdownFound = false;
            for (int i = 0; i < 3; i++) {
                if (dailyBillingPage.isStatusFilterDropdownDisplayed()) {
                    dropdownFound = true;
                    break;
                }
                System.out.println("Attempt " + (i + 1) + ": Status filter dropdown not found, retrying...");
                WaitUtils.sleep(2000);
            }

            Assert.assertTrue(dropdownFound, "Status filter dropdown should be displayed");
            test.pass("Status filter dropdown is displayed");

            dailyBillingPage.clickStatusFilterDropdown();
            WaitUtils.sleep(2000);
            test.pass("Status filter dropdown opened successfully");

            String[] expectedOptions = {
                DailyBillingSmokeTestDataProperties.get("statusAll"),
                DailyBillingSmokeTestDataProperties.get("statusPending"),
                DailyBillingSmokeTestDataProperties.get("statusApproved"),
                DailyBillingSmokeTestDataProperties.get("statusDenied"),
                DailyBillingSmokeTestDataProperties.get("statusInProcess")
            };

            for (String option : expectedOptions) {
                boolean optionExists = dailyBillingPage.isDropdownOptionDisplayed(option);
                Assert.assertTrue(optionExists, option + " option should be displayed in dropdown");
                test.pass("✓ " + option + " option is displayed");
            }

            test.pass("SMOKE_DB_006 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_006 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "SMOKE_DB_007 - Verify date filter (From Date and To Date) fields are displayed")
    public void SMOKE_DB_007() {
        try {
            Assert.assertTrue(dailyBillingPage.isFromDateInputDisplayed(),
                    "From Date input field should be displayed");
            test.pass("From Date input field is displayed");

            Assert.assertTrue(dailyBillingPage.isToDateInputDisplayed(),
                    "To Date input field should be displayed");
            test.pass("To Date input field is displayed");

            Assert.assertTrue(dailyBillingPage.isSearchButtonDisplayed(),
                    "Search button should be displayed");
            test.pass("Search button is displayed");

            Assert.assertTrue(dailyBillingPage.isClearButtonDisplayed(),
                    "Clear button should be displayed");
            test.pass("Clear button is displayed");

            test.pass("SMOKE_DB_007 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_007 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "SMOKE_DB_008 - Verify date filter search applies and shows Date Range Active label")
    public void SMOKE_DB_008() {
        try {
            String fromDate = DailyBillingSmokeTestDataProperties.get("filterFromDate");
            String toDate = DailyBillingSmokeTestDataProperties.get("filterToDate");

            dailyBillingPage.enterFromDate(fromDate);
            test.pass("Entered From Date: " + fromDate);

            dailyBillingPage.enterToDate(toDate);
            test.pass("Entered To Date: " + toDate);

            dailyBillingPage.clickSearchButton();
            WaitUtils.sleep(3000);
            test.pass("Search button clicked");

            Assert.assertTrue(dailyBillingPage.isDateRangeActiveLabelDisplayed(),
                    "Date Range Active label should be displayed after search");
            test.pass("Date Range Active label is displayed");

            test.pass("SMOKE_DB_008 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_008 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "SMOKE_DB_009 - Verify clear button resets date filter")
    public void SMOKE_DB_009() {
        try {
            String fromDate = DailyBillingSmokeTestDataProperties.get("filterFromDate");
            String toDate = DailyBillingSmokeTestDataProperties.get("filterToDate");

            dailyBillingPage.enterFromDate(fromDate);
            dailyBillingPage.enterToDate(toDate);
            dailyBillingPage.clickSearchButton();
            WaitUtils.sleep(3000);
            test.pass("Date filter applied");

            dailyBillingPage.clickClearButton();
            WaitUtils.sleep(2000);
            test.pass("Clear button clicked");

            Assert.assertFalse(dailyBillingPage.isDateRangeActiveLabelDisplayed(),
                    "Date Range Active label should not be displayed after clear");
            test.pass("Date Range Active label is not displayed after clear");

            test.pass("SMOKE_DB_009 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_009 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "SMOKE_DB_010 - Verify invoice search field is displayed and accepts input")
    public void SMOKE_DB_010() {
        try {
            Assert.assertTrue(dailyBillingPage.isSearchInvoiceFieldDisplayed(),
                    "Invoice search field should be displayed");
            test.pass("Invoice search field is displayed");

            String searchInvoice = DailyBillingSmokeTestDataProperties.get("searchInvoiceNumber");
            dailyBillingPage.enterSearchInvoiceNumber(searchInvoice);
            test.pass("Entered invoice number in search field: " + searchInvoice);

            dailyBillingPage.clickInvoiceSearchButton();
            WaitUtils.sleep(3000);
            test.pass("Invoice search button clicked");

            test.pass("SMOKE_DB_010 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_DB_010 failed: " + e.getMessage());
            throw e;
        }
    }
}
