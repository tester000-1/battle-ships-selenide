package com.test.components;


import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public abstract class BaseComponent {

    protected SelenideElement locator;

    public boolean isDisplayed(){
        log.debug("Call isDisplayed on {}", locator);
        return locator.isDisplayed();
    }

    public void click(){
        log.debug("Perform Click on {}", locator);
        locator.click();
    }

    public SelenideElement getLocator(){
        log.debug("Get locator {}", locator);
        return locator;
    }

}
