package nl.han.ica.oose.osaka.datasource.util;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        setup();
    }

    public void setProperties(Properties properties){
        this.properties = properties;
    }

    private void setup(){
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Properties get(){
        return this.properties;
    }

    public String getDriver(){
        return this.properties.getProperty("driver");
    }

    public String getConnectionString(){
        return this.properties.getProperty("connectionString");
    }
}
