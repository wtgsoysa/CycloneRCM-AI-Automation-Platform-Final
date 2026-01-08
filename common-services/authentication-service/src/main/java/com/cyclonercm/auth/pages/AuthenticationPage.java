package com.cyclonercm.auth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AuthenticationPage {
    private WebDriver driver;

    public AuthenticationPage(WebDriver driver) {
        this.driver = driver;
    }


    //-------- Locators ---------

    //----- Login Page -----
    private final By systemLabel = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[1]/label");
    private final By versionText = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[7]/div[1]/span");
    private final By userIdField = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[2]/div[2]/div/input");
    private final By passwordField = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[3]/div/div/p-password/div/input");
    private final By eyeButton = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[3]/div/div/p-password/div/i");
    private final By signInButton = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[4]/button");
    private final By forgotPasswordButton = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[6]/a");
    private final By passwordResetLabel = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[2]/div[1]");
    private final By passwordcopyrightText = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[7]/div[1]/span");
    private final By forgotUserIdField = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[2]/div[2]/div/input");
    private final By submitButton = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[5]/button[1]");
    private final By backButton = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[5]/button[2]");
    private final By logoutButton = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[4]/ul/li/div");



    //----- Toast Locators -----
    private final By invalidCredentialsToast = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem");
    private final By userIdValidationMessage = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");
    private final By passwordValidationMessage = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");
    private final By forgotPasswordUserIDValidationMessage= By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");
    private final By temporaryPasswordSendValidationMessage= By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div/div/p");
    private final By forgotPasswordUserIDErrorValidationMessage= By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");

    private final By errorToast = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[5]/div/p-toastitem[2]/div/div/div/div/div/p");

    //----- Dashboard Page -----
    private final By dashboardsystemLabel = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[1]/a/img[1]");




    //-------- Actions ---------
    public String getSystemLabelText() {
        return driver.findElement(systemLabel).getText().trim();
    }

    public String getVersionText() {
        return driver.findElement(versionText).getText().trim();
    }

    public void enterUsername(String username) {
        WebElement inputField = driver.findElement(userIdField);
        inputField.click();
        inputField.clear();
        inputField.sendKeys(username);

    }

    public void enterPassword(String password) {
        WebElement inputField = driver.findElement(passwordField);
        inputField.click();
        inputField.clear();
        inputField.sendKeys(password);

    }

    public void enterUserIDForgot(String userId) {
        WebElement inputField = driver.findElement(forgotUserIdField);
        inputField.click();
        inputField.clear();
        inputField.sendKeys(userId);

    }

    public void clickSubmitButton() {
        driver.findElement(submitButton).click();
    }

    public void clickBackButton() {
        driver.findElement(backButton).click();
    }



    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    public boolean isDisplayDashboardSystemLabelDisplayed() {
        return driver.findElement(dashboardsystemLabel).isDisplayed();
    }

    public String errorToastMessageText(){
        return driver.findElement(errorToast).getText().trim();
    }

    public void clickForgotPasswordButton() {
        driver.findElement(forgotPasswordButton).click();
    }

    public String getPasswordResetLabelText() {
        return driver.findElement(passwordResetLabel).getText();
    }

    public void getPasswordCopyrightText() {
        driver.findElement(passwordcopyrightText).getText().trim();
    }

    public Boolean isDisplayInvalidCredentialsToastText() {
        return (Boolean) driver.findElement(invalidCredentialsToast).isDisplayed();
    }

    public Boolean isDisplayUserIdValidationMessageText() {
        return (Boolean) driver.findElement(userIdValidationMessage).isDisplayed();
    }

    public Boolean isDisplayPasswordValidationMessageText() {
        return (Boolean) driver.findElement(passwordValidationMessage).isDisplayed();
    }

    public String isDisplayForgotPasswordUserIDValidationMessageText() {
        return driver.findElement(forgotPasswordUserIDValidationMessage).getText();
    }

    public String getForgotPasswordUser() {
        return driver.findElement(forgotPasswordUserIDErrorValidationMessage).getText();
    }

    public void togglePasswordVisibility(){
        driver.findElement(eyeButton).click();
    }



    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }


}
