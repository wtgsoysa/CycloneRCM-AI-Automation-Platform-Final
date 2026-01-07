package com.cyclonercm.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class ScreenshotUtil {

    public static String captureFullPageScreenshot(WebDriver driver, String name) {
        try {
            if (driver == null) return "";
            Path screenshotsDir = Paths.get("target", "screenshots");
            Files.createDirectories(screenshotsDir);
            String fileName = name + "_" + Instant.now().toEpochMilli() + ".png";
            Path dest = screenshotsDir.resolve(fileName);

            if (driver instanceof TakesScreenshot) {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(src.toPath(), dest);
                return dest.toString();
            }
            return "";
        } catch (IOException e) {
            return "";
        }
    }
}
