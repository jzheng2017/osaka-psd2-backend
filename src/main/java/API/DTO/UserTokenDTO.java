package API.DTO;

public class UserTokenDTO {
    private String token;

    public UserTokenDTO() {

    }

    public UserTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
