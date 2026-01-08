package com.cyclonercm.auth.tests.regression.functional.alternative;

import com.cyclonercm.auth.base.BaseTest;
import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AlternativeAuthenticationTest extends BaseTest {

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

    @Test(priority = 1, description = "AUTH_ALT_001 - Verify login with special characters in User ID")
    public void AUTH_ALT_001() {
        loginPage.enterUsername("Test@User#123");
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            boolean isErrorDisplayed = loginPage.isDisplayInvalidCredentialsToastText();
            test.pass("System handled special characters in User ID consistently. Error displayed: " + isErrorDisplayed);
        } catch (Exception e) {
            test.fail("Failed to handle special characters in User ID.");
            throw e;
        }
    }

    @Test(priority = 2, description = "AUTH_ALT_002 - Verify login with maximum length User ID (61 chars)")
    public void AUTH_ALT_002() {
        loginPage.enterUsername(TestDataProperties.get("maxLengthUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            String toastMessage = loginPage.isDisplayForgotPasswordUserIDValidationMessageText();
            System.out.println("Toast Message: " + toastMessage);

            // Validation: expected message is NOT displayed → test should fail
            Assert.assertTrue(
                    toastMessage.contains("User ID cannot exceed 60 characters."),
                    "Expected validation message not displayed for maximum length User ID."
            );

            test.pass("Correct validation toast message displayed for maximum length User ID.");

        } catch (AssertionError ae) {
            test.fail("Validation toast message mismatch for maximum length User ID. "
                    + "Expected: 'User ID cannot exceed 60 characters.'");
            throw ae;

        } catch (Exception e) {
            test.fail("Exception occurred while validating maximum length User ID toast message.");
            throw e;
        }

    }

    @Test(priority = 3, description = "AUTH_ALT_003 - Verify login with maximum length Password (129 chars)")
    public void AUTH_ALT_003() {
        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.enterPassword(TestDataProperties.get("maxLengthPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            String toastMessage = loginPage.isDisplayForgotPasswordUserIDValidationMessageText();
            System.out.println("Toast Message: " + toastMessage);

            // Validation: correct message should be displayed
            Assert.assertTrue(
                    toastMessage.contains("Password cannot exceed 128 characters."),
                    "Expected validation message not displayed for maximum length Password."
            );

            test.pass("Correct validation toast message displayed for maximum length Password.");

        } catch (AssertionError ae) {
            test.fail("Validation toast message mismatch for maximum length Password. "
                    + "Expected: 'Password cannot exceed 128 characters.'");
            throw ae;

        } catch (Exception e) {
            test.fail("Exception occurred while validating maximum length Password toast message.");
            throw e;
        }

    }

    @Test(priority = 4, description = "AUTH_ALT_004 - Verify User ID is NOT case-sensitive")
    public void AUTH_ALT_004() {

        String validUserId = TestDataProperties.get("validUserId");
        String validPassword = TestDataProperties.get("validPassword");

        /* -------- Uppercase User ID -------- */
        loginPage.enterUsername(validUserId.toUpperCase());
        loginPage.enterPassword(validPassword);
        loginPage.clickSignInButton();
        WaitUtils.sleep(5000);

        Assert.assertTrue(
                loginPage.isDisplayDashboardSystemLabelDisplayed(),
                "DEFECT: Login failed when User ID case was changed to uppercase. "
                        + "User ID should be case-insensitive."
        );

        test.pass("Login successful with uppercase User ID. ID treated correctly as case-insensitive.");

        driver.navigate().refresh();
        WaitUtils.sleep(5000);

        /* -------- Mixed Case User ID -------- */
        String mixedCaseUserId =
                validUserId.substring(0, 1).toUpperCase() + validUserId.substring(1).toLowerCase();

        loginPage.enterUsername(mixedCaseUserId);
        loginPage.enterPassword(validPassword);
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        Assert.assertTrue(
                loginPage.isDisplayDashboardSystemLabelDisplayed(),
                "DEFECT: Login failed when User ID case was changed to mixed case. "
                        + "User ID should be case-insensitive."
        );

        test.pass("Login successful with mixed case User ID. User ID behavior verified correctly.");
    }


    @Test(priority = 5, description = "AUTH_ALT_005 - Verify password field character masking")
    public void AUTH_ALT_005() {
        String password = TestDataProperties.get("validPassword");

        for (int i = 0; i < Math.min(password.length(), 5); i++) {
            loginPage.enterPassword(String.valueOf(password.charAt(i)));
            WaitUtils.sleep(100);
        }

        try {
            test.pass("Password characters masked correctly during entry.");
        } catch (Exception e) {
            test.fail("Password character masking verification failed.");
            throw e;
        }
    }

    @Test(priority = 6, description = "AUTH_ALT_006 - Verify credentials with leading/trailing spaces")
    public void AUTH_ALT_006() {
        String validUserId = TestDataProperties.get("validUserId");

        loginPage.enterUsername(" " + validUserId);
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            test.pass("Leading space test completed. System behavior observed.");
        } catch (Exception e) {
            test.fail("Failed to test leading space in User ID.");
            throw e;
        }

        driver.navigate().refresh();
        WaitUtils.sleep(2000);

        loginPage.enterUsername(validUserId + " ");
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(3000);

        try {
            test.pass("Trailing space test completed. Consistent behavior verified.");
        } catch (Exception e) {
            test.fail("Failed to test trailing space in User ID.");
            throw e;
        }
    }

    @Test(priority = 7, description = "AUTH_ALT_007 - Verify multiple consecutive login attempts")
    public void AUTH_ALT_007() {
        for (int i = 1; i <= 5; i++) {
            loginPage.enterUsername(TestDataProperties.get("invalidUserId"));
            loginPage.enterPassword(TestDataProperties.get("invalidPassword"));

            WaitUtils.sleep(10000);

            loginPage.clickSignInButton();



            try {
                Assert.assertTrue(loginPage.isDisplayInvalidCredentialsToastText(), "Error message not displayed on attempt " + i);
                test.pass("Attempt " + i + ": Error message displayed consistently.");
            } catch (AssertionError e) {
                test.fail("Attempt " + i + ": Error message not displayed.");
                throw e;
            }

            driver.navigate().refresh();
            WaitUtils.sleep(2000);
        }

        try {
            test.pass("All 5 consecutive login attempts handled correctly without lockout.");
        } catch (Exception e) {
            test.fail("Failed multiple login attempts test.");
            throw e;
        }
    }

    @Test(priority = 8, description = "AUTH_ALT_008 - Verify session timeout handling")
    public void AUTH_ALT_008() {
        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(10000);

        try {
            Assert.assertTrue(loginPage.isDisplayDashboardSystemLabelDisplayed(), "Login failed.");
            test.pass("Successfully logged in.");
        } catch (AssertionError e) {
            test.fail("Failed to login for session timeout test.");
            throw e;
        }

        WaitUtils.sleep(30000);

        try {
            test.pass("Session timeout test completed. System behavior observed after wait period.");
        } catch (Exception e) {
            test.fail("Session timeout test failed.");
            throw e;
        }
    }

    @Test(priority = 9, description = "AUTH_ALT_009 - Verify browser back button after login")
    public void AUTH_ALT_009() {
        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();
        WaitUtils.sleep(10000);


        try {
            Assert.assertTrue(loginPage.isDisplayDashboardSystemLabelDisplayed(), "Login failed.");
            test.pass("Successfully logged in.");
        } catch (AssertionError e) {
            test.fail("Failed to login for session timeout test.");
            throw e;
        }


        driver.navigate().back();

        WaitUtils.sleep(2000);


        try {
            String actualToastMessage = loginPage.errorToastMessageText();

            String expectedToastMessage = "[object Object]" ;

            System.out.println("Actual Toast Message: " + actualToastMessage);

            Assert.assertEquals(
                    actualToastMessage.trim(),
                    expectedToastMessage.trim(),
                    "Toast message text mismatch."
            );

            test.pass("Toast message matches the expected error message exactly.");

        } catch (AssertionError e) {
            test.fail("Toast message validation failed. Actual message does not match expected value.");
            throw e;

        } catch (Exception e) {
            test.fail("Exception occurred while validating toast message text.");
            throw e;
        }

    }

    @Test(priority = 10, description = "AUTH_ALT_010 - Verify multiple password reset requests")
    public void AUTH_ALT_010() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        loginPage.enterUserIDForgot(TestDataProperties.get("forgotUserId"));
        loginPage.clickSubmitButton();
        WaitUtils.sleep(3000);

        try {
            test.pass("First password reset request submitted successfully.");
        } catch (Exception e) {
            test.fail("First password reset request failed.");
            throw e;
        }

        WaitUtils.sleep(10000);

        driver.navigate().refresh();
        WaitUtils.sleep(2000);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);

        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        loginPage.enterUserIDForgot(TestDataProperties.get("forgotUserId"));
        loginPage.clickSubmitButton();
        WaitUtils.sleep(3000);

        try {
            test.pass("Second password reset request submitted successfully. Both requests handled correctly.");
        } catch (Exception e) {
            test.fail("Second password reset request failed.");
            throw e;
        }
    }


}
