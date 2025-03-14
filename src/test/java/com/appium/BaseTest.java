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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {

    private AppiumDriverLocalService service;
    protected AndroidDriver driver;
    private Properties prop;

    @BeforeClass
    public void setUp() throws IOException, URISyntaxException {
        prop = new Properties();
        try (FileInputStream path = new FileInputStream("src/test/resources/global.properties")) {
            prop.load(path);
        }

        startEmulator();
        startAppiumServer();
        initializeDriver();
    }

    private void startEmulator() {
        ProcessBuilder pb = new ProcessBuilder(prop.getProperty("emulator.location"), "-avd", prop.getProperty("device.name"));
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to start emulator", e);
        }

        while (!isEmulatorReady()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for emulator to be ready", e);
            }
        }
    }

    private boolean isEmulatorReady() {
        ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "getprop", "sys.boot_completed");
        try {
            Process process = processBuilder.start();
            process.waitFor(10, TimeUnit.SECONDS);

            try (Scanner scanner = new Scanner(process.getInputStream())) {
                if (scanner.hasNext()) {
                    String output = scanner.next().trim();
                    return output.equals("1");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to check emulator status", e);
        }
        return false;
    }

    private void startAppiumServer() {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(prop.getProperty("appium.location")))
                .withIPAddress(prop.getProperty("appium.server"))
                .usingPort(Integer.parseInt(prop.getProperty("appium.port")))
                .build();
        service.start();
    }

    private void initializeDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("device.name"));
        Path path = Paths.get(prop.getProperty("app.location"));
        options.setApp(path.toAbsolutePath().toString());
        driver = new AndroidDriver(new URI("http://"+prop.getProperty("appium.server")+":"+prop.getProperty("appium.port")).toURL(), options);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
        stopEmulator();
    }

    private void stopEmulator() {
        ProcessBuilder closeEmulator = new ProcessBuilder("adb", "emu", "kill");
        try {
            closeEmulator.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to stop emulator", e);
        }
    }
}