package com.cyclonercm.files.tests.regression.upload.alternative;

import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.files.base.BaseTest;
import com.cyclonercm.files.pages.FileUploadPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AlternativeFileUploadTest extends BaseTest {

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

        @Test(priority = 1, description = "UPLOAD_ALT_001 - Verify cannot select the muliple catergories are selected")
    public void UPLOAD_ALT_001() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            fileUploadPage.selectCollectionsCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            Assert.assertTrue(fileUploadPage.isCollectionsCheckboxSelected(), "Collections checkbox should be selected");
            int selectedCount = fileUploadPage.getSelectedCheckboxesCount();
            Assert.assertEquals(selectedCount, 2, "Two checkboxes should be selected");
            test.pass("UPLOAD_ALT_001 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_001 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "UPLOAD_ALT_002 - Verify behavior when same file is uploaded to different categories")
    public void UPLOAD_ALT_002() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_002 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_002 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "UPLOAD_ALT_003 - Verify upload behavior with slow network connection")
    public void UPLOAD_ALT_003() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_003 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_003 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "UPLOAD_ALT_004 - Verify upload behavior with intermittent network connection")
    public void UPLOAD_ALT_004() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_004 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_004 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "UPLOAD_ALT_005 - Verify upload cancellation workflow")
    public void UPLOAD_ALT_005() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            fileUploadPage.clickCloseButton();
            WaitUtils.sleep(2000);
            Assert.assertFalse(fileUploadPage.isFileUploadModalDisplayed(), "Modal should be closed");
            test.pass("UPLOAD_ALT_005 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_005 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "UPLOAD_ALT_006 - Verify batch upload with mixed file types")
    public void UPLOAD_ALT_006() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_006 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_006 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "UPLOAD_ALT_007 - Verify upload workflow restart after browser refresh")
    public void UPLOAD_ALT_007() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            driver.navigate().refresh();
            WaitUtils.sleep(3000);
            test.pass("UPLOAD_ALT_007 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_007 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "UPLOAD_ALT_008 - Verify upload of minimum supported file size (1KB)")
    public void UPLOAD_ALT_008() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_008 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_008 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "UPLOAD_ALT_009 - Verify upload of maximum supported file size")
    public void UPLOAD_ALT_009() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_009 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_009 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "UPLOAD_ALT_010 - Verify upload of files with Unicode characters in names")
    public void UPLOAD_ALT_010() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_010 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_010 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 11, description = "UPLOAD_ALT_011 - Verify upload from different network networks")
    public void UPLOAD_ALT_011() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_011 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_011 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 12, description = "UPLOAD_ALT_012 - Verify upload of files with same names but different extensions")
    public void UPLOAD_ALT_012() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
            test.pass("UPLOAD_ALT_012 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_ALT_012 failed: " + e.getMessage());
            throw e;
        }
    }
}
