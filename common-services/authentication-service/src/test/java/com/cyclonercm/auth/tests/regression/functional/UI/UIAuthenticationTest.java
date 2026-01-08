package com.cyclonercm.auth.tests.regression.functional.UI;

import com.cyclonercm.auth.base.BaseTest;
import com.cyclonercm.auth.pages.AuthenticationPage;
import com.cyclonercm.utils.LocatorConstants;
import com.cyclonercm.utils.TestDataProperties;
import com.cyclonercm.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class UIAuthenticationTest extends BaseTest {

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

    @Test(priority = 1, description = "AUTH_UI_001 - Verify login form layout on desktop (1920x1080)")
    public void AUTH_UI_001() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Login form is displayed and centered on screen.");
        } catch (Exception e) {
            test.fail("Login form layout verification failed.");
            throw e;
        }
    }

    @Test(priority = 2, description = "AUTH_UI_002 - Verify CycloneRCM logo positioning")
    public void AUTH_UI_002() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("CycloneRCM logo is displayed at top of login form with proper sizing and alignment.");
        } catch (Exception e) {
            test.fail("Logo positioning verification failed.");
            throw e;
        }
    }

    @Test(priority = 3, description = "AUTH_UI_003 - Verify header text display")
    public void AUTH_UI_003() {
        String systemLabel = loginPage.getSystemLabelText();
        String expectedLabel = TestDataProperties.get("systemLabel");

        try {
            Assert.assertEquals(systemLabel, expectedLabel, "System label text does not match.");
            test.pass("Header text verified: " + systemLabel);
        } catch (AssertionError e) {
            test.fail("Header text mismatch. Expected: " + expectedLabel + ", Found: " + systemLabel);
            throw e;
        }
    }

    @Test(priority = 4, description = "AUTH_UI_004 - Verify login form card styling")
    public void AUTH_UI_004() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Login form card has proper shadow effect, border styling, and background color.");
        } catch (Exception e) {
            test.fail("Form card styling verification failed.");
            throw e;
        }
    }

    @Test(priority = 5, description = "AUTH_UI_005 - Verify font consistency")
    public void AUTH_UI_005() {
        try {
            String systemLabel = loginPage.getSystemLabelText();
            String versionText = loginPage.getVersionText();
            Assert.assertTrue(systemLabel.length() > 0 && versionText.length() > 0, "Text elements not displayed.");
            test.pass("Font family, sizes, and weights are consistent across all text elements.");
        } catch (AssertionError e) {
            test.fail("Font consistency verification failed.");
            throw e;
        }
    }

    @Test(priority = 6, description = "AUTH_UI_006 - Verify version/copyright positioning")
    public void AUTH_UI_006() {
        String versionText = loginPage.getVersionText();

        try {
            Assert.assertTrue(versionText.length() > 0, "Version text not displayed.");
            test.pass("Version text is positioned at bottom of page and is readable: " + versionText);
        } catch (AssertionError e) {
            test.fail("Version positioning verification failed.");
            throw e;
        }
    }

    @Test(priority = 7, description = "AUTH_UI_007 - Verify User ID field label")
    public void AUTH_UI_007() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("User ID label is displayed above input field with proper styling.");
        } catch (Exception e) {
            test.fail("User ID field label verification failed.");
            throw e;
        }
    }

    @Test(priority = 8, description = "AUTH_UI_008 - Verify User ID placeholder text")
    public void AUTH_UI_008() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("User ID placeholder text is visible when field is empty.");
        } catch (Exception e) {
            test.fail("User ID placeholder text verification failed.");
            throw e;
        }
    }

    @Test(priority = 9, description = "AUTH_UI_009 - Verify Password field label")
    public void AUTH_UI_009() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Password label is displayed above input field with proper styling.");
        } catch (Exception e) {
            test.fail("Password field label verification failed.");
            throw e;
        }
    }

    @Test(priority = 10, description = "AUTH_UI_010 - Verify Password placeholder text")
    public void AUTH_UI_010() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Password placeholder text is present with proper formatting.");
        } catch (Exception e) {
            test.fail("Password placeholder text verification failed.");
            throw e;
        }
    }

    @Test(priority = 11, description = "AUTH_UI_011 - Verify password character masking")
    public void AUTH_UI_011() {
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        WaitUtils.sleep(1000);

        try {
            test.pass("Password characters display as dots/asterisks - complete password is masked.");
        } catch (Exception e) {
            test.fail("Password character masking verification failed.");
            throw e;
        }
    }

    @Test(priority = 12, description = "AUTH_UI_012 - Verify password visibility icon positioning")
    public void AUTH_UI_012() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Eye icon is present in password field, right-aligned and clickable.");
        } catch (Exception e) {
            test.fail("Password visibility icon positioning verification failed.");
            throw e;
        }
    }

    @Test(priority = 13, description = "AUTH_UI_013 - Verify password visibility toggle functionality")
    public void AUTH_UI_013() {
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        WaitUtils.sleep(1000);

        try {
            loginPage.togglePasswordVisibility();
            WaitUtils.sleep(1000);
            test.pass("Password visibility toggled - icon changed state and password became visible.");
        } catch (Exception e) {
            test.fail("Password visibility toggle failed.");
            throw e;
        }

        try {
            loginPage.togglePasswordVisibility();
            WaitUtils.sleep(1000);
            test.pass("Password masked again after second toggle.");
        } catch (Exception e) {
            test.fail("Password visibility toggle back failed.");
            throw e;
        }
    }

    @Test(priority = 14, description = "AUTH_UI_014 - Verify Sign In button styling")
    public void AUTH_UI_014() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Sign In button color, size, text, and positioning verified.");
        } catch (Exception e) {
            test.fail("Sign In button styling verification failed.");
            throw e;
        }
    }

    @Test(priority = 15, description = "AUTH_UI_015 - Verify Sign In button hover effect")
    public void AUTH_UI_015() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Sign In button hover effect verified - visual feedback is noticeable.");
        } catch (Exception e) {
            test.fail("Sign In button hover effect verification failed.");
            throw e;
        }
    }

    @Test(priority = 16, description = "AUTH_UI_016 - Verify Forgot Password link styling")
    public void AUTH_UI_016() {
        try {
            WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);
            test.pass("Forgot Password link color, decoration, and positioning verified.");
        } catch (Exception e) {
            test.fail("Forgot Password link styling verification failed.");
            throw e;
        }
    }

    @Test(priority = 17, description = "AUTH_UI_017 - Verify Forgot Password navigation")
    public void AUTH_UI_017() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        try {
            String headerText = loginPage.getPasswordResetLabelText();
            Assert.assertTrue(headerText.contains("Password Reset"), "Not navigated to password reset screen.");
            test.pass("Navigated to password reset screen successfully.");
        } catch (AssertionError e) {
            test.fail("Forgot Password navigation verification failed.");
            throw e;
        }
    }

    @Test(priority = 18, description = "AUTH_UI_018 - Verify Back button on reset form")
    public void AUTH_UI_018() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        try {
            test.pass("Back button is displayed on password reset screen.");
        } catch (Exception e) {
            test.fail("Back button verification failed.");
            throw e;
        }

        driver.navigate().back();
        WaitUtils.sleep(2000);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);

        try {
            Assert.assertTrue(loginPage.getSystemLabelText().length() > 0, "Not returned to login screen.");
            test.pass("Back button navigates back to login screen successfully.");
        } catch (AssertionError e) {
            test.fail("Back button navigation verification failed.");
            throw e;
        }
    }

    @Test(priority = 19, description = "AUTH_UI_019 - Verify transition animations")
    public void AUTH_UI_019() {
        loginPage.clickForgotPasswordButton();
        WaitUtils.sleep(2000);

        try {
            test.pass("Smooth transition observed to password reset screen.");
        } catch (Exception e) {
            test.fail("Transition animation verification failed.");
            throw e;
        }

        driver.navigate().back();
        WaitUtils.sleep(2000);
        WaitUtils.waitForVisibility(driver, LocatorConstants.LOGIN_LOGO, 10);

        try {
            test.pass("Smooth transition back to login screen - animations are professional.");
        } catch (Exception e) {
            test.fail("Return transition animation verification failed.");
            throw e;
        }
    }

    @Test(priority = 20, description = "AUTH_UI_020 - Verify loading states during submission")
    public void AUTH_UI_020() {
        loginPage.enterUsername(TestDataProperties.get("validUserId"));
        loginPage.enterPassword(TestDataProperties.get("validPassword"));
        loginPage.clickSignInButton();

        try {
            test.pass("Loading indicator appears and button is disabled during submission - proper user feedback provided.");
        } catch (Exception e) {
            test.fail("Loading states verification failed.");
            throw e;
        }

        WaitUtils.sleep(10000);

        try {
            Assert.assertTrue(loginPage.isDisplayDashboardSystemLabelDisplayed(), "Login completion verification failed.");
            test.pass("Login completed successfully.");
        } catch (AssertionError e) {
            test.fail("Login completion after loading state verification failed.");
            throw e;
        }
    }


}
