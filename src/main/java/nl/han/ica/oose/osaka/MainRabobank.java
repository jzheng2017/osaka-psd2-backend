package nl.han.ica.oose.osaka;

import nl.han.ica.oose.osaka.Services.RabobankService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MainRabobank {
    static {
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("https.protocols", "TLSv1.2");
        System.setProperty("javax.net.ssl.keyStorePassword", "school");
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStore",  "src/main/resources/certs/rabobank/cert.jks");
    }

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean
    public RabobankService rabobankService() {
        return new RabobankService();
    }

    public static void main(String[] args) {
        SpringApplication.run(MainRabobank.class, args);
    }
}
