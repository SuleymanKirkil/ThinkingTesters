# Thinking Testers Test Automation Framework

## Overview
This is a robust test automation framework built using Java, Selenium WebDriver, and Cucumber BDD. The framework is designed to support both UI and API testing with a focus on maintainability, readability, and extensibility.

## Architecture

### Key Components
- **Page Objects**: Encapsulates the UI elements and their interactions
- **Step Definitions**: Implements the Cucumber step definitions
- **API Requests**: Handles API interactions using RestAssured
- **Utilities**: Common utilities and helpers for the framework
- **Hooks**: Manages test lifecycle and configurations
- **Known Issue Handler**: Sophisticated system for managing known issues and test skipping

### Directory Structure
```
src/
├── test/
│   ├── java/
│   │   └── com/
│   │       └── thinkingtesters/
│   │           ├── apirequest/    # API request handlers
│   │           ├── driver/        # WebDriver management
│   │           ├── hooks/         # Cucumber hooks
│   │           ├── pages/         # Page objects
│   │           ├── steps/         # Step definitions
│   │           └── utils/         # Utilities and helpers
│   └── resources/
│       ├── features/             # Cucumber feature files
│       └── config/               # Configuration files
```

## Setup and Configuration

### Prerequisites
- Java JDK 11 veya üzeri
- Maven
- Chrome/Firefox tarayıcı
- Allure Report (isteğe bağlı, raporlama için)

### Kullanılan Teknolojiler ve Versiyonlar
- Selenium WebDriver: 4.16.1
- Cucumber: 7.15.0
- RestAssured: 5.5.0
- Allure Report: 2.24.0
- Log4j: 2.22.1
- AssertJ: 3.24.2
- Lombok: 1.18.30
- Jackson: 2.16.1

### Installation
1. Projeyi klonlayın
2. Bağımlılıkları yükleyin:
```bash
mvn clean install
```

## Running Tests

### Tüm Testleri Çalıştırma
```bash
mvn clean test
```

### Belirli Özellikleri Çalıştırma
```bash
mvn test -Dcucumber.filter.tags="@SignUpFeatures"
```

### Test Ortamı Yapılandırması
Test ortamını ayarlamak için:
```bash
mvn test -Dtest.environment=staging
```

### Headless Mode'da Çalıştırma
```bash
mvn test -Dheadless=true
```

### Allure Raporu Oluşturma
```bash
mvn allure:report
```

## Features

### BDD Testing
- Cucumber tabanlı BDD implementasyonu
- Gherkin sözdiziminde yazılmış özellik dosyaları
- Organize edilmiş adım tanımlamaları

### CI/CD Entegrasyonu
- GitHub Actions ile otomatik test çalıştırma
- Paralel test çalıştırma desteği
- QA ve Staging ortamları için ayrı test koşumları
- Test sonuçlarının Allure Report ile raporlanması

### Known Issue Management
The framework includes a sophisticated system for handling known issues:

```gherkin
# Basic known issue
@KnownIssue
Scenario: Feature with known issue

# With JIRA reference
@KnownIssue @bug=JIRA-123
Scenario: Feature with tracked issue

# Environment specific
@KnownIssue @bug=JIRA-123 @env=staging
Scenario: Environment-specific issue

# With expiration date
@KnownIssue @bug=JIRA-123 @expires=2024-03-01
Scenario: Temporary known issue
```

### Screenshot Capture
- Automatic screenshot capture on test failure
- Screenshots stored in organized directory structure

### API Testing
- RestAssured integration for API testing
- Reusable API request methods
- Response validation utilities

## Best Practices

### Writing Tests
1. Follow the Page Object pattern for UI elements
2. Keep step definitions simple and reusable
3. Use appropriate tags for test organization
4. Implement proper wait strategies

### Known Issues
1. Use `@KnownIssue` tag for temporarily skipping tests
2. Always include a bug reference (`@bug=`)
3. Set expiration dates for known issues (`@expires=`)
4. Use environment-specific tags when applicable

## Contributing
1. Create a feature branch
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## Documentation
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [RestAssured Documentation](https://rest-assured.io/)

## Support
For support and questions, please contact the test automation team or create an issue in the repository.

## License
This project is licensed under the MIT License - see the LICENSE file for details
