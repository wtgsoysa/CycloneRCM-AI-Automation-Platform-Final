package com.cyclonercm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read Billing Smoke Test Data Properties
 */
public class HistoryBillingTestDataProperties {

    private static Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "src/test/resources/historybillingsmoketestdata.properties";

    static {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load billing smoke test data properties file: " + PROPERTIES_FILE, e);
        }
    }

    /**
     * Get property value by key
     *
     * @param key Property key
     * @return Property value
     */
    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property key '" + key + "' not found in billing smoke test data properties");
        }
        return value;
    }

    /**
     * Get property value by key with default value
     *
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Check if property key exists
     *
     * @param key Property key
     * @return true if key exists, false otherwise
     */
    public static boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}
