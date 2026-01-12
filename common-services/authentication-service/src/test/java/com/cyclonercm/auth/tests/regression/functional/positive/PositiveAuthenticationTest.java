package com.cyclonercm.auth.tests.regression.functional.positive;

import com.cyclonercm.auth.base.BaseTest;
import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;

import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class PositiveAuthenticationTest extends BaseTest {

    private AuthenticationPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new AuthenticationPage(driver);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 30);

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

    }

    @Test(priority = 1, description = "SMOKE_AU_001 - Verify user can successfully login with valid User Id and Password")
    public void SMOKE_AU_001() {

        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
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

        loginPage.enterUserIDForgot(TestDataProperties.get("validUserId1"));
        loginPage.clickSubmitButton();

        WaitUtils.waitForVisibility(driver, LocatorConstants.ForgotPasswordValidationToast, 10);

        try {
            String toastMessage = loginPage.isDisplayForgotPasswordUserIDValidationMessageText();
            System.out.println("Toast Message: " + toastMessage);
            Assert.assertTrue(toastMessage.contains("Password Reset Successfully"), "Password reset toast message not displayed correctly.");
            test.pass("Password reset successful with valid User ID: " + toastMessage);
        } catch (AssertionError e) {
            test.fail("Password reset failed with valid User ID.");
            throw e;
        }
    }


    @Test(priority = 3, description = "AUTH_POS_003 - Verify password visibility eye functionality")
    public void AUTH_POS_003() {
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        WaitUtils.sleep(1000);

        try {
            loginPage.togglePasswordVisibility();
            WaitUtils.sleep(1000);
            test.pass("Password visibility toggled successfully - password is now visible.");
        } catch (Exception e) {
            test.fail("Failed to toggle password visibility.");
            throw e;
        }

        try {
            loginPage.togglePasswordVisibility();
            WaitUtils.sleep(1000);
            test.pass("Password visibility toggled back - password is masked again.");
        } catch (Exception e) {
            test.fail("Failed to toggle password visibility back to masked.");
            throw e;
        }
    }

    @Test(priority = 4, description = "AUTH_POS_005 - Verify navigation back from password reset screen")
    public void AUTH_POS_004() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        try {
            String headerText = loginPage.getPasswordResetLabelText();
            Assert.assertTrue(headerText.contains("Password Reset"), "Not navigated to password reset screen.");
            test.pass("Navigated to password reset screen successfully.");
        } catch (AssertionError e) {
            test.fail("Failed to navigate to password reset screen.");
            throw e;
        }

        loginPage.clickBackButton();
        WaitUtils.sleep(2000);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);

        try {
            Assert.assertTrue(loginPage.getSystemLabelText().length() > 0, "Not returned to login screen.");
            test.pass("Successfully navigated back to login screen.");
        } catch (AssertionError e) {
            test.fail("Failed to navigate back to login screen.");
            throw e;
        }
    }

    @Test(priority = 5, description = "AUTH_POS_005 - Verify version information display")
    public void AUTH_POS_005() {
        String actualVersionText = loginPage.getVersionText();
        String expectedVersionText = TestDataProperties.get("buildNumber");

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
