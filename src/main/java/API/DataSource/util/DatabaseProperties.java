package API.DataSource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        setup();
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private void setup() {
        Logger log = Logger.getLogger(getClass().getName());
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
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
