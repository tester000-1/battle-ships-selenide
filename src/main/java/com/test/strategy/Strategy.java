package com.test.strategy;

import com.test.page.GamePageObject;

import java.util.Map;

public interface Strategy {

    boolean gameResult(GameStatus status);

    GameStatus updateGameStatus(GamePageObject page);

    Map<String, FieldCellStatus> getEnemyTable();

    void initEnemyTable();

    String getRandomCell();

    boolean checkIfCellIsShip(String[] classes);

    int[] calculateStrategicLocation();

    InitialAttack getInitialAttack();


}
