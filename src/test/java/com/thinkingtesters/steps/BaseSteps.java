package com.thinkingtesters.steps;

import com.thinkingtesters.utils.DriverManager;
import org.openqa.selenium.WebDriver;

public class BaseSteps {
    protected static WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
