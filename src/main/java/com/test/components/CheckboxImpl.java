package com.test.components;

import com.codeborne.selenide.SelenideElement;
import com.test.page.BasePage;

public class CheckboxImpl extends BasePage implements Checkbox {

    public CheckboxImpl(SelenideElement locator){
        super(locator);
    }

    @Override
    public void checkElement() {
        locator.setSelected(true);
    }

    @Override
    public boolean isCheckedElement() {
        return locator.isSelected();
    }
}
