package com.cyclonercm.pages;




import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleBillingPage {

    //region ======================== DRIVER & CONSTRUCTOR ========================
    private WebDriver driver;
    private WebDriverWait wait;

    public SingleBillingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //--------- Navigation Locators & Methods ---------
    private final By billingMenu = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/button[4]");
    private final By billingDropdown = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div");
    private final By singleBillingOption = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[2]/p-menu[1]/div/ul/li[2]");
    private final By singleBillingLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-toolbar/div/div[1]/span/b");
    // HCFA Toggle Button (enable before EMC submission)
    private final By hcfaToggleButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/div/div[2]/p-inputswitch");


    //-------- Navigation Methods ---------
    public Boolean isBillingMenuDisplayed() {
        return driver.findElement(billingMenu).isDisplayed();
    }

    public void clickBillingMenu() {
        driver.findElement(billingMenu).click();
    }

    public void clickSingleBillingOption() {
        driver.findElement(singleBillingOption).click();
    }

    public String getSingleBillingLabel() {
        return driver.findElement(singleBillingLabel).getText().trim();
    }

    //========== APPLICANT SEARCH LOCATORS ==========
    private final By applicantSearchInput = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[1]/div/span/p-autocomplete/span/input");
    private final By dateOfServiceDropdown = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[2]/p-dropdown/div/div[2]");
    private final By applicantlist = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[1]/div/span/p-autocomplete/span/div/ul");
    private final By firstapplicant = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[1]/div/div[1]/div/span/p-autocomplete/span/div");
    private final By dosListItems = By.xpath("/html/body/div[2]/div[2]/ul/p-dropdownitem/li");

    //========== INVOICE TABLE LOCATORS ==========
    private final By invoiceTableBody = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]");
    private final By firstInvoiceApplicantLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[1]/td/b/span[1]");
    private final By firstInvoiceDosLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[2]/td/b");

    // Mandatory Field Locators for first invoice
    private final By firstInvoiceNumber = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[2]");
    private final By firstInvoiceCaseNumber = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[1]/td/b/span[4]/span");
    private final By firstInvoiceClaimNumber = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[3]");
    private final By firstInvoiceAmount = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[4]");
    private final By firstInvoiceStatus = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[5]/div/div[1]/p-badge");

    // Checkbox and Modal Locators
    private final By checkbox = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[1]/div/p-checkbox");
    private final By informationMessageModal = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/p-dialog/div/div/div[2]/div");

    // EAMS Details Popup Locators (SMOKE_SB_006)
    private final By eamsDetailsPopup = By.xpath("/html/body/div[2]/div/div[2]/cyclone-eams-details-view/div");
    private final By eamsDetailsPopupTitle = By.xpath("/html/body/div[2]/div/div[2]/cyclone-eams-details-view/div/div/p-toolbar/div/div[1]/span");

    // Report View Locators (SMOKE_SB_008)
    private final By reportViewButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[6]");
    private final By reportViewModal = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/p-dialog/div/div/div[2]/cyclone-reporting-board/div/form/div/div[2]");
    private final By reportViewLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/p-dialog/div/div/div[2]/cyclone-reporting-board/div/form/div/div[2]/div[1]/p-toolbar/div/div[1]/span");
    private final By ClaimForm = By.xpath("/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/span[2]");

    // Filter Radio Buttons (SMOKE_SB_009 - SMOKE_SB_012)
    private final By emcFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[3]/div/div/label[2]/p-radiobutton");
    private final By emailFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[3]/div/div/label[3]/p-radiobutton/div");
    private final By faxFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[3]/div/div/label[4]/p-radiobutton/div");
    private final By paperFilterRadioButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[3]/div/div/label[5]/p-radiobutton/div/div[2]");

    // Submission Buttons (SMOKE_SB_013 - SMOKE_SB_022)
    private final By emcSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[1]");
    private final By epSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[2]");
    private final By mailSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[3]");
    private final By faxSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[4]");
    private final By paperSubmissionButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[5]");

    //========== APPLICANT SEARCH METHODS ==========

    /**
     * Enter applicant name in the search field
     * @param applicantName the name to search for
     */
    public void searchApplicant(String applicantName) {
        WebElement searchInput = driver.findElement(applicantSearchInput);
        searchInput.clear();
        searchInput.sendKeys(applicantName);
        WaitUtils.sleep(2000); // Wait for autocomplete suggestions
    }

    public Boolean viewApplicantList(){
        return driver.findElement(applicantlist).isDisplayed();
    }

    /**
     * Get the applicant name from the first item in autocomplete dropdown
     * @return applicant name text
     */
    public String getApplicantNameFromDropdown() {
        WaitUtils.waitForVisibility(driver, firstapplicant, 10);
        return driver.findElement(firstapplicant).getText().trim();
    }

    public void clickApplicant(){
        driver.findElement(firstapplicant).click();
    }

    /**
     * Click on the applicant from autocomplete dropdown
     * This assumes the first suggestion matches the entered name
     */
    public void selectApplicantFromAutocomplete() {
        // Wait for autocomplete panel to appear
        WaitUtils.sleep(2000);

        // Click on the first suggestion in autocomplete
        By autocompletePanel = By.xpath("//p-autocomplete//ul[@role='listbox']//li[1]");
        driver.findElement(autocompletePanel).click();

        // Wait longer for selection to process and DOS dropdown to become available
        WaitUtils.sleep(5000);
    }

    /**
     * Click the Date of Service dropdown
     */
    public void clickDateOfServiceDropdown() {
        // Wait for DOS dropdown to be visible and clickable
        WaitUtils.waitForVisibility(driver, dateOfServiceDropdown, 30);
        WaitUtils.sleep(2000);

        driver.findElement(dateOfServiceDropdown).click();
        WaitUtils.sleep(3000); // Wait for dropdown list to load
    }

    /**
     * Select a specific DOS from the dropdown list
     * @param dosIndex the index of the DOS to select (0-based)
     */
    public void selectDosFromList(int dosIndex) {
        List<WebElement> dosList = driver.findElements(dosListItems);

        if (dosIndex >= 0 && dosIndex < dosList.size()) {
            dosList.get(dosIndex).click();
            WaitUtils.sleep(3000); // Wait for invoice to be added
        } else {
            throw new IndexOutOfBoundsException("DOS index " + dosIndex + " is out of range. Available items: " + dosList.size());
        }
    }



    /**
     * Select the first available DOS from the dropdown
     */
    public void selectFirstDos() {
        selectDosFromList(0);
    }

    public void selectDropDown(){
        driver.findElement(billingDropdown).click();
    }

    /**
     * Get the number of available DOS options
     * @return count of DOS items
     */
    public int getDosListCount() {
        List<WebElement> dosList = driver.findElements(dosListItems);
        return dosList.size();
    }

    /**
     * Get all available DOS values from the dropdown
     * @return list of DOS text values
     */
    public List<String> getAllDosValues() {
        List<WebElement> dosList = driver.findElements(dosListItems);
        return dosList.stream()
                .map(WebElement::getText)
                .toList();
    }

    //========== INVOICE VERIFICATION METHODS ==========

    /**
     * Get the applicant name from the first invoice in the table
     * @return applicant name text
     */
    public String getFirstInvoiceApplicantName() {
        WaitUtils.waitForVisibility(driver, firstInvoiceApplicantLabel, 10);
        return driver.findElement(firstInvoiceApplicantLabel).getText().trim();
    }

    /**
     * Get the DOS label from the first invoice in the table
     * Format: "DOS : MM/DD/YY"
     * @return DOS label text
     */
    public String getFirstInvoiceDos() {
        WaitUtils.waitForVisibility(driver, firstInvoiceDosLabel, 10);
        return driver.findElement(firstInvoiceDosLabel).getText().trim();
    }

    /**
     * Get the Invoice Number from the first invoice in the table
     * @return invoice number text
     */
    public String getFirstInvoiceNumber() {
        WaitUtils.waitForVisibility(driver, firstInvoiceNumber, 10);
        return driver.findElement(firstInvoiceNumber).getText().trim();
    }

    /**
     * Click the first invoice number to open Edit Invoice screen
     */
    public void clickFirstInvoiceNumber() {
        try {
            WaitUtils.waitForVisibility(driver, firstInvoiceNumber, 10);
            WaitUtils.sleep(2000);
            driver.findElement(firstInvoiceNumber).click();
            WaitUtils.sleep(5000);
            System.out.println("✓ First invoice number clicked - Edit Invoice screen should open");
        } catch (Exception e) {
            System.err.println("Error clicking first invoice number: " + e.getMessage());
        }
    }

    /**
     * Get the Case Number from the first invoice in the table
     * @return case number text
     */
    public String getFirstInvoiceCaseNumber() {
        WaitUtils.waitForVisibility(driver, firstInvoiceCaseNumber, 10);
        return driver.findElement(firstInvoiceCaseNumber).getText().trim();
    }

    /**
     * Get the Claim Number from the first invoice in the table
     * @return claim number text
     */
    public String getFirstInvoiceClaimNumber() {
        WaitUtils.waitForVisibility(driver, firstInvoiceClaimNumber, 10);
        return driver.findElement(firstInvoiceClaimNumber).getText().trim();
    }

    /**
     * Get the Amount from the first invoice in the table
     * @return amount text
     */
    public String getFirstInvoiceAmount() {
        WaitUtils.waitForVisibility(driver, firstInvoiceAmount, 10);
        return driver.findElement(firstInvoiceAmount).getText().trim();
    }

    /**
     * Get the Status from the first invoice in the table
     * Status can be: "EAMS Verified", "EAMS Not Verified", or "Not Verified"
     * @return status text
     */
    public String getFirstInvoiceStatus() {
        WaitUtils.waitForVisibility(driver, firstInvoiceStatus, 10);
        return driver.findElement(firstInvoiceStatus).getText().trim();
    }

    /**
     * Check if the invoice table has any invoices
     * @return true if invoices exist, false otherwise
     */
    public boolean hasInvoices() {
        try {
            WebElement tableBody = driver.findElement(invoiceTableBody);
            List<WebElement> rows = tableBody.findElements(By.xpath(".//tr"));
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the count of invoices in the table
     * @return number of invoice rows
     */
    public int getInvoiceCount() {
        try {
            WebElement tableBody = driver.findElement(invoiceTableBody);
            List<WebElement> rows = tableBody.findElements(By.xpath(".//tr[contains(@class, 'p-element p-selectable-row ng-star-inserted')]"));

            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Complete workflow: Search applicant, select DOS, and add to Single Billing
     * @param applicantName the applicant to search for
     * @param dosIndex the index of the DOS to select (0 for first)
     */
    public void addInvoiceToSingleBilling(String applicantName, int dosIndex) {
        searchApplicant(applicantName);
        selectApplicantFromAutocomplete();
        clickDateOfServiceDropdown();
        selectDosFromList(dosIndex);
    }

    /**
     * Complete workflow using the first available DOS
     * @param applicantName the applicant to search for
     */
    public void addInvoiceToSingleBillingFirstDos(String applicantName) {
        searchApplicant(applicantName);
        selectApplicantFromAutocomplete();
        clickDateOfServiceDropdown();
        selectFirstDos();
    }

    //========== UTILITY METHODS ==========

    /**
     * Format date from "MM/DD/YYYY" to "MM/DD/YY" for comparison
     * @param fullDate date in format MM/DD/YYYY
     * @return formatted date in MM/DD/YY
     */
    public String removeYearPrefix(String fullDate) {
        if (fullDate == null || fullDate.isEmpty()) {
            return fullDate;
        }

        String[] parts = fullDate.split("/");
        if (parts.length == 3) {
            String month = parts[0];
            String day = parts[1];
            String year = parts[2];

            // Convert year from YYYY to YY
            String shortYear = year.length() == 4 ? year.substring(2) : year;

            return month + "/" + day + "/" + shortYear;
        }

        return fullDate;
    }

    /**
     * Wait for invoice table to load
     */
    public void waitForInvoiceTableLoad() {
        WaitUtils.waitForVisibility(driver, invoiceTableBody, 30);
        WaitUtils.sleep(2000);
    }

    //========== CHECKBOX AND MODAL METHODS (SMOKE_SB_003) ==========

    /**
     * Click the checkbox on the first invoice
     * Used to test EMC scrubbing flag (red background) validation
     */
    public void clickCheckBox() {
        WaitUtils.sleep(2000);
        driver.findElement(checkbox).click();
        WaitUtils.sleep(2000);
    }

    /**
     * Check if the Missing Information modal is displayed
     * This modal appears when trying to tick an invoice with red EMC flag (missing mandatory fields)
     * @return true if modal is displayed, false otherwise
     */
    public boolean isMissingInfoModalDisplayed() {
        try {
            WaitUtils.sleep(2000);
            return driver.findElement(informationMessageModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //========== EAMS VERIFICATION METHODS (SMOKE_SB_004) ==========

    /**
     * Find a verified invoice (EAMS Verified or EAMS Not Verified) with its comment
     * Used for SMOKE_SB_004 - EAMS verification test
     * @return Map containing "status" and "comment", or null if no verified invoice found
     */
    public Map<String, String> findVerifiedInvoiceWithComment() {
        try {
            WaitUtils.sleep(3000); // Wait for table to load

            // Find all table rows in tbody
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            System.out.println("📊 Found " + rows.size() + " rows in the table");

            // Iterate through each row
            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);

                    // Try to find status badge in column 5 (td[5])
                    try {
                        WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                        String statusText = statusBadge.getText().trim();

                        System.out.println("   Row " + (i + 1) + " Status: " + statusText);

                        // Check if this invoice is EAMS Verified or EAMS Not Verified
                        if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified") || statusText.equals("No Carrier in EAMS") || statusText.equals("No Carrier; Employer Bill") || statusText.equals("Nothing Match") || statusText.equals("Multiple Carrier")) {
                            System.out.println("✓ Found EAMS processed invoice: " + statusText);

                            // Try to get the comment from the same cell (td[5])
                            try {
                                // The comment is in td[5]/div/div[2] according to structure
                                // Structure: td[5] -> div -> div[1] (has p-badge) and div[2] (has comment)
                                WebElement commentDiv = row.findElement(By.xpath(".//td[5]/div/div[2]"));
                                String commentText = commentDiv.getText().trim();

                                System.out.println("✓ Found Comment: " + commentText);

                                // Create result map
                                Map<String, String> result = new HashMap<>();
                                result.put("status", statusText);
                                result.put("comment", commentText);
                                return result;

                            } catch (Exception commentError) {
                                System.err.println("❌ Could not find comment for this invoice: " + commentError.getMessage());
                                // Continue to next invoice
                            }
                        }

                    } catch (Exception statusError) {
                        // No status badge in this row, might be a grouping row
                        // Skip and continue
                    }

                } catch (Exception e) {
                    // Skip this row and continue
                    System.err.println("⚠ Error processing row " + (i + 1) + ": " + e.getMessage());
                }
            }

            System.err.println("❌ No EAMS Verified or EAMS Not Verified invoices found in the table");
            return null;

        } catch (Exception e) {
            System.err.println("❌ Error finding verified invoice: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Validate if comment is one of the 8 valid EAMS comments
     * @param comment The comment text to validate
     * @return true if comment matches one of the 8 valid comments
     */
    public boolean isValidEamsComment(String comment) {
        String[] validComments = {
                "Carrier and Address Match; Billed to Carrier on EAMS",
                "Name Match, Address Mismatch; Replace Invoice Address with Address at EAMS; Billed to Carrier on EAMS ",
                "Address Match, Name Mismatch; Replace Invoice Name with Name at EAMS; Billed to Carrier on EAMS",
                "No Carrier in EAMS",
                "No Carrier on Invoice; Billed to Carrier on EAMS",
                "No Carrier on Invoice or EAMS; Billed to Single Employer",
                "No Carrier on Invoice or EAMS; Billed to Multiple Employers",
                "Invoice Data and EAMS Data Mismatch; (Billed to EAMS Carrier)",
                "Invoice Data and EAMS Data Mismatch; Created Multiple Invoices (Billed to EAMS Carrier)"
        };

        for (String validComment : validComments) {
            if (comment.equals(validComment) || comment.contains(validComment)) {
                System.out.println("✓ Comment is valid: " + comment);
                return true;
            }
        }

        System.err.println("❌ Comment is NOT valid: " + comment);
        return false;
    }

    /**
     * Validate ALL invoices in the table for EAMS status and comments
     * Used for SMOKE_SB_005 - Validate multiple invoices
     * @return Map containing validation results and counts
     */
    public Map<String, Object> validateAllInvoicesEamsComments() {
        Map<String, Object> result = new HashMap<>();

        int totalInvoices = 0;
        int eamsVerifiedCount = 0;
        int eamsNotVerifiedCount = 0;
        int notVerifiedCount = 0;

        try {
            WaitUtils.sleep(3000); // Wait for table to load

            // Find all table rows in tbody
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            System.out.println("📊 Validating " + rows.size() + " rows in the table");
            System.out.println("═══════════════════════════════════════════════════════════");

            // Iterate through each row
            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);

                    // Try to find status badge in column 5 (td[5])
                    try {
                        WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                        String statusText = statusBadge.getText().trim();

                        totalInvoices++;
                        System.out.println("\n📋 Row " + (i + 1) + " - Status: " + statusText);

                        // Case 1: EAMS Verified or EAMS Not Verified - MUST have valid comment
                        if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {

                            if (statusText.equals("EAMS Verified")) {
                                eamsVerifiedCount++;
                            } else {
                                eamsNotVerifiedCount++;
                            }

                            // Get the comment
                            String commentText = "";
                            try {
                                WebElement commentDiv = row.findElement(By.xpath(".//td[5]/div/div[2]"));
                                commentText = commentDiv.getText().trim();
                                System.out.println("   💬 Comment: " + commentText);
                            } catch (Exception e) {
                                String errorMsg = "❌ VALIDATION FAILED - Row " + (i + 1) +
                                        ": Status is '" + statusText + "' but comment element not found";
                                System.err.println(errorMsg);
                                result.put("error", errorMsg);
                                result.put("failedRow", i + 1);
                                result.put("success", false);
                                return result;
                            }

                            // Validate comment is not empty
                            if (commentText == null || commentText.isEmpty()) {
                                String errorMsg = "❌ VALIDATION FAILED - Row " + (i + 1) +
                                        ": Status is '" + statusText + "' but comment is EMPTY. " +
                                        "Business Rule: Invoices with EAMS status MUST have scrubbing comment.";
                                System.err.println(errorMsg);
                                result.put("error", errorMsg);
                                result.put("failedRow", i + 1);
                                result.put("success", false);
                                return result;
                            }

                            // Validate comment matches one of 8 valid comments
                            if (!isValidEamsComment(commentText)) {
                                String errorMsg = "❌ VALIDATION FAILED - Row " + (i + 1) +
                                        ": Comment does NOT match any of the 8 valid EAMS comments.\n" +
                                        "   Status: " + statusText + "\n" +
                                        "   Comment: " + commentText;
                                System.err.println(errorMsg);
                                result.put("error", errorMsg);
                                result.put("failedRow", i + 1);
                                result.put("success", false);
                                return result;
                            }

                            System.out.println("   ✅ Valid");

                            // Case 2: Not Verified - Should NOT have comment
                        } else if (statusText.equals("Not Verified")) {
                            notVerifiedCount++;

                            // Check if comment exists (it shouldn't)
                            try {
                                WebElement commentDiv = row.findElement(By.xpath(".//td[5]/div/div[2]"));
                                String commentText = commentDiv.getText().trim();

                                if (commentText != null && !commentText.isEmpty()) {
                                    String errorMsg = "❌ VALIDATION FAILED - Row " + (i + 1) +
                                            ": Status is 'Not Verified' but comment EXISTS: '" + commentText + "'. " +
                                            "Business Rule: 'Not Verified' invoices should NOT have scrubbing comments.";
                                    System.err.println(errorMsg);
                                    result.put("error", errorMsg);
                                    result.put("failedRow", i + 1);
                                    result.put("success", false);
                                    return result;
                                }
                            } catch (Exception e) {
                                // No comment div found - this is correct for Not Verified
                                System.out.println("   ✅ No comment (correct)");
                            }
                        }

                    } catch (Exception statusError) {
                        // No status badge in this row, might be a grouping row - skip
                    }

                } catch (Exception e) {
                    // Skip this row and continue
                    System.err.println("⚠ Error processing row " + (i + 1) + ": " + e.getMessage());
                }
            }

            System.out.println("\n═══════════════════════════════════════════════════════════");

            // Store counts
            result.put("totalInvoices", totalInvoices);
            result.put("eamsVerifiedCount", eamsVerifiedCount);
            result.put("eamsNotVerifiedCount", eamsNotVerifiedCount);
            result.put("notVerifiedCount", notVerifiedCount);
            result.put("success", true);

            return result;

        } catch (Exception e) {
            System.err.println("❌ Error validating invoices: " + e.getMessage());
            e.printStackTrace();
            result.put("error", "Unexpected error: " + e.getMessage());
            result.put("success", false);
            return result;
        }
    }

    //========== EAMS POPUP METHODS (SMOKE_SB_006) ==========

    /**
     * Click status button by text
     */
    private void clickStatusButtonByText(String statusText) {
        try {
            WaitUtils.sleep(2000);
            By statusButton = By.xpath("//p-badge/span[text()='" + statusText + "']");
            driver.findElement(statusButton).click();
            WaitUtils.sleep(2000);
        } catch (Exception e) {
            System.err.println("❌ Failed to click status button: " + statusText);
            throw e;
        }
    }

    public void clickNotVerifiedButton() {
        clickStatusButtonByText("Not Verified");
    }

    public void clickEamsVerifiedButton() {
        clickStatusButtonByText("EAMS Verified");
    }

    public void clickEamsNotVerifiedButton() {
        clickStatusButtonByText("EAMS Not Verified");
    }

    public boolean isEamsDetailsPopupDisplayed() {
        try {
            WaitUtils.sleep(3000);
            return driver.findElements(eamsDetailsPopup).size() > 0 &&
                    driver.findElement(eamsDetailsPopup).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEamsDetailsPopupTitle() {
        try {
            WaitUtils.sleep(2000);
            return driver.findElement(eamsDetailsPopupTitle).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Find the first available status button in the invoice table
     * Checks for: EAMS Verified, EAMS Not Verified, or Not Verified
     * @return Map containing "status" and "found" (boolean)
     */
    public Map<String, Object> findFirstAvailableStatus() {
        Map<String, Object> result = new HashMap<>();
        result.put("found", false);
        result.put("status", "");

        try {
            WaitUtils.sleep(3000);
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            System.out.println("📊 Searching for available status buttons in " + rows.size() + " rows");

            // Priority order: EAMS Verified > EAMS Not Verified > Not Verified
            String[] statusPriority = {"EAMS Verified", "EAMS Not Verified", "Not Verified"};

            for (String targetStatus : statusPriority) {
                for (int i = 0; i < rows.size(); i++) {
                    try {
                        WebElement row = rows.get(i);
                        WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                        String statusText = statusBadge.getText().trim();

                        if (statusText.equals(targetStatus)) {
                            System.out.println("✓ Found '" + targetStatus + "' status button in row " + (i + 1));
                            result.put("found", true);
                            result.put("status", targetStatus);
                            return result;
                        }
                    } catch (Exception e) {
                        // Skip rows without status badge
                        continue;
                    }
                }
            }

            System.err.println("❌ No status buttons found (EAMS Verified, EAMS Not Verified, or Not Verified)");
            return result;

        } catch (Exception e) {
            System.err.println("❌ Error finding status buttons: " + e.getMessage());
            return result;
        }
    }

    /**
     * Click status button by status text
     * @param statusText The status button to click: "EAMS Verified", "EAMS Not Verified", or "Not Verified"
     * @return true if clicked successfully, false otherwise
     */
    public boolean clickStatusButton(String statusText) {
        try {
            WaitUtils.sleep(2000);

            // Search through table rows to find and click the status button
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);
                    WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                    String foundStatus = statusBadge.getText().trim();

                    if (foundStatus.equals(statusText)) {
                        // Found the status, now click it
                        statusBadge.click();
                        WaitUtils.sleep(2000);
                        System.out.println("✓ Clicked '" + statusText + "' status button in row " + (i + 1));
                        return true;
                    }
                } catch (Exception e) {
                    // Skip rows without status badge
                    continue;
                }
            }

            System.err.println("❌ Status button '" + statusText + "' not found in table");
            return false;

        } catch (Exception e) {
            System.err.println("❌ Failed to click status button '" + statusText + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //========== REPORT VIEW METHODS (SMOKE_SB_008) ==========

    public boolean clickViewButtonForEamsVerifiedInvoice() {
        try {
            WaitUtils.sleep(3000);
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);
                    WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                    String statusText = statusBadge.getText().trim();

                    if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verify")) {
                        WebElement invoice = row.findElement(By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[3]/p-table/div/div/table/tbody/tr[3]/td[1]/div/p-checkbox/p-checkbox/div"));
                        invoice.click();
                        WaitUtils.sleep(3000);
                        //WebElement hcfa = row.findElement(By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/div/div[2]/p-inputswitch/div"));
                        //hcfa.click();
                        //WaitUtils.sleep(3000);
                        WebElement viewButton = row.findElement(By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[6]"));
                        viewButton.click();
                        WaitUtils.sleep(3000);
                        return true;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ Error clicking view button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get claim number from a specific row and click view button for EAMS Verified invoice
     * @return claim number of the selected invoice, or null if not found
     */
    public String clickViewButtonAndGetClaimNumber() {
        try {
            WaitUtils.sleep(3000);
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);
                    WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                    String statusText = statusBadge.getText().trim();

                    if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verify")) {
                        // Get claim number from this row before clicking
                        WebElement claimNumberElement = row.findElement(By.xpath(".//td[3]/span"));
                        String claimNumber = claimNumberElement.getText().trim();

                        WebElement invoice = row.findElement(By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[3]/td[1]/div/p-checkbox/div"));
                        invoice.click();
                        WaitUtils.sleep(3000);

                        WebElement viewButton = row.findElement(By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[1]/p-toolbar/div/div[2]/cyclone-billing-print-option/div/div/div/div/div/div/button[6]"));
                        viewButton.click();
                        WaitUtils.sleep(3000);

                        return claimNumber;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("❌ Error clicking view button: " + e.getMessage());
            return null;
        }
    }


    public void enableHcfaToggle() {
        try {
            WebElement toggle = driver.findElement(hcfaToggleButton);
            WaitUtils.sleep(1000);

            // Check if toggle is already enabled
            String ariaChecked = toggle.getAttribute("aria-checked");
            String className = toggle.getAttribute("class");

            boolean isEnabled = "true".equalsIgnoreCase(ariaChecked) ||
                    (className != null && className.contains("p-inputswitch-checked"));

            if (!isEnabled) {
                toggle.click();
                System.out.println("✓ HCFA Toggle enabled");
                WaitUtils.sleep(1000);
            } else {
                System.out.println("✓ HCFA Toggle already enabled");
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to enable HCFA Toggle: " + e.getMessage());
            throw e;
        }
    }

    public boolean isReportViewDisplayed() {
        try {
            WaitUtils.sleep(5000);
            return driver.findElement(reportViewModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDisplayHCFAForm() {
        try {
            String actualForm = driver.findElement(ClaimForm).getText().trim();
            return actualForm.equals("CLAIM FORM");
        } catch (Exception e) {
            return false;
        }
    }

    //========== FILTER METHODS (SMOKE_SB_009 - SMOKE_SB_012) ==========

    public void clickEmcFilter() {
        WaitUtils.sleep(2000);
        driver.findElement(emcFilterRadioButton).click();
        WaitUtils.sleep(3000);
    }

    public void clickEmailFilter() {
        WaitUtils.sleep(2000);
        driver.findElement(emailFilterRadioButton).click();
        WaitUtils.sleep(3000);
    }

    public void clickFaxFilter() {
        WaitUtils.sleep(2000);
        driver.findElement(faxFilterRadioButton).click();
        WaitUtils.sleep(3000);
    }

    public void clickPaperFilter() {
        WaitUtils.sleep(2000);
        driver.findElement(paperFilterRadioButton).click();
        WaitUtils.sleep(3000);
    }

    //========== SUBMISSION METHODS (SMOKE_SB_013 - SMOKE_SB_022) ==========

    public void clickEmcSubmissionButton() {
        WaitUtils.sleep(2000);
        driver.findElement(emcSubmissionButton).click();
        WaitUtils.sleep(5000);
    }

    public void clickEpSubmissionButton() {
        WaitUtils.sleep(2000);
        driver.findElement(epSubmissionButton).click();
        WaitUtils.sleep(5000);
    }

    public void clickMailSubmissionButton() {
        WaitUtils.sleep(2000);
        driver.findElement(mailSubmissionButton).click();
        WaitUtils.sleep(5000);
    }

    public void clickFaxSubmissionButton() {
        WaitUtils.sleep(2000);
        driver.findElement(faxSubmissionButton).click();
        WaitUtils.sleep(5000);
    }

    public void clickPaperSubmissionButton() {
        WaitUtils.sleep(2000);
        driver.findElement(paperSubmissionButton).click();
        WaitUtils.sleep(5000);
    }

    public boolean isReportViewDisplayedAfterSubmission() {
        try {
            WaitUtils.sleep(5000);
            return driver.findElement(reportViewLabel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========== SMOKE_SB_023: Remove Single Invoice ==========

    // Remove button for a specific invoice (row 6 in this case)
    private final By removeButtonRow6 = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[16]/td[8]/button");

    // Invoice number for row 6
    private final By invoiceNumberRow6 = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[2]/p-table/div/div/table/tbody/tr[16]/td[2]/span/u");

    // Confirmation popup after clicking remove
    private final By removeConfirmationPopup = By.xpath("/html/body/div[2]/div");

    // Yes button in remove confirmation popup
    private final By removeYesButton = By.xpath("/html/body/div[2]/div/div[3]/button[2]");

    /**
     * Get the invoice number from row 6
     * @return Invoice number as String
     */
    public String getInvoiceNumberRow6() {
        try {
            WaitUtils.waitForVisibility(driver, invoiceNumberRow6, 10);
            String invoiceNumber = driver.findElement(invoiceNumberRow6).getText().trim();
            System.out.println("Invoice number from row 6: " + invoiceNumber);
            return invoiceNumber;
        } catch (Exception e) {
            System.err.println("Error getting invoice number from row 6: " + e.getMessage());
            return "";
        }
    }

    /**
     * Click the remove button for row 6
     */
    public void clickRemoveButtonRow6() {
        try {
            WaitUtils.waitForVisibility(driver, removeButtonRow6, 10);
            WaitUtils.sleep(2000);
            driver.findElement(removeButtonRow6).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Remove button clicked for row 6");
        } catch (Exception e) {
            System.err.println("Error clicking remove button: " + e.getMessage());
        }
    }

    /**
     * Get the first visible invoice number from the table
     * This method is flexible and doesn't depend on specific row indices
     * @return Invoice number of the first visible invoice, or empty string if none found
     */
    public String getFirstVisibleInvoiceNumber() {
        try {
            WaitUtils.sleep(2000);
            // Find all invoice rows in the table (excluding grouping rows)
            By allInvoiceRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allInvoiceRows);

            System.out.println("🔍 Searching for first visible invoice in " + rows.size() + " rows");

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);

                    // Try to get invoice number from column 2 (td[2])
                    WebElement invoiceElement = row.findElement(By.xpath(".//td[2]//span"));
                    String invoiceNumber = invoiceElement.getText().trim();

                    if (!invoiceNumber.isEmpty()) {
                        System.out.println("✓ Found first invoice: " + invoiceNumber + " (row " + (i + 1) + ")");
                        return invoiceNumber;
                    }
                } catch (Exception e) {
                    // Skip this row and continue
                }
            }

            System.err.println("❌ No invoices found in table");
            return "";
        } catch (Exception e) {
            System.err.println("Error getting first visible invoice number: " + e.getMessage());
            return "";
        }
    }

    /**
     * Remove an invoice by its invoice number (flexible method - not hardcoded to specific row)
     * This method finds the invoice in the table and clicks its remove button
     * @param invoiceNumber The invoice number to remove
     * @return true if remove button was clicked successfully, false otherwise
     */
    public boolean removeInvoiceByNumber(String invoiceNumber) {
        try {
            WaitUtils.sleep(2000);
            System.out.println("🔍 Searching for invoice " + invoiceNumber + " to remove...");

            // Find all invoice rows in the table
            By allInvoiceRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allInvoiceRows);

            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);

                    // Get invoice number from column 2 (td[2])
                    WebElement invoiceElement = row.findElement(By.xpath(".//td[2]//span"));
                    String currentInvoiceNumber = invoiceElement.getText().trim();

                    if (currentInvoiceNumber.equals(invoiceNumber)) {
                        System.out.println("✓ Found invoice " + invoiceNumber + " at row " + (i + 1));

                        // Click the remove button in column 8 (td[8])
                        WebElement removeButton = row.findElement(By.xpath(".//td[8]/button"));
                        WaitUtils.sleep(1000);
                        removeButton.click();
                        WaitUtils.sleep(2000);

                        System.out.println("✓ Remove button clicked for invoice " + invoiceNumber);
                        return true;
                    }
                } catch (Exception e) {
                    // Skip this row and continue
                }
            }

            System.err.println("❌ Invoice " + invoiceNumber + " not found in table");
            return false;
        } catch (Exception e) {
            System.err.println("Error removing invoice by number: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if remove confirmation popup is displayed
     * @return true if popup is displayed, false otherwise
     */
    public boolean isRemoveConfirmationPopupDisplayed() {
        try {
            WaitUtils.waitForVisibility(driver, removeConfirmationPopup, 10);
            return driver.findElement(removeConfirmationPopup).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click Yes button in remove confirmation popup
     */
    public void clickRemoveYesButton() {
        try {
            WaitUtils.waitForVisibility(driver, removeYesButton, 10);
            WaitUtils.sleep(1000);
            driver.findElement(removeYesButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Yes button clicked in remove confirmation popup");
        } catch (Exception e) {
            System.err.println("Error clicking Yes button: " + e.getMessage());
        }
    }

    /**
     * Check if a specific invoice number is still displayed in the table
     * @param invoiceNumber Invoice number to search for
     * @return true if invoice is still displayed, false if removed
     */
    public boolean isInvoiceDisplayed(String invoiceNumber) {
        try {
            WaitUtils.sleep(2000);
            // Search through all invoice rows for the invoice number
            By invoiceXPath = By.xpath("//span/u[contains(text(), '" + invoiceNumber + "')]");
            return driver.findElements(invoiceXPath).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ========== SMOKE_SB_024: Delete All Invoices ==========

    // Delete All button
    private final By deleteAllButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/div/div/div[2]/div/div/billing-list/div/div[1]/p-toolbar/div/div[3]/button[1]");

    // Delete All confirmation popup
    private final By deleteAllConfirmationPopup = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-confirmdialog[1]/div/div");

    // Yes button in Delete All confirmation popup
    private final By deleteAllYesButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-confirmdialog[1]/div/div/div[3]/button[2]");

    /**
     * Click the Delete All button
     */
    public void clickDeleteAllButton() {
        try {
            WaitUtils.waitForVisibility(driver, deleteAllButton, 10);
            WaitUtils.sleep(2000);
            driver.findElement(deleteAllButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Delete All button clicked");
        } catch (Exception e) {
            System.err.println("Error clicking Delete All button: " + e.getMessage());
        }
    }

    /**
     * Check if Delete All confirmation popup is displayed
     * @return true if popup is displayed, false otherwise
     */
    public boolean isDeleteAllConfirmationPopupDisplayed() {
        try {
            WaitUtils.waitForVisibility(driver, deleteAllConfirmationPopup, 10);
            return driver.findElement(deleteAllConfirmationPopup).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click Yes button in Delete All confirmation popup
     */
    public void clickDeleteAllYesButton() {
        try {
            WaitUtils.waitForVisibility(driver, deleteAllYesButton, 10);
            WaitUtils.sleep(1000);
            driver.findElement(deleteAllYesButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Yes button clicked in Delete All confirmation popup");
        } catch (Exception e) {
            System.err.println("Error clicking Yes button: " + e.getMessage());
        }
    }

    public List<String> selectMultipleEamsVerifiedInvoiceCheckboxes(int maxCount) {
        List<String> selectedInvoices = new ArrayList<>();

        try {
            WaitUtils.sleep(3000); // Wait for table to load

            // Find all table rows in tbody
            By allTableRows = By.xpath("//p-table//tbody/tr[contains(@class, 'ng-star-inserted')]");
            List<WebElement> rows = driver.findElements(allTableRows);

            System.out.println("🔍 Searching for multiple EAMS Verified/Not Verified invoices in " + rows.size() + " rows");
            System.out.println("Target: Select minimum 2, maximum " + maxCount + " invoices");

            int selectedCount = 0;

            // Iterate through each row
            for (int i = 0; i < rows.size() && selectedCount < maxCount; i++) {
                try {
                    WebElement row = rows.get(i);

                    // Try to find status badge in column 5 (td[5])
                    try {
                        WebElement statusBadge = row.findElement(By.xpath(".//td[5]//p-badge/span"));
                        String statusText = statusBadge.getText().trim();

                        // Check if status is "EAMS Verified" or "EAMS Not Verified"
                        if (statusText.equals("EAMS Verified") || statusText.equals("EAMS Not Verified")) {
                            System.out.println("   Row " + (i + 1) + " - Status: '" + statusText + "'");

                            // Get invoice number from column 2 (td[2])
                            String invoiceNumber = "";
                            try {
                                WebElement invoiceElement = row.findElement(By.xpath(".//td[2]//span"));
                                invoiceNumber = invoiceElement.getText().trim();
                                System.out.println("   ✓ Invoice Number: " + invoiceNumber);
                            } catch (Exception e) {
                                System.err.println("   ⚠ Could not get invoice number");
                                continue;
                            }

                            // Click checkbox in column 1 (td[1])
                            try {
                                WebElement checkbox = row.findElement(By.xpath(".//td[1]//div"));
                                WaitUtils.sleep(500);
                                checkbox.click();
                                selectedCount++;
                                selectedInvoices.add(invoiceNumber);
                                System.out.println("   ✓ Checkbox " + selectedCount + " selected: " + invoiceNumber + " (Status: " + statusText + ")");
                                WaitUtils.sleep(500); // Small delay between selections

                            } catch (Exception checkboxError) {
                                System.err.println("   ❌ Could not find or click checkbox: " + checkboxError.getMessage());
                            }
                        }

                    } catch (Exception statusError) {
                        // No status badge in this row, might be a grouping row - skip
                    }

                } catch (Exception e) {
                    // Skip this row and continue
                }
            }

            if (selectedInvoices.size() >= 2) {
                System.out.println("\n✓ Successfully selected " + selectedInvoices.size() + " invoices:");
                for (int i = 0; i < selectedInvoices.size(); i++) {
                    System.out.println("   " + (i + 1) + ". " + selectedInvoices.get(i));
                }
            } else {
                System.err.println("❌ Only selected " + selectedInvoices.size() + " invoice(s). Minimum required: 2");
            }

            return selectedInvoices;

        } catch (Exception e) {
            System.err.println("❌ Failed to select multiple EAMS Verified invoice checkboxes: " + e.getMessage());
            e.printStackTrace();
            return selectedInvoices;
        }
    }
}

