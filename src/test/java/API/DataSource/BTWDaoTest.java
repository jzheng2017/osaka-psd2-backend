package API.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BTWDaoTest {

    private BTWDao btwDaoUnderTest;

    @BeforeEach
    void setUp() {
        btwDaoUnderTest = new BTWDao();
    }

    @Test
    void testGetBTWPercentages() {
        // Setup
        final String[] expectedResult = new String[]{"21","9","0"};

        // Run the test
        final String[] result = btwDaoUnderTest.getBTWPercentages();

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }
}
