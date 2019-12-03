package DataSource.util;

import API.DataSource.util.DatabaseProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DatabasePropertiesTest {
    private DatabaseProperties sut;
    private Properties mockedProperties;

    @BeforeEach
    void setup(){
        sut = new DatabaseProperties();

        mockedProperties = mock(Properties.class);

        sut.setProperties(mockedProperties);
    }


    @Test
    void getPropertiesReturnsProperties(){
        Assertions.assertNotNull(sut.get());
    }

    @Test
    void getDriverCallsGetPropertyFunctionAndReturnsString(){
        when(mockedProperties.getProperty(anyString())).thenReturn(anyString());
        Assertions.assertNotNull(sut.getDriver());
        verify(mockedProperties).getProperty(anyString());
    }

    @Test
    void getConnectionStringCallsGetPropertyFunctionAndReturnsString(){
        when(mockedProperties.getProperty(anyString())).thenReturn(anyString());
        Assertions.assertNotNull(sut.getConnectionString());
        verify(mockedProperties).getProperty(anyString());
    }
}
