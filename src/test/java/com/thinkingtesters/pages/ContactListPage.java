package com.thinkingtesters.pages;

import com.thinkingtesters.utils.DriverManager;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactListPage extends BasePage {

    @FindBy(css = "h1")
    private WebElement pageHeader;

    @FindBy(id = "add-contact")
    private WebElement addContactButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(className = "contactTable")
    private WebElement contactTable;

    @FindBy(id = "search-input")
    private WebElement searchInput;

    // Add Contact Form Elements
    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "phone")
    private WebElement phoneInput;

    @FindBy(id = "street1")
    private WebElement address1Input;

    @FindBy(id = "street2")
    private WebElement address2Input;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "stateProvince")
    private WebElement stateInput;

    @FindBy(id = "postalCode")
    private WebElement postalCodeInput;

    @FindBy(id = "country")
    private WebElement countryInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(css = ".error-message")
    private List<WebElement> errorMessages;

    @FindBy(css = ".pagination")
    private WebElement paginationControls;

    @FindBy(css = ".next-page")
    private WebElement nextPageButton;

    @FindBy(css = ".prev-page")
    private WebElement prevPageButton;

    private final WebDriverWait wait;

    public ContactListPage() {
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageHeader() {
        return pageHeader.getText();
    }

    public void clickAddContact() {
        addContactButton.click();
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public boolean isContactTableVisible() {
        return contactTable.isDisplayed();
    }
    public boolean isAddContactButtonVisible() {
        return addContactButton.isDisplayed();
    }

    public void fillContactDetails(Map<String, String> contactDetails) {
        if (contactDetails.containsKey("firstName")) {
            firstNameInput.sendKeys(contactDetails.get("firstName"));
        }
        if (contactDetails.containsKey("lastName")) {
            lastNameInput.sendKeys(contactDetails.get("lastName"));
        }
        if (contactDetails.containsKey("email")) {
            emailInput.sendKeys(contactDetails.get("email"));
        }
        if (contactDetails.containsKey("phone")) {
            phoneInput.sendKeys(contactDetails.get("phone"));
        }
        if (contactDetails.containsKey("address1")) {
            address1Input.sendKeys(contactDetails.get("address1"));
        }
        if (contactDetails.containsKey("address2")) {
            address2Input.sendKeys(contactDetails.get("address2"));
        }
        if (contactDetails.containsKey("city")) {
            cityInput.sendKeys(contactDetails.get("city"));
        }
        if (contactDetails.containsKey("state")) {
            stateInput.sendKeys(contactDetails.get("state"));
        }
        if (contactDetails.containsKey("postalCode")) {
            postalCodeInput.sendKeys(contactDetails.get("postalCode"));
        }
        if (contactDetails.containsKey("country")) {
            countryInput.sendKeys(contactDetails.get("country"));
        }
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void clickContact(String firstName, String lastName) {
        String xpath = String.format("//td[contains(text(),'%s')]/parent::tr", firstName + " " + lastName);
        WebElement contact = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        contact.click();
    }

    public boolean isContactVisible(String firstName, String lastName) {
        String xpath = String.format("//td[contains(text(),'%s')]", firstName + " " + lastName);
        try {
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void searchContact(String searchTerm) {
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
    }

    public void clickColumnHeader(String columnName) {
        String xpath = String.format("//th[contains(text(),'%s')]", columnName);
        driver.findElement(By.xpath(xpath)).click();
    }

    public List<String> getErrorMessages() {
        return errorMessages.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isPaginationVisible() {
        return paginationControls.isDisplayed();
    }

    public void clickNextPage() {
        nextPageButton.click();
    }

    public void clickPrevPage() {
        prevPageButton.click();
    }

    public void confirmDeletion() {
        driver.switchTo().alert().accept();
    }

    public void cancelDeletion() {
        driver.switchTo().alert().dismiss();
    }
    public Map<String, String> getContactDetails(String firstName, String lastName) {
        String xpath = String.format("//td[contains(text(),'%s')]", firstName + " " + lastName);
        try {
             driver.findElement(By.xpath(xpath)).click();
             waitForElementVisible(By.id("firstName"));
        } catch (Exception e) {
             throw e;
        }
        return Map.of(
                "firstName", firstNameInput.getText(),
                "lastName", lastNameInput.getText(),
                "email", emailInput.getText(),
                "phone", phoneInput.getText(),
               "address1", address1Input.getText(),
               // "address2", address2Input.getText(),
                "city", cityInput.getText(),
               // "state", stateInput.getText(),
                "postalCode", postalCodeInput.getText(),
                "country", countryInput.getText()
        );
    }
    public Map<String, String> getContactDetailsFromTable(String firstName, String lastName) {
        clickContact(firstName, lastName);

        By tableByXpath = By.id("myTable");
        WebElement table = DriverManager.getDriver().findElement(tableByXpath);
        WebElement lastContact = table.findElement(By.tagName("//tbody/tr[last()]"));
        List<WebElement> rowElements = lastContact.findElements(By.tagName("td"));


        // Implementation depends on how contact details are displayed
        // This is a placeholder that should be implemented based on actual page structure
        return Map.of(
            "firstName",rowElements.get(1).getText().split(" ")[0],
            "lastName", rowElements.get(1).getText().split(" ")[1],
            "birthdate", rowElements.get(2).getText(),
            "email", rowElements.get(3).getText(),
            "phone", rowElements.get(4).getText(),
            "address", rowElements.get(5).getText(),
            "city", rowElements.get(6).getText(),
            "country", rowElements.get(7).getText()
        );
    }

    public void clearContactForm() {
        firstNameInput.clear();
        lastNameInput.clear();
        emailInput.clear();
        phoneInput.clear();
        address1Input.clear();
        address2Input.clear();
        cityInput.clear();
        stateInput.clear();
        postalCodeInput.clear();
        countryInput.clear();
    }
}
