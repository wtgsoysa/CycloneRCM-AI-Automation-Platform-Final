package com.cyclonercm.ai.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Base AI Agent
 * All AI agents extend this class to interact with Claude AI API
 *
 * @author Nolo (Senior QA AI Agent)
 * @version 1.0
 * @date January 06, 2026
 */
public abstract class BaseAIAgent {

    // Claude AI API Configuration
    private static final String CLAUDE_API_URL = "https://api.anthropic.com/v1/messages";
    private static final String CLAUDE_MODEL = "claude-sonnet-4-20250514";
    private static final String API_VERSION = "2023-06-01";

    // API Key (Load from environment variable for security)
    private final String apiKey;

    // Agent Configuration
    protected int maxTokens = 4000;
    protected double temperature = 0.7;

    /**
     * Constructor
     * Loads API key from environment variable
     */
    public BaseAIAgent() {
        this.apiKey = System.getenv("ANTHROPIC_API_KEY");
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            System.err.println("⚠️  WARNING: ANTHROPIC_API_KEY environment variable not set!");
            System.err.println("   Set it using: export ANTHROPIC_API_KEY=your_api_key");
        }
    }

    /**
     * Send prompt to Claude AI and get response
     *
     * @param systemPrompt System context for the AI
     * @param userPrompt User's actual request
     * @return AI response text
     */
    protected String callClaudeAPI(String systemPrompt, String userPrompt) {
        try {
            // Create connection
            URL url = new URL(CLAUDE_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-api-key", apiKey);
            conn.setRequestProperty("anthropic-version", API_VERSION);
            conn.setDoOutput(true);

            // Build request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", CLAUDE_MODEL);
            requestBody.put("max_tokens", maxTokens);
            requestBody.put("temperature", temperature);
            requestBody.put("system", systemPrompt);

            // Add messages
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", userPrompt);
            messages.put(userMessage);
            requestBody.put("messages", messages);

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                // Success
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                // Parse response JSON
                JSONObject responseJson = new JSONObject(response.toString());
                JSONArray content = responseJson.getJSONArray("content");
                if (content.length() > 0) {
                    JSONObject firstContent = content.getJSONObject(0);
                    return firstContent.getString("text");
                }

                return "No response from AI";

            } else {
                // Error
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errorResponse = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    errorResponse.append(responseLine.trim());
                }
                br.close();

                System.err.println("❌ Claude API Error: " + responseCode);
                System.err.println("   Response: " + errorResponse.toString());

                return "ERROR: API call failed - " + errorResponse.toString();
            }

        } catch (Exception e) {
            System.err.println("❌ Exception calling Claude API: " + e.getMessage());
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Load prompt template from resources
     *
     * @param templateName Name of the template file
     * @return Template content
     */
    protected String loadPromptTemplate(String templateName) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            getClass().getResourceAsStream("/prompts/templates/" + templateName),
                            StandardCharsets.UTF_8
                    )
            );
            StringBuilder template = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                template.append(line).append("\n");
            }
            reader.close();
            return template.toString();
        } catch (Exception e) {
            System.err.println("⚠️  Could not load template: " + templateName);
            return "";
        }
    }

    /**
     * Set maximum tokens for AI response
     */
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    /**
     * Set temperature (creativity) for AI responses
     * 0.0 = deterministic, 1.0 = creative
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Check if API key is configured
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isEmpty();
    }

    /**
     * Abstract method that each agent must implement
     * This is the main entry point for agent functionality
     */
    public abstract String execute(String input);

    /**
     * Get agent name (for logging)
     */
    public abstract String getAgentName();
}