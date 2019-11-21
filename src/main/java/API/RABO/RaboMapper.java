package API.RABO;

import API.DTO.Account;
import API.DTO.RABO.RaboAccount;

public class RaboMapper {
    public Account mapToAccount(RaboAccount account) {
        Account acc = new Account();
        acc.setAccountType(account.toString());
        return acc;
    }
}
