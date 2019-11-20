package API.DTO;

public class AuthorizationCode {
    private String authorizationCode;

    public AuthorizationCode() {
    }

    public AuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
