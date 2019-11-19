package API.Adapter;

import API.DTO.Account;
import API.DTO.AuthorizationCode;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RABO.Controller.RabobankController;
import org.springframework.stereotype.Component;

@Component("RaboAdapter")
public class RaboAdapter extends BankAdapter {
    private RabobankController controller =  new RabobankController();
    @Override
    public Account getUserAccounts(String token, String id) {
        return controller.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return controller.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return controller.getAccountTransactions(token, id);
    }

    @Override
    public AuthorizationCode authorize() {
        return controller.authorize();
    }
}
