package API.Adapter;

import API.DTO.Account;
import API.DTO.AuthorizationCode;
import API.DTO.Balance;
import API.DTO.Transaction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("API.Adapter")
public abstract class BankAdapter {

    public abstract Account getUserAccounts(String token, String id);

    public abstract Balance getAccountBalances(String token, String id);

    public abstract Transaction getAccountTransactions(String token, String id);

    public static BankAdapter getBankAdapter(String bank) {
        if ("RABO".equals(bank)) {
            return new RaboAdapter() ;
        } else {
            return new INGAdapter();
        }
    }

    public abstract AuthorizationCode authorize();
}
