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
            By.xpath("/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[1]");

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

    public String getTableInvoiceNumber() {
        String baseXPath =
                "/html/body/ng-component/div/div/div[2]/billing-history-app/div/div/div/div/div/div[2]/div/div/div/div/billing-history-injury-list/p-table/div/div/table/tbody/tr[2]/td/p-table/div/div/table/tbody";

        List<WebElement> rows = driver.findElements(By.xpath(baseXPath + "/tr"));
        for (int i = 1; i <= rows.size(); i++) {
            for (int j = 1; j <= 8; j++) {
                By cell = By.xpath(baseXPath + "/tr[" + i + "]/td[" + j + "]");
                if (!driver.findElements(cell).isEmpty()) {
                    String text = driver.findElement(cell).getText().trim();
                    if (!text.isEmpty() && text.matches(".*\\d{5,}-.*")) {
                        return text;
                    }
                }
            }
        }
        return "";
    }
}
