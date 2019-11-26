import API.DTO.BankToken;
import API.ING.Service.INGAccountService;
import API.ING.Util.INGUtil;
import API.RABO.Service.RabobankService;
import com.google.gson.Gson;

public class MainTest {
    public static void main(String[] args) {
        var service = new INGAccountService();
        var test = service.refresh("ff");

        var t = service.getUserAccounts(test.getAccessToken());

        System.out.println(t.getAccounts());
    }
}
