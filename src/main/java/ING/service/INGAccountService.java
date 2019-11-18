package ING.service;

import ING.INGAccesTokenGenerator;
import org.springframework.http.ResponseEntity;


public class INGAccountService {
    //Dependency injection toepassen
    private INGAccesTokenGenerator tokenGenerator = new INGAccesTokenGenerator();
    public ResponseEntity<String> getUserAccounts() {
        return null;
    }

    public ResponseEntity<String> getAccountBalances() {
        return null;
    }

    public ResponseEntity<String> getAccountTransactions() {
        return null;
    }

    public String getHeaders() {
        String accessToken = tokenGenerator.getAccessToken();
//        return tokenGenerator.getHeaders(accessToken);
        return null;
    }

}
