package API.DataSource.util;

import java.io.IOException;
import java.util.Properties;

public class SqlLoader {
    private Properties properties;


    public SqlLoader(String property) {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(property + ".properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public SqlLoader() {

    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    public String get(String sql) {
        return this.properties.getProperty(sql);
    }
}
