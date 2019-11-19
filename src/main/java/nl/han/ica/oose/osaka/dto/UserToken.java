package nl.han.ica.oose.osaka.dto;

public class UserToken {
    private String token;

    public UserToken(){

    }
    public UserToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
