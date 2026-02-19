package com.cyclonercm.pages;

import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for DMS Daily Billing page.
 * Supports SMOKE_DB_001 through SMOKE_DB_010 test cases.
 */
public class DailyBillingPage {

    private WebDriver driver;

    public DailyBillingPage(WebDriver driver) {
        this.driver = driver;
    }

    // ========== NAVIGATION LOCATORS ==========

    private final By megaMenu = By.xpath("//p-megamenu//span[@class='p-menuitem-icon pi pi-fw pi-bars ng-star-inserted']");
    private final By mastersTab = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-megamenu/div/ul/li/div/div/div[1]/ul/li[1]/span");
    private final By dailyBillingMenu = By.xpath("//p-megamenu//a[.//span[contains(text(),'Daily Billing')]] | /html/body/ng-component/div/div/div[1]/div/div[2]/p-megamenu/div/ul/li/div/div/div[2]/ul/li[1]/a");

    // ========== PAGE HEADER LOCATORS ==========

    private final By dailyBillingHeader = By.xpath("//span[contains(text(),'Daily Billing')] | //div[contains(@class,'p-toolbar-group-left')]//span[contains(text(),'Daily Billing')]");
    private final By totalBillingCount = By.xpath("//strong[contains(text(),'Total Billing Count:')] | //span[contains(text(),'Total Billing Count:')]");
    private final By newBillingButton = By.xpath("//button[@ptooltip='New Billing'] | //button[@icon='pi pi-plus' and contains(@class,'p-button')]");

    // ========== BILLING LIST LOCATORS ==========

    private final By billingListSection = By.xpath("//cyclone-daily-billing-list | //div[contains(@class,'daily-billing')]");
    private final By billingTableRows = By.xpath("//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'] | //p-table//tbody//tr[contains(@class,'p-selectable-row')]");
    private final By firstBillingRow = By.xpath("(//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'])[1] | (//p-table//tbody//tr[contains(@class,'p-selectable-row')])[1]");

    // Billing list column headers
    private final By columnInvoiceNumber = By.xpath("//p-table//th[contains(.,'Invoice #')] | //p-table//th[contains(.,'Invoice')]");
    private final By columnDate = By.xpath("//p-table//th[contains(.,'Date')]");
    private final By columnApplicant = By.xpath("//p-table//th[contains(.,'Applicant')]");
    private final By columnProvider = By.xpath("//p-table//th[contains(.,'Provider')]");
    private final By columnStatus = By.xpath("//p-table//th[contains(.,'Status')]");
    private final By columnAmount = By.xpath("//p-table//th[contains(.,'Amount')]");

    // First row cell data
    private final By firstRowInvoiceNumber = By.xpath("(//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td[1] | (//p-table//tbody//tr[contains(@class,'p-selectable-row')])[1]//td[1]");
    private final By firstRowDate = By.xpath("(//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td[2] | (//p-table//tbody//tr[contains(@class,'p-selectable-row')])[1]//td[2]");
    private final By firstRowApplicant = By.xpath("(//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td[3] | (//p-table//tbody//tr[contains(@class,'p-selectable-row')])[1]//td[3]");
    private final By firstRowStatus = By.xpath("(//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//p-badge//span | (//p-table//tbody//tr[contains(@class,'p-selectable-row')])[1]//p-badge//span");
    private final By firstRowAmount = By.xpath("(//cyclone-daily-billing-list//tr[@class='p-element p-selectable-row ng-star-inserted'])[1]//td[last()] | (//p-table//tbody//tr[contains(@class,'p-selectable-row')])[1]//td[last()]");

    // ========== FILTER / SEARCH LOCATORS ==========

