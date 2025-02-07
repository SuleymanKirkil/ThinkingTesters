package com.thinkingtesters.pages;

import com.thinkingtesters.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible after timeout: {}", locator);
            throw e;
        }
    }

    protected WebElement waitForElementClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable after timeout: {}", locator);
            throw e;
        }
    }

    protected void click(By locator) {
        try {
            WebElement element = waitForElementClickable(locator);
            element.click();
            logger.debug("Clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            throw e;
        }
    }

    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementVisible(locator);
            element.clear();
            element.sendKeys(text);
            logger.debug("Sent keys '{}' to element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {}", locator, e);
            throw e;
        }
    }

    protected String getText(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            String text = element.getText();
            logger.debug("Got text '{}' from element: {}", text, locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            throw e;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForElementVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitForUrlContains(String urlFragment) {
        try {
            wait.until(ExpectedConditions.urlContains(urlFragment));
        } catch (Exception e) {
            logger.error("URL did not contain '{}' after timeout", urlFragment);
            throw e;
        }
    }
}
