package com.appium.stepdefinitions;

import com.appium.utils.PageFactory;
import com.appium.utils.TestContextSetup;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions{

    private PageFactory pageFactory;

    public StepDefinitions(TestContextSetup testContextSetup) {
        this.pageFactory = testContextSetup.pageFactory;
    }

    @Given("I have logged into the app")
    public void i_have_logged_into_the_app() {
        System.out.println("Logged into the app successfully");
    }
    
    @When("I navigate to the wifi settings")
    public void i_navigate_to_the_wifi_settings() {
        pageFactory.getLandingPage().clickPreference();
        pageFactory.getPreferencePage().clickPreferenceDependencies();
    }

    @When("enable wifi")
    public void enable_wifi() {
        pageFactory.getPreferenceDependenciesPage().clickWifiCheckBox();
        pageFactory.getPreferenceDependenciesPage().clickWifiSettingsTextView();
    }

    @Then("I should be able to type the {string}")
    public void i_should_be_able_to_type_the(String wifi_name) {
        pageFactory.getPreferenceDependenciesPage().typeWifiSettingBox(wifi_name);
        pageFactory.getPreferenceDependenciesPage().clickWifiSettingOkButton();
    }

    @When("I navigate to the text page")
    public void i_navigate_to_the_text_page() {
        pageFactory.getLandingPage().clickText();
    }

    @When("click Marquee button")
    public void click_marquee_button() {
        pageFactory.getTextPage().clickMarquee();
    }

    @Then("I should be able to get the text from the Marquee page button")
    public void i_should_be_able_to_get_the_text_from_the_marquee_page_button() {
        System.out.println("Text from Marquee page button: " + pageFactory.getMarqueePage().getPreferenceText());
    }
}
