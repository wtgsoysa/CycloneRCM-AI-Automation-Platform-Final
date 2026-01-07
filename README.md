# CycloneRCM AI Automation Platform

## 🚀 Enterprise AI-Powered Test Automation Platform

An advanced, AI-enabled test automation framework designed for the CycloneRCM platform. This multi-module Maven project leverages AI agents to enhance test creation, maintenance, and analysis capabilities.

### 📋 Project Overview

- **AI-Driven Testing**: Autonomous AI agents for test generation, failure analysis, and locator healing
- **Multi-Module Architecture**: Modular design for scalability and maintainability
- **Enterprise Features**: Comprehensive reporting, cloud integration, and API testing
- **Test Coverage**: 1700+ tests across Billing module with smoke, regression, and E2E scenarios

### 📁 Project Structure

```
CycloneRCM-AI-Automation-Platform-Final/
├── ai-core/                    # AI Agents (THE BRAIN)
├── common-services/            # Shared Services (AUTH & FILE)
├── business-services/          # Business Logic (BILLING & PETITION)
├── shared-libraries/           # Reusable Components & Utilities
├── reporting-hub/              # Advanced Reporting
├── test-data/                  # Test Data Repository
├── docker/                     # Containerization
├── cloud-config/               # CI/CD & Cloud Configs
├── docs/                       # Documentation
└── scripts/                    # Automation Scripts
```

### 🧠 AI Core Module

**Intelligent Agents:**
- `TestGenerationAgent`: Auto-generates tests from descriptions
- `SelfHealingAgent`: Auto-fixes broken locators
- `FailureAnalysisAgent`: Analyzes and categorizes failures
- `DataGenerationAgent`: Generates realistic test data
- `LocatorOptimizerAgent`: Optimizes element locators
- `PredictiveAgent`: Predicts flaky tests

### 🔗 Common Services

#### Authentication Service
- Login, logout, password reset workflows
- Session management
- Security testing (SQL injection, XSS, encryption)
- 40+ test cases

#### File Management Service
- File upload/download operations
- OCR data extraction
- File history and categorization
- 100+ test cases

### 💼 Business Services

#### Billing Service (1700+ Tests)
- **Daily Billing**: Filter, verify, and process daily invoices
- **Single Billing**: Individual invoice management
- **Edit Invoice**: Modify invoices with complex calculations
- **Billing History**: Track and resubmit invoices

**Test Categories:**
- Smoke Tests (56 tests)
- Functional Tests - Positive (400+ tests)
- Functional Tests - Negative (250+ tests)
- Functional Tests - Alternative (150+ tests)
- UI Tests (100+ tests)
- E2E Tests (50 tests)
- API Tests (30 tests)
- Database Tests (20 tests)

#### Petition Service (Future)
- Dashboard management
- Petition status tracking
- LACA petition handling

### 📚 Shared Libraries

- **core-framework**: Base test classes, driver management, listeners
- **common-utils**: Wait utilities, element interactions, file operations
- **page-components**: Reusable UI components (modals, tables, dropdowns)
- **data-handlers**: Excel, JSON, CSV, and database handlers
- **constants**: Locators, messages, timeouts, URLs

### 📊 Reporting Hub

- **Extent Reports**: Interactive HTML reports
- **Allure Reports**: Detailed allure-based reports
- **Custom Reports**: Email-enabled custom reports
- **Analytics**: Trend analysis and flaky test detection
- **Real-time Dashboard**: Executive dashboard with KPIs

### 🛠️ Tech Stack

| Component | Version |
|-----------|---------|
| Java | 11 |
| Selenium | 4.15.0 |
| TestNG | 7.8.1 |
| Maven | 3.6+ |
| Log4j | 2.20.0 |
| Extent Reports | 5.0.9 |
| REST Assured | 5.3.2 |
| Apache POI | 5.2.3 |

### 🚀 Getting Started

#### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome/Firefox browser
- Git

#### Installation

```bash
git clone https://github.com/cyclonercm/automation-platform.git
cd CycloneRCM-AI-Automation-Platform-Final
mvn clean install
```

#### Running Tests

```bash
# All tests
mvn clean test

# Billing Service - Smoke Tests
mvn clean test -Dsuites=src/test/resources/suites/testng-billing-smoke.xml

# Billing Service - Regression Tests
mvn clean test -Dsuites=src/test/resources/suites/testng-billing-regression-full.xml

# Authentication Service - Regression
mvn -pl common-services/authentication-service clean test
```

### 📖 Documentation

- [Architecture Guide](docs/architecture/README.md)
- [Setup Instructions](docs/guides/SETUP.md)
- [AI Agents Documentation](docs/guides/AI_AGENTS.md)
- [API Documentation](docs/api-docs/README.md)
- [Test Examples](docs/examples/README.md)
- [Changelog](docs/changelog/CHANGELOG.md)

### 🐳 Docker Support

Run tests in isolated Docker containers:

```bash
docker-compose -f docker/compose-files/selenium-grid.yml up
mvn clean test -Drunner=docker
```

### ☁️ Cloud Integration

- **GitHub Actions**: CI/CD workflows
- **Jenkins**: Enterprise Jenkins integration
- **Kubernetes**: Container orchestration
- **AWS**: Cloud deployment
- **BrowserStack**: Cross-browser testing
- **SauceLabs**: Mobile and web testing

### 📈 Test Metrics

- **Total Test Cases**: 2000+
- **Billing Module**: 1700 tests
- **Authentication Module**: 40 tests
- **File Management Module**: 100 tests
- **Average Execution Time**: 8-10 hours (parallel)

### 🤝 Contributing

1. Create a feature branch
2. Commit changes
3. Push to branch
4. Create Pull Request

### 📝 License

Licensed under the MIT License. See [LICENSE](LICENSE) file for details.

### 📧 Support

For issues and questions, please contact: support@cyclonercm.com

### 🔗 Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Guide](https://testng.org/doc/)
- [Maven Documentation](https://maven.apache.org/)

---

**Last Updated**: 2026-01-06  
**Maintained By**: QA Automation Team
