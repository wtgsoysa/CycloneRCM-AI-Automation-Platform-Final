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
}
