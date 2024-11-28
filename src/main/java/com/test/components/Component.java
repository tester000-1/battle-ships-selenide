package com.test.components;

import com.codeborne.selenide.SelenideElement;
import com.test.page.BasePage;

public class Component extends BasePage {

    public Component(SelenideElement locator){
        super(locator);
    }

}
