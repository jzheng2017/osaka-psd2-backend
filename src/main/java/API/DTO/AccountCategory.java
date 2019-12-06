package API.DTO;

public class AccountCategory {
    private String name;
    private String id;
    private String iban;

    public AccountCategory(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public AccountCategory() {
    }

    public AccountCategory(String name, String id, String iban) {
        this.name = name;
        this.id = id;
        this.iban = iban;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
