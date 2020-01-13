package API.Banks.ING;

import API.DTO.AccountDetails;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class INGMapperTest {

    private INGMapper ingMapperUnderTest;

    @BeforeEach
    void setUp() {
        ingMapperUnderTest = new INGMapper();
    }

    @Test
    void testMapToAccountDetailsReturnsEmptyDetails() {
        // Setup
        JsonObject object = new JsonObject();
        object.addProperty("tppMessages","True");
        // Run the test
        final AccountDetails result = ingMapperUnderTest.mapToAccountDetails(object);
        // Verify the results
        assertNotNull(result);
    }

    @Test
    void getIbanFromStringReturnsIban() {
        String expectedResult = "NL691234";
        String ibanToFind = "BLAHBLAHIBAN:NL691234<>";
        final String result = ingMapperUnderTest.getIbanFromString(ibanToFind);
        assertEquals(expectedResult, result);
    }

    @Test
    void getIbanFromStringReturnsNull() {
        String expectedResult = null;
        String ibanToFind = "no<>";
        final String result = ingMapperUnderTest.getIbanFromString(ibanToFind);
        assertEquals(expectedResult, result);
    }
}
