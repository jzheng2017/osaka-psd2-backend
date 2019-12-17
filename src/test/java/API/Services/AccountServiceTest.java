package API.Services;

import API.Banks.ING.INGClient;
import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
import API.DataSource.AccountDAO;
import API.DataSource.BankTokenDao;
import API.DataSource.TransactionDAO;
import API.DataSource.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountServiceTest {

    private AccountService accountServiceUnderTest;
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private String token = "token";
    private User user = new User();

    @BeforeEach
    void setUp() {
        accountServiceUnderTest = new AccountService();
        userDAO = Mockito.mock(UserDAO.class);
        bankTokenDao = Mockito.mock(BankTokenDao.class);
        accountDAO = Mockito.mock(AccountDAO.class);
        transactionDAO = Mockito.mock(TransactionDAO.class);
        accountServiceUnderTest.setAccountDAO(accountDAO);
        accountServiceUnderTest.setBankTokenDao(bankTokenDao);
        accountServiceUnderTest.setTransactionDAO(transactionDAO);
        accountServiceUnderTest.setUserDAO(userDAO);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        final AccountsResponse expectedResult = new AccountsResponse();
        expectedResult.setBalance(-900.4500045776367);
        Account account = new Account();
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account);
        expectedResult.setAccounts(accounts);

        ArrayList<BankToken> bankTokens = new ArrayList<>();
        BankToken bankToken = new BankToken();
        bankToken.setBank(Bank.ING);
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankTokens.add(bankToken);

        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(bankTokenDao.getBankTokensForUser(user)).thenReturn(bankTokens);
        // Run the test
        final AccountsResponse result = accountServiceUnderTest.getUserAccounts(token);

        // Verify the results
        assertEquals(expectedResult.getBalance(), result.getBalance());
    }

    @Test
    void testGetAccountDetails() {
        // Setup
        final AccountDetails expectedResult = new AccountDetails();
        expectedResult.setAccount(new Account("id", "NL69INGB0123456789", "name", "currency", 0.0));
        expectedResult.setTransactions(new ArrayList<>(Arrays.asList()));
        BankToken bankToken = new BankToken();
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankToken.setBank(Bank.ING);

        // Run the test
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(bankTokenDao.getBankTokensForUser(user, "tableId")).thenReturn(bankToken);
        final AccountDetails result = accountServiceUnderTest.getAccountDetails(token, "450ffbb8-9f11-4ec6-a1e1-df48aefc82ef", "tableId");

        // Verify the results
        assertEquals(expectedResult.getAccount().getIban(), result.getAccount().getIban());
    }

    @Test
    void testAssignAccountToCategory() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");
        final AccountCategory expectedResult = new AccountCategory("name", "id", "iban");
        BankToken bankToken = new BankToken();
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankToken.setBank(Bank.ING);

        // Run the test
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(bankTokenDao.getBankTokensForUser(user, "tableId")).thenReturn(bankToken);
        Mockito.when(accountDAO.addToAccountCategory(request,user)).thenReturn(expectedResult);
        final AccountCategory result = accountServiceUnderTest.assignAccountToCategory("token", request);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testAddNewCategory() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");
        final AccountCategory expectedResult = new AccountCategory("name", "id", "iban");

        // Run the test
        BankToken bankToken = new BankToken();
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankToken.setBank(Bank.ING);

        // Run the test
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(bankTokenDao.getBankTokensForUser(user, "tableId")).thenReturn(bankToken);
        Mockito.when(accountDAO.createAccountCategory(request,user)).thenReturn(expectedResult);
        final AccountCategory result = accountServiceUnderTest.addNewCategory("token", request);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllCategories() {
        // Setup
        final ArrayList<AccountCategory> expectedResult = new ArrayList<>(Arrays.asList());

        // Run the test
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(accountDAO.getAccountCategoriesByUserId(Mockito.any())).thenReturn(new ArrayList<>());
        final ArrayList<AccountCategory> result = accountServiceUnderTest.getAllCategories("token");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllCategoriesNull() {
        // Setup
        final ArrayList<AccountCategory> expectedResult = null;

        // Run the test
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(null);
        Mockito.when(accountDAO.getAccountCategoriesByUserId(Mockito.any())).thenReturn(new ArrayList<>());
        final ArrayList<AccountCategory> result = accountServiceUnderTest.getAllCategories("token");

        // Verify the results
        assertTrue(result.isEmpty());
    }
    @Test
    void testGetUserAccountsCategorized() {
        // Setup
        final AccountsResponse expectedResult = new AccountsResponse();
        expectedResult.setBalance(0.0);
        Account account = new Account();
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account);
        expectedResult.setAccounts(accounts);

        ArrayList<BankToken> bankTokens = new ArrayList<>();
        BankToken bankToken = new BankToken();
        bankToken.setBank(Bank.ING);
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankTokens.add(bankToken);

        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(bankTokenDao.getBankTokensForUser(user)).thenReturn(bankTokens);
        Mockito.when(accountDAO.getAccountCategoryById(Mockito.anyString(), Mockito.anyInt())).thenReturn("test");

        // Run the test
        final AccountsResponse result = accountServiceUnderTest.getUserAccountsCategorized("token", "categoryId");

        // Verify the results
        assertEquals(expectedResult.getBalance(), result.getBalance());
    }

    @Test
    void testGetTransactionDetails(){
        // Setup
        final Transaction expectedResult = new Transaction();
        expectedResult.setDate("2016-10-01");
        BankToken bankToken = new BankToken();
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());
        bankToken.setBank(Bank.ING);

        // Run the test
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
        Mockito.when(bankTokenDao.getBankTokensForUser(user, "tableId")).thenReturn(bankToken);
        final Transaction result = accountServiceUnderTest.getTransactionDetails(token, "450ffbb8-9f11-4ec6-a1e1-df48aefc82ef", "021614321078455845000000001", "tableId");

        // Verify the results
        assertEquals(expectedResult.getDate(), result.getDate());
    }
}
