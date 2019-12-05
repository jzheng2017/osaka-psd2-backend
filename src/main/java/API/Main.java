package API;

import API.DTO.Account;
import API.DTO.User;
import API.DataSource.AccountDAO;

public class Main {
    public static void main(String[] args) {
        var dao = new AccountDAO();

        var account = new Account();
        account.setIban("ifoijeroijero");

        var user = new User();
        user.setId(103);

        var name = "hallo";

        dao.createAccountType(account, user, name);
    }
}
