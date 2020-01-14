package API.Services;

import API.DTO.Bank;
import java.net.URI;

public class DummyService {
    public static final String DEFAULT_ING_AUTHORIZATION_CODE = "2c1c404c-c960-49aa-8777-19c805713edf";
    public static final String DEFAULT_DUMMY_AUTHORIZATION_CODE = "dummy";

    public URI authorize(Bank bank, String uri, String state) {
        var code = getDefaultAuthorizationCode(bank);
        return URI.create(uri+"?code="+code+"&state="+state);
    }

    private String getDefaultAuthorizationCode(Bank bank) {
        return bank.equals(Bank.ING) ? DEFAULT_ING_AUTHORIZATION_CODE : DEFAULT_DUMMY_AUTHORIZATION_CODE;
    }
}
