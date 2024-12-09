package com.test.components;

import lombok.Getter;

@Getter
public enum User {
    LEFT_USER(1),
    RIGHT_USER(2);

    final private int value;

    User(int value){
        this.value = value;
    }

}
