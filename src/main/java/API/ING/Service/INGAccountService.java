package API.ING.Service;

import API.DTO.*;
import API.DTO.ING.INGAccount;
import API.DTO.ING.INGBalance;
import API.DTO.ING.INGTransaction;
import API.Generator;
import API.ING.INGMapper;
import API.ING.Util.INGUtil;
import API.RSA;
import com.google.gson.Gson;

import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;


public class INGAccountService {
    private static final String BASE_URL = "https://api.sandbox.ing.com";
    private static final String KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDS/VFSaWsUKxw+lBFH/7xfpRQQNtDiqpn4dS1cpRxZgvjNnikFn9ROqjlILCYIC8FvkclN0kqzjNSihQKzUyyrbxoXTxw/dHfDIEOddGzJIO0X0Pi3R3hi4ruGeU+as+Dx3z2+TGZTpnYWlWwIVi2OKkyFoXPGTGYzXRXCFnCwnQN8Rr6W9lBVq4eEyxYAGcE84UumakPz/OS3FoEzz6ZGenGrvhJO+cA1ly1eySL7egsD8rnHButKAnNszfozlp+/+VAuMmZtv6VIlrfyQHL/WQkaUeKqU5YukAOgyaryZfiiQEGk4Yr35FTguMjS7FsAsmVprEn79755NraSfN7AgMBAAECggEAG0Qo+Wyj9TcDuKqjBNfyL+CjmE7/ufUQEma5r7zNywbwLQ1g3GK3qfzOmlbBlbYJTd4IFFh432TXD6sRInUkGm8uE1ZZePWdIf8Mxh39oIBSwaCPDovw6qf6ABxsmRvBYJKLBxcVD8tc86s+5Eboj18Q9A/tVebbf+oa4QAg8+r1KGUio2zUwal3lWeULZ2K8Xri9ltfiyqriTodd9nkV2TiSsjn1m5djSzI8DCTwdf10cbjgJ9XP9tMbPSp53aE8PEpp9vQUyL6uuS/5CFLXDbYK/BiqBzLdKlXKt/92qaXaDDDagUFQ8IyLdI6zwjW8f7DXyTSVqqjH1Jw+3BKUQKBgQDSEQ8x7lXc3MVFfJ/H24iN/PALA8Yl0adP/mvXZH9zM/OfW/hr6tTUU/F2utSx6QT6zWUKczskbXaENKH5v0BlZ9tsF68c+yd0zbPoDhhuM4iR+sdGUuZXjM6u3JBqCb8I0pxvpW5GqZbAn05Ktk0BRwMZpBOTT9Ho5AuRGd5PbQKBgQCgAZRR2BS6sznYTmci7kWnKpbJVS3p+KJTE4Y9vuiGkKb0dncl3k4EzW23zUCwNh6xEiLFbXe1ivWlMYSuOmZpa3ISI6FtcEOpkd8HUu19753bQHUTdDBLTMoNL0FioqKAo6b6vJcPaqtqt6ijsBOuGO2t85o8g34e0/KjSb61hwKBgBSG3jk+1N0UJaK4ntRku19EjCBHaiFf7z192wPdKicTuIal8gx5kfp9iWbUstv/rSDk2S7AO9M/bwlUK0/ARIakM2jIl6/5Ss27HA1c8z4xgvLg0oAosaF0fO3RV7tE4In4KpkuTSxSfgyshHYAgl3Rlpf21ILcleJwBkFTicmxAoGAEwidEC9YJ+1yEB0jf7BAcOZMEZ8kWxTMmn1UFrxDBN7oPWRqQAL13PRi/N5Zt5x4gi/aGwoul1X0arY9RkyEKj4xz56VcWNNaTqFAWYIAlcivBYq1ymXJR35WyAn8wfNtOfC0Ujl31udEJDQashjTu6AN5Um39P0iM5Fqs729LkCgYBszT9OlaE6xgzMvZPeS79OboYBP4bFWcT5rbZhfTacY9pF+Xa67L3HC+8vaYOGu3iK/KSAGKEZ4/BUW2l36PjFq7QMprY6NGgYz9vSPahBB+baZXWYJTN5KlL/IrhAPBsdHo1qsUY3POMZboY4LPPDvk4nT1DA641MdbFJGWHaBA==";
    private static final String CERT = "MIID8zCCAtugAwIBAgIESZYC0jANBgkqhkiG9w0BAQsFADByMR8wHQYDVQQDDBZBcHBDZXJ0aWZpY2F0ZU1lYW5zQVBJMQwwCgYDVQQLDANJTkcxDDAKBgNVBAoMA0lORzESMBAGA1UEBwwJQW1zdGVyZGFtMRIwEAYDVQQIDAlBbXN0ZXJkYW0xCzAJBgNVBAYTAk5MMB4XDTE5MDMwNDEzNTkwOFoXDTIwMDMwNDE0NTkwOFowPDEbMBkGA1UECwwSc2FuZGJveF9laWRhc19xd2FjMR0wGwYDVQRhDBRQU0ROTC1TQlgtMTIzNDUxMjM0NTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAINL9UVJpaxQrHD6UEUf/vF+lFBA20OKqmfh1LVylHFmC+M2eKQWf1E6qOUgsJggLwW+RyU3SSrOM1KKFArNTLKtvGhdPHD90d8MgQ510bMkg7RfQ+LdHeGLiu4Z5T5qz4PHfPb5MZlOmdhaVbAhWLY4qTIWhc8ZMZjNdFcIWcLCdA3xGvpb2UFWrh4TLFgAZwTzhS6ZqQ/P85LcWgTPPpkZ6cau+Ek75wDWXLV7JIvt6CwPyuccG60oCc2zN+jOWn7/5UC4yZm2/pUiWt/JAcv9ZCRpR4qpTli6QA6DJqvJl+KJAQaThivfkVOC4yNLsWwCyZWmsSfv3vnk2tpJ83sCAwEAAaOBxjCBwzAVBgNVHR8EDjAMMAqgCKAGhwR/AAABMCEGA1UdIwQaMBigFgQUcEi7XgDA9Cb4xHTReNLETt+0clkwgYYGCCsGAQUFBwEDBHoweDAKBgYEAI5GAQEMADATBgYEAI5GAQYwCQYHBACORgEGAzBVBgYEAIGYJwIwSzA5MBEGBwQAgZgnAQMMBlBTUF9BSTARBgcEAIGYJwEBDAZQU1BfQVMwEQYHBACBmCcBAgwGUFNQX1BJDAZYLVdJTkcMBk5MLVhXRzANBgkqhkiG9w0BAQsFAAOCAQEAX2cd5VV48bkc9o1i//f0LG8IhvQiaqFYJFsBEyaWjJYrTVKcY1w6aL9hEnBfIY+6cXiHADkoI9cJGSeOFRw9c86Wyqe1cQhMri43os2svQCuMr/p9HFFs6FvdVJa+uNXB4U2wEBY++N0DPe65XiW7oJYkntZxpa3AfIpDS2+yo5rueWMsChBxC5KbNpKDt11UN14Bmxnwu9yzGUQkdfOE0xWyw9Vmy2X5oJVsBsEUa+UZ5GVOLq36bQNzCScqO1JCK7ZQfHaK89VfHrCx/zNx+INBPriTqpD0czQC28lsntB2JNRMksZZSio1tiwqwCSxCtD1BPiX5KUevQsfOxTAQ==";
    private static final String SIGNING_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDFZU4DuACe0841AB+YsISlBRx2Lag1GRDseKjEPVwjGzL1BEU0WFuXxtJb76hKH8Z2ja12z057uL/nhzGnuy9CK0EO84suoLn/j7G01c5gsVWNAXYTIlaUUGr1kSqYP/m6TxSkke32Woll+7FR4XRkD80AANHecQvWZJJsSpGnFG90n6uf70Nqq5EG2FlKU8mQ5MrJD23NtOCayuvMPUYsDxQy3oaqlhmcW+4lqlr3IMfER7WVprPCBOaZwPSy78rkP2GpJaVvEQOnQG53kZ4RhWFXpmp/Yny8xtNUP1Rkcj0FvRb327X41TI8qpX6PCBuYDft6ENoHXuLgAFsi3EDAgMBAAECggEBAJ2tRFoYGvbD/c28YYDYT7x5jrif0+NGOIyL/VN0KCsqyNOLK7Sad9+PQ+2ITIeZRjDhDxT+l5SgiTpqdbGkBiX24ysMQ7tlS+3mXG808nuua8YMysKSBVLHwgiSgtHHGLZIARdMMic9Ps+l84iSbSSYsoo0HXBwIKKLB6NVFW+9rHWCU7vdV0javwRBq0vwO9AK/GsNpU7zfzi0G0YjqYuUoabJ7EFHt84WRm2DbccBbGSm5KbwBeKoEYjsLHR4ba2Lbe38oKGHZ8NRnyVvToA5c7QoYwB6UkD0tRgMzWm6gYK2NBQx9pHeMeD+GHkMM35uHCEmMRWGxb26mB0xPfECgYEA9puX2sMnZYDTRIDc6kUgW17iHqo6c5Ko6chmJJB8w57vCU0P0QQ2l4wB0rENHxR/XgelDpHQh451B8DfDXWMssQwoOIBDcPkWT+CKkTHsLhKZSZktJo3Pf+veq284zNiDi3YTsHC3ksQQfZirBOptIiDTKks4XqQqIqNtqd+RX0CgYEAzOnl0rrCbuLhlMGFfDJC2vEBqafVnIW+suCAC8DPCQjQ6elXGdLprd7zf9T//8X1YUvLIHVGeeIaFcnIdTo2xOKQNT6vZE0298EZarBHJRvFZYnHKEqanq1onFsdD9rypm5iixyW+cEXatNzQz+FTaxx0lyTJ6h7bbnaBKBUWH8CgYAbAZUSrvZ8hiwcv2Px/9n4R57JruixyWjYGUseS/htz9TrltXZlPWJiRqWAS+nrK36FSo9Oziz76TfUX7b0Xi0BwowRC/LWx2BrJPLnzajIrt68kZrBMxx7LNB4w7hbroZRWn/zfZSM7Q3FQ1fPNQD4kNGvOweUTbQTkQQsa0BXQKBgQCaz8djWtfUgLKe+UoJF8vsr5JrA7Ld6ym06Om0d3mzQKbdYf39M5x6UOu0U08JhRyq4mFXZ2LUaJ7+gRFih5WjT+xVXcOSysdRjODP+tf1UDLlUJ9XcG26nhZfsnKVbPxQAyuOIA5sKJaxjYkScGYc1cC0hl5i4uZFctklD6BJrwKBgQCksHiK2vTh2RPDxv+K+XgAQTSa7Cj5lFbKfOg+uGdJAhEb6e9njRn5gREj20CYY/26MoRgzqpyhLRLNzq7d++r6LExysXK+MA0CIHmxqHZnJsFAlgZvBdT8YXO5uLsAnA9CqlUX7Ao9iCvNnaulfnj+DUUdDSbmsIpmzvWO1XUaQ==";
    private static final String KEY_ID = "SN=499602D2,CA=C=NL,ST=Amsterdam,L=Amsterdam,O=ING,OU=ING,CN=AppCertificateMeansAPI";
    private static final String CLIENT_ID = "5ca1ab1e-c0ca-c01a-cafe-154deadbea75";
    private static final String certificate = "-----BEGIN CERTIFICATE-----MIID9TCCAt2gAwIBAgIESZYC0jANBgkqhkiG9w0BAQsFADByMR8wHQYDVQQDDBZBcHBDZXJ0aWZpY2F0ZU1lYW5zQVBJMQwwCgYDVQQLDANJTkcxDDAKBgNVBAoMA0lORzESMBAGA1UEBwwJQW1zdGVyZGFtMRIwEAYDVQQIDAlBbXN0ZXJkYW0xCzAJBgNVBAYTAk5MMB4XDTE5MDMwNDEzNTkwN1oXDTIwMDMwNDE0NTkwN1owPjEdMBsGA1UECwwUc2FuZGJveF9laWRhc19xc2VhbGMxHTAbBgNVBGEMFFBTRE5MLVNCWC0xMjM0NTEyMzQ1MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxWVOA7gAntPONQAfmLCEpQUcdi2oNRkQ7HioxD1cIxsy9QRFNFhbl8bSW++oSh/Gdo2tds9Oe7i/54cxp7svQitBDvOLLqC5/4+xtNXOYLFVjQF2EyJWlFBq9ZEqmD/5uk8UpJHt9lqJZfuxUeF0ZA/NAADR3nEL1mSSbEqRpxRvdJ+rn+9DaquRBthZSlPJkOTKyQ9tzbTgmsrrzD1GLA8UMt6GqpYZnFvuJapa9yDHxEe1laazwgTmmcD0su/K5D9hqSWlbxEDp0Bud5GeEYVhV6Zqf2J8vMbTVD9UZHI9Bb0W99u1+NUyPKqV+jwgbmA37ehDaB17i4ABbItxAwIDAQABo4HGMIHDMBUGA1UdHwQOMAwwCqAIoAaHBH8AAAEwIQYDVR0jBBowGKAWBBRwSLteAMD0JvjEdNF40sRO37RyWTCBhgYIKwYBBQUHAQMEejB4MAoGBgQAjkYBAQwAMBMGBgQAjkYBBjAJBgcEAI5GAQYCMFUGBgQAgZgnAjBLMDkwEQYHBACBmCcBAwwGUFNQX0FJMBEGBwQAgZgnAQEMBlBTUF9BUzARBgcEAIGYJwECDAZQU1BfUEkMBlgtV0lORwwGTkwtWFdHMA0GCSqGSIb3DQEBCwUAA4IBAQB3TXQgvS+vm0CuFoILkAwXc/FKL9MNHb1JYc/TZKbHwPDsYJT3KNCMDs/HWnBD/VSNPMteH8Pk5Eh+PIvQyOhY3LeqvmTwDZ6lMUYk5yRRXXh/zYbiilZAATrOBCo02ymm6TqcYfPHF3kh4FHIVLsSe4m/XuGoBO2ru5sMUCxncrWtw4UXZ38ncms2zHbkH6TB5cSh2LXY2aZSX8NvYyHPCCw0jrkVm1/kAs69xM2JfIh8fJtycI9NvcoSd8HGSe/D5SjUwIFKTWXq2eCMsNEAG51qS0jWXQqPtqBRRTdu+AEAJ3DeIY6Qqg2mjk+i6rTMqSwFVqo7Cq7zHty4E7qK-----END CERTIFICATE-----";

