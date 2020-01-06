package API.Services;

import API.DataSource.BTWDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BTWServiceTest {

    private BTWService btwServiceUnderTest;
    private BTWDao btwDao;

    @BeforeEach
    void setUp() {
        btwServiceUnderTest = new BTWService();
        btwDao = mock(BTWDao.class);
        btwServiceUnderTest.setBtwDao(btwDao);
    }

    @Test
    void testGetBTWPercentages() {
        // Setup
        final String[] expectedResult = new String[]{};

        // Run the test
        when(btwDao.getBTWPercentages()).thenReturn(expectedResult);
        final String[] result = btwServiceUnderTest.getBTWPercentages();

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }
}
