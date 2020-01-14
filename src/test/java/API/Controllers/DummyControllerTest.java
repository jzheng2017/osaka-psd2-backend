package API.Controllers;

import API.DTO.Bank;
import API.Services.DummyService;
import API.Services.DummyServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyControllerTest {
    private static final String DEFAULT_ING_AUTHORIZATION_CODE = "2c1c404c-c960-49aa-8777-19c805713edf";

    private DummyController dummyController;
    private DummyService mockedDummyService;
    private Response expected;

    @BeforeEach
    void setup() {
        dummyController = new DummyController();
        mockedDummyService = Mockito.mock(DummyService.class);
        dummyController.setDummyService(mockedDummyService);

        var url = URI.create("URL");
        Mockito.when(mockedDummyService.authorize(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(url);
        expected = Response.temporaryRedirect(url).build();
    }

    void testAuthorizeForBank(Bank bank) {
        // Arrange

        // Act
        var result = dummyController.authorize(bank,"uri", "state");

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testAuthorizeForBanks() {
        List<Bank> banks = new ArrayList<>(Arrays.asList(Bank.DUMMY, Bank.ING));
        banks.forEach(this::testAuthorizeForBank);
    }
}
