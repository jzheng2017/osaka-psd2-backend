package Services;

import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import API.Services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestUserService {

    private UserService sut;
    private UserDAO mockedUserDao;
    private BankTokenDao mockedBankTokenDao;
    private User mockedUserDTO;
    private BankToken mockedBankToken;

    @BeforeEach
    void setup() {
    sut = new UserService();
    mockedUserDao = Mockito.mock(UserDAO.class);
    mockedBankTokenDao = Mockito.mock(BankTokenDao.class);
    mockedUserDTO = Mockito.mock(User.class);
    mockedBankToken = Mockito.mock(BankToken.class);

    sut.setUserDAO(mockedUserDao);
    sut.setBankTokenDao(mockedBankTokenDao);

    }

    @Test
    public void testGetUserByToken(){
        String token = "";
        sut.getUserByToken(token);

        verify(mockedUserDao).getUserByToken(token);
        Assertions.assertEquals(mockedUserDao.getUserByToken(token), sut.getUserByToken(token));
//        Assertions.assertNotNull(mockedUserDao.getUserByToken(token));
    }

    @Test
    public void testRegister(){

    }

    @Test
    public void testGetAttachedAccounts(){
        //Arrange
        String token = "";
        sut.getAttachedAccounts(token);

        //Act

        //Assert
        verify(mockedUserDao).getUserByToken(token);
        Assertions.assertEquals(mockedUserDao.getAttachedAccounts(mockedUserDTO), sut.getAttachedAccounts(token) );
        Assertions.assertNotNull(mockedUserDao.getAttachedAccounts(mockedUserDTO));
    }
}
