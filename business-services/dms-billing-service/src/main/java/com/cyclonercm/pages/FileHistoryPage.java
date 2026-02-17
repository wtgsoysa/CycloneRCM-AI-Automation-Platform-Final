package com.cyclonercm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FileHistoryPage {
    private WebDriver driver;

    public FileHistoryPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By megaMenu = By.xpath("//p-megamenu//span[@class='p-menuitem-icon pi pi-fw pi-bars ng-star-inserted']");
    private final By fileHistoryMenu = By.xpath("//div[@class='p-megamenu-panel ng-star-inserted']//a[contains(text(),'File History')]");
    private final By mastersTab = By.xpath("//div[@class='p-megamenu-panel ng-star-inserted']//span[contains(text(),'Masters')]");

    private final By receivedFilesSection = By.xpath("//span[contains(text(),'Received Files')]");
    private final By invoiceListSection = By.xpath("//span[contains(text(),'Invoice List')]");

    private final By totalFileCount = By.xpath("//strong[contains(text(),'Total File Count:')]");
    private final By totalInvoiceCount = By.xpath("//strong[contains(text(),'Total Invoice Count:')]");

    private final By fileCard = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']");
    private final By fileId = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']//div[@class='p-col-fixed app-p-0'][1]//b");
    private final By fileName = By.xpath("//cyclone-list-file-history//div[@tooltipposition='bottom' and contains(@class,'text-overflow-ellipsis')]");
    private final By uploadDate = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']//div[@class='p-col-8 app-p-0 app-mt-5']");
    private final By fileStatus = By.xpath("//cyclone-list-file-history//p-badge//span[@class='p-badge p-component']");
    private final By processingStatus = By.xpath("//cyclone-list-file-history//p-badge[@severity='warning']//span[contains(text(),'Processing')]");
    private final By completedStatus = By.xpath("//cyclone-list-file-history//p-badge[@severity='success']//span[contains(text(),'Completed')]");

    private final By pages = By.xpath("//cyclone-list-file-history//div[@class='p-grid app-p-0'][contains(.,'Pages')]//following-sibling::div[@class='p-grid app-p-0'][1]//div[@class='p-col-1 app-p-0']");
    private final By invoiceCount = By.xpath("//cyclone-list-file-history//span[contains(text(),'Invoice Count')]/ancestor::div[@class='p-col-2 app-p-0']/following-sibling::div[@class='p-col-2 app-p-0'][1]");
    private final By successCount = By.xpath("//cyclone-list-file-history//span[contains(text(),'Success Count')]/ancestor::div[@class='p-col-2 app-p-0']/following-sibling::div[@class='p-col-2 app-p-0'][1]//span");
    private final By failCount = By.xpath("//cyclone-list-file-history//span[contains(text(),'Fail Count')]/ancestor::div[@class='p-col-2 app-p-0']/following-sibling::div[@class='p-col-2 app-p-0'][1]//span");
    private final By deletedCount = By.xpath("//cyclone-list-file-history//span[contains(text(),'Deleted Count')]/ancestor::div[@class='p-col-2 app-p-0']/following-sibling::div[@class='p-col-2 app-p-0'][1]//span");
    private final By amount = By.xpath("//cyclone-list-file-history//span[contains(text(),'Amount')]/ancestor::div[@class='p-col-3 app-p-0']/following-sibling::div[@class='p-col-3 app-p-0'][1]");

    private final By invoiceStatusDropdown = By.xpath("//label[contains(text(),'Invoice Status')]/ancestor::div[@class='p-field p-grid']//p-dropdown");
    private final By caseAdjField = By.xpath("//label[contains(text(),'Case/ADJ')]/ancestor::div[@class='p-field p-grid']//p-dropdown[@placeholder='Select Case Number']");
    private final By fileTypeDropdown = By.xpath("//label[contains(text(),'File Type')]/ancestor::div[@class='p-field p-grid']//p-dropdown");
    private final By fromDate = By.xpath("//label[contains(text(),'From Date')]/following-sibling::p-calendar//input[@placeholder='MM/DD/YYYY']");
    private final By toDate = By.xpath("//label[contains(text(),'To Date')]/following-sibling::p-calendar//input[@placeholder='MM/DD/YYYY']");
    private final By searchButton = By.xpath("//button[@ptooltip='Search by date range']");
    private final By clearButton = By.xpath("//button[@ptooltip='Clear date search']");
    private final By searchByFileNameField = By.xpath("//input[@placeholder='Search by file name invoice #']");
    private final By fileNameSearchButton = By.xpath("//button[@ptooltip='Search']");

    private final By firstPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-first')]");
    private final By previousPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top')]//button[contains(@class,'p-paginator-prev')]");
    private final By currentPageNumber = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-page') and contains(@class,'p-highlight')]");
    private final By nextPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-next')]");
    private final By lastPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-last')]");

    private final By jsonButton = By.xpath("//button[@ptooltip='View JSON File' and contains(text(),'JSON')]");
    private final By downloadButton = By.xpath("//button[@ptooltip='View Uploaded File' and @icon='pi pi-file']");
    private final By invoiceFileIcon = By.xpath("//cyclone-invoice-history-list//button[@ptooltip='View Invoice' and @icon='pi pi-file']");
    private final By invoiceEditIcon = By.xpath("//cyclone-invoice-history-list//button[@ptooltip='Edit Invoice' and @icon='pi pi-pencil']");
    private final By invoiceDeleteIcon = By.xpath("//cyclone-invoice-history-list//button[@ptooltip='Delete Invoice' and @icon='pi pi-trash']");
    private final By deleteConfirmModal = By.xpath("//p-confirmdialog[@header='Confirm Delete']");
    private final By confirmDeleteButton = By.xpath("//p-confirmdialog[@header='Confirm Delete']//button[contains(@class,'p-confirm-dialog-accept')]");

    private final By jsonContainer = By.xpath("//p-dialog[@header='View Documents']//div[@class='p-dialog-content']");
    private final By jsonFields = By.xpath("//p-dialog[@header='View Documents']//pre | //p-dialog[@header='View Documents']//code");
    private final By invoiceCheckbox = By.xpath("//cyclone-invoice-history-list//p-checkbox//input[@type='checkbox']");

    public void clickMegaMenu() {
        driver.findElement(megaMenu).click();
    }

    public void clickFileHistoryOption() {
        driver.findElement(fileHistoryMenu).click();
    }

    public String getMastersTabText() {
        return driver.findElement(mastersTab).getText().trim();
    }

    public boolean isReceivedFilesSectionDisplayed() {
        try {
            return driver.findElement(receivedFilesSection).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInvoiceListSectionDisplayed() {
        try {
            return driver.findElement(invoiceListSection).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getReceivedFilesSectionText() {
        return driver.findElement(receivedFilesSection).getText().trim();
    }

    public String getInvoiceListSectionText() {
        return driver.findElement(invoiceListSection).getText().trim();
    }

    public String getTotalFileCountText() {
        return driver.findElement(totalFileCount).getText().trim();
    }

    public String getTotalInvoiceCountText() {
        return driver.findElement(totalInvoiceCount).getText().trim();
    }

    public boolean isFileDisplayed() {
        try {
            return driver.findElements(fileCard).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstFileId() {
        return driver.findElements(fileId).get(0).getText().trim();
    }

    public String getFirstFileName() {
        return driver.findElements(fileName).get(0).getText().trim();
    }

    public String getFirstFileUploadDate() {
        return driver.findElements(uploadDate).get(0).getText().trim();
    }

    public String getFirstFileStatus() {
        return driver.findElements(fileStatus).get(0).getText().trim();
    }

    public boolean isProcessingStatusDisplayed() {
        try {
            return driver.findElement(processingStatus).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCompletedStatusDisplayed() {
        try {
            return driver.findElement(completedStatus).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstFilePages() {
        return driver.findElements(pages).get(0).getText().trim();
    }

    public String getFirstFileInvoiceCount() {
        return driver.findElements(invoiceCount).get(0).getText().trim();
    }

    public String getFirstFileSuccessCount() {
        return driver.findElements(successCount).get(0).getText().trim();
    }

    public String getFirstFileFailCount() {
        return driver.findElements(failCount).get(0).getText().trim();
    }

    public String getFirstFileDeletedCount() {
        return driver.findElements(deletedCount).get(0).getText().trim();
    }

    public String getFirstFileAmount() {
        return driver.findElements(amount).get(0).getText().trim();
    }

    public void clickInvoiceStatusDropdown() {
        driver.findElement(invoiceStatusDropdown).click();
    }

    public boolean isInvoiceStatusDropdownDisplayed() {
        try {
            return driver.findElement(invoiceStatusDropdown).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectInvoiceStatusOption(String option) {
        clickInvoiceStatusDropdown();
        driver.findElement(By.xpath("//span[contains(text(),'" + option + "')]")).click();
    }

    public boolean isCaseAdjFieldDisplayed() {
        try {
            return driver.findElement(caseAdjField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCaseAdjField() {
        driver.findElement(caseAdjField).click();
    }

    public boolean isFileTypeDropdownDisplayed() {
        try {
            return driver.findElement(fileTypeDropdown).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFileTypeDropdown() {
        driver.findElement(fileTypeDropdown).click();
    }

    public void enterFromDate(String date) {
        driver.findElement(fromDate).sendKeys(date);
    }

    public void enterToDate(String date) {
        driver.findElement(toDate).sendKeys(date);
    }

    public boolean isFromDateFieldDisplayed() {
        try {
            return driver.findElement(fromDate).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isToDateFieldDisplayed() {
        try {
            return driver.findElement(toDate).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public void clickClearButton() {
        driver.findElement(clearButton).click();
    }

    public void enterSearchFileName(String fileName) {
        driver.findElement(searchByFileNameField).sendKeys(fileName);
    }

    public void clickFileNameSearchButton() {
        driver.findElement(fileNameSearchButton).click();
    }

    public boolean isSearchByFileNameFieldDisplayed() {
        try {
            return driver.findElement(searchByFileNameField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstPage() {
        driver.findElement(firstPageButton).click();
    }

    public void clickPreviousPage() {
        driver.findElement(previousPageButton).click();
    }

    public void clickNextPage() {
        driver.findElement(nextPageButton).click();
    }

    public void clickLastPage() {
        driver.findElement(lastPageButton).click();
    }

    public String getCurrentPageNumber() {
        return driver.findElement(currentPageNumber).getText().trim();
    }

    public boolean isFirstPageButtonEnabled() {
        try {
            return !driver.findElement(firstPageButton).getAttribute("class").contains("p-disabled");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNextPageButtonEnabled() {
        try {
            return !driver.findElement(nextPageButton).getAttribute("class").contains("p-disabled");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstJsonButton() {
        driver.findElements(jsonButton).get(0).click();
    }

    public void clickFirstDownloadButton() {
        driver.findElements(downloadButton).get(0).click();
    }

    public boolean isJsonButtonDisplayed() {
        try {
            return driver.findElements(jsonButton).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDownloadButtonDisplayed() {
        try {
            return driver.findElements(downloadButton).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isJsonContainerDisplayed() {
        try {
            return driver.findElement(jsonContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getJsonFieldsText() {
        return driver.findElement(jsonFields).getText().trim();
    }

    public void clickFirstFile() {
        driver.findElements(fileCard).get(0).click();
    }

    public void clickFirstInvoiceFileIcon() {
        driver.findElements(invoiceFileIcon).get(0).click();
    }

    public void clickFirstInvoiceEditIcon() {
        driver.findElements(invoiceEditIcon).get(0).click();
    }

    public void clickFirstInvoiceDeleteIcon() {
        driver.findElements(invoiceDeleteIcon).get(0).click();
    }

    public boolean isInvoiceFileIconDisplayed() {
        try {
            return driver.findElements(invoiceFileIcon).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInvoiceEditIconDisplayed() {
        try {
            return driver.findElements(invoiceEditIcon).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInvoiceDeleteIconDisplayed() {
        try {
            return driver.findElements(invoiceDeleteIcon).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteConfirmModalDisplayed() {
        try {
            return driver.findElement(deleteConfirmModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickConfirmDeleteButton() {
        driver.findElement(confirmDeleteButton).click();
    }

    public int getInvoiceCount() {
        return driver.findElements(invoiceCheckbox).size();
    }

    public List<WebElement> getAllFiles() {
        return driver.findElements(fileCard);
    }

    public List<WebElement> getAllInvoices() {
        return driver.findElements(invoiceCheckbox);
    }
}
