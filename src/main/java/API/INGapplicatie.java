package API;

import API.ING.Controller.INGAccountController;
import API.ING.Service.INGAccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class INGapplicatie {
    static {
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("https.protocols", "TLSv1.2");
        System.setProperty("javax.net.ssl.keyStorePassword", "school");
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/certs/ING/ingjks.jks");
    }
    //TODO:Autowiring, dat werkt allemaal voor geen meter
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean("INGAccountServiceImplementation")
    public INGAccountService accountService() {
        return new INGAccountService();
    }

    @Bean("INGAccountControllerImplementation")
    public INGAccountController accountController() {
        return new INGAccountController();
    }

    public static void main(String[] args) {
        SpringApplication.run(INGapplicatie.class, args);
    }
}
