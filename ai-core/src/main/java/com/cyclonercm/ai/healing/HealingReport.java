package com.cyclonercm.ai.healing;

import org.openqa.selenium.By;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logs all healed locators so developers can update PageObjects.
 * Output: logs/healing-report.log
 */
public class HealingReport {

    private static final String LOG_PATH = "logs/healing-report.log";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(By original, By healed, String reason) {
        try {
            Files.createDirectories(Paths.get("logs"));
            try (PrintWriter pw = new PrintWriter(
                    new FileWriter(LOG_PATH, true))) {
                pw.println("─────────────────────────────────────────");
                pw.println("⚠️  HEALING EVENT: " + LocalDateTime.now().format(FMT));
                pw.println("   ORIGINAL : " + original);
                pw.println("   HEALED   : " + healed);
                pw.println("   REASON   : " + reason);
                pw.println("   ACTION   : Update PageObject with healed locator");
                pw.println("─────────────────────────────────────────");
                pw.println();
            }
        } catch (IOException e) {
            System.err.println("Could not write to healing report: " + e.getMessage());
        }
    }

    public static void logFailure(By original, String message) {
        try {
            Files.createDirectories(Paths.get("logs"));
            try (PrintWriter pw = new PrintWriter(
                    new FileWriter(LOG_PATH, true))) {
                pw.println("─────────────────────────────────────────");
                pw.println("🔴 HEALING FAILED: " + LocalDateTime.now().format(FMT));
                pw.println("   ORIGINAL : " + original);
                pw.println("   MESSAGE  : " + message);
                pw.println("   ACTION   : Manual investigation required");
                pw.println("─────────────────────────────────────────");
                pw.println();
            }
        } catch (IOException e) {
            System.err.println("Could not write to healing report: " + e.getMessage());
        }
    }
}