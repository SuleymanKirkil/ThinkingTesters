package com.thinkingtesters.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private static final By EMAIL_FIELD = By.id("email");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By SUBMIT_BUTTON = By.id("submit");
    private static final By SIGN_UP_LINK = By.id("signup");
    private static final By ERROR_MESSAGE = By.cssSelector("#error");

    public LoginPage() {
        super();
    }

    public void enterEmail(String email) {
        logger.info("Entering email: {}", email);
        sendKeys(EMAIL_FIELD, email);
    }

    public void enterPassword(String password) {
        logger.info("Entering password: ****");
        sendKeys(PASSWORD_FIELD, password);
    }

    public void clickSubmit() {
        logger.info("Clicking submit button");
        click(SUBMIT_BUTTON);
    }

    public void clickSignUpLink() {
        logger.info("Clicking sign up link");
        click(SIGN_UP_LINK);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(ERROR_MESSAGE);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }
}
