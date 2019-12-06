package API.Services;

import API.DTO.AddToCategoryRequest;
import API.DTO.TransactionCategory;
import API.DataSource.TransactionDAO;
import API.DataSource.UserDAO;
import javax.inject.Inject;

public class TransactionService {
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;

    @Inject
    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public TransactionCategory createCategory(TransactionCategory category, String token) {
        var user = userDAO.getUserByToken(token);
        category.setUser(user);
        return transactionDAO.createCategory(category);
    }

    public void addToCategory(int categoryId, AddToCategoryRequest request, String token) {
        var user = userDAO.getUserByToken(token);
        var category = transactionDAO.getCategory(categoryId, user);
        transactionDAO.addTransactionToCategory(category, request.getContent());
    }
}
