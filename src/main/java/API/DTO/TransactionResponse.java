package API.DTO;

import java.net.URI;

public class TransactionResponse {
    private URI url;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
