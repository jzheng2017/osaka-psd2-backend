package API.ING.Service;

import API.Adapter.INGAdapter;
import API.DataSource.Transaction;
import API.DTO.Account;
import API.DTO.Balance;
import API.ING.Token.INGAccessTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ComponentScan("ING.Token")
public class INGAccountService {
    private final String baseUrl = INGAdapter.baseUrl;;

    @Autowired
    private  RestTemplate rest = new RestTemplate();
    @Autowired
    private INGAccessTokenGenerator tokenGenerator = new INGAccessTokenGenerator();

    //TODO: bij een request een access token ophalen en deze omzetten in een customer access token.
    // Wanneer deze eenmaal bekend en opgeslagen is in de database kan deze hergebruikt en refreshed worden met een refresh token.
    public Account getUserAccounts() {
        String url = "/v3/accounts/";
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());
        ResponseEntity<Account> responseEntity = rest.exchange(baseUrl + url, HttpMethod.GET, requestEntity, Account.class);
        return responseEntity.getBody();
    }

    public Balance getAccountBalances(String accountID) {
        String url = "/v3/accounts/"+accountID+"/balances";
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());
        ResponseEntity<Balance> responseEntity = rest.exchange(baseUrl + url, HttpMethod.GET, requestEntity, Balance.class);
        return responseEntity.getBody();
    }

    public Transaction getAccountTransactions(String accountID) {
        String url = "/v2/accounts/"+accountID+"/transactions";
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders());
        ResponseEntity<Transaction> responseEntity = rest.exchange(baseUrl + url, HttpMethod.GET, requestEntity, Transaction.class);
        return responseEntity.getBody();
    }

    private HttpHeaders getHeaders() {
        return tokenGenerator.getHeaders();
    }

}
