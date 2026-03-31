package com.appium.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseTest {

    // Declare SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected AndroidDriver driver;
    private Properties prop;

    protected String getProfile() {
        String profile = System.getProperty("profile");
        logger.info("Profile: {}", profile);
        return profile;
    }

    public AndroidDriver setUp() throws IOException, URISyntaxException {
        logger.info("Initializing AndroidDriver...");
        driver = initializeDriver();
        logger.info("AndroidDriver initialized successfully.");
        return driver;
    }

    private AndroidDriver initializeDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(getProfile());
        options.setApp(System.getProperty("app.path"));
        String serverUrl = "http://" + prop.getProperty("appium.server") + ":" + prop.getProperty("appium.port");
        logger.info("Connecting to Appium server at: {}", serverUrl);
        return new AndroidDriver(new URI(serverUrl).toURL(), options);
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("AndroidDriver quit successfully.");
        }
    }

}
