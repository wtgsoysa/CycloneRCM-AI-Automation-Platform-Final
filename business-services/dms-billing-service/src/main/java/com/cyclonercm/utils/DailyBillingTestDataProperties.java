package com.cyclonercm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DailyBillingTestDataProperties {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = HistoryBillingTestDataProperties.class.getClassLoader()
                .getResourceAsStream("dailybillingsmoketestdata.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dailybillingsmoketestdata.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
