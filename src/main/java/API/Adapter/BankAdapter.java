package API.Adapter;

import API.DTO.AccessToken;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("API.Adapter")
public abstract class BankAdapter {

    public abstract ResponseEntity<String> getUserAccounts(String token);

    public abstract ResponseEntity<String> getAccountBalances(String token, String id);

    public abstract ResponseEntity<String> getAccountTransactions(String token, String id);

    public static BankAdapter getBankAdapter(String bank) {
        if ("RABO".equals(bank) || "rabo".equals(bank)) {
            return new RaboAdapter() ;
        } else {
            return new INGAdapter();
        }
    }

    public abstract String authorize();

    public abstract AccessToken token(String code);
}
