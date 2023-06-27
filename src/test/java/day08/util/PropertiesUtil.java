package day08.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    public static String redProperties(String result, String file) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
            result = properties.getProperty(result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
