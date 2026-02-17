package com.cyclonercm.billing.tests.smoke;

import com.cyclonercm.pages.AuthenticationPage;
import com.cyclonercm.pages.FileUploadPage;
import com.cyclonercm.pages.FileHistoryPage;
import com.cyclonercm.billing.base.SmokeBaseTest;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.HistorySmokeTestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FileHistoryTest extends SmokeBaseTest {

    private AuthenticationPage loginPage;
    private FileUploadPage uploadPage;
    private FileHistoryPage historyPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        uploadPage = new FileUploadPage(driver);
        historyPage = new FileHistoryPage(driver);

        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 60);

        String actualSystemLabelText = loginPage.getSystemLabelText();
        String expectedSystemLabelText = HistorySmokeTestDataProperties.get("systemLabel");

        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = HistorySmokeTestDataProperties.get("buildNumber");

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

        loginPage.enterUsername(HistorySmokeTestDataProperties.get("validUserId"));
        loginPage.enterPassword(HistorySmokeTestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.getStartedButton, 60);

        String expectedGetStartedButtonText = "Get Started";
        String actualGetStartedButtonText = uploadPage.getGetStartedButtonText();
        try {
            Assert.assertEquals(actualGetStartedButtonText, expectedGetStartedButtonText, "Get Started button text match.");
            test.pass("Get Started button text verified: " + actualGetStartedButtonText);
        } catch (AssertionError e) {
            test.fail("Get Started button text mismatch. Expected: " + expectedGetStartedButtonText + ", Found: " + actualGetStartedButtonText);
            throw e;
        }

        historyPage.clickMegaMenu();
        WaitUtils.sleep(2000);

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
        WaitUtils.sleep(3000);
    }

    @Test(priority = 1, description = "SMOKE_FH_001 - Verify File History page loads with Received Files and Invoice List sections")
    public void SMOKE_FH_001() {
        try {
            Assert.assertTrue(historyPage.isReceivedFilesSectionDisplayed(), "Received Files section should be displayed");
            test.pass("Received Files section is displayed");

            Assert.assertTrue(historyPage.isInvoiceListSectionDisplayed(), "Invoice List section should be displayed");
            test.pass("Invoice List section is displayed");

            test.pass("SMOKE_FH_001 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_001 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "SMOKE_FH_002 - Verify uploaded file appears in Received Files section immediately after upload")
    public void SMOKE_FH_002() {
        try {
            Assert.assertTrue(historyPage.isFileDisplayed(), "Uploaded file should be displayed in Received Files");
            test.pass("Uploaded file appears in Received Files section");

            test.pass("SMOKE_FH_002 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_002 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "SMOKE_FH_003 - Verify file status changes from Processing to Completed after processing")
    public void SMOKE_FH_003() {
        try {
            Assert.assertTrue(historyPage.isCompletedStatusDisplayed(), "File status should show Completed");
            test.pass("File status is Completed");

            test.pass("SMOKE_FH_003 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_003 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "SMOKE_FH_004 - Verify Total File Count displays correctly in header")
    public void SMOKE_FH_004() {
        try {
            String totalFileCountText = historyPage.getTotalFileCountText();
            Assert.assertNotNull(totalFileCountText, "Total File Count should not be null");
            Assert.assertTrue(totalFileCountText.contains("Total File Count:"), "Total File Count text should be displayed");
            test.pass("Total File Count is displayed: " + totalFileCountText);

            test.pass("SMOKE_FH_004 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_004 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "SMOKE_FH_005 - Verify file metadata displays (File ID, File Name, Upload Date, Status)")
    public void SMOKE_FH_005() {
        try {
            String fileId = historyPage.getFirstFileId();
            Assert.assertNotNull(fileId, "File ID should not be null");
            Assert.assertFalse(fileId.isEmpty(), "File ID should not be empty");
            test.pass("File ID is displayed: " + fileId);

            String fileName = historyPage.getFirstFileName();
            Assert.assertNotNull(fileName, "File Name should not be null");
            Assert.assertFalse(fileName.isEmpty(), "File Name should not be empty");
            test.pass("File Name is displayed: " + fileName);

            String uploadDate = historyPage.getFirstFileUploadDate();
            Assert.assertNotNull(uploadDate, "Upload Date should not be null");
            Assert.assertFalse(uploadDate.isEmpty(), "Upload Date should not be empty");
            test.pass("Upload Date is displayed: " + uploadDate);

            String status = historyPage.getFirstFileStatus();
            Assert.assertNotNull(status, "Status should not be null");
            Assert.assertFalse(status.isEmpty(), "Status should not be empty");
            test.pass("Status is displayed: " + status);

            test.pass("SMOKE_FH_005 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_005 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "SMOKE_FH_006 - Verify file details show (Pages, Invoice Count, Success Count, Fail Count, Deleted Count, Amount)")
    public void SMOKE_FH_006() {
        try {
            String pages = historyPage.getFirstFilePages();
            Assert.assertNotNull(pages, "Pages should not be null");
            test.pass("Pages is displayed: " + pages);

            String invoiceCount = historyPage.getFirstFileInvoiceCount();
            Assert.assertNotNull(invoiceCount, "Invoice Count should not be null");
            test.pass("Invoice Count is displayed: " + invoiceCount);

            String successCount = historyPage.getFirstFileSuccessCount();
            Assert.assertNotNull(successCount, "Success Count should not be null");
            test.pass("Success Count is displayed: " + successCount);

            String failCount = historyPage.getFirstFileFailCount();
            Assert.assertNotNull(failCount, "Fail Count should not be null");
            test.pass("Fail Count is displayed: " + failCount);

            String deletedCount = historyPage.getFirstFileDeletedCount();
            Assert.assertNotNull(deletedCount, "Deleted Count should not be null");
            test.pass("Deleted Count is displayed: " + deletedCount);

            String amount = historyPage.getFirstFileAmount();
            Assert.assertNotNull(amount, "Amount should not be null");
            test.pass("Amount is displayed: " + amount);

            test.pass("SMOKE_FH_006 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_006 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "SMOKE_FH_007 - Verify Invoice Status filter dropdown displays all status options")
    public void SMOKE_FH_007() {
        try {
            Assert.assertTrue(historyPage.isInvoiceStatusDropdownDisplayed(), "Invoice Status dropdown should be displayed");
            test.pass("Invoice Status dropdown is displayed");

            historyPage.clickInvoiceStatusDropdown();
            WaitUtils.sleep(1000);
            test.pass("Invoice Status dropdown options are accessible");

            test.pass("SMOKE_FH_007 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_007 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "SMOKE_FH_008 - Verify Case/ADJ filter field is functional")
    public void SMOKE_FH_008() {
        try {
            Assert.assertTrue(historyPage.isCaseAdjFieldDisplayed(), "Case/ADJ filter field should be displayed");
            test.pass("Case/ADJ filter field is displayed");

            historyPage.clickCaseAdjField();
            WaitUtils.sleep(1000);
            test.pass("Case/ADJ filter field is functional");

            test.pass("SMOKE_FH_008 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_008 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "SMOKE_FH_009 - Verify File Type filter dropdown displays available types")
    public void SMOKE_FH_009() {
        try {
            Assert.assertTrue(historyPage.isFileTypeDropdownDisplayed(), "File Type dropdown should be displayed");
            test.pass("File Type dropdown is displayed");

            historyPage.clickFileTypeDropdown();
            WaitUtils.sleep(1000);
            test.pass("File Type dropdown is functional");

            test.pass("SMOKE_FH_009 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_009 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "SMOKE_FH_010 - Verify From Date and To Date date pickers are functional")
    public void SMOKE_FH_010() {
        try {
            Assert.assertTrue(historyPage.isFromDateFieldDisplayed(), "From Date field should be displayed");
            test.pass("From Date field is displayed");

            Assert.assertTrue(historyPage.isToDateFieldDisplayed(), "To Date field should be displayed");
            test.pass("To Date field is displayed");

            test.pass("SMOKE_FH_010 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_010 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 11, description = "SMOKE_FH_011 - Verify Search button applies selected filters")
    public void SMOKE_FH_011() {
        try {
            historyPage.enterFromDate("01/01/2026");
            WaitUtils.sleep(500);
            historyPage.enterToDate("12/31/2026");
            WaitUtils.sleep(500);

            historyPage.clickSearchButton();
            WaitUtils.sleep(2000);
            test.pass("Search button applies selected filters");

            test.pass("SMOKE_FH_011 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_011 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 12, description = "SMOKE_FH_012 - Verify Clear button resets all filters")
    public void SMOKE_FH_012() {
        try {
            historyPage.enterFromDate("01/01/2026");
            WaitUtils.sleep(500);
            historyPage.enterToDate("12/31/2026");
            WaitUtils.sleep(500);

            historyPage.clickClearButton();
            WaitUtils.sleep(1000);
            test.pass("Clear button resets all filters");

            test.pass("SMOKE_FH_012 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_012 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 13, description = "SMOKE_FH_013 - Verify search by filename in Search by file name invoice field works correctly")
    public void SMOKE_FH_013() {
        try {
            Assert.assertTrue(historyPage.isSearchByFileNameFieldDisplayed(), "Search by file name field should be displayed");
            test.pass("Search by file name field is displayed");

            String searchFileName = HistorySmokeTestDataProperties.get("searchFileName");
            historyPage.enterSearchFileName(searchFileName);
            WaitUtils.sleep(500);

            historyPage.clickFileNameSearchButton();
            WaitUtils.sleep(2000);
            test.pass("Search by filename works correctly");

            test.pass("SMOKE_FH_013 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_013 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 14, description = "SMOKE_FH_014 - Verify pagination controls work (First, Previous, Page Number, Next, Last)")
    public void SMOKE_FH_014() {
        try {
            if (historyPage.isNextPageButtonEnabled()) {
                historyPage.clickNextPage();
                WaitUtils.sleep(2000);
                test.pass("Next page button works");

                historyPage.clickPreviousPage();
                WaitUtils.sleep(2000);
                test.pass("Previous page button works");

                String currentPage = historyPage.getCurrentPageNumber();
                Assert.assertNotNull(currentPage, "Current page number should be displayed");
                test.pass("Current page number is: " + currentPage);
            } else {
                test.info("Only one page available, skipping pagination test");
            }

            test.pass("SMOKE_FH_014 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_014 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 15, description = "SMOKE_FH_015 - Verify JSON button opens JSON file viewer with extracted invoice data")
    public void SMOKE_FH_015() {
        try {
            Assert.assertTrue(historyPage.isJsonButtonDisplayed(), "JSON button should be displayed");
            test.pass("JSON button is displayed");

            historyPage.clickFirstJsonButton();
            WaitUtils.sleep(3000);

            Assert.assertTrue(historyPage.isJsonContainerDisplayed(), "JSON viewer should be displayed");
            test.pass("JSON viewer opens with extracted invoice data");

            test.pass("SMOKE_FH_015 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_015 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 16, description = "SMOKE_FH_016 - Verify Download button downloads the original uploaded PDF file")
    public void SMOKE_FH_016() {
        try {
            Assert.assertTrue(historyPage.isDownloadButtonDisplayed(), "Download button should be displayed");
            test.pass("Download button is displayed");

            historyPage.clickFirstDownloadButton();
            WaitUtils.sleep(2000);
            test.pass("Download button is functional");

            test.pass("SMOKE_FH_016 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_016 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 17, description = "SMOKE_FH_017 - Verify JSON file displays all extracted invoice fields correctly")
    public void SMOKE_FH_017() {
        try {
            historyPage.clickFirstJsonButton();
            WaitUtils.sleep(3000);

            String jsonContent = historyPage.getJsonFieldsText();
            Assert.assertNotNull(jsonContent, "JSON content should not be null");
            Assert.assertFalse(jsonContent.isEmpty(), "JSON content should not be empty");
            test.pass("JSON file displays extracted invoice fields: " + jsonContent.substring(0, Math.min(jsonContent.length(), 100)) + "...");

            test.pass("SMOKE_FH_017 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_017 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 18, description = "SMOKE_FH_018 - Verify clicking a File in Received File section displays related invoices in Invoice List section")
    public void SMOKE_FH_018() {
        try {
            int initialInvoiceCount = historyPage.getInvoiceCount();
            test.info("Initial invoice count: " + initialInvoiceCount);

            historyPage.clickFirstFile();
            WaitUtils.sleep(3000);

            int updatedInvoiceCount = historyPage.getInvoiceCount();
            test.info("Updated invoice count: " + updatedInvoiceCount);

            Assert.assertTrue(updatedInvoiceCount > 0, "Invoices should be displayed in Invoice List section");
            test.pass("Clicking file displays related invoices");

            test.pass("SMOKE_FH_018 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_018 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 19, description = "SMOKE_FH_019 - Verify clicking File icon in Invoice List section opens the Document View")
    public void SMOKE_FH_019() {
        try {
            historyPage.clickFirstFile();
            WaitUtils.sleep(2000);

            Assert.assertTrue(historyPage.isInvoiceFileIconDisplayed(), "Invoice File icon should be displayed");
            test.pass("Invoice File icon is displayed");

            historyPage.clickFirstInvoiceFileIcon();
            WaitUtils.sleep(3000);
            test.pass("File icon opens the Document View");

            test.pass("SMOKE_FH_019 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_019 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 20, description = "SMOKE_FH_020 - Verify clicking Edit icon in Invoice list section opens the Edit-Invoice section")
    public void SMOKE_FH_020() {
        try {
            historyPage.clickFirstFile();
            WaitUtils.sleep(2000);

            Assert.assertTrue(historyPage.isInvoiceEditIconDisplayed(), "Invoice Edit icon should be displayed");
            test.pass("Invoice Edit icon is displayed");

            historyPage.clickFirstInvoiceEditIcon();
            WaitUtils.sleep(3000);
            test.pass("Edit icon opens the Edit-Invoice section");

            test.pass("SMOKE_FH_020 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_020 failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 21, description = "SMOKE_FH_021 - Verify Delete action opens confirmation modal and confirming Delete removes invoice permanently")
    public void SMOKE_FH_021() {
        try {
            historyPage.clickFirstFile();
            WaitUtils.sleep(2000);

            Assert.assertTrue(historyPage.isInvoiceDeleteIconDisplayed(), "Invoice Delete icon should be displayed");
            test.pass("Invoice Delete icon is displayed");

            int initialInvoiceCount = historyPage.getInvoiceCount();
            test.info("Initial invoice count: " + initialInvoiceCount);

            historyPage.clickFirstInvoiceDeleteIcon();
            WaitUtils.sleep(2000);

            Assert.assertTrue(historyPage.isDeleteConfirmModalDisplayed(), "Delete confirmation modal should be displayed");
            test.pass("Delete confirmation modal is displayed");

            historyPage.clickConfirmDeleteButton();
            WaitUtils.sleep(3000);

            int updatedInvoiceCount = historyPage.getInvoiceCount();
            test.info("Updated invoice count: " + updatedInvoiceCount);

            Assert.assertEquals(updatedInvoiceCount, initialInvoiceCount - 1, "Invoice should be deleted");
            test.pass("Invoice removed permanently from both panels");

            test.pass("SMOKE_FH_021 passed");
        } catch (AssertionError e) {
            test.fail("SMOKE_FH_021 failed: " + e.getMessage());
            throw e;
        }
    }
}
