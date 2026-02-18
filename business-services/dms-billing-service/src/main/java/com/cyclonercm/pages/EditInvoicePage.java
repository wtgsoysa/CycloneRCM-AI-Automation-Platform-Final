package com.cyclonercm.pages;


import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
     * Page Object Model for Daily Billing Edit Invoice functionality
     * Supports SMOKE_DEI_001 through SMOKE_DEI_012 test cases
     */
public class EditInvoicePage {

    private WebDriver driver;

    // ========== LOCATORS ==========

    // Main Edit Invoice Screen
    private By editInvoiceLabel = By.xpath("/html/body/div[2]/div/div[2]/div/div[1]/p-toolbar/div/div[1]/span");
    private By editFormEditButton = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[1]/p-toolbar/div/div[2]/button[6]");
    private By editFormSaveButton = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[1]/p-toolbar/div/div[2]/button[5]");

    // Mandatory Fields
    private By claimAdminField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[1]");
    private By employerField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[2]");
    private By caseField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[1]");
    private By applicantField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[2]");
    private By claimField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[3]");
    private By InterpreterField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[3]/div/div/div[1]/span[1]/u/b");
    private By InterpreterInvoiceField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[3]/div/div/div[2]");
    private By dosField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[4]/div[2]/div/input");
    private By qtyField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/tbody/tr/td[3]");
    private By invoiceNumberField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[2]/div[2]/input");
    private By invoiceDateField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[3]/div[2]/div/input");
    private By payDateField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[5]/div[2]/div/input");

    // Claim Administrator Popup
    private By claimAdminLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[1]/span[1]/u");
    private By claimAdminPopupLabel = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/div/p-toolbar/div/div[1]/span");
    private By claimAdminEditButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/div/p-toolbar/div/div[2]/button[3]");
    private By claimAdminNameField = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[2]/div/div/div/div/input");
    private By claimAdminZipField = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[1]/div/div/div/div[5]/div/div[1]/div/div/div/span/input");
    private By claimAdminZipDropdown = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[2]/div/cyclone-search-zip");
    private By zipsLabel = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[2]/div/cyclone-search-zip/div/div/div/div[1]/p-toolbar/div/div[1]/span");
    private By zipList = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[2]/div/cyclone-search-zip/div/div/div/div[2]/form/p-table/div/div/table/tbody");
    private By zipAddress = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/form/div/div/div/div[2]/div/cyclone-search-zip/div/div/div/div[2]/form/p-table/div/div/table/tbody/tr[1]");
    private By claimAdminSaveButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-claim-admin/div/div/p-toolbar/div/div[2]/button[2]");

    // Employer Popup
    private By employerLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[2]/span[1]/u");
    private By employerPopupLabel = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-employer/div/div/p-toolbar/div/div[1]/span");
    private By employerEditButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-employer/div/div/p-toolbar/div/div[2]/button[3]");
    private By employerNameField = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-employer/div/form/div/div/div/div[1]/div/div/div[1]/div[3]/div/div/div/div/input");
    private By employerAddressField = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-employer/div/form/div/div/div/div[1]/div/div/div[1]/div[4]/div/div/div/div/input");
    private By employerSaveButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-employer/div/div/p-toolbar/div/div[2]/button[2]");

    // Case Popup
    private By caseLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[3]/div[1]/span/u");
    private By casePopupLabel = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[1]/span");
    private By caseEditButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[3]");
    private By caseDosField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[2]/div/div/div/div/div");
    private By caseFileNoField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[3]/div/div/div/div/input");
    private By caseSaveButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[2]");

    // Applicant Popup
    private By applicantLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[3]/div[2]/span/u");
    private By applicantPopupLabel = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[1]/span");
    private By applicantEditButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[3]");
    private By applicantLastNameField = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[3]/div/div/div/div/input");
    private By applicantFirstNameField = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[4]/div/div[1]/div/div/input");
    private By applicantSaveButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[2]");

    // Claim Popup
    private By claimLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[3]/div[3]/span/u");
    private By claimPopupLabel = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[1]");
    private By claimEditButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[3]");
    private By claimDosField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[2]/div/div/div/div/div");
    private By claimFileNoField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[3]/div/div/div/div/input");
    private By claimSaveButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[2]");

