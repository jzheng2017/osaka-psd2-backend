package ING;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
        INGAccesTokenGenerator service = new INGAccesTokenGenerator();
        System.out.println(service.getAccessToken());
    }
}
