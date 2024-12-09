package com.test.page;


import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;


public abstract class BasePage {

    public SelenideElement getPageHeader() {
        return $(By.xpath("//h1[@class='logo']"));
    }

}
