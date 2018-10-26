package util;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigPropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigPropertiesUtil.class);

    private static ResourceBundle bundle = null;


    static {
        bundle = ResourceBundle.getBundle("config", Locale.CHINESE);
    }

    public static long getLong(String key) {
        return getLong(key, -1L);
    }

    public static long getLong(String key, long defaultValue) {
        try {
            return Long.parseLong(getValue(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Boolean.valueOf(value);
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(getValue(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getValue(String key) {
        return getValue(key, null);
    }

    public static String getValue(String key, String defaultValue) {
        return getValue(key, defaultValue, Charsets.UTF_8);
    }

    public static String getValue(String key, String defaultValue, Charset convertCharset) {
        return getValue(key, defaultValue, Charsets.ISO_8859_1, convertCharset);
    }

    public static String getValue(String key, String defaultValue, Charset originCharset, Charset convertCharset) {
        String value = null;
        try {
            value = bundle.getString(key);
            if (StringUtils.isNotBlank(value)) {
                value = new String(value.getBytes(originCharset), convertCharset);
            }
        } catch (Exception e) {
            logger.debug("获取" + key + "时出现问题", e);
        }
        value = value == null ? defaultValue : value;
        return value;
    }


}
