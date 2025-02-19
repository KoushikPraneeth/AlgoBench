package com.algobench.pro.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages application configuration settings.
 */
public class ConfigurationManager {
    private static final Logger logger = LogManager.getLogger(ConfigurationManager.class);
    private static final String CONFIG_FILE = "application.properties";
    private final Properties config;

    private static ConfigurationManager instance;

    private ConfigurationManager() {
        config = new Properties();
        loadConfiguration();
    }

    /**
     * Gets the singleton instance of ConfigurationManager.
     * @return The ConfigurationManager instance
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.error("Unable to find " + CONFIG_FILE);
                return;
            }
            config.load(input);
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading configuration: " + e.getMessage());
        }
    }

    /**
     * Gets a configuration property value.
     * @param key The property key
     * @param defaultValue The default value if the key is not found
     * @return The property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    /**
     * Gets an integer configuration property value.
     * @param key The property key
     * @param defaultValue The default value if the key is not found or invalid
     * @return The property value as integer or default value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = config.getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for key '{}': {}", key, value);
            return defaultValue;
        }
    }

    /**
     * Gets a boolean configuration property value.
     * @param key The property key
     * @param defaultValue The default value if the key is not found
     * @return The property value as boolean or default value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = config.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
}
