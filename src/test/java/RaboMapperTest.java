import API.DTO.Account;
import API.DTO.RABO.RaboAccount;
import API.RABO.RaboMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaboMapperTest {
    private RaboMapper sut;

    @BeforeEach
    void setup() {
        sut = new RaboMapper();
    }

    @Test
    void testMapToAccount() {
        // Setup
        RaboAccount raboAccount = new RaboAccount();
        ArrayList<RaboAccount> raboAccounts = new ArrayList<>();
//        raboAccounts.add(new RaboAccount("leh", "EUR", "NL","Frank","19","enabled"));
        raboAccount.setAccounts(raboAccounts);
        String expectedResult = "Frank";
        // Run the test
        final Account result = sut.mapToAccount(raboAccount);
        // Verify the results
        assertEquals(expectedResult, result.getAccounts().get(0).getName());
    }
}
