package com.test.components;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CheckboxImpl extends BaseComponent implements Checkbox {

    public CheckboxImpl(SelenideElement locator){
        super(locator);
    }

    @Override
    public void checkElement() {
        locator.setSelected(true);
    }

    @Override
    public boolean isCheckedElement() {
        log.debug("Call isSelected on {}", locator);
        return locator.isSelected();
    }
}
