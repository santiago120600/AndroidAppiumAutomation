package com.appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppTest {
    @Test
    public void AppiumTest() throws MalformedURLException, URISyntaxException{
        AppiumDriverLocalService  service = new AppiumServiceBuilder()
        .withAppiumJS(new File("C://Users//santi//scoop//apps//nvm//current//nodejs//nodejs//node_modules//appium//build//lib//main.js"))
        .withIPAddress("0.0.0.0").usingPort(4723).build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel_3");
        Path path = Paths.get("src/test/resources/ApiDemos-debug.apk");
        options.setApp(path.toAbsolutePath().toString());
        AndroidDriver driver = new AndroidDriver(new URI("http://0.0.0.0:4723").toURL(), options);
        driver.quit();
        service.stop();
    }
   
}
