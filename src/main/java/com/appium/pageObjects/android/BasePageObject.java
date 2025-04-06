package com.appium.pageObjects.android;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class BasePageObject {

    protected AndroidDriver driver;
	private WebDriverWait wait;

    public BasePageObject(AndroidDriver driver) {
        this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
	
	protected void click(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}
	
	protected void type(WebElement element, String text) {
		wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
	}
	
	protected String getText(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element)).getText();
	}

}
