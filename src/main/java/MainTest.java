import API.ING.Service.INGAccountService;

public class MainTest {
    public static void main(String[] args) {
        var service = new INGAccountService();
        var test = service.refresh("ff");

        var t = service.getUserAccounts(test.getAccessToken());

        System.out.println(t.getAccounts());
    }
}
