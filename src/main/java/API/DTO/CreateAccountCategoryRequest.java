package API.DTO;

public class CreateAccountCategoryRequest {
    private String name;
    private String categoryId;
    private String iban;

    public CreateAccountCategoryRequest(String name, String iban) {
        this.name = name;
        this.iban = iban;
    }

    public CreateAccountCategoryRequest() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
