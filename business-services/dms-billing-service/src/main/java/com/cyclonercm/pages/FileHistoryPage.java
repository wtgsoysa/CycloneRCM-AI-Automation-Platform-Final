package com.cyclonercm.pages;

import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
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
    private final By fromDate = By.xpath("//label[contains(.,'From Date')]/following-sibling::p-calendar//input | //div[contains(@class,'p-col-6')]//label[contains(.,'From Date')]/..//input[@placeholder='MM/DD/YYYY']");
    private final By toDate = By.xpath("//label[contains(.,'To Date')]/following-sibling::p-calendar//input | //div[contains(@class,'p-col-6')]//label[contains(.,'To Date')]/..//input[@placeholder='MM/DD/YYYY']");
    private final By fromDateButton = By.xpath("//label[contains(.,'From Date')]/following-sibling::p-calendar//button | //div[contains(@class,'p-col-6')]//label[contains(.,'From Date')]/..//button[contains(@class,'p-datepicker-trigger')]");
    private final By toDateButton = By.xpath("//label[contains(.,'To Date')]/following-sibling::p-calendar//button | //div[contains(@class,'p-col-6')]//label[contains(.,'To Date')]/..//button[contains(@class,'p-datepicker-trigger')]");
    private final By searchButton = By.xpath("//button[@ptooltip='Search by date range'] | //button[contains(.,'Search') and @icon='pi pi-search']");
    private final By clearButton = By.xpath("//button[@ptooltip='Clear date search'] | //button[contains(.,'Clear') and @icon='pi pi-times']");
    private final By datePickerCalendar = By.xpath("//div[contains(@class,'p-datepicker')]");
    private final By searchByFileNameField = By.xpath("//input[@placeholder='Search by file name invoice #'] | //input[@placeholder='Search by file name invoice']");
    private final By fileNameSearchButton = By.xpath("//button[@ptooltip='Search'] | //button[@icon='pi pi-search']");
    private final By dateRangeActiveLabel = By.xpath("//a[contains(@class,'info-link') and contains(.,'Date Range Active')]");

    private final By firstPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-first')]");
    private final By previousPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top')]//button[contains(@class,'p-paginator-prev')]");
    private final By currentPageNumber = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-page') and contains(@class,'p-highlight')]");
    private final By nextPageButton = By.xpath("/html/body/ng-component/div/div/div[2]/file-history-app/div/div[2]/cyclone-invoice-history-list/div/div/div/div/div[3]/div/form/p-table/div/p-paginator/div/button[3]");
    private final By lastPageButton = By.xpath("//p-paginator[@styleclass='p-paginator-top']//button[contains(@class,'p-paginator-last')]");

    private final By jsonButton = By.xpath("/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form/p-table/div/div/table/tbody/tr[1]/td/span[2]/div/div[3]/button[1]");
    private final By downloadButton = By.xpath("//button[@ptooltip='View Uploaded File' and @icon='pi pi-file']");
    private final By invoiceFileIcon = By.xpath("//cyclone-invoice-history-list//button[@ptooltip='View Invoice' and @icon='pi pi-file']");
    private final By invoiceEditIcon = By.xpath("//cyclone-invoice-history-list//button[@ptooltip='Edit Invoice' and @icon='pi pi-pencil']");
    private final By invoiceDeleteIcon = By.xpath("//cyclone-invoice-history-list//button[@ptooltip='Delete Invoice' and @icon='pi pi-trash']");
    private final By deleteConfirmModal = By.xpath("//p-confirmdialog[@header='Confirm Delete']");
    private final By confirmDeleteButton = By.xpath("//p-confirmdialog[@header='Confirm Delete']//button[contains(@class,'p-confirm-dialog-accept')]");

    private final By jsonContainer = By.xpath("/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]/cyclone-list-file-history/div/div/div[2]/p-dialog/div/div/div[2]");
    private final By jsonFields = By.xpath("//p-dialog[@header='View Documents']//pre | //p-dialog[@header='View Documents']//code");
    private final By invoiceCheckbox = By.xpath("//cyclone-invoice-history-list//p-checkbox//input[@type='checkbox']");
    private final By documentView = By.xpath("/html/body/div[3]/div/div[2]");

    public void clickMegaMenu() {
        driver.findElement(megaMenu).click();
    }

    public void clickFileHistoryOption() {
        driver.findElement(fileHistoryMenu).click();
    }

    public void clickEditInvoiceIcon() {
        driver.findElement(invoiceEditIcon).click();
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
            Thread.sleep(3000); // Wait longer for dropdown panel to render

            // Wait for dropdown panel to be visible - using body/div[3] structure
            boolean panelVisible = false;
            for (int i = 0; i < 8; i++) {
                try {
                    // Check for the dropdown panel using multiple approaches
                    List<WebElement> panels = driver.findElements(By.xpath(
                        "//body/div[@class='p-dropdown-panel p-component p-ripple-disabled ng-star-inserted'] | " +
                        "//div[contains(@class,'p-dropdown-panel')][@style]"
                    ));

                    if (!panels.isEmpty() && panels.get(0).isDisplayed()) {
                        panelVisible = true;
                        System.out.println("✓ Dropdown panel is visible");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Waiting for dropdown panel... attempt " + (i + 1));
                    Thread.sleep(1000);
                }
            }

            if (!panelVisible) {
                System.out.println("WARNING: Dropdown panel might not be visible, but continuing...");
            }

            // Additional wait for options to fully render
            Thread.sleep(2000);

            // Debug: Print all available options using correct XPath structure
            try {
                // Based on your XPath: /html/body/div[3]/div/ul/p-dropdownitem[X]/li/span[1]
                List<WebElement> allOptions = driver.findElements(By.xpath(
                    "//body/div[contains(@class,'p-dropdown-panel')]//p-dropdownitem//li | " +
                    "//body/div[3]//p-dropdownitem//li | " +
                    "//p-dropdownitem/li"
                ));
                System.out.println("DEBUG: Found " + allOptions.size() + " dropdown options");
                for (int i = 0; i < Math.min(allOptions.size(), 10); i++) {
                    String optionText = allOptions.get(i).getText().trim();
                    System.out.println("  Option " + (i+1) + ": '" + optionText + "'");
                }
            } catch (Exception e) {
                System.out.println("DEBUG: Could not list dropdown options: " + e.getMessage());
            }

            // Try multiple strategies to find and click the option
            WebElement optionElement = null;

            // Strategy 1: Based on actual DOM - body/div[3]/div/ul/p-dropdownitem/li structure
            try {
                optionElement = driver.findElement(By.xpath(
                    "//body/div[contains(@class,'p-dropdown-panel')]//p-dropdownitem//li[normalize-space()='" + fileType + "']"
                ));
                System.out.println("✓ Found option using Strategy 1 (p-dropdownitem structure)");
            } catch (Exception e) {
                System.out.println("✗ Strategy 1 failed");
            }

            // Strategy 2: Using body/div[3] direct path
            if (optionElement == null) {
                try {
                    optionElement = driver.findElement(By.xpath(
                        "//body/div[3]//p-dropdownitem//li[normalize-space()='" + fileType + "']"
                    ));
                    System.out.println("✓ Found option using Strategy 2 (body/div[3] path)");
                } catch (Exception e) {
                    System.out.println("✗ Strategy 2 failed");
                }
            }

            // Strategy 3: Contains match within span
            if (optionElement == null) {
                try {
                    optionElement = driver.findElement(By.xpath(
                        "//p-dropdownitem//li[contains(.//span, '" + fileType + "')]"
                    ));
                    System.out.println("✓ Found option using Strategy 3 (span contains)");
                } catch (Exception e) {
                    System.out.println("✗ Strategy 3 failed");
                }
            }

            // Strategy 4: Direct p-dropdownitem/li with contains
            if (optionElement == null) {
                try {
                    optionElement = driver.findElement(By.xpath(
                        "//p-dropdownitem/li[contains(normalize-space(), '" + fileType + "')]"
                    ));
                    System.out.println("✓ Found option using Strategy 4 (p-dropdownitem/li contains)");
                } catch (Exception e) {
                    System.out.println("✗ Strategy 4 failed");
                }
            }

            // Strategy 5: Any li with the text anywhere in the dropdown
            if (optionElement == null) {
                try {
                    List<WebElement> allLiElements = driver.findElements(By.xpath("//p-dropdownitem//li"));
                    for (WebElement li : allLiElements) {
                        if (li.getText().trim().equals(fileType)) {
                            optionElement = li;
                            System.out.println("✓ Found option using Strategy 5 (iterate all li)");
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("✗ Strategy 5 failed");
                }
            }

            if (optionElement == null) {
                throw new Exception("Could not locate option: " + fileType + " after trying all strategies");
            }

            // Scroll element into view and click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", optionElement);
            Thread.sleep(500);

            // Try clicking with JavaScript directly (more reliable for PrimeNG)
            try {
                js.executeScript("arguments[0].click();", optionElement);
                System.out.println("✓ Clicked option with JavaScript click");
            } catch (Exception e) {
                // Fallback to regular click
                optionElement.click();
                System.out.println("✓ Clicked option with regular click");
            }

            Thread.sleep(3000); // Wait for filter to apply
        } catch (Exception e) {
            throw new RuntimeException("Failed to select file type: " + fileType + " - " + e.getMessage());
        }
    }

    /**
     * Check if file type dropdown option is displayed
     */
    public boolean isFileTypeOptionDisplayed(String option) {
        try {
            // Wait a bit for dropdown options to render
            Thread.sleep(1500);

            // Try multiple approaches to find the option
            By optionLocator = By.xpath(
                "//body/div[contains(@class,'p-dropdown-panel')]//p-dropdownitem//li[contains(normalize-space(),'" + option + "')] | " +
                "//body/div[3]//p-dropdownitem//li[contains(normalize-space(),'" + option + "')] | " +
                "//p-dropdownitem//li[contains(normalize-space(),'" + option + "')]"
            );

            List<WebElement> options = driver.findElements(optionLocator);

            if (!options.isEmpty()) {
                System.out.println("✓ Option '" + option + "' found in dropdown");
                return true;
            } else {
                System.out.println("✗ Option '" + option + "' not found in dropdown");
                return false;
            }
        } catch (Exception e) {
            System.out.println("✗ Option '" + option + "' not found: " + e.getMessage());
            return false;
        }
    }

    public void enterFromDate(String date) {
        try {
            System.out.println("⏳ Entering From Date: " + date);
            WebElement fromDateField = driver.findElement(fromDate);

            // Close any open calendar first by clicking elsewhere
            try {
                if (isDatePickerCalendarOpen()) {
                    System.out.println("ℹ Closing open calendar first");
                    driver.findElement(By.xpath("//label[contains(.,'From Date')]")).click();
                    Thread.sleep(300);
                }
            } catch (Exception ignored) {}

            // Click to focus the field
            fromDateField.click();
            Thread.sleep(300);

            // Clear existing value using Ctrl+A and Delete
            fromDateField.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
            fromDateField.sendKeys(org.openqa.selenium.Keys.DELETE);
            Thread.sleep(200);

            // Send keys directly
            fromDateField.sendKeys(date);
            Thread.sleep(300);

            // Press Escape to close calendar if it opened, then Tab to move focus
            fromDateField.sendKeys(org.openqa.selenium.Keys.ESCAPE);
            Thread.sleep(200);
            fromDateField.sendKeys(org.openqa.selenium.Keys.TAB);
            Thread.sleep(500);

            // Trigger change events for Angular
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                fromDateField);

            Thread.sleep(300);

            System.out.println("✓ From Date entered successfully");
        } catch (Exception e) {
            System.out.println("✗ Error entering From Date: " + e.getMessage());
            throw new RuntimeException("Failed to enter From Date: " + e.getMessage());
        }
    }

    public void enterToDate(String date) {
        try {
            System.out.println("⏳ Entering To Date: " + date);
            WebElement toDateField = driver.findElement(toDate);

            // Close any open calendar first
            try {
                if (isDatePickerCalendarOpen()) {
                    System.out.println("ℹ Closing open calendar first");
                    driver.findElement(By.xpath("//label[contains(.,'To Date')]")).click();
                    Thread.sleep(300);
                }
            } catch (Exception ignored) {}

            // Click to focus the field
            toDateField.click();
            Thread.sleep(300);

            // Clear existing value using Ctrl+A and Delete
            toDateField.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
            toDateField.sendKeys(org.openqa.selenium.Keys.DELETE);
            Thread.sleep(200);

            // Send keys directly
            toDateField.sendKeys(date);
            Thread.sleep(300);

            // Press Escape to close calendar if it opened, then Tab to move focus
            toDateField.sendKeys(org.openqa.selenium.Keys.ESCAPE);
            Thread.sleep(200);
            toDateField.sendKeys(org.openqa.selenium.Keys.TAB);
            Thread.sleep(500);

            // Trigger change events for Angular
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                toDateField);

            // Extra wait for Angular to process both dates and enable Search button
            Thread.sleep(800);

            System.out.println("✓ To Date entered successfully");
        } catch (Exception e) {
            System.out.println("✗ Error entering To Date: " + e.getMessage());
            throw new RuntimeException("Failed to enter To Date: " + e.getMessage());
        }
    }

    /**
     * Check if date picker calendar popup is currently open
     */
    private boolean isDatePickerCalendarOpen() {
        try {
            List<WebElement> calendars = driver.findElements(datePickerCalendar);
            return calendars.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
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
        try {
            System.out.println("⏳ Clicking Search button...");
            WebElement button = driver.findElement(searchButton);
            Thread.sleep(500);
            button.click();
            Thread.sleep(1000);
            System.out.println("✓ Search button clicked successfully");
        } catch (Exception e) {
            System.out.println("✗ Error clicking Search button: " + e.getMessage());
            throw new RuntimeException("Failed to click Search button: " + e.getMessage());
        }
    }

    public boolean isSearchButtonEnabled() {
        try {
            WebElement button = driver.findElement(searchButton);
            boolean isEnabled = button.isEnabled();
            String disabled = button.getAttribute("disabled");

            // Button is enabled if:
            // 1. isEnabled() returns true
            // 2. disabled attribute is null OR equals "false"
            boolean hasDisabledAttr = (disabled != null && !disabled.equals("false") && !disabled.isEmpty());

            System.out.println("ℹ Search button state - isEnabled: " + isEnabled +
                              ", disabled attr: '" + disabled + "'" +
                              ", hasDisabledAttr: " + hasDisabledAttr);

            return isEnabled && !hasDisabledAttr;
        } catch (Exception e) {
            System.out.println("✗ Error checking Search button state: " + e.getMessage());
            return false;
        }
    }

    public int getFileCount() {
        try {
            return driver.findElements(fileCard).size();
        } catch (Exception e) {
            System.out.println("✗ Error getting file count: " + e.getMessage());
            return 0;
        }
    }

    public void clickClearButton() {
        try {
            System.out.println("⏳ Clicking Clear button...");
            driver.findElement(clearButton).click();
            Thread.sleep(500);
            System.out.println("✓ Clear button clicked successfully");
        } catch (Exception e) {
            System.out.println("✗ Error clicking Clear button: " + e.getMessage());
        }
    }

    public boolean isDateRangeActive() {
        try {
            return driver.findElement(dateRangeActiveLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDateRangeActiveText() {
        try {
            return driver.findElement(dateRangeActiveLabel).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void enterSearchFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        driver.findElement(searchByFileNameField).clear();
        driver.findElement(searchByFileNameField).sendKeys(fileName);
    }

    public String getSearchFileNameFieldValue() {
        try {
            return driver.findElement(searchByFileNameField).getAttribute("value");
        } catch (Exception e) {
            return "";
        }
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

    public boolean documentView() {
        try {
            WaitUtils.waitForElementVisible(driver, documentView, 10);
            return driver.findElement(documentView).isDisplayed();
        } catch (Exception e) {
            System.out.println("Document view not displayed: " + e.getMessage());
            return false;
        }
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

    /**
     * Click a file row by zero-based index in the Received Files panel.
     */
    public void clickFileByIndex(int index) {
        List<WebElement> files = driver.findElements(fileCard);
        if (index >= files.size()) {
            throw new IndexOutOfBoundsException("File index " + index + " out of bounds (total files: " + files.size() + ")");
        }
        files.get(index).click();
    }

    /**
     * Get all invoice rows from the Invoice List panel using the
     * absolute container XPath provided in the test spec.
     * Falls back to getRightPanelRows() if nothing is found.
     */
    public List<WebElement> getInvoiceListRows() {
        // Primary: exact tbody rows inside the invoice list panel
        By primary = By.xpath(
            "/html/body/ng-component/div/div/div[2]/file-history-app/div/div[2]" +
            "/cyclone-invoice-history-list/div/div/div/div/div[3]" +
            "/div/form/p-table/div/div/table/tbody/tr");
        List<WebElement> rows = driver.findElements(primary);
        if (!rows.isEmpty()) {
            System.out.println("✓ getInvoiceListRows: found " + rows.size() + " row(s) via primary XPath");
            return rows;
        }
        // Fallback
        System.out.println("⚠ getInvoiceListRows: primary XPath returned 0, falling back to getRightPanelRows()");
        return getRightPanelRows();
    }

    /**
     * Get the status badge text of an invoice row (1-based index) from the
     * Invoice List panel using the absolute XPath supplied in the test spec.
     *
     * @param rowIndex 1-based row index
     */
    public String getInvoiceStatusByIndex(int rowIndex) {
        try {
            // Primary: absolute XPath with dynamic row index
            By statusLocator = By.xpath(
                "/html/body/ng-component/div/div/div[2]/file-history-app/div/div[2]" +
                "/cyclone-invoice-history-list/div/div/div/div/div[3]" +
                "/div/form/p-table/div/div/table/tbody/tr[" + rowIndex + "]/td/span[2]/div[1]/div[7]/div[1]/p-badge/span");
            List<WebElement> badges = driver.findElements(statusLocator);
            if (!badges.isEmpty()) {
                String status = badges.get(0).getText().trim();
                System.out.println("✓ Invoice row[" + rowIndex + "] status (primary XPath): '" + status + "'");
                return status;
            }

            // Fallback: use getInvoiceRowStatus on the nth row from getInvoiceListRows()
            List<WebElement> rows = getInvoiceListRows();
            if (rowIndex <= rows.size()) {
                String status = getInvoiceRowStatus(rows.get(rowIndex - 1));
                System.out.println("✓ Invoice row[" + rowIndex + "] status (fallback): '" + status + "'");
                return status;
            }
        } catch (Exception e) {
            System.out.println("✗ getInvoiceStatusByIndex(" + rowIndex + "): " + e.getMessage());
        }
        return "";
    }

    /**
     * Click the Delete icon of an invoice row (1-based index) in the Invoice List panel.
     *
     * @param rowIndex 1-based row index
     */
    public void clickDeleteIconByIndex(int rowIndex) {
        try {
            // Primary: locate delete button inside the specific row's action section
            By deleteBtn = By.xpath(
                "/html/body/ng-component/div/div/div[2]/file-history-app/div/div[2]" +
                "/cyclone-invoice-history-list/div/div/div/div/div[3]" +
                "/div/form/p-table/div/div/table/tbody/tr[" + rowIndex + "]/td/span[2]" +
                "//button[@ptooltip='Delete Invoice' or @icon='pi pi-trash']");
            List<WebElement> btns = driver.findElements(deleteBtn);
            if (!btns.isEmpty()) {
                System.out.println("✓ Clicking Delete icon on invoice row[" + rowIndex + "] via primary XPath");
                btns.get(0).click();
                return;
            }

            // Fallback: use the nth element from the global invoiceDeleteIcon list
            List<WebElement> allDeleteIcons = driver.findElements(invoiceDeleteIcon);
            if (rowIndex <= allDeleteIcons.size()) {
                System.out.println("✓ Clicking Delete icon on invoice row[" + rowIndex + "] via fallback list index");
                allDeleteIcons.get(rowIndex - 1).click();
                return;
            }
            throw new RuntimeException("No Delete icon found for row index " + rowIndex);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("clickDeleteIconByIndex(" + rowIndex + ") failed: " + e.getMessage());
        }
    }

    /**
     * Return the total number of invoice rows currently visible in the Invoice List panel.
     */
    public int getInvoiceRowCountInPanel() {
        return getInvoiceListRows().size();
    }

    /**
     * Wait until the Deleted Count for a specific left-panel file row (1-based)
     * increases above {@code oldCount}, polling every 500 ms up to {@code maxWaitSeconds}.
     *
     * @param fileRowIndex    1-based row index in left panel
     * @param oldCount        the Deleted Count value that existed BEFORE the deletion
     * @param maxWaitSeconds  maximum seconds to poll
     * @return the new Deleted Count once it changes, or oldCount if timeout is reached
     */
    public int waitForDeletedCountToIncrease(int fileRowIndex, int oldCount, int maxWaitSeconds) {
        System.out.println("⏳ Waiting for Deleted Count to increase above " + oldCount +
                           " for file row[" + fileRowIndex + "] (max " + maxWaitSeconds + "s)...");
        long deadline = System.currentTimeMillis() + (maxWaitSeconds * 1000L);
        while (System.currentTimeMillis() < deadline) {
            int current = getDeletedCountByFileIndex(fileRowIndex);
            System.out.println("   → Deleted Count now: " + current);
            if (current > oldCount) {
                System.out.println("✓ Deleted Count increased to " + current);
                return current;
            }
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
        System.out.println("✗ Deleted Count did not increase within " + maxWaitSeconds + "s");
        return getDeletedCountByFileIndex(fileRowIndex);
    }

    /**
     * Get the Deleted Count displayed on a specific file row (1-based) in the
     * Received Files (left) panel.
     * XPath pattern from spec:
     *   …/tbody/tr[N]/td/span[2]/div/div[7]/div[5]
     *
     * @param fileRowIndex 1-based row index in the left-panel file table
     * @return parsed integer deleted count, or -1 if not readable
     */
    public int getDeletedCountByFileIndex(int fileRowIndex) {
        try {
            // Primary: absolute XPath with dynamic row index
            By locator = By.xpath(
                "/html/body/ng-component/div/div/div[2]/file-history-app/div/div[1]" +
                "/cyclone-list-file-history/div/div/div/div/div[2]/div/div/form" +
                "/p-table/div/div/table/tbody/tr[" + fileRowIndex + "]/td/span[2]/div/div[7]/div[5]");
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                String raw = elements.get(0).getText().trim();
                System.out.println("✓ getDeletedCountByFileIndex[" + fileRowIndex + "] raw text: '" + raw + "'");
                // Extract numeric value – text may be "Deleted: 2" or just "2"
                String numeric = raw.replaceAll("[^0-9]", "");
                return numeric.isEmpty() ? 0 : Integer.parseInt(numeric);
            }

            // Fallback: use getFileRowDeletedCount via left panel rows list
            List<WebElement> leftRows = getLeftPanelRows();
            if (fileRowIndex <= leftRows.size()) {
                return getFileRowDeletedCount(leftRows.get(fileRowIndex - 1));
            }
        } catch (Exception e) {
            System.out.println("✗ getDeletedCountByFileIndex(" + fileRowIndex + "): " + e.getMessage());
        }
        return -1;
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
     * Get all visible invoice rows in Invoice List panel with fallback strategies
     */
    public List<WebElement> getRightPanelRows() {
        // Strategy 1: Standard second p-table with selectable rows
        try {
            By rightRows = By.xpath("(//p-table)[2]//tbody//tr[contains(@class,'p-selectable-row')][td]");
            List<WebElement> rows = driver.findElements(rightRows);
            if (rows.size() > 0) {
                System.out.println("✓ Found " + rows.size() + " invoice rows using Strategy 1 (standard locator)");
                return rows;
            }
        } catch (Exception e) {
            System.out.println("✗ Strategy 1 failed: " + e.getMessage());
        }

        // Strategy 2: Invoice history component directly
        try {
            By rightRows = By.xpath("//cyclone-invoice-history-list//tbody//tr[contains(@class,'p-selectable-row')]");
            List<WebElement> rows = driver.findElements(rightRows);
            if (rows.size() > 0) {
                System.out.println("✓ Found " + rows.size() + " invoice rows using Strategy 2 (component locator)");
                return rows;
            }
        } catch (Exception e) {
            System.out.println("✗ Strategy 2 failed: " + e.getMessage());
        }

        // Strategy 3: Any selectable row in Invoice List panel
        try {
            By rightRows = By.xpath("//div[contains(@class,'invoice')]//tbody//tr[td]");
            List<WebElement> rows = driver.findElements(rightRows);
            if (rows.size() > 0) {
                System.out.println("✓ Found " + rows.size() + " invoice rows using Strategy 3 (generic locator)");
                return rows;
            }
        } catch (Exception e) {
            System.out.println("✗ Strategy 3 failed: " + e.getMessage());
        }

        System.out.println("✗ All strategies failed - returning empty list");
        return new ArrayList<>();
    }

    /**
     * Get status badge text from a file row (left panel)
     */
    public String getFileRowStatus(WebElement row) {
        try {
            // Strategy 1: Direct p-badge lookup
            try {
                By statusBadge = By.xpath(".//p-badge/span");
                WebElement badge = row.findElement(statusBadge);
                if (badge.isDisplayed()) {
                    String status = badge.getText().trim();
                    if (!status.isEmpty()) {
                        return status;
                    }
                }
            } catch (Exception e) {
                System.out.println("✗ Strategy 1 failed (p-badge/span)");
            }

            // Strategy 2: Badge within status column (common structure)
            try {
                By statusBadge = By.xpath(".//td//p-badge//span");
                String status = row.findElement(statusBadge).getText().trim();
                if (!status.isEmpty()) {
                    System.out.println("✓ Got file status (Strategy 2): " + status);
                    return status;
                }
            } catch (Exception e) {
                System.out.println("✗ Strategy 2 failed (td//p-badge//span)");
            }

            // Strategy 3: Span within any status-related div
            try {
                By statusBadge = By.xpath(".//div[contains(@class,'status')]//span | .//span[contains(@class,'badge')]");
                String status = row.findElement(statusBadge).getText().trim();
                if (!status.isEmpty()) {
                    System.out.println("✓ Got file status (Strategy 3): " + status);
                    return status;
                }
            } catch (Exception e) {
                System.out.println("✗ Strategy 3 failed (status div/span)");
            }

            // Strategy 4: Badge in file details section (expanded row structure)
            try {
                By statusBadge = By.xpath(".//span[2]//p-badge//span | .//td/span[2]//p-badge/span");
                String status = row.findElement(statusBadge).getText().trim();
                if (!status.isEmpty()) {
                    System.out.println("✓ Got file status (Strategy 4): " + status);
                    return status;
                }
            } catch (Exception e) {
                System.out.println("✗ Strategy 4 failed (span[2]//p-badge)");
            }

            // Strategy 5: Direct badge class lookup
            try {
                By statusBadge = By.xpath(".//*[contains(@class,'p-badge')]//span");
                String status = row.findElement(statusBadge).getText().trim();
                if (!status.isEmpty()) {
                    System.out.println("✓ Got file status (Strategy 5): " + status);
                    return status;
                }
            } catch (Exception e) {
                System.out.println("✗ Strategy 5 failed (p-badge class)");
            }

            System.out.println("⚠ All strategies failed to locate file status badge");
            return "";
        } catch (Exception e) {
            System.out.println("Error getting file status: " + e.getMessage());
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
     * Get Deleted Count from a file row (left panel)
     * XPath: .//td/span[2]/div/div[7]/div[5]
     * Matches the absolute path: …/tbody/tr[N]/td/span[2]/div/div[7]/div[5]
     */
    public int getFileRowDeletedCount(WebElement row) {
        try {
            By deletedCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[5]");
            String text = row.findElement(deletedCountLocator).getText().trim();
            System.out.println("✓ getFileRowDeletedCount raw text: '" + text + "'");
            String numeric = text.replaceAll("[^0-9]", "");
            return numeric.isEmpty() ? 0 : Integer.parseInt(numeric);
        } catch (Exception e) {
            System.out.println("✗ getFileRowDeletedCount failed: " + e.getMessage());
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
     * Check if right panel has data with enhanced debugging
     */
    public boolean hasRightPanelData() {
        try {
            List<WebElement> invoiceRows = getRightPanelRows();
            int rowCount = invoiceRows.size();

            if (rowCount > 0) {
                System.out.println("✓ Right panel has " + rowCount + " invoice row(s)");
                return true;
            } else {
                System.out.println("✗ Right panel has 0 invoice rows");

                // Try alternative locator strategies for debugging
                try {
                    By alternativeRows = By.xpath("//cyclone-invoice-history-list//tbody//tr");
                    int altCount = driver.findElements(alternativeRows).size();
                    System.out.println("ℹ Alternative locator found " + altCount + " rows");
                } catch (Exception e) {
                    System.out.println("⚠ Alternative locator also failed: " + e.getMessage());
                }

                return false;
            }
        } catch (Exception e) {
            System.out.println("✗ Error checking right panel data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Wait for invoice list data to be available after a search or filter operation
     * Returns true if data is loaded within timeout, false otherwise
     */
    public boolean waitForInvoiceDataToLoad(int maxWaitSeconds) {
        System.out.println("⏳ Waiting for invoice data to load (max " + maxWaitSeconds + " seconds)...");
        int attempts = 0;
        int maxAttempts = maxWaitSeconds * 2; // Check every 500ms
        boolean dataFoundAtLeastOnce = false;

        while (attempts < maxAttempts) {
            try {
                // Check if invoice rows are present
                if (hasRightPanelData()) {
                    dataFoundAtLeastOnce = true;
                    // Wait a bit longer to ensure data is stable
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                    // Re-check to confirm data is still there
                    if (hasRightPanelData()) {
                        System.out.println("✓ Invoice data loaded and stable after " + (attempts * 500) + "ms");
                        return true;
                    }
                }

                // Also check if total invoice count element is visible as alternative indicator
                try {
                    String countText = getTotalInvoiceCountText();
                    if (countText != null && !countText.isEmpty() && countText.contains("Total Invoice Count")) {
                        System.out.println("ℹ Invoice count header detected: " + countText);
                        // Extract count to see if there are results
                        int count = extractNumericCountFromInvoiceHeader(countText);
                        if (count > 0) {
                            System.out.println("ℹ Invoice count shows " + count + " invoices");
                            // Re-check for rows after seeing positive count
                            try {
                                Thread.sleep(1000); // Give more time for rows to render
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                            if (hasRightPanelData()) {
                                System.out.println("✓ Invoice data loaded after seeing positive count header");
                                return true;
                            }
                        } else if (count == 0) {
                            System.out.println("⚠ Search returned 0 results");
                            return false; // No point waiting if count is 0
                        }
                    }
                } catch (Exception e) {
                    // Count text not available, continue waiting
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
                attempts++;
            } catch (Exception e) {
                System.out.println("⚠ Error while waiting for invoice data: " + e.getMessage());
                attempts++;
            }
        }

        System.out.println("✗ Invoice data did not load within " + maxWaitSeconds + " seconds");
        return false;
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

    /**
     * Get selected value from File Type dropdown
     */
    public String getSelectedFileType() {
        try {
            // Strategy 1: Try direct p-dropdown span
            try {
                By selectedValue1 = By.xpath("//label[text()='File Type']/following::p-dropdown[1]//span[contains(@class,'p-dropdown-label')]");
                String text = driver.findElement(selectedValue1).getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Got selected file type (Strategy 1): " + text);
                    return text;
                }
            } catch (Exception ignored) {}

            // Strategy 2: Try using the field input structure
            try {
                By selectedValue2 = By.xpath("//label[text()='File Type']/..//p-dropdown//span[contains(@class,'p-dropdown-label')]");
                String text = driver.findElement(selectedValue2).getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Got selected file type (Strategy 2): " + text);
                    return text;
                }
            } catch (Exception ignored) {}

            // Strategy 3: Use the dropdown that contains the File Type field
            try {
                By selectedValue3 = By.xpath("//div[contains(@class,'p-field')]//label[text()='File Type']/following-sibling::div//p-dropdown//span[contains(@class,'p-dropdown-label')]");
                String text = driver.findElement(selectedValue3).getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Got selected file type (Strategy 3): " + text);
                    return text;
                }
            } catch (Exception ignored) {}

            // Strategy 4: Try with the fileTypeDropdown locator
            try {
                WebElement dropdown = driver.findElement(fileTypeDropdown);
                By labelSpan = By.xpath(".//span[contains(@class,'p-dropdown-label')]");
                String text = dropdown.findElement(labelSpan).getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Got selected file type (Strategy 4): " + text);
                    return text;
                }
            } catch (Exception ignored) {}

            // Strategy 5: Get the input element value attribute
            try {
                By inputValue = By.xpath("//label[text()='File Type']/following::input[contains(@class,'p-dropdown')]");
                String text = driver.findElement(inputValue).getAttribute("value");
                if (text != null && !text.isEmpty()) {
                    System.out.println("✓ Got selected file type (Strategy 5): " + text);
                    return text;
                }
            } catch (Exception ignored) {}

            System.out.println("⚠ All strategies failed to get selected file type");
            return "";
        } catch (Exception e) {
            System.out.println("Error getting selected file type: " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if Total File Count is displayed
     */
    public boolean isTotalFileCountDisplayed() {
        try {
            return driver.findElement(totalFileCount).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get Total File Count text (e.g., "Total File Count: 21")
     */
    public String getTotalFileCount() {
        try {
            return driver.findElement(totalFileCount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting total file count: " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if Total Invoice Count is displayed
     */
    public boolean isTotalInvoiceCountDisplayed() {
        try {
            return driver.findElement(totalInvoiceCount).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get Total Invoice Count text (e.g., "Total Invoice Count: 9")
     */
    public String getTotalInvoiceCount() {
        try {
            return driver.findElement(totalInvoiceCount).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting total invoice count: " + e.getMessage());
            return "";
        }
    }

    /**
     * Extract numeric count from text like "Total File Count: 21"
     */
    public int extractNumericCount(String text) {
        try {
            String numericPart = text.replaceAll("[^0-9]", "");
            return Integer.parseInt(numericPart);
        } catch (Exception e) {
            System.out.println("Error extracting numeric count from: " + text);
            return 0;
        }
    }

    /**
     * Extract numeric count from invoice header (handles "Total Invoice Count: 9 | Total Assigned: 5 | Total Unassigned: 4")
     */
    public int extractNumericCountFromInvoiceHeader(String headerText) {
        try {
            // Extract first number after "Total Invoice Count:"
            String[] parts = headerText.split("\\|");
            if (parts.length > 0) {
                String totalPart = parts[0].trim();
                String numericPart = totalPart.replaceAll("[^0-9]", "");
                return Integer.parseInt(numericPart);
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Error extracting invoice count from header: " + headerText);
            return 0;
        }
    }

    /**
     * Validate first file has complete data (File ID, Name, Status, Counts)
     */
    public boolean validateFirstFileData() {
        try {
            if (!hasLeftPanelData()) {
                return false;
            }

            WebElement firstFile = getLeftPanelRows().get(0);

            // Check File ID
            By fileIdLocator = By.xpath(".//div[@class='p-col-fixed app-p-0'][1]//b");
            String fileId = firstFile.findElement(fileIdLocator).getText().trim();
            if (fileId.isEmpty()) {
                System.out.println("DEBUG: File ID is empty");
                return false;
            }

            // Check File Name
            String fileName = getFileRowFileName(firstFile);
            if (fileName.isEmpty()) {
                System.out.println("DEBUG: File Name is empty");
                return false;
            }

            // Check Status (Optional - may not be present for files in Processing state)
            String status = getFileRowStatus(firstFile);
            if (status.isEmpty()) {
                System.out.println("⚠ WARNING: File Status badge is not visible (may be in Processing or initial state)");
                System.out.println("ℹ INFO: Continuing validation - Status badge is optional for certain file states");
            } else {
                System.out.println("✓ File Status badge found: " + status);
            }

            // Check Invoice Count exists (can be 0)
            By invoiceCountLocator = By.xpath(".//td/span[2]/div/div[7]/div[2]");
            String invoiceCountText = firstFile.findElement(invoiceCountLocator).getText().trim();
            if (invoiceCountText == null) {
                System.out.println("DEBUG: Invoice Count is null");
                return false;
            }

            System.out.println("✓ First file data validated - ID: " + fileId + ", Name: " + fileName + ", Status: " + (status.isEmpty() ? "N/A" : status));
            return true;
        } catch (Exception e) {
            System.out.println("Error validating first file data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validate first invoice has complete data (Invoice #, Date, Applicant, Amount, Status)
     */
    public boolean validateFirstInvoiceData() {
        try {
            if (!hasRightPanelData()) {
                System.out.println("✗ No invoice data in right panel");
                return false;
            }

            WebElement firstInvoice = getRightPanelRows().get(0);

            // Get all td elements to understand the structure
            List<WebElement> tdElements = firstInvoice.findElements(By.xpath(".//td"));
            System.out.println("ℹ DEBUG: Invoice row has " + tdElements.size() + " <td> elements");
            for (int i = 0; i < Math.min(tdElements.size(), 2); i++) {
                String cellText = tdElements.get(i).getText().trim();
                // Limit output for large cells
                String displayText = cellText.length() > 150 ? cellText.substring(0, 150) + "..." : cellText;
                System.out.println("  Cell[" + i + "]: '" + displayText + "'");
            }

            String invoiceNumber = "";
            String invoiceDate = "";
            String applicant = "";
            String amount = "";

            // Strategy 1: Nested structure within single td (based on provided XPath: td/span[2]/div[1]/div[2] for invoice number)
            if (tdElements.size() == 1) {
                System.out.println("ℹ Trying Strategy 1: Nested structure (single td with nested divs)");
                try {
                    // Based on XPath: /html/.../tr[1]/td/span[2]/div[1]/div[2] for invoice number
                    WebElement mainTd = tdElements.get(0);

                    // Invoice Number: td/span[2]/div[1]/div[2]
                    try {
                        invoiceNumber = mainTd.findElement(By.xpath(".//span[2]/div[1]/div[2]")).getText().trim();
                    } catch (Exception e) {
                        System.out.println("  Could not find invoice number using td/span[2]/div[1]/div[2]");
                    }

                    // Invoice Date: Try multiple locations
                    if (invoiceDate.isEmpty()) {
                        String[] dateXPaths = {
                            ".//span[2]/div[1]/div[3]",
                            ".//span[2]/div[2]/div[2]",
                            ".//div[contains(text(),'/') and string-length(text())=10][1]"
                        };
                        for (String xpath : dateXPaths) {
                            try {
                                String dateText = mainTd.findElement(By.xpath(xpath)).getText().trim();
                                if (dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
                                    invoiceDate = dateText;
                                    break;
                                }
                            } catch (Exception ignored) {}
                        }
                    }

                    // Applicant: Try multiple locations
                    if (applicant.isEmpty()) {
                        String[] applicantXPaths = {
                            ".//span[2]/div[1]/div[4]",
                            ".//span[2]/div[1]/div[5]",
                            ".//span[2]/div[2]/div[3]"
                        };
                        for (String xpath : applicantXPaths) {
                            try {
                                String applicantText = mainTd.findElement(By.xpath(xpath)).getText().trim();
                                if (!applicantText.isEmpty() && !applicantText.matches("\\$[\\d,]+\\.\\d{2}") &&
                                    !applicantText.matches("\\d{2}/\\d{2}/\\d{4}") && applicantText.length() > 3) {
                                    applicant = applicantText;
                                    break;
                                }
                            } catch (Exception ignored) {}
                        }
                    }

                    // Amount: Look for $ sign
                    if (amount.isEmpty()) {
                        try {
                            amount = mainTd.findElement(By.xpath(".//div[starts-with(normalize-space(),\"$\") and string-length(normalize-space()) > 1][1]")).getText().trim();
                        } catch (Exception e) {
                            System.out.println("  Could not find amount using $ prefix search");
                        }
                    }

                    if (!invoiceNumber.isEmpty()) {
                        System.out.println("✓ Strategy 1 succeeded");
                    }
                } catch (Exception e) {
                    System.out.println("✗ Strategy 1 failed: " + e.getMessage());
                }
            }

            // Strategy 2: Try standard structure (checkbox + 4+ data columns)
            if (invoiceNumber.isEmpty() && tdElements.size() >= 5) {
                System.out.println("ℹ Trying Strategy 2: Standard structure (checkbox + 4 data columns)");
                try {
                    invoiceNumber = tdElements.get(1).getText().trim();  // Skip checkbox column
                    invoiceDate = tdElements.get(2).getText().trim();
                    applicant = tdElements.get(3).getText().trim();
                    amount = tdElements.get(4).getText().trim();
                    System.out.println("✓ Strategy 2 succeeded");
                } catch (Exception e) {
                    System.out.println("✗ Strategy 2 failed: " + e.getMessage());
                }
            }

            // Strategy 3: Try without checkbox column (4 data columns only)
            if (invoiceNumber.isEmpty() && tdElements.size() >= 4) {
                System.out.println("ℹ Trying Strategy 3: Structure without checkbox (4 data columns)");
                try {
                    invoiceNumber = tdElements.get(0).getText().trim();
                    invoiceDate = tdElements.get(1).getText().trim();
                    applicant = tdElements.get(2).getText().trim();
                    amount = tdElements.get(3).getText().trim();
                    System.out.println("✓ Strategy 3 succeeded");
                } catch (Exception e) {
                    System.out.println("✗ Strategy 3 failed: " + e.getMessage());
                }
            }

            // Strategy 4: Parse from single cell text content (fallback)
            if (invoiceNumber.isEmpty() && tdElements.size() == 1) {
                System.out.println("ℹ Trying Strategy 4: Parse single cell text");
                try {
                    String cellText = tdElements.get(0).getText();
                    String[] lines = cellText.split("\\n");

                    for (String line : lines) {
                        line = line.trim();
                        if (line.isEmpty() || line.startsWith("File:")) continue;

                        if (invoiceNumber.isEmpty() && line.matches("^\\d{4,6}$")) {
                            invoiceNumber = line;
                        } else if (invoiceDate.isEmpty() && line.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            invoiceDate = line;
                        } else if (amount.isEmpty() && line.matches("^\\$[\\d,]+\\.\\d{2}$")) {
                            amount = line;
                        } else if (!line.matches("(?i)(Success|Fail|Processing|Duplicate|Manually Corrected).*") &&
                                   applicant.isEmpty() && !line.matches("^\\d+$") && line.length() > 3) {
                            applicant = line;
                        }
                    }

                    if (!invoiceNumber.isEmpty()) {
                        System.out.println("✓ Strategy 4 succeeded");
                    }
                } catch (Exception e) {
                    System.out.println("✗ Strategy 4 failed: " + e.getMessage());
                }
            }

            // Log the extracted data
            System.out.println("ℹ Extracted Invoice Data:");
            System.out.println("  Invoice #: '" + invoiceNumber + "'");
            System.out.println("  Date: '" + invoiceDate + "'");
            System.out.println("  Applicant: '" + applicant + "'");
            System.out.println("  Amount: '" + amount + "'");

            // Check Status - used to determine which fields are mandatory
            String status = getInvoiceRowStatus(firstInvoice);
            if (status.isEmpty()) {
                System.out.println("⚠ WARNING: Invoice Status badge not found (optional)");
            } else {
                System.out.println("✓ Invoice Status badge found: " + status);
            }

            // Determine if this invoice is in a "fully processed" success state
            // Only Success and Success/Manually Corrected invoices must have all fields populated
            boolean isSuccessStatus = status.equalsIgnoreCase("Success")
                    || status.equalsIgnoreCase("Success/Manually Corrected");

            // Validate required fields - Invoice # and Date are always mandatory
            if (invoiceNumber.isEmpty()) {
                System.out.println("✗ Invoice Number is empty");
                return false;
            }

            if (invoiceDate.isEmpty()) {
                System.out.println("✗ Invoice Date is empty");
                return false;
            }

            // Applicant and Amount are mandatory ONLY for Success / Success/Manually Corrected invoices.
            // For other statuses (Deleted, Fail, Duplicate, Processing, Fail/Manually Corrected, etc.)
            // these fields may legitimately be absent.
            if (isSuccessStatus) {
                if (applicant.isEmpty()) {
                    System.out.println("✗ Applicant is empty for a Success status invoice");
                    return false;
                }
                if (amount.isEmpty()) {
                    System.out.println("✗ Amount is empty for a Success status invoice");
                    return false;
                }
            } else {
                if (applicant.isEmpty()) {
                    System.out.println("⚠ Applicant is empty - acceptable for status: '" + status + "'");
                }
                if (amount.isEmpty()) {
                    System.out.println("⚠ Amount is empty - acceptable for status: '" + status + "'");
                }
            }

            System.out.println("✓ First invoice data validated - #: " + invoiceNumber + ", Date: " + invoiceDate +
                             ", Applicant: " + (applicant.isEmpty() ? "(empty – status: " + status + ")" : applicant) +
                             ", Amount: " + (amount.isEmpty() ? "(empty – status: " + status + ")" : amount));
            return true;
        } catch (Exception e) {
            System.out.println("Error validating first invoice data: " + e.getMessage());
            return false;
        }
    }
}
