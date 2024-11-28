package com.test.strategy;

import com.test.page.GamePage;

public interface Strategy {

    boolean gameResult(GameStatus status);

    GameStatus updateGameStatus(GamePage page);


}
