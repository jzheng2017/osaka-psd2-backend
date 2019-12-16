package API.Banks;

import API.Banks.Dummy.DummyClient;
import API.Banks.ING.INGClient;
import API.Banks.Rabobank.RabobankClient;
import API.DTO.Bank;

public class ClientFactory {
    public static Client getClient(Bank name) {
        switch (name) {
            case RABOBANK:
                return new RabobankClient();
            case DUMMY:
                return new DummyClient();
            default:
                return new INGClient();
        }
    }
}
