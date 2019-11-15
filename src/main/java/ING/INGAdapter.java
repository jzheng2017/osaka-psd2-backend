package ING;

import ING.controller.INGAccountController;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class INGAdapter implements BankService {
    @Override
    public ResponseEntity<String> getUserAccounts() throws IOException, GeneralSecurityException {
        return INGAccountController.getUserAccounts();
    }

    @Override
    public ResponseEntity<String> getAccountBalances(String id) throws IOException, GeneralSecurityException {
        return INGAccountController.getAccountBalances();
    }

    @Override
    public ResponseEntity<String> getAccountTransactions(String id) throws IOException, GeneralSecurityException {
        return INGAccountController.getAccountTransactions();
    }
}
