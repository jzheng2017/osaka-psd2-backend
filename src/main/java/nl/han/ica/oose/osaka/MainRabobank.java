package nl.han.ica.oose.osaka;

import API.RABO.Service.RabobankService;
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

    }

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MainRabobank.class, args);
    }
}
