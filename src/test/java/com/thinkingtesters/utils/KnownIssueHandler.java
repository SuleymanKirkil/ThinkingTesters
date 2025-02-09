package com.thinkingtesters.utils;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

public class KnownIssueHandler {
    private static final Logger logger = LogManager.getLogger(KnownIssueHandler.class);
    private static final String KNOWN_ISSUE_TAG = "@KnownIssue";
    private static final String BUG_TAG_PREFIX = "@bug=";
    private static final String EXPIRES_TAG_PREFIX = "@expires=";
    private static final String ENV_TAG_PREFIX = "@env=";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    public static boolean shouldSkipScenario(Scenario scenario) {
        Set<String> tags = scenario.getSourceTagNames();
        
        if (!tags.contains(KNOWN_ISSUE_TAG)) {
            return false;
        }

        // Check environment specific skip
        Optional<String> envTag = findTagWithPrefix(tags, ENV_TAG_PREFIX);
        if (envTag.isPresent()) {
            String targetEnv = envTag.get().substring(ENV_TAG_PREFIX.length());
            String currentEnv = System.getProperty("test.environment", "local");
            if (!targetEnv.equalsIgnoreCase(currentEnv)) {
                return false;
            }
        }

        // Check expiration date
        Optional<String> expiresTag = findTagWithPrefix(tags, EXPIRES_TAG_PREFIX);
        if (expiresTag.isPresent()) {
            String expiryDateStr = expiresTag.get().substring(EXPIRES_TAG_PREFIX.length());
            try {
                LocalDate expiryDate = LocalDate.parse(expiryDateStr, DATE_FORMATTER);
                if (LocalDate.now().isAfter(expiryDate)) {
                    logger.warn("Known issue has expired on {}. This tag should be removed.", expiryDateStr);
                    return false;
                }
            } catch (Exception e) {
                logger.error("Invalid date format in expires tag: {}. Expected format: YYYY-MM-DD", expiryDateStr);
            }
        }

        return true;
    }

    public static String getSkipReason(Scenario scenario) {
        Set<String> tags = scenario.getSourceTagNames();
        
        StringBuilder reason = new StringBuilder("Test skipped due to known issue");
        
        // Add bug ID if present
        findTagWithPrefix(tags, BUG_TAG_PREFIX)
                .ifPresent(bug -> reason.append(": ").append(bug.substring(BUG_TAG_PREFIX.length())));
        
        // Add environment info if present
        findTagWithPrefix(tags, ENV_TAG_PREFIX)
                .ifPresent(env -> reason.append(" (Environment: ").append(env.substring(ENV_TAG_PREFIX.length())).append(")"));
        
        // Add expiry info if present
        findTagWithPrefix(tags, EXPIRES_TAG_PREFIX)
                .ifPresent(expires -> reason.append(" (Expires: ").append(expires.substring(EXPIRES_TAG_PREFIX.length())).append(")"));
        
        return reason.toString();
    }

    private static Optional<String> findTagWithPrefix(Set<String> tags, String prefix) {
        return tags.stream()
                .filter(tag -> tag.startsWith(prefix))
                .findFirst();
    }
}
