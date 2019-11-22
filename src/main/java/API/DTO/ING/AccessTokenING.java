package API.DTO.ING;

import API.DTO.Key;

import java.util.ArrayList;

public class AccessTokenING {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String client_id;
    private ArrayList<AccessTokenING> accessTokenINGS;
    private ArrayList<Key> keys = new ArrayList<>();

    public AccessTokenING() {
    }

    public AccessTokenING(String access_token, String expires_in, String scope, String token_type, String client_id, ArrayList<Key> keys) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.token_type = token_type;
        this.client_id = client_id;
        this.keys = keys;
    }

    public ArrayList<AccessTokenING> getAccessTokenINGS() {
        return accessTokenINGS;
    }

    public void setAccessTokenINGS(ArrayList<AccessTokenING> accessTokenINGS) {
        this.accessTokenINGS = accessTokenINGS;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = keys;
    }
}

