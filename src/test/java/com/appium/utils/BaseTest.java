package com.appium.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseTest {

    // Declare SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected AndroidDriver driver;
    public String userName;
    public String accessKey;

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
        userName = System.getenv("BROWSERSTACK_USERNAME");
        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (userName == null || userName.isEmpty() || accessKey == null || accessKey.isEmpty()) {
            throw new IllegalStateException("BrowserStack credentials not provided. Set BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY");
        }
        logger.info("Connecting to BrowserStack...");
        UiAutomator2Options options = new UiAutomator2Options();
        return new AndroidDriver(new URI(String.format("https://%s:%s@hub.browserstack.com/wd/hub", userName , accessKey)).toURL(), options);
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("AndroidDriver quit successfully.");
        }
    }

}
