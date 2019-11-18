package API.Adapter;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component("RaboAdapter")
public class RaboAdapter extends BankAdapter {
    @Override
    public Response getUserAccounts() {
        return null;
    }

    @Override
    public Response getAccountBalances(String id) {
        return null;
    }

    @Override
    public Response getAccountTransactions(String id) {
        return null;
    }
}
