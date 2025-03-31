package com.appium.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LandingPage extends BasePageObject {

    @AndroidFindBy(accessibility = "Preference")
    private WebElement preference;

    @AndroidFindBy(accessibility = "Accessibility")
    private WebElement accessibility;

    @AndroidFindBy(accessibility = "Animation")
    private WebElement animation;

    @AndroidFindBy(accessibility = "App")
    private WebElement App;

    @AndroidFindBy(accessibility = "Content")
    private WebElement Content;

    @AndroidFindBy(accessibility = "Graphics")
    private WebElement Graphics;

    @AndroidFindBy(accessibility = "Media")
    private WebElement Media;

    @AndroidFindBy(accessibility = "NFC")
    private WebElement NFC;

    @AndroidFindBy(accessibility = "OS")
    private WebElement OS;

    @AndroidFindBy(accessibility = "Text")
    private WebElement Text;

    @AndroidFindBy(accessibility = "Views")
    private WebElement Views;

    public LandingPage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickPreference() {
        click(preference);
    }

    public void clickAccessibility() {
        click(accessibility);
    }

    public void clickAnimation() {
        click(animation);
    }

    public void clickApp() {
        click(App);
    }

    public void clickContent() {
        click(Content);
    }

    public void clickGraphics() {
        click(Graphics);
    }

    public void clickMedia() {
        click(Media);
    }

    public void clickNFC() {
        click(NFC);
    }

    public void clickOS() {
        click(OS);
    }

    public void clickText() {
        click(Text);
    }

    public void clickViews() {
        click(Views);
    }
}
