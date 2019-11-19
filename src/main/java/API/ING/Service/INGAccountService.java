package API.ING.Service;

import API.Adapter.INGAdapter;
import API.DTO.Transaction;
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
    private final String baseUrl = INGAdapter.baseUrl;
    private final String customerAuthToken = "2c1c404c-c960-49aa-8777-19c805713edf";

    @Autowired
    private RestTemplate rest;
    @Autowired
    private INGAccessTokenGenerator tokenGenerator = new INGAccessTokenGenerator();

    //TODO: bij een request een access token ophalen en deze omzetten in een customer access token.
    // Wanneer deze eenmaal bekend en opgeslagen is in de database kan deze hergebruikt en refreshed worden met een refresh token.
    public ResponseEntity<String> getUserAccounts() {
        String url = "/v3/accounts/";
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders(url, customerAuthToken));
        ResponseEntity<String> responseEntity = rest.exchange(baseUrl + url, HttpMethod.GET, requestEntity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> getAccountBalances(String accountID) {
        String url = "/v3/accounts/" + accountID + "/balances";
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders(url, customerAuthToken));
        ResponseEntity<String> responseEntity = rest.exchange(baseUrl + url, HttpMethod.GET, requestEntity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> getAccountTransactions(String accountID) {
        String url = "/v2/accounts/" + accountID + "/transactions";
        HttpEntity<String> requestEntity = new HttpEntity<>("", getHeaders(url, customerAuthToken));
        ResponseEntity<String> responseEntity = rest.exchange(baseUrl + url, HttpMethod.GET, requestEntity, String.class);
        return responseEntity;
    }

    private HttpHeaders getHeaders(String endpoint, String authorizationCode) {
        return tokenGenerator.getHeaders(endpoint, authorizationCode);
    }

}
