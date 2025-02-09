package com.thinkingtesters.steps;

import com.thinkingtesters.config.Configuration;
import com.thinkingtesters.driver.DriverManager;
import com.thinkingtesters.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class LoginSteps {
    private final LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        DriverManager.getDriver().get(Configuration.getInstance().getBaseUrl());
    }

    @When("I enter valid email {string}")
    public void iEnterValidEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("I enter valid password {string}")
    public void iEnterValidPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I enter login email {string}")
    public void iEnterEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("I enter login password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login submit button")
    public void iClickTheSubmitButton() {
        loginPage.clickSubmit();
    }

    @When("I click on the sign up link")
    public void iClickOnTheSignUpLink() {
        loginPage.clickSignUpLink();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assertions.assertThat(loginPage.isLoggedIn())
            .as("User should be logged in successfully")
            .isTrue();
    }

    @Then("I should see a login error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedError) {
        Assertions.assertThat(loginPage.isErrorMessageDisplayed())
            .as("Error message should be displayed")
            .isTrue();
        
        Assertions.assertThat(loginPage.getErrorMessage())
            .as("Error message should match expected message")
            .contains(expectedError);
    }

    @Then("I should be redirected to the sign up page")
    public void iShouldBeRedirectedToTheSignUpPage() {
        DriverManager.getWait().until(driver -> 
            driver.getCurrentUrl().contains("/addUser"));
    }
}
