package com.thinkingtesters.hooks;

import com.thinkingtesters.utils.DriverManager;
import com.thinkingtesters.utils.KnownIssueHandler;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assume;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static final String SCREENSHOT_DIR = "target/cucumber-reports/screenshots";

    @Before
    public void setUp(Scenario scenario) {
        if (KnownIssueHandler.shouldSkipScenario(scenario)) {
            String skipReason = KnownIssueHandler.getSkipReason(scenario);
            logger.warn("Skipping scenario '{}': {}", scenario.getName(), skipReason);
            Assume.assumeTrue(skipReason, false);
        }
        
        logger.info("Starting scenario: {}", scenario.getName());
        DriverManager.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.warn("Scenario failed: {}", scenario.getName());
            try {
                // Ekran görüntüsünü al
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());

                // Ekran görüntüsünü dosyaya kaydet
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String screenshotName = String.format("%s_%s.png", 
                    scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_"),
                    timestamp);

                // Dizini oluştur
                Files.createDirectories(Paths.get(SCREENSHOT_DIR));

                // Ekran görüntüsünü kaydet
                Path screenshotPath = Paths.get(SCREENSHOT_DIR, screenshotName);
                Files.write(screenshotPath, screenshot);
                
                logger.info("Screenshot saved: {}", screenshotPath);
            } catch (IOException e) {
                logger.error("Failed to save screenshot", e);
            }
        }
        
        DriverManager.quitDriver();
        logger.info("Finished scenario: {}", scenario.getName());
    }
}
