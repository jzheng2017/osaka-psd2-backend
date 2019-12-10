package DataSource.util;

import API.DataSource.util.SqlLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
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
    void constructorThrowsIOException() {
        sut = new SqlLoader("idontexist");
        assertNull(sut.get("idontexisteither"));
    }

    @Test
    void constructorRunsSuccesfully() {
        sut = new SqlLoader("user");
        String sql = "SELECT * FROM users WHERE token = ?";
        assertEquals(sql,sut.get("select.user.by.login.token"));
    }


    @Test
    void callToGetSqlCallsGetPropertyFunctionAndReturnsString(){
        when(mockedProperties.getProperty(anyString())).thenReturn(anyString());

        Assertions.assertNotNull(sut.get("test"));

        verify(mockedProperties).getProperty(anyString());
    }
}
