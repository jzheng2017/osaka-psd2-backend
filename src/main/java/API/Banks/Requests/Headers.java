package API.Banks.Requests;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Headers {

    private Headers() {
        //Voor sonar validatie
    }

    private static final Logger LOGGER = Logger.getLogger(Headers.class.getName());
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Headers.class.getClassLoader().getResourceAsStream("ip.properties"));
        } catch (IOException e) {
            LOGGER.severe("PROPERTY NOT AVAILABLE " + e);
        }
    }

    public static final String BEARER = "Bearer ";
    public static final String SIGNATUREWITHSPACE = "Signature ";
    public static final String BASIC = "Basic ";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DIGEST = "Digest";
    public static final String DATE = "Date";
    public static final String TPP_SIGNATURE_CERTIFICATE = "TPP-Signature-Certificate";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SIGNATURE = "Signature";
    public static final String ACCEPT = "Accept";
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String TPP_REDIRECT_URI = "TPP-Redirect-URI";
    public static final String PSU_IP_ADDRESS = "PSU-IP-Address";
    public static final String X_IBM_CLIENT_ID = "x-ibm-client-id";
    public static final String HARDCODED_IP_PAYMENT_REQUEST = properties.getProperty("paymentrequest.ip");
    public static final String HARDCODED_IP_RABOBANK = properties.getProperty("rabobank.ip");
}
