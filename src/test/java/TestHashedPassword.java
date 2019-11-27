import API.HashedPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class TestHashedPassword {

    private HashedPassword sut;

    @BeforeEach
    void setup(){
//        sut = new HashedPassword();

    }

    @Test
    void testVerify() throws InvalidKeySpecException, NoSuchAlgorithmException {
        //Arrange
        String  inputPassword = "password";
        String generatedSecuredPasswordHash = sut.generate(inputPassword);

        //Act
        boolean passwordDoesMatch = sut.verify("password", generatedSecuredPasswordHash);

        //Assert
        Assertions.assertEquals(true, passwordDoesMatch);
    }
}
