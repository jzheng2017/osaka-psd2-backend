package API.DTO;

public class CreateAccountCategoryRequest {
    private String name;
    private String id;
    private String iban;

    public CreateAccountCategoryRequest(String name, String iban) {
        this.name = name;
        this.iban = iban;
    }

    public CreateAccountCategoryRequest() {
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
}
