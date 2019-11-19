package API.ING.Controller;

import API.ING.Service.INGAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

    @GetMapping()
    public ResponseEntity<String> getUserAccounts() {
        return ingAccountService.getUserAccounts();
    }

    @GetMapping()
    public ResponseEntity<String> getAccountBalances(String accountID) {
        return ingAccountService.getAccountBalances(accountID);
    }

    @GetMapping()
    public ResponseEntity<String> getAccountTransactions(String accountID) {
        return ingAccountService.getAccountTransactions(accountID);
    }
}
