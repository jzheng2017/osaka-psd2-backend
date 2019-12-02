package API.DTO;

import com.google.gson.annotations.SerializedName;

public class FundsResponse {
    @SerializedName("fundsAvailable")
    private Boolean available;
    @SerializedName("checkDateTime")
    private String date;

    public FundsResponse(Boolean available, String date) {
        this.available = available;
        this.date = date;
    }

    public FundsResponse() {
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