    private final By statusFilterDropdown = By.xpath("(//cyclone-daily-billing-list//p-dropdown)[1] | //label[contains(text(),'Status')]/following::p-dropdown[1]");
    private final By fromDateInput = By.xpath("//label[contains(.,'From Date')]/following-sibling::p-calendar//input | //div[contains(@class,'p-col')]//label[contains(.,'From Date')]/..//input[@placeholder='MM/DD/YYYY']");
    private final By toDateInput = By.xpath("//label[contains(.,'To Date')]/following-sibling::p-calendar//input | //div[contains(@class,'p-col')]//label[contains(.,'To Date')]/..//input[@placeholder='MM/DD/YYYY']");
    private final By fromDateButton = By.xpath("//label[contains(.,'From Date')]/following-sibling::p-calendar//button | //label[contains(.,'From Date')]/..//button[contains(@class,'p-datepicker-trigger')]");
    private final By toDateButton = By.xpath("//label[contains(.,'To Date')]/following-sibling::p-calendar//button | //label[contains(.,'To Date')]/..//button[contains(@class,'p-datepicker-trigger')]");
    private final By searchButton = By.xpath("//button[@ptooltip='Search'] | //button[@ptooltip='Search by date range']");
    private final By clearButton = By.xpath("//button[@ptooltip='Clear'] | //button[@ptooltip='Clear date search']");
    private final By searchInvoiceField = By.xpath("//input[@placeholder='Search by invoice #'] | //input[@placeholder='Search Invoice #'] | //input[contains(@placeholder,'invoice')]");
    private final By invoiceSearchButton = By.xpath("//button[@ptooltip='Search Invoice'] | //button[@icon='pi pi-search']");
    private final By dateRangeActiveLabel = By.xpath("//a[contains(@class,'info-link') and contains(.,'Date Range Active')]");

    // ========== PAGINATION LOCATORS ==========

    private final By paginatorFirstButton = By.xpath("//p-paginator//button[contains(@class,'p-paginator-first')]");
    private final By paginatorPrevButton = By.xpath("//p-paginator//button[contains(@class,'p-paginator-prev')]");
    private final By paginatorNextButton = By.xpath("//p-paginator//button[contains(@class,'p-paginator-next')]");
    private final By paginatorLastButton = By.xpath("//p-paginator//button[contains(@class,'p-paginator-last')]");
    private final By currentPageHighlighted = By.xpath("//p-paginator//button[contains(@class,'p-paginator-page') and contains(@class,'p-highlight')]");

    // ========== DROPDOWN OPTIONS LOCATOR ==========

    private final By dropdownPanel = By.xpath("//div[contains(@class,'p-dropdown-panel')]");
    private final By dropdownOptions = By.xpath("//div[contains(@class,'p-dropdown-panel')]//li[contains(@class,'p-dropdown-item')]");

    // ========== ACTIONS ==========

    public void clickMegaMenu() {
        driver.findElement(megaMenu).click();
    }

    public void clickDailyBillingOption() {
        driver.findElement(dailyBillingMenu).click();
    }

    public String getMastersTabText() {
        return driver.findElement(mastersTab).getText().trim();
    }

    // -------- Page Header --------

