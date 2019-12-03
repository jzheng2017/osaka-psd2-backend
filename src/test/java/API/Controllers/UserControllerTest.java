package API.Controllers;

import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private UserController userControllerUnderTest;
    private UserService mockedUserService;
    private String name = "name";
    private String email = "email";
    private String password = "password";
    private RegisterRequest registerRequest = new RegisterRequest();
    private LoginRequest loginRequest = new LoginRequest();

    @BeforeEach
    void setUp() {
        userControllerUnderTest = new UserController();
        mockedUserService = Mockito.mock(UserService.class);
        userControllerUnderTest.setUserService(mockedUserService);
        registerRequest.setName(name);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");
    }

    @Test
    void testRegister() {
        // Setup
        final Response expectedResult = Response.ok().build();
        Mockito.when(mockedUserService.register(name, email, password)).thenReturn(new LoginResponse());
        // Run the test
        final Response result = userControllerUnderTest.register(registerRequest);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testRegister401() {
        // Setup
        final Response expectedResult = Response.status(Response.Status.UNAUTHORIZED).build();
        Mockito.when(mockedUserService.register(name, email,password)).thenReturn(null);

        // Run the test
        final Response result = userControllerUnderTest.register(registerRequest);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testLogin() {
        // Setup


        final Response expectedResult = Response.ok().build();
        Mockito.when(mockedUserService.login(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(new LoginResponse());
        // Run the test
        final Response result = userControllerUnderTest.login(loginRequest);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testLogin401() {
        // Setup
        final Response expectedResult = Response.status(Response.Status.UNAUTHORIZED).build();
        Mockito.when(mockedUserService.login(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(null);
        // Run the test
        final Response result = userControllerUnderTest.login(loginRequest);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }
}
