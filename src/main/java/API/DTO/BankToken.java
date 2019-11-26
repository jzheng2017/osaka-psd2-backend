package API.DTO;

import com.google.gson.annotations.SerializedName;

public class BankToken {
    private int id;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
    private Bank bank;

    public BankToken(int id, Bank bank, String accessToken, String refreshToken) {
        this.id = id;
        this.bank = bank;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public BankToken() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