    public boolean isDailyBillingHeaderDisplayed() {
        try {
            return driver.findElement(dailyBillingHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDailyBillingHeaderText() {
        try {
            return driver.findElement(dailyBillingHeader).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getTotalBillingCountText() {
        try {
            return driver.findElement(totalBillingCount).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isTotalBillingCountDisplayed() {
        try {
            return driver.findElement(totalBillingCount).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNewBillingButtonDisplayed() {
        try {
            return driver.findElement(newBillingButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickNewBillingButton() {
        driver.findElement(newBillingButton).click();
    }

    // -------- Billing List --------

    public boolean isBillingListDisplayed() {
        try {
            return driver.findElement(billingListSection).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasBillingRows() {
        try {
            List<WebElement> rows = driver.findElements(billingTableRows);
            return !rows.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public int getBillingRowCount() {
        try {
            return driver.findElements(billingTableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getFirstRowInvoiceNumber() {
        try {
            return driver.findElement(firstRowInvoiceNumber).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getFirstRowDate() {
        try {
            return driver.findElement(firstRowDate).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getFirstRowApplicant() {
        try {
            return driver.findElement(firstRowApplicant).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getFirstRowStatus() {
        try {
            return driver.findElement(firstRowStatus).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getFirstRowAmount() {
        try {
            return driver.findElement(firstRowAmount).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // -------- Column Headers --------

    public boolean isInvoiceNumberColumnDisplayed() {
        try {
            return driver.findElement(columnInvoiceNumber).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDateColumnDisplayed() {
        try {
            return driver.findElement(columnDate).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isApplicantColumnDisplayed() {
        try {
            return driver.findElement(columnApplicant).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStatusColumnDisplayed() {
        try {
            return driver.findElement(columnStatus).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAmountColumnDisplayed() {
        try {
            return driver.findElement(columnAmount).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // -------- Filters / Search --------

    public boolean isStatusFilterDropdownDisplayed() {
        try {
            return driver.findElement(statusFilterDropdown).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickStatusFilterDropdown() {
        driver.findElement(statusFilterDropdown).click();
    }

    public boolean isDropdownOptionDisplayed(String optionText) {
        try {
            WaitUtils.waitForVisibility(driver, dropdownPanel, 5);
            List<WebElement> options = driver.findElements(dropdownOptions);
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(optionText)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectStatusFilter(String statusText) {
        clickStatusFilterDropdown();
        WaitUtils.sleep(1000);
        List<WebElement> options = driver.findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(statusText)) {
                option.click();
                return;
            }
        }
        List<String> availableOptions = options.stream()
                .map(e -> e.getText().trim())
                .collect(java.util.stream.Collectors.toList());
        throw new RuntimeException("Status filter option not found: " + statusText
                + ". Available options: " + availableOptions);
    }

    public boolean isFromDateInputDisplayed() {
        try {
            return driver.findElement(fromDateInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isToDateInputDisplayed() {
        try {
            return driver.findElement(toDateInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterFromDate(String date) {
        WebElement field = driver.findElement(fromDateInput);
        field.click();
        field.clear();
        field.sendKeys(date);
    }

    public void enterToDate(String date) {
        WebElement field = driver.findElement(toDateInput);
        field.click();
        field.clear();
        field.sendKeys(date);
    }

    public boolean isSearchButtonDisplayed() {
        try {
            return driver.findElement(searchButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isClearButtonDisplayed() {
        try {
            return driver.findElement(clearButton).isDisplayed();
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

    public boolean isSearchInvoiceFieldDisplayed() {
        try {
            return driver.findElement(searchInvoiceField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterSearchInvoiceNumber(String invoiceNumber) {
        WebElement field = driver.findElement(searchInvoiceField);
        field.click();
        field.clear();
        field.sendKeys(invoiceNumber);
    }

    public void clickInvoiceSearchButton() {
        driver.findElement(invoiceSearchButton).click();
    }

    public boolean isDateRangeActiveLabelDisplayed() {
        try {
            return driver.findElement(dateRangeActiveLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // -------- Pagination --------

    public boolean isPaginatorDisplayed() {
        try {
            return driver.findElement(paginatorNextButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNextPageButtonEnabled() {
        try {
            WebElement btn = driver.findElement(paginatorNextButton);
            return btn.isEnabled() && !btn.getAttribute("class").contains("p-disabled");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPrevPageButtonEnabled() {
        try {
            WebElement btn = driver.findElement(paginatorPrevButton);
            return btn.isEnabled() && !btn.getAttribute("class").contains("p-disabled");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickNextPage() {
        driver.findElement(paginatorNextButton).click();
    }

    public void clickPrevPage() {
        driver.findElement(paginatorPrevButton).click();
    }

    public String getCurrentPageNumber() {
        try {
            return driver.findElement(currentPageHighlighted).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // -------- Utility --------

    public boolean validateBillingRowsHaveStatus(String expectedStatus) {
        try {
            List<WebElement> statusBadges = driver.findElements(
                    By.xpath("//cyclone-daily-billing-list//p-badge//span | //p-table//tbody//tr//p-badge//span"));
            if (statusBadges.isEmpty()) {
                return false;
            }
            for (WebElement badge : statusBadges) {
                String actual = badge.getText().trim();
                if (!actual.equalsIgnoreCase(expectedStatus)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
