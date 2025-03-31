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
        pb.redirectErrorStream(true); // Combine stdout and stderr
        pb.redirectOutput(ProcessBuilder.Redirect.DISCARD); // Prevent blocking by discarding output
        try {
            pb.start();
            logger.info("Emulator started successfully for device: {}", prop.getProperty("device.name"));
            // Give some initial time for the emulator to initialize
            Thread.sleep(5000);
        } catch (IOException e) {
            logger.error("Failed to start emulator", e);
            throw new RuntimeException("Failed to start emulator", e);
        } catch (InterruptedException e) {
            logger.error("Interrupted while starting emulator", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while starting emulator", e);
        }

        int maxWaitTimeSeconds = 120; // Maximum wait time (adjust as needed)
        int elapsedTimeSeconds = 0;
        int pollingIntervalSeconds = 5;

        while (!isEmulatorReady() && elapsedTimeSeconds < maxWaitTimeSeconds) {
            try {
                logger.debug("Waiting for emulator to be ready...");
                Thread.sleep(pollingIntervalSeconds * 1000);
                elapsedTimeSeconds += pollingIntervalSeconds;
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for emulator", e);
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for emulator", e);
            }
        }

        if (elapsedTimeSeconds >= maxWaitTimeSeconds) {
            logger.error("Emulator failed to start within {} seconds", maxWaitTimeSeconds);
            throw new RuntimeException("Emulator failed to start in time");
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
        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                Process process = processBuilder.start();
                process.waitFor(5, TimeUnit.SECONDS); // Reduced timeout per attempt

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String output = reader.readLine();
                    if (output != null && output.trim().equals("1")) {
                        logger.debug("Emulator boot completed.");
                        return true;
                    }
                }
                attempt++;
                logger.debug("Emulator not ready yet, attempt {}/{}", attempt, maxAttempts);
                Thread.sleep(2000); // Wait before retrying
            } catch (IOException | InterruptedException e) {
                logger.warn("Failed to check emulator status on attempt {}/{}", attempt + 1, maxAttempts, e);
                attempt++;
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(2000); // Wait before retrying
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted while waiting to retry emulator status check", ie);
                    }
                }
            }
        }
        logger.warn("Emulator not ready after {} attempts", maxAttempts);
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
