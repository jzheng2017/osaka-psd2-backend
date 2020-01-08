package API;

import API.Banks.ABNAMRO.ABNAMROClient;
import API.DTO.Account;
import API.DTO.Currency;
import API.DTO.PaymentRequest;

public class Main {
    public static void main(String[] args) {
        var client = new ABNAMROClient();
        System.out.println( client.getAuthorizationUrl("xx","xx").toString());

        var token = "JRPuHP99l_cerf38KVaWVDSNb5z4WrZqwloJ6vAA";
        var response = client.token(token);

        System.out.println(response.getAccessToken());


    }
}
