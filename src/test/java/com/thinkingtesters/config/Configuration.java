package com.thinkingtesters.config;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class Configuration {
    private static final Logger logger = LogManager.getLogger(Configuration.class);
    private static Configuration instance;
    private final Properties properties;

    private Configuration() {
        properties = new Properties();
        loadProperties();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            logger.error("Error loading properties file", ex);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url", "https://thinking-tester-contact-list.herokuapp.com");
    }

    public int getExplicitWaitTimeout() {
        return Integer.parseInt(properties.getProperty("explicit.wait.timeout", "30"));
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }
}
