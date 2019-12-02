package API.DTO;

import java.net.URI;

public class TransactionResponse {
    private String id;
    private URI url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
