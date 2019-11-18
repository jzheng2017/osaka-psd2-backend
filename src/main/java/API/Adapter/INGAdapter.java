package API.Adapter;

import API.DataSource.Transaction;
import API.DTO.Account;
import API.DTO.Balance;
import API.ING.Controller.INGAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component("INGAdapter")
@ComponentScan("API.ING.Controller.INGAccountController")
public class INGAdapter extends BankAdapter {
    private INGAccountController controller = new INGAccountController();

    @Override
    public Account getUserAccounts() {
        return controller.getUserAccounts();
    }

    @Override
    public Balance getAccountBalances(String id)  {
        return controller.getAccountBalances(id);
    }

    @Override
    public Transaction getAccountTransactions(String id)  {
        return controller.getAccountTransactions(id);
    }
}
