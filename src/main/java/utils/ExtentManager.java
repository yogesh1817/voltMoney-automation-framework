package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports manager class
 * Handles ExtentReports configuration and test reporting
 */
public class ExtentManager {

    private static final Logger logger = LoggerFactory.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    /**
     * Safely create an ExtentReports instance (idempotent).
     * Call this before any tests run to ensure reporting is set up.
     */
    public static synchronized void createInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportPath = ConfigReader.getReportPath() + "ExtentReport_" + timestamp + ".html";

            // Create reports directory if it doesn't exist
            File reportsDir = new File(ConfigReader.getReportPath());
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            configureSparkReporter(sparkReporter);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            setSystemInfo();

            logger.info("ExtentReports initialized. Report will be saved at: {}", reportPath);
        } else {
            logger.info("ExtentReports already initialized, skipping re-creation");
        }
    }

    /**
     * Configure Spark Reporter
     */
    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setDocumentTitle("Volt Money Automation Report");
        sparkReporter.config().setReportName("Eligibility Page Test Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        // Custom CSS for better appearance
        String customCSS = """
            .badge-success { background-color: #28a745 !important; }
            .badge-danger { background-color: #dc3545 !important; }
            .badge-warning { background-color: #ffc107 !important; }
            .badge-info { background-color: #17a2b8 !important; }
            """;
        sparkReporter.config().setCss(customCSS);
    }

    /**
     * Set system information in the report
     */
    private static void setSystemInfo() {
        extent.setSystemInfo("Application", "Volt Money Eligibility Page");
        extent.setSystemInfo("Environment", ConfigReader.getEnvironment());
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("Headless Mode", String.valueOf(ConfigReader.isHeadless()));
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Base URL", ConfigReader.getBaseUrl());
        extent.setSystemInfo("Framework", "Java + Selenium + TestNG");
        extent.setSystemInfo("Report Generated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * Create a new test in the report
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    /**
     * Get current ExtentTest instance
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Flush the ExtentReports
     */
    public static void flush() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed. Report available at: {}", reportPath);
        }
    }

    /**
     * Get report path
     */
    public static String getReportPath() {
        return reportPath;
    }
}
