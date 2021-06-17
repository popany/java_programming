package org.example.hello.world.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.example.hello.world.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    private static final Properties properties = new Properties();

    private PropertyUtils() {
        throw new IllegalStateException("PropertyUtils class");
    }

    static {
        String[] propertyFiles = new String[]{Constants.COMMON_PROPERTIES_PATH};
        for (String fileName : propertyFiles) {
            InputStream fis = null;
            try {
                fis = PropertyUtils.class.getResourceAsStream(fileName);
                properties.load(fis);

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                if (fis != null) {
                    IOUtils.closeQuietly(fis);
                }
                System.exit(1);
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }
    }
    
    public static String getString(String key) {
        return properties.getProperty(key.trim());
    }

    public static String getString(String key, String defaultVal) {
        String val = properties.getProperty(key.trim());
        return val == null ? defaultVal : val;
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(),e);
        }
        return defaultValue;
    }

    public static boolean getBoolean(String key) {
        String value = properties.getProperty(key.trim());
        if(null != value){
            return Boolean.parseBoolean(value);
        }

        return false;
    }

    public static Boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key.trim());
        if(null != value){
            return Boolean.parseBoolean(value);
        }

        return defaultValue;
    }

    public static long getLong(String key, long defaultVal) {
        String val = getString(key);
        return val == null ? defaultVal : Long.parseLong(val);
    }

    public static long getLong(String key) {
        return getLong(key,-1);
    }

    public double getDouble(String key, double defaultVal) {
        String val = getString(key);
        return val == null ? defaultVal : Double.parseDouble(val);
    }

    public static String[] getArray(String key, String splitStr) {
        String value = getString(key);
        if (value == null) {
            return new String[0];
        }
        try {
            String[] propertyArray = value.split(splitStr);
            return propertyArray;
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(),e);
        }
        return new String[0];
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> type,
                                         T defaultValue) {
        String val = getString(key);
        return val == null ? defaultValue : Enum.valueOf(type, val);
    }

    public static Map<String, String> getPrefixedProperties(String prefix) {
        Map<String, String> matchedProperties = new HashMap<>();
        for (String propName : properties.stringPropertyNames()) {
            if (propName.startsWith(prefix)) {
                matchedProperties.put(propName, properties.getProperty(propName));
            }
        }
        return matchedProperties;
    }
}
