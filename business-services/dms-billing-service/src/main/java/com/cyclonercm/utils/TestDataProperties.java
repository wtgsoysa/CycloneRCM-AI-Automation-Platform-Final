package com.cyclonercm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataProperties {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = TestDataProperties.class.getClassLoader()
                .getResourceAsStream("testdata.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load testdata.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
