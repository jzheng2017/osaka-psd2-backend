package API.Controllers;

import API.Services.BTWService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BtwControllerTest {
    @InjectMocks
    private BTWController sut;
    @Mock
    private BTWService mockedBtwService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBtwPercentagesReturnsStatus200() {
        final int expectedStatusCode = 200;
        final int actualStatusCode = sut.getBTWPercentages().getStatus();

        Assertions.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    void getBtwPercentagesCallsBtwServiceGetBtwPercentages() {
        sut.getBTWPercentages();
        verify(mockedBtwService).getBTWPercentages();
    }

    @Test
    void getBtwPercentagesReturnsStringArray() {
        final String[] stringArray = {};
        when(mockedBtwService.getBTWPercentages()).thenReturn(stringArray);

        Assertions.assertEquals(stringArray, sut.getBTWPercentages().getEntity());
    }
}
