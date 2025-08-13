package testdata;

public class TestData {

    // Valid Data
    public static final String[] VALID_MOBILE_NUMBERS = {
        "9876543210", "8765432109", "7654321098", "6543210987",
        "9123456789", "8234567890", "7345678901", "6456789012"
    };

    public static final String[] VALID_PAN_NUMBERS = {
        "ABCDE1234F", "PQRST5678G", "LMNOP9012H", "UVWXY3456I",
        "FGHIJ7890J", "KLMNO2345K", "RSTUV6789L", "WXYZ01234M"
    };

    // Invalid Data
    public static final String[] INVALID_MOBILE_NUMBERS = {
        "123456789", "12345678901", "1234567890", "0987654321", "5876543210",
        "abcdefghij", "987-654-3210", "", " ", "987.654.3210",
        "+919876543210", "(987) 654-3210"
    };

    public static final String[] INVALID_PAN_NUMBERS = {
        "ABCD1234F", "ABCDE123F", "ABCDE1234", "abcde1234f",
        "ABCDE12345", "12345ABCDF", "ABCDE1234FG", "", " ",
        "ABC-DE1234F", "ABCDE@1234F"
    };

    // URLs & Expected Texts
    public static final String BASE_URL = "https://voltmoney.in";
    public static final String ELIGIBILITY_PAGE_URL = BASE_URL + "/check-loan-eligibility-against-mutual-funds";
    public static final String EXPECTED_PAGE_TITLE = "Volt Money";
    public static final String EXPECTED_HEADING = "Check eligibility";
    public static final String EXPECTED_SUBMIT_BUTTON_TEXT = "Check eligibility";

    // Expected Error Messages
    public static final String MOBILE_REQUIRED_ERROR = "Mobile number is required";
    public static final String PAN_REQUIRED_ERROR = "PAN number is required";
    public static final String TERMS_REQUIRED_ERROR = "Please accept Terms & Conditions";
    public static final String INVALID_MOBILE_ERROR = "Please enter a valid mobile number";
    public static final String INVALID_PAN_ERROR = "Please enter a valid PAN number";

    // Expected Success Messages
    public static final String FORM_SUBMISSION_SUCCESS = "Eligibility check initiated";
    public static final String REDIRECT_SUCCESS_URL_CONTAINS = "next-step";

    // Browser Config
    public static final String[] SUPPORTED_BROWSERS = {"chrome", "firefox", "edge"};
    public static final String DEFAULT_BROWSER = "chrome";
    public static final boolean DEFAULT_HEADLESS = false;

    // Timeouts
    public static final int IMPLICIT_WAIT_TIMEOUT = 10;
    public static final int EXPLICIT_WAIT_TIMEOUT = 30;
    public static final int PAGE_LOAD_TIMEOUT = 60;

    // TestNG Groups
    public static final String SMOKE_TESTS = "smoke";
    public static final String REGRESSION_TESTS = "regression";
    public static final String CRITICAL_TESTS = "critical";

    // Cross-browser Test Data
    public static final Object[][] BROWSER_TEST_DATA = {
        {"chrome", false}, {"firefox", false}, {"edge", false}, {"chrome", true}
    };

    // Device Sizes
    public static final Object[][] RESPONSIVE_TEST_DATA = {
        {1920, 1080, "Desktop"}, {1366, 768, "Laptop"},
        {768, 1024, "Tablet"}, {375, 667, "Mobile"}
    };

    // Special Boundary Cases
    public static final String[] BOUNDARY_MOBILE_NUMBERS = {
        "6000000000", "9999999999", "6111111111", "9876543210"
    };

    public static final String[] BOUNDARY_PAN_NUMBERS = {
        "AAAAA0000A", "ZZZZZ9999Z", "ABCDE1111A", "PQRST4567G"
    };

    // Security Tests
    public static final String[] XSS_TEST_INPUTS = {
        "<script>alert('xss')</script>", "javascript:alert('xss')",
        "<img src=x onerror=alert('xss')>", "';alert('xss');//"
    };

    public static final String[] SQL_INJECTION_INPUTS = {
        "'; DROP TABLE users; --", "' OR '1'='1",
        "'; DELETE FROM users; --", "' UNION SELECT * FROM users --"
    };

    // Helpers
    public static String getRandomValidMobile() {
        return VALID_MOBILE_NUMBERS[(int) (Math.random() * VALID_MOBILE_NUMBERS.length)];
    }

    public static String getRandomValidPAN() {
        return VALID_PAN_NUMBERS[(int) (Math.random() * VALID_PAN_NUMBERS.length)];
    }

    public static String getRandomInvalidMobile() {
        return INVALID_MOBILE_NUMBERS[(int) (Math.random() * INVALID_MOBILE_NUMBERS.length)];
    }

    public static String getRandomInvalidPAN() {
        return INVALID_PAN_NUMBERS[(int) (Math.random() * INVALID_PAN_NUMBERS.length)];
    }

    public static Object[][] getValidFormDataCombinations() {
        Object[][] data = new Object[VALID_MOBILE_NUMBERS.length][2];
        for (int i = 0; i < VALID_MOBILE_NUMBERS.length; i++) {
            data[i][0] = VALID_MOBILE_NUMBERS[i];
            data[i][1] = VALID_PAN_NUMBERS[i % VALID_PAN_NUMBERS.length];
        }
        return data;
    }

    public static Object[][] getInvalidFormDataCombinations() {
        return new Object[][] {
            {INVALID_MOBILE_NUMBERS[0], VALID_PAN_NUMBERS[0], "Invalid mobile, valid PAN"},
            {VALID_MOBILE_NUMBERS[0], INVALID_PAN_NUMBERS[0], "Valid mobile, invalid PAN"},
            {INVALID_MOBILE_NUMBERS[0], INVALID_PAN_NUMBERS[0], "Invalid mobile, invalid PAN"},
            {"", VALID_PAN_NUMBERS[0], "Empty mobile, valid PAN"},
            {VALID_MOBILE_NUMBERS[0], "", "Valid mobile, empty PAN"},
            {"", "", "Empty mobile, empty PAN"}
        };
    }
}
