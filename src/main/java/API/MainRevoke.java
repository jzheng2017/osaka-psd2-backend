package API;

import API.Banks.ING.INGClient;

public class MainRevoke {

    public static void main(String[] args) {
        INGClient client = new INGClient();
        String token = "8cd0b519-a29b-4bf4-bb4e-462c793a4d69";
        client.revoke(token);
    }
}
