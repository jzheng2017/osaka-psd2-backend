package ING;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface BankService {
    ResponseEntity<String> getUserAccounts() throws IOException, GeneralSecurityException;

    ResponseEntity<String> getAccountBalances(@PathVariable String id) throws IOException, GeneralSecurityException;

    ResponseEntity<String> getAccountTransactions(@PathVariable String id) throws IOException, GeneralSecurityException;
}
