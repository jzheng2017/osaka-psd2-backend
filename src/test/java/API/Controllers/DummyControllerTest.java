package API.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyControllerTest {

    private DummyController dummyControllerUnderTest;

    @BeforeEach
    void setUp() {
        dummyControllerUnderTest = new DummyController();
    }

    @Test
    void testAuthorizeIng() throws URISyntaxException {
        // Setup
        String DEFAULT_ING_AUTHORIZATION_CODE = "2c1c404c-c960-49aa-8777-19c805713edf";
        String url = "uri?code=" + DEFAULT_ING_AUTHORIZATION_CODE + "&state=state";
        final Response expectedResult = Response.temporaryRedirect(new URI(url)).build();

        // Run the test
        final Response result = dummyControllerUnderTest.authorizeIng("uri", "state");

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testAuthorizeIngReturnsNull() {
        // Setup
        final Response expectedResult = null;

        // Run the test
        final Response result = dummyControllerUnderTest.authorizeIng("%%%", "state");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
