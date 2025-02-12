package com.thinkingtesters.utils;

import com.thinkingtesters.config.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Configuration.getExplicitWaitTimeout()));
        }
        return wait;
    }

    public static void initializeDriver() {
        logger.info("Initializing WebDriver");
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // Log Chrome version
            String chromeVersion = WebDriverManager.chromedriver().getDownloadedDriverVersion();
            logger.info("Chrome Driver Version: {}", chromeVersion);

            // CI environment specific options
            if (isRunningInCI()) {
                logger.info("Configuring Chrome for CI environment");
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
            }

            driver = new ChromeDriver(options);
            logger.info("WebDriver initialized successfully");

            // Set default window size for non-CI environment
            if (!isRunningInCI()) {
                driver.manage().window().maximize();
            }

            // Set timeouts
            int timeout = Configuration.getExplicitWaitTimeout();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeout));
            
            logger.info("Timeouts configured: {} seconds", timeout);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver", e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    private static boolean isRunningInCI() {
        return System.getenv("CI") != null || System.getProperty("CI") != null;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            } finally {
                driver = null;
                wait = null;
            }
        }
    }
}
