package API.Controllers;

import API.DTO.AccountAttach;
import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DTO.User;
import API.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private UserController userControllerUnderTest;
    private UserService mockedUserService;
    private String name = "name";
    private String email = "email";
    private String token = "token";
    private String password = "password";
    private ArrayList<AccountAttach> users = new ArrayList<>();
    private RegisterRequest registerRequest = new RegisterRequest();
    private LoginRequest loginRequest = new LoginRequest();
    private User user = new User();

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
        Mockito.when(mockedUserService.register(registerRequest)).thenReturn(new LoginResponse());
        // Run the test
        final Response result = userControllerUnderTest.register(registerRequest);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testRegister401() {
        // Setup
        final Response expectedResult = Response.status(Response.Status.BAD_REQUEST).build();
        Mockito.when(mockedUserService.register(registerRequest)).thenReturn(null);

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
        final Response expectedResult = Response.status(Response.Status.BAD_REQUEST).build();
        Mockito.when(mockedUserService.login(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(null);
        // Run the test
        final Response result = userControllerUnderTest.login(loginRequest);

        // Verify the results
        assertEquals(expectedResult.getStatus(), result.getStatus());
    }

    @Test
    void testUserDetails() {
        final Response.Status expectedResult = Response.Status.OK;
        Mockito.when(mockedUserService.getUserByToken(token)).thenReturn(user);
        final Response result = userControllerUnderTest.getUserDetails(token);
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testUserDetails400() {
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;
        Mockito.when(mockedUserService.getUserByToken(token)).thenReturn(null);
        final Response result = userControllerUnderTest.getUserDetails(token);
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void userAttachedAccounts() {
        final Response.Status expectedResult = Response.Status.OK;
        Mockito.when(mockedUserService.getAttachedAccounts(token)).thenReturn(users);
        final Response result = userControllerUnderTest.getAttachedAccounts(token);
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void userAttachedAccounts400() {
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;
        Mockito.when(mockedUserService.getAttachedAccounts(token)).thenReturn(null);
        final Response result = userControllerUnderTest.getAttachedAccounts(token);
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

}
