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
    private final By fileHistoryMenu = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-megamenu/div/ul/li/div/div/div[1]/ul/li[9]/a");
    private final By mastersTab = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-megamenu/div/ul/li/div/div/div[1]/ul/li[1]/span");

    private final By receivedFilesSection = By.xpath("//span[contains(text(),'Received Files')]");
    private final By invoiceListSection = By.xpath("//span[contains(text(),'Invoice List')]");

    private final By totalFileCount = By.xpath("//strong[contains(text(),'Total File Count:')]");
    private final By totalInvoiceCount = By.xpath("//strong[contains(text(),'Total Invoice Count:')]");

    private final By fileCard = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']");
    private final By fileId = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']//div[@class='p-col-fixed app-p-0'][1]//b");
    private final By fileName = By.xpath("//cyclone-list-file-history//div[@tooltipposition='bottom' and contains(@class,'text-overflow-ellipsis')]");
    private final By uploadDate = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']//div[@class='p-col-8 app-p-0 app-mt-5']");
    private final By fileStatus = By.xpath("//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted']//p-badge[@class='p-element']//span");
    private final By processingStatus = By.xpath("//cyclone-list-file-history//p-badge[@severity='warning']//span[contains(text(),'Processing')]");
    private final By completedStatus = By.xpath("//cyclone-list-file-history//p-badge[@severity='success']//span[contains(text(),'Completed')]");

    private final By pages = By.xpath("(//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//div[contains(@class,'p-col-2')][contains(.,'Pages')]/following-sibling::div[1]");
    private final By invoiceCount = By.xpath("(//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td/span[2]/div/div[7]/div[2]");
    private final By successCount = By.xpath("(//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td/span[2]/div/div[7]/div[3]");
    private final By failCount = By.xpath("(//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td/span[2]/div/div[7]/div[4]");
    private final By deletedCount = By.xpath("(//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td/span[2]/div/div[7]/div[5]");
    private final By amount = By.xpath("(//cyclone-list-file-history//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td/span[2]/div/div[7]/div[6]");

    private final By invoiceStatusDropdown = By.xpath("(//cyclone-list-file-history//p-dropdown)[1] | //label[contains(text(),'Invoice Status')]/following::p-dropdown[1]");
    private final By caseAdjField = By.xpath("(//cyclone-list-file-history//p-dropdown)[2] | //label[contains(text(),'Case/ADJ')]/following::p-dropdown[1]");
    private final By fileTypeDropdown = By.xpath("(//cyclone-list-file-history//p-dropdown)[3] | //label[contains(text(),'File Type')]/following::p-dropdown[1]");
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
        try {
            Thread.sleep(2000); // Wait for status badge to load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<WebElement> statusElements = driver.findElements(fileStatus);
        for (WebElement element : statusElements) {
            String statusText = element.getText().trim();
            if (!statusText.isEmpty()) {
                return statusText;
            }
        }
        return ""; // Return empty if no text found
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
        try {
            return driver.findElement(pages).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting pages: " + e.getMessage());
            return "";
        }
    }

    public String getFirstFileInvoiceCount() {
        try {
            return driver.findElement(invoiceCount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting invoice count: " + e.getMessage());
            return "";
        }
    }

    public String getFirstFileSuccessCount() {
        try {
            return driver.findElement(successCount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting success count: " + e.getMessage());
            return "";
        }
    }

    public String getFirstFileFailCount() {
        try {
            return driver.findElement(failCount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting fail count: " + e.getMessage());
            return "";
        }
    }

    public String getFirstFileDeletedCount() {
        try {
            return driver.findElement(deletedCount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting deleted count: " + e.getMessage());
            return "";
        }
    }

    public String getFirstFileAmount() {
        try {
            return driver.findElement(amount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting amount: " + e.getMessage());
            return "";
        }
    }

    public void clickInvoiceStatusDropdown() {
        // Click the dropdown trigger button (div[2] in the structure)
        By dropdownButton = By.xpath("(//cyclone-list-file-history//p-dropdown)[1]//div[contains(@class,'p-dropdown-trigger')]");
        driver.findElement(dropdownButton).click();
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

    public boolean isDropdownOptionDisplayed(String optionText) {
        try {
            // Wait for dropdown panel to appear
            Thread.sleep(1500);

            // Based on actual DOM: /html/body/div[3]/div/ul/p-dropdownitem[x]/li
            // Try multiple XPath strategies for maximum compatibility
            By optionLocator = By.xpath(
                "//p-dropdownitem//li[normalize-space()='" + optionText + "'] | " +
                "//li[contains(@class,'p-dropdown-item')][normalize-space()='" + optionText + "'] | " +
                "//div[@role='listbox']//li[normalize-space()='" + optionText + "']"
            );

            return driver.findElement(optionLocator).isDisplayed();
        } catch (Exception e) {
            System.out.println("Option '" + optionText + "' not found: " + e.getMessage());
            return false;
        }
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

    /**
     * Select file type from dropdown (e.g., "Interpreted Billing")
     */
    public void selectFileType(String fileType) {
        try {
            clickFileTypeDropdown();
            Thread.sleep(2000); // Wait for dropdown panel to render

            // Wait for dropdown panel to be visible
            By dropdownPanel = By.xpath("//div[contains(@class,'p-dropdown-panel')] | //body/div[3]/div");
            for (int i = 0; i < 3; i++) {
                try {
                    if (driver.findElement(dropdownPanel).isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    Thread.sleep(1000);
                }
            }

            // Try multiple XPath strategies for clicking the option
            By optionLocator = By.xpath(
                "//p-dropdownitem//li[normalize-space()='" + fileType + "'] | " +
                "//li[contains(@class,'p-dropdown-item')][normalize-space()='" + fileType + "'] | " +
                "//div[@role='listbox']//li[contains(normalize-space(),'" + fileType + "')]"
            );

            driver.findElement(optionLocator).click();
            Thread.sleep(2000); // Wait for filter to apply
        } catch (Exception e) {
            throw new RuntimeException("Failed to select file type: " + fileType + " - " + e.getMessage());
        }
    }

    /**
     * Check if file type dropdown option is displayed
     */
    public boolean isFileTypeOptionDisplayed(String option) {
        try {
            By optionLocator = By.xpath(
                "//p-dropdownitem//li[contains(normalize-space(),'" + option + "')] | " +
                "//li[contains(@class,'p-dropdown-item')][contains(normalize-space(),'" + option + "')]"
            );
            return driver.findElement(optionLocator).isDisplayed();
        } catch (Exception e) {
            System.out.println("Option '" + option + "' not found: " + e.getMessage());
            return false;
        }
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

    // ============ FILTER & SEARCH VALIDATION METHODS ============

    /**
     * Select a filter option from Invoice Status dropdown
     */
    public void selectInvoiceStatusFilter(String filterOption) {
        try {
            clickInvoiceStatusDropdown();
            Thread.sleep(2000); // Increased wait for dropdown panel to render

            // Wait for dropdown panel to be visible
            By dropdownPanel = By.xpath("//div[contains(@class,'p-dropdown-panel')] | //body/div[3]/div");
            for (int i = 0; i < 3; i++) {
                try {
                    if (driver.findElement(dropdownPanel).isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    Thread.sleep(1000);
                }
            }

            // Try multiple XPath strategies for clicking the option
            By optionLocator = By.xpath(
                "//p-dropdownitem//li[normalize-space()='" + filterOption + "'] | " +
                "//li[contains(@class,'p-dropdown-item')][normalize-space()='" + filterOption + "'] | " +
                "//div[@role='listbox']//li[contains(normalize-space(),'" + filterOption + "')]"
            );

            driver.findElement(optionLocator).click();
            Thread.sleep(2000); // Wait for filter to apply
        } catch (Exception e) {
            throw new RuntimeException("Failed to select filter: " + filterOption + " - " + e.getMessage());
        }
    }

    /**
     * Get all visible file rows in Received Files panel
     */
    public List<WebElement> getLeftPanelRows() {
        By leftRows = By.xpath("(//p-table)[1]//tbody//tr[contains(@class,'p-selectable-row')][td]");
        return driver.findElements(leftRows);
    }

    /**
     * Get all visible invoice rows in Invoice List panel
     */
    public List<WebElement> getRightPanelRows() {
        By rightRows = By.xpath("(//p-table)[2]//tbody//tr[contains(@class,'p-selectable-row')][td]");
        return driver.findElements(rightRows);
    }

    /**
     * Get status badge text from a file row (left panel)
     */
    public String getFileRowStatus(WebElement row) {
        try {
            By statusBadge = By.xpath(".//p-badge/span");
            return row.findElement(statusBadge).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get status badge text from an invoice row (right panel)
     */
    public String getInvoiceRowStatus(WebElement row) {
        try {
            By statusBadge = By.xpath(".//td[last()]//p-badge//span");
            return row.findElement(statusBadge).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get Success Count from a file row (left panel)
     */
    public int getFileRowSuccessCount(WebElement row) {
        try {
            By successCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[3]/span");
            String text = row.findElement(successCountLocator).getText().trim();
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get Fail Count from a file row (left panel)
     */
    public int getFileRowFailCount(WebElement row) {
        try {
            By failCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[4]/span");
            String text = row.findElement(failCountLocator).getText().trim();
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get Invoice Count from a file row (left panel)
     */
    public int getFileRowInvoiceCount(WebElement row) {
        try {
            By invoiceCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[2]");
            String text = row.findElement(invoiceCountLocator).getText().trim();
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get file name from a file row (left panel)
     */
    public String getFileRowFileName(WebElement row) {
        try {
            By fileNameLocator = By.xpath(".//div[contains(@class,'text-overflow-ellipsis')]");
            return row.findElement(fileNameLocator).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get invoice number from an invoice row (right panel)
     */
    public String getInvoiceRowNumber(WebElement row) {
        try {
            By invoiceNumberLocator = By.xpath("./td[1]");
            return row.findElement(invoiceNumberLocator).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Search by file name or invoice number
     */
    public void searchByFileNameOrInvoice(String searchText) {
        try {
            WebElement searchField = driver.findElement(searchByFileNameField);
            searchField.clear();
            searchField.sendKeys(searchText);
            Thread.sleep(500);
            driver.findElement(fileNameSearchButton).click();
            Thread.sleep(2000); // Wait for search results
        } catch (Exception e) {
            throw new RuntimeException("Failed to search: " + e.getMessage());
        }
    }

    /**
     * Clear search field
     */
    public void clearSearch() {
        try {
            WebElement searchField = driver.findElement(searchByFileNameField);
            searchField.clear();
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Failed to clear search: " + e.getMessage());
        }
    }

    /**
     * Check if left panel has data
     */
    public boolean hasLeftPanelData() {
        return getLeftPanelRows().size() > 0;
    }

    /**
     * Check if right panel has data
     */
    public boolean hasRightPanelData() {
        return getRightPanelRows().size() > 0;
    }

    /**
     * Validate all invoices in right panel match the expected status
     */
    public boolean validateRightPanelStatus(String expectedStatus) {
        List<WebElement> invoiceRows = getRightPanelRows();
        if (invoiceRows.isEmpty()) {
            return false;
        }

        for (WebElement row : invoiceRows) {
            String actualStatus = getInvoiceRowStatus(row);
            if (!actualStatus.equalsIgnoreCase(expectedStatus)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate left panel has files with matching counts for the filter
     */
    public boolean validateLeftPanelForSuccessFilter() {
        List<WebElement> fileRows = getLeftPanelRows();
        if (fileRows.isEmpty()) {
            return false;
        }

        for (WebElement row : fileRows) {
            int successCount = getFileRowSuccessCount(row);
            if (successCount == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate left panel has files with matching counts for Fail filter
     */
    public boolean validateLeftPanelForFailFilter() {
        List<WebElement> fileRows = getLeftPanelRows();
        if (fileRows.isEmpty()) {
            return false;
        }

        for (WebElement row : fileRows) {
            int failCount = getFileRowFailCount(row);
            if (failCount == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate right panel status with flexible matching (supports partial match)
     * Useful for statuses like "Manually Corrected" which might vary
     */
    public boolean validateRightPanelStatusFlexible(String expectedStatusKeyword) {
        List<WebElement> invoiceRows = getRightPanelRows();
        if (invoiceRows.isEmpty()) {
            return false;
        }

        for (WebElement row : invoiceRows) {
            String actualStatus = getInvoiceRowStatus(row).toLowerCase();
            if (!actualStatus.contains(expectedStatusKeyword.toLowerCase())) {
                System.out.println("DEBUG: Status mismatch - Expected keyword: '" + expectedStatusKeyword +
                                   "', Actual: '" + actualStatus + "'");
                return false;
            }
        }
        return true;
    }

    /**
     * Get all unique statuses from right panel for debugging
     */
    public java.util.Set<String> getAllInvoiceStatuses() {
        java.util.Set<String> statuses = new java.util.HashSet<>();
        List<WebElement> invoiceRows = getRightPanelRows();

        for (WebElement row : invoiceRows) {
            String status = getInvoiceRowStatus(row);
            if (!status.isEmpty()) {
                statuses.add(status);
            }
        }
        return statuses;
    }
}
