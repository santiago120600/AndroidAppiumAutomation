package com.appium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {

    // Declare SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    private AppiumDriverLocalService service;
    protected AndroidDriver driver;
    private Properties prop;

    @BeforeClass
    public void setUp() throws IOException, URISyntaxException {
        prop = new Properties();
        try (FileInputStream path = new FileInputStream("src/test/resources/global.properties")) {
            prop.load(path);
            logger.info("Loaded global.properties file successfully");
        } catch (IOException e) {
            logger.error("Failed to load global.properties", e);
            throw e;
        }

        if (!checkIfAndroidEmulatorIsRunnning()) {
            logger.info("Emulator is not running. Starting emulator...");
            startEmulator();
        } else {
            logger.info("Emulator is already running.");
        }

        if (!checkIfAppiumServerIsRunnning(Integer.parseInt(prop.getProperty("appium.port")))) {
            logger.info("Appium server is not running. Starting Appium server...");
            startAppiumServer();
        } else {
            logger.info("Appium server is already running.");
            service = createAppiumServiceBuilder().build();
        }

        logger.info("Initializing AndroidDriver...");
        initializeDriver();
    }

    private void startEmulator() {
        ProcessBuilder pb = new ProcessBuilder(prop.getProperty("emulator.location"), "-avd",
                prop.getProperty("device.name"));
        try {
            pb.start();
            logger.info("Emulator started successfully for device: {}", prop.getProperty("device.name"));
        } catch (IOException e) {
            logger.error("Failed to start emulator", e);
            throw new RuntimeException("Failed to start emulator", e);
        }

        while (!isEmulatorReady()) {
            try {
                logger.debug("Waiting for emulator to be ready...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for emulator", e);
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for emulator to be ready", e);
            }
        }
        logger.info("Emulator is ready.");
    }

    private boolean checkIfAndroidEmulatorIsRunnning() {
        ProcessBuilder pb = new ProcessBuilder("adb", "devices");
        boolean isEmulatorRunning = false;
        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("emulator") && line.contains("device")) {
                    String deviceId = line.split("\\s+")[0];

                    ProcessBuilder avdPb = new ProcessBuilder("adb", "-s", deviceId, "emu", "avd", "name");
                    Process avdProcess = avdPb.start();
                    BufferedReader avdReader = new BufferedReader(new InputStreamReader(avdProcess.getInputStream()));
                    String avdName = avdReader.readLine();

                    if (prop.getProperty("device.name").equals(avdName)) {
                        isEmulatorRunning = true;
                        logger.info("Found running emulator with AVD name: {}", avdName);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error checking if emulator is running", e);
        }
        return isEmulatorRunning;
    }

    private boolean isEmulatorReady() {
        ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "getprop", "sys.boot_completed");
        try {
            Process process = processBuilder.start();
            process.waitFor(10, TimeUnit.SECONDS);

            try (Scanner scanner = new Scanner(process.getInputStream())) {
                if (scanner.hasNext()) {
                    String output = scanner.next().trim();
                    logger.debug("Emulator boot status: {}", output);
                    return output.equals("1");
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to check emulator status", e);
            throw new RuntimeException("Failed to check emulator status", e);
        }
        return false;
    }

    private void startAppiumServer() {
        service = createAppiumServiceBuilder().build();
        service.start();
        logger.info("Appium server started on {}:{}", prop.getProperty("appium.server"), prop.getProperty("appium.port"));
    }

    private AppiumServiceBuilder createAppiumServiceBuilder() {
        return new AppiumServiceBuilder()
                .withAppiumJS(new File(prop.getProperty("appium.location")))
                .withIPAddress(prop.getProperty("appium.server"))
                .usingPort(Integer.parseInt(prop.getProperty("appium.port")));
    }

    private static boolean checkIfAppiumServerIsRunnning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
            logger.debug("Port {} is available.", port);
        } catch (IOException e) {
            isServerRunning = true;
            logger.debug("Port {} is in use, assuming Appium server is running.", port);
        }
        return isServerRunning;
    }

    private void initializeDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("device.name"));
        Path path = Paths.get(prop.getProperty("app.location"));
        options.setApp(path.toAbsolutePath().toString());
        driver = new AndroidDriver(new URI("http://" + prop.getProperty("appium.server") + ":" + prop.getProperty("appium.port")).toURL(), options);
        logger.info("AndroidDriver initialized successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("AndroidDriver quit successfully.");
        }
        if (service != null) {
            service.stop();
            logger.info("Appium server stopped.");
        }
        stopEmulator();
    }

    private void stopEmulator() {
        ProcessBuilder closeEmulator = new ProcessBuilder("adb", "emu", "kill");
        try {
            closeEmulator.start();
            logger.info("Emulator stopped successfully.");
        } catch (IOException e) {
            logger.error("Failed to stop emulator", e);
            throw new RuntimeException("Failed to stop emulator", e);
        }
    }
}