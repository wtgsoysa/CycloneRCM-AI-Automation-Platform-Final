package com.cyclonercm.auth.tests.smoke;

import com.cyclonercm.auth.base.BaseTest;
import com.cyclonercm.auth.base.SmokeBaseTest;
import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.utils.*;
import com.cyclonercm.utils.SmokeTestDataProperties;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AuthenticationTest extends SmokeBaseTest {

    private AuthenticationPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 30);

        String actualSystemLabelText = loginPage.getSystemLabelText();
        String expectedSystemLabelText = SmokeTestDataProperties.get("systemLabel");

        String actualSystemVersionText = loginPage.getVersionText();
        String expectedSystemVersionText = SmokeTestDataProperties.get("buildNumber");

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

    }

    @Test(priority = 1, description = "SMOKE_AU_001 - Verify user can successfully login with valid User Id and Password")
    public void SMOKE_AU_001() {

        loginPage.enterUsername(SmokeTestDataProperties.get("validUserId"));
        loginPage.enterPassword(SmokeTestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();

        WaitUtils.sleep(10000);

        try{
            Assert.assertTrue(loginPage.isDisplayDashboardSystemLabelDisplayed(), "Dashboard successfully displayed after login.");
            test.pass("Successfully navigated to dashboard after valid login.");
        } catch (AssertionError e) {
            test.fail("Failed to navigate to dashboard with valid login.");
            throw e;
        }
    }

    @Test(priority = 2, description = "AUTH_POS_002 - Verify password reset with valid User ID")
    public void AUTH_POS_002() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        loginPage.enterUserIDForgot(SmokeTestDataProperties.get("validUserId1"));
        loginPage.clickSubmitButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.ForgotPasswordValidationToast, 10);

        try {
            String toastMessage = loginPage.isDisplayForgotPasswordUserIDValidationMessageText();
            System.out.println("Toast Message: " + toastMessage);
            Assert.assertTrue(toastMessage.contains("Temporary password has been sent to dilan@123.com"), "Password reset toast message not displayed correctly.");
            test.pass("Password reset successful with valid User ID: " + toastMessage);
        } catch (AssertionError e) {
            test.fail("Password reset failed with valid User ID.");
            throw e;
        }
    }

    @Test(priority = 3, description = "AUTH_POS_003 - Verify version information display")
    public void AUTH_POS_005() {
        String actualVersionText = loginPage.getVersionText();
        String expectedVersionText = SmokeTestDataProperties.get("buildNumber");

        try {
            Assert.assertEquals(actualVersionText, expectedVersionText, "Version text does not match.");
            test.pass("Version information verified: " + actualVersionText);
        } catch (AssertionError e) {
            test.fail("Version text mismatch. Expected: " + expectedVersionText + ", Found: " + actualVersionText);
            throw e;
        }

        try {
            Assert.assertTrue(actualVersionText.length() > 0, "Version text is not displayed.");
            test.pass("Version text is displayed and readable.");
        } catch (AssertionError e) {
            test.fail("Version text is not displayed.");
            throw e;
        }
    }
}
