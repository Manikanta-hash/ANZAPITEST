package helpers;

import org.testng.Reporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtility {
    public PropertyUtility() {
    }

    public Properties loadProperty(String file) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream(file);
        Properties props = new Properties();

        try {
            props.load(in);
        } catch (IOException var6) {
            Reporter.log(String.format("Could not load properties %s", file));
        }

        return props;
    }
}
