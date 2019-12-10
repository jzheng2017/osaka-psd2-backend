package API.DataSource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlLoader {
    private Properties properties;


    public SqlLoader(String property) {
        Logger LOGGER = Logger.getLogger(getClass().getName());
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(property + ".properties"));
        } catch (IOException | NullPointerException e) {
            LOGGER.severe(e.toString());
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
