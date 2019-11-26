package API.RABO.Controller;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;
import API.RABO.Service.RabobankService;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

public class RabobankController {
    private RabobankService rabobankService = new RabobankService();

    public String authorize() {
        return rabobankService.authorize();
    }

    public BankToken token(String code) {
        return rabobankService.token(code);
    }

    public Account getUserAccounts(String token)  {
        return rabobankService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String id) {
        return rabobankService.getAccountBalances(token, id);
    }

    public Transaction getAccountTransactions(String token, String id) {
        return rabobankService.getAccountTransactions(token, id);
    }

    public String checkEnoughBalance(String code) {
        return rabobankService.checkEnoughBalance(code);
    }

    public BankToken refresh(String code) {
        return rabobankService.refresh(code);
    }

    public URI getAuthorizationUrl(String redirectUrl, String state) {
        try {
            return new URI(rabobankService.getAuthorizationUrl(redirectUrl, state));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
