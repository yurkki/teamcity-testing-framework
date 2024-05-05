package api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static Config config;
    private final Properties properties;
    private final String resourceName = "application.properties";

    private Config() {
        properties = new Properties();
        appendFromResource(properties);
    }

    private static Config getInstance() {
        if (config == null) {
            return new Config();
        }
        return config;
    }

    private Properties appendFromResource(final Properties objProperties) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.printf("Resource \"%1$s\" could not be found%n", resourceName);
        }
        return objProperties;
    }

    public static String getProperty(final String key) {
        return getInstance().properties.getProperty(key);
    }
}