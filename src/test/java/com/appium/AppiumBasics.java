package com.appium;

import org.testng.annotations.Test;

public class AppiumBasics extends BaseTest{

    @Test
    public void appiumTest() {
        PageFactory pageFactory = new PageFactory(driver);

        pageFactory.getLandingPage().clickPreference();
        pageFactory.getPreferencePage().clickPreferenceDependencies();
        pageFactory.getPreferenceDependenciesPage().clickWifiCheckBox();
        pageFactory.getPreferenceDependenciesPage().clickWifiSettingsTextView();
        pageFactory.getPreferenceDependenciesPage().typeWifiSettingBox("My Wifi");
        pageFactory.getPreferenceDependenciesPage().clickWifiSettingOkButton();
    }
}
