package API.DataSource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class SqlLoader {
    private Properties properties;
    private static Logger LOGGER = Logger.getLogger(SqlLoader.class.getName());


    public SqlLoader(String property) {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(property + ".properties"));
        } catch (IOException e) {
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
