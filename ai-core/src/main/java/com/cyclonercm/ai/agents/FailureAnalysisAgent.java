package com.cyclonercm.ai.agents;

import com.cyclonercm.ai.core.BaseAIAgent;
import com.cyclonercm.ai.models.FailureAnalysis;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Failure Analysis Agent
 * Analyzes test failures and provides intelligent root cause analysis
 *
 * USAGE:
 * FailureAnalysisAgent agent = new FailureAnalysisAgent();
 * FailureAnalysis analysis = agent.analyzeFailure(
 *     "testDailyBillingFilter",
 *     exception,
 *     screenshot,
 *     pageSource
 * );
 *
 * @author Nolo (Senior QA AI Agent)
 * @version 1.0
 * @date January 06, 2026
 */
public class FailureAnalysisAgent extends BaseAIAgent {

    private static final String SYSTEM_PROMPT =
            "You are an expert QA automation engineer analyzing test failures. " +
                    "Your task is to provide ACTIONABLE ROOT CAUSE ANALYSIS.\n\n" +
                    "ANALYSIS SHOULD INCLUDE:\n" +
                    "1. FAILURE CATEGORY (Element, Timeout, Assertion, Data, Environment, etc.)\n" +
                    "2. ROOT CAUSE (What actually went wrong)\n" +
                    "3. LIKELY REASON (Why it went wrong)\n" +
                    "4. RECOMMENDED FIX (How to fix it)\n" +
                    "5. PREVENTION (How to prevent it in future)\n" +
                    "6. IS_FLAKY (true/false - is this a flaky test?)\n\n" +
                    "IMPORTANT:\n" +
                    "- Be specific and actionable\n" +
                    "- Consider common Selenium issues (timing, stale elements, locators)\n" +
                    "- Analyze stack trace carefully\n" +
                    "- Check page source for clues\n" +
                    "- Return JSON format ONLY\n\n" +
                    "JSON FORMAT:\n" +
                    "{\n" +
                    "  \"category\": \"ELEMENT_NOT_FOUND\",\n" +
                    "  \"rootCause\": \"Element with ID 'dos-filter' does not exist on page\",\n" +
                    "  \"likelyReason\": \"Page structure changed or element ID was modified\",\n" +
                    "  \"recommendedFix\": \"Update locator to use data-testid or unique class\",\n" +
                    "  \"prevention\": \"Use more stable locators (data-testid, ID) instead of XPath\",\n" +
                    "  \"isFlaky\": false,\n" +
                    "  \"confidence\": 95\n" +
                    "}";

    /**
     * Constructor
     */
    public FailureAnalysisAgent() {
        super();
        this.maxTokens = 3000;
        this.temperature = 0.4; // Moderate creativity for analysis
    }

    @Override
    public String execute(String input) {
        // Input format: "testName|exception|screenshot|pageSource"
        String[] parts = input.split("\\|", 4);
        if (parts.length >= 2) {
            String testName = parts[0];
            String exception = parts[1];
            String screenshot = parts.length > 2 ? parts[2] : null;
            String pageSource = parts.length > 3 ? parts[3] : null;

            FailureAnalysis analysis = analyzeFailure(testName, exception, screenshot, pageSource);
            return analysis != null ? analysis.toString() : "Analysis failed";
        }
        return "Invalid input format";
    }

    @Override
    public String getAgentName() {
        return "FailureAnalysisAgent";
    }

