package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            props.load(fis);
            logger.info("Config loaded from {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.warn("Config file not found. Using defaults.");
            loadDefaults();
        }
    }

    private static void loadDefaults() {
        props.setProperty("browser", "chrome");
        props.setProperty("headless", "false");
        props.setProperty("implicit.wait", "10");
        props.setProperty("explicit.wait", "30");
        props.setProperty("page.load.timeout", "60");
        props.setProperty("base.url", "https://voltmoney.in/check-loan-eligibility-against-mutual-funds");
        props.setProperty("environment", "prod");
        props.setProperty("screenshot.path", "screenshots/");
        props.setProperty("report.path", "reports/");
    }

    public static String getBrowser() {
        return System.getProperty("browser", props.getProperty("browser"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", props.getProperty("headless")));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(props.getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(props.getProperty("explicit.wait"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(props.getProperty("page.load.timeout"));
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

    public static String getEnvironment() {
        return System.getProperty("environment", props.getProperty("environment"));
    }

    public static String getScreenshotPath() {
        return props.getProperty("screenshot.path");
    }

    public static String getReportPath() {
        return props.getProperty("report.path");
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
