package API.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyControllerTest {
    private static final String DEFAULT_ING_AUTHORIZATION_CODE = "2c1c404c-c960-49aa-8777-19c805713edf";

    private DummyController dummyController;

    @BeforeEach
    void setup() {
        dummyController = new DummyController();
    }

    @Test
    void testAuthorizeIng() throws URISyntaxException {
        // Arrange
        var url = "uri?code=" + DEFAULT_ING_AUTHORIZATION_CODE + "&state=state";
        var expected = Response.temporaryRedirect(new URI(url)).build();

        // Act
        var result = dummyController.authorize("ING","uri", "state");

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testAuthorizeDummy() throws URISyntaxException {
        // Arrange
        var url = "uri?code=" + DEFAULT_ING_AUTHORIZATION_CODE + "&state=state";
        var expected = Response.temporaryRedirect(new URI(url)).build();

        // Act
        var result = dummyController.authorize("DUMMY","uri", "state");

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }
}
