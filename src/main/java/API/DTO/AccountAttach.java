package API.DTO;

public class AccountAttach {
    private int id;
    private String bank;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AccountAttach() {
    }

    public AccountAttach(int id, String bank, int userId) {
        this.id = id;
        this.bank = bank;
        this.userId = userId;
    }
}


