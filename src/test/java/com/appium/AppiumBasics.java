package com.appium;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class AppiumBasics extends BaseTest{

    @Test
    public void appiumTest() {
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
    }
}
