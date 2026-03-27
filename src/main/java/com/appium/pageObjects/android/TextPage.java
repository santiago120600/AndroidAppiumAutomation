package com.appium.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class TextPage extends BasePageObject {

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='KeyEventText']")
    private WebElement KeyEventText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Linkify']")
    private WebElement Linkify;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='LogTextBox']")
    private WebElement LogTextBox;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Marquee']")
    private WebElement Marquee;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Unicode']")
    private WebElement Unicode;

    public TextPage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickKeyEventText() {
        click(KeyEventText);
    }

    public void clickLinkify() {
        click(Linkify);
    }

    public void clickLogTextBox() {
        click(LogTextBox);
    }

    public void clickMarquee() {
        click(Marquee);
    }

    public void clickUnicode() {
        click(Unicode);
    }

}
