package com.cyclonercm.pages;

import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BillingHistoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BillingHistoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ======================== LOCATORS ========================

    // Navigation
    private final By billingMenu = LocatorConstants.BillingMenu;
    private final By billingHistoryOption =
            By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div/ul/li[3]/a");
    private final By billingHistoryLabel =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[1]/p-toolbar/div/div[1]/span");

    // Biller filter
    private final By billerDropdown =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[3]/div/p-dropdown");
    private final By billerDropdownOptions =
            By.xpath("//p-dropdownitem//span[@class='ng-star-inserted']");

    // Invoice search
    private final By invoiceSearchInput =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[4]/div/div/span/input");
    private final By invoiceSearchButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[4]/div/div/button/span[1]");

    // Table
    private final By historyTable = By.xpath("//p-table");
    private final By tableBillerColumn =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]/td[5]/div");

    // Parent row expand
    private final By datatableBody =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody");
    private final By firstParentRow =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]/td[5]/div");

    // Account filter
    private final By accountDropdownButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[5]/div/p-dropdown/div/div[2]");
    private final By accountSearchField =
            By.xpath("/html/body/div[2]/div[1]/div/input");
    private final By accountSearchButton =
            By.xpath("/html/body/div[2]/div[1]/div/span");
    private final By accountResults =
            By.xpath("/html/body/div[2]/div[2]/ul/p-dropdownitem/li");

    // Applicant filter
    private final By applicantDropdownButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[6]/div/p-dropdown/div/div[2]");
    private final By applicantSearchField =
            By.xpath("/html/body/div[2]/div[1]/div/input");
    private final By applicantSearchButton =
            By.xpath("/html/body/div[2]/div[1]/div/span");
    private final By applicantResults =
            By.xpath("/html/body/div[2]/div[2]/ul/p-dropdownitem[1]/li");

    // Invoice details
    private final By subNumber =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr/td[2]");
    private final By expandedInvoiceNumber =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[2]");
    private final By caseNumber =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[1]/td[1]/b/span[4]/span");
    private final By claimNumber =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[3]/span");
    private final By amount =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[4]/span");
    private final By status =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[5]/div[1]/p-badge/span");

    // EMC/Paper filters
    private final By emcRadioButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[8]/div/div/label[1]/p-radiobutton/div/div[2]");
    private final By paperRadioButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[2]/p-toolbar/div/div/div[8]/div/div/label[2]/p-radiobutton/div/div[2]");
    private final By billingTypeStatus =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]/td[8]/div/span");

    // Re-submit
    private final By fileSelectButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]/td[1]/div");
    private final By emcSubmitButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[1]/p-toolbar/div/div[2]/billing-bill-types/div/div/div/div/div/div/button[1]");
    private final By paperSubmitButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[1]/div/div[1]/p-toolbar/div/div[2]/billing-bill-types/div/div/div/div/div/div/button[4]");

    // Edocs
    private final By edocButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[6]/div");
    private final By edocViewHeading =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-dialog[3]/div/div/div[2]/cyclone-petition-view-edocs/div/div[1]/p-toolbar/div");
    private final By edocCloseButton =
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-dialog[3]/div/div/div[2]/cyclone-petition-view-edocs/div/div[1]/p-toolbar/div/div[2]/button[2]");

    // ======================== NAVIGATION ========================

    public void clickBillingMenu() {
        WaitUtils.waitForVisibility(driver, billingMenu, 30);
        wait.until(ExpectedConditions.elementToBeClickable(billingMenu)).click();
        WaitUtils.sleep(3000);
    }

    public void clickBillingHistoryOption() {
        wait.until(ExpectedConditions.elementToBeClickable(billingHistoryOption)).click();
        WaitUtils.sleep(4000);
    }

    public String getBillingHistoryLabel() {
        WaitUtils.waitForVisibility(driver, billingHistoryLabel, 20);
        return driver.findElement(billingHistoryLabel).getText().trim();
    }

    // ======================== BILLER FILTER ========================

    public void clickBillerDropdown() {
        WaitUtils.waitForVisibility(driver, billerDropdown, 20);
        wait.until(ExpectedConditions.elementToBeClickable(billerDropdown)).click();
        WaitUtils.sleep(3000);
    }

    public String getFirstBillerName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(billerDropdownOptions));
        List<WebElement> options = driver.findElements(billerDropdownOptions);

        for (WebElement option : options) {
            String text = option.getText().trim();
            if (!text.equalsIgnoreCase("All") && !text.isEmpty()) {
                return text;
            }
        }
        return "";
    }

    public void selectBillerByName(String billerName) {
        List<WebElement> options = driver.findElements(billerDropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equals(billerName)) {
                option.click();
                WaitUtils.sleep(3000);
                return;
            }
        }
        throw new RuntimeException("Biller not found: " + billerName);
    }

    public void waitForTableLoad() {
        WaitUtils.waitForVisibility(driver, historyTable, 30);
        WaitUtils.sleep(3000);
    }

    public String getTableBillerName() {
        WaitUtils.waitForVisibility(driver, tableBillerColumn, 20);
        return driver.findElement(tableBillerColumn).getText().trim();
    }

    public String extractBillerLastName(String fullName) {
        if (fullName == null) {
            return "";
        }
        String[] parts = fullName.trim().split("\\s+");
        return parts.length == 0 ? "" : parts[parts.length - 1].trim();
    }

    // ======================== INVOICE SEARCH ========================

    public void enterInvoiceNumber(String invoiceNumber) {
        WaitUtils.waitForVisibility(driver, invoiceSearchInput, 20);
        WebElement input = driver.findElement(invoiceSearchInput);
        input.clear();
        input.sendKeys(invoiceNumber);
        WaitUtils.sleep(1000);
    }

    public void clickInvoiceSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(invoiceSearchButton)).click();
        WaitUtils.sleep(3000);
    }

    // ======================== TABLE EXPANSION ========================

    public void expandFirstParentRow() {
        WaitUtils.waitForVisibility(driver, datatableBody, 20);
        wait.until(ExpectedConditions.elementToBeClickable(firstParentRow)).click();
        WaitUtils.sleep(3000);
    }

    private final By expandedInvoiceNumberLocator = By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody/tr[3]/td[2]");

    public String getTableInvoiceNumber() {
        String invoiceNumber  = driver.findElement(expandedInvoiceNumberLocator).getText().trim();

        return invoiceNumber;
    }

    // ======================== ACCOUNT FILTER ========================

    public void clickAccountDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(accountDropdownButton)).click();
        WaitUtils.sleep(2000);
    }

    public void enterAccountSearchText(String accountText) {
        WaitUtils.waitForVisibility(driver, accountSearchField, 20);
        WebElement searchField = driver.findElement(accountSearchField);
        searchField.clear();
        searchField.sendKeys(accountText);
        WaitUtils.sleep(1000);
    }

    public void clickAccountSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accountSearchButton)).click();
        WaitUtils.sleep(2000);
    }

    public void selectFirstAccountResult() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountResults));
        List<WebElement> results = driver.findElements(accountResults);
        if (!results.isEmpty()) {
            results.get(0).click();
            WaitUtils.sleep(3000);
        }
    }

    // ======================== APPLICANT FILTER ========================

    public void clickApplicantDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(applicantDropdownButton)).click();
        WaitUtils.sleep(2000);
    }

    public void enterApplicantSearchText(String applicantText) {
        WaitUtils.waitForVisibility(driver, applicantSearchField, 20);
        WebElement searchField = driver.findElement(applicantSearchField);
        searchField.clear();
        searchField.sendKeys(applicantText);
        WaitUtils.sleep(1000);
    }

    public void clickApplicantSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(applicantSearchButton)).click();
        WaitUtils.sleep(2000);
    }

    public void selectFirstApplicantResult() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(applicantResults));
        List<WebElement> results = driver.findElements(applicantResults);
        if (!results.isEmpty()) {
            results.get(0).click();
            WaitUtils.sleep(3000);
        }
    }

    // ======================== INVOICE DETAILS ========================

    public String getSubNumber() {
        WaitUtils.waitForVisibility(driver, subNumber, 20);
        return driver.findElement(subNumber).getText().trim();
    }

    public String getExpandedInvoiceNumber() {
        WaitUtils.waitForVisibility(driver, expandedInvoiceNumber, 20);
        return driver.findElement(expandedInvoiceNumber).getText().trim();
    }

    public String getCaseNumber() {
        WaitUtils.waitForVisibility(driver, caseNumber, 20);
        return driver.findElement(caseNumber).getText().trim();
    }

    public String getClaimNumber() {
        WaitUtils.waitForVisibility(driver, claimNumber, 20);
        return driver.findElement(claimNumber).getText().trim();
    }

    public String getAmount() {
        WaitUtils.waitForVisibility(driver, amount, 20);
        return driver.findElement(amount).getText().trim();
    }

    public String getStatus() {
        WaitUtils.waitForVisibility(driver, status, 20);
        return driver.findElement(status).getText().trim();
    }

    // ======================== EMC/PAPER FILTERS ========================

    public void clickEMCRadioButton() {
        wait.until(ExpectedConditions.elementToBeClickable(emcRadioButton)).click();
        WaitUtils.sleep(3000);
    }

    public void clickPaperRadioButton() {
        wait.until(ExpectedConditions.elementToBeClickable(paperRadioButton)).click();
        WaitUtils.sleep(3000);
    }

    public String getBillingTypeStatus() {
        WaitUtils.waitForVisibility(driver, billingTypeStatus, 20);
        return driver.findElement(billingTypeStatus).getText().trim();
    }

    // ======================== RE-SUBMIT ========================

    public void clickFileSelectButton() {
        wait.until(ExpectedConditions.elementToBeClickable(fileSelectButton)).click();
        WaitUtils.sleep(2000);
    }

    public void clickEMCSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(emcSubmitButton)).click();
        WaitUtils.sleep(3000);
    }

    public void clickPaperSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(paperSubmitButton)).click();
        WaitUtils.sleep(3000);
    }

    // ======================== EDOCS ========================

    public void clickEdocButton() {
        wait.until(ExpectedConditions.elementToBeClickable(edocButton)).click();
        WaitUtils.sleep(3000);
    }

    public String getEdocViewHeading() {
        WaitUtils.waitForVisibility(driver, edocViewHeading, 20);
        return driver.findElement(edocViewHeading).getText().trim();
    }

    public void clickEdocCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(edocCloseButton)).click();
        WaitUtils.sleep(2000);
    }
}
