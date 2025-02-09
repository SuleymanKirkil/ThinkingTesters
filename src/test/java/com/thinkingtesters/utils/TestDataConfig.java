package com.thinkingtesters.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataConfig {
    private static final Logger logger = LogManager.getLogger(TestDataConfig.class);
    private static final Properties props = new Properties();
    private static final String currentEnv = System.getProperty("test.environment", "qa");

    static {
        try (InputStream input = TestDataConfig.class.getClassLoader().getResourceAsStream("test-data.properties")) {
            if (input == null) {
                logger.error("Unable to find test-data.properties");
                throw new RuntimeException("test-data.properties not found in classpath");
            }
            props.load(input);
        } catch (IOException ex) {
            logger.error("Error loading test data properties", ex);
            throw new RuntimeException("Could not load test data properties", ex);
        }
    }

    public static String getTestUserEmail() {
        return props.getProperty(currentEnv + ".test.user.email");
    }

    public static String getTestUserPassword() {
        return props.getProperty(currentEnv + ".test.user.password");
    }

    public static String getInvalidEmail() {
        return props.getProperty("test.invalid.email");
    }

    public static String getInvalidPassword() {
        return props.getProperty("test.invalid.password");
    }
}
