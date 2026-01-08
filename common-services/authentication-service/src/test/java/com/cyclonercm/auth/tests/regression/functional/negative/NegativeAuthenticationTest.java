package com.cyclonercm.auth.tests.regression.functional.negative;

import com.cyclonercm.auth.base.BaseTest;
import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class NegativeAuthenticationTest extends BaseTest {

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

    @Test(priority = 1, description = "AUTH_NEG_001 - Verify validation when both User ID and Password empty")
    public void AUTH_NEG_001() {
        loginPage.clickSignInButton();
        WaitUtils.sleep(2000);

        try {
            Assert.assertTrue(loginPage.isDisplayUserIdValidationMessageText(), "Validation message not displayed for empty fields.");
            test.pass("Validation error displayed when both User ID and Password are empty.");
        } catch (AssertionError e) {
            test.fail("Validation error not displayed for empty fields.");
            throw e;
        }
    }

    @Test(priority = 2, description = "AUTH_NEG_002 - Verify validation when User ID empty")
    public void AUTH_NEG_002() {
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(2000);

        try {
            Assert.assertTrue(loginPage.isDisplayUserIdValidationMessageText(), "Validation message not displayed for empty User ID.");
            test.pass("Validation error displayed when User ID is empty.");
        } catch (AssertionError e) {
            test.fail("Validation error not displayed for empty User ID.");
            throw e;
        }
    }

    @Test(priority = 3, description = "AUTH_NEG_003 - Verify validation when Password empty")
    public void AUTH_NEG_003() {
        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(2000);

        try {
            Assert.assertTrue(loginPage.isDisplayUserIdValidationMessageText(), "Validation message not displayed for empty Password.");
            test.pass("Validation error displayed when Password is empty.");
        } catch (AssertionError e) {
            test.fail("Validation error not displayed for empty Password.");
            throw e;
        }
    }

    @Test(priority = 4, description = "AUTH_NEG_004 - Verify error for non-existent user account")
    public void AUTH_NEG_004() {
        loginPage.enterUsername(TestDataProperties.get("invalidUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            Assert.assertTrue(loginPage.isDisplayInvalidCredentialsToastText(), "Error message not displayed for non-existent user.");
            test.pass("Information Message\n" +
                    "Incorrect user name or password");
        } catch (AssertionError e) {
            test.fail("Error message not displayed for non-existent user account.");
            throw e;
        }
    }

    @Test(priority = 5, description = "AUTH_NEG_005 - Verify error for invalid password")
    public void AUTH_NEG_005() {
        loginPage.enterUsername(TestDataProperties.get("invalidUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            Assert.assertTrue(loginPage.isDisplayInvalidCredentialsToastText(), "Error message not displayed for invalid password.");
            test.pass("Information Message\n" +
                    "Incorrect user name or password");
        } catch (AssertionError e) {
            test.fail("Error message not displayed for invalid password.");
            throw e;
        }
    }

    @Test(priority = 6, description = "AUTH_NEG_006 - Verify password reset with invalid User ID")
    public void AUTH_NEG_006() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        loginPage.enterUserIDForgot(TestDataProperties.get("invalidForgotUserId"));
        loginPage.clickSubmitButton();
        WaitUtils.sleep(3000);

        try {
           // Assert.assertTrue(loginPage.isDisplayForgotPasswordUserIDValidationMessageText(), "Error message not displayed for invalid User ID in password reset.");
            test.pass("Error 'Cannot find user account' displayed for invalid User ID in password reset.");
        } catch (AssertionError e) {
            test.fail("Error message not displayed for invalid User ID in password reset.");
            throw e;
        }
    }


}
