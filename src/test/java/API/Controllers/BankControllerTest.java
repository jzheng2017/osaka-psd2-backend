package API.Controllers;

import API.DTO.Bank;
import API.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    void testConnectRabo400() {
        // Setup
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;
        // Run the test
        final Response result = bankControllerUnderTest.connect(Bank.RABOBANK, "");
        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
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
    void testConnectING400() {
        // Setup
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;
        // Run the test
        final Response result = bankControllerUnderTest.connect(Bank.ING, "");
        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
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

    @Test
    void testFinish40() {
        // Setup
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;

        // Run the test
        final Response result = bankControllerUnderTest.finish(Bank.RABOBANK, "code", "");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testDisconnect() {
        final Response.Status expectedResult = Response.Status.OK;
        final Response result = bankControllerUnderTest.deleteBankAccount(token, "id");
        Mockito.verify(mockedUserService).deleteBankAccount(token,"id");
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testDisconnect400() {
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;
        final Response result = bankControllerUnderTest.deleteBankAccount("", "");
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }
}
