package API.DataSource;

import API.DataSource.util.DatabaseProperties;
import API.Errors.Error;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class BTWDao {
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(DatabaseProperties.class.getName());

    public BTWDao() {
        properties = new Properties();
        setup();
    }

    private void setup() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("btw.percentages.properties"));
        } catch (IOException e) {
            LOGGER.severe(Error.DATABASEERROR + e);
        }
    }

    public String[] getBTWPercentages(){
        String[] propertiesKeys = {"btw.percentage.21", "btw.percentage.9", "btw.percentage.0"};
        String[] btwPercentageLijst = new String[properties.size()];

        for (int i = 0; i < properties.size(); i++) {
            btwPercentageLijst[i] = properties.getProperty(propertiesKeys[i]);
        }

        return btwPercentageLijst;
    }
}
