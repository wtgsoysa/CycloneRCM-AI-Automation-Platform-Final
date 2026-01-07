package com.cyclonercm.ai.agents;

import com.cyclonercm.ai.core.BaseAIAgent;
import com.cyclonercm.ai.models.GeneratedTestCode;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Test Generation Agent
 * Generates complete test code from natural language descriptions
 *
 * USAGE:
 * TestGenerationAgent agent = new TestGenerationAgent();
 * String testCode = agent.generateTest(
 *     "Create a positive test for Daily Billing DOS filter with valid date 10/02/2025"
 * );
 *
 * @author Nolo (Senior QA AI Agent)
 * @version 1.0
 * @date January 06, 2026
 */
public class TestGenerationAgent extends BaseAIAgent {

    private static final String SYSTEM_PROMPT =
            "You are an expert QA automation engineer specializing in Selenium, Java, and TestNG. " +
                    "Your task is to generate PRODUCTION-READY test code for healthcare RCM systems. " +
                    "\n\n" +
                    "CRITICAL RULES:\n" +
                    "1. Use Page Object Model pattern\n" +
                    "2. Include proper TestNG annotations (@Test, @BeforeClass, @AfterClass)\n" +
                    "3. Add descriptive test names and documentation\n" +
                    "4. Use explicit waits (NOT Thread.sleep)\n" +
                    "5. Include assertions with meaningful messages\n" +
                    "6. Add ExtentReports logging\n" +
                    "7. Follow Java naming conventions\n" +
                    "8. Include proper package declarations\n" +
                    "9. Add imports at the top\n" +
                    "10. Generate ONLY the Java code, no explanations\n" +
                    "\n" +
                    "FRAMEWORK DETAILS:\n" +
                    "- Base class: extends BaseTest\n" +
                    "- Page objects available: DailyBillingPage, SingleBillingPage, EditInvoicePage, etc.\n" +
                    "- Utilities: WaitUtils, ElementUtils, TestDataProperties\n" +
                    "- Reporting: ExtentManager, test.info(), test.pass(), test.fail()\n" +
                    "- Workflows: LoginWorkflow, FileUploadWorkflow\n";

    /**
     * Constructor
     */
    public TestGenerationAgent() {
        super();
        this.maxTokens = 4000; // Tests can be long
        this.temperature = 0.3; // Low creativity for consistent code
    }

    @Override
    public String execute(String input) {
        return generateTest(input);
    }

    @Override
    public String getAgentName() {
        return "TestGenerationAgent";
    }

    /**
     * Generate test code from natural language description
     *
     * @param testDescription Natural language description of the test
     * @return Generated Java test code
     */
    public String generateTest(String testDescription) {
        System.out.println("\n🤖 " + getAgentName() + " is generating test...");
        System.out.println("📝 Description: " + testDescription);

        if (!isConfigured()) {
            return "ERROR: ANTHROPIC_API_KEY not configured. Cannot generate test.";
        }

        // Build user prompt
        String userPrompt =
                "Generate a complete TestNG test class based on this description:\n\n" +
                        testDescription + "\n\n" +
                        "The test should:\n" +
                        "- Be in package: com.cyclonercm.billing.tests.regression.dailybilling.functional.positive\n" +
                        "- Follow the Page Object Model pattern\n" +
                        "- Include @BeforeClass setup (using LoginWorkflow and FileUploadWorkflow)\n" +
                        "- Have detailed assertions\n" +
                        "- Include ExtentReports logging\n" +
                        "- Be production-ready\n\n" +
                        "Return ONLY the Java code, nothing else.";

        // Call AI
        String generatedCode = callClaudeAPI(SYSTEM_PROMPT, userPrompt);

        // Clean up response (remove markdown code blocks if present)
        generatedCode = cleanGeneratedCode(generatedCode);

        System.out.println("✅ Test generated successfully!");
        System.out.println("📄 Lines of code: " + generatedCode.split("\n").length);

        return generatedCode;
    }

