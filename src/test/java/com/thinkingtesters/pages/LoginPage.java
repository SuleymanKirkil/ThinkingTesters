package com.thinkingtesters.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage {
    private static final By EMAIL_FIELD = By.id("email");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By SUBMIT_BUTTON = By.id("submit");
    private static final By SIGN_UP_LINK = By.id("signup");
    private static final By ERROR_MESSAGE = By.cssSelector(".error-message");
    private static final By LOGOUT_BUTTON = By.id("logout");
    private static final By ADD_CONTACT_BUTTON = By.id("add-contact");
    private static final By CONTACT_LIST = By.cssSelector(".contact-list");

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

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
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            return true;
        } catch (Exception e) {
            logger.error("Error message not displayed", e);
            return false;
        }
    }

    public String getErrorMessage() {
        logger.info("Getting error message");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            return getText(ERROR_MESSAGE).trim();
        } catch (Exception e) {
            logger.error("Failed to get error message", e);
            return "";
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }

    public boolean isLoggedIn() {
        logger.info("Checking if user is logged in");
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(CONTACT_LIST),
                ExpectedConditions.visibilityOfElementLocated(LOGOUT_BUTTON),
                ExpectedConditions.visibilityOfElementLocated(ADD_CONTACT_BUTTON)
            ));
            return true;
        } catch (Exception e) {
            logger.error("Failed to verify login status", e);
            return false;
        }
    }
}