    // Services Popup
    private By servicesLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/thead/tr/th[1]/div/u");
    private By servicesPopupLabel = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-dialog[1]/div/div/div[2]/cyclone-servicecode-multiselect/div/div[1]/p-toolbar/div/div[1]/span");
    private By servicesCodeList = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-dialog[1]/div/div/div[2]/cyclone-servicecode-multiselect/div/div[2]/form/div/div/div/div/div/p-table/div/div[2]/table");
    private By servicesSearchBar = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-dialog[1]/div/div/div[2]/cyclone-servicecode-multiselect/div/div[2]/form/div/div/div/div/div/p-table/div/div[1]/div/span/input");
    private By serviceSingleCode = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-dialog[1]/div/div/div[2]/cyclone-servicecode-multiselect/div/div[2]/form/div/div/div/div/div/p-table/div/div[2]/table/tbody/tr[1]");
    private By serviceDescription = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-dialog[1]/div/div/div[2]/cyclone-servicecode-multiselect/div/div[2]/form/div/div/div/div/div/p-table/div/div[2]/table/tbody/tr[1]/td[4]");
    private By servicesCloseButton = By.xpath("/html/body/ng-component/div/div/div[2]/billing-app/p-dialog[1]/div/div/div[2]/cyclone-servicecode-multiselect/div/div[1]/p-toolbar/div/div[2]/button");
    private By servicesInEditForm = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/tbody");
    private By servicesDescriptionInForm = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/tbody/tr/td[5]");

    // Invoice Date Picker
    private By invoiceDateSelector = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[3]/div[2]/div/button");
    private By invoiceDatePicker = By.xpath("/html/body/ngb-datepicker");

    // DOS Date Picker
    private By dosSelector = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[4]/div[2]/div/button");
    private By dosPicker = By.xpath("/html/body/ngb-datepicker");

    // Success Message
    private By successMessagePopup = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div");
    private By successMessageText = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p");

    // Financial Fields
    private By billedAmountField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/div[1]/div[2]");
    private By penaltyField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/div[2]/div[2]");
    private By interestField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/div[3]/div[2]");
    private By salesTaxField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/div[4]/div[2]");
    private By totalField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/div[5]/div[2]");
    private By adjustmentsField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[7]/div[1]/div[2]");
    private By paymentsField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[7]/div[2]/div[2]");
    private By balanceDueField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[7]/div[3]/div[2]");

    // Billing History Tab and Fields
    private By billingHistoryTab = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/p-tabview/div/ul/li[2]");
    private By historyStartDateField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/p-tabview/div/div[2]/p-tabpanel/div/div/div/div[2]/div/div/div/div[1]/div/div/div/input");
    private By historyEndDateField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/p-tabview/div/div[2]/p-tabpanel/div/div/div/div[2]/div/div/div/div[2]/div/div/div/input");
    private By historyApplyButton = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/p-tabview/div/div[2]/p-tabpanel/div/div/div/div[2]/div/div/div/div[3]/button");
    private By billingHistoryTable = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/p-tabview/div/div[2]/p-tabpanel/div/div/div/div[3]/p-table");

    // Left Side Invoice PDF Display
    private By leftSideInvoiceDisplay = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[1]");

    // ========== CONSTRUCTOR ==========

    public EditInvoicePage(WebDriver driver) {
        this.driver = driver;
    }

    // ========== NAVIGATION METHODS ==========

    /**
     * Get Edit Invoice label text
     * @return Edit Invoice label text
     */
    public String getEditInvoiceLabel() {
        try {
            WaitUtils.waitForVisibility(driver, editInvoiceLabel, 10);
            WebElement label = driver.findElement(editInvoiceLabel);
            return label.getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Edit Invoice label: " + e.getMessage());
            return null;
        }
    }

