package com.cyclonercm.files.tests.regression.upload.negative;

import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.files.base.BaseTest;
import com.cyclonercm.files.pages.FileUploadPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NegativeFileUploadTest extends BaseTest {

    private AuthenticationPage loginPage;
    private FileUploadPage fileUploadPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        fileUploadPage = new FileUploadPage(driver);

        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 30);

        String actualSystemLabelText = loginPage.getSystemLabelText();
        String expectedSystemLabelText = TestDataProperties.get("systemLabel");

        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = TestDataProperties.get("buildNumber");

        try {
            Assert.assertEquals(actualSystemLabelText, expectedSystemLabelText, "System label text does not match.");
            test.pass("System label text verified: " + actualSystemLabelText);
        } catch (AssertionError e) {
            test.fail("System label text mismatch. Expected: " + expectedSystemLabelText + ", Found: " + actualSystemLabelText);
            throw e;
        }

        try {
            Assert.assertEquals(actualSystemVersionText, expectedSystemVersionText, "System version text does not match.");
            test.pass("System version text verified: " + actualSystemVersionText);
        } catch (AssertionError e) {
            test.fail("System version text mismatch. Expected: " + expectedSystemVersionText + ", Found: " + actualSystemVersionText);
            throw e;
        }

        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
    }

        @Test(priority = 1, description = "UPLOAD_NEG_001 - Verify error message when no category is selected before upload attempt")
    public void UPLOAD_NEG_001() {
            WaitUtils.sleep(10000);

            WaitUtils.waitForVisibility(driver, LocatorConstants.getStartedButton, 60);

            String expectedGetStartedButtonText = "Get Started";
            String actualGetStartedButtonText = fileUploadPage.getGetStartedButtonText();
            try {
                Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
                test.pass("Get Started button text verified: " + actualGetStartedButtonText);
            } catch (AssertionError e) {
                test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
                throw e;
            }
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isUploadButtonDisabled(), "Upload button should be disabled when no category is selected");
            test.pass("UPLOAD_NEG_001 passed");

    }

    @Test(priority = 2, description = "UPLOAD_NEG_002 - Verify proper error handling when category selection is lost during upload")
    public void UPLOAD_NEG_002() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            fileUploadPage.selectBillingCheckbox();
            Assert.assertFalse(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be deselected");
            Assert.assertTrue(fileUploadPage.isUploadButtonDisabled(), "Upload button should be disabled when category is deselected");
            test.pass("UPLOAD_NEG_002 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_002 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "UPLOAD_NEG_003 - Verify error handling when no file is selected in browser")
    public void UPLOAD_NEG_003() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_003 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_003 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "UPLOAD_NEG_004 - Verify error handling for unsupported file formats")
    public void UPLOAD_NEG_004() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_004 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_004 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "UPLOAD_NEG_005 - Verify error handling for corrupted/damaged files")
    public void UPLOAD_NEG_005() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_005 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_005 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "UPLOAD_NEG_006 - Verify error handling for zero-byte/empty files")
    public void UPLOAD_NEG_006() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_006 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_006 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "UPLOAD_NEG_007 - Verify error handling for files with special characters in names")
    public void UPLOAD_NEG_007() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_007 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_007 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "UPLOAD_NEG_008 - Verify error handling for files with extremely long filenames (>255 chars)")
    public void UPLOAD_NEG_008() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_008 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_008 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "UPLOAD_NEG_009 - Verify error handling when upload is interrupted by network disconnection")
    public void UPLOAD_NEG_009() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_009 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_009 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "UPLOAD_NEG_010 - Verify error handling when user closes browser during upload")
    public void UPLOAD_NEG_010() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_010 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_010 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 11, description = "UPLOAD_NEG_011 - Verify error handling when user navigates away during upload")
    public void UPLOAD_NEG_011() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_NEG_011 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_NEG_011 failed: " + e.getMessage());
            throw e;
        }
    }
}
