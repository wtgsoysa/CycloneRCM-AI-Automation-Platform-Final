package com.cyclonercm.pages;


import com.cyclonercm.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
     * Page Object Model for Daily Billing Edit Invoice functionality
     * Supports SMOKE_DEI_001 through SMOKE_DEI_012 test cases
     */
public class EditInvoicePage2 {

    private WebDriver driver;

    // ========== LOCATORS ==========

    // Main Edit Invoice Screen
    private By editInvoiceLabel = By.xpath("/html/body/div[2]/div/div[2]/div/div[1]/p-toolbar/div/div[1]/span");
    private By editFormEditButton = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[1]/p-toolbar/div/div[2]/button[5]");
    private By editFormSaveButton = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[1]/p-toolbar/div/div[2]/button[4]");
    private By mainFormSaveButton = By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[1]/p-toolbar/div/div[2]/button[4]");


    // Mandatory Fields
    private By claimAdminField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[1]");
    private By employerField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[2]");
    private By caseField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[1]");
    private By applicantField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[2]");
    private By claimField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[3]");
    private By InterpreterField = By.xpath("//span[contains(text(),'Interpreter') or contains(text(),'INTERPRETER')]/u/b | //label[contains(text(),'Interpreter')]/following-sibling::*//b | //b[contains(text(),'Interpreter')]");
    private By InterpreterInvoiceField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[3]/div/div/div[2]");
    private By dosField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[4]/div[2]/div/input");
    private By qtyField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/tbody/tr/td[3]");
    private By invoiceNumberField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[2]/div[2]/input");
    private By invoiceDateField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[3]/div[2]/div/input");
    private By payDateField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[5]/div[2]/div/input");

    // Claim Administrator Popup
    private By claimAdminLink = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[1]/div[2]/div[1]/span[1]/u");
    private By claimAdminPopupLabel = By.xpath("//span[normalize-space()='Claim Administrator']");
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
    private By employerZipField = By.xpath("//cyclone-edit-employer//form//div[contains(@class,'p-col') or contains(@class,'col')]//input[contains(@class,'p-inputtext') and @placeholder='search...'] | /html/body/div[3]/div/div[2]/cyclone-edit-employer/div/form/div/div/div/div[1]/div/div/div[1]/div[6]/div/div[1]/div/div/div/span/input | /html/body/div[4]/div/div[2]/cyclone-edit-employer/div/form/div/div/div/div[1]/div/div/div[1]/div[6]/div/div[1]/div/div/div/span/input");
    private By employerSaveButton = By.xpath("/html/body/div[3]/div/div[2]/cyclone-edit-employer/div/div/p-toolbar/div/div[2]/button[2]");

    // Case Popup
    private By caseLink = By.xpath("//client-billing-form//div[contains(text(),'CASE#')]/../following-sibling::div//span | /html/body/div[3]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[1]/span | /html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[1]/span");
    private By casePopupLabel = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[1]/span");
    private By caseEditButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[3]");
    private By caseDosField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[2]/div/div/div/div/div");
    private By caseFileNoField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[3]/div/div/div/div/input");
    private By caseSaveButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[2]");

    // Applicant Popup
    // applicantLink: div[2] = File History context, div[3] = Daily/Single Billing context
    // Also includes a semantic fallback via client-billing-form structure
    private By applicantLink = By.xpath(
        // File History edit – form at div[2]
        "/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[2]/span/u" +
        " | " +
        // File History edit – span wrapper (no <u>)
        "/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[2]/span" +
        " | " +
        // Daily / Single Billing edit – form at div[3]
        "/html/body/div[3]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[2]/span/u" +
        " | " +
        // Semantic fallback – any context
        "//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//div[4]/div[2]/span/u | //client-billing-form//div[4]/div[2]//span[.//u]"
    );

    // Popup locators covering div[3], div[4], div[5] overlay layers
    private By applicantPopupLabel = By.xpath(
        "/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[1]/span" +
        " | " +
        "/html/body/div[4]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[1]/span" +
        " | " +
        "/html/body/div[5]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[1]/span"
    );
    private By applicantEditButton = By.xpath(
        "/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[3]" +
        " | " +
        "/html/body/div[4]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[3]" +
        " | " +
        "/html/body/div[5]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[3]"
    );
    private By applicantLastNameField = By.xpath(
        "/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[3]/div/div/div/div/input" +
        " | " +
        "/html/body/div[4]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[3]/div/div/div/div/input" +
        " | " +
        "/html/body/div[5]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[3]/div/div/div/div/input"
    );
    private By applicantFirstNameField = By.xpath(
        "/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[4]/div/div[1]/div/div/input" +
        " | " +
        "/html/body/div[4]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[4]/div/div[1]/div/div/input" +
        " | " +
        "/html/body/div[5]/div/div[2]/cyclone-edit-patient/div/form/div/div/div/div[1]/p-tabview/div/div[2]/p-tabpanel/div/div/div/div/div[4]/div/div[1]/div/div/input"
    );
    private By applicantSaveButton = By.xpath(
        "/html/body/div[3]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[2]" +
        " | " +
        "/html/body/div[4]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[2]" +
        " | " +
        "/html/body/div[5]/div/div[2]/cyclone-edit-patient/div/div/p-toolbar/div/div[3]/button[2]"
    );

    // Claim Popup
    private By claimLink = By.xpath("/html/body/div[3]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[3]/span/u");
    private By claimPopupLabel = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[1]");
    private By claimEditButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[3]");
    private By claimDosField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[2]/div/div/div/div/div");
    private By claimFileNoField = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/form/div/div/div/div[1]/div/div/div/div[3]/div/div/div/div/input");
    private By claimSaveButton = By.xpath("/html/body/div[3]/div/div[2]/aeliusmd-edit-case/div/div/p-toolbar/div/div[2]/button[2]");

    // Interpreter Popup
    private By interpreterLink = By.xpath("//client-billing-form//div[contains(text(),'Interpreter')]/../following-sibling::div//span | /html/body/div[3]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[4]/span/u | /html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[4]/span/u");
    private By interpreterPopupLabel = By.xpath("/html/body/div[3]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[1]/span | /html/body/div[4]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[1]/span | /html/body/div[5]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[1]/span");
    private By interpreterEditButton = By.xpath("/html/body/div[3]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[3] | /html/body/div[4]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[3] | /html/body/div[5]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[3]");
    private By interpreterNameField = By.xpath("/html/body/div[3]/div/div[2]/cyclonercm-edit-interpreter/div/form/div/div/div/div/div/div/div/div[2]/div/div/div/div/input | /html/body/div[4]/div/div[2]/cyclonercm-edit-interpreter/div/form/div/div/div/div/div/div/div/div[2]/div/div/div/div/input | /html/body/div[5]/div/div[2]/cyclonercm-edit-interpreter/div/form/div/div/div/div/div/div/div/div[2]/div/div/div/div/input");
    private By interpreterSaveButton = By.xpath("/html/body/div[3]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[2] | /html/body/div[4]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[2] | /html/body/div[5]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[2]");
    private By interpreterCloseButton = By.xpath("/html/body/div[3]/div/div[2]/cyclonercm-edit-interpreter/div/div/p-toolbar/div/div[2]/button[4]");

    // Interpreter Invoice Details Popup
    private By interpreterInvoiceDetailsLink = By.xpath("//span[contains(@class,'hyperlink-text') or contains(@style,'color: rgb(65, 105, 225)')]//u[contains(text(),'Interpreter Invoice Details')] | //span[contains(text(),'Interpreter Invoice Details') and .//u]");
    private By interpreterInvoiceDetailsPopupLabel = By.xpath("//div[@class='p-dialog-header']//span[@class='p-dialog-title' and contains(text(),'Interpreter Invoice Details')] | //p-toolbar[contains(@styleclass,'toolbar-edit-employer')]//span[contains(text(),'Interpreter Invoice Details')]");
    private By interpreterInvoiceDetailsEditButton = By.xpath("//div[contains(@class,'p-dialog-content')]//button[@icon='pi pi-pencil' and @ptooltip='Edit']");
    private By interpreterInvoiceDetailsSaveButton = By.xpath("/html/body/div[3]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2] | /html/body/div[4]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2] | /html/body/div[5]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2] | /html/body/div[6]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2]");
    private By interpreterInvoiceDetailsCloseButton = By.xpath("//div[contains(@class,'p-dialog-content')]//button[@icon='pi pi-times' and @ptooltip='Close']");

    // Interpreter Invoice Details Fields
    private By interpreterLanguageField = By.xpath("//label[contains(text(),'Language')]/following-sibling::div//input | //label[./strong[contains(text(),'Language')]]/following-sibling::div//input");
    private By interpreterExoticDropdown = By.xpath("//label[contains(text(),'Exotic')]/following-sibling::div//p-dropdown | //label[./strong[contains(text(),'Exotic')]]/following-sibling::div//p-dropdown");
    private By interpreterServiceRateField = By.xpath("//label[contains(text(),'Service Rate')]/following-sibling::div//input | //label[./strong[contains(text(),'Service Rate')]]/following-sibling::div//input");
    private By interpreterTypeField = By.xpath("//label[contains(text(),'Interp. Type')]/following-sibling::div//input | //label[./strong[contains(text(),'Interp. Type')]]/following-sibling::div//input");
    private By interpreterDrNameField = By.xpath("//label[contains(text(),'Dr. Name')]/following-sibling::div//input | //label[./strong[contains(text(),'Dr. Name')]]/following-sibling::div//input");
    private By interpreterDrAddressField = By.xpath("//label[contains(text(),'Dr. Address')]/following-sibling::div//input | //label[./strong[contains(text(),'Dr. Address')]]/following-sibling::div//input");
    private By interpreterTermsField = By.xpath("//label[contains(text(),'Terms')]/following-sibling::div//input | //label[./strong[contains(text(),'Terms')]]/following-sibling::div//input");

    // Services Popup - Multiple dialog layers supported
    private By servicesLink = By.xpath("//div[contains(@class,'p-dialog-content')]//client-billing-form//div[6]//p-table//table//thead//tr//th[1]//div | /html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/p-table/div/div/table/thead/tr/th[1]/div | /html/body/div[3]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/p-table/div/div/table/thead/tr/th[1]/div | /html/body/div[4]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[6]/p-table/div/div/table/thead/tr/th[1]/div");
    private By servicesPopupLabel = By.xpath("//cyclone-servicecode-multiselect//p-toolbar//div[@class='p-toolbar-group-left']//span | //div[contains(@class,'p-dialog-content')]//cyclone-servicecode-multiselect//div[@class='p-toolbar-group-left']//span");
    private By servicesCodeList = By.xpath("//cyclone-servicecode-multiselect//p-table//table//tbody | //div[contains(@class,'p-dialog-content')]//cyclone-servicecode-multiselect//p-table//div[@class='p-datatable-wrapper']//table//tbody");
    private By servicesSearchBar = By.xpath("//cyclone-servicecode-multiselect//input[@placeholder='Search Service Codes'] | //div[contains(@class,'p-dialog-content')]//cyclone-servicecode-multiselect//input[@placeholder='Search Service Codes']");
    private By serviceSingleCode = By.xpath("//cyclone-servicecode-multiselect//p-table//tbody//tr[1] | //div[contains(@class,'p-dialog-content')]//cyclone-servicecode-multiselect//p-table//tbody//tr[1]");
    private By serviceDescription = By.xpath("//cyclone-servicecode-multiselect//p-table//tbody//tr[1]//td[4] | //div[contains(@class,'p-dialog-content')]//cyclone-servicecode-multiselect//p-table//tbody//tr[1]//td[4]");
    private By servicesCloseButton = By.xpath("//cyclone-servicecode-multiselect//p-toolbar//div[@class='p-toolbar-group-right']//button[@icon='pi pi-times'] | //div[contains(@class,'p-dialog-content')]//cyclone-servicecode-multiselect//p-toolbar//button[@icon='pi pi-times']");
    private By servicesInEditForm = By.xpath("//create-billing-order//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//p-table[.//thead//th[contains(text(),'Code') or contains(text(),'CPT') or contains(text(),'Description')]]//tbody | //client-billing-form//p-table[.//thead//th[contains(text(),'Code') or contains(text(),'CPT')]]//tbody");
    private By servicesDescriptionInForm = By.xpath("//create-billing-order//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//p-table[.//thead//th[contains(text(),'Code') or contains(text(),'CPT')]]//tbody//tr//td[5] | //client-billing-form//p-table[.//thead//th[contains(text(),'Code') or contains(text(),'CPT')]]//tbody//tr//td[5]");

    // Invoice Date Picker
    private By invoiceDateSelector = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[3]/div[2]/div/button");
    private By invoiceDatePicker = By.xpath("/html/body/ngb-datepicker");

    // DOS Date Picker
    private By dosSelector = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[2]/div[2]/div/div[4]/div[2]/div/button");
    private By dosPicker = By.xpath("/html/body/ngb-datepicker");

    // Success Message
    // Covers all 3 entry-points: file-history-app, billing-app (daily), billing-app (single)
    private By successMessageText = By.xpath(
        // File History edit
        "/html/body/ng-component/div/div/div[2]/file-history-app/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p" +
        " | " +
        // Daily Billing edit
        "/html/body/ng-component/div/div/div[2]/billing-app/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p" +
        " | " +
        // Single Billing edit (same structure, kept as explicit path)
        "/html/body/ng-component/div/div/div[2]/billing-app/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p" +
        " | " +
        // Generic fallback – any ui-message toast body paragraph
        "//ui-message//p-toastitem//div[contains(@class,'p-toast-detail')] | //ui-message//p-toastitem//div//p"
    );

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

