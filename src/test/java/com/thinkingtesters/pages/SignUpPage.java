package com.thinkingtesters.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends BasePage {

    private static final By FIRST_NAME_FIELD = By.id("firstName");
    private static final By LAST_NAME_FIELD = By.id("lastName");
    private static final By EMAIL_FIELD = By.id("email");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By SUBMIT_BUTTON = By.id("submit");
    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By ERROR_MESSAGE = By.id("error");

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(css = "a[href='/']")
    private WebElement cancelButton;

    @FindBy(css = ".alert.alert-danger")
    private WebElement errorMessage;

    public SignUpPage() {
        super();
    }

    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        sendKeys(FIRST_NAME_FIELD, firstName);
    }

    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        sendKeys(LAST_NAME_FIELD, lastName);
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

    public void clickCancel() {
        logger.info("Clicking cancel button");
        click(CANCEL_BUTTON);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    public void fillSignUpForm(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
    }
}
