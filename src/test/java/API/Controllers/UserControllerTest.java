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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    private static final String TOKEN = UUID.randomUUID().toString();

    private UserController userController;
    private UserService mockedUserService;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setup() {
        userController = new UserController();
        mockedUserService = Mockito.mock(UserService.class);
        userController.setUserService(mockedUserService);

        registerRequest = new RegisterRequest();
        registerRequest.setName("NAME");
        registerRequest.setEmail("EMAIL");
        registerRequest.setPassword("PASSWORD");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("EMAIL");
        loginRequest.setPassword("PASSWORD");
    }

    @Test
    void testRegister() {
        // Arrange
        var expected = Response.ok().build();
        Mockito.when(mockedUserService.register(registerRequest)).thenReturn(new LoginResponse());

        // Act
        var result = userController.register(registerRequest);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testRegister401() {
        // Arrange
        var expected = Response.status(Response.Status.BAD_REQUEST).build();
        Mockito.when(mockedUserService.register(registerRequest)).thenReturn(null);

        // Act
        var result = userController.register(registerRequest);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testLogin() {
        // Arrange
        var expected = Response.ok().build();
        Mockito.when(mockedUserService.login(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(new LoginResponse());

        // Act
        var result = userController.login(loginRequest);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testLogin401() {
        // Arrange
        var expected = Response.status(Response.Status.BAD_REQUEST).build();
        Mockito.when(mockedUserService.login(loginRequest.getEmail(),loginRequest.getPassword())).thenReturn(null);

        // Act
        var result = userController.login(loginRequest);

        // Assert
        assertEquals(expected.getStatus(), result.getStatus());
    }

    @Test
    void testUserDetails() {
        // Arrange
        var expected = Response.Status.OK;
        var user = new User();
        Mockito.when(mockedUserService.getUserByToken(TOKEN)).thenReturn(user);

        // Act
        var result = userController.getUserDetails(TOKEN);

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testUserDetails400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;
        Mockito.when(mockedUserService.getUserByToken(TOKEN)).thenReturn(null);

        // Act
        var result = userController.getUserDetails(TOKEN);

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void userAttachedAccounts() {
        // Arrange
        var expected = Response.Status.OK;
        var users = new ArrayList<AccountAttach>();
        Mockito.when(mockedUserService.getAttachedAccounts(TOKEN)).thenReturn(users);

        // Act
        var result = userController.getAttachedAccounts(TOKEN);

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void userAttachedAccounts400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;
        Mockito.when(mockedUserService.getAttachedAccounts(TOKEN)).thenReturn(null);

        // Act
        var result = userController.getAttachedAccounts(TOKEN);

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

}
