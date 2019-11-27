package DataSource.util;

import API.DataSource.util.SqlLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SqlLoaderTest {
    private SqlLoader sut;

    private Properties mockedProperties;

    @BeforeEach
    void setup(){
        sut = new SqlLoader();

        mockedProperties = mock(Properties.class);

        sut.setProperties(mockedProperties);
    }


    @Test
    void callToGetSqlCallsGetPropertyFunctionAndReturnsString(){
        when(mockedProperties.getProperty(anyString())).thenReturn(anyString());

        Assertions.assertNotNull(sut.get("test"));

        verify(mockedProperties).getProperty(anyString());
    }
}
