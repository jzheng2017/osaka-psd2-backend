import API.HashedPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class TestHashedPassword {

    @BeforeEach
    void setup(){

    }

    @Test
    void testVerify() {
        //Arrange
        String inputPassword = "password";
        String generatedSecuredPasswordHash = HashedPassword.generate(inputPassword);

        //Act
        boolean passwordDoesMatch = HashedPassword.verify(generatedSecuredPasswordHash, inputPassword);

        //Assert
        Assertions.assertEquals(true, passwordDoesMatch);
    }
}
