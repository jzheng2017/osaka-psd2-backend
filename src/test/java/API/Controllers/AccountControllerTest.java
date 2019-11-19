//package API.Controllers;
//
//import API.DTO.Account;
//import API.DTO.Balance;
//import API.DTO.Transaction;
//import API.Services.AccountService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import javax.ws.rs.core.Response;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//class AccountControllerTest {
//
//    @Mock
//    private AccountService mockService;
//
//    @InjectMocks
//    private AccountController accountControllerUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        initMocks(this);
//    }
//
//    @Test
//    void testAuthorize() {
//        // Setup
//        final String expectedResult = "result";
//        when(mockService.authorize("bank")).thenReturn("result");
//
//        // Run the test
//        final String result = accountControllerUnderTest.authorize("bank");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testToken() {
//        // Setup
//        final ResponseEntity<String> expectedResult = new ResponseEntity<>(HttpStatus.CONTINUE);
//        when(mockService.token("bank", "code")).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
//
//        // Run the test
//        final ResponseEntity<String> result = accountControllerUnderTest.token("code", "bank");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetUserAccounts() {
//        // Setup
//        final Account expectedResult = new Account("ID", "iban", "name", "accountType", "currency", "maskedPan", "linkToBalance", "linkToTransactions", new ArrayList<>(Arrays.asList()));
//
//        // Configure AccountService.getUserAccounts(...).
//        final ResponseEntity<String> account = new Account("ID", "iban", "name", "accountType", "currency", "maskedPan", "linkToBalance", "linkToTransactions", new ArrayList<>(Arrays.asList()));
//        when(mockService.getUserAccounts("bank", "token")).thenReturn(account);
//
//        // Run the test
//        final ResponseEntity<String> result = accountControllerUnderTest.getUserAccounts("bank", "token");
//        // Verify the results
//        assertEquals(expectedResult.getIban(), result.getIban());
//    }
//
//    @Test
//    void testGetAccountBalances() {
//        // Setup
//        final Balance expectedResult = new Balance(null, new ArrayList<>(Arrays.asList()), "balanceType", null, "lastChangeDateTime", "referenceDate");
//
//        // Configure AccountService.getAccountBalances(...).
//        final Balance balance = new Balance(null, new ArrayList<>(Arrays.asList()), "balanceType", null, "lastChangeDateTime", "referenceDate");
//        when(mockService.getAccountBalances("bank", "token", "id")).thenReturn(balance);
//
//        // Run the test
//        final Balance result = accountControllerUnderTest.getAccountBalances("id", "token", "bank");
//
//        // Verify the results
//        assertEquals(expectedResult.getBalances(), balance.getBalances());
//    }
//
//    @Test
//    void testGetAccountTransactions() {
//        // Setup
//        final Transaction expectedResult = new Transaction();
//        when(mockService.getAccountTransactions("bank", "token", "id")).thenReturn(new Transaction());
//
//        // Run the test
//        final Transaction result = accountControllerUnderTest.getAccountTransactions("id", "token", "bank");
//        // Verify the results
//        assertEquals(expectedResult.getIban(), result.getIban());
//    }
//}
