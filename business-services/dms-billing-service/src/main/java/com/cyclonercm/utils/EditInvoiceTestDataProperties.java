package com.cyclonercm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EditInvoiceTestDataProperties {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = HistorySmokeTestDataProperties.class.getClassLoader()
                .getResourceAsStream("editinvoicesmoketestdata.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load editinvoicesmoketestdata.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
