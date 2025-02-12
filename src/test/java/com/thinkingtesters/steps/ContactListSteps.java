package com.thinkingtesters.steps;

import com.thinkingtesters.pages.ContactDetatailsPage;
import com.thinkingtesters.pages.ContactListPage;
import com.thinkingtesters.pages.LoginPage;
import com.thinkingtesters.utils.DriverManager;
import com.thinkingtesters.utils.TestDataConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

public class ContactListSteps {
    private final ContactListPage contactListPage;
    private final ContactDetatailsPage contactDetatailsPage;
    private final LoginPage loginPage;
    private Map<String, String> lastAddedContact;

    public ContactListSteps() {
        contactListPage = new ContactListPage();
        loginPage = new LoginPage();
        contactDetatailsPage = new ContactDetatailsPage();
    }

    @Given("I am logged in with valid credentials")
    public void iAmLoggedInWithValidCredentials() {
        String email = TestDataConfig.getTestUserEmail();
        String password = TestDataConfig.getTestUserPassword();
        loginPage.navigateToLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickSubmit();
    }

    @Given("I am on the contact list page")
    public void iAmOnTheContactListPage() {
        Assert.assertEquals("Contact List App", contactListPage.getPageHeader());
    }

    @Then("I should see the {string} header")
    public void iShouldSeeTheHeader(String headerText) {
        Assert.assertEquals(headerText, contactListPage.getPageHeader());
    }

    @Then("I should see the {string} button")
    public void iShouldSeeTheButton(String buttonText) {
        By buttonPath = By.xpath("//button[text()='"+buttonText+"']");
        Assert.assertTrue(DriverManager.getDriver().findElement(buttonPath).isDisplayed());
    }

    @Then("I should see the contact table if contacts exist")
    public void iShouldSeeTheContactTableIfContactsExist() {
        Assert.assertTrue(contactListPage.isContactTableVisible());
    }

    @When("I click on {string} button")
    public void iClickOnButton(String buttonText) {
        switch (buttonText) {
            case "Add a New Contact" : contactListPage.clickAddContact();
            break;
            case "Submit" : contactListPage.clickSubmit();
            break;
            case "Logout" : contactListPage.clickLogout();
            break;
            default : throw new IllegalArgumentException("Button " + buttonText + " not supported");
        }
    }

