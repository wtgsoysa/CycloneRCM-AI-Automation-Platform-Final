# Documentation: AI Agents Guide

## Overview
The AI Core module provides intelligent agents for test automation enhancement:
- **TestGenerationAgent**: Automatically generates test cases from natural language descriptions
- **SelfHealingAgent**: Identifies and repairs broken element locators
- **FailureAnalysisAgent**: Analyzes test failures and categorizes root causes
- **DataGenerationAgent**: Generates realistic test data
- **LocatorOptimizerAgent**: Optimizes element locator strategies
- **PredictiveAgent**: Predicts flaky tests before execution

## Configuration
Edit `test-data/config/ai-agents/ai-config.properties` to enable/disable agents and set parameters.

## Usage Examples

### Test Generation
```java
TestGenerationAgent agent = new TestGenerationAgent();
String testDescription = "User should be able to login with valid credentials";
GeneratedTestCode testCode = agent.generateTest(testDescription);
```

### Self Healing
```java
SelfHealingAgent healer = new SelfHealingAgent();
boolean isHealed = healer.healLocator(brokenLocator, targetElement);
```

### Failure Analysis
```java
FailureAnalysisAgent analyzer = new FailureAnalysisAgent();
FailureAnalysis analysis = analyzer.analyzeFailure(testResult, screenshot);
```

## Best Practices
1. Always configure AI settings before test execution
2. Review generated test code before deployment
3. Monitor AI-suggested locators for accuracy
4. Use predictive agent to identify flaky tests early

For more details, see `ai-core/src/main/java/com/cyclonercm/ai/agents/`
