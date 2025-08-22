package org.oladushek.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {
    private static Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesConfig(){}

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try(InputStream input = PropertiesConfig.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(input);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
