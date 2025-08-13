package tests;

import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ExtentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected ExtentTest extentTest;

    private String browser;
    private boolean headless;
    private String environment;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser", "headless", "environment"})
    public void beforeSuite(
            @Optional("chrome") String browserParam,
            @Optional("false") String headlessParam,
            @Optional("prod") String environmentParam) {

        logger.info("=== STARTING TEST SUITE EXECUTION ===");

        // Load configuration with system property override
        browser = System.getProperty("browser",
                  browserParam != null ? browserParam : ConfigReader.getBrowser());
        headless = Boolean.parseBoolean(System.getProperty("headless", headlessParam));
        environment = System.getProperty("environment", environmentParam);

        ExtentManager.createInstance();
        logger.info("ExtentReports initialized");

        // Initialize driver ONCE for the entire suite
        DriverFactory.initializeDriverWithAutoVersion(browser, headless);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(java.lang.reflect.Method method) {
        extentTest = ExtentManager.createTest(method.getName(), getTestDescription(method));
        extentTest.info("Test started: " + method.getName());
        logger.info("Starting test method: {}", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        try {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    extentTest.pass("Test passed successfully");
                    logger.info("Test PASSED: {}", testName);
                    break;

                case ITestResult.FAILURE:
                    extentTest.fail("Test failed: " + result.getThrowable().getMessage());
                    logger.error("Test FAILED: {} - {}", testName, result.getThrowable().getMessage());
                    captureFailureScreenshot();
                    break;

                case ITestResult.SKIP:
                    extentTest.skip("Test skipped: " + (result.getThrowable() != null ? result.getThrowable().getMessage() : ""));
                    logger.info("Test SKIPPED: {}", testName);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in afterMethod for test: {}", testName, e);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        try {
            DriverFactory.quitDriver();
            ExtentManager.flush();
            logger.info("ExtentReports flushed successfully");
        } catch (Exception e) {
            logger.error("Error during suite cleanup", e);
        }
        logger.info("=== TEST SUITE EXECUTION COMPLETED ===");
    }

    private void captureFailureScreenshot() {
        if (DriverFactory.isDriverInitialized()) {
            try {
                String screenshot = DriverFactory.takeScreenshotBase64();
                extentTest.addScreenCaptureFromBase64String(screenshot, "Failure Screenshot");
            } catch (Exception e) {
                logger.warn("Could not capture screenshot: {}", e.getMessage());
            }
        }
    }

    private String getTestDescription(java.lang.reflect.Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        return (testAnnotation != null && !testAnnotation.description().isEmpty())
                ? testAnnotation.description()
                : "Test method: " + method.getName();
    }

    protected void logTestStep(String stepDescription) {
        extentTest.info(stepDescription);
        logger.debug("Test Step: {}", stepDescription);
    }

    protected void logTestStepWithScreenshot(String stepDescription) {
        extentTest.info(stepDescription);
        if (DriverFactory.isDriverInitialized()) {
            try {
                String screenshot = DriverFactory.takeScreenshotBase64();
                extentTest.addScreenCaptureFromBase64String(screenshot, stepDescription);
            } catch (Exception e) {
                logger.warn("Could not capture screenshot for step: {}", stepDescription);
            }
        }
        logger.debug("Test Step with Screenshot: {}", stepDescription);
    }
}
