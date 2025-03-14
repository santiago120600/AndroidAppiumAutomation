package com.appium;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppTest {
    @Test
    public void AppiumTest() throws MalformedURLException, URISyntaxException {
        Properties prop = new Properties();
        try (
            FileInputStream path = new FileInputStream("src\\test\\resources\\global.properties")) {
            prop.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder pb = new ProcessBuilder(prop.getProperty("emulator"), "-avd", prop.getProperty("deviceName"));
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppiumDriverLocalService service = new AppiumServiceBuilder()
                .withAppiumJS(new File(prop.getProperty("appium")))
                .withIPAddress("0.0.0.0").usingPort(4723).build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("deviceName"));
        Path path = Paths.get("src/test/resources/ApiDemos-debug.apk");
        options.setApp(path.toAbsolutePath().toString());
        AndroidDriver driver = new AndroidDriver(new URI("http://0.0.0.0:4723").toURL(), options);
        driver.quit();
        service.stop();
    }

}
