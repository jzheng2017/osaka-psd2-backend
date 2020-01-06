package API.DataSource;

import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.core.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BankTokenDaoTest {

    private BankTokenDao bankTokenDaoUnderTest;
    private Database db;
   private final BankToken bankToken = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

    @Mock
    private ResultSet mockedResultset;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        bankTokenDaoUnderTest = new BankTokenDao();
        db = Mockito.mock(Database.class);
        bankTokenDaoUnderTest.setDb(db);
        when(db.query(anyString(),any())).thenReturn(mockedResultset);
        when(mockedResultset.next()).thenReturn(true).thenReturn(false);
        when(mockedResultset.first()).thenReturn(true).thenReturn(false);
        when(mockedResultset.getString(anyString())).thenReturn("RABOBANK");
        when(mockedResultset.getInt(anyString())).thenReturn(0);

    }

    @Test
    void testAttachBankAccountToUser() {
        // Setup
        final User user = new User(0, "name", "email", "password", "token");

        // Run the test
        bankTokenDaoUnderTest.attachBankAccountToUser(user, Bank.RABOBANK, "accessToken", "refreshToken");

        // Verify the results
        verify(db).query(anyString(),any());
    }

    @Test
    void testGetBankTokensArrayForUser() {
        // Setup
        // Run the test
        final List<BankToken> result = bankTokenDaoUnderTest.getBankTokensForUser("token");

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testGetBankTokensArrayForUserError() throws SQLException {
        // Setup
        // Run the test
        when(mockedResultset.next()).thenReturn(false);
        final List<BankToken> result = bankTokenDaoUnderTest.getBankTokensForUser("token");

        // Verify the results
        assertEquals(Collections.emptyList(),result);
    }

    @Test
    void testGetBankTokensForUser() {
        // Setup
        // Run the test
        final BankToken result = bankTokenDaoUnderTest.getBankTokensForUser("token", "id");

        // Verify the results
        assertEquals(bankToken.getBank(), result.getBank());
    }

    @Test
    void testGetBankTokensForUserError() throws SQLException {
        // Setup
        // Run the test
        when(mockedResultset.first()).thenReturn(false);
        final BankToken result = bankTokenDaoUnderTest.getBankTokensForUser("token", "id");

        // Verify the results
        assertNull(result);
    }

    @Test
    void testUpdateBankToken() {
        // Setup
        // Run the test
        bankTokenDaoUnderTest.updateBankToken(bankToken);
        verify(db).query(anyString(),any());
        // Verify the results
    }

    @Test
    void testDeleteBankToken() {
        // Setup

        // Run the test
        bankTokenDaoUnderTest.deleteBankToken("tableid", "token");

        // Verify the results
        verify(db).query(anyString(),any());
    }
}
