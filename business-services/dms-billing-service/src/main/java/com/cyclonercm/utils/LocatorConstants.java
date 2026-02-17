package com.cyclonercm.utils;

import org.openqa.selenium.By;

public class LocatorConstants {

    public static final By LOGIN_LOGO = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[1]/label");
    public static final By DASHBOARD_LOGO = By.xpath("/html/body/ng-component/div/div/div[1]/div/div[1]/a/img[1]");
    public static final By LoginSystemLabel = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[1]/label");

    //-------- Login Page Locators ---------
    public static final By IncorrectToast = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem");
    public static final By ValidationToast = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");
    public static final By ResetPasswordLabel = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/div/div/div/form/div/div[2]/div[1]");
    public static final By ForgotPasswordValidationToast = By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");
    public static final By ForgotPasswordErrorValidationToast= By.xpath("/html/body/ng-component/div/div/aeliusmd-login/ui-message/p-toast[3]/div/p-toastitem/div/div/div/div");

    //-------- File Upload Locators ---------
    public static final By getStartedButton = By.xpath("//div[@class='clearfix system-color- topbar']//button[1]");
    public static final By UploadSuccessToast = By.xpath("/html/body/ui-message/p-toast[2]/div/p-toastitem/div/div/div/div/h4");
    public static final By UploadProgressToast = By.xpath("//p-toast[@position='top-right']//div[contains(@class,'ui-toast-message')]");

}
