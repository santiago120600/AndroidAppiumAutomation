package com.appium.utils;

import com.appium.pageObjects.PreferenceDependenciesPage;
import com.appium.pageObjects.android.LandingPage;
import com.appium.pageObjects.android.PreferencePage;

import io.appium.java_client.android.AndroidDriver;
import lombok.Getter;

public class PageFactory {
    
    private AndroidDriver driver;
    @Getter(lazy = true) 
    private final LandingPage landingPage = new LandingPage(driver);
    @Getter(lazy = true) 
    private final PreferencePage preferencePage = new PreferencePage(driver);
    @Getter(lazy = true) 
    private final PreferenceDependenciesPage preferenceDependenciesPage = new PreferenceDependenciesPage(driver);
    
    public PageFactory(AndroidDriver driver) {
        this.driver = driver;
    }

}