    /**
     * Generate test and save to file
     *
     * @param testDescription Test description
     * @param outputPath File path to save the test
     * @return GeneratedTestCode object with metadata
     */
    public GeneratedTestCode generateAndSaveTest(String testDescription, String outputPath) {
        String code = generateTest(testDescription);

        try {
            FileWriter writer = new FileWriter(outputPath);
            writer.write(code);
            writer.close();

            System.out.println("💾 Test saved to: " + outputPath);

            return new GeneratedTestCode(
                    code,
                    extractClassName(code),
                    outputPath,
                    LocalDateTime.now(),
                    true,
                    "Test generated and saved successfully"
            );

        } catch (IOException e) {
            System.err.println("❌ Error saving test: " + e.getMessage());
            return new GeneratedTestCode(
                    code,
                    extractClassName(code),
                    outputPath,
                    LocalDateTime.now(),
                    false,
                    "Error saving file: " + e.getMessage()
            );
        }
    }

    /**
     * Generate multiple tests from a list of descriptions
     *
     * @param descriptions Array of test descriptions
     * @return Array of generated test codes
     */
    public String[] generateBatchTests(String[] descriptions) {
        System.out.println("\n🚀 Batch Test Generation Started");
        System.out.println("📊 Total tests to generate: " + descriptions.length);

        String[] generatedTests = new String[descriptions.length];

        for (int i = 0; i < descriptions.length; i++) {
            System.out.println("\n--- Generating test " + (i + 1) + "/" + descriptions.length + " ---");
            generatedTests[i] = generateTest(descriptions[i]);

            // Small delay to avoid API rate limits
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n✅ Batch generation complete!");
        return generatedTests;
    }

    /**
     * Generate test with specific classification
     *
     * @param testDescription Test description
     * @param testType Test type (positive, negative, alternative, ui)
     * @return Generated test code
     */
    public String generateTestByType(String testDescription, String testType) {
        String enhancedDescription = testDescription + "\n\n" +
                "TEST TYPE: " + testType.toUpperCase() + "\n";

        switch (testType.toLowerCase()) {
            case "positive":
                enhancedDescription += "This is a POSITIVE test - use valid inputs and expect success.";
                break;
            case "negative":
                enhancedDescription += "This is a NEGATIVE test - use invalid inputs and expect errors.";
                break;
            case "alternative":
                enhancedDescription += "This is an ALTERNATIVE test - use different valid paths.";
                break;
            case "ui":
                enhancedDescription += "This is a UI test - validate visual elements, layout, and styling.";
                break;
            default:
                enhancedDescription += "This is a FUNCTIONAL test.";
        }

        return generateTest(enhancedDescription);
    }

    /**
     * Clean generated code (remove markdown, extra whitespace, etc.)
     */
    private String cleanGeneratedCode(String code) {
        // Remove markdown code blocks
        code = code.replaceAll("```java\\n?", "");
        code = code.replaceAll("```\\n?", "");

        // Trim whitespace
        code = code.trim();

        return code;
    }

    /**
     * Extract class name from generated code
     */
    private String extractClassName(String code) {
        try {
            // Look for "public class ClassName"
            int classIndex = code.indexOf("public class ");
            if (classIndex != -1) {
                int start = classIndex + "public class ".length();
                int end = code.indexOf(" ", start);
                if (end == -1) {
                    end = code.indexOf("{", start);
                }
                return code.substring(start, end).trim();
            }
        } catch (Exception e) {
            // Ignore
        }
        return "UnknownTest";
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        TestGenerationAgent agent = new TestGenerationAgent();

        // Example 1: Simple test generation
        String testCode = agent.generateTest(
                "Create a positive test for Daily Billing DOS filter. " +
                        "The test should select DOS 10/02/2025 and verify invoices are displayed."
        );
        System.out.println("\n" + testCode);

        // Example 2: Generate and save
        // GeneratedTestCode result = agent.generateAndSaveTest(
        //     "Create a negative test for invalid DOS filter",
        //     "src/test/java/.../InvalidDOSFilterTest.java"
        // );

        // Example 3: Batch generation
        // String[] descriptions = {
        //     "Test 1 description",
        //     "Test 2 description",
        //     "Test 3 description"
        // };
        // String[] tests = agent.generateBatchTests(descriptions);
    }
}