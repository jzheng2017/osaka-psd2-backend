package API;

import API.Utils.HashedPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestHashedPassword {

    @Test
    void testVerify() {
        // Arrange
        var password = "password";
        var hash = HashedPassword.generate(password);

        // Act
        var passwordDoesMatch = HashedPassword.verify(hash, password);

        // Assert
        Assertions.assertTrue(passwordDoesMatch);
    }
}
