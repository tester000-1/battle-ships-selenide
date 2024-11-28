package com.test.strategy;

import com.test.page.GamePage;

public final class StrategyImpl implements Strategy {

    private static Strategy instance;

    private StrategyImpl(){

    }

    public static synchronized Strategy getInstance(){
        if(instance == null){
            instance = new StrategyImpl();
        }
        return instance;
    }

    @Override
    public boolean gameResult(GameStatus status) {
        return status == GameStatus.GAME_OVER_WIN;
    }

    @Override
    public GameStatus updateGameStatus(GamePage page) {
        return page.detectGameStatus();
    }
}
