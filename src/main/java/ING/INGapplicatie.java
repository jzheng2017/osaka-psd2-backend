package ING;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class INGapplicatie {
    static {
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("https.protocols", "TLSv1.2");
        System.setProperty("javax.net.ssl.keyStorePassword", "godver");
        System.setProperty("javax.net.ssl.keyStoreType", "RSA");
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/certs/example_eidas_tls.cer");
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/certs/example_eidas_tls_.key");
    }

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(INGapplicatie.class, args);
    }
}
