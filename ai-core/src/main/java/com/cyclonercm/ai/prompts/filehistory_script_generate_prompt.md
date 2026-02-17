Generate complete test scripts for SMOKE_FH_001	to SMOKE_FH_021

For this task you need to act a senior automation engineer

COST OPTIMIZATION NOTICE ⚠️

      This request is subject to API usage limits. Please provide a CONCISE, COMPLETE 
response in a SINGLE JSON object to minimize token usage while maintaining quality.

Cost Governance Rule (Mandatory):

1. AI usage for the File Upload Module is strictly limited to a maximum budget of $2
   The target cost for generating all test scripts is $1.5

CycloneRCM AI Automation Platform – Smoke Pack

Prompt Version: 1.0 (FINAL – LOCKED)
Date: January 09, 2026
Target Module: File Upload
Purpose: Selenium Java TestNG Test Script Generation
Model: claude-sonnet-4-20250514
Temperature: 0.1
Max Tokens: 4096

CRITICAL RESTRICTIONS (NON-NEGOTIABLE)

❌ NO self-healing logic

❌ NO AI agents or autonomous execution

❌ NO framework refactoring

❌ NO new dependencies

❌ NO explanations or markdown in output

❌ NO API keys, secrets, or credentials

❌ NO multiple classes per response

Violation of any rule invalidates the output.

MANDATORY FOLDER STRUCTURE (STRICT)

You MUST generate code ONLY inside the following paths.


Now Successfully generate the X paths and now It's time to code

X path related to file history :
E:\MedCube-USA\ProjectClaudMD\n8n\CycloneRCM-AI-Automation-Platform-Final\ai-core\src\main\java\com\cyclonercm\ai\xpaths\filehistory_xpath.json

Analysis deeply and start the Test Automation:

SMOKE_FH_001	Verify File History page loads with 'Received Files' and 'Invoice List' sections
SMOKE_FH_002	Verify uploaded file appears in 'Received Files' section immediately after upload
SMOKE_FH_003	Verify file status changes from 'Processing' to 'Completed' after processing
SMOKE_FH_004	Verify Total File Count displays correctly in header
SMOKE_FH_005	Verify file metadata displays (File ID, File Name, Upload Date, Status)
SMOKE_FH_006	Verify file details show (Pages, Invoice Count, Success Count, Fail Count, Deleted Count, Amount)
SMOKE_FH_007	Verify 'Invoice Status' filter dropdown displays all status options (All, Success, Fail, Manually Corrected, Duplicate, Processing)
SMOKE_FH_008	Verify 'Case/ADJ' filter field is functional
SMOKE_FH_009	Verify 'File Type' filter dropdown displays available types
SMOKE_FH_010	Verify 'From Date' and 'To Date' date pickers are functional
SMOKE_FH_011	Verify 'Search' button applies selected filters
SMOKE_FH_012	Verify 'Clear' button resets all filters
SMOKE_FH_013	Verify search by filename in 'Search by file name invoice' field works correctly
SMOKE_FH_014	Verify pagination controls work (First, Previous, Page Number, Next, Last)
SMOKE_FH_015	Verify 'JSON' button opens JSON file viewer with extracted invoice data
SMOKE_FH_016	Verify 'Download' button downloads the original uploaded PDF file
SMOKE_FH_017	Verify JSON file displays all extracted invoice fields correctly
SMOKE_FH_018	Verify Click a File in Recieved File section and that File related all invoices are display in the Invoice List section
SMOKE_FH_019	Verify clicking File icon in Invoice List section open the Document View
SMOKE_FH_020	Verify clicking Edit icon in Invoice list section open the Edit-Invoice section
SMOKE_FH_021	Verify Delete action opens confirmation modal with warning message and confirming Delete removes invoice permanently from both panels

Now analysis the all File History Test Cases and Make a Complete Page File and Test File
E:\MedCube-USA\ProjectClaudMD\n8n\CycloneRCM-AI-Automation-Platform-Final\business-services\dms-billing-service\src\test\java\com\cyclonercm\billing\tests\smoke\FileHistoryTest.java
E:\MedCube-USA\ProjectClaudMD\n8n\CycloneRCM-AI-Automation-Platform-Final\business-services\dms-billing-service\src\main\java\com\cyclonercm\pages\FileHistoryPage.java

Please use separate test data file -> E:\MedCube-USA\ProjectClaudMD\n8n\CycloneRCM-AI-Automation-Platform-Final\business-services\dms-billing-service\src\test\resources\historysmoketestdata.properties

User ID : Thanuga
Password : 123

Steps after login :

Navigate to the Mega Menu -> /html/body/ng-component/div/div/div[1]/div/div[2]/p-megamenu/div/ul/li/a

Click File History - Invoice option ->/html/body/ng-component/div/div/div[1]/div/div[2]/p-megamenu/div/ul/li/div/div/div[1]/ul/li[9]/a/span[1]

File History URL -> https://qa.cyclonercm.com/file-history-app/3

This is sample base file for test -> @BeforeMethod
public void setUp() {
loginPage = new LoginPage(driver);
uploadPage = new UploadPage(driver);
historyPage = new HistoryPage(driver);

    WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 60);

    String actualSystemLabelText = loginPage.getSystemLabelText();
    String expectedSystemLabelText = TestDataProperties.get("systemLabel");

    String actualSystemVersionText = loginPage.getVersionText();
    String expectedSystemVersionText = TestDataProperties.get("buildNumber");

    try {
        Assert.assertEquals(actualSystemLabelText, expectedSystemLabelText, "System label text does not match.");
        test.pass("System label text verified: " + actualSystemLabelText);
    } catch (AssertionError e) {
        test.fail("System label text mismatch. Expected: " + expectedSystemLabelText + ", Found: " + actualSystemLabelText);
        throw e;
    }

    try {
        Assert.assertEquals(actualSystemVersionText, expectedSystemVersionText, "System version text does not match.");
        test.pass("System version text verified: " + actualSystemVersionText);
    } catch (AssertionError e) {
        test.fail("System version text mismatch. Expected: " + expectedSystemVersionText + ", Found: " + actualSystemVersionText);
        throw e;
    }

    loginPage.enterUsername(TestDataProperties.get("validUserId"));
    loginPage.enterPassword(TestDataProperties.get("validPassword"));
    loginPage.clickSignInButton();

    //WaitUtils.sleep(5000);
    WaitUtils.waitForVisibility(driver, LocatorConstants.GetStartedButton, 60);

    String expectedGetStartedButtonText = "Get Started";
    String actualGetStartedButtonText = uploadPage.getStartedButtonText();
    try {
        Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
        test.pass("Get Started button text verified: " + actualGetStartedButtonText);
    } catch (AssertionError e) {
        test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
        throw e;
    }

    // Navigate to File History
    historyPage.clickMegaMenu();

    String expectedMastersText = "Masters";
    String actualMastersText = historyPage.getMastersTabText();

    try {
        Assert.assertEquals(actualMastersText, expectedMastersText, "Masters tab text does not match.");
        test.pass("Masters tab text verified: " + actualMastersText);
    } catch (AssertionError e) {
        test.fail("Masters tab text mismatch. Expected: " + expectedMastersText + ", Found: " + actualMastersText);
        throw e;
    }

    historyPage.clickFileHistoryOption();








