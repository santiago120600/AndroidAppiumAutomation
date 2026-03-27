package com.appium.utils;

import com.appium.pageObjects.android.LandingPage;
import com.appium.pageObjects.android.PreferenceDependenciesPage;
import com.appium.pageObjects.android.PreferencePage;
import com.appium.pageObjects.android.TextPage;
import com.appium.pageObjects.android.MarqueePage;

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
    @Getter(lazy = true)
    private final TextPage textPage = new TextPage(driver);
    @Getter(lazy = true)
    private final MarqueePage marqueePage = new MarqueePage(driver);
    
    public PageFactory(AndroidDriver driver) {
        this.driver = driver;
    }

}
