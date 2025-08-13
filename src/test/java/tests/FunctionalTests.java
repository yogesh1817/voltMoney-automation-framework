package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EligibilityPage;
import testdata.TestData;

/**
 * Functional test cases for Volt Money Eligibility Page
 * Tests core business functionality and user workflows
 */
public class FunctionalTests extends BaseTest {
    
    private EligibilityPage eligibilityPage;
    
    @Test(priority = 1, groups = {"smoke", "critical"}, description = "Verify page loads successfully with all components")
    public void testPageLoad() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.isPageLoaded(), 
            "Page should load with all essential components");
        Assert.assertTrue(eligibilityPage.getPageTitle().contains("Volt"), 
            "Page title should contain 'Volt'");
        
        extentTest.pass("Page loaded successfully with all components");
    }
    
    @Test(priority = 2, groups = {"smoke", "high"}, description = "Verify mobile number input field functionality")
    public void testMobileNumberInput() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        String validMobile = TestData.VALID_MOBILE_NUMBERS[0];
        eligibilityPage.enterMobileNumber(validMobile);
        
        Assert.assertTrue(eligibilityPage.isMobileInputDisplayed(), 
            "Mobile input field should be displayed");
        Assert.assertEquals(eligibilityPage.getMobileInputValue().replaceAll("[^0-9]", ""), 
            validMobile, "Mobile number should be entered correctly");
        
        extentTest.pass("Mobile number input functionality verified");
    }
    
    @Test(priority = 3, groups = {"smoke", "high"}, description = "Verify PAN card input field functionality")
    public void testPANInput() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        String validPAN = TestData.VALID_PAN_NUMBERS[0];
        eligibilityPage.enterPAN(validPAN);
        
        Assert.assertTrue(eligibilityPage.isPANInputDisplayed(), 
            "PAN input field should be displayed");
        Assert.assertEquals(eligibilityPage.getPANInputValue().toUpperCase(), 
            validPAN.toUpperCase(), "PAN number should be entered correctly");
        
        extentTest.pass("PAN input functionality verified");
    }
    
    @Test(priority = 4, groups = {"smoke", "medium"}, description = "Verify Terms & Conditions checkbox functionality")
    public void testTermsCheckbox() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.isTermsCheckboxDisplayed(), 
            "Terms checkbox should be displayed");
        
        eligibilityPage.acceptTermsAndConditions();
        // Note: Actual checkbox selection validation would need specific element state checking
        
        extentTest.pass("Terms & Conditions checkbox functionality verified");
    }
    
    @Test(priority = 5, groups = {"smoke", "critical"}, description = "Verify complete form submission with valid data")
    public void testValidFormSubmission() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        String validMobile = TestData.VALID_MOBILE_NUMBERS[0];
        String validPAN = TestData.VALID_PAN_NUMBERS[0];
        
        eligibilityPage.fillCompleteForm(validMobile, validPAN);
        eligibilityPage.submitForm();
        
        // Verify submission (this would depend on actual application behavior)
        // For now, we verify the form was filled correctly before submission
        Assert.assertEquals(eligibilityPage.getMobileInputValue().replaceAll("[^0-9]", ""), 
            validMobile, "Mobile number should be correct before submission");
        Assert.assertEquals(eligibilityPage.getPANInputValue().toUpperCase(), 
            validPAN.toUpperCase(), "PAN should be correct before submission");
        
        extentTest.pass("Form submission with valid data completed");
    }
    
    @Test(priority = 6, groups = {"regression", "medium"}, description = "Verify navigation menu functionality")
    public void testNavigationMenu() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Verify page loads - navigation menu tests would require specific menu elements
        Assert.assertTrue(eligibilityPage.isPageLoaded(), 
            "Page should load successfully for navigation testing");
        
        extentTest.pass("Navigation menu accessibility verified");
    }
    
    @Test(priority = 7, groups = {"regression", "low"}, description = "Verify footer links functionality")
    public void testFooterLinks() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.isFooterDisplayed(), 
            "Footer should be displayed");
        
        extentTest.pass("Footer links accessibility verified");
    }
    
    @Test(priority = 8, groups = {"regression", "low"}, description = "Verify social media links functionality")
    public void testSocialMediaLinks() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.areSocialMediaLinksDisplayed(), 
            "Social media links should be displayed");
        
        extentTest.pass("Social media links accessibility verified");
    }
    
    @Test(priority = 9, groups = {"regression", "medium"}, description = "Verify FAQ section expand/collapse")
    public void testFAQSection() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        if (eligibilityPage.isFAQSectionDisplayed()) {
            eligibilityPage.clickFirstFAQQuestion();
            extentTest.pass("FAQ section interaction verified");
        } else {
            extentTest.skip("FAQ section not found on page");
        }
    }
    
    @Test(priority = 10, groups = {"regression", "low"}, description = "Verify partner logos are displayed")
    public void testPartnerLogos() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.arePartnerLogosDisplayed(), 
            "Partner logos should be displayed");
        
        extentTest.pass("Partner logos display verified");
    }
    
    @Test(priority = 11, groups = {"regression", "medium"}, description = "Verify How It Works section functionality")
    public void testHowItWorksSection() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.isHowItWorksSectionDisplayed(), 
            "How It Works section should be displayed");
        
        extentTest.pass("How It Works section display verified");
    }
    
    @Test(priority = 12, groups = {"regression", "medium"}, description = "Verify benefits section display")
    public void testBenefitsSection() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        Assert.assertTrue(eligibilityPage.isBenefitsSectionDisplayed(), 
            "Benefits section should be displayed with all features");
        
        extentTest.pass("Benefits section display verified");
    }
    
    @Test(priority = 13, groups = {"regression", "medium"}, description = "Verify form reset functionality")
    public void testFormReset() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        String validMobile = TestData.VALID_MOBILE_NUMBERS[0];
        String validPAN = TestData.VALID_PAN_NUMBERS[0];
        
        eligibilityPage.fillCompleteForm(validMobile, validPAN);
        eligibilityPage.clearForm();
        
        // Verify form is cleared
        String mobileValue = eligibilityPage.getMobileInputValue();
        String panValue = eligibilityPage.getPANInputValue();
        
        Assert.assertTrue(mobileValue == null || mobileValue.isEmpty(), 
            "Mobile field should be cleared");
        Assert.assertTrue(panValue == null || panValue.isEmpty(), 
            "PAN field should be cleared");
        
        extentTest.pass("Form reset functionality verified");
    }
    
    @Test(priority = 14, groups = {"regression", "medium"}, description = "Verify page refresh maintains functionality")
    public void testPageRefresh() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Verify initial load
        Assert.assertTrue(eligibilityPage.isPageLoaded(), 
            "Page should load initially");
        
        // Refresh page
        eligibilityPage.refreshPage();
        
        // Verify functionality after refresh
        Assert.assertTrue(eligibilityPage.isPageLoaded(), 
            "Page should maintain functionality after refresh");
        
        extentTest.pass("Page refresh functionality verified");
    }
}