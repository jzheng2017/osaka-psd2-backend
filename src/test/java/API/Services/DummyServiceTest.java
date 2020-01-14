package API.Services;

import API.DTO.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;

public class DummyServiceTest {
    private static final String URL = "URL";
    private static final String STATE = "STATE";
    private DummyService dummyService;

    @BeforeEach
    void setup() {
        this.dummyService = new DummyService();
    }

    void testAuthorizeWithCode(Bank bank, String code) {
        // Arrange
        var expected = URI.create(URL+"?code="+code+"&state="+STATE);

        // Act
        var response = dummyService.authorize(bank, URL, STATE);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testAuthorizeIng() {
        testAuthorizeWithCode(Bank.ING, DummyService.DEFAULT_ING_AUTHORIZATION_CODE);
    }

    @Test
    void testAuthorizeDummy() {
        testAuthorizeWithCode(Bank.DUMMY, DummyService.DEFAULT_DUMMY_AUTHORIZATION_CODE);
    }
}
