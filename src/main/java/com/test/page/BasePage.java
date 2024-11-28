package com.test.page;


import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BasePage {

    protected SelenideElement locator;

    public boolean isDisplayed(){
        return locator.isDisplayed();
    }

    public boolean isVisible(){
        return locator.isDisplayed();
    }

    public void doClick(){
        locator.click();
    }
}