    /**
     * Click main Edit button
     */
    public void clickEditButton() {
        try {
            WaitUtils.waitForVisibility(driver, editFormEditButton, 10);
            WebElement button = driver.findElement(editFormEditButton);
            button.click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Edit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Edit button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click main Save button
     */
    public void clickSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, editFormSaveButton, 10);
            WebElement button = driver.findElement(editFormSaveButton);
            button.click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== FIELD PRESENCE VERIFICATION METHODS ==========

    public boolean isClaimAdminPresent() {
        try {
            return driver.findElement(claimAdminField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmployerPresent() {
        try {
            return driver.findElement(employerField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCasePresent() {
        try {
            return driver.findElement(caseField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isApplicantPresent() {
        try {
            return driver.findElement(applicantField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isClaimPresent() {
        try {
            return driver.findElement(claimField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInterpreterPresent() {
        try {
            return driver.findElement(InterpreterField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInterpreterInvoiceDetailsPresent() {
        try {
            return driver.findElement(InterpreterInvoiceField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDosPresent() {
        try {
            return driver.findElement(dosField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInvoiceNumberPresent() {
        try {
            return driver.findElement(invoiceNumberField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInvoiceDatePresent() {
        try {
            return driver.findElement(invoiceDateField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isQtyPresent() {
        try {
            return driver.findElement(qtyField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPayDatePresent() {
        try {
            return driver.findElement(payDateField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========== FIELD VALUE GETTER METHODS ==========

    public String getClaimAdminValue() {
        try {
            WaitUtils.waitForVisibility(driver, claimAdminField, 10);
            return driver.findElement(claimAdminField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Claim Admin value: " + e.getMessage());
            return null;
        }
    }

    public String getEmployerValue() {
        try {
            WaitUtils.waitForVisibility(driver, employerField, 10);
            return driver.findElement(employerField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Employer value: " + e.getMessage());
            return null;
        }
    }

    public String getCaseValue() {
        try {
            WaitUtils.waitForVisibility(driver, caseField, 10);
            return driver.findElement(caseField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Case value: " + e.getMessage());
            return null;
        }
    }

    public String getApplicantLastNameValue() {
        try {
            WaitUtils.waitForVisibility(driver, applicantLastNameField, 10);
            return driver.findElement(applicantLastNameField).getAttribute("value");
        } catch (Exception e) {
            System.err.println("Failed to get Applicant Last Name value: " + e.getMessage());
            return null;
        }
    }

    public String getApplicantFirstNameValue() {
        try {
            WaitUtils.waitForVisibility(driver, applicantFirstNameField, 10);
            return driver.findElement(applicantFirstNameField).getAttribute("value");
        } catch (Exception e) {
            System.err.println("Failed to get Applicant First Name value: " + e.getMessage());
            return null;
        }
    }

    public String getClaimValue() {
        try {
            WaitUtils.waitForVisibility(driver, claimField, 10);
            return driver.findElement(claimField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Claim value: " + e.getMessage());
            return null;
        }
    }

    public String getServicesValue() {
        try {
            WaitUtils.waitForVisibility(driver, servicesDescriptionInForm, 10);
            return driver.findElement(servicesDescriptionInForm).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Services value: " + e.getMessage());
            return null;
        }
    }

    public String getInvoiceNumberValue() {
        try {
            WaitUtils.waitForVisibility(driver, invoiceNumberField, 10);
            return driver.findElement(invoiceNumberField).getAttribute("value");
        } catch (Exception e) {
            System.err.println("Failed to get Invoice Number value: " + e.getMessage());
            return null;
        }
    }

    public String getInvoiceDateValue() {
        try {
            WaitUtils.waitForVisibility(driver, invoiceDateField, 10);
            return driver.findElement(invoiceDateField).getAttribute("value");
        } catch (Exception e) {
            System.err.println("Failed to get Invoice Date value: " + e.getMessage());
            return null;
        }
    }

    public String getDosValue() {
        try {
            WaitUtils.waitForVisibility(driver, dosField, 10);
            return driver.findElement(dosField).getAttribute("value");
        } catch (Exception e) {
            System.err.println("Failed to get DOS value: " + e.getMessage());
            return null;
        }
    }

    // ========== CLAIM ADMINISTRATOR METHODS ==========

    public void clickClaimAdminLink() {
        try {
            WaitUtils.waitForVisibility(driver, claimAdminLink, 10);
            WebElement link = driver.findElement(claimAdminLink);
            link.click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Claim Administrator link clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Claim Admin link: " + e.getMessage());
            throw e;
        }
    }

    public String getClaimAdminPopupLabel() {
        try {
            WaitUtils.waitForVisibility(driver, claimAdminPopupLabel, 10);
            return driver.findElement(claimAdminPopupLabel).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Claim Admin popup label: " + e.getMessage());
            return null;
        }
    }

    public void clickClaimAdminEditButton() {
        try {
            WaitUtils.waitForVisibility(driver, claimAdminEditButton, 10);
            driver.findElement(claimAdminEditButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Claim Admin Edit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Claim Admin Edit button: " + e.getMessage());
            throw e;
        }
    }

    public void editClaimAdminName(String name) {
        try {
            WaitUtils.waitForVisibility(driver, claimAdminNameField, 10);
            WebElement nameField = driver.findElement(claimAdminNameField);
            nameField.clear();
            nameField.sendKeys(name);
            WaitUtils.sleep(1000);
            System.out.println("✓ Claim Admin name edited: " + name);
        } catch (Exception e) {
            System.err.println("Failed to edit Claim Admin name: " + e.getMessage());
            throw e;
        }
    }

    public void selectRandomZip() {
        try {
            // Click ZIP field to open dropdown
            WaitUtils.waitForVisibility(driver, claimAdminZipField, 10);
            driver.findElement(claimAdminZipField).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ ZIP field clicked");

            // Wait for ZIP list to appear
            WaitUtils.waitForVisibility(driver, zipList, 10);

            // Select first ZIP address
            WaitUtils.waitForVisibility(driver, zipAddress, 10);
            driver.findElement(zipAddress).click();
            WaitUtils.sleep(1000);
            System.out.println("✓ Random ZIP selected");
        } catch (Exception e) {
            System.err.println("Failed to select random ZIP: " + e.getMessage());
            throw e;
        }
    }

    public void clickClaimAdminSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, claimAdminSaveButton, 10);
            driver.findElement(claimAdminSaveButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Claim Admin Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Claim Admin Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== EMPLOYER METHODS ==========

    public void clickEmployerLink() {
        try {
            WaitUtils.waitForVisibility(driver, employerLink, 10);
            driver.findElement(employerLink).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Employer link clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Employer link: " + e.getMessage());
            throw e;
        }
    }

    public String getEmployerPopupLabel() {
        try {
            WaitUtils.waitForVisibility(driver, employerPopupLabel, 10);
            return driver.findElement(employerPopupLabel).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Employer popup label: " + e.getMessage());
            return null;
        }
    }

    public void clickEmployerEditButton() {
        try {
            WaitUtils.waitForVisibility(driver, employerEditButton, 10);
            driver.findElement(employerEditButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Employer Edit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Employer Edit button: " + e.getMessage());
            throw e;
        }
    }

    public void editEmployerName(String name) {
        try {
            WaitUtils.waitForVisibility(driver, employerNameField, 10);
            WebElement nameField = driver.findElement(employerNameField);
            nameField.clear();
            nameField.sendKeys(name);
            WaitUtils.sleep(1000);
            System.out.println("✓ Employer name edited: " + name);
        } catch (Exception e) {
            System.err.println("Failed to edit Employer name: " + e.getMessage());
            throw e;
        }
    }

    public void editEmployerAddress(String address) {
        try {
            WaitUtils.waitForVisibility(driver, employerAddressField, 10);
            WebElement addressField = driver.findElement(employerAddressField);
            addressField.clear();
            addressField.sendKeys(address);
            WaitUtils.sleep(1000);
            System.out.println("✓ Employer address edited: " + address);
        } catch (Exception e) {
            System.err.println("Failed to edit Employer address: " + e.getMessage());
            throw e;
        }
    }

    public void clickEmployerSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, employerSaveButton, 10);
            driver.findElement(employerSaveButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Employer Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Employer Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== CASE METHODS ==========

    public void clickCaseLink() {
        try {
            WaitUtils.waitForVisibility(driver, caseLink, 10);
            driver.findElement(caseLink).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Case link clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Case link: " + e.getMessage());
            throw e;
        }
    }

    public String getCasePopupLabel() {
        try {
            WaitUtils.waitForVisibility(driver, casePopupLabel, 10);
            return driver.findElement(casePopupLabel).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Case popup label: " + e.getMessage());
            return null;
        }
    }

    public void clickCaseEditButton() {
        try {
            WaitUtils.waitForVisibility(driver, caseEditButton, 10);
            driver.findElement(caseEditButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Case Edit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Case Edit button: " + e.getMessage());
            throw e;
        }
    }

    public void editCaseFileNo(String fileNo) {
        try {
            WaitUtils.waitForVisibility(driver, caseFileNoField, 10);
            WebElement field = driver.findElement(caseFileNoField);
            field.clear();
            field.sendKeys(fileNo);
            WaitUtils.sleep(1000);
            System.out.println("✓ Case File No edited: " + fileNo);
        } catch (Exception e) {
            System.err.println("Failed to edit Case File No: " + e.getMessage());
            throw e;
        }
    }

    public void clickCaseSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, caseSaveButton, 10);
            driver.findElement(caseSaveButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Case Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Case Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== APPLICANT METHODS ==========

    public void clickApplicantLink() {
        try {
            WaitUtils.waitForVisibility(driver, applicantLink, 10);
            driver.findElement(applicantLink).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Applicant link clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Applicant link: " + e.getMessage());
            throw e;
        }
    }

    public String getApplicantPopupLabel() {
        try {
            WaitUtils.waitForVisibility(driver, applicantPopupLabel, 10);
            return driver.findElement(applicantPopupLabel).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Applicant popup label: " + e.getMessage());
            return null;
        }
    }

    public void clickApplicantEditButton() {
        try {
            WaitUtils.waitForVisibility(driver, applicantEditButton, 20);
            driver.findElement(applicantEditButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Applicant Edit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Applicant Edit button: " + e.getMessage());
            throw e;
        }
    }

    public void editApplicantLastName(String lastName) {
        try {
            WaitUtils.waitForVisibility(driver, applicantLastNameField, 10);
            WebElement field = driver.findElement(applicantLastNameField);
            field.clear();
            field.sendKeys(lastName);
            WaitUtils.sleep(1000);
            System.out.println("✓ Applicant Last Name edited: " + lastName);
        } catch (Exception e) {
            System.err.println("Failed to edit Applicant Last Name: " + e.getMessage());
            throw e;
        }
    }

    public void editApplicantFirstName(String firstName) {
        try {
            WaitUtils.waitForVisibility(driver, applicantFirstNameField, 10);
            WebElement field = driver.findElement(applicantFirstNameField);
            field.clear();
            field.sendKeys(firstName);
            WaitUtils.sleep(1000);
            System.out.println("✓ Applicant First Name edited: " + firstName);
        } catch (Exception e) {
            System.err.println("Failed to edit Applicant First Name: " + e.getMessage());
            throw e;
        }
    }

    public void clickApplicantSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, applicantSaveButton, 10);
            driver.findElement(applicantSaveButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Applicant Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Applicant Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== CLAIM METHODS ==========

    public void clickClaimLink() {
        try {
            WaitUtils.waitForVisibility(driver, claimLink, 10);
            driver.findElement(claimLink).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Claim link clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Claim link: " + e.getMessage());
            throw e;
        }
    }

    public String getClaimPopupLabel() {
        try {
            WaitUtils.waitForVisibility(driver, claimPopupLabel, 10);
            return driver.findElement(claimPopupLabel).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Claim popup label: " + e.getMessage());
            return null;
        }
    }

    public void clickClaimEditButton() {
        try {
            WaitUtils.waitForVisibility(driver, claimEditButton, 10);
            driver.findElement(claimEditButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Claim Edit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Claim Edit button: " + e.getMessage());
            throw e;
        }
    }

    public void editClaimFileNo(String fileNo) {
        try {
            WaitUtils.waitForVisibility(driver, claimFileNoField, 10);
            WebElement field = driver.findElement(claimFileNoField);
            field.clear();
            field.sendKeys(fileNo);
            WaitUtils.sleep(1000);
            System.out.println("✓ Claim File No edited: " + fileNo);
        } catch (Exception e) {
            System.err.println("Failed to edit Claim File No: " + e.getMessage());
            throw e;
        }
    }

    public void clickClaimSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, claimSaveButton, 10);
            driver.findElement(claimSaveButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Claim Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Claim Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== SIMPLIFIED EDIT METHODS (Single Billing Edit Invoice) ==========

    /**
     * Simplified method to edit Claim Administrator using direct field text
     * @param newClaimAdmin New claim admin name to set
     */
    public void editClaimAdmin(String newClaimAdmin) {
        try {
            clickClaimAdminLink();
            clickClaimAdminEditButton();
            editClaimAdminName(newClaimAdmin);
            clickClaimAdminSaveButton();
            System.out.println("✓ Claim Admin edited successfully to: " + newClaimAdmin);
        } catch (Exception e) {
            System.err.println("Failed to edit Claim Admin: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Simplified method to edit Employer using direct field
     * @param newEmployer New employer name to set
     */
    public void editEmployer(String newEmployer) {
        try {
            clickEmployerLink();
            clickEmployerEditButton();
            editEmployerName(newEmployer);
            clickEmployerSaveButton();
            System.out.println("✓ Employer edited successfully to: " + newEmployer);
        } catch (Exception e) {
            System.err.println("Failed to edit Employer: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Simplified method to edit Case
     * @param newCase New case file number to set
     */
    public void editCase(String newCase) {
        try {
            clickCaseLink();
            clickCaseEditButton();
            editCaseFileNo(newCase);
            clickCaseSaveButton();
            System.out.println("✓ Case edited successfully to: " + newCase);
        } catch (Exception e) {
            System.err.println("Failed to edit Case: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Simplified method to edit Claim
     * @param newClaim New claim file number to set
     */
    public void editClaim(String newClaim) {
        try {
            clickClaimLink();
            clickClaimEditButton();
            editClaimFileNo(newClaim);
            clickClaimSaveButton();
            System.out.println("✓ Claim edited successfully to: " + newClaim);
        } catch (Exception e) {
            System.err.println("Failed to edit Claim: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Simplified method to edit Services by selecting first service with matching text
     * @param serviceText Service description text to search for
     */
    public void editServices(String serviceText) {
        try {
            clickServicesLink();
            WaitUtils.sleep(2000);
            // Search for the service if search bar available
            try {
                WebElement searchBar = driver.findElement(servicesSearchBar);
                searchBar.clear();
                searchBar.sendKeys(serviceText);
                WaitUtils.sleep(2000);
            } catch (Exception e) {
                System.out.println("Search bar not available, selecting from list");
            }
            selectFirstServiceAndGetDescription();
            clickServicesCloseButton();
            System.out.println("✓ Services edited successfully");
        } catch (Exception e) {
            System.err.println("Failed to edit Services: " + e.getMessage());
            throw e;
        }
    }

    // ========== SERVICES METHODS ==========

    public void clickServicesLink() {
        try {
            WaitUtils.waitForVisibility(driver, servicesLink, 10);
            driver.findElement(servicesLink).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Services link clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Services link: " + e.getMessage());
            throw e;
        }
    }

    public String getServicesPopupLabel() {
        try {
            WaitUtils.waitForVisibility(driver, servicesPopupLabel, 10);
            return driver.findElement(servicesPopupLabel).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Services popup label: " + e.getMessage());
            return null;
        }
    }

    public String selectFirstServiceAndGetDescription() {
        try {
            // Get description before clicking
            WaitUtils.waitForVisibility(driver, serviceDescription, 10);
            String description = driver.findElement(serviceDescription).getText().trim();
            System.out.println("✓ Service description captured: " + description);

            // Click the service
            driver.findElement(serviceSingleCode).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ First service selected");

            return description;
        } catch (Exception e) {
            System.err.println("Failed to select service: " + e.getMessage());
            throw e;
        }
    }

    public void clickServicesCloseButton() {
        try {
            WaitUtils.waitForVisibility(driver, servicesCloseButton, 10);
            driver.findElement(servicesCloseButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Services popup closed");
        } catch (Exception e) {
            System.err.println("Failed to close Services popup: " + e.getMessage());
            throw e;
        }
    }

    public String getServiceDescriptionInForm() {
        try {
            WaitUtils.waitForVisibility(driver, servicesDescriptionInForm, 10);
            String description = driver.findElement(servicesDescriptionInForm).getText().trim();
            System.out.println("✓ Service description in form: " + description);
            return description;
        } catch (Exception e) {
            System.err.println("Failed to get service description in form: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get all service descriptions from the services table in Edit Form
     * @return List of all service descriptions
     */
    public List<String> getAllServiceDescriptionsInForm() {
        try {
            // XPath to get all service rows
            By allServiceRows = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/tbody/tr");

            WaitUtils.waitForVisibility(driver, servicesInEditForm, 10);
            List<WebElement> rows = driver.findElements(allServiceRows);

            List<String> descriptions = new java.util.ArrayList<>();
            for (WebElement row : rows) {
                try {
                    // Get the 5th column (description) from each row
                    WebElement descriptionCell = row.findElement(By.xpath(".//td[5]"));
                    String description = descriptionCell.getText().trim();
                    if (!description.isEmpty()) {
                        descriptions.add(description);
                        System.out.println("  ✓ Found service: " + description);
                    }
                } catch (Exception e) {
                    // Skip rows that don't have a 5th column
                    continue;
                }
            }

            System.out.println("✓ Total services found in form: " + descriptions.size());
            return descriptions;
        } catch (Exception e) {
            System.err.println("Failed to get all service descriptions in form: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    // ========== INVOICE NUMBER METHODS ==========

    public String getCurrentInvoiceNumber() {
        try {
            WaitUtils.waitForVisibility(driver, invoiceNumberField, 10);
            String invoiceNumber = driver.findElement(invoiceNumberField).getAttribute("value");
            System.out.println("✓ Current invoice number: " + invoiceNumber);
            return invoiceNumber;
        } catch (Exception e) {
            System.err.println("Failed to get current invoice number: " + e.getMessage());
            return null;
        }
    }

    public void editInvoiceNumber(String newNumber) {
        try {
            WaitUtils.waitForVisibility(driver, invoiceNumberField, 10);
            WebElement field = driver.findElement(invoiceNumberField);
            field.clear();
            field.sendKeys(newNumber);
            WaitUtils.sleep(1000);
            System.out.println("✓ Invoice number edited to: " + newNumber);
        } catch (Exception e) {
            System.err.println("Failed to edit invoice number: " + e.getMessage());
            throw e;
        }
    }

    // ========== DATE PICKER METHODS ==========

    public void clickInvoiceDateSelector() {
        try {
            WaitUtils.waitForVisibility(driver, invoiceDateSelector, 10);
            driver.findElement(invoiceDateSelector).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Invoice Date selector clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Invoice Date selector: " + e.getMessage());
            throw e;
        }
    }

    public void clickDosSelector() {
        try {
            WaitUtils.waitForVisibility(driver, dosSelector, 10);
            driver.findElement(dosSelector).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ DOS selector clicked");
        } catch (Exception e) {
            System.err.println("Failed to click DOS selector: " + e.getMessage());
            throw e;
        }
    }

    public boolean isDatePickerDisplayed() {
        try {
            return driver.findElement(invoiceDatePicker).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Edit Invoice Date by directly entering the date
     * @param date Date string in format MM/DD/YYYY
     */
    public void editInvoiceDate(String date) {
        try {
            WaitUtils.waitForVisibility(driver, invoiceDateField, 10);
            WebElement field = driver.findElement(invoiceDateField);
            field.clear();
            field.sendKeys(date);
            WaitUtils.sleep(1000);
            System.out.println("✓ Invoice Date edited to: " + date);
        } catch (Exception e) {
            System.err.println("Failed to edit Invoice Date: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edit DOS (Date of Service) by directly entering the date
     * @param date Date string in format MM/DD/YYYY
     */
    public void editDos(String date) {
        try {
            WaitUtils.waitForVisibility(driver, dosField, 10);
            WebElement field = driver.findElement(dosField);
            field.clear();
            field.sendKeys(date);
            WaitUtils.sleep(1000);
            System.out.println("✓ DOS edited to: " + date);
        } catch (Exception e) {
            System.err.println("Failed to edit DOS: " + e.getMessage());
            throw e;
        }
    }

    // ========== SUCCESS MESSAGE METHODS ==========

    public String getSuccessMessage() {
        try {
            WaitUtils.waitForVisibility(driver, successMessageText, 10);
            String message = driver.findElement(successMessageText).getText().trim();
            System.out.println("✓ Success message: " + message);
            return message;
        } catch (Exception e) {
            System.err.println("Failed to get success message: " + e.getMessage());
            return null;
        }
    }

    // ========== LEFT SIDE DISPLAY METHODS ==========

    public boolean isInvoicePdfDisplayed() {
        try {
            WaitUtils.waitForVisibility(driver, leftSideInvoiceDisplay, 10);
            return driver.findElement(leftSideInvoiceDisplay).isDisplayed();
        } catch (Exception e) {
            System.err.println("Failed to check invoice PDF display: " + e.getMessage());
            return false;
        }
    }

    public WebElement getInvoicePdfElement() {
        try {
            WaitUtils.waitForVisibility(driver, leftSideInvoiceDisplay, 10);
            return driver.findElement(leftSideInvoiceDisplay);
        } catch (Exception e) {
            System.err.println("Failed to get invoice PDF element: " + e.getMessage());
            return null;
        }
    }

    // ========== FINANCIAL AMOUNT METHODS ==========

    public String getBilledAmount() {
        try {
            WaitUtils.waitForVisibility(driver, billedAmountField, 10);
            return driver.findElement(billedAmountField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Billed Amount: " + e.getMessage());
            return null;
        }
    }

    public String getPenalty() {
        try {
            WaitUtils.waitForVisibility(driver, penaltyField, 10);
            return driver.findElement(penaltyField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Penalty: " + e.getMessage());
            return null;
        }
    }

    public String getInterest() {
        try {
            WaitUtils.waitForVisibility(driver, interestField, 10);
            return driver.findElement(interestField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Interest: " + e.getMessage());
            return null;
        }
    }

    public String getSalesTax() {
        try {
            WaitUtils.waitForVisibility(driver, salesTaxField, 10);
            return driver.findElement(salesTaxField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Sales Tax: " + e.getMessage());
            return null;
        }
    }

    public String getTotal() {
        try {
            WaitUtils.waitForVisibility(driver, totalField, 10);
            return driver.findElement(totalField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Total: " + e.getMessage());
            return null;
        }
    }

    public String getAdjustments() {
        try {
            WaitUtils.waitForVisibility(driver, adjustmentsField, 10);
            return driver.findElement(adjustmentsField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Adjustments: " + e.getMessage());
            return null;
        }
    }

    public String getPayments() {
        try {
            WaitUtils.waitForVisibility(driver, paymentsField, 10);
            return driver.findElement(paymentsField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Payments: " + e.getMessage());
            return null;
        }
    }

    public String getBalanceDue() {
        try {
            WaitUtils.waitForVisibility(driver, balanceDueField, 10);
            return driver.findElement(balanceDueField).getText().trim();
        } catch (Exception e) {
            System.err.println("Failed to get Balance Due: " + e.getMessage());
            return null;
        }
    }

    // ========== BILLING HISTORY METHODS ==========

    public void clickBillingHistoryTab() {
        try {
            WaitUtils.waitForVisibility(driver, billingHistoryTab, 10);
            driver.findElement(billingHistoryTab).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Billing History tab clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Billing History tab: " + e.getMessage());
            throw e;
        }
    }

    public void setHistoryStartDate(String date) {
        try {
            WaitUtils.waitForVisibility(driver, historyStartDateField, 10);
            WebElement field = driver.findElement(historyStartDateField);
            field.clear();
            field.sendKeys(date);
            WaitUtils.sleep(1000);
            System.out.println("✓ History Start Date set to: " + date);
        } catch (Exception e) {
            System.err.println("Failed to set History Start Date: " + e.getMessage());
            throw e;
        }
    }

    public void setHistoryEndDate(String date) {
        try {
            WaitUtils.waitForVisibility(driver, historyEndDateField, 10);
            WebElement field = driver.findElement(historyEndDateField);
            field.clear();
            field.sendKeys(date);
            WaitUtils.sleep(1000);
            System.out.println("✓ History End Date set to: " + date);
        } catch (Exception e) {
            System.err.println("Failed to set History End Date: " + e.getMessage());
            throw e;
        }
    }

    public void clickHistoryApplyButton() {
        try {
            WaitUtils.waitForVisibility(driver, historyApplyButton, 10);
            driver.findElement(historyApplyButton).click();
            WaitUtils.sleep(2000);
            System.out.println("✓ History Apply button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click History Apply button: " + e.getMessage());
            throw e;
        }
    }

    public boolean isBillingHistoryDisplayed() {
        try {
            WaitUtils.waitForVisibility(driver, billingHistoryTable, 10);
            return driver.findElement(billingHistoryTable).isDisplayed();
        } catch (Exception e) {
            System.err.println("Failed to check if Billing History is displayed: " + e.getMessage());
            return false;
        }
    }
}