    @When("I fill in the following required contact details:")
    public void iFillInTheFollowingRequiredContactDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        lastAddedContact = rows.get(0);
        contactListPage.fillContactDetails(lastAddedContact);
    }

    @When("I fill in the following contact details:")
    public void iFillInTheFollowingContactDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        lastAddedContact = rows.get(0);
        contactListPage.fillContactDetails(lastAddedContact);
    }

    @Then("I should see the contact in the list")
    public void iShouldSeeTheContactInTheList() {
        Assert.assertTrue(
            contactListPage.isContactVisible(
                lastAddedContact.get("firstName"),
                lastAddedContact.get("lastName")
            )
        );
    }

    @Then("the contact details should match the input")
    public void theContactDetailsShouldMatchTheInput() {
        Map<String, String> displayedDetails = contactDetatailsPage.readContactDetails(
            lastAddedContact.get("firstName"),
            lastAddedContact.get("lastName")
        );
        
        lastAddedContact.forEach((key, value) ->
            Assert.assertEquals("Contact detail mismatch for " + key,
                value, displayedDetails.get(key)));
    }

    @Given("I have at least one contact in the list")
    public void iHaveAtLeastOneContactInTheList() {
        // First check if contact exists, if not create one
        if (!contactListPage.isContactTableVisible()) {
            contactListPage.clickAddContact();
            Map<String, String> newContact = Map.of(
                "firstName", "Test",
                "lastName", "User",
                "email", "test.user@example.com"
            );
            contactListPage.fillContactDetails(newContact);
            contactListPage.clickSubmit();
        }
    }

    @When("I click on a contact's name")
    public void iClickOnAContactName() {
        contactListPage.clickContact("Test", "User");
    }

    @When("I update the following fields:")
    public void iUpdateTheFollowingFields(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        contactListPage.clearContactForm();
        contactListPage.fillContactDetails(rows.get(0));
    }

    @Then("the contact details should reflect the changes")
    public void theContactDetailsShouldReflectTheChanges() {
        // Implementation similar to "theContactDetailsShouldMatchTheInput"
        // but specifically for updated fields
    }

    @When("I confirm the deletion")
    public void iConfirmTheDeletion() {
        contactListPage.confirmDeletion();
    }

    @When("I cancel the deletion")
    public void iCancelTheDeletion() {
        contactListPage.cancelDeletion();
    }

    @Then("the contact should be removed from the list")
    public void theContactShouldBeRemovedFromTheList() {
        Assert.assertFalse(
            contactListPage.isContactVisible("Test", "User")
        );
    }

    @Then("I should not see the deleted contact")
    public void iShouldNotSeeTheDeletedContact() {
        Assert.assertFalse(
            contactListPage.isContactVisible("Test", "User")
        );
    }

    @When("I click on {string} button without filling any fields")
    public void iClickOnButtonWithoutFillingAnyFields(String buttonText) {
        contactListPage.clearContactForm();
        contactListPage.clickSubmit();
    }

    @Then("I should see error messages for required fields")
    public void iShouldSeeErrorMessagesForRequiredFields() {
        List<String> errorMessages = contactListPage.getErrorMessages();
        Assert.assertFalse("No error messages found", errorMessages.isEmpty());
    }

    @Then("I should see an error message for invalid email")
    public void iShouldSeeAnErrorMessageForInvalidEmail() {
        List<String> errorMessages = contactListPage.getErrorMessages();
        Assert.assertTrue(
            errorMessages.stream()
                .anyMatch(msg -> msg.toLowerCase().contains("email"))
        );
    }

    @Given("I have multiple contacts in the list")
    public void iHaveMultipleContactsInTheList() {
        // Add multiple contacts if they don't exist
        if (!contactListPage.isContactTableVisible()) {
            addTestContact("John", "Doe", "john.doe@example.com");
            addTestContact("Jane", "Smith", "jane.smith@example.com");
        }
    }

    private void addTestContact(String firstName, String lastName, String email) {
        contactListPage.clickAddContact();
        contactListPage.fillContactDetails(Map.of(
            "firstName", firstName,
            "lastName", lastName,
            "email", email
        ));
        contactListPage.clickSubmit();
    }

    @When("I enter a search term in the search box")
    public void iEnterASearchTermInTheSearchBox() {
        contactListPage.searchContact("John");
    }

    @Then("I should only see contacts matching the search term")
    public void iShouldOnlySeeContactsMatchingTheSearchTerm() {
        Assert.assertTrue(contactListPage.isContactVisible("John", "Doe"));
        Assert.assertFalse(contactListPage.isContactVisible("Jane", "Smith"));
    }

    @When("I click on a column header")
    public void iClickOnAColumnHeader() {
        contactListPage.clickColumnHeader("Name");
    }

    @Then("the contacts should be sorted by that column")
    public void theContactsShouldBeSortedByThatColumn() {
        // Implementation would depend on how we can verify sorting
        // This might require getting all rows and verifying their order
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage());
    }

    @Given("I have more than {int} contacts in the list")
    public void iHaveMoreThanContactsInTheList(int count) {
        // Implementation to ensure enough contacts exist
        // This might involve creating contacts if needed
    }

    @Then("I should see pagination controls")
    public void iShouldSeePaginationControls() {
        Assert.assertTrue(contactListPage.isPaginationVisible());
    }

    @When("I click on the next page")
    public void iClickOnTheNextPage() {
        contactListPage.clickNextPage();
    }

    @Then("I should see the next set of contacts")
    public void iShouldSeeTheNextSetOfContacts() {
        // Implementation would depend on how we can verify different contacts are shown
    }

    @Then("previous page should be accessible")
    public void previousPageShouldBeAccessible() {
        contactListPage.clickPrevPage();
        // Verify we're back to the previous page
    }
}
