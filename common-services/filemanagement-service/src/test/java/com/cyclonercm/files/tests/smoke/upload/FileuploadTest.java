package com.cyclonercm.files.tests.smoke.upload;

import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.files.base.SmokeBaseTest;
import com.cyclonercm.files.pages.FileUploadPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.UploadSmokeTestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class FileuploadTest extends SmokeBaseTest {

    private AuthenticationPage loginPage;
    private FileUploadPage fileUploadPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        fileUploadPage = new FileUploadPage(driver);

        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 30);

        String actualSystemLabelText = loginPage.getSystemLabelText();
        String expectedSystemLabelText = UploadSmokeTestDataProperties.get("systemLabel");

        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = UploadSmokeTestDataProperties.get("buildNumber");

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

        loginPage.enterUsername(UploadSmokeTestDataProperties.get("validUserId"));
        loginPage.enterPassword(UploadSmokeTestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();


    }

    @Test(priority = 1, description = "UPLOAD_POS_001 - Verify successful single file upload to Billing category")
    public void TC_AUT_001() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.getStartedButton, 120);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = fileUploadPage.getGetStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }

        fileUploadPage.clickGetStartedButton();


        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));

        fileUploadPage.selectInterpreterCheckbox();
        WaitUtils.sleep(2000);
        //Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
        //Assert.assertTrue(fileUploadPage.isUploadButtonEnabled(), "Upload button should be enabled");

        WaitUtils.sleep(2000);

        String relativePath = UploadSmokeTestDataProperties.get("uploadFilePathDMS1");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        fileUploadPage.addFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_001 passed");

    }

    @Test(priority = 2, description = "UPLOAD_POS_002 - Verify successful multiple file upload to same category (Billing)")
    public void TC_AUT_002() {

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
        fileUploadPage.clickGetStartedButton();
        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));

        fileUploadPage.selectInterpreterCheckbox();
        WaitUtils.sleep(2000);

        // Upload multiple files (3 files for this test)
        String[] fileKeys = {
            "uploadMultipleFilePathDMS2",
            "uploadMultipleFilePathDMS3"
        };

        for (int i = 0; i < fileKeys.length; i++) {
            String relativePath = UploadSmokeTestDataProperties.get(fileKeys[i]);
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();

            System.out.println("Uploading file " + (i + 1) + ": " + absolutePath);
            fileUploadPage.addFile(absolutePath);
            WaitUtils.sleep(2000); // Wait between file uploads
        }

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "Multiple file upload should succeed");
        test.pass("UPLOAD_POS_002 passed - Successfully uploaded " + fileKeys.length + " files");

    }
}
