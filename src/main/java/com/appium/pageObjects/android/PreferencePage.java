package com.appium.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PreferencePage extends BasePageObject {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='1. Preferences from XML']")
    private WebElement preferencesFromXML;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='2. Launching preferences']")
    private WebElement launchingPreferences;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='3. Preference dependencies']")
    private WebElement preferenceDependencies;

    public PreferencePage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickPreferenceDependencies() {
        click(preferenceDependencies);
    }

    public void clickPreferencesFromXML() {
        click(preferencesFromXML);
    }

    public void clickLaunchingPreferences() {
        click(launchingPreferences);
    }

}
