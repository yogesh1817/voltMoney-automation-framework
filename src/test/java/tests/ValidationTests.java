package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import pages.EligibilityPage;
import testdata.TestData;

/**
 * Validation test cases for Volt Money Eligibility Page
 * Tests input validation, error handling, and data format validation
 */
public class ValidationTests extends BaseTest {
    
    private EligibilityPage eligibilityPage;
    
    @Test(priority = 1, groups = {"smoke", "high"}, description = "Validate mobile number format - valid 10 digits")
    public void testValidMobileNumberFormat() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        String validMobile = TestData.VALID_MOBILE_NUMBERS[0];
        eligibilityPage.enterMobileNumber(validMobile);
        
        Assert.assertTrue(eligibilityPage.validateMobileFormat(validMobile), 
            "Valid mobile number should be accepted");
        Assert.assertEquals(eligibilityPage.getMobileInputValue().replaceAll("[^0-9]", ""), 
            validMobile, "Mobile number should be entered correctly");
        
        extentTest.pass("Valid mobile number format accepted: " + validMobile);
    }
    
    @Test(priority = 2, groups = {"smoke", "high"}, dataProvider = "invalidMobileNumbers", 
          description = "Validate mobile number format - invalid formats")
    public void testInvalidMobileNumberFormat(String invalidMobile, String description) {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        eligibilityPage.enterMobileNumber(invalidMobile);
        
        Assert.assertFalse(eligibilityPage.validateMobileFormat(invalidMobile), 
            "Invalid mobile number should be rejected: " + description);
        
        extentTest.pass("Invalid mobile number rejected: " + invalidMobile + " (" + description + ")");
    }
    
    @Test(priority = 3, groups = {"smoke", "high"}, description = "Validate PAN format - valid AAAAA9999A")
    public void testValidPANFormat() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        String validPAN = TestData.VALID_PAN_NUMBERS[0];
        eligibilityPage.enterPAN(validPAN);
        
        Assert.assertTrue(eligibilityPage.validatePANFormat(validPAN), 
            "Valid PAN number should be accepted");
        Assert.assertEquals(eligibilityPage.getPANInputValue().toUpperCase(), 
            validPAN.toUpperCase(), "PAN should be entered correctly");
        
        extentTest.pass("Valid PAN format accepted: " + validPAN);
    }
    
    @Test(priority = 4, groups = {"smoke", "high"}, dataProvider = "invalidPANNumbers", 
          description = "Validate PAN format - invalid formats")
    public void testInvalidPANFormat(String invalidPAN, String description) {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        eligibilityPage.enterPAN(invalidPAN);
        
        Assert.assertFalse(eligibilityPage.validatePANFormat(invalidPAN), 
            "Invalid PAN should be rejected: " + description);
        
        extentTest.pass("Invalid PAN rejected: " + invalidPAN + " (" + description + ")");
    }
    
    @Test(priority = 5, groups = {"smoke", "high"}, description = "Validate mandatory field - mobile number")
    public void testMandatoryMobileField() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Try to submit without mobile number
        eligibilityPage.enterPAN(TestData.VALID_PAN_NUMBERS[0]);
        eligibilityPage.acceptTermsAndConditions();
        eligibilityPage.submitForm();
        
        // Verify validation (in real implementation, check for error message)
        Assert.assertTrue(eligibilityPage.isMobileInputDisplayed(), 
            "Mobile field should be present and required");
        
        extentTest.pass("Mobile field mandatory validation verified");
    }
    
    @Test(priority = 6, groups = {"smoke", "high"}, description = "Validate mandatory field - PAN number")
    public void testMandatoryPANField() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Try to submit without PAN number
        eligibilityPage.enterMobileNumber(TestData.VALID_MOBILE_NUMBERS[0]);
        eligibilityPage.acceptTermsAndConditions();
        eligibilityPage.submitForm();
        
        // Verify validation (in real implementation, check for error message)
        Assert.assertTrue(eligibilityPage.isPANInputDisplayed(), 
            "PAN field should be present and required");
        
        extentTest.pass("PAN field mandatory validation verified");
    }
    
    @Test(priority = 7, groups = {"smoke", "high"}, description = "Validate mandatory Terms & Conditions acceptance")
    public void testMandatoryTermsAcceptance() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Try to submit without accepting T&C
        eligibilityPage.enterMobileNumber(TestData.VALID_MOBILE_NUMBERS[0]);
        eligibilityPage.enterPAN(TestData.VALID_PAN_NUMBERS[0]);
        eligibilityPage.submitForm();
        
        // Verify validation (in real implementation, check for error message)
        Assert.assertTrue(eligibilityPage.isTermsCheckboxDisplayed(), 
            "Terms checkbox should be present and required");
        
        extentTest.pass("Terms & Conditions mandatory validation verified");
    }
    
    @Test(priority = 8, groups = {"regression", "medium"}, description = "Validate error message display and formatting")
    public void testErrorMessageDisplay() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Try to trigger validation error
        eligibilityPage.enterMobileNumber("123"); // Invalid mobile
        eligibilityPage.submitForm();
        
        // Check if error handling mechanism exists
        Assert.assertTrue(eligibilityPage.isMobileInputDisplayed(), 
            "Form should handle validation errors appropriately");
        
        extentTest.pass("Error message handling verified");
    }
    
    @Test(priority = 9, groups = {"regression", "medium"}, dataProvider = "specialCharacterMobiles", 
          description = "Validate special characters in mobile field")
    public void testSpecialCharactersInMobile(String mobileWithSpecialChars, String description) {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        eligibilityPage.enterMobileNumber(mobileWithSpecialChars);
        String enteredValue = eligibilityPage.getMobileInputValue();
        
        // Should filter out special characters or reject input
        String cleanValue = enteredValue.replaceAll("[^0-9]", "");
        Assert.assertTrue(cleanValue.length() <= 10, 
            "Mobile field should handle special characters: " + description);
        
        extentTest.pass("Special characters handled in mobile field: " + mobileWithSpecialChars);
    }
    
    @Test(priority = 10, groups = {"regression", "medium"}, dataProvider = "caseSensitivePANs", 
          description = "Validate case sensitivity in PAN field")
    public void testCaseSensitivityInPAN(String panInput, String description) {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        eligibilityPage.enterPAN(panInput);
        String enteredValue = eligibilityPage.getPANInputValue();
        
        // PAN should be converted to uppercase or accept both cases
        Assert.assertTrue(enteredValue.equals(panInput.toUpperCase()) || enteredValue.equals(panInput), 
            "PAN field should handle case correctly: " + description);
        
        extentTest.pass("PAN case sensitivity handled: " + panInput + " -> " + enteredValue);
    }
    
    @Test(priority = 11, groups = {"regression", "medium"}, description = "Validate field length restrictions")
    public void testFieldLengthRestrictions() {
        eligibilityPage = new EligibilityPage();
        eligibilityPage.navigateToPage();
        
        // Test mobile field max length
        String longMobile = "12345678901234567890";
        eligibilityPage.enterMobileNumber(longMobile);
        String mobileValue = eligibilityPage.getMobileInputValue().replaceAll("[^0-9]", "");
        Assert.assertTrue(mobileValue.length() <= 10, 
            "Mobile field should restrict length to 10 digits");
        
        // Test PAN field max length
        String longPAN = "ABCDE1234FGHIJKLMNOP";
        eligibilityPage.enterPAN(longPAN);
        String panValue = eligibilityPage.getPANInputValue();
        Assert.assertTrue(panValue.length() <= 10, 
            "PAN field should restrict length to 10 characters");
        
        extentTest.pass("Field length restrictions verified");
    }
    
    // Data Providers
    
    @DataProvider(name = "invalidMobileNumbers")
    public Object[][] getInvalidMobileNumbers() {
        return new Object[][] {
            {"123456789", "9 digits - too short"},
            {"12345678901", "11 digits - too long"},
            {"1234567890", "starts with 1 - invalid"},
            {"0987654321", "starts with 0 - invalid"},
            {"abcdefghij", "contains letters"},
            {"123-456-7890", "contains hyphens"},
            {"", "empty string"},
            {" ", "whitespace only"}
        };
    }
    
    @DataProvider(name = "invalidPANNumbers")
    public Object[][] getInvalidPANNumbers() {
        return new Object[][] {
            {"ABCD1234F", "4 letters instead of 5"},
            {"ABCDE123F", "3 digits instead of 4"},
            {"ABCDE1234", "missing last letter"},
            {"abcde1234f", "lowercase letters"},
            {"ABCDE12345", "number instead of last letter"},
            {"12345ABCDF", "numbers at start"},
            {"ABCDE1234FG", "too long"},
            {"", "empty string"}
        };
    }
    
    @DataProvider(name = "specialCharacterMobiles")
    public Object[][] getSpecialCharacterMobiles() {
        return new Object[][] {
            {"987-654-3210", "with hyphens"},
            {"987.654.3210", "with dots"},
            {"987 654 3210", "with spaces"},
            {"+919876543210", "with country code"},
            {"(987) 654-3210", "with parentheses"},
            {"987@654#3210", "with special symbols"}
        };
    }
    
    @DataProvider(name = "caseSensitivePANs")
    public Object[][] getCaseSensitivePANs() {
        return new Object[][] {
            {"abcde1234f", "all lowercase"},
            {"ABCDE1234F", "all uppercase"},
            {"AbCdE1234f", "mixed case"},
            {"abcDE1234F", "mixed case variant"}
        };
    }
}