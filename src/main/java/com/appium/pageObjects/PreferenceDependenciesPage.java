package com.appium.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.appium.pageObjects.android.BasePageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PreferenceDependenciesPage extends BasePageObject{

    @AndroidFindBy(id="android:id/checkbox")
    private WebElement wifiCheckBox;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='WiFi settings']")
    private WebElement wifiSettingsTextView;

    @AndroidFindBy(id = "android:id/edit")
    private WebElement wifiSettingBox;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement wifiSettingOkButton;

    public PreferenceDependenciesPage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickWifiCheckBox() {
        click(wifiCheckBox);
    }

    public void clickWifiSettingsTextView() {
        click(wifiSettingsTextView);
    }

    public void typeWifiSettingBox(String text) {
        type(wifiSettingBox, text);
    }

    public void clickWifiSettingOkButton() {
        click(wifiSettingOkButton);
    }

}
