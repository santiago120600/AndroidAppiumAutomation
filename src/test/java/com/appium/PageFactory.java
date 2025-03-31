package com.appium;

import com.appium.pageObjects.android.LandingPage;
import com.appium.pageObjects.android.PreferencePage;

import io.appium.java_client.android.AndroidDriver;

public class PageFactory {

    private AndroidDriver driver;
    private LandingPage landingPage;
    private PreferencePage preferencePage;

    public PageFactory(AndroidDriver driver) {
        this.driver = driver;
    }

    public LandingPage getLandingPage() {
        return new LandingPage(driver);
    }

    public PreferencePage getPreferencePage() {
        return new PreferencePage(driver);
    }

}
