package com.thinkingtesters.utils;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static Map<String, Object> scenarioContext = new HashMap<>();
    
    public static void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }
    
    public static Object getContext(String key) {
        return scenarioContext.get(key);
    }

    public static void clearContext() {
        scenarioContext.clear();
    }

    public static boolean containsKey(String key) {
        return scenarioContext.containsKey(key);
    }
}
