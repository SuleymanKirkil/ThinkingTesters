package com.thinkingtesters.pages;

import com.thinkingtesters.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactDetatailsPage extends BasePage {

    @FindBy(id = "edit-contact")
    private WebElement editContactButton;
    @FindBy(id = "delete")
    private WebElement deleteContactButton;
    @FindBy(id = "return")
    private WebElement returnContactListButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

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


    private final WebDriverWait wait;

    public ContactDetatailsPage() {
        super();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickLogout() {
        logoutButton.click();
    }


    public Map<String, String> readContactDetails(String firstName, String lastName) {
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
                 "address2", address2Input.getText(),
                 "city", cityInput.getText(),
                 "state", stateInput.getText(),
                 "postalCode", postalCodeInput.getText(),
                 "country", countryInput.getText()
        );
    }


    public void confirmDeletion() {
        driver.switchTo().alert().accept();
    }

    public void cancelDeletion() {
        driver.switchTo().alert().dismiss();
    }

}
