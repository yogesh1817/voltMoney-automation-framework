package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EligibilityPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(EligibilityPage.class);

    public static final String PAGE_URL =
            "https://voltmoney.in/check-loan-eligibility-against-mutual-funds";

    @FindBy(xpath = "//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'mobile') or " +
            "contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'mobile') or " +
            "contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'mobile')]")
    private WebElement mobileInput;

    @FindBy(xpath = "//input[contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pan') or " +
            "contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pan') or " +
            "contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'pan')]")
    private WebElement panInput;

    @FindBy(xpath = "//input[@type='checkbox' or contains(@class,'checkbox')]")
    private WebElement termsCheckbox;

    @FindBy(xpath = "//button[contains(.,'Check eligibility') or contains(.,'FREE') or contains(@class,'submit')]")
    private WebElement submitButton;

    @FindBy(xpath = "//img[contains(@src,'Volt') or contains(@alt,'Volt') or contains(@alt,'Logo')]")
    private WebElement voltLogo;

    @FindBy(xpath = "//div[contains(@class,'error') or contains(.,'required') or contains(.,'invalid')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[contains(@class,'success') or contains(.,'success')]")
    private WebElement successMessage;

    @FindBy(xpath = "//div[contains(.,'Interest rates') or contains(.,'9%')]")
    private WebElement interestRateSection;

    @FindBy(xpath = "//div[contains(.,'Withdraw flexibly')]")
    private WebElement flexibleWithdrawSection;

    @FindBy(xpath = "//div[contains(.,'Repay easily')]")
    private WebElement easyRepaySection;

    @FindBy(xpath = "//div[contains(.,'Secure') and contains(.,'paperless')]")
    private WebElement secureSection;

    @FindBy(xpath = "//div[contains(.,'How It Works') or contains(.,'How it works')]")
    private WebElement howItWorksSection;

    @FindBy(xpath = "//div[contains(.,'FAQ') or contains(.,'Frequently')]")
    private WebElement faqSection;

    @FindBy(xpath = "(//div[contains(@class,'faq') or contains(@class,'accordion')]//*[self::button or self::div][contains(@class,'question') or contains(@class,'header')])[1]")
    private WebElement firstFaqQuestion;

    @FindBy(xpath = "//footer | //div[contains(@class,'footer')]")
    private WebElement footer;

    @FindBy(xpath = "(//a[contains(@href,'twitter') or contains(@href,'instagram') or contains(@href,'linkedin')])[1]")
    private WebElement socialMediaLinks;

    @FindBy(xpath = "//img[contains(@src,'Cams') or contains(@alt,'CAMS')]")
    private WebElement camsLogo;

    @FindBy(xpath = "//img[contains(@src,'Kfin') or contains(@alt,'KFintech') or contains(@alt,'KFin')]")
    private WebElement kfintechLogo;

    public void navigateToPage() {
        driver.get(PAGE_URL);
        waitForPageLoad();
        logger.info("Opened eligibility page");
    }

    public void enterMobileNumber(String mobileNumber) {
        waitForElementVisible(mobileInput);
        safeSendKeys(mobileInput, mobileNumber);
    }

    public void enterPAN(String panNumber) {
        waitForElementVisible(panInput);
        safeSendKeys(panInput, panNumber);
    }

    public void acceptTermsAndConditions() {
        waitForElementVisible(termsCheckbox);
        if (!isElementSelected(termsCheckbox)) {
            safeClick(termsCheckbox);
        }
    }

    public void submitForm() {
        waitForElementClickable(submitButton);
        safeClick(submitButton);
    }

    public void fillCompleteForm(String mobileNumber, String panNumber) {
        enterMobileNumber(mobileNumber);
        enterPAN(panNumber);
        acceptTermsAndConditions();
    }

    public boolean isPageLoaded() {
        try {
            waitForElementVisible(voltLogo);
            return isElementDisplayed(voltLogo)
                    && isElementDisplayed(mobileInput)
                    && isElementDisplayed(panInput)
                    && isElementDisplayed(submitButton);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSubmissionSuccessful() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ignored) {}
        return !PAGE_URL.equalsIgnoreCase(getCurrentUrl()) || isElementDisplayed(successMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return safeGetText(errorMessage);
        }
        return "";
    }

    public boolean isMobileInputDisplayed() {
        return isElementDisplayed(mobileInput);
    }

    public boolean isPANInputDisplayed() {
        return isElementDisplayed(panInput);
    }

    public boolean isTermsCheckboxDisplayed() {
        return isElementDisplayed(termsCheckbox);
    }

    public boolean isSubmitButtonEnabled() {
        return isElementEnabled(submitButton);
    }

    public String getMobileInputValue() {
        try {
            return mobileInput.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public String getPANInputValue() {
        try {
            return panInput.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public void clearForm() {
        try {
            if (isElementDisplayed(mobileInput)) mobileInput.clear();
            if (isElementDisplayed(panInput)) panInput.clear();
            if (isElementSelected(termsCheckbox)) safeClick(termsCheckbox);
        } catch (Exception ignored) { }
    }

    public boolean isBenefitsSectionDisplayed() {
        return isElementDisplayed(interestRateSection)
                && isElementDisplayed(flexibleWithdrawSection)
                && isElementDisplayed(easyRepaySection)
                && isElementDisplayed(secureSection);
    }

    public boolean isHowItWorksSectionDisplayed() {
        return isElementDisplayed(howItWorksSection);
    }

    public boolean isFAQSectionDisplayed() {
        return isElementDisplayed(faqSection);
    }

    public void clickFirstFAQQuestion() {
        if (isElementDisplayed(firstFaqQuestion)) {
            scrollToElement(firstFaqQuestion);
            safeClick(firstFaqQuestion);
        }
    }

    public boolean isFooterDisplayed() {
        return isElementDisplayed(footer);
    }

    public boolean areSocialMediaLinksDisplayed() {
        return isElementDisplayed(socialMediaLinks);
    }

    public boolean arePartnerLogosDisplayed() {
        return isElementDisplayed(camsLogo) && isElementDisplayed(kfintechLogo);
    }

    public boolean validateMobileFormat(String mobile) {
        String clean = mobile == null ? "" : mobile.replaceAll("[^0-9]", "");
        return clean.matches("^[6-9][0-9]{9}$");
    }

    public boolean validatePANFormat(String pan) {
        return pan != null && pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]$");
    }
}
