package API.Banks;

import API.Banks.ABNAMRO.ABNAMROClient;
import API.Banks.ING.INGClient;
import API.Banks.Rabobank.RabobankClient;
import API.DTO.Bank;

public class ClientFactory {
    public static Client getClient(Bank name) {
        switch (name) {
            case RABOBANK:
                return new RabobankClient();
            case ABNAMRO:
                return new ABNAMROClient();
            default:
                return new INGClient();
        }
    }
}
