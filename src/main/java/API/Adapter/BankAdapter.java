package API.Adapter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
@ComponentScan("API.Adapter")
public abstract class BankAdapter {
    public abstract Response getUserAccounts();

    public abstract Response getAccountBalances(String id);

    public abstract Response getAccountTransactions(String id);

    public static BankAdapter getBankAdapter(String bank) {
        if ("RABO".equals(bank)) {
            return new RaboAdapter();
        } else {
            return new INGAdapter();
        }
    }
}
