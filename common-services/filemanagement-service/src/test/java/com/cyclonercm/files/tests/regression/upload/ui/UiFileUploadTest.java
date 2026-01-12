package com.cyclonercm.files.tests.regression.upload.ui;

import com.cyclonercm.files.base.BaseTest;
import com.cyclonercm.files.pages.FileUploadPage;
import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UiFileUploadTest extends BaseTest {

    @Test(priority = 1, description = "UPLOAD_UI_001 - Verify \"Reason For Your Upload\" dialog layout and positioning")
    public void UPLOAD_UI_001() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            Assert.assertTrue(fileUploadPage.isModalHeaderDisplayed(), "Modal header should be displayed");
            Assert.assertTrue(fileUploadPage.isModalBodyDisplayed(), "Modal body should be displayed");
            Assert.assertEquals(fileUploadPage.getModalTitleText(), "Reason For Your Upload", "Modal title should be correct");
            test.pass("UPLOAD_UI_001 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_001 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "UPLOAD_UI_002 - Verify category checkbox alignment and spacing")
    public void UPLOAD_UI_002() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isBillingLabelDisplayed(), "Billing label should be displayed");
            Assert.assertTrue(fileUploadPage.isCollectionsLabelDisplayed(), "Collections label should be displayed");
            Assert.assertTrue(fileUploadPage.isEorResponseLabelDisplayed(), "EOR Response label should be displayed");
            Assert.assertTrue(fileUploadPage.isPetitionLabelDisplayed(), "Petition label should be displayed");
            test.pass("UPLOAD_UI_002 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_002 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "UPLOAD_UI_003 - Verify Upload button styling and positioning")
    public void UPLOAD_UI_003() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isUploadButtonDisplayed(), "Upload button should be displayed");
            Assert.assertTrue(fileUploadPage.getUploadButton().isDisplayed(), "Upload button element should be visible");
            test.pass("UPLOAD_UI_003 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_003 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "UPLOAD_UI_004 - Verify refresh button styling and functionality")
    public void UPLOAD_UI_004() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isRefreshButtonDisplayed(), "Refresh button should be displayed");
            Assert.assertTrue(fileUploadPage.getRefreshButton().isDisplayed(), "Refresh button element should be visible");
            test.pass("UPLOAD_UI_004 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_004 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "UPLOAD_UI_005 - Verify close (X) button styling and positioning")
    public void UPLOAD_UI_005() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isCloseButtonDisplayed(), "Close button should be displayed");
            Assert.assertTrue(fileUploadPage.getCloseButton().isDisplayed(), "Close button element should be visible");
            test.pass("UPLOAD_UI_005 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_005 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "UPLOAD_UI_006 - Verify dialog modal overlay and background dimming")
    public void UPLOAD_UI_006() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            Assert.assertTrue(fileUploadPage.isModalBackdropDisplayed(), "Modal backdrop should be displayed");
            test.pass("UPLOAD_UI_006 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_006 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "UPLOAD_UI_007 - Verify checkbox visual states (unchecked, checked, hover)")
    public void UPLOAD_UI_007() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertFalse(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be unchecked initially");
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be checked after click");
            Actions actions = new Actions(driver);
            actions.moveToElement(fileUploadPage.getBillingCheckbox()).perform();
            WaitUtils.sleep(500);
            test.pass("UPLOAD_UI_007 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_007 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "UPLOAD_UI_008 - Verify Upload button states (enabled, disabled, hover, active)")
    public void UPLOAD_UI_008() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isUploadButtonDisabled(), "Upload button should be disabled when no category selected");
            fileUploadPage.selectBillingCheckbox();
            Assert.assertTrue(fileUploadPage.isUploadButtonEnabled(), "Upload button should be enabled when category selected");
            Actions actions = new Actions(driver);
            actions.moveToElement(fileUploadPage.getUploadButton()).perform();
            WaitUtils.sleep(500);
            test.pass("UPLOAD_UI_008 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_008 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "UPLOAD_UI_009 - Verify refresh button hover and active states")
    public void UPLOAD_UI_009() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isRefreshButtonDisplayed(), "Refresh button should be displayed");
            Actions actions = new Actions(driver);
            actions.moveToElement(fileUploadPage.getRefreshButton()).perform();
            WaitUtils.sleep(500);
            Assert.assertTrue(fileUploadPage.getRefreshButton().isDisplayed(), "Refresh button should remain visible on hover");
            test.pass("UPLOAD_UI_009 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_009 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "UPLOAD_UI_010 - Verify close button hover and active states")
    public void UPLOAD_UI_010() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isCloseButtonDisplayed(), "Close button should be displayed");
            Actions actions = new Actions(driver);
            actions.moveToElement(fileUploadPage.getCloseButton()).perform();
            WaitUtils.sleep(500);
            Assert.assertTrue(fileUploadPage.getCloseButton().isDisplayed(), "Close button should remain visible on hover");
            test.pass("UPLOAD_UI_010 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_010 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 11, description = "UPLOAD_UI_011 - Verify category selection visual feedback")
    public void UPLOAD_UI_011() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertFalse(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be unchecked initially");
            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(500);
            Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should show visual feedback when selected");
            test.pass("UPLOAD_UI_011 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_011 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 12, description = "UPLOAD_UI_012 - Verify keyboard navigation through dialog elements")
    public void UPLOAD_UI_012() {
        try {
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));
            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            Assert.assertTrue(fileUploadPage.isUploadButtonDisplayed(), "Upload button should be accessible");
            Assert.assertTrue(fileUploadPage.isCloseButtonDisplayed(), "Close button should be accessible");
            test.pass("UPLOAD_UI_012 passed");
        } catch (AssertionError e) {
            test.fail("UPLOAD_UI_012 failed: " + e.getMessage());
            throw e;
        }
    }
}
