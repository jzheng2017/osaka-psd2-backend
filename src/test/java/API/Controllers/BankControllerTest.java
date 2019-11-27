package API.Controllers;

import API.Adapters.BaseAdapter;
import API.DTO.Bank;
import API.DTO.BankToken;
import API.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankControllerTest {

    private BankController bankControllerUnderTest;
    private UserService mockedUserService;
    private final URI FINAL_REDIRECT_URL = URI.create("http://localhost:4200/overzicht/rekeningen");
    private String token = "token";

    @BeforeEach
    void setUp() {
        bankControllerUnderTest = new BankController();
        mockedUserService = Mockito.mock(UserService.class);
        bankControllerUnderTest.setUserService(mockedUserService);
    }

    @Test
    void testConnectRabo() {
        // Setup
        final Response expectedResult = Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        // Run the test
        final Response result = bankControllerUnderTest.connect(Bank.RABOBANK, token);
        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testConnectING() {
        // Setup
        final Response expectedResult = Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        // Run the test
        final Response result = bankControllerUnderTest.connect(Bank.ING, token);
        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testFinish() {
        // Setup
        final Response expectedResult =  Response.temporaryRedirect(FINAL_REDIRECT_URL).build();

        // Run the test
        final Response result = bankControllerUnderTest.finish(Bank.RABOBANK, "code", token);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }
}
