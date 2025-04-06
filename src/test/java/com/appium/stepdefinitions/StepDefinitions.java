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
        pageFactory.getLandingPage().clickPreference();
    }

    @When("I navigate to the wifi settings")
    public void i_navigate_to_the_wifi_settings() {
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
}