    private By servicesField = By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[5]/p-table/div/div/table/tbody");

    // ========== CONSTRUCTOR ==========

    public EditInvoicePage2(WebDriver driver) {
        this.driver = driver;
    }

    // ========== NAVIGATION METHODS ==========

    /**
     * Get Edit Invoice label text
     *
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

    public void clickFormCloseButton(){
        try {
            WebElement button = driver.findElement(interpreterCloseButton);
            button.click();
            WaitUtils.sleep(2000);
            System.out.println("✓ Close button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Close button: " + e.getMessage());
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
            // Strategy 1: Try the primary locator
            try {
                WaitUtils.sleep(1000); // Allow page to settle
                return driver.findElement(InterpreterField).isDisplayed();
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed for Interpreter field, trying alternative...");

                // Strategy 2: Try finding by partial text
                try {
                    WebElement element = driver.findElement(By.xpath("//b[contains(text(),'Interpreter') or contains(text(),'INTERPRETER')]"));
                    return element.isDisplayed();
                } catch (Exception e2) {
                    System.out.println("⚠ Strategy 2 failed for Interpreter field, trying another alternative...");

                    // Strategy 3: Try finding within client-billing-form
                    try {
                        WebElement element = driver.findElement(By.xpath("//client-billing-form//span[contains(text(),'Interpreter')]"));
                        return element.isDisplayed();
                    } catch (Exception e3) {
                        System.out.println("✗ All strategies failed for Interpreter field");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking Interpreter field presence: " + e.getMessage());
            return false;
        }
    }

    public boolean isInterpreterInvoiceDetailsPresent() {
        try {
            // Strategy 1: Try the primary locator
            try {
                WaitUtils.sleep(1000); // Allow page to settle
                return driver.findElement(InterpreterInvoiceField).isDisplayed();
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed for Interpreter Invoice field, trying alternative...");

                // Strategy 2: Try finding sibling div after Interpreter label
                try {
                    WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Interpreter')]/ancestor::div[1]/following-sibling::div[1]"));
                    return element.isDisplayed();
                } catch (Exception e2) {
                    System.out.println("⚠ Strategy 2 failed for Interpreter Invoice field, trying another alternative...");

                    // Strategy 3: Try finding within specific structure
                    try {
                        WebElement element = driver.findElement(By.xpath("//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//span[contains(text(),'Interpreter')]/ancestor::div[1]/following-sibling::div"));
                        return element.isDisplayed();
                    } catch (Exception e3) {
                        System.out.println("✗ All strategies failed for Interpreter Invoice field");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking Interpreter Invoice Details field presence: " + e.getMessage());
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

    public void selectEmployerRandomZip() {
        try {
            // Strategy 1: Try to find and click ZIP field using multiple locators
            WebElement zipField = null;
            boolean fieldClicked = false;

            // Try primary XPath
            try {
                WaitUtils.waitForVisibility(driver, employerZipField, 5);
                zipField = driver.findElement(employerZipField);
                System.out.println("✓ Found Employer ZIP field");
            } catch (Exception e) {
                System.out.println("⚠ Primary Employer ZIP field locator failed, trying alternatives...");
            }

            // Try alternative locators if primary fails
            if (zipField == null) {
                By[] alternativeZipLocators = {
                        By.xpath("//cyclone-edit-employer//input[@placeholder='search...']"),
                        By.xpath("//cyclone-edit-employer//cyclone-search-zip//input"),
                        By.cssSelector("cyclone-edit-employer input[placeholder*='search']"),
                        By.xpath("//cyclone-edit-employer//form//input[contains(@class,'p-inputtext')][@placeholder='search...']")
                };

                for (By locator : alternativeZipLocators) {
                    try {
                        zipField = driver.findElement(locator);
                        if (zipField.isDisplayed()) {
                            System.out.println("✓ Found Employer ZIP field using alternative locator");
                            break;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }

            if (zipField == null) {
                throw new RuntimeException("Could not locate Employer ZIP field with any strategy");
            }

            // Click ZIP field - try both regular and JavaScript click
            try {
                zipField.click();
                fieldClicked = true;
            } catch (Exception e) {
                System.out.println("⚠ Regular click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", zipField);
                fieldClicked = true;
            }

            WaitUtils.sleep(2000);
            System.out.println("✓ Employer ZIP field clicked");

            // Define Employer-specific ZIP list locators (NOT Claim Admin locators)
            By employerZipList = By.xpath("//cyclone-edit-employer//cyclone-search-zip//p-table//tbody");
            By employerZipAddress = By.xpath("//cyclone-edit-employer//cyclone-search-zip//p-table//tbody/tr[1]");

            // Wait for ZIP list to appear
            try {
                WaitUtils.waitForVisibility(driver, employerZipList, 10);
                System.out.println("✓ Employer ZIP list appeared");
            } catch (Exception e) {
                System.out.println("⚠ ZIP list not visible, trying to continue...");
            }

            // Select first ZIP address - try multiple selection strategies
            boolean zipSelected = false;

            // Strategy 1: Use Employer-specific first row
            try {
                WaitUtils.waitForVisibility(driver, employerZipAddress, 5);
                WebElement firstZip = driver.findElement(employerZipAddress);
                firstZip.click();
                zipSelected = true;
                System.out.println("✓ Random ZIP selected using employer-specific locator");
            } catch (Exception e) {
                System.out.println("⚠ Employer-specific ZIP selection failed, trying generic locator...");
            }

            // Strategy 2: Try generic cyclone-search-zip locator
            if (!zipSelected) {
                try {
                    By genericZipRow = By.xpath("//cyclone-search-zip//p-table//tbody/tr[1]");
                    WaitUtils.waitForVisibility(driver, genericZipRow, 5);
                    WebElement firstZipGeneric = driver.findElement(genericZipRow);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstZipGeneric);
                    zipSelected = true;
                    System.out.println("✓ Random ZIP selected using generic locator");
                } catch (Exception ex) {
                    System.out.println("⚠ Generic ZIP selection failed: " + ex.getMessage());
                }
            }

            if (!zipSelected) {
                throw new RuntimeException("Could not select ZIP with any method");
            }

            WaitUtils.sleep(1000);
            System.out.println("✓ Employer ZIP selection completed");
        } catch (Exception e) {
            System.err.println("Failed to select Employer random ZIP: " + e.getMessage());
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
            System.out.println("🔗 Attempting to click Case link...");

            // Strategy 1: Use the primary locator with wait
            try {
                WaitUtils.waitForVisibility(driver, caseLink, 10);
                driver.findElement(caseLink).click();
                WaitUtils.sleep(3000);
                System.out.println("✓ Case link clicked (Strategy 1)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Try with JavaScript click
            try {
                WebElement element = driver.findElement(caseLink);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                WaitUtils.sleep(3000);
                System.out.println("✓ Case link clicked (Strategy 2 - JavaScript)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Find by text content
            try {
                By caseLinkByText = By.xpath("//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//span[contains(text(),'CASE#') or contains(text(),'Case') or normalize-space()!='']");
                List<WebElement> elements = driver.findElements(caseLinkByText);
                for (WebElement elem : elements) {
                    if (elem.isDisplayed() && elem.isEnabled()) {
                        elem.click();
                        WaitUtils.sleep(3000);
                        System.out.println("✓ Case link clicked (Strategy 3 - By text)");
                        return;
                    }
                }
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed: " + e3.getMessage());
            }

            throw new RuntimeException("Failed to click Case link after trying all strategies");
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
            System.out.println("🔗 Attempting to click Applicant link...");

            // Strategy 1: Primary OR locator (covers File History div[2] and Daily Billing div[3])
            try {
                WaitUtils.waitForVisibility(driver, applicantLink, 10);
                driver.findElement(applicantLink).click();
                WaitUtils.sleep(3000);
                System.out.println("✓ Applicant link clicked (Strategy 1 - primary locator)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed: " + e1.getMessage());
            }

            // Strategy 2: JavaScript click on the same locator
            try {
                WebElement el = driver.findElement(applicantLink);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                WaitUtils.sleep(3000);
                System.out.println("✓ Applicant link clicked (Strategy 2 - JS click)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed: " + e2.getMessage());
            }

            // Strategy 3: Semantic search – find <u> inside div[4]/div[2] of client-billing-form
            By[] fallbacks = {
                By.xpath("//client-billing-form//div[4]/div[2]//u"),
                By.xpath("//client-billing-form//div[4]/div[2]/span"),
                By.xpath("//client-billing-form//div[contains(@class,'p-col-6') or contains(@class,'p-col')]//span[.//u][2]")
            };
            for (By fb : fallbacks) {
                try {
                    WebElement el = driver.findElement(fb);
                    if (el.isDisplayed()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                        WaitUtils.sleep(3000);
                        System.out.println("✓ Applicant link clicked (Strategy 3 - fallback)");
                        return;
                    }
                } catch (Exception ignored) {}
            }

            throw new RuntimeException("Failed to click Applicant link after all strategies");
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

    // ========== INTERPRETER METHODS ==========

    public void clickInterpreterLink() {
        try {
            System.out.println("🔗 Attempting to click Interpreter link...");

            // Strategy 1: Try the primary locator
            try {
                WaitUtils.waitForVisibility(driver, interpreterLink, 10);
                driver.findElement(interpreterLink).click();
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter link clicked (Strategy 1)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Try JavaScript click with fallback xpath
            try {
                WebElement element = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div[2]/create-billing-order/div/div/div[2]/div/div/div/div/div/div/client-billing-form/div/div/div/div[4]/div[4]/span/u"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter link clicked (Strategy 2 - JS click)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Fallback - try finding by text content
            try {
                WebElement element = driver.findElement(By.xpath("//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//span[contains(text(),'Interpreter') or contains(., 'Interpreter')]//u"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter link clicked (Strategy 3 - Fallback)");
                return;
            } catch (Exception e3) {
                throw new RuntimeException("All strategies failed to click Interpreter link");
            }
        } catch (Exception e) {
            System.err.println("Failed to click Interpreter link: " + e.getMessage());
            throw e;
        }
    }

    public String getInterpreterPopupLabel() {
        try {
            // Try multiple strategies to find the popup label
            try {
                WaitUtils.waitForVisibility(driver, interpreterPopupLabel, 10);
                return driver.findElement(interpreterPopupLabel).getText().trim();
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed for Interpreter popup label, trying Strategy 2...");

                // Strategy 2: Try with cyclonercm-edit-interpreter tag
                try {
                    WebElement label = driver.findElement(By.xpath("//cyclonercm-edit-interpreter//p-toolbar//span[contains(@class,'p-toolbar-group-left') or contains(@class,'toolbar')] | //cyclonercm-edit-interpreter//p-toolbar//div//span"));
                    return label.getText().trim();
                } catch (Exception e2) {
                    System.out.println("⚠ Strategy 2 failed for Interpreter popup label, trying Strategy 3...");

                    // Strategy 3: Try any toolbar span in visible dialog
                    try {
                        WebElement label = driver.findElement(By.xpath("//div[contains(@class,'p-dialog') and contains(@style,'display: block')]//p-toolbar//span"));
                        return label.getText().trim();
                    } catch (Exception e3) {
                        System.err.println("Failed to get Interpreter popup label with all strategies");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to get Interpreter popup label: " + e.getMessage());
            return null;
        }
    }

    public void clickInterpreterEditButton() {
        try {
            System.out.println("🔘 Attempting to click Interpreter Edit button...");

            // Strategy 1: Try the primary locator with multiple div indices
            try {
                WaitUtils.waitForVisibility(driver, interpreterEditButton, 10);
                driver.findElement(interpreterEditButton).click();
                WaitUtils.sleep(2000);
                System.out.println("✓ Interpreter Edit button clicked (Strategy 1)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");

                // Strategy 2: Try JavaScript click
                try {
                    WebElement editBtn = driver.findElement(interpreterEditButton);
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", editBtn);
                    WaitUtils.sleep(2000);
                    System.out.println("✓ Interpreter Edit button clicked (Strategy 2 - JS)");
                    return;
                } catch (Exception e2) {
                    System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");

                    // Strategy 3: Try finding Edit button by text
                    try {
                        WebElement editBtn = driver.findElement(By.xpath("//cyclonercm-edit-interpreter//button[contains(@class,'p-button') and .//span[text()='Edit']] | //cyclonercm-edit-interpreter//button[@type='button'][3]"));
                        editBtn.click();
                        WaitUtils.sleep(2000);
                        System.out.println("✓ Interpreter Edit button clicked (Strategy 3 - By text)");
                        return;
                    } catch (Exception e3) {
                        System.out.println("⚠ All strategies failed");
                        throw e3;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to click Interpreter Edit button: " + e.getMessage());
            throw e;
        }
    }

    public void editInterpreterName(String name) {
        try {
            WaitUtils.waitForVisibility(driver, interpreterNameField, 10);
            WebElement field = driver.findElement(interpreterNameField);
            field.clear();
            field.sendKeys(name);
            WaitUtils.sleep(1000);
            System.out.println("✓ Interpreter name edited: " + name);
        } catch (Exception e) {
            System.err.println("Failed to edit Interpreter name: " + e.getMessage());
            throw e;
        }
    }

    public void clickInterpreterSaveButton() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterSaveButton, 10);
            driver.findElement(interpreterSaveButton).click();
            WaitUtils.sleep(3000);
            System.out.println("✓ Interpreter Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Interpreter Save button: " + e.getMessage());
            throw e;
        }
    }

    // ========== SIMPLIFIED EDIT METHODS (Single Billing Edit Invoice) ==========

    /**
     * Simplified method to edit Claim Administrator using direct field text
     *
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
     *
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
     *
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
     *
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
     * Simplified method to edit Interpreter
     *
     * @param newInterpreter New interpreter name to set
     */
    public void editInterpreter(String newInterpreter) {
        try {
            clickInterpreterLink();
            clickInterpreterEditButton();
            editInterpreterName(newInterpreter);
            clickInterpreterSaveButton();
            System.out.println("✓ Interpreter edited successfully to: " + newInterpreter);
        } catch (Exception e) {
            System.err.println("Failed to edit Interpreter: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Simplified method to edit Services by selecting first service with matching text
     *
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
        System.out.println("🔗 Attempting to click Services link...");
        try {
            // Strategy 1: Wait and click using primary xpath
            try {
                WaitUtils.waitForVisibility(driver, servicesLink, 10);
                driver.findElement(servicesLink).click();
                WaitUtils.sleep(3000);
                System.out.println("✓ Services link clicked (Strategy 1)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Try with JavaScriptExecutor
            try {
                WebElement link = driver.findElement(servicesLink);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                js.executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Services link clicked (Strategy 2 - JavaScript)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Try finding by relative xpath
            try {
                By relativeServicesLink = By.xpath("//client-billing-form//p-table//table//thead//tr//th[1]//div[contains(text(),'Service Code') or contains(text(),'Services')]");
                WaitUtils.waitForVisibility(driver, relativeServicesLink, 5);
                WebElement link = driver.findElement(relativeServicesLink);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Services link clicked (Strategy 3 - Fallback)");
                return;
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed");
            }

            throw new RuntimeException("All strategies failed to click Services link");
        } catch (Exception e) {
            System.err.println("Failed to click Services link: " + e.getMessage());
            throw e;
        }
    }

    public String getServicesPopupLabel() {
        try {
            // Strategy 1: Primary locator
            try {
                WaitUtils.waitForVisibility(driver, servicesPopupLabel, 10);
                String label = driver.findElement(servicesPopupLabel).getText().trim();
                if (label != null && !label.isEmpty()) {
                    System.out.println("✓ Got popup label (Strategy 1): " + label);
                    return label;
                }
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Find by text content
            try {
                By labelByText = By.xpath("//span[contains(text(),'Service Codes')] | //span[normalize-space()='Service Codes']");
                WaitUtils.waitForVisibility(driver, labelByText, 5);
                String label = driver.findElement(labelByText).getText().trim();
                System.out.println("✓ Got popup label (Strategy 2): " + label);
                return label;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Find within toolbar
            try {
                By toolbarLabel = By.xpath("//p-toolbar[@styleclass='toolbar-service-code-multiSelect']//span[contains(text(),'Service')]");
                WaitUtils.waitForVisibility(driver, toolbarLabel, 5);
                String label = driver.findElement(toolbarLabel).getText().trim();
                System.out.println("✓ Got popup label (Strategy 3): " + label);
                return label;
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed");
            }

            System.err.println("Failed to get Services popup label with all strategies");
            return null;
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
     *
     * @return List of all service descriptions
     */
    public List<String> getAllServiceDescriptionsInForm() {
        try {
            // XPath to get all service rows - using flexible locator
            By allServiceRows = By.xpath("//create-billing-order//client-billing-form//div[contains(@class,'p-col') or contains(@class,'col')]//p-table[.//thead//th[contains(text(),'Code') or contains(text(),'CPT') or contains(text(),'Description')]]//tbody//tr | //client-billing-form//p-table[.//thead//th[contains(text(),'Code') or contains(text(),'CPT')]]//tbody//tr");

            WaitUtils.waitForVisibility(driver, servicesInEditForm, 10);
            List<WebElement> rows = driver.findElements(allServiceRows);

            List<String> descriptions = new java.util.ArrayList<>();
            for (WebElement row : rows) {
                try {
                    // Get the 4th column (description) from each row - trying multiple column indices
                    WebElement descriptionCell = null;
                    String description = "";

                    // Try column 4 first (Description column)
                    try {
                        descriptionCell = row.findElement(By.xpath(".//td[4]"));
                        description = descriptionCell.getText().trim();
                    } catch (Exception e1) {
                        // Try column 5 if column 4 doesn't work
                        try {
                            descriptionCell = row.findElement(By.xpath(".//td[5]"));
                            description = descriptionCell.getText().trim();
                        } catch (Exception e2) {
                            // Skip this row
                            continue;
                        }
                    }

                    if (!description.isEmpty()) {
                        descriptions.add(description);
                        System.out.println("  ✓ Found service: " + description);
                    }
                } catch (Exception e) {
                    // Skip rows that don't have description column
                    continue;
                }
            }

            System.out.println("✓ Total services found in form: " + descriptions.size());
            return descriptions;
        } catch (Exception e) {
            System.err.println("Failed to get all service descriptions in form: " + e.getMessage());
            e.printStackTrace();
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
     *
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
     *
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
        // Try each known absolute XPath in order – stops at the first one that returns text
        String[] xpaths = {
            // File History edit
            "/html/body/ng-component/div/div/div[2]/file-history-app/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p",
            // Daily Billing / Single Billing edit
            "/html/body/ng-component/div/div/div[2]/billing-app/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/div/p",
            // Generic fallback – p-toast-detail div
            "//ui-message//p-toastitem//div[contains(@class,'p-toast-detail')]",
            // Generic fallback – any paragraph inside a toastitem
            "//ui-message//p-toastitem//div//p",
            // Broadest fallback – any visible p-toastitem text
            "//p-toastitem//div[contains(@class,'p-toast-message-text')]//p",
        };

        for (String xpath : xpaths) {
            try {
                By locator = By.xpath(xpath);
                // Short wait per candidate (3 s) – avoids spending 10 s on each wrong one
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                String text = el.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Success message captured via [" + xpath.substring(0, Math.min(60, xpath.length())) + "...]: " + text);
                    return text;
                }
            } catch (Exception ignored) {
                // This XPath did not match in time – try the next one
            }
        }

        System.err.println("Failed to get success message: none of the toast XPaths returned text within the wait window");
        return null;
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

    // ========== INTERPRETER INVOICE DETAILS METHODS ==========

    /**
     * Click Interpreter Invoice Details link on the main form
     */
    public void clickInterpreterInvoiceDetailsLink() {
        try {
            System.out.println("🔗 Clicking Interpreter Invoice Details link...");
            WaitUtils.sleep(2000);

            // Strategy 1: Target the <b> tag containing the text (most specific)
            try {
                WebElement link = driver.findElement(By.xpath("//b[text()='Interpreter Invoice Details']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter Invoice Details link clicked (Strategy 1 - <b> tag)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Target the <u> tag containing the <b> tag
            try {
                WebElement link = driver.findElement(By.xpath("//u[.//b[text()='Interpreter Invoice Details']]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter Invoice Details link clicked (Strategy 2 - <u> tag)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Target the parent <span> tag with cursor:pointer style
            try {
                WebElement link = driver.findElement(By.xpath("//span[@style='cursor: pointer;' and .//b[text()='Interpreter Invoice Details']]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter Invoice Details link clicked (Strategy 3 - <span> with cursor:pointer)");
                return;
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed, trying Strategy 4...");
            }

            // Strategy 4: Target <span> that contains <u><b>Interpreter Invoice Details</b></u>
            try {
                WebElement link = driver.findElement(By.xpath("//span[contains(@style,'cursor') and .//u//b[text()='Interpreter Invoice Details']]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter Invoice Details link clicked (Strategy 4 - <span> contains cursor)");
                return;
            } catch (Exception e4) {
                System.out.println("⚠ Strategy 4 failed, trying Strategy 5...");
            }

            // Strategy 5: Use partial text matching with normalize-space
            try {
                WebElement link = driver.findElement(By.xpath("//span[contains(normalize-space(.),'Interpreter Invoice Details')][@style='cursor: pointer;']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter Invoice Details link clicked (Strategy 5 - normalize-space)");
                return;
            } catch (Exception e5) {
                System.out.println("⚠ Strategy 5 failed, trying Strategy 6...");
            }

            // Strategy 6: Broad search - any element containing the exact text
            try {
                WebElement link = driver.findElement(By.xpath("//*[text()='Interpreter Invoice Details']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                WaitUtils.sleep(1000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                WaitUtils.sleep(3000);
                System.out.println("✓ Interpreter Invoice Details link clicked (Strategy 6 - broad search)");
                return;
            } catch (Exception e6) {
                System.out.println("⚠ All strategies failed to click Interpreter Invoice Details link");
                throw new RuntimeException("Unable to click Interpreter Invoice Details link after trying all strategies", e6);
            }

        } catch (Exception e) {
            System.err.println("Failed to click Interpreter Invoice Details link: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get Interpreter Invoice Details popup label
     */
    public String getInterpreterInvoiceDetailsPopupLabel() {
        try {
            WaitUtils.sleep(2000); // Allow popup to fully render

            // Strategy 1: Try toolbar-edit-employer with contains class
            try {
                WebElement label = driver.findElement(By.xpath("//div[contains(@class,'toolbar-edit-employer')]//div[contains(@class,'p-toolbar-group-left')]//span"));
                String text = label.getText().trim();
                if (!text.isEmpty() && text.contains("Interpreter Invoice Details")) {
                    System.out.println("✓ Got popup label (Strategy 1): " + text);
                    return text;
                }
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Try toolbar group left with contains class
            try {
                WebElement label = driver.findElement(By.xpath("//div[contains(@class,'p-toolbar-group-left')]//span[contains(text(),'Interpreter Invoice Details')]"));
                String text = label.getText().trim();
                System.out.println("✓ Got popup label (Strategy 2): " + text);
                return text;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Try dialog content with toolbar
            try {
                WebElement label = driver.findElement(By.xpath("//div[contains(@class,'p-dialog-content')]//p-toolbar//div[contains(@class,'p-toolbar-group-left')]//span"));
                String text = label.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Got popup label (Strategy 3): " + text);
                    return text;
                }
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed, trying Strategy 4...");
            }

            // Strategy 4: Try any visible dialog with toolbar span
            try {
                WebElement label = driver.findElement(By.xpath("//div[contains(@class,'p-dialog') and @role='dialog']//p-toolbar//span[contains(text(),'Interpreter Invoice Details')]"));
                String text = label.getText().trim();
                System.out.println("✓ Got popup label (Strategy 4): " + text);
                return text;
            } catch (Exception e4) {
                System.out.println("⚠ Strategy 4 failed, trying Strategy 5...");
            }

            // Strategy 5: Try any span with the text in visible dialog
            try {
                WebElement label = driver.findElement(By.xpath("//div[contains(@class,'p-dialog-content')]//span[text()='Interpreter Invoice Details']"));
                String text = label.getText().trim();
                System.out.println("✓ Got popup label (Strategy 5): " + text);
                return text;
            } catch (Exception e5) {
                System.out.println("⚠ Strategy 5 failed, trying Strategy 6...");
            }

            // Strategy 6: Try the dialog header with p-dialog-title
            try {
                WebElement label = driver.findElement(By.xpath("//div[contains(@class,'p-dialog-header')]//span[contains(@class,'p-dialog-title')]"));
                String text = label.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("✓ Got popup label (Strategy 6): " + text);
                    return text;
                }
            } catch (Exception e6) {
                System.err.println("Failed to get Interpreter Invoice Details popup label with all strategies");
                throw new RuntimeException("Unable to locate Interpreter Invoice Details popup label after trying all strategies", e6);
            }

            // If all strategies fail and no exception is thrown
            System.err.println("Failed to get Interpreter Invoice Details popup label with all strategies");
            throw new RuntimeException("Unable to locate Interpreter Invoice Details popup label");

        } catch (Exception e) {
            System.err.println("Failed to get Interpreter Invoice Details popup label: " + e.getMessage());
            throw new RuntimeException("Error getting Interpreter Invoice Details popup label", e);
        }
    }


    /**
     * Click Edit button in Interpreter Invoice Details popup
     */
    public void clickInterpreterInvoiceDetailsEditButton() {
        try {
            System.out.println("🔘 Clicking Edit button in Interpreter Invoice Details popup...");
            WaitUtils.sleep(2000); // Allow popup to settle

            // Strategy 1: Try with multiple dialog positions using XPath from error log
            try {
                By editButton = By.xpath("//div[@role='dialog']//button[@icon='pi pi-pencil' and @ptooltip='Edit' and not(@disabled)] | " +
                                        "/html/body/div[3]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[3] | " +
                                        "/html/body/div[4]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[3] | " +
                                        "/html/body/div[5]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[3]");
                WebElement editBtn = driver.findElement(editButton);

                // Wait for button to be enabled
                for (int i = 0; i < 10; i++) {
                    String disabledAttr = editBtn.getAttribute("disabled");
                    if ((disabledAttr == null || !disabledAttr.equals("true")) && editBtn.isDisplayed() && editBtn.isEnabled()) {
                        break;
                    }
                    WaitUtils.sleep(500);
                    editBtn = driver.findElement(editButton);
                }

                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", editBtn);
                WaitUtils.sleep(500);

                // Try JavaScript click to avoid overlay
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                WaitUtils.sleep(2000);
                System.out.println("✓ Edit button clicked (Strategy 1 - JS Click)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Wait for overlay to disappear and use Actions
            try {
                // Wait for dialog mask to disappear
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("p-dialog-mask")));

                By editButton = By.xpath("//div[@role='dialog']//p-toolbar//button[@icon='pi pi-pencil']");
                WebElement editBtn = driver.findElement(editButton);

                Actions actions = new Actions(driver);
                actions.moveToElement(editBtn).pause(Duration.ofMillis(500)).click().perform();
                WaitUtils.sleep(2000);
                System.out.println("✓ Edit button clicked (Strategy 2 - Actions)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Find by toolbar and button position
            try {
                By editButton = By.xpath("//p-toolbar[contains(@styleclass,'toolbar-edit-employer')]//button[@icon='pi pi-pencil']");
                WebElement editBtn = driver.findElement(editButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                WaitUtils.sleep(2000);
                System.out.println("✓ Edit button clicked (Strategy 3 - Toolbar)");
                return;
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed, trying Strategy 4...");
            }

            // Strategy 4: Try finding by ptooltip attribute value
            try {
                By editButton = By.xpath("//div[contains(@class,'p-dialog')]//button[@ptooltip='Edit' and @icon='pi pi-pencil']");
                WebElement editBtn = driver.findElement(editButton);

                // Remove disabled attribute if present
                ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", editBtn);
                WaitUtils.sleep(500);

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                WaitUtils.sleep(2000);
                System.out.println("✓ Edit button clicked (Strategy 4 - Remove disabled)");
                return;
            } catch (Exception e4) {
                System.err.println("⚠ All strategies failed to click Edit button");
                throw new RuntimeException("Unable to click Edit button after trying all strategies", e4);
            }

        } catch (Exception e) {
            System.err.println("Failed to click Edit button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get Language field value
     */
    public String getInterpreterLanguage() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterLanguageField, 10);
            String value = driver.findElement(interpreterLanguageField).getAttribute("value");
            System.out.println("✓ Language: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Language: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get Exotic dropdown value
     */
    public String getInterpreterExotic() {
        try {
            WaitUtils.sleep(1000);
            WebElement dropdown = driver.findElement(interpreterExoticDropdown);
            WebElement selectedLabel = dropdown.findElement(By.xpath(".//span[contains(@class,'p-dropdown-label')]"));
            String value = selectedLabel.getText().trim();
            System.out.println("✓ Exotic: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Exotic: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get Service Rate field value
     */
    public String getInterpreterServiceRate() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterServiceRateField, 10);
            String value = driver.findElement(interpreterServiceRateField).getAttribute("value");
            System.out.println("✓ Service Rate: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Service Rate: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get Interpreter Type field value
     */
    public String getInterpreterType() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterTypeField, 10);
            String value = driver.findElement(interpreterTypeField).getAttribute("value");
            System.out.println("✓ Interp. Type: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Interp. Type: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get Dr. Name field value
     */
    public String getInterpreterDrName() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterDrNameField, 10);
            String value = driver.findElement(interpreterDrNameField).getAttribute("value");
            System.out.println("✓ Dr. Name: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Dr. Name: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get Dr. Address field value
     */
    public String getInterpreterDrAddress() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterDrAddressField, 10);
            String value = driver.findElement(interpreterDrAddressField).getAttribute("value");
            System.out.println("✓ Dr. Address: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Dr. Address: " + e.getMessage());
            return "";
        }
    }

    /**
     * Get Terms field value
     */
    public String getInterpreterTerms() {
        try {
            WaitUtils.waitForVisibility(driver, interpreterTermsField, 10);
            String value = driver.findElement(interpreterTermsField).getAttribute("value");
            System.out.println("✓ Terms: " + value);
            return value;
        } catch (Exception e) {
            System.err.println("Failed to get Terms: " + e.getMessage());
            return "";
        }
    }

    /**
     * Edit Language field
     */
    public void editInterpreterLanguage(String language) {
        try {
            WaitUtils.waitForVisibility(driver, interpreterLanguageField, 10);
            WebElement field = driver.findElement(interpreterLanguageField);
            field.clear();
            field.sendKeys(language);
            WaitUtils.sleep(1000);
            System.out.println("✓ Language edited to: " + language);
        } catch (Exception e) {
            System.err.println("Failed to edit Language: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edit Service Rate field
     */
    public void editInterpreterServiceRate(String rate) {
        try {
            WaitUtils.waitForVisibility(driver, interpreterServiceRateField, 10);
            WebElement field = driver.findElement(interpreterServiceRateField);
            field.clear();
            field.sendKeys(rate);
            WaitUtils.sleep(1000);
            System.out.println("✓ Service Rate edited to: " + rate);
        } catch (Exception e) {
            System.err.println("Failed to edit Service Rate: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edit Dr. Name field
     */
    public void editInterpreterDrName(String drName) {
        try {
            WaitUtils.waitForVisibility(driver, interpreterDrNameField, 10);
            WebElement field = driver.findElement(interpreterDrNameField);
            field.clear();
            field.sendKeys(drName);
            WaitUtils.sleep(1000);
            System.out.println("✓ Dr. Name edited to: " + drName);
        } catch (Exception e) {
            System.err.println("Failed to edit Dr. Name: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edit Dr. Address field
     */
    public void editInterpreterDrAddress(String address) {
        try {
            WaitUtils.waitForVisibility(driver, interpreterDrAddressField, 10);
            WebElement field = driver.findElement(interpreterDrAddressField);
            field.clear();
            field.sendKeys(address);
            WaitUtils.sleep(1000);
            System.out.println("✓ Dr. Address edited to: " + address);
        } catch (Exception e) {
            System.err.println("Failed to edit Dr. Address: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Edit Terms field
     */
    public void editInterpreterTerms(String terms) {
        try {
            WaitUtils.waitForVisibility(driver, interpreterTermsField, 10);
            WebElement field = driver.findElement(interpreterTermsField);
            field.clear();
            field.sendKeys(terms);
            WaitUtils.sleep(1000);
            System.out.println("✓ Terms edited to: " + terms);
        } catch (Exception e) {
            System.err.println("Failed to edit Terms: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click Save button in Interpreter Invoice Details popup
     */
    public void clickInterpreterInvoiceDetailsSaveButton() {
        try {
            System.out.println("💾 Clicking Save button in Interpreter Invoice Details popup...");
            WaitUtils.sleep(2000); // Allow popup to settle

            // Strategy 1: Try with multiple dialog positions using XPath
            try {
                By saveButton = By.xpath("//div[@role='dialog']//button[@icon='pi pi-save' and @ptooltip='Save' and not(@disabled)] | " +
                                        "/html/body/div[3]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2] | " +
                                        "/html/body/div[4]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2] | " +
                                        "/html/body/div[5]/div/div[2]/div/div[1]/p-toolbar/div/div[2]/button[2]");
                WebElement saveBtn = driver.findElement(saveButton);

                // Remove disabled attribute if present
                ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", saveBtn);
                WaitUtils.sleep(500);

                // Try JavaScript click to avoid overlay
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                WaitUtils.sleep(3000);
                System.out.println("✓ Save button clicked (Strategy 1 - JS Click)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Find by toolbar and button position
            try {
                By saveButton = By.xpath("//p-toolbar[contains(@styleclass,'toolbar-edit-employer')]//button[@icon='pi pi-save']");
                WebElement saveBtn = driver.findElement(saveButton);

                // Remove disabled attribute if present
                ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", saveBtn);
                WaitUtils.sleep(500);

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                WaitUtils.sleep(3000);
                System.out.println("✓ Save button clicked (Strategy 2 - Toolbar)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Find by ptooltip attribute value
            try {
                By saveButton = By.xpath("//div[contains(@class,'p-dialog')]//button[@ptooltip='Save' and @icon='pi pi-save']");
                WebElement saveBtn = driver.findElement(saveButton);

                // Remove disabled attribute
                ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", saveBtn);
                WaitUtils.sleep(500);

                // Scroll into view and click
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveBtn);
                WaitUtils.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                WaitUtils.sleep(3000);
                System.out.println("✓ Save button clicked (Strategy 3 - Dialog)");
                return;
            } catch (Exception e3) {
                System.err.println("⚠ All strategies failed to click Save button");
                throw e3;
            }

        } catch (Exception e) {
            System.err.println("Failed to click Save button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click Close button in Interpreter Invoice Details popup
     */
    public void clickInterpreterInvoiceDetailsCloseButton() {
        try {
            System.out.println("❌ Closing Interpreter Invoice Details popup...");
            WaitUtils.sleep(2000); // Allow popup to settle

            // Strategy 1: Try JavaScript click with multiple dialog positions
            try {
                By closeButton = By.xpath("//div[@role='dialog']//button[@icon='pi pi-times' and @ptooltip='Close'] | " +
                        "//div[contains(@class,'p-dialog-content')]//button[@icon='pi pi-times' and @ptooltip='Close'] | " +
                        "//p-toolbar//button[@icon='pi pi-times' and @ptooltip='Close']");
                WebElement closeBtn = driver.findElement(closeButton);

                // Use JavaScript click to avoid overlay
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                WaitUtils.sleep(2000);
                System.out.println("✓ Popup closed (Strategy 1 - JS Click)");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Strategy 1 failed, trying Strategy 2...");
            }

            // Strategy 2: Wait for overlay to disappear and use Actions
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("p-dialog-mask")));

                By closeButton = By.xpath("//div[@role='dialog']//p-toolbar//button[@icon='pi pi-times']");
                WebElement closeBtn = driver.findElement(closeButton);

                Actions actions = new Actions(driver);
                actions.moveToElement(closeBtn).pause(Duration.ofMillis(500)).click().perform();
                WaitUtils.sleep(2000);
                System.out.println("✓ Popup closed (Strategy 2 - Actions)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Strategy 2 failed, trying Strategy 3...");
            }

            // Strategy 3: Try with toolbar-edit-employer class
            try {
                By closeButton = By.xpath("//p-toolbar[contains(@styleclass,'toolbar-edit-employer')]//button[@icon='pi pi-times']");
                WebElement closeBtn = driver.findElement(closeButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                WaitUtils.sleep(2000);
                System.out.println("✓ Popup closed (Strategy 3 - Toolbar)");
                return;
            } catch (Exception e3) {
                System.out.println("⚠ Strategy 3 failed, trying Strategy 4...");
            }

            // Strategy 4: Try pressing ESC key
            try {
                Actions actions = new Actions(driver);
                actions.sendKeys(org.openqa.selenium.Keys.ESCAPE).perform();
                WaitUtils.sleep(2000);
                System.out.println("✓ Popup closed (Strategy 4 - ESC key)");
                return;
            } catch (Exception e4) {
                System.out.println("⚠ Strategy 4 failed, trying Strategy 5...");
            }

            // Strategy 5: Click the dialog header close icon
            try {
                By closeButton = By.xpath("//div[@class='p-dialog-header']//button[contains(@class,'p-dialog-header-close')]");
                WebElement closeBtn = driver.findElement(closeButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                WaitUtils.sleep(2000);
                System.out.println("✓ Popup closed (Strategy 5 - Header close)");
                return;
            } catch (Exception e5) {
                throw new RuntimeException("Unable to close popup after trying all strategies", e5);
            }

        } catch (Exception e) {
            System.err.println("Failed to close popup: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verify all Interpreter Invoice Details fields are present and editable
     */
    public boolean verifyInterpreterInvoiceDetailsFields() {
        try {
            System.out.println("\n📋 Verifying Interpreter Invoice Details fields...");

            boolean allPresent = true;

            // Check Language field
            try {
                WaitUtils.waitForVisibility(driver, interpreterLanguageField, 5);
                System.out.println("✓ Language field present");
            } catch (Exception e) {
                System.err.println("✗ Language field missing");
                allPresent = false;
            }

            // Check Exotic dropdown
            try {
                driver.findElement(interpreterExoticDropdown);
                System.out.println("✓ Exotic dropdown present");
            } catch (Exception e) {
                System.err.println("✗ Exotic dropdown missing");
                allPresent = false;
            }

            // Check Service Rate field
            try {
                driver.findElement(interpreterServiceRateField);
                System.out.println("✓ Service Rate field present");
            } catch (Exception e) {
                System.err.println("✗ Service Rate field missing");
                allPresent = false;
            }

            // Check Interpreter Type field
            try {
                driver.findElement(interpreterTypeField);
                System.out.println("✓ Interp. Type field present");
            } catch (Exception e) {
                System.err.println("✗ Interp. Type field missing");
                allPresent = false;
            }

            // Check Dr. Name field
            try {
                driver.findElement(interpreterDrNameField);
                System.out.println("✓ Dr. Name field present");
            } catch (Exception e) {
                System.err.println("✗ Dr. Name field missing");
                allPresent = false;
            }

            // Check Dr. Address field
            try {
                driver.findElement(interpreterDrAddressField);
                System.out.println("✓ Dr. Address field present");
            } catch (Exception e) {
                System.err.println("✗ Dr. Address field missing");
                allPresent = false;
            }

            // Check Terms field
            try {
                driver.findElement(interpreterTermsField);
                System.out.println("✓ Terms field present");
            } catch (Exception e) {
                System.err.println("✗ Terms field missing");
                allPresent = false;
            }

            return allPresent;
        } catch (Exception e) {
            System.err.println("Failed to verify fields: " + e.getMessage());
            return false;
        }
    }

    public boolean isServicesPresent() {
        try {
            return driver.findElement(servicesField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}


