# CI/CD Entegrasyonu Rehberi

## İçindekiler
1. [GitHub Actions ile CI/CD Pipeline](#github-actions-ile-cicd-pipeline)
2. [Kurulum ve Yapılandırma](#kurulum-ve-yapılandırma)
3. [İyileştirme Önerileri](#iyileştirme-önerileri)
4. [CI/CD İş Akışı](#cicd-iş-akışı)
5. [Test Stratejisi](#test-stratejisi)
6. [Monitoring ve Alerting](#monitoring-ve-alerting)
7. [Güvenlik Önlemleri](#güvenlik-önlemleri)
8. [Troubleshooting](#troubleshooting)

## GitHub Actions ile CI/CD Pipeline

### Temel Özellikler

#### 1. Otomatik Tetikleme
- **Push Events**:
  ```yaml
  on:
    push:
      branches: [ main, develop, 'feature/*' ]
      paths-ignore:
        - '**.md'
        - 'docs/**'
  ```
- **Pull Request Events**:
  ```yaml
  pull_request:
    types: [opened, synchronize, reopened]
    branches: [ main, develop ]
  ```
- **Scheduled Events**:
  ```yaml
  schedule:
    - cron: '0 0 * * *'  # Her gece yarısı
  ```
- **Manual Events**:
  ```yaml
  workflow_dispatch:
    inputs:
      environment:
        description: 'Test ortamı'
        required: true
        default: 'qa'
  ```

#### 2. Paralel Test Çalıştırma
```yaml
strategy:
  matrix:
    java: [11, 17]
    environment: [qa, staging]
  fail-fast: false
  max-parallel: 4
```

#### 3. Caching Mekanizmaları
```yaml
- name: Cache Maven packages
  uses: actions/cache@v3
  with:
    path: ~/.m2
    key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
    restore-keys: ${{ runner.os }}-m2

- name: Cache Browser Binaries
  uses: actions/cache@v3
  with:
    path: ~/.cache/selenium
    key: ${{ runner.os }}-selenium-${{ hashFiles('**/pom.xml') }}
```

## Kurulum ve Yapılandırma

### 1. GitHub Secrets
```bash
# Required Secrets
SLACK_WEBHOOK_URL: Slack bildirimleri için webhook
SONAR_TOKEN: SonarQube analizi için token
DOCKER_USERNAME: Docker registry erişimi
DOCKER_PASSWORD: Docker registry erişimi

# Optional Secrets
BROWSERSTACK_USERNAME: Cross-browser testing
BROWSERSTACK_ACCESS_KEY: Cross-browser testing
```

### 2. Test Çalıştırma Komutları

#### Tüm Test Süiti
```bash
mvn clean test
```

#### Belirli Test Grupları
```bash
# Smoke Tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# Regression Tests
mvn clean test -Dcucumber.filter.tags="@regression"

# API Tests
mvn clean test -Dcucumber.filter.tags="@api"
```

#### Ortam Bazlı Testler
```bash
# QA Environment
mvn clean test -Dtest.environment=qa

# Staging Environment
mvn clean test -Dtest.environment=staging
```

### 3. Raporlama Komutları

#### Allure Reports
```bash
# Report Generation
mvn allure:report

# Start Report Server
mvn allure:serve
```

#### Custom Reports
```bash
# Test Execution Summary
mvn surefire-report:report

# Coverage Report
mvn jacoco:report
```

## Test Stratejisi

### 1. Test Piramidi
```
     UI Tests (10%)
    /            \
   /  API Tests   \
  /     (20%)      \
 /                  \
/ Unit Tests (70%)   \
----------------------
```

### 2. Test Kategorileri
- **Smoke Tests**: Kritik fonksiyonlar
- **Regression Tests**: Tüm fonksiyonlar
- **API Tests**: Servis kontrolleri
- **UI Tests**: Arayüz kontrolleri
- **Performance Tests**: Yük testleri

### 3. Test Execution Stratejisi
```yaml
jobs:
  smoke_tests:
    runs-on: ubuntu-latest
    steps:
      - name: Run Smoke Tests
        run: mvn test -Dcucumber.filter.tags="@smoke"

  regression_tests:
    needs: smoke_tests
    runs-on: ubuntu-latest
    steps:
      - name: Run Regression Tests
        run: mvn test -Dcucumber.filter.tags="@regression"
```

## Monitoring ve Alerting

### 1. Test Metrikleri
```yaml
- name: Collect Test Metrics
  run: |
    echo "TEST_SUCCESS_RATE=$(grep -o 'Tests run:.*' target/surefire-reports/*.txt | awk '{s+=$3; f+=$5} END {print s/(s+f)*100}')" >> $GITHUB_ENV
```

### 2. Slack Bildirimleri
```yaml
- name: Send Slack Notification
  if: always()
  uses: 8398a7/action-slack@v3
  with:
    status: ${{ job.status }}
    fields: repo,message,commit,author,action,eventName,ref,workflow,job,took
```

### 3. Email Bildirimleri
```yaml
- name: Send Email
  if: failure()
  uses: dawidd6/action-send-mail@v3
  with:
    server_address: smtp.gmail.com
    server_port: 465
    username: ${{ secrets.EMAIL_USERNAME }}
    password: ${{ secrets.EMAIL_PASSWORD }}
    subject: Test Execution Failed
    body: Test execution failed in ${{ github.workflow }}
```

## Güvenlik Önlemleri

### 1. Dependency Check
```yaml
- name: OWASP Dependency Check
  run: mvn org.owasp:dependency-check-maven:check
```

### 2. Code Quality
```yaml
- name: SonarQube Analysis
  run: mvn sonar:sonar
  env:
    SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
```

### 3. Secret Scanning
```yaml
- name: Secret Scanning
  uses: gitleaks/gitleaks-action@v2
```

## Troubleshooting

### 1. Yaygın Hatalar

#### Test Failures
```bash
# Test loglarını görüntüle
cat target/surefire-reports/*.txt

# Screenshot'ları kontrol et
ls -la target/screenshots/

# Allure raporunu incele
mvn allure:serve
```

#### Pipeline Failures
```bash
# GitHub Actions loglarını kontrol et
gh run view --log

# Job'ı yeniden çalıştır
gh run rerun
```

### 2. Debug Mode
```yaml
- name: Enable Debug Mode
  run: |
    echo "MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000" >> $GITHUB_ENV
    mvn test
```

### 3. Log Analizi
```bash
# Test execution sürelerini analiz et
find target/surefire-reports -name "TEST-*.xml" -exec grep "testcase" {} \; | awk -F'"' '{print $2, $4}'

# Hata pattern'lerini analiz et
grep -r "Exception" target/surefire-reports/
