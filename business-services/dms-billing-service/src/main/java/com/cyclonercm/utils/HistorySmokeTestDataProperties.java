package com.cyclonercm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HistorySmokeTestDataProperties {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = HistorySmokeTestDataProperties.class.getClassLoader()
                .getResourceAsStream("historysmoketestdata.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load historysmoketestdata.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
