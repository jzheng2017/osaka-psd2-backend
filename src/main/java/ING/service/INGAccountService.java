package ING.service;

import ING.Token.INGAccesTokenGenerator;
import datasource.Transaction;
import dto.Account;
import dto.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class INGAccountService {
    private final String baseUrl = "https://api.sandbox.ing.com";

    @Autowired
    private  RestTemplate rest;
    @Autowired
    private INGAccesTokenGenerator tokenGenerator;

    public Account getUserAccounts() {
        String url = "/v3/accounts";
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
