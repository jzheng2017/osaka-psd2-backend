package API.Adapter;

import API.DataSource.Transaction;
import API.DTO.Account;
import API.DTO.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.thymeleaf.context.IContext;

import javax.persistence.criteria.CriteriaBuilder;

@Component
@ComponentScan("API.Adapter")
public abstract class BankAdapter {
    public abstract Account getUserAccounts();

    public abstract Balance getAccountBalances(String id);

    public abstract Transaction getAccountTransactions(String id);

    public static BankAdapter getBankAdapter(String bank) {
        if ("RABO".equals(bank)) {
            return new RaboAdapter();
        } else {
            return new INGAdapter();
        }
    }
}
