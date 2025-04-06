package com.appium.utils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestContextSetup {

    public BaseTest base;
    public PageFactory pageFactory;

    public TestContextSetup(){
        base = new BaseTest();
    }

    public void initialize() throws IOException, URISyntaxException {
        if (pageFactory == null) {
            pageFactory = new PageFactory(base.setUp());
        }
    }

}
