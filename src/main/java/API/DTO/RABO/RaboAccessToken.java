package API.DTO.RABO;

public class RaboAccessToken {
    private String token_type;
    private String access_token;
    private int expires_in;
    private int consented_on;
    private String scope;
    private String refresh_token;
    private int refresh_token_expires_in;

    public RaboAccessToken() {
    }

    public RaboAccessToken(String token_type, String access_token, int expires_in, int consented_on, String scope, String refresh_token, int refresh_token_expires_in) {
        this.token_type = token_type;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.consented_on = consented_on;
        this.scope = scope;
        this.refresh_token = refresh_token;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getConsented_on() {
        return consented_on;
    }

    public void setConsented_on(int consented_on) {
        this.consented_on = consented_on;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getRefresh_token_expires_in() {
        return refresh_token_expires_in;
    }

    public void setRefresh_token_expires_in(int refresh_token_expires_in) {
        this.refresh_token_expires_in = refresh_token_expires_in;
    }
}
