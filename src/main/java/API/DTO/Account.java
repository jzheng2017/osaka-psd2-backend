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
    private String category;

    public Account() {
        this.type = DEFAULT_TYPE;
    }

    public Account(String id, String iban, String name, String currency, Double balance) {
        this.id = id;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.type = DEFAULT_TYPE;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public Integer getTableId() {
        return tableId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return iban + " - " + name + " " + balance + " " + currency;
    }
}
