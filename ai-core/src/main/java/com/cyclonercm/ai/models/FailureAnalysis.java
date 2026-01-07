package com.cyclonercm.ai.models;

/**
 * Failure Analysis Model
 * Represents analysis of a test failure
 *
 * @author Nolo (Senior QA AI Agent)
 * @version 1.0
 * @date January 06, 2026
 */
public class FailureAnalysis {

    private String testName;
    private String category;
    private String rootCause;
    private String likelyReason;
    private String recommendedFix;
    private String prevention;
    private boolean isFlaky;
    private int confidence;

    /**
     * Constructor
     */
    public FailureAnalysis(String testName, String category, String rootCause,
                           String likelyReason, String recommendedFix, String prevention,
                           boolean isFlaky, int confidence) {
        this.testName = testName;
        this.category = category;
        this.rootCause = rootCause;
        this.likelyReason = likelyReason;
        this.recommendedFix = recommendedFix;
        this.prevention = prevention;
        this.isFlaky = isFlaky;
        this.confidence = confidence;
    }

    // Getters
    public String getTestName() { return testName; }
    public String getCategory() { return category; }
    public String getRootCause() { return rootCause; }
    public String getLikelyReason() { return likelyReason; }
    public String getRecommendedFix() { return recommendedFix; }
    public String getPrevention() { return prevention; }
    public boolean isFlaky() { return isFlaky; }
    public int getConfidence() { return confidence; }

    // Setters
    public void setTestName(String testName) { this.testName = testName; }
    public void setCategory(String category) { this.category = category; }
    public void setRootCause(String rootCause) { this.rootCause = rootCause; }
    public void setLikelyReason(String likelyReason) { this.likelyReason = likelyReason; }
    public void setRecommendedFix(String recommendedFix) { this.recommendedFix = recommendedFix; }
    public void setPrevention(String prevention) { this.prevention = prevention; }
    public void setFlaky(boolean flaky) { isFlaky = flaky; }
    public void setConfidence(int confidence) { this.confidence = confidence; }

    /**
     * Get severity based on category
     */
    public String getSeverity() {
        switch (category.toUpperCase()) {
            case "ELEMENT_NOT_FOUND":
            case "TIMEOUT":
                return "HIGH";
            case "ASSERTION_FAILED":
            case "STALE_ELEMENT":
                return "MEDIUM";
            case "JAVASCRIPT_ERROR":
            case "DRIVER_ERROR":
                return "LOW";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Get priority (1-5, 1=highest)
     */
    public int getPriority() {
        if (isFlaky) return 1; // Flaky tests are highest priority

        switch (getSeverity()) {
            case "HIGH": return 2;
            case "MEDIUM": return 3;
            case "LOW": return 4;
            default: return 5;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test: ").append(testName).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Root Cause: ").append(rootCause).append("\n");
        sb.append("Recommended Fix: ").append(recommendedFix).append("\n");
        sb.append("Flaky: ").append(isFlaky ? "YES ⚠️" : "NO").append("\n");
        sb.append("Confidence: ").append(confidence).append("%\n");

        return sb.toString();
    }

    /**
     * Get detailed string representation
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════╗\n");
        sb.append("║           FAILURE ANALYSIS                                     ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════╝\n\n");

        sb.append("Test Name: ").append(testName).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Severity: ").append(getSeverity()).append("\n");
        sb.append("Priority: ").append(getPriority()).append(" (1=highest)\n");
        sb.append("Confidence: ").append(confidence).append("%\n");
        sb.append("Is Flaky: ").append(isFlaky ? "YES ⚠️" : "NO").append("\n\n");

        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append("ROOT CAUSE:\n");
        sb.append(rootCause).append("\n\n");

        sb.append("LIKELY REASON:\n");
        sb.append(likelyReason).append("\n\n");

        sb.append("─────────────────────────────────────────────────────────────────\n");
        sb.append("RECOMMENDED FIX:\n");
        sb.append(recommendedFix).append("\n\n");

        sb.append("PREVENTION:\n");
        sb.append(prevention).append("\n");

        sb.append("─────────────────────────────────────────────────────────────────\n");

        return sb.toString();
    }
}