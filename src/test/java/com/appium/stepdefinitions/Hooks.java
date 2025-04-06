package com.appium.stepdefinitions;

import com.appium.utils.TestContextSetup;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    TestContextSetup testContextSetup;

    public Hooks(TestContextSetup testContextSetup) {
        this.testContextSetup = testContextSetup;
    }

    @Before
    public void setUp() {
        try {
            testContextSetup.initialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize TestContextSetup in Hooks", e);
        }
    }

    @After
	public void tearDown() {
        if (testContextSetup != null && testContextSetup.base != null) {
            testContextSetup.base.tearDown();
        }
	}
}
