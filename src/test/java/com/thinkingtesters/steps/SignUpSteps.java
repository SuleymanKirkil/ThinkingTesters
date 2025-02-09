package com.thinkingtesters.steps;

import com.thinkingtesters.apirequest.UserApiRequests;
import com.thinkingtesters.config.Configuration;
import com.thinkingtesters.driver.DriverManager;
import com.thinkingtesters.pages.SignUpPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.fail;

public class SignUpSteps {
    private static final Logger logger = LoggerFactory.getLogger(SignUpSteps.class);
    private final SignUpPage signUpPage;

    public SignUpSteps() {
        this.signUpPage = new SignUpPage();
    }

    @Given("I am on the sign up page")
    public void iAmOnTheSignUpPage() {
        DriverManager.getDriver().get(Configuration.getInstance().getBaseUrl() + "/addUser");
    }

    @When("I enter first name {string}")
    public void iEnterFirstName(String firstName) {
        signUpPage.enterFirstName(firstName);
    }

    @When("I enter last name {string}")
    public void iEnterLastName(String lastName) {
        signUpPage.enterLastName(lastName);
    }

    @When("I enter sign up email {string}")
    public void iEnterEmail(String email) {
        signUpPage.enterEmail(email);
    }

    @When("I enter sign up password {string}")
    public void iEnterPassword(String password) {
        signUpPage.enterPassword(password);
    }

    @When("I click the sign up submit button")
    public void iClickTheSubmitButton() {
        signUpPage.clickSubmit();
    }

    @When("I click the cancel button")
    public void iClickTheCancelButton() {
        signUpPage.clickCancel();
    }

    @Then("I should be registered successfully")
    public void iShouldBeRegisteredSuccessfully() {
        // Wait for successful registration by checking URL change
        DriverManager.getWait().until(driver -> 
            driver.getCurrentUrl().contains("/contactList"));
    }

    @Then("I should be redirected to the contact list page")
    public void iShouldBeRedirectedToTheContactListPage() {
        DriverManager.getWait().until(driver -> 
            driver.getCurrentUrl().contains("/contactList"));
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        DriverManager.getWait().until(driver ->
                driver.getCurrentUrl().equals(Configuration.getInstance().getBaseUrl() + "/login"));
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedError) {
        Assertions.assertThat(signUpPage.isErrorMessageDisplayed())
            .as("Error message should be displayed")
            .isTrue();
        
        Assertions.assertThat(signUpPage.getErrorMessage())
            .as("Error message should match expected message")
            .contains(expectedError);
    }

    @Then("I should see an error message containing {string}")
    public void iShouldSeeAnErrorMessageContaining(String errorText) {
        Assertions.assertThat(signUpPage.isErrorMessageDisplayed())
            .as("Error message should be displayed")
            .isTrue();
        
        Assertions.assertThat(signUpPage.getErrorMessage())
            .as("Error message should contain expected text")
            .containsIgnoringCase(errorText);
    }

    @Then("I should delete user through api request")
    public void iShouldDeleteUserThroughApiRequest() {
        try {
            Response response = UserApiRequests.deleteUser();
            response.then().statusCode(200);
        } catch (Exception e) {
            logger.error("Failed to delete user: {}", e.getMessage());
            fail("Failed to delete user: " + e.getMessage());
        }
    }
}
