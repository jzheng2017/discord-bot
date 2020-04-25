package app.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        setup();
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private void setup() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Properties get() {
        return this.properties;
    }
}
