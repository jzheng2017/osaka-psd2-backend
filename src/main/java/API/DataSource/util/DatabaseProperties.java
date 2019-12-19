package API.DataSource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(DatabaseProperties.class.getName());

    public DatabaseProperties() {
        properties = new Properties();
        setup();
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private void setup() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            LOGGER.severe("PROPERTY DOESNT EXIST" + e);
        }
    }

    public Properties get() {
        return this.properties;
    }

    public String getDriver() {
        return this.properties.getProperty("driver");
    }

    public String getConnectionString() {
        return this.properties.getProperty("connectionString");
    }
}
