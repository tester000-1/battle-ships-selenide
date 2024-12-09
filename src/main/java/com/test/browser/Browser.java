package com.test.browser;

import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class Browser {

    public void openPage(String url){
        log.info("Navigate to URL: {}", url);
        open(url);
    }

}
