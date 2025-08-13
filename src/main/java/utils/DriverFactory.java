package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * WebDriver Factory - Singleton per thread
 * Automatically matches driver version to installed browser
 */
public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private static final int IMPLICIT_WAIT_TIMEOUT = 10;
    private static final int PAGE_LOAD_TIMEOUT = 60;

    /**
     * Initialize WebDriver with automatic driver version matching
     */
    public static void initializeDriverWithAutoVersion(String browserName, boolean headless) {
        if (isDriverInitialized()) {
            logger.warn("Driver already initialized, skipping re-initialization");
            return;
        }

        try {
            WebDriver driver = createDriverAutoVersion(browserName.toLowerCase(), headless);
            setDriver(driver);
            configureDriver(driver);
            logger.info("Initialized {} driver successfully with auto version match", browserName);
        } catch (Exception e) {
            logger.error("Failed to initialize {} driver", browserName, e);
            throw new RuntimeException("Driver initialization failed for: " + browserName, e);
        }
    }

    /**
     * Create driver with auto version detection
     */
    private static WebDriver createDriverAutoVersion(String browserName, boolean headless) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup(); // auto-detect version
                return createChromeDriver(headless);

            case "firefox":
                WebDriverManager.firefoxdriver().setup(); // auto-detect version
                return createFirefoxDriver(headless);

            case "edge":
                WebDriverManager.edgedriver().setup(); // auto-detect version
                return createEdgeDriver(headless);

            default:
                logger.warn("Unknown browser '{}', defaulting to Chrome", browserName);
                WebDriverManager.chromedriver().setup();
                return createChromeDriver(headless);
        }
    }

    /**
     * Chrome driver creation
     */
    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        setCommonOptions(options, headless);
        return new ChromeDriver(options);
    }

    /**
     * Firefox driver creation
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        return new FirefoxDriver(options);
    }

    /**
     * Edge driver creation
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        setCommonOptions(options, headless);
        return new EdgeDriver(options);
    }

    /**
     * Common browser arguments
     */
    private static void setCommonOptions(Object options, boolean headless) {
        if (options instanceof ChromeOptions) {
            ChromeOptions chromeOptions = (ChromeOptions) options;
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--window-size=1920,1080");
            if (headless) chromeOptions.addArguments("--headless=new");
        } else if (options instanceof EdgeOptions) {
            EdgeOptions edgeOptions = (EdgeOptions) options;
            edgeOptions.addArguments("--disable-dev-shm-usage");
            edgeOptions.addArguments("--no-sandbox");
            edgeOptions.addArguments("--disable-extensions");
            edgeOptions.addArguments("--window-size=1920,1080");
            if (headless) edgeOptions.addArguments("--headless=new");
        }
    }

    /**
     * Configure driver (timeouts, maximize)
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            logger.warn("Could not maximize window (headless mode?)");
        }
    }

    /**
     * Get driver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized");
        }
        return driver;
    }

    /**
     * Set driver instance for current thread
     */
    private static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    /**
     * Quit driver
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting driver", e);
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }

    /**
     * Take screenshot (Base64)
     */
    public static String takeScreenshotBase64() {
        if (!isDriverInitialized()) {
            throw new IllegalStateException("Driver is not initialized");
        }
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Error taking screenshot", e);
            return "";
        }
    }
}
