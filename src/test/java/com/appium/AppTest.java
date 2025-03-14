package com.appium;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class AppTest {
    @Test
    public void AppiumTest() throws MalformedURLException{
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel_3");
        Path path = Paths.get("src/test/resources/ApiDemos-debug.apk");
        options.setApp(path.toAbsolutePath().toString());
        AndroidDriver driver = new AndroidDriver(new URL("http://0.0.0.0:4723"), options);
        driver.quit();
    }
   
}
