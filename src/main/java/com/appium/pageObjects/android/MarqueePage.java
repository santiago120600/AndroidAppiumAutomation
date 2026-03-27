package com.appium.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MarqueePage extends BasePageObject {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='This use the default marquee animation limit of 3']")
    private WebElement preference;

    public MarqueePage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getPreferenceText() {
        return getText(preference);
    }
}
