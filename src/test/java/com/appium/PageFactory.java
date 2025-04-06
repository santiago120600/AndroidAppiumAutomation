package com.appium;

import com.appium.pageObjects.PreferenceDependenciesPage;
import com.appium.pageObjects.android.LandingPage;
import com.appium.pageObjects.android.PreferencePage;

import io.appium.java_client.android.AndroidDriver;

public class PageFactory {

    private AndroidDriver driver;
    private LandingPage landingPage;
    private PreferencePage preferencePage;
    private PreferenceDependenciesPage preferenceDependenciesPage;
    
    public PageFactory(AndroidDriver driver) {
        this.driver = driver;
    }

    public LandingPage getLandingPage() {
        if (landingPage == null) {
            landingPage = new LandingPage(driver);
        }
        return landingPage;
    }

    public PreferencePage getPreferencePage() {
        if (preferencePage == null) {
            preferencePage = new PreferencePage(driver);
        }
        return preferencePage;
    }

    public PreferenceDependenciesPage getPreferenceDependenciesPage() {
        if (preferenceDependenciesPage == null) {
            preferenceDependenciesPage = new PreferenceDependenciesPage(driver);
        }
        return preferenceDependenciesPage;
    }

}
