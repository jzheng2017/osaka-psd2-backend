package API.ING.Controller;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.ING.Service.INGAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.core.Response;

@Component
public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

    @GetMapping()
    public Account getUserAccounts() {
        return ingAccountService.getUserAccounts();
    }

    @GetMapping()
    public Balance getAccountBalances(String accountID) {
        return ingAccountService.getAccountBalances(accountID);
    }

    @GetMapping()
    public Transaction getAccountTransactions(String accountID) {
        return ingAccountService.getAccountTransactions(accountID);
    }
}
