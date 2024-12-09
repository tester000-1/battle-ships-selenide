package com.test.strategy;

import com.codeborne.selenide.SelenideElement;
import com.test.page.GamePageObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Getter
@Setter
@Slf4j
public final class StrategyImpl implements Strategy {

    private static Strategy instance;
    private GameStatus gameStatus;
    private Map<String, FieldCellStatus> enemyTable = new HashMap<>();
    private InitialAttack initialAttack = InitialAttack.NONE;
    private SelenideElement lastSuccessAttackCell;

    private StrategyImpl(){
        initEnemyTable();
    }

    public static synchronized Strategy getInstance(){
        if(instance == null){
            instance = new StrategyImpl();
        }
        return instance;
    }

    @Override
    public Map<String, FieldCellStatus> getEnemyTable(){
        return enemyTable;
    }

    @Override
    public void initEnemyTable(){
        if(enemyTable == null){
            enemyTable = new HashMap<>();
        }
        for (int i = 1; i <= 10; i++){
            for (int j = 1; j <= 10; j++){
                enemyTable.put(i + "-" + j, FieldCellStatus.UNDISCOVERED);
            }
        }
    }

    @Override
    public String getRandomCell() {
        List<String> valuesList = new ArrayList<>();
        enemyTable.forEach((ind, value) -> {
            if (value == FieldCellStatus.UNDISCOVERED){
                valuesList.add(ind);
            }
        });

        Collections.shuffle(valuesList);
        String result = valuesList.get(new Random().nextInt(valuesList.size()));
        log.debug("Return random cell: {}", result);
        return result;
    }

    @Override
    public boolean gameResult(GameStatus status) {
        return status == GameStatus.GAME_OVER_WIN;
    }

    @Override
    public GameStatus updateGameStatus(GamePageObject page) {
        return page.detectGameStatus();
    }

    @Override
    public boolean checkIfCellIsShip(String[] classes){
        for (String aClass : classes) {
            if (aClass.equals("battlefield-cell__hit") || aClass.equals("battlefield-cell__done")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] calculateStrategicLocation(){
        //todo
        return null;
    }

    @Override
    public InitialAttack getInitialAttack(){
        return initialAttack;
    }


}
