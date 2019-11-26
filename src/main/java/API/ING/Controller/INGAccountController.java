package API.ING.Controller;

import API.DTO.*;
import API.ING.Service.INGAccountService;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

    public Account getUserAccounts(String token) {
        return ingAccountService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String accountID) {
        return ingAccountService.getAccountBalances(token ,accountID);
    }

    public Transaction getAccountTransactions(String token,String accountID) {
        return ingAccountService.getAccountTransactions(token,accountID);
    }

    public BankToken authorize() {
        return ingAccountService.authorize();
    }

    public BankToken getCustomerAuthorizationToken(String code) {
        return ingAccountService.getAuthorizationCode(code);
    }

    public BankToken refresh(String code) {
        return ingAccountService.refresh(code);
    }

    public URI getAuthorizationUrl(String redirectUrl, String state) {
        try {
            return new URI(ingAccountService.getAuthorizationUrl(redirectUrl, state));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BankToken token(String code) {
        return ingAccountService.token(code);
    }
}
