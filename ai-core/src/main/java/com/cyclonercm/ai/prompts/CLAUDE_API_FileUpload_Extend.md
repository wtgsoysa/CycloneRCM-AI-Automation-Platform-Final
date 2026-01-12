Now we have successfully create the all Positive , nagative , alternative and UI test cases related to the File Upload.

This is a File Upload Smoke Pack Test Pack I have created and verify successfully run.

First You need to analysis those test cases and You have a complete Idea of Regression pack
you already generated test cases for File Upload module.

Without running I have see some issues , gaps in you generate test scripts.
     Ex:-
         1. Upload file is not added properly. You need to real time upload a file through the folders.

Compare with Smoke Pack Automation script you can get a deep idea about what miss by you.

Get through the idea of those test cases and then regenerate the complete Regression Pack for File Upload module.
This time there are cannot be any miss or issues in the test scripts.

please maiantain the test data properties file properly to avoid hard coding.
src/test/resources/testdata.properties

please consider don't change your xpath in anytime please maintain. If you only have all access to change but cannot change the X paths you have alraedy generated.

API USAGE & COST GOVERNANCE

Total File Upload regenerate automation budget: $1.2

Target cost: $1.0



____________________________________________________________________________
package com.cyclone.matrix.pages;

import com.cyclone.matrix.utils.WaitUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class UploadPage {

    private WebDriver driver;

    public UploadPage(WebDriver driver) {
        this.driver = driver;
    }

    //----Locators----//

    //----Upload Page ----//
    private final By getStartedButton = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/button[1]");
    private final By uploadModal = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form");
    private final By uploadModalLabel = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[1]/p-toolbar/div/div[1]/span");
    private final By billingFolder = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[1]/li/div/span[2]/span");
    private final By collectionFolder = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[2]/li/div/span[2]/span");
    private final By eorFolder = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[3]/li/div/span[2]/span");
    private final By petitionFolder = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[4]/li/div/span[2]/span");
    private final By billingCheckbox = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[1]/li/div/div/div");
    private final By collectionCheckbox = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[2]/li/div/div/div");
    private final By eorCheckbox = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[3]/li/div/span[2]/span");
    private final By petitionCheckbox = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[4]/li/div/div/div");
    private final By uploadButton = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[1]/p-toolbar/div/div[2]/p-fileupload/div");


    //----Toast Locators ----//
    private final By uploadProgressToast = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/div/div/div[3]");
    private final By uploadSuccessToast = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4");
    private final By uploadSelectFolderToast = By.xpath("/html/body/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div/div/p");
    private final By uploadDuplicateToast = By.xpath("/html/body/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div/h4");
    private final By UploadErrorToast = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[1]/p-toolbar/div/div[2]/p-fileupload/div/p-messages/div/div/div/span[3]");
    private final By SuccessToastText = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p");
    


    //----Methods----//

    public void clickGetStartedButton(){
        driver.findElement(getStartedButton).click();
    }

    public String getStartedButtonText(){
        return driver.findElement(getStartedButton).getText().trim();
    }

    public boolean isUploadModalDisplayed(){
        return driver.findElement(uploadModal).isDisplayed();
    }

    public String getUploadModalLabelText(){
        return driver.findElement(uploadModalLabel).getText().trim();
    }

    public String getBillingFolderText(){
        return driver.findElement(billingFolder).getText().trim();
    }

    public String getCollectionFolderText() {
        return driver.findElement(collectionFolder).getText().trim();
    }

    public String getEorFolderText() {
        return driver.findElement(eorFolder).getText().trim();
    }

    public String getPetitionFolderText() {
        return driver.findElement(petitionFolder).getText().trim();
    }

    public String getProgressToastText(){
        return driver.findElement(uploadProgressToast).getText().trim();
    }

    public String getSuccessToastText(){
        return driver.findElement(uploadSuccessToast).getText().trim();
    }

    public String getSelectFolderToastText(){
        return driver.findElement(uploadSelectFolderToast).getText().trim();
    }

    public String getDuplicateToastText(){
        return driver.findElement(uploadDuplicateToast).getText().trim();
    }

    public String getErrorToastText(){
        return driver.findElement(UploadErrorToast).getText().trim();
    }

    public void clickBillingCheckbox(){
        driver.findElement(billingCheckbox).click();
    }


    public void clickUploadButton(){
        driver.findElement(uploadButton).click();
    }

    public void debugUploadButtonState() {
        System.out.println("=== DEBUG: Upload Button State ===");
        try {
            By fileInputLocator = By.xpath("//p-fileupload//input[@type='file' and @accept='application/pdf' and @multiple]");
            WebElement fileInput = driver.findElement(fileInputLocator);

            System.out.println("File Input disabled attr: " + fileInput.getAttribute("disabled"));
            System.out.println("File Input isEnabled(): " + fileInput.isEnabled());
            System.out.println("File Input pointer-events: " + fileInput.getCssValue("pointer-events"));
            System.out.println("File Input display: " + fileInput.getCssValue("display"));

            WebElement uploadContainer = driver.findElement(uploadButton);
            System.out.println("Container class: " + uploadContainer.getAttribute("class"));

            try {
                WebElement chooseBtn = uploadContainer.findElement(By.xpath(".//button"));
                System.out.println("Button found: YES");
                System.out.println("Button disabled attr: " + chooseBtn.getAttribute("disabled"));
                System.out.println("Button class: " + chooseBtn.getAttribute("class"));
                System.out.println("Button isDisplayed(): " + chooseBtn.isDisplayed());
                System.out.println("Button isEnabled(): " + chooseBtn.isEnabled());
                System.out.println("Button pointer-events: " + chooseBtn.getCssValue("pointer-events"));
            } catch (NoSuchElementException e) {
                System.out.println("Button found: NO");
            }
        } catch (Exception e) {
            System.err.println("Error during debug: " + e.getMessage());
        }
        System.out.println("=== END DEBUG ===");
    }

    public boolean isUploadButtonEnabled(){
        try {
            // Check the file input element which is what gets disabled
            By fileInputLocator = By.xpath("//p-fileupload//input[@type='file' and @accept='application/pdf' and @multiple]");
            WebElement fileInput = driver.findElement(fileInputLocator);

            // Primary check: disabled attribute (will be "true" when disabled, null when enabled)
            String disabledAttr = fileInput.getAttribute("disabled");
            if (disabledAttr != null && disabledAttr.equals("true")) {
                return false; // Explicitly disabled
            }

            // Secondary check: Selenium's isEnabled method
            if (!fileInput.isEnabled()) {
                return false; // Selenium considers it disabled
            }

            // Tertiary check: pointer-events CSS (will be "none" when disabled, "auto" when enabled)
            String pointerEvents = fileInput.getCssValue("pointer-events");
            if (pointerEvents != null && pointerEvents.equals("none")) {
                return false; // Pointer events disabled
            }

            // NOTE: Do NOT check display:none - file inputs are always hidden in PrimeNG

            return true; // All checks passed, button is enabled

        } catch (NoSuchElementException e) {
            System.err.println("Upload button/input not found: " + e.getMessage());
            return false;
        }
    }

    public String getSuccessToastMessageText(){
        return driver.findElement(SuccessToastText).getText().trim();
    }



    //----Upload File Methods----//
    public void addFile(String filePath) {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Upload file does not exist: " + filePath);
        }
        if (!file.isAbsolute()) {
            throw new RuntimeException("Upload file path is not absolute: " + filePath);
        }

        try {
            By fileInputLocator = By.xpath(
                    "//p-fileupload//input[@type='file' and @accept='application/pdf' and @multiple]"
            );

            WebElement fileInput = WaitUtils.waitForElementPresent(driver, fileInputLocator, 60);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", fileInput);

            fileInput.sendKeys(filePath);

            System.out.println("File uploaded successfully: " + filePath);

        } catch (Exception e) {
            System.err.println("File upload failed: " + e.getMessage());
            throw e;
        }
    }

}

