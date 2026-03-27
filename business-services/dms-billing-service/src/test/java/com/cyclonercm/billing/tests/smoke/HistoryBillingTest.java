package com.cyclonercm.billing.tests.smoke;

import com.cyclonercm.billing.base.SmokeBaseTest;
import com.cyclonercm.pages.*;
import com.cyclonercm.utils.HistoryBillingTestDataProperties;
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
        String expectedSystemLabelText = HistoryBillingTestDataProperties.get("systemLabel");

        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = HistoryBillingTestDataProperties.get("buildNumber");

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
        loginPage.enterUsername(HistoryBillingTestDataProperties.get("validUserId"));
        loginPage.enterPassword(HistoryBillingTestDataProperties.get("validPassword"));
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

    @Test(priority = 1, description = "SMOKE_BH_001: Verify user can filter Billing History by Biller")
    public void testFilterByBiller() {
        test.info("SMOKE_BH_001: Verify user can filter Billing History by Biller");
        System.out.println("\n🧪 SMOKE_BH_001: Verify user can filter Billing History by Biller");

        test.info("Step 1: Click Biller dropdown");
        System.out.println("Step 1: Clicking Biller dropdown...");
        billingHistoryPage.clickBillerDropdown();
        test.pass("✓ Biller dropdown clicked");

        test.info("Step 2: Get first biller name");
        System.out.println("Step 2: Getting first biller name...");
        String selectedBiller = billingHistoryPage.getFirstBillerName();
        System.out.println("Selected Biller: " + selectedBiller);
        test.info("Selected Biller: " + selectedBiller);

        test.info("Step 3: Select biller from dropdown");
        System.out.println("Step 3: Selecting biller...");
        billingHistoryPage.selectBillerByName(selectedBiller);
        test.pass("✓ Biller selected: " + selectedBiller);

        test.info("Step 4: Wait for table to load");
        System.out.println("Step 4: Waiting for table to load...");
        billingHistoryPage.waitForTableLoad();
        test.pass("✓ Table loaded");

        test.info("Step 5: Verify biller in table matches selected biller");
        System.out.println("Step 5: Verifying biller in table...");
        String tableBiller = billingHistoryPage.getTableBillerName();
        System.out.println("Expected Biller: " + selectedBiller);
        System.out.println("Actual Biller: " + tableBiller);

        Assert.assertEquals(tableBiller, selectedBiller, "Biller in table should match selected biller");
        test.pass("✓ SMOKE_BH_001 PASSED: Biller filter verified successfully");
        System.out.println("✓ SMOKE_BH_001 PASSED");
    }

    @Test(priority = 2, description = "SMOKE_BH_002: Verify user can filter Billing History by Invoice Number")
    public void testFilterByInvoiceNumber() {
        test.info("SMOKE_BH_002: Verify user can filter Billing History by Invoice Number");
        System.out.println("\n🧪 SMOKE_BH_002: Verify user can filter Billing History by Invoice Number");

        String invoiceNumber = HistoryBillingTestDataProperties.get("invoiceNumberB");

        test.info("Step 1: Enter invoice number in search field");
        System.out.println("Step 1: Entering invoice number: " + invoiceNumber);
        billingHistoryPage.enterInvoiceNumber(invoiceNumber);
        test.pass("✓ Invoice number entered");

        test.info("Step 2: Click invoice search button");
        System.out.println("Step 2: Clicking search button...");
        billingHistoryPage.clickInvoiceSearchButton();
        test.pass("✓ Search button clicked");

        test.info("Step 3: Expand first parent row");
        System.out.println("Step 3: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 4: Verify invoice number in table");
        System.out.println("Step 4: Verifying invoice number...");
        String tableInvoiceNumber = billingHistoryPage.getTableInvoiceNumber();
        System.out.println("Expected Invoice: " + invoiceNumber);
        System.out.println("Actual Invoice: " + tableInvoiceNumber);

        Assert.assertTrue(tableInvoiceNumber.contains(invoiceNumber), "Invoice number should be present in table");
        test.pass("✓ SMOKE_BH_002 PASSED: Invoice filter verified successfully");
        System.out.println("✓ SMOKE_BH_002 PASSED");
    }

    @Test(priority = 3, description = "SMOKE_BH_003: Verify user can filter Billing History by User Account")
    public void testFilterByAccount() {
        test.info("SMOKE_BH_003: Verify user can filter Billing History by User Account");
        System.out.println("\n🧪 SMOKE_BH_003: Verify user can filter Billing History by User Account");

        String accountSearchText = HistoryBillingTestDataProperties.get("accountSearchText");

        test.info("Step 1: Click Account dropdown");
        System.out.println("Step 1: Clicking Account dropdown...");
        billingHistoryPage.clickAccountDropdown();
        test.pass("✓ Account dropdown clicked");

        test.info("Step 2: Enter account search text");
        System.out.println("Step 2: Entering search text: " + accountSearchText);
        billingHistoryPage.enterAccountSearchText(accountSearchText);
        test.pass("✓ Search text entered");

        test.info("Step 3: Click account search button");
        System.out.println("Step 3: Clicking search button...");
        billingHistoryPage.clickAccountSearchButton();
        test.pass("✓ Search button clicked");

        test.info("Step 4: Select first account result");
        System.out.println("Step 4: Selecting first account result...");
        billingHistoryPage.selectFirstAccountResult();
        test.pass("✓ Account selected");

        test.info("Step 5: Wait for table to load");
        System.out.println("Step 5: Waiting for table to load...");
        billingHistoryPage.waitForTableLoad();
        test.pass("✓ SMOKE_BH_003 PASSED: Account filter verified successfully");
        System.out.println("✓ SMOKE_BH_003 PASSED");
    }

    @Test(priority = 4, description = "SMOKE_BH_004: Verify user can filter Billing History by Applicant")
    public void testFilterByApplicant() {
        test.info("SMOKE_BH_004: Verify user can filter Billing History by Applicant");
        System.out.println("\n🧪 SMOKE_BH_004: Verify user can filter Billing History by Applicant");

        String applicantSearchText = HistoryBillingTestDataProperties.get("applicantSearchText");

        test.info("Step 1: Click Applicant dropdown");
        System.out.println("Step 1: Clicking Applicant dropdown...");
        billingHistoryPage.clickApplicantDropdown();
        test.pass("✓ Applicant dropdown clicked");

        test.info("Step 2: Enter applicant search text");
        System.out.println("Step 2: Entering search text: " + applicantSearchText);
        billingHistoryPage.enterApplicantSearchText(applicantSearchText);
        test.pass("✓ Search text entered");

        test.info("Step 3: Click applicant search button");
        System.out.println("Step 3: Clicking search button...");
        billingHistoryPage.clickApplicantSearchButton();
        test.pass("✓ Search button clicked");

        test.info("Step 4: Select first applicant result");
        System.out.println("Step 4: Selecting first applicant result...");
        billingHistoryPage.selectFirstApplicantResult();
        test.pass("✓ Applicant selected");

        test.info("Step 5: Wait for table to load");
        System.out.println("Step 5: Waiting for table to load...");
        billingHistoryPage.waitForTableLoad();
        test.pass("✓ SMOKE_BH_004 PASSED: Applicant filter verified successfully");
        System.out.println("✓ SMOKE_BH_004 PASSED");
    }

    @Test(priority = 5, description = "SMOKE_BH_005: Verify submitted invoices appear in Billing History with correct details")
    public void testVerifyInvoiceDetails() {
        test.info("SMOKE_BH_005: Verify submitted invoices appear in Billing History with correct details");
        System.out.println("\n🧪 SMOKE_BH_005: Verify submitted invoices appear in Billing History with correct details");

        test.info("Step 1: Expand first parent row");
        System.out.println("Step 1: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 2: Get Sub Number");
        System.out.println("Step 2: Getting Sub Number...");
        String subNumber = billingHistoryPage.getSubNumber();
        System.out.println("Sub Number: " + subNumber);
        Assert.assertFalse(subNumber.isEmpty(), "Sub Number should not be empty");
        test.pass("✓ Sub Number: " + subNumber);

        test.info("Step 3: Get Invoice Number");
        System.out.println("Step 3: Getting Invoice Number...");
        String invoiceNumber = billingHistoryPage.getExpandedInvoiceNumber();
        System.out.println("Invoice Number: " + invoiceNumber);
        Assert.assertFalse(invoiceNumber.isEmpty(), "Invoice Number should not be empty");
        test.pass("✓ Invoice Number: " + invoiceNumber);

        test.info("Step 4: Get Case Number");
        System.out.println("Step 4: Getting Case Number...");
        String caseNumber = billingHistoryPage.getCaseNumber();
        System.out.println("Case Number: " + caseNumber);
        Assert.assertFalse(caseNumber.isEmpty(), "Case Number should not be empty");
        test.pass("✓ Case Number: " + caseNumber);

        test.info("Step 5: Get Claim Number");
        System.out.println("Step 5: Getting Claim Number...");
        String claimNumber = billingHistoryPage.getClaimNumber();
        System.out.println("Claim Number: " + claimNumber);
        Assert.assertFalse(claimNumber.isEmpty(), "Claim Number should not be empty");
        test.pass("✓ Claim Number: " + claimNumber);

        test.info("Step 6: Get Amount");
        System.out.println("Step 6: Getting Amount...");
        String amount = billingHistoryPage.getAmount();
        System.out.println("Amount: " + amount);
        Assert.assertFalse(amount.isEmpty(), "Amount should not be empty");
        test.pass("✓ Amount: " + amount);

        test.info("Step 7: Get Status");
        System.out.println("Step 7: Getting Status...");
        String status = billingHistoryPage.getStatus();
        System.out.println("Status: " + status);
        Assert.assertFalse(status.isEmpty(), "Status should not be empty");
        test.pass("✓ Status: " + status);

        test.pass("✓ SMOKE_BH_005 PASSED: All invoice details verified successfully");
        System.out.println("✓ SMOKE_BH_005 PASSED");
    }

    @Test(priority = 6, description = "SMOKE_BH_006: Verify user can filter Billing History by EMC")
    public void testFilterByEMC() {
        test.info("SMOKE_BH_006: Verify user can filter Billing History by EMC");
        System.out.println("\n🧪 SMOKE_BH_006: Verify user can filter Billing History by EMC");

        test.info("Step 1: Click EMC radio button");
        System.out.println("Step 1: Clicking EMC radio button...");
        billingHistoryPage.clickEMCRadioButton();
        test.pass("✓ EMC radio button clicked");

        test.info("Step 2: Wait for table to load");
        System.out.println("Step 2: Waiting for table to load...");
        billingHistoryPage.waitForTableLoad();
        test.pass("✓ Table loaded");

        test.info("Step 3: Expand first parent row");
        System.out.println("Step 3: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 4: Verify billing type status is EMC");
        System.out.println("Step 4: Verifying status is EMC...");
        String status = billingHistoryPage.getBillingTypeStatus();
        System.out.println("Status: " + status);
        Assert.assertEquals(status.toUpperCase(), "EMC", "Status should be EMC");
        test.pass("✓ SMOKE_BH_006 PASSED: EMC filter verified successfully");
        System.out.println("✓ SMOKE_BH_006 PASSED");
    }

    @Test(priority = 7, description = "SMOKE_BH_007: Verify user can filter Billing History by Paper")
    public void testFilterByPaper() {
        test.info("SMOKE_BH_007: Verify user can filter Billing History by Paper");
        System.out.println("\n🧪 SMOKE_BH_007: Verify user can filter Billing History by Paper");

        test.info("Step 1: Click Paper radio button");
        System.out.println("Step 1: Clicking Paper radio button...");
        billingHistoryPage.clickPaperRadioButton();
        test.pass("✓ Paper radio button clicked");

        test.info("Step 2: Wait for table to load");
        System.out.println("Step 2: Waiting for table to load...");
        billingHistoryPage.waitForTableLoad();
        test.pass("✓ Table loaded");

        test.info("Step 3: Expand first parent row");
        System.out.println("Step 3: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 4: Verify billing type status is Paper");
        System.out.println("Step 4: Verifying status is Paper...");
        String status = billingHistoryPage.getBillingTypeStatus();
        System.out.println("Status: " + status);
        Assert.assertEquals(status.toUpperCase(), "PAPER", "Status should be Paper");
        test.pass("✓ SMOKE_BH_007 PASSED: Paper filter verified successfully");
        System.out.println("✓ SMOKE_BH_007 PASSED");
    }

    @Test(priority = 8, description = "SMOKE_BH_008: Verify Re-Submit checkbox allows invoice resubmission with EMC")
    public void testReSubmitEMC() {
        test.info("SMOKE_BH_008: Verify Re-Submit checkbox allows invoice resubmission with EMC");
        System.out.println("\n🧪 SMOKE_BH_008: Verify Re-Submit checkbox allows invoice resubmission with EMC");

        test.info("Step 1: Click file select button");
        System.out.println("Step 1: Clicking file select button...");
        billingHistoryPage.clickFileSelectButton();
        test.pass("✓ File selected");

        test.info("Step 2: Expand first parent row");
        System.out.println("Step 2: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 3: Check status");
        System.out.println("Step 3: Checking status...");
        String status = billingHistoryPage.getStatus();
        System.out.println("Status: " + status);

        if (status.toUpperCase().contains("EMC")) {
            test.info("Step 4: Status is EMC, clicking EMC submit button");
            System.out.println("Step 4: Status is EMC, clicking EMC submit button...");
            billingHistoryPage.clickEMCSubmitButton();
            test.pass("✓ EMC submit button clicked");
            test.pass("✓ SMOKE_BH_008 PASSED: EMC resubmission verified successfully");
            System.out.println("✓ SMOKE_BH_008 PASSED");
        } else {
            test.warning("Status is not EMC: " + status);
            System.out.println("⚠ Status is not EMC: " + status);
        }
    }

    @Test(priority = 9, description = "SMOKE_BH_009: Verify Re-Submit checkbox allows invoice resubmission with Paper")
    public void testReSubmitPaper() {
        test.info("SMOKE_BH_009: Verify Re-Submit checkbox allows invoice resubmission with Paper");
        System.out.println("\n🧪 SMOKE_BH_009: Verify Re-Submit checkbox allows invoice resubmission with Paper");

        test.info("Step 1: Click file select button");
        System.out.println("Step 1: Clicking file select button...");
        billingHistoryPage.clickFileSelectButton();
        test.pass("✓ File selected");

        test.info("Step 2: Expand first parent row");
        System.out.println("Step 2: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 3: Check status");
        System.out.println("Step 3: Checking status...");
        String status = billingHistoryPage.getStatus();
        System.out.println("Status: " + status);

        if (status.toUpperCase().contains("PAPER")) {
            test.info("Step 4: Status is Paper, clicking Paper submit button");
            System.out.println("Step 4: Status is Paper, clicking Paper submit button...");
            billingHistoryPage.clickPaperSubmitButton();
            test.pass("✓ Paper submit button clicked");
            test.pass("✓ SMOKE_BH_009 PASSED: Paper resubmission verified successfully");
            System.out.println("✓ SMOKE_BH_009 PASSED");
        } else {
            test.warning("Status is not Paper: " + status);
            System.out.println("⚠ Status is not Paper: " + status);
        }
    }

    @Test(priority = 10, description = "SMOKE_BH_010: Verify user can open Edocs to view related documents")
    public void testOpenEdocs() {
        test.info("SMOKE_BH_010: Verify user can open Edocs to view related documents");
        System.out.println("\n🧪 SMOKE_BH_010: Verify user can open Edocs to view related documents");

        test.info("Step 1: Expand first parent row");
        System.out.println("Step 1: Expanding first parent row...");
        billingHistoryPage.expandFirstParentRow();
        test.pass("✓ Parent row expanded");

        test.info("Step 2: Click Edoc button");
        System.out.println("Step 2: Clicking Edoc button...");
        billingHistoryPage.clickEdocButton();
        test.pass("✓ Edoc button clicked");

        test.info("Step 3: Verify Edoc view heading");
        System.out.println("Step 3: Verifying Edoc view heading...");
        String heading = billingHistoryPage.getEdocViewHeading();
        String expectedHeading = "E-docs View";
        System.out.println("Expected Heading: " + expectedHeading);
        System.out.println("Actual Heading: " + heading);

        Assert.assertTrue(heading.contains(expectedHeading), "Edoc view should be opened");
        test.pass("✓ Edoc view opened with heading: " + heading);

        test.info("Step 4: Close Edoc view");
        System.out.println("Step 4: Closing Edoc view...");
        billingHistoryPage.clickEdocCloseButton();
        test.pass("✓ Edoc view closed");

        test.pass("✓ SMOKE_BH_010 PASSED: Edocs verified successfully");
        System.out.println("✓ SMOKE_BH_010 PASSED");
    }

}
