//package API.Controllers;
//
//import API.Services.AccountService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
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
//        final ResponseEntity<String> expectedResult = new ResponseEntity<>(HttpStatus.CONTINUE);
//        when(mockService.getUserAccounts("bank", "token")).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
//
//        // Run the test
//        final ResponseEntity<String> result = accountControllerUnderTest.getUserAccounts("bank", "token");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetAccountBalances() {
//        // Setup
//        final ResponseEntity<String> expectedResult = new ResponseEntity<>(HttpStatus.CONTINUE);
//        when(mockService.getAccountBalances("bank", "token", "id")).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
//
//        // Run the test
//        final ResponseEntity<String> result = accountControllerUnderTest.getAccountBalances("id", "token", "bank");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetAccountTransactions() {
//        // Setup
//        final ResponseEntity<String> expectedResult = new ResponseEntity<>(HttpStatus.CONTINUE);
//        when(mockService.getAccountTransactions("bank", "token", "id")).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
//
//        // Run the test
//        final ResponseEntity<String> result = accountControllerUnderTest.getAccountTransactions("id", "token", "bank");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//}