    /**
     * Analyze test failure
     *
     * @param testName Name of the failed test
     * @param exception Exception/error that occurred
     * @param screenshotPath Path to failure screenshot (optional)
     * @param pageSource Page HTML at time of failure (optional)
     * @return FailureAnalysis with detailed analysis
     */
    public FailureAnalysis analyzeFailure(String testName,
                                          String exception,
                                          String screenshotPath,
                                          String pageSource) {
        System.out.println("\n🔍 " + getAgentName() + " analyzing failure...");
        System.out.println("❌ Test: " + testName);

        if (!isConfigured()) {
            System.err.println("⚠️  API key not configured. Cannot analyze failure.");
            return createFallbackAnalysis(testName, exception);
        }

        // Build context for AI
        StringBuilder context = new StringBuilder();
        context.append("TEST NAME: ").append(testName).append("\n\n");
        context.append("EXCEPTION/ERROR:\n").append(exception).append("\n\n");

        if (screenshotPath != null && !screenshotPath.isEmpty()) {
            context.append("SCREENSHOT: ").append(screenshotPath).append("\n\n");
        }

        if (pageSource != null && !pageSource.isEmpty()) {
            // Truncate page source
            String truncatedSource = truncatePageSource(pageSource, 2000);
            context.append("PAGE SOURCE (excerpt):\n```html\n");
            context.append(truncatedSource).append("\n```\n\n");
        }

        // Build user prompt
        String userPrompt =
                context.toString() +
                        "Task: Analyze this test failure and provide detailed root cause analysis. " +
                        "Return ONLY JSON, no other text.";

        // Call AI
        String aiResponse = callClaudeAPI(SYSTEM_PROMPT, userPrompt);

        // Parse response
        try {
            FailureAnalysis analysis = parseFailureAnalysis(aiResponse, testName);

            if (analysis != null) {
                System.out.println("✅ Analysis complete!");
                System.out.println("📋 Category: " + analysis.getCategory());
                System.out.println("🎯 Confidence: " + analysis.getConfidence() + "%");
                System.out.println("🔄 Is Flaky: " + (analysis.isFlaky() ? "YES" : "NO"));
                return analysis;
            } else {
                System.err.println("⚠️  Failed to parse AI response");
                return createFallbackAnalysis(testName, exception);
            }

        } catch (Exception e) {
            System.err.println("❌ Error parsing analysis: " + e.getMessage());
            return createFallbackAnalysis(testName, exception);
        }
    }

    /**
     * Analyze failure from Throwable
     */
    public FailureAnalysis analyzeFailure(String testName, Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String stackTrace = sw.toString();

        return analyzeFailure(testName, stackTrace, null, null);
    }

