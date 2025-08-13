# Volt Money Automation Framework

## 📌 Project Overview
This is a **Java + Selenium + TestNG** automation framework built to validate the **Volt Money Eligibility Page** and its related UI functionalities.  
It follows **Page Object Model (POM)** design, supports **cross-browser testing**, integrates with **ExtentReports** for HTML reporting, and includes data-driven test scenarios.

---

## 🛠 Tech Stack
- **Java 17
- **Selenium WebDriver 4.15.0
- **TestNG 7.8.0
- **Maven** (Build & Dependency Management)
- **WebDriverManager** (Automatic driver management)
- **ExtentReports** (HTML Reports)
- **SLF4J + Logback** (Logging)

---

## 📂 Project Structure
```
volt-automation-framework/
│
├── src/main/java
│   ├── pages/          # Page Object classes
│   ├── utils/          # Utilities (DriverFactory, ConfigReader, ExtentManager)
│   └── testdata/       # Centralized test data
│
├── src/test/java
│   ├── tests/          # Test classes (BaseTest, FunctionalTests)
│
├── src/test/resources  # Config files, test data, properties
│
├── pom.xml             # Maven dependencies
└── README.md           # Project documentation
```

---

## ⚙️ Configuration
The project uses `src/test/resources/config.properties` for configuration:

```properties
browser=chrome
headless=false
implicit.wait=10
explicit.wait=30
page.load.timeout=60
base.url=https://voltmoney.in/check-loan-eligibility-against-mutual-funds
environment=prod
screenshot.path=screenshots/
report.path=reports/
```

You can override these using **Maven command line parameters**:
```bash
mvn clean test -Dbrowser=firefox -Dheadless=true -Denvironment=qa
```

---

## 🚀 How to Run Tests

### 1. Clone the repository
```bash
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>
```

### 2. Install dependencies
```bash
mvn clean install
```

### 3. Run all tests
```bash
mvn clean test
```

### 4. Run specific TestNG groups
```bash
mvn clean test -Dgroups="smoke"
```

---

## 📊 Reports
After execution, **ExtentReports** HTML report will be generated in:
```
/reports/ExtentReport_<timestamp>.html
```
Open the report in your browser to view detailed results with screenshots.

---

## 🧪 Features
- **Page Object Model (POM)** for maintainability
- **Cross-Browser Testing** (Chrome, Firefox, Edge)
- **Headless Mode** support
- **Data-Driven Testing** from centralized `TestData.java`
- **Logging** with SLF4J
- **Automatic Driver Management** via WebDriverManager
- **Custom HTML Reports** with screenshots

---

## 📜 Test Categories
| Group           | Description |
|-----------------|-------------|
| smoke           | Quick sanity checks |
| regression      | Full regression tests |
| critical        | High-priority flows |
| validation      | Input validation scenarios |
| ui              | UI & element visibility checks |

---

## 📌 Author
*Yogesh davaragave
yogeshrd07@gmail.com

---