package com.cyclone.matrix.tests.fileuploadTestSuite;

import com.cyclone.matrix.base.BaseTest;
import com.cyclone.matrix.pages.LoginPage;
import com.cyclone.matrix.pages.UploadPage;
import com.cyclone.matrix.utils.LocatorConstants;
import com.cyclone.matrix.utils.TestDataProperties;
import com.cyclone.matrix.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class UploadTest extends BaseTest {
private LoginPage loginPage;
private UploadPage uploadPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        uploadPage = new UploadPage(driver);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 60);

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

    @Test(priority = 1, description = "SMOKE_FU_001 - Verify user can open File Upload modal and view available categories (Billing, Collections, EOR Response, Petition")
    public void SMOKE_FU_001() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }

        WaitUtils.sleep(10000);

        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }


        // Verify Billing category

        String expectedBillingText = "Billing";
        String actualBillingText = uploadPage.getBillingFolderText();

        try {
            Assert.assertEquals(actualBillingText, expectedBillingText, "Billing folder text match.");
            test.pass("Billing folder text verified: " + actualBillingText);
        } catch (AssertionError e) {
            test.fail("Billing folder text mismatch. Expected: " + expectedBillingText + ", Found: " + actualBillingText);
            throw e;
        }

        // Verify Collections category

        String expectedCollectionsText = "Collections";
        String actualCollectionsText = uploadPage.getCollectionFolderText();

        try {
            Assert.assertEquals(actualCollectionsText, expectedCollectionsText, "Collections folder text match.");
            test.pass("Collections folder text verified: " + actualCollectionsText);
        } catch (AssertionError e) {
            test.fail("Collections folder text mismatch. Expected: " + expectedCollectionsText + ", Found: " + actualCollectionsText);
            throw e;
        }

        // Verify EOR Response category

        String expectedEORResponseText = "EOR Response";
        String actualEORResponseText = uploadPage.getEorFolderText();

        try {
            Assert.assertEquals(actualEORResponseText, expectedEORResponseText, "EOR Response folder text match.");
            test.pass("EOR Response folder text verified: " + actualEORResponseText);
        } catch (AssertionError e) {
            test.fail("EOR Response folder text mismatch. Expected: " + expectedEORResponseText + ", Found: " + actualEORResponseText);
            throw e;
        }

        // Verify Petition category

        String expectedPetitionText = "Petition";
        String actualPetitionText = uploadPage.getPetitionFolderText();

        try {
            Assert.assertEquals(actualPetitionText, expectedPetitionText, "Petition folder text match.");
            test.pass("Petition folder text verified: " + actualPetitionText);
        } catch (AssertionError e) {
            test.fail("Petition folder text mismatch. Expected: " + expectedPetitionText + ", Found: " + actualPetitionText);
            throw e;
        }

    }

    @Test(priority = 2, description = "SMOKE_FU_002 - Verify upload button is disabled without selecting a folder (user must select folder to enable upload)")
    public void SMOKE_FU_002() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }

        WaitUtils.sleep(10000);

        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Verify upload button is disabled without selecting a folder
        WaitUtils.sleep(2000);

        try {
            Assert.assertFalse(uploadPage.isUploadButtonEnabled(), "Upload button should be disabled without folder selection.");
            test.pass("Upload button is disabled as expected without folder selection.");
        } catch (AssertionError e) {
            test.fail("Upload button is enabled when it should be disabled without folder selection.");
            throw e;
        }

        // Verify upload button becomes enabled after selecting a folder
        uploadPage.clickBillingCheckbox();
        WaitUtils.sleep(1000);

        try {
            Assert.assertTrue(uploadPage.isUploadButtonEnabled(), "Upload button should be enabled after selecting a folder.");
            test.pass("Upload button is enabled after selecting Billing folder.");
        } catch (AssertionError e) {
            test.fail("Upload button is disabled when it should be enabled after folder selection.");
            throw e;
        }

    }

    @Test(priority = 3, description = "SMOKE_FU_003 - Verify user can successfully upload one or multiple valid PDF files and receive success confirmation)")
    public void SMOKE_FU_003() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }


        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and upload a valid PDF file

        uploadPage.clickBillingCheckbox();

        WaitUtils.sleep(2500);

        String relativePath = TestDataProperties.get("uploadFilePath");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadSuccessToast, 30);

        String expectedValidationMessage = "Success";
        String actualValidationMessage = uploadPage.getSuccessToastText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "File upload should succeed with folder selected.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }


    }

    @Test(priority = 4, description = "SMOKE_FU_004 - Verify system rejects non-PDF file formats and displays appropriate error message)")
    public void SMOKE_FU_004() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }

        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and attempt to upload a non-PDF file

        uploadPage.clickBillingCheckbox();

        WaitUtils.sleep(2500);

        String relativePath = TestDataProperties.get("uploadFilePath2");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadErrorToast, 30);

        String expectedValidationMessage = "allowed file types: application/pdf.";
        String actualValidationMessage = uploadPage.getErrorToastText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "System should reject non-PDF file formats.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }

    }

    @Test(priority = 5, description = "SMOKE_FU_005 - Verify system displays “File Already Exist” message when uploading a duplicate file)")
    public void SMOKE_FU_005() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }


        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and attempt to upload a duplicate file

        uploadPage.clickBillingCheckbox();

        String relativePath = TestDataProperties.get("uploadFilePath");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadDuplicateToast, 30);

        String expectedValidationMessage = "File Already Exists!";
        String actualValidationMessage = uploadPage.getDuplicateToastText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "System should detect and reject duplicate files.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }

    }

    @Test(priority = 6, description = "SMOKE_FU_006 - Verify upload progress indicator is displayed while files are being uploaded)")
    public void SMOKE_FU_006() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }


        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and upload file to verify progress indicator

        uploadPage.clickBillingCheckbox();

        String relativePath = TestDataProperties.get("uploadFilePath");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadProgressToast, 30);

        String expectedValidationMessage = "Please wait, do not close this window...";
        String actualValidationMessage = uploadPage.getProgressToastText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Upload progress indicator should be displayed during file upload.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }

    }

    @Test(priority = 7, description = "SMOKE_FU_007 - Verify user can successfully upload one or multiple valid PDF files and receive success confirmation)")
    public void SMOKE_FU_007() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }


        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and upload a PDF file

        uploadPage.clickBillingCheckbox();

        String relativePath = TestDataProperties.get("uploadFilePath3");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadSuccessToast, 30);

        String expectedFileName = TestDataProperties.get("expectedFileName");
        String pageCount = TestDataProperties.get("uploadFilePageCount");
        String expectedValidationMessage = "FileReceived : " + expectedFileName + " (" + pageCount + " Pages)";

        String actualValidationMessage = uploadPage.getSuccessToastMessageText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Success message with file name and page count should match.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }

    }

    @Test(priority = 8, description = "SMOKE_FU_008 - Verify small file successfully upload to the Billing)")
    public void SMOKE_FU_008() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }


        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and upload a small PDF file (5 or fewer pages)

        uploadPage.clickBillingCheckbox();

        String relativePath = TestDataProperties.get("smallUploadFilePath");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadSuccessToast, 30);

        String expectedFileName = TestDataProperties.get("smallUploadFileName");
        String pageCount = TestDataProperties.get("smallUploadFilePageCount");
        // Validate page count is a number and 5 or fewer
        try{
            int pageCountInt = Integer.parseInt(pageCount);
            if(pageCountInt <=5){
                test.pass("File contains 5 or fewer pages: " + pageCount);
            } else {
                test.fail("File contains more than 5 pages: " + pageCount);
            }
        } catch (NumberFormatException e){
            test.fail("Page count is not a valid number: " + pageCount);
        }

        String expectedValidationMessage = "FileReceived : " + expectedFileName + " (" + pageCount + " Pages)";

        String actualValidationMessage = uploadPage.getSuccessToastMessageText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Success message with file name match.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }

    }

    @Test(priority = 9, description = "SMOKE_FU_009 - Verify Large file successfully upload to the Billing)")
    public void SMOKE_FU_009() {

        WaitUtils.sleep(10000);

        WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }


        uploadPage.clickGetStartedButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadModal, 60);
        try {
            Assert.assertTrue(uploadPage.isUploadModalDisplayed(), "File Upload modal is displayed.");
            test.pass("File Upload modal is displayed successfully.");
        } catch (AssertionError e) {
            test.fail("File Upload modal is not displayed.");
            throw e;
        }
        // Verify Upload modal title
        String expectedModalTitle = "Reason For Your Upload";
        String actualModalTitle = uploadPage.getUploadModalLabelText();

        try {
            Assert.assertEquals(actualModalTitle, expectedModalTitle, "Upload modal title match.");
            test.pass("Upload modal title verified: " + actualModalTitle);
        } catch (AssertionError e) {
            test.fail("Upload modal title mismatch. Expected: " + expectedModalTitle + ", Found: " + actualModalTitle);
            throw e;
        }

        // Select Billing folder and upload a large PDF file (6 or more pages)

        uploadPage.clickBillingCheckbox();
        WaitUtils.sleep(2500);

        String relativePath = TestDataProperties.get("largeUploadFilePath");
        File uploadFile = new File(relativePath);
        String absolutePath = uploadFile.getAbsolutePath();

        // HARD DEBUG — DO NOT REMOVE
        System.out.println("FINAL UPLOAD PATH USED: " + absolutePath);

        uploadPage.addFile(absolutePath);

        WaitUtils.waitForVisibility(driver, LocatorConstants.UploadSuccessToast, 30);

        String expectedFileName = TestDataProperties.get("largeUploadFileName");
        String pageCount = TestDataProperties.get("largeUploadFilePageCount");
        // Validate page count is a number and 6 or more
        try{
            int pageCountInt = Integer.parseInt(pageCount);
            if(pageCountInt >=6){
                test.pass("File contains 6 or more pages: " + pageCount);
            } else {
                test.fail("File contains fewer than 6 pages: " + pageCount);
            }
        } catch (NumberFormatException e){
            test.fail("Page count is not a valid number: " + pageCount);
        }

        String expectedValidationMessage = "FileReceived : " + expectedFileName + " (" + pageCount + " Pages)";

        String actualValidationMessage = uploadPage.getSuccessToastMessageText();

        try {
            Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Success message with file name match.");
            test.pass("Validation message verified: " + actualValidationMessage);
        } catch (AssertionError e) {
            test.fail("Validation message mismatch. Expected: " + expectedValidationMessage + ", Found: " + actualValidationMessage);
            throw e;
        }


    }



}