    /**
     * Batch analyze multiple failures
     */
    public FailureAnalysis[] analyzeBatchFailures(String[] testNames,
                                                  String[] exceptions) {
        System.out.println("\n🚀 Batch Failure Analysis Started");
        System.out.println("📊 Total failures to analyze: " + testNames.length);

        FailureAnalysis[] analyses = new FailureAnalysis[testNames.length];

        for (int i = 0; i < testNames.length; i++) {
            System.out.println("\n--- Analyzing failure " + (i + 1) + "/" + testNames.length + " ---");
            analyses[i] = analyzeFailure(testNames[i], exceptions[i], null, null);

            // Small delay to avoid API rate limits
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Summary
        int flakyCount = 0;
        for (FailureAnalysis analysis : analyses) {
            if (analysis != null && analysis.isFlaky()) {
                flakyCount++;
            }
        }

        System.out.println("\n✅ Batch analysis complete!");
        System.out.println("📊 Flaky tests detected: " + flakyCount + "/" + testNames.length);

        return analyses;
    }

    /**
     * Categorize failure automatically (without AI)
     */
    public String categorizeFailure(String exception) {
        String lowerException = exception.toLowerCase();

        if (lowerException.contains("nosuchelementexception") ||
                lowerException.contains("element not found")) {
            return "ELEMENT_NOT_FOUND";

        } else if (lowerException.contains("timeoutexception") ||
                lowerException.contains("timeout")) {
            return "TIMEOUT";

        } else if (lowerException.contains("assertionerror") ||
                lowerException.contains("expected") && lowerException.contains("but was")) {
            return "ASSERTION_FAILED";

        } else if (lowerException.contains("staleelementreferenceexception")) {
            return "STALE_ELEMENT";

        } else if (lowerException.contains("webdriverexception")) {
            return "DRIVER_ERROR";

        } else if (lowerException.contains("javascriptexception")) {
            return "JAVASCRIPT_ERROR";

        } else if (lowerException.contains("nullpointerexception")) {
            return "NULL_POINTER";

        } else {
            return "UNKNOWN";
        }
    }

    /**
     * Create fallback analysis (when AI is not available)
     */
    private FailureAnalysis createFallbackAnalysis(String testName, String exception) {
        String category = categorizeFailure(exception);
        String rootCause = "Test failed with " + category;
        String recommendedFix = "Review stack trace and page state";

        return new FailureAnalysis(
                testName,
                category,
                rootCause,
                "Analysis not available - API not configured",
                recommendedFix,
                "Implement proper error handling and logging",
                false,
                50
        );
    }

    /**
     * Truncate page source for API
     */
    private String truncatePageSource(String pageSource, int maxChars) {
        if (pageSource.length() <= maxChars) {
            return pageSource;
        }
        return pageSource.substring(0, maxChars) + "\n<!-- HTML truncated -->";
    }

    /**
     * Parse AI response into FailureAnalysis object
     */
    private FailureAnalysis parseFailureAnalysis(String aiResponse, String testName) {
        try {
            // Simple JSON parsing
            String category = extractJsonValue(aiResponse, "category");
            String rootCause = extractJsonValue(aiResponse, "rootCause");
            String likelyReason = extractJsonValue(aiResponse, "likelyReason");
            String recommendedFix = extractJsonValue(aiResponse, "recommendedFix");
            String prevention = extractJsonValue(aiResponse, "prevention");
            boolean isFlaky = Boolean.parseBoolean(extractJsonValue(aiResponse, "isFlaky"));
            int confidence = Integer.parseInt(extractJsonValue(aiResponse, "confidence"));

            return new FailureAnalysis(
                    testName,
                    category,
                    rootCause,
                    likelyReason,
                    recommendedFix,
                    prevention,
                    isFlaky,
                    confidence
            );

        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extract JSON value (simple implementation)
     */
    private String extractJsonValue(String json, String key) {
        int keyIndex = json.indexOf("\"" + key + "\"");
        if (keyIndex == -1) return "";

        int valueStart = json.indexOf(":", keyIndex) + 1;
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd == -1) {
            valueEnd = json.indexOf("}", valueStart);
        }
        if (valueEnd == -1) valueEnd = json.length();

        String value = json.substring(valueStart, valueEnd).trim();
        return value.replace("\"", "").trim();
    }

    /**
     * Generate failure report
     */
    public String generateFailureReport(FailureAnalysis[] analyses) {
        StringBuilder report = new StringBuilder();
        report.append("╔════════════════════════════════════════════════════════════════╗\n");
        report.append("║           FAILURE ANALYSIS REPORT                              ║\n");
        report.append("╚════════════════════════════════════════════════════════════════╝\n\n");

        // Summary
        int total = analyses.length;
        int flaky = 0;
        for (FailureAnalysis analysis : analyses) {
            if (analysis != null && analysis.isFlaky()) {
                flaky++;
            }
        }

        report.append("📊 SUMMARY\n");
        report.append("─────────────────────────────────────────────────────────────────\n");
        report.append("Total Failures: ").append(total).append("\n");
        report.append("Flaky Tests: ").append(flaky).append(" (")
                .append(String.format("%.1f", (flaky * 100.0 / total))).append("%)\n");
        report.append("Stable Failures: ").append(total - flaky).append("\n\n");

        // Detailed analysis
        report.append("📋 DETAILED ANALYSIS\n");
        report.append("─────────────────────────────────────────────────────────────────\n");

        for (int i = 0; i < analyses.length; i++) {
            FailureAnalysis analysis = analyses[i];
            if (analysis == null) continue;

            report.append("\n").append(i + 1).append(". ").append(analysis.getTestName()).append("\n");
            report.append("   Category: ").append(analysis.getCategory()).append("\n");
            report.append("   Root Cause: ").append(analysis.getRootCause()).append("\n");
            report.append("   Fix: ").append(analysis.getRecommendedFix()).append("\n");
            report.append("   Flaky: ").append(analysis.isFlaky() ? "YES ⚠️" : "NO").append("\n");
        }

        return report.toString();
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        FailureAnalysisAgent agent = new FailureAnalysisAgent();

        // Example failure
        String exceptionTrace =
                "org.openqa.selenium.NoSuchElementException: no such element: " +
                        "Unable to locate element: {\"method\":\"xpath\",\"selector\":\"//button[@id='apply-filter']\"}\n" +
                        "  at com.cyclonercm.billing.pages.DailyBillingPage.clickApplyFilter(DailyBillingPage.java:45)\n" +
                        "  at com.cyclonercm.billing.tests.DailyBillingTest.testDOSFilter(DailyBillingTest.java:30)";

        FailureAnalysis analysis = agent.analyzeFailure(
                "testDailyBillingDOSFilter",
                exceptionTrace,
                null,
                null
        );

        if (analysis != null) {
            System.out.println("\n" + analysis.toDetailedString());
        }
    }
}