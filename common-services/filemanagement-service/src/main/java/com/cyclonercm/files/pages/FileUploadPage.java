package com.cyclonercm.files.pages;

import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.*;
import java.io.File;
import java.util.List;

public class FileUploadPage {
    private WebDriver driver;

    public FileUploadPage(WebDriver driver) {
        this.driver = driver;
    }



    private final By getStartedButton = By.xpath("//div[@class='clearfix system-color- topbar']//button[1]");
    private final By fileUploadModalContainer = By.xpath("//div[@role='dialog' and contains(., 'Reason For Your Upload')]");
    private final By modalBackdrop = By.xpath("//div[contains(@class, 'modal-backdrop') or contains(@class, 'overlay') or contains(@class, 'p-dialog-mask')]");
    private final By modalTitle = By.xpath("//*[normalize-space(text())='Reason For Your Upload']");
    private final By modalHeader = By.xpath("//div[contains(@class, 'modal-header') or contains(@class, 'p-dialog-header')]");
    private final By modalBody = By.xpath("//div[contains(@class, 'modal-body') or contains(@class, 'p-dialog-content')]");
    private final By modalFooter = By.xpath("//div[contains(@class, 'modal-footer') or contains(@class, 'p-dialog-footer')]");
    private final By fileUploadForm = By.xpath("//form[.//input[@type='checkbox'] and .//button[contains(text(), 'Upload')]]");

    private final By uploadButton = By.xpath("//button[normalize-space(text())='Upload']");
    private final By refreshButton = By.xpath("//button[@aria-label='Refresh' or contains(@title, 'Refresh')]");
    private final By closeButton = By.xpath("//button[@aria-label='Close' or normalize-space(text())='×']");
    private final By cancelButton = By.xpath("//button[normalize-space(text())='Cancel']");

    private final By folderTreeContainer = By.xpath("//div[@role='tree' or contains(@class, 'tree') or contains(@class, 'folder-list')]");
    private final By folderTreeRoot = By.xpath("//div[@role='tree']/*[1] | //ul[contains(@class, 'tree-root')]");

    private final By billingCheckbox = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[2]/div/div/p-tree/div/div/ul/p-treenode[1]/li/div/div/div");
    private final By billingLabel = By.xpath("//label[normalize-space(text())='Billing']");
    private final By billingIcon = By.xpath("//label[text()='Billing']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']");
    private final By billingExpandArrow = By.xpath("//label[text()='Billing']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow') or contains(@class, 'toggle')]");
    private final By billingFolderContainer = By.xpath("//label[text()='Billing']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]");

    private final By collectionsCheckbox = By.xpath("//label[normalize-space(text())='Collections']/preceding-sibling::input[@type='checkbox']");
    private final By collectionsLabel = By.xpath("//label[normalize-space(text())='Collections']");
    private final By collectionsIcon = By.xpath("//label[text()='Collections']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']");
    private final By collectionsExpandArrow = By.xpath("//label[text()='Collections']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]");
    private final By collectionsFolderContainer = By.xpath("//label[text()='Collections']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]");

    private final By eorResponseCheckbox = By.xpath("//label[normalize-space(text())='EOR Response']/preceding-sibling::input[@type='checkbox']");
    private final By eorResponseLabel = By.xpath("//label[normalize-space(text())='EOR Response']");
    private final By eorResponseIcon = By.xpath("//label[text()='EOR Response']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']");
    private final By eorResponseExpandArrow = By.xpath("//label[text()='EOR Response']/ancestor::div[1]//button[contains(@aria-label, 'expand') or contains(@class, 'arrow')]");
    private final By eorResponseFolderContainer = By.xpath("//label[text()='EOR Response']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]");

    private final By petitionCheckbox = By.xpath("//label[normalize-space(text())='Petition']/preceding-sibling::input[@type='checkbox']");
    private final By petitionLabel = By.xpath("//label[normalize-space(text())='Petition']");
    private final By petitionIcon = By.xpath("//label[text()='Petition']/preceding-sibling::*[contains(@class, 'icon') or name()='svg' or name()='i']");
    private final By petitionExpandArrow = By.xpath("//label[text()='Petition']/ancestor::div[1]//button[contains(@class, 'arrow') or contains(@class, 'toggle')]");
    private final By petitionFolderContainer = By.xpath("//label[text()='Petition']/ancestor::*[contains(@class, 'folder-item') or contains(@class, 'tree-node')][1]");

    private final By allFolderCheckboxes = By.xpath("//input[@type='checkbox' and ancestor::*[contains(@class, 'tree') or @role='tree']]");
    private final By allFolderLabels = By.xpath("//label[ancestor::*[contains(@class, 'tree') or @role='tree']]");
    private final By allFolderIcons = By.xpath("//*[(contains(@class, 'icon') or name()='svg') and ancestor::*[contains(@class, 'tree')]]");
    private final By allExpandArrows = By.xpath("//button[contains(@class, 'arrow') or contains(@aria-label, 'expand')]");

    private final By uploadButtonEnabled = By.xpath("//button[contains(text(), 'Upload') and not(@disabled)]");
    private final By uploadButtonDisabled = By.xpath("//button[contains(text(), 'Upload') and @disabled]");
    private final By selectedCheckboxes = By.xpath("//input[@type='checkbox' and (@checked='true' or @checked='checked')]");
    private final By unselectedCheckboxes = By.xpath("//input[@type='checkbox' and not(@checked)]");
    private final By modalVisible = By.xpath("//div[@role='dialog' and not(contains(@style, 'display: none')) and not(contains(@class, 'hidden'))]");
    private final By modalHidden = By.xpath("//div[@role='dialog' and (contains(@style, 'display: none') or contains(@class, 'hidden') or contains(@class, 'ng-hide'))]");

    private final By fileInputField = By.xpath("//input[@type='file']");
    private final By browseFilesButton = By.xpath("//button[contains(text(), 'Browse') or contains(text(), 'Choose')]");
    private final By fileDropZone = By.xpath("//div[contains(@class, 'drop-zone') or contains(@class, 'file-drop') or contains(text(), 'Drop')]");
    private final By displayedFileName = By.xpath("//*[contains(@class, 'file-name') or contains(@class, 'selected-file')]");

    private final By loadingSpinner = By.xpath("//*[contains(@class, 'spinner') or contains(@class, 'loading')]");
    private final By uploadProgressBar = By.xpath("//div[contains(@class, 'progress-bar') or @role='progressbar']");

    private final By uploadSuccessMessage = By.xpath("//*[contains(@class, 'success') and (contains(text(), 'success') or contains(text(), 'uploaded'))]");
    private final By uploadErrorMessage = By.xpath("//*[contains(@class, 'error') or contains(@class, 'alert-danger')]");
    private final By validationErrorMessage = By.xpath("//*[contains(@class, 'validation-error') or contains(@class, 'invalid-feedback')]");

    private final By uploadProgressToast = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/div/div/div[3]");
    private final By uploadSuccessToast = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4");
    private final By uploadSelectFolderToast = By.xpath("/html/body/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div/div/p");
    private final By uploadDuplicateToast = By.xpath("/html/body/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div/h4");
    private final By uploadErrorToast = By.xpath("/html/body/ng-component/p-dialog/div/div/div[2]/aeliusmd-file-upload/div/div/div/div/form/div/div/div[1]/p-toolbar/div/div[2]/p-fileupload/div/p-messages/div/div/div/span[3]");
    private final By successToastText = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p");
    private final By fileInputLocator = By.xpath("//p-fileupload//input[@type='file' and @accept='application/pdf' and @multiple]");

    public void clickGetStartedButton() {

        driver.findElement(getStartedButton).click();
    }

    public String getGetStartedButtonText() {
        return driver.findElement(getStartedButton).getText().trim();
    }


    public boolean isFileUploadModalDisplayed() {
        try {
            return driver.findElement(fileUploadModalContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isModalBackdropDisplayed() {
        try {
            return driver.findElement(modalBackdrop).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getModalTitleText() {
        return driver.findElement(modalTitle).getText().trim();
    }

    public boolean isModalHeaderDisplayed() {
        try {
            return driver.findElement(modalHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isModalBodyDisplayed() {
        try {
            return driver.findElement(modalBody).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isModalFooterDisplayed() {
        try {
            return driver.findElement(modalFooter).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickUploadButton() {
        driver.findElement(uploadButton).click();
    }

    public void clickRefreshButton() {
        driver.findElement(refreshButton).click();
    }

    public void clickCloseButton() {
        driver.findElement(closeButton).click();
    }

    public void clickCancelButton() {
        driver.findElement(cancelButton).click();
    }

    public boolean isUploadButtonDisplayed() {
        try {
            return driver.findElement(uploadButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshButtonDisplayed() {
        try {
            return driver.findElement(refreshButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCloseButtonDisplayed() {
        try {
            return driver.findElement(closeButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectBillingCheckbox() {
        driver.findElement(billingCheckbox).click();
    }

    public void selectCollectionsCheckbox() {
        driver.findElement(collectionsCheckbox).click();
    }

    public void selectEorResponseCheckbox() {
        driver.findElement(eorResponseCheckbox).click();
    }

    public void selectPetitionCheckbox() {
        driver.findElement(petitionCheckbox).click();
    }

    public boolean isBillingCheckboxSelected() {
        return driver.findElement(billingCheckbox).isSelected();
    }

    public boolean isCollectionsCheckboxSelected() {
        return driver.findElement(collectionsCheckbox).isSelected();
    }

    public boolean isEorResponseCheckboxSelected() {
        return driver.findElement(eorResponseCheckbox).isSelected();
    }

    public boolean isPetitionCheckboxSelected() {
        return driver.findElement(petitionCheckbox).isSelected();
    }

    public boolean isBillingLabelDisplayed() {
        try {
            return driver.findElement(billingLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCollectionsLabelDisplayed() {
        try {
            return driver.findElement(collectionsLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEorResponseLabelDisplayed() {
        try {
            return driver.findElement(eorResponseLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPetitionLabelDisplayed() {
        try {
            return driver.findElement(petitionLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBillingIconDisplayed() {
        try {
            return driver.findElement(billingIcon).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCollectionsIconDisplayed() {
        try {
            return driver.findElement(collectionsIcon).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEorResponseIconDisplayed() {
        try {
            return driver.findElement(eorResponseIcon).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPetitionIconDisplayed() {
        try {
            return driver.findElement(petitionIcon).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUploadButtonEnabled() {
        try {
            WebElement fileInput = driver.findElement(fileInputLocator);
            String disabledAttr = fileInput.getAttribute("disabled");
            if (disabledAttr != null && disabledAttr.equals("true")) {
                return false;
            }
            if (!fileInput.isEnabled()) {
                return false;
            }
            String pointerEvents = fileInput.getCssValue("pointer-events");
            if (pointerEvents != null && pointerEvents.equals("none")) {
                return false;
            }
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Upload button/input not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isUploadButtonDisabled() {
        try {
            return driver.findElement(uploadButtonDisabled).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getSelectedCheckboxesCount() {
        try {
            return driver.findElements(selectedCheckboxes).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getTotalCheckboxesCount() {
        return driver.findElements(allFolderCheckboxes).size();
    }

    public boolean isModalVisibleState() {
        try {
            return driver.findElement(modalVisible).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isModalHiddenState() {
        try {
            return driver.findElement(modalHidden).isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public void uploadFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Upload file does not exist: " + filePath);
        }
        if (!file.isAbsolute()) {
            throw new RuntimeException("Upload file path is not absolute: " + filePath);
        }
        try {
            WebElement fileInput = WaitUtils.waitForElementPresent(driver, fileInputLocator, 60);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fileInput);
            fileInput.sendKeys(filePath);
            System.out.println("File uploaded successfully: " + filePath);
        } catch (Exception e) {
            System.err.println("File upload failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean isFileInputFieldPresent() {
        try {
            return driver.findElement(fileInputField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoadingSpinnerDisplayed() {
        try {
            return driver.findElement(loadingSpinner).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUploadProgressBarDisplayed() {
        try {
            return driver.findElement(uploadProgressBar).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUploadSuccessMessageDisplayed() {
        try {
            return driver.findElement(uploadSuccessMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getUploadSuccessMessageText() {
        try {
            return driver.findElement(uploadSuccessMessage).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isUploadErrorMessageDisplayed() {
        try {
            return driver.findElement(uploadErrorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getUploadErrorMessageText() {
        try {
            return driver.findElement(uploadErrorMessage).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isValidationErrorMessageDisplayed() {
        try {
            return driver.findElement(validationErrorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getValidationErrorMessageText() {
        try {
            return driver.findElement(validationErrorMessage).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public List<WebElement> getAllFolderLabels() {
        return driver.findElements(allFolderLabels);
    }

    public List<WebElement> getAllFolderCheckboxes() {
        return driver.findElements(allFolderCheckboxes);
    }

    public boolean isFolderTreeContainerDisplayed() {
        try {
            return driver.findElement(folderTreeContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickBackdrop() {
        driver.findElement(modalBackdrop).click();
    }

    public boolean isFileDropZoneDisplayed() {
        try {
            return driver.findElement(fileDropZone).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDisplayedFileName() {
        try {
            return driver.findElement(displayedFileName).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public WebElement getUploadButton() {
        return driver.findElement(uploadButton);
    }



    public WebElement getRefreshButton() {
        return driver.findElement(refreshButton);
    }

    public WebElement getCloseButton() {
        return driver.findElement(closeButton);
    }

    public WebElement getBillingCheckbox() {
        return driver.findElement(billingCheckbox);
    }

    public WebElement getCollectionsCheckbox() {
        return driver.findElement(collectionsCheckbox);
    }

    public WebElement getEorResponseCheckbox() {
        return driver.findElement(eorResponseCheckbox);
    }

    public WebElement getPetitionCheckbox() {
        return driver.findElement(petitionCheckbox);
    }

    public String getProgressToastText() {
        return driver.findElement(uploadProgressToast).getText().trim();
    }

    public String getSuccessToastText() {
        return driver.findElement(uploadSuccessToast).getText().trim();
    }

    public String getSelectFolderToastText() {
        return driver.findElement(uploadSelectFolderToast).getText().trim();
    }

    public String getDuplicateToastText() {
        return driver.findElement(uploadDuplicateToast).getText().trim();
    }

    public String getErrorToastText() {
        return driver.findElement(uploadErrorToast).getText().trim();
    }

    public String getSuccessToastMessageText() {
        return driver.findElement(successToastText).getText().trim();
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
