package API.Adapter;

import API.ING.Controller.INGAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
public class INGAdapter extends BankAdapter {
    public final static String baseUrl = "https://api.sandbox.ing.com";

    private INGAccountController controller = new INGAccountController();

    @Override
    public Response getUserAccounts() {
        return controller.getUserAccounts();
    }

    @Override
    public Response getAccountBalances(String id)  {
        return controller.getAccountBalances(id);
    }

    @Override
    public Response getAccountTransactions(String id)  {
        return controller.getAccountTransactions(id);
    }
}
