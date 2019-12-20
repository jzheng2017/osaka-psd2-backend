package API.Services;

import API.DataSource.BTWDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BtwServiceTest {
    @InjectMocks
    private BTWService sut;
    @Mock
    private BTWDao mockedBtwDao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBtwPercentagesReturnsStringArray(){
        final String[] array = {};
        when(mockedBtwDao.getBTWPercentages()).thenReturn(array);

        Assertions.assertEquals(array, sut.getBTWPercentages());
    }

    @Test
    void getBtwPercentagesCallsBtwDaoGetPercentages(){
        sut.getBTWPercentages();
        verify(mockedBtwDao).getBTWPercentages();
    }
}
