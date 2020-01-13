package API.Banks;

import API.Banks.ABNAMRO.ABNAMROClient;
import API.Banks.Dummy.DummyClient;
import API.Banks.ING.INGClient;
import API.Banks.Rabobank.RabobankClient;
import API.DTO.Bank;

public class ClientFactory {
    private ClientFactory() {
        // Constructor toegevoegd voor Sonar validatie
    }
    
    public static Client getClient(Bank name) {
        switch (name) {
            case RABOBANK:
                return new RabobankClient();
            case ABNAMRO:
                return new ABNAMROClient();
            case ING:
                return new INGClient();
            default:
                return new DummyClient();
        }
    }
}
