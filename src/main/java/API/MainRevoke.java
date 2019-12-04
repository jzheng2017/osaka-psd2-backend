package API;

import API.Services.UserService;

public class MainRevoke {

    public static void main(String[] args) {
        UserService service = new UserService();
        String token = "eb465a15-594d-480e-94d9-7179fb5cb7d1";
        service.deleteBankAccount(token, 41+"");
    }
}
