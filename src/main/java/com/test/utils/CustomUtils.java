package com.test.utils;

import com.codeborne.selenide.Selenide;

import java.util.Random;

public class CustomUtils {

    private final static int MIN_RANDOM_NUMBER = 1;
    private final static int MAX_RANDOM_NUMBER = 15;

    public void sleep(Long time){
        Selenide.sleep(time);
    }

    public int generateRandomNumber() {
        Random rn = new Random();
        return rn.nextInt(MAX_RANDOM_NUMBER - MIN_RANDOM_NUMBER + 1) + MIN_RANDOM_NUMBER;
    }

    

}
