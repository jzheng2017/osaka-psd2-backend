package API.Services;

import API.Banks.ING.INGClient;
import API.DTO.*;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import API.HashedPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userServiceUnderTest;
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;
    private String token = "token";
    private User user = new User();

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService();
        userDAO = Mockito.mock(UserDAO.class);
        bankTokenDao = Mockito.mock(BankTokenDao.class);
        userServiceUnderTest.setBankTokenDao(bankTokenDao);
        userServiceUnderTest.setUserDAO(userDAO);
        when(userDAO.getUserByToken(token)).thenReturn(user);
    }

    @Test
    void testRegister() {
        // Setup
        final RegisterRequest request = new RegisterRequest();
        request.setName("name");
        request.setEmail("email");
        request.setPassword("password");
        // Run the test
        when(userDAO.getUserByEmail(anyString())).thenReturn(null);
        userServiceUnderTest.register(request);
        Mockito.verify(userDAO).registerUser(anyString(), anyString(), anyString());
        // Verify the results
    }

    @Test
    void testRegisterReturnsNull() {
        // Setup
        final RegisterRequest request = new RegisterRequest();
        request.setName("name");
        request.setEmail("email");
        request.setPassword("password");

        // Run the test
        when(userDAO.getUserByEmail(anyString())).thenReturn(user);
        final LoginResponse result = userServiceUnderTest.register(request);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testLogin() {
        // Setup
        user.setPassword(HashedPassword.generate("password"));
        user.setEmail("email");
        final LoginResponse expectedResult = new LoginResponse("name", "email", "token");

        // Run the test
        when(userDAO.getUserByEmail(anyString())).thenReturn(user);
        ArrayList<BankToken> bankTokens = new ArrayList<>();
        BankToken bankToken = new BankToken();
        bankToken.setBank(Bank.ING);
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankTokens.add(bankToken);
        when(bankTokenDao.getBankTokensForUser(any())).thenReturn(bankTokens);
        final LoginResponse result = userServiceUnderTest.login("email", "password");
        Mockito.verify(bankTokenDao).updateBankToken(any());
        // Verify the results
        assertEquals(expectedResult.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserByToken() {
        // Setup
        // Run the test
        final User result = userServiceUnderTest.getUserByToken("token");

        // Verify the results
        assertEquals(user, result);
    }

    @Test
    void testAttachBankAccount() {
        // Setup
        final BankToken bankToken = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        userServiceUnderTest.attachBankAccount("token", bankToken);
        Mockito.verify(bankTokenDao).attachBankAccountToUser(any(),any(),anyString(),anyString());
        // Verify the results
    }

    @Test
    void testGetAttachedAccounts() {
        // Setup
        final ArrayList<AccountAttach> expectedResult = new ArrayList<>(Collections.emptyList());

        // Run the test
        when(userDAO.getAttachedAccounts("token")).thenReturn(expectedResult);
        final ArrayList<AccountAttach> result = userServiceUnderTest.getAttachedAccounts("token");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeleteBankAccount() {
        // Setup
        BankToken bankToken = new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf");
        bankToken.setBank(Bank.ING);
        when(bankTokenDao.getBankTokensForUser(any(), anyString())).thenReturn(bankToken);
        // Run the test
        userServiceUnderTest.deleteBankAccount("token", "tableid");
        Mockito.verify(bankTokenDao).deleteBankToken(anyString(),any());
        // Verify the results
    }

    @Test
    void testCheckIfAvailableTrue() {
        // Setup
        final BankConnection expectedResult = new BankConnection(false, 4);

        // Run the test
        when(userDAO.getUserConnections(anyString())).thenReturn(2);
        final BankConnection result = userServiceUnderTest.checkIfAvailable("token");

        // Verify the results
        assertEquals(expectedResult.isLimitReached(), result.isLimitReached());
    }

    @Test
    void testCheckIfAvailableFalse() {
        // Setup
        final BankConnection expectedResult = new BankConnection(true, 4);

        // Run the test
        when(userDAO.getUserConnections(anyString())).thenReturn(4);
        final BankConnection result = userServiceUnderTest.checkIfAvailable("token");

        // Verify the results
        assertEquals(expectedResult.isLimitReached(), result.isLimitReached());
    }
}
