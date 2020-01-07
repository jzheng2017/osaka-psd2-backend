package API.Banks.ABNAMRO;

import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ABNAMROClientTest {
    private ABNAMROClient abnamroClient;
    private ABNAMROUtil mockedABNAMROUtil;
    private Gson gson;

    @BeforeEach
    void setup() {
        gson = new Gson();
        abnamroClient = new ABNAMROClient();
        mockedABNAMROUtil = Mockito.mock(ABNAMROUtil.class);
        abnamroClient.setAbnamroUtil(mockedABNAMROUtil);
    }

    @Test
    void testGetAuthorizationUrl() {
        // Arrange
        var expected = "URL";
        Mockito.when(mockedABNAMROUtil.getAuthorizationUrl(Mockito.anyString())).thenReturn(URI.create(expected));

        // Act
        var result = abnamroClient.getAuthorizationUrl("XXX", "XXX");

        // Assert
        assertEquals(expected, result.toString());
    }

    @Test
    void testToken() {
        // Arrange
        var accessToken = "XXX";
        var expectedBankToken = new BankToken();
        expectedBankToken.setAccessToken(accessToken);
        var expected = new JsonObject();
        expected.addProperty("access_token", accessToken);
        Mockito.when(mockedABNAMROUtil.getBankToken(Mockito.anyString())).thenReturn(gson.fromJson(expected, JsonObject.class));

        // Act
        var result = abnamroClient.token("XXX");

        // Assert
        assertEquals(expectedBankToken.getAccessToken(), result.getAccessToken());
    }

    @Test
    void testRefresh() {
        // Arrange
        var accessToken = "XXX";
        var expectedBankToken = new BankToken();
        expectedBankToken.setAccessToken(accessToken);
        var expected = new JsonObject();
        expected.addProperty("access_token", accessToken);

        Mockito.when(mockedABNAMROUtil.getBankToken(Mockito.anyString())).thenReturn(gson.fromJson(expected, JsonObject.class));

        // Act
        var result = abnamroClient.refresh("XXX");

        // Assert
        assertEquals(accessToken, result.getAccessToken());
    }

    @Test
    void testGetUserAccounts() {
        // Arrange
        var token = "TOKEN";
        var iban = "IBAN";

        var exampleConsentInfo = new JsonObject();
        exampleConsentInfo.addProperty("iban", iban);

        var exampleDetails = new JsonObject();
        exampleDetails.addProperty("accountNumber", iban);
        exampleDetails.addProperty("accountHolderName", "NAME");
        exampleDetails.addProperty("currency", "EUR");

        Mockito.when(mockedABNAMROUtil.get("/consentinfo", token)).thenReturn(exampleConsentInfo);
        Mockito.when(mockedABNAMROUtil.get(ABNAMROClient.ACCOUNTS+iban+"/details", token)).thenReturn(exampleDetails);

        // Act
        var accounts = abnamroClient.getUserAccounts(token);
        var account = accounts.get(0);

        // Assert
        assertEquals(iban, account.getIban());
    }


    @Test
    void testGetBalance() {
        // Arrange
        var expected = new JsonObject();
        var token = "TOKEN";
        var id = "ID";
        var amount = 500;
        expected.addProperty("amount", amount);

        Mockito.when(mockedABNAMROUtil.get(ABNAMROClient.ACCOUNTS+id+"/balances", token)).thenReturn(expected);

        // Act
        var balance = abnamroClient.getBalance(token, id);

        // Assert
        assertEquals(amount, balance);
    }

    @Test
    void testGetAccountDetails() {
        // Arrange
        var token = "TOKEN";
        var iban = "IBAN";
        var noAccounts = 20;

        var exampleConsentInfo = new JsonObject();
        exampleConsentInfo.addProperty("iban", iban);

        var exampleDetails = new JsonObject();
        exampleDetails.addProperty("accountNumber", iban);
        exampleDetails.addProperty("accountHolderName", "NAME");
        exampleDetails.addProperty("currency", "EUR");

        var exampleTransactionsResponse = "{\"accountNumber\":\"NL12ABNA9999876523\",\"nextPageKey\":null,\"transactions\":[{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL91ABNA9999414548\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 229\",\"Omschrijving: regel 0005/00219\",\"regeloverloop\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":-2452.08,\"counterPartyAccountNumber\":\"NL73ABNA9998944031\",\"counterPartyName\":\" Opdrachtgever 229\",\"amount\":19.18,\"currency\":\"EUR\",\"transactionId\":\"9835M4905095147S0WP\"},{\"mutationCode\":\"247\",\"descriptionLines\":[\"SEPA Incasso algemeen doorlopend\",\"Incassant: NL98ZZZ123465500000\",\"Naam Begunstigde 699\",\"Machtiging: 000015\",\"Omschrijving: regel 0005/00365\",\"regeloverloop\",\"regeloverloop\",\"IBAN: NL19BOFA9999539999\",\"Kenmerk: 0003-00A010\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":-2471.26,\"counterPartyAccountNumber\":\"NL08BOFA9992751363\",\"counterPartyName\":\" Begunstigde 184\",\"amount\":-4982.96,\"currency\":\"EUR\",\"transactionId\":\"9834I4417249943S0AD\"},{\"mutationCode\":\"654\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL58INGB9999114160\",\"BIC: INGBNL2A\",\"Naam:  Opdrachtgever 152\",\"Omschrijving: regel 0005/00362\",\"regeloverloop\",\"regeloverloop\",\"regeloverloop\",\"Kenmerk: 0003-00A006\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":2511.7,\"counterPartyAccountNumber\":\"NL08INGB9994418696\",\"counterPartyName\":\" Opdrachtgever 152\",\"amount\":541.19,\"currency\":\"EUR\",\"transactionId\":\"9835I3632849880S0AQ\"},{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL91ABNA9999414548\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 229\",\"Omschrijving: regel 0005/00216\",\"regeloverloop\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":1970.51,\"counterPartyAccountNumber\":\"NL73ABNA9998944031\",\"counterPartyName\":\" Opdrachtgever 229\",\"amount\":59.53,\"currency\":\"EUR\",\"transactionId\":\"9835M4707098742S0WP\"},{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL91ABNA9999414548\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 229\",\"Omschrijving: regel 0005/00217\",\"regeloverloop\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":1910.98,\"counterPartyAccountNumber\":\"NL73ABNA9998944031\",\"counterPartyName\":\" Opdrachtgever 229\",\"amount\":38.36,\"currency\":\"EUR\",\"transactionId\":\"9835M4746284964S0WP\"},{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL91ABNA9999414548\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 229\",\"Omschrijving: regel 0005/00218\",\"regeloverloop\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":1872.62,\"counterPartyAccountNumber\":\"NL73ABNA9998944031\",\"counterPartyName\":\" Opdrachtgever 229\",\"amount\":19.18,\"currency\":\"EUR\",\"transactionId\":\"9835M4989206911S0WP\"},{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL91ABNA9999414548\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 229\",\"Omschrijving: regel 0005/00220\",\"regeloverloop\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":1853.44,\"counterPartyAccountNumber\":\"NL73ABNA9998944031\",\"counterPartyName\":\" Opdrachtgever 229\",\"amount\":19.18,\"currency\":\"EUR\",\"transactionId\":\"9835M4929807202S0WP\"},{\"mutationCode\":\"656\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL69ABNA9999049411\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 230\",\"Omschrijving: regel 0005/00123\",\"Kenmerk: 00000-0029\"],\"bookDate\":\"2019-12-17\",\"balanceAfterMutation\":1834.26,\"counterPartyAccountNumber\":\"NL95ABNA9996760456\",\"counterPartyName\":\" Opdrachtgever 230\",\"amount\":710.09,\"currency\":\"EUR\",\"transactionId\":\"GT020008635274980AO\"},{\"mutationCode\":\"654\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL41INGB9999915444\",\"BIC: INGBNL2A\",\"Naam:  Opdrachtgever 233\",\"Omschrijving: regel 0005/00124\",\"Kenmerk: NOTPROVIDED\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":1124.17,\"counterPartyAccountNumber\":\"NL13INGB9998729092\",\"counterPartyName\":\" Opdrachtgever 233\",\"amount\":944.41,\"currency\":\"EUR\",\"transactionId\":\"9834H1457743146S0AQ\"},{\"mutationCode\":\"654\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL41RABO9999946411\",\"BIC: RABONL2U\",\"Naam:  Opdrachtgever 232\",\"Omschrijving: regel 0005/00001\",\"Kenmerk: 00000-0054\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":179.76,\"counterPartyAccountNumber\":\"NL08RABO9999092039\",\"counterPartyName\":\" Opdrachtgever 232\",\"amount\":36.6,\"currency\":\"EUR\",\"transactionId\":\"9834L5410693378S0AQ\"},{\"mutationCode\":\"654\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL05DEUT9999114119\",\"BIC: DEUTNL2AXXX\",\"Naam:  Opdrachtgever 231\",\"regeloverloop\",\"Omschrijving: /INV/00003\",\"Kenmerk: NOTPROVIDED\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":143.16,\"counterPartyAccountNumber\":\"NL71DEUT9998386705\",\"counterPartyName\":\" Opdrachtgever 231\",\"amount\":126.38,\"currency\":\"EUR\",\"transactionId\":\"9834O1212366121S0AB\"},{\"mutationCode\":\"656\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL14ABNA9999045014\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 171\",\"Omschrijving: regel 0005/00134\",\"Kenmerk: 00000-0084\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":16.78,\"counterPartyAccountNumber\":\"NL42ABNA9999195431\",\"counterPartyName\":\" Opdrachtgever 171\",\"amount\":239.88,\"currency\":\"EUR\",\"transactionId\":\"GT020008624263060AO\"},{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL49ABNA9999050494\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 187\",\"Omschrijving: regel 0005/00335\",\"regeloverloop\",\"Kenmerk: NONREF\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":-223.1,\"counterPartyAccountNumber\":\"NL38ABNA9997035313\",\"counterPartyName\":\" Opdrachtgever 187\",\"amount\":311.98,\"currency\":\"EUR\",\"transactionId\":\"GT020008620664020AO\"},{\"mutationCode\":\"656\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL94ABNA9999406511\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 178\",\"Omschrijving: regel 0005/00113\",\"Kenmerk: 00000-0046\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":-535.08,\"counterPartyAccountNumber\":\"NL69ABNA9994558927\",\"counterPartyName\":\" Opdrachtgever 178\",\"amount\":19.12,\"currency\":\"EUR\",\"transactionId\":\"GT020008627965306AO\"},{\"mutationCode\":\"247\",\"descriptionLines\":[\"SEPA Incasso algemeen doorlopend\",\"Incassant: NL93ZZZ123456790051\",\"Naam Begunstigde 704\",\"Machtiging: 000004\",\"Omschrijving: regel 0005/00292\",\"regeloverloop\",\"IBAN: NL12COBA9999959555\",\"Kenmerk: 01647041/207883/2018-40\"],\"bookDate\":\"2019-12-16\",\"balanceAfterMutation\":-554.2,\"counterPartyAccountNumber\":\"NL42COBA9990177147\",\"counterPartyName\":\" Begunstigde 234\",\"amount\":-129.46,\"currency\":\"EUR\",\"transactionId\":\"9832K1328612124S0AD\"},{\"mutationCode\":\"656\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL11ABNA9999454141\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 235\",\"Omschrijving: regel 0005/00111\",\"Kenmerk: A0000-0094\",\"regeloverloop\"],\"bookDate\":\"2019-12-13\",\"balanceAfterMutation\":-424.74,\"counterPartyAccountNumber\":\"NL62ABNA9990891192\",\"counterPartyName\":\" Opdrachtgever 235\",\"amount\":151.25,\"currency\":\"EUR\",\"transactionId\":\"GT030606165111000FS\"},{\"mutationCode\":\"656\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL59ABNA9999149551\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 215\",\"Omschrijving: regel 0005/00303\",\"Kenmerk: 00000-0013\",\"regeloverloop\",\"ID debiteur: NL123456789B01\",\"ID begunstigde: NL811981997B01\"],\"bookDate\":\"2019-12-13\",\"balanceAfterMutation\":-575.99,\"counterPartyAccountNumber\":\"NL97ABNA9990063249\",\"counterPartyName\":\" Opdrachtgever 215\",\"amount\":196.63,\"currency\":\"EUR\",\"transactionId\":\"GT037006173935170AE\"},{\"mutationCode\":\"526\",\"descriptionLines\":[\"ABN AMRO Bank N.V.\",\"Heeft u internetbankieren dan is\",\"uw nota beschikbaar onder Tools,\",\"Downloaden, Afschriften          \",\"of u ontvangt deze per post.\"],\"bookDate\":\"2019-12-12\",\"balanceAfterMutation\":-772.62,\"amount\":-53.3,\"currency\":\"EUR\"},{\"mutationCode\":\"247\",\"descriptionLines\":[\"SEPA Incasso algemeen doorlopend\",\"Incassant: NL64ZZZ123423030000\",\"Naam Begunstigde 712\",\"Machtiging: 000008\",\"Omschrijving: regel 0005/00310\",\"regeloverloop\",\"regeloverloop\",\"regeloverloop\",\"IBAN: NL10ABNA0240120000\"],\"bookDate\":\"2019-12-12\",\"balanceAfterMutation\":-719.32,\"counterPartyAccountNumber\":\"NL37ABNA9996543056\",\"counterPartyName\":\" Begunstigde 237\",\"amount\":-1193,\"currency\":\"EUR\",\"transactionId\":\"GT025020237138830AE\"},{\"mutationCode\":\"658\",\"descriptionLines\":[\"SEPA Overboeking\",\"IBAN: NL40ABNA9999144415\",\"BIC: ABNANL2A\",\"Naam:  Opdrachtgever 236\",\"Omschrijving: regel 0005/00356\",\"regeloverloop\"],\"bookDate\":\"2019-12-12\",\"balanceAfterMutation\":473.68,\"counterPartyAccountNumber\":\"NL08ABNA9992751363\",\"counterPartyName\":\" Opdrachtgever 236\",\"amount\":26.98,\"currency\":\"EUR\",\"transactionId\":\"9830J5542785372S0AI\"}]}";
        var exampleTransactionResponseJson = gson.fromJson(exampleTransactionsResponse, JsonObject.class);

        Mockito.when(mockedABNAMROUtil.get("/consentinfo", token)).thenReturn(exampleConsentInfo);
        Mockito.when(mockedABNAMROUtil.get(ABNAMROClient.ACCOUNTS+iban+"/details", token)).thenReturn(exampleDetails);
        Mockito.when(mockedABNAMROUtil.get(ABNAMROClient.ACCOUNTS+iban+ABNAMROClient.TRANSACTIONS, token)).thenReturn(exampleTransactionResponseJson);

        // Act
        var response = abnamroClient.getAccountDetails(token, "XXX");

        // Assert
        assertEquals(noAccounts, response.getTransactions().size());
    }

    @Test
    void testInitiateTransaction() {
        // Arrange
        var token = "XXX";
        var url = URI.create("URL");

        var exampleAccount = new Account();
        exampleAccount.setName("NAME");
        exampleAccount.setIban("IBAN");

        var request = new PaymentRequest();
        request.setSender(exampleAccount);
        request.setReceiver(exampleAccount);
        request.setCurrency(Currency.EUR);
        request.setAmount(50.00);

        var exampleBankTokenResponse = new JsonObject();
        exampleBankTokenResponse.addProperty("access_token", token);
        Mockito.when(mockedABNAMROUtil.getBankToken(Mockito.anyString())).thenReturn(exampleBankTokenResponse);

        var examplePaymentsResponse = new JsonObject();
        examplePaymentsResponse.addProperty("transactionId", "123");
        Mockito.when(mockedABNAMROUtil.post(Mockito.any(), Mockito.any(), Mockito.anyString())).thenReturn(examplePaymentsResponse);

        Mockito.when(mockedABNAMROUtil.getAuthorizationUrl(Mockito.anyString())).thenReturn(url);

        // Act
        var response = abnamroClient.initiateTransaction(token, request);

        // Assert
        assertEquals(url, response.getUrl());
    }

    @Test
    void testIsPaymentToken() {
        // Arrange
        var token = "TOKEN";
        var exampleConsentResponse = new JsonObject();
        exampleConsentResponse.addProperty("scopes", "psd2:payment:sepa:write psd2:payment:sepa:read");
        Mockito.when(mockedABNAMROUtil.get("/consentinfo", token)).thenReturn(exampleConsentResponse);

        // Act
        var response = abnamroClient.isPaymentToken(token);

        // Assert
        assertTrue(response);
    }

    @Test
    void testPay() {
        // Arrange
        var token = "TOKEN";
        var id = "PAYMENTID";
        var status = "EXECUTED";

        var examplePaymentsResponse = new JsonObject();
        examplePaymentsResponse.addProperty("status", status);
        examplePaymentsResponse.addProperty("transactionId", id);
        Mockito.when(mockedABNAMROUtil.put(Mockito.anyString(), Mockito.anyString())).thenReturn(examplePaymentsResponse);

        // Act
        var response = abnamroClient.pay(token, id);

        // Assert
        assertTrue(response.isPaid());
        assertEquals(id, response.getId());
    }
}
