package SystemDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    // connect to config.properties to get the details
    static {
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(props.getProperty(key, String.valueOf(defaultValue)));
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}