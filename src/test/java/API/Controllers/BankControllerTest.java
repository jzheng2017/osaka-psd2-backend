package API.Controllers;

import API.DTO.Bank;
import API.DTO.BankConnection;
import API.Services.BankService;
import API.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BankControllerTest {
    private static final URI FINAL_REDIRECT_URL = URI.create("http://localhost:4200/overzicht/rekeningen");
    private static final String TOKEN = UUID.randomUUID().toString();

    private BankController bankController;
    private BankService mockedBankService;

    @BeforeEach
    void setup() {
        bankController = new BankController();
        mockedBankService = Mockito.mock(BankService.class);
        bankController.setBankService(mockedBankService);
    }

    @Test
    void testConnectRabo() {
        // Arrange
        var expected = Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        BankConnection connection = new BankConnection(false,4);

        // Act
        when(mockedBankService.getConnections(TOKEN)).thenReturn(connection);
        var result = bankController.connect(Bank.RABOBANK, TOKEN);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testConnectRabo400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = bankController.connect(Bank.RABOBANK, "");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }


    @Test
    void testConnectING() {
        // Arrange
        var expected = Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        BankConnection connection = new BankConnection(false,4);

        // Act
        when(mockedBankService.getConnections(TOKEN)).thenReturn(connection);
        var result = bankController.connect(Bank.ING, TOKEN);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testConnectING400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = bankController.connect(Bank.ING, "");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testFinish() {
        // Arrange
        var expected =  Response.temporaryRedirect(FINAL_REDIRECT_URL).build();

        // Act
        var result = bankController.finish(Bank.RABOBANK, "code", TOKEN);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testFinish40() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = bankController.finish(Bank.RABOBANK, "code", "");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testDisconnect() {
        // Arrange
        var expected = Response.Status.OK;

        // Act
        var result = bankController.delete(TOKEN, "id");

        // Assert
        Mockito.verify(mockedBankService).delete(TOKEN,"id");
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testDisconnect400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = bankController.delete("", "");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void getConnections() {
        // Arrange
        var expected = Response.Status.OK;
        var connection = new BankConnection(false,4);

        // Act
        when(mockedBankService.getConnections("sjaak")).thenReturn(connection);
        var result = bankController.connections("sjaak");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void getConnections400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = bankController.connections("");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testGetBanks() {
        var expected = Response.Status.OK;

        var result = bankController.getBanks();

        assertEquals(expected.getStatusCode(), result.getStatus());
    }
}
