# Setup Instructions

## Prerequisites
- **Java 11 or higher**
- **Maven 3.6+**
- **Git**
- **Chrome/Firefox Browser** (for UI tests)
- **IDE** (VS Code, IntelliJ IDEA, or Eclipse)

## Installation Steps

### 1. Clone the Repository
```bash
git clone https://github.com/cyclonercm/automation-platform.git
cd CycloneRCM-AI-Automation-Platform-Final
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Environment
```bash
# Copy and customize environment configuration
cp test-data/config/environments/qa.properties test-data/config/environments/qa.local.properties

# Edit qa.local.properties with your credentials
```

### 4. Setup WebDriver
The project uses WebDriver Manager to handle driver downloads automatically.

### 5. Verify Installation
```bash
# Run a smoke test
mvn -pl common-services/authentication-service test \
  -Dsuites=src/test/resources/suites/testng-auth-smoke.xml
```

## Project Structure Overview

```
CycloneRCM-AI-Automation-Platform-Final/
├── ai-core/                  # AI agents and ML models
├── common-services/          # Shared authentication and file services
├── business-services/        # Billing and petition services
├── shared-libraries/         # Reusable utilities and components
├── reporting-hub/            # Advanced reporting
├── test-data/                # Test configurations and data
├── docker/                   # Docker configurations
├── cloud-config/             # CI/CD configurations
├── docs/                     # Documentation
└── scripts/                  # Automation scripts
```

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Module
```bash
# Authentication Service
mvn -pl common-services/authentication-service clean test

# Billing Service
mvn -pl business-services/billing-service clean test
```

### Run Specific Test Suite
```bash
mvn clean test -Dsuites=path/to/testng.xml
```

### Run with Custom Configuration
```bash
mvn clean test \
  -Denvironment=uat \
  -Dbrowser=firefox \
  -Dheadless=true
```

## IDE Setup

### IntelliJ IDEA
1. Open project
2. File → Project Structure → Set Java version to 11
3. Maven should be auto-detected
4. Right-click test class → Run or Debug

### VS Code
1. Install "Extension Pack for Java"
2. Install "Maven for Java"
3. Open command palette (Ctrl+Shift+P)
4. Select "Maven: Run" or "Maven: Debug"

## Docker Setup

### Build and Run with Docker
```bash
docker-compose -f docker/compose-files/selenium-grid.yml up
mvn clean test -Drunner=docker
```

## Troubleshooting

### WebDriver Issues
```bash
# Clear WebDriver cache
rm -rf ~/.wdm/

# Re-download drivers
mvn clean test
```

### Maven Issues
```bash
# Clean and rebuild
mvn clean install -DskipTests

# Update dependencies
mvn dependency:tree
```

### Test Execution Issues
- Check browser compatibility
- Verify environment URL
- Review application logs
- Enable debug logging in log4j2.xml

## Next Steps
1. Review [Architecture Guide](ARCHITECTURE.md)
2. Explore [AI Agents Documentation](AI_AGENTS.md)
3. Run sample tests from `docs/examples/`
4. Configure CI/CD pipeline from `cloud-config/`

For issues, contact: support@cyclonercm.com