    private Gson gson;
    private INGMapper mapper;
    private Generator gen;
    private INGUtil util;

    public INGAccountService() {
        this.gen = new Generator();
        this.util = new INGUtil();
        this.gson = new Gson();
        this.mapper = new INGMapper();
    }

    public BankToken authorize() {
        var body = "grant_type=client_credentials";
        var url = "/oauth2/token";
        return gson.fromJson(util.getAccessToken(body, url), BankToken.class);
    }

    public BankToken getAuthorizationCode(String code) {
        var body = "grant_type=authorization_code&code=2c1c404c-c960-49aa-8777-19c805713edf&redirect_uri=xxx";
        var url = "/oauth2/token";
        var request = util.getCustomerAccessToken(body, code, url);
        return gson.fromJson(request, BankToken.class);
    }

    public Account getUserAccounts(String code) {
        var url = "/v3/accounts";
        INGAccount account = gson.fromJson(util.doApiRequest(code, url), INGAccount.class);
        return mapper.mapToAccount(account);
    }

    public Balance getAccountBalances(String code, String accountID) {
        var url = "/v3/accounts/" + accountID + "/balances?balanceTypes=expected";
        INGBalance balance = gson.fromJson(util.doApiRequest(code, url), INGBalance.class);
        return mapper.mapToBalance(balance);
    }

    public Transaction getAccountTransactions(String code, String accountID) {
        var url = "/v2/accounts/" + accountID + "/transactions?dateFrom=2016-10-01&dateTo=2016-11-21&limit=10";
        INGTransaction transactions = gson.fromJson(util.doApiRequest(code, url), INGTransaction.class);
        return mapper.mapToTransaction(transactions);
    }
}
