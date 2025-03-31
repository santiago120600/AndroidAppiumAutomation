package com.appium.pageObjects.android;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

public class BasePageObject {

    protected AndroidDriver driver;

    public BasePageObject(AndroidDriver driver) {
        this.driver = driver;
    }
	
	protected void click(WebElement element) {
		element.click();
	}
	
	protected void type(WebElement element, String text) {
		element.sendKeys(text);
	}
	
	protected String getText(WebElement element) {
		return element.getText();
	}

}
