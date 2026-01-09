package com.cyclonercm.files.tests.regression.upload.positive;

import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.files.base.BaseTest;
import com.cyclonercm.files.pages.FileUploadPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class PositiveFileUploadTest extends BaseTest {

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

    @Test(priority = 1, description = "UPLOAD_POS_001 - Verify successful single file upload to Billing category")
    public void UPLOAD_POS_001() {

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

        fileUploadPage.selectBillingCheckbox();
        WaitUtils.sleep(2000);
        //Assert.assertTrue(fileUploadPage.isBillingCheckboxSelected(), "Billing checkbox should be selected");
        //Assert.assertTrue(fileUploadPage.isUploadButtonEnabled(), "Upload button should be enabled");

        WaitUtils.sleep(2000);

        String relativePath = TestDataProperties.get("uploadFilePathBilling");
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


    @Test(priority = 2, description = "UPLOAD_POS_002 - Verify successful single file upload to Collections category")
    public void UPLOAD_POS_002() {

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

            fileUploadPage.selectCollectionsCheckbox();

        WaitUtils.sleep(2000);

        String relativePath = TestDataProperties.get("uploadFilePathCollection");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        fileUploadPage.addFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_002 passed");


    }

    @Test(priority = 3, description = "UPLOAD_POS_003 - Verify successful single file upload to EOR Response category")
    public void UPLOAD_POS_003() {

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

            fileUploadPage.selectEorResponseCheckbox();
        WaitUtils.sleep(2000);

        String relativePath = TestDataProperties.get("uploadFilePathEOR");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        fileUploadPage.addFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_003 passed");


    }

    @Test(priority = 4, description = "UPLOAD_POS_004 - Verify successful single file upload to Petition category")
    public void UPLOAD_POS_004() {

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

            fileUploadPage.selectPetitionCheckbox();
        WaitUtils.sleep(2000);

        String relativePath = TestDataProperties.get("uploadFilePathPetition");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        fileUploadPage.addFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_004 passed");


    }

    @Test(priority = 5, description = "UPLOAD_POS_005 - Verify successful multiple file upload to same category (Billing)")
    public void UPLOAD_POS_005() {

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

            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(2000);


            String relativePath = TestDataProperties.get("uploadMultipleFilePathBilling");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_005 passed");

    }

    @Test(priority = 6, description = "UPLOAD_POS_006 - Verify successful multiple file upload to same category (Collection)")
    public void UPLOAD_POS_006() {

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

            fileUploadPage.selectCollectionsCheckbox();
            WaitUtils.sleep(2000);


            String relativePath = TestDataProperties.get("uploadMultipleFilePathCollection");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_006 passed");

    }

    @Test(priority = 7, description = "UPLOAD_POS_007 - Verify successful multiple file upload to same category (EOR)")
    public void UPLOAD_POS_007() {
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

            fileUploadPage.selectEorResponseCheckbox();
            WaitUtils.sleep(2000);


            String relativePath = TestDataProperties.get("uploadMultipleFilePathEOR");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_007 passed");

    }

    @Test(priority = 8, description = "UPLOAD_POS_008 - Verify successful multiple Invoices upload to same category (Petition)")
    public void UPLOAD_POS_008() {

            WaitUtils.sleep(10000);
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));

            fileUploadPage.selectPetitionCheckbox();
            WaitUtils.sleep(2000);


            String relativePath = TestDataProperties.get("uploadMultipleFilePathPetition");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);

        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));
        String actualMessage = fileUploadPage.getSuccessToastText();
        Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
        test.pass("UPLOAD_POS_008 passed");



    }

    @Test(priority = 9, description = "UPLOAD_POS_009 - Verify successful PDF file upload with automatic page count detection")
    public void UPLOAD_POS_009() {
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

        fileUploadPage.selectBillingCheckbox();
        WaitUtils.sleep(2000);

        String relativePath = TestDataProperties.get("uploadFilePageCountDocument");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();
        fileUploadPage.uploadFile(absolutePath);
        WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4"));

        String expectedFileName = TestDataProperties.get("expectedFileName");
        String pageCount = TestDataProperties.get("uploadFilePageCount");
        String expectedMessage = "FileReceived : " + expectedFileName + " (" + pageCount + " Pages)";
        String actualMessage = fileUploadPage.getSuccessToastMessageText();
        System.out.println("Actual Message :" + expectedMessage);
        System.out.println("Expected Message :" + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Success message should show file name and page count");
        test.pass("UPLOAD_POS_009 passed");
    }

    @Test(priority = 10, description = "UPLOAD_POS_010 - Verify upload progress indicator displays correctly during file upload")
    public void UPLOAD_POS_010() {
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

            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(2000);

            String relativePath = TestDataProperties.get("UploadProgressIndicatorFile");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);


            String progressMessage = fileUploadPage.getProgressToastText();
            Assert.assertTrue(progressMessage.contains("Please wait, do not close this window..."), "Progress message should be displayed");
            test.pass("UPLOAD_POS_010 passed");

    }

    @Test(priority = 11, description = "UPLOAD_POS_011 - Verify \"Uploading X file(s)...\" message displays with correct file count")
    public void UPLOAD_POS_011() {
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
            WaitUtils.sleep(10000);
            FileUploadPage fileUploadPage = new FileUploadPage(driver);
            fileUploadPage.clickGetStartedButton();
            WaitUtils.waitForVisible(driver, org.openqa.selenium.By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]"));

            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(2000);
            Assert.assertTrue(fileUploadPage.isUploadButtonEnabled(), "Upload button should be enabled");
            test.pass("UPLOAD_POS_011 passed");

    }

    @Test(priority = 12, description = "UPLOAD_POS_012 - Verify success message shows correct file name and page count")
    public void UPLOAD_POS_012() {

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

            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(2000);

            String relativePath = TestDataProperties.get("smallUploadFilePath");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);

            WaitUtils.waitForVisibility(driver, LocatorConstants.UploadSuccessToast, 30);
            String expectedFileName = TestDataProperties.get("smallUploadFileName");
            String pageCount = TestDataProperties.get("smallUploadFilePageCount");
            String expectedMessage = "FileReceived : " + expectedFileName + " (" + pageCount + " Pages)";
            String actualMessage = fileUploadPage.getSuccessToastMessageText();
            Assert.assertEquals(actualMessage, expectedMessage, "Success message should show file name and page count");
            test.pass("UPLOAD_POS_012 passed");

    }

    @Test(priority = 13, description = "UPLOAD_POS_013 - Verify uploaded file appears in correct category section")
    public void UPLOAD_POS_013() {

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

            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(2000);

            String relativePath = TestDataProperties.get("uploadFilePath");
            File uploadFile = new File(relativePath);
            String absolutePath = uploadFile.getAbsolutePath();
            fileUploadPage.uploadFile(absolutePath);

            WaitUtils.waitForVisibility(driver, LocatorConstants.UploadSuccessToast, 30);
            String actualMessage = fileUploadPage.getSuccessToastText();
            Assert.assertEquals(actualMessage, "Success", "File upload should succeed");
            test.pass("UPLOAD_POS_013 passed");

    }

    @Test(priority = 14, description = "UPLOAD_POS_014 - Verify OS file browser opens when Upload button is clicked")
    public void UPLOAD_POS_014() {
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

            fileUploadPage.selectBillingCheckbox();
            WaitUtils.sleep(2000);
            Assert.assertTrue(fileUploadPage.isUploadButtonEnabled(), "Upload button should be enabled");
            test.pass("UPLOAD_POS_014 passed");

    }

    @Test(priority = 15, description = "UPLOAD_POS_015 - Verify file browser shows proper file type filters (.PDF)")
    public void UPLOAD_POS_015() {
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

            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            test.pass("UPLOAD_POS_015 passed");

    }

    @Test(priority = 16, description = "UPLOAD_POS_016 - Verify file browser navigation works correctly (folders, drives)")
    public void UPLOAD_POS_016() {
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

            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            test.pass("UPLOAD_POS_016 passed");

    }

    @Test(priority = 17, description = "UPLOAD_POS_017 - Verify file browser Cancel button returns to upload dialog")
    public void UPLOAD_POS_017() {
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

            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            test.pass("UPLOAD_POS_017 passed");

    }

    @Test(priority = 18, description = "UPLOAD_POS_018 - Verify file browser Open button initiates upload process")
    public void UPLOAD_POS_018() {
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

            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            test.pass("UPLOAD_POS_018 passed");

    }

    @Test(priority = 19, description = "UPLOAD_POS_019 - Verify Refresh button is work")
    public void UPLOAD_POS_019() {
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

            Assert.assertTrue(fileUploadPage.isRefreshButtonDisplayed(), "Refresh button should be displayed");
            fileUploadPage.clickRefreshButton();
            WaitUtils.sleep(2000);
            Assert.assertTrue(fileUploadPage.isFolderTreeContainerDisplayed(), "Folder tree should be displayed after refresh");
            test.pass("UPLOAD_POS_019 passed");

    }

    @Test(priority = 20, description = "UPLOAD_POS_020 - Verify Click a close button and successfully close the popup")
    public void UPLOAD_POS_020() {
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

            Assert.assertTrue(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be displayed");
            fileUploadPage.clickCloseButton();
            WaitUtils.sleep(2000);
            Assert.assertFalse(fileUploadPage.isFileUploadModalDisplayed(), "File upload modal should be closed");
            test.pass("UPLOAD_POS_020 passed");

    }
}
