package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverFactory;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;
    protected final JavascriptExecutor js;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    private static final int EXPLICIT_WAIT_SECONDS = 30;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SECONDS));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    protected void waitForElementVisible(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    protected void waitForElementClickable(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    protected void safeClick(WebElement el) {
        try {
            waitForElementClickable(el);
            el.click();
        } catch (Exception e) {
            logger.debug("Native click failed, using JS click: {}", e.getMessage());
            js.executeScript("arguments[0].click();", el);
        }
    }

    protected void safeSendKeys(WebElement el, String text) {
        waitForElementVisible(el);
        el.clear();
        el.sendKeys(text);
    }

    protected String safeGetText(WebElement el) {
        waitForElementVisible(el);
        return el.getText();
    }

    protected boolean isElementDisplayed(WebElement el) {
        try {
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementEnabled(WebElement el) {
        try {
            return el.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementSelected(WebElement el) {
        try {
            return el.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollToElement(WebElement el) {
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center',inline:'nearest'});", el);
        } catch (Exception ignored) { }
    }

    protected void hover(WebElement el) {
        waitForElementVisible(el);
        actions.moveToElement(el).perform();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    protected void waitForPageLoad() {
        wait.until(d -> "complete".equals(js.executeScript("return document.readyState")));
    }

    protected Object execJs(String script, Object... args) {
        return js.executeScript(script, args);
    }

    public String takeScreenshot() {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.warn("Screenshot failed: {}", e.getMessage());
            return null;
        }
    }
}
