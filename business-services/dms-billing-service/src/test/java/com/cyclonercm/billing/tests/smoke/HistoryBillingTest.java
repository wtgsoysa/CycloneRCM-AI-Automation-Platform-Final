package com.cyclonercm.billing.tests.smoke;

import com.cyclonercm.billing.base.SmokeBaseTest;
import com.cyclonercm.pages.*;
import com.cyclonercm.utils.DailyBillingTestDataProperties;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HistoryBillingTest extends SmokeBaseTest {

    private AuthenticationPage loginPage;
    private FileUploadPage uploadPage;
    private FileHistoryPage historyPage;
    private DailyBillingPage dailyBillingPage;
    private SingleBillingPage singleBillingPage;
    private BillingHistoryPage billingHistoryPage;



    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        uploadPage = new FileUploadPage(driver);
        historyPage = new FileHistoryPage(driver);
        dailyBillingPage = new DailyBillingPage(driver);
        singleBillingPage = new SingleBillingPage(driver);
        billingHistoryPage = new BillingHistoryPage(driver);

        // Wait for login page
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 60);
        WaitUtils.sleep(3000);

        // Verify system label and version
        String actualSystemLabelText = loginPage.getSystemLabelText();
        String expectedSystemLabelText = DailyBillingTestDataProperties.get("systemLabel");

        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = DailyBillingTestDataProperties.get("buildNumber");

        System.out.println("Expected System Label: " + expectedSystemLabelText);
        System.out.println("Actual System Label: " + actualSystemLabelText);
        System.out.println("Expected Version: " + expectedSystemVersionText);
        System.out.println("Actual Version: " + actualSystemVersionText);

        // Validate system label if visible
        if (!actualSystemLabelText.isEmpty()) {
            try {
                Assert.assertEquals(actualSystemLabelText, expectedSystemLabelText, "System label text does not match.");
                test.pass("System label text verified: " + actualSystemLabelText);
            } catch (AssertionError e) {
                test.fail("System label text mismatch. Expected: " + expectedSystemLabelText + ", Found: " + actualSystemLabelText);
                throw e;
            }
        } else {
            test.info("System label text not found - skipping validation");
            System.out.println("⚠ System label text not found - skipping validation");
        }

        // Validate version
        try {
            Assert.assertEquals(actualSystemVersionText, expectedSystemVersionText, "System version text does not match.");
            test.pass("System version text verified: " + actualSystemVersionText);
        } catch (AssertionError e) {
            test.fail("System version text mismatch. Expected: " + expectedSystemVersionText + ", Found: " + actualSystemVersionText);
            throw e;
        }

        // Login
        loginPage.enterUsername(DailyBillingTestDataProperties.get("validUserId"));
        loginPage.enterPassword(DailyBillingTestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.BillingMenu, 60);

        // Wait for splash screen to disappear after login
        try {
            org.openqa.selenium.By splashScreen = org.openqa.selenium.By.xpath("//div[@class='splash-screen ng-star-inserted']");
            org.openqa.selenium.support.ui.WebDriverWait splashWait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(20));
            splashWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(splashScreen));
            System.out.println("✓ Splash screen disappeared after login");
        } catch (Exception e) {
            System.out.println("⚠ Splash screen already gone or not found");
        }

        WaitUtils.sleep(3000);

        System.out.println("✓ Setup completed - Ready for Billing History tests");

        // Step 1: Click Billing Menu
        test.info("Step 1: Clicking Billing Menu");
        System.out.println("\n📝 Step 1: Clicking Billing Menu...");

        billingHistoryPage.clickBillingMenu();
        test.pass("✓ Billing Menu clicked");
        System.out.println("✓ Billing Menu clicked");


        // Step 2: Click Billing History Option
        test.info("Step 2: Clicking Billing History option");
        System.out.println("\n🖱️ Step 2: Clicking Billing History option...");

        billingHistoryPage.clickBillingHistoryOption();
        test.pass("✓ Billing History option clicked");
        System.out.println("✓ Billing History option clicked");

        // Step 3: Verify Billing History Label
        test.info("Step 3: Verifying Billing History label");
        System.out.println("\n✅ Step 3: Verifying Billing History label...");

        String actualLabel = billingHistoryPage.getBillingHistoryLabel();
        String expectedLabel = "Billing History";

        System.out.println("Expected Label: " + expectedLabel);
        System.out.println("Actual Label: " + actualLabel);

        Assert.assertEquals(actualLabel, expectedLabel,
                "Billing History label should match expected text");

        test.pass("✓ Billing History label verified: " + actualLabel);
        System.out.println("✓ Billing History label verified: " + actualLabel);

        // Wait for splash screen to disappear before proceeding to tests
        try {
            org.openqa.selenium.By splashScreen = org.openqa.selenium.By.xpath("//div[@class='splash-screen ng-star-inserted']");
            org.openqa.selenium.support.ui.WebDriverWait splashWait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
            splashWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(splashScreen));
            System.out.println("✓ Splash screen disappeared");
        } catch (Exception e) {
            System.out.println("⚠ Splash screen already gone or not found");
        }

        WaitUtils.sleep(3000);
        System.out.println("✓ Billing History page fully loaded and ready for testing");

    }

    // ========== TEST METHODS ==========

    @Test(priority = 1, description = "SMOKE_BH_001 - Verify user can filter Billing History by Biller")
    public void SMOKE_BH_001() {
        test.info("📋 Starting SMOKE_BH_001 - Filter Billing History by Biller");
        System.out.println("\n========================================");
        System.out.println("🧪 SMOKE_BH_001: Filter Billing History by Biller");
        System.out.println("========================================\n");

        try {
            // Step 1: Click Biller Dropdown
            test.info("Step 1: Clicking Biller dropdown");
            System.out.println("\n📝 Step 1: Clicking Biller dropdown...");

            billingHistoryPage.clickBillerDropdown();
            test.pass("✓ Biller dropdown clicked");
            System.out.println("✓ Biller dropdown clicked");

            // Step 2: Get first biller name from dropdown
            test.info("Step 2: Getting first biller name from dropdown");
            System.out.println("\n🔍 Step 2: Getting first biller name from dropdown...");

            String selectedBillerFullName = billingHistoryPage.getFirstBillerName();

            Assert.assertFalse(selectedBillerFullName.isEmpty(),
                    "Biller name should not be empty");

            System.out.println("Selected Biller (Full Name): " + selectedBillerFullName);
            test.info("Selected Biller: " + selectedBillerFullName);

            // Extract first name from full name (format: "LastName FirstName" -> "FirstName")
            String[] nameParts = selectedBillerFullName.split(" ");
            String expectedFirstName = nameParts.length > 1 ? nameParts[1] : selectedBillerFullName;
            System.out.println("Expected First Name in table: " + expectedFirstName);

            test.pass("✓ Biller name retrieved: " + selectedBillerFullName);

            // Step 3: Select the biller from dropdown
            test.info("Step 3: Selecting biller: " + selectedBillerFullName);
            System.out.println("\n🖱️ Step 3: Selecting biller...");

            billingHistoryPage.selectBillerByName(selectedBillerFullName);
            test.pass("✓ Biller selected: " + selectedBillerFullName);
            System.out.println("✓ Biller selected");

            // Step 4: Wait for table to load
            test.info("Step 4: Waiting for table to load");
            System.out.println("\n⏳ Step 4: Waiting for table to load...");

            billingHistoryPage.waitForTableLoad();
            test.pass("✓ Table loaded");
            System.out.println("✓ Table loaded");

            // Step 5: Get biller name from table (first row, column 5)
            test.info("Step 5: Getting biller name from table");
            System.out.println("\n✅ Step 5: Getting biller name from table...");

            String actualBillerName = billingHistoryPage.getTableBillerName();

            Assert.assertFalse(actualBillerName.isEmpty(),
                    "Table biller name should not be empty");

            System.out.println("Actual Biller Name in Table: " + actualBillerName);
            test.info("Table Biller Name: " + actualBillerName);

            // Step 6: Validate biller name (table shows only first name)
            test.info("Step 6: Validating biller name");
            System.out.println("\n✅ Step 6: Validating biller name...");

            System.out.println("Expected First Name: " + expectedFirstName);
            System.out.println("Actual Table Name: " + actualBillerName);

            Assert.assertEquals(actualBillerName, expectedFirstName,
                    "Table biller first name should match selected biller's first name");

            test.pass("✓ Biller name validated successfully");
            System.out.println("✓ Biller filter validated: " + actualBillerName + " = " + expectedFirstName);

            // Summary
            System.out.println("\n========================================");
            System.out.println("📊 SMOKE_BH_001 Summary:");
            System.out.println("   ✓ Biller dropdown opened");
            System.out.println("   ✓ Selected biller: " + selectedBillerFullName);
            System.out.println("   ✓ Table loaded successfully");
            System.out.println("   ✓ Table biller name: " + actualBillerName);
            System.out.println("   ✓ Validation passed: " + expectedFirstName + " = " + actualBillerName);
            System.out.println("========================================");

        } catch (AssertionError e) {
            test.fail("❌ SMOKE_BH_001 FAILED - " + e.getMessage());
            System.out.println("\n❌ TEST FAILED: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("❌ SMOKE_BH_001 FAILED - Unexpected error: " + e.getMessage());
            System.out.println("\n❌ TEST FAILED - Unexpected error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        test.pass("🎉 SMOKE_BH_001 PASSED - Biller filter validated successfully");
        System.out.println("\n✅ SMOKE_BH_001 TEST PASSED");
    }

    @Test(priority = 2, description = "SMOKE_BH_002 - Verify user can filter Billing History by Invoice Number")
    public void SMOKE_BH_002() {
        test.info("📋 Starting SMOKE_BH_002 - Filter Billing History by Invoice Number");
        System.out.println("\n========================================");
        System.out.println("🧪 SMOKE_BH_002: Filter Billing History by Invoice Number");
        System.out.println("========================================\n");

        try {
            // Step 1: Get invoice number from test data
            test.info("Step 1: Getting invoice number from test data");
            System.out.println("\n📝 Step 1: Getting invoice number from test data...");

            String invoiceNumber = TestDataProperties.get("billingHistory.invoiceNumber1");

            Assert.assertFalse(invoiceNumber.isEmpty(),
                    "Invoice number should not be empty");

            System.out.println("Invoice Number to search: " + invoiceNumber);
            test.info("Invoice Number: " + invoiceNumber);
            test.pass("✓ Invoice number retrieved from test data");

            // Step 2: Enter invoice number in search field
            test.info("Step 2: Entering invoice number in search field");
            System.out.println("\n🔍 Step 2: Entering invoice number in search field...");

            billingHistoryPage.enterInvoiceNumber(invoiceNumber);
            test.pass("✓ Invoice number entered: " + invoiceNumber);
            System.out.println("✓ Invoice number entered");

            // Step 3: Click invoice search button
            test.info("Step 3: Clicking invoice search button");
            System.out.println("\n🖱️ Step 3: Clicking invoice search button...");

            billingHistoryPage.clickInvoiceSearchButton();
            test.pass("✓ Invoice search button clicked");
            System.out.println("✓ Invoice search button clicked");

            // Step 4: Wait for table to load
            test.info("Step 4: Waiting for table to load");
            System.out.println("\n⏳ Step 4: Waiting for table to load...");

            billingHistoryPage.waitForTableLoad();
            test.pass("✓ Table loaded");
            System.out.println("✓ Table loaded");

            // Step 5: Expand parent row to reveal nested invoice table
            test.info("Step 5: Expanding parent row to reveal nested invoice table");
            System.out.println("\n🔽 Step 5: Expanding parent row to reveal nested invoice table...");

            billingHistoryPage.expandFirstParentRow();
            test.pass("✓ Parent row expanded");
            System.out.println("✓ Parent row expanded");

            // Step 6: Get invoice number from nested table
            test.info("Step 6: Getting invoice number from nested table");
            System.out.println("\n✅ Step 6: Getting invoice number from nested table...");

            String actualInvoiceNumber = billingHistoryPage.getTableInvoiceNumber();

            Assert.assertFalse(actualInvoiceNumber.isEmpty(),
                    "Table invoice number should not be empty");

            System.out.println("Actual Invoice Number in Table: " + actualInvoiceNumber);
            test.info("Table Invoice Number: " + actualInvoiceNumber);

            // Step 7: Validate invoice number
            test.info("Step 7: Validating invoice number");
            System.out.println("\n✅ Step 7: Validating invoice number...");

            System.out.println("Expected Invoice Number: " + invoiceNumber);
            System.out.println("Actual Table Invoice Number: " + actualInvoiceNumber);

            Assert.assertEquals(actualInvoiceNumber, invoiceNumber,
                    "Table invoice number should match searched invoice number");

            test.pass("✓ Invoice number validated successfully");
            System.out.println("✓ Invoice filter validated: " + actualInvoiceNumber + " = " + invoiceNumber);

            // Summary
            System.out.println("\n========================================");
            System.out.println("📊 SMOKE_BH_002 Summary:");
            System.out.println("   ✓ Invoice number entered: " + invoiceNumber);
            System.out.println("   ✓ Search button clicked");
            System.out.println("   ✓ Table loaded successfully");
            System.out.println("   ✓ Parent row expanded to reveal nested invoice table");
            System.out.println("   ✓ Table invoice number: " + actualInvoiceNumber);
            System.out.println("   ✓ Validation passed: " + invoiceNumber + " = " + actualInvoiceNumber);
            System.out.println("========================================");

        } catch (AssertionError e) {
            test.fail("❌ SMOKE_BH_002 FAILED - " + e.getMessage());
            System.out.println("\n❌ TEST FAILED: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            test.fail("❌ SMOKE_BH_002 FAILED - Unexpected error: " + e.getMessage());
            System.out.println("\n❌ TEST FAILED - Unexpected error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        test.pass("🎉 SMOKE_BH_002 PASSED - Invoice filter validated successfully");
        System.out.println("\n✅ SMOKE_BH_002 TEST PASSED");

    }

}
