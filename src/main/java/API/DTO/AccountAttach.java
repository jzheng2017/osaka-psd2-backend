package API.DTO;

public class AccountAttach {
    private int id;
    private String bank;

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

    public AccountAttach(int id, String bank) {
        this.id = id;
        this.bank = bank;
    }
}


