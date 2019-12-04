package API.DTO;

import com.google.gson.annotations.SerializedName;

public class Account {
    private static final String DEFAULT_TYPE = "Betaalrekening";

    @SerializedName("resourceId")
    private String id;
    private String iban;
    private String name;
    @SerializedName("product")
    private String type;
    private String currency;
    private Double balance;
    private Integer tableId;

    public Account() {
        type = DEFAULT_TYPE;
    }

    public Account(String id, String iban, String name, String currency, Double balance) {
        this.id = id;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
