package com.test.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.test.components.BoardMap;
import com.test.components.CheckboxImpl;
import com.test.components.CustomComponent;
import com.test.strategy.GameStatus;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
@Setter
public class GamePageObject extends BasePage {

    private long opponentWaitDelay = 4000L;

    private CustomComponent createComponent(SelenideElement locator) {
        return new CustomComponent(locator);
    }

    private BoardMap createBoardMap(SelenideElement locator) {
        return new BoardMap(locator);
    }

    private CheckboxImpl createCheckboxComponent(SelenideElement locator) {
        return new CheckboxImpl(locator);
    }

    public CustomComponent getLanguageDropdown() {
        return createComponent($(By.xpath("//ul[@class='langs']")));
    }

    public CustomComponent getLanguageSelect(String language) {
        return createComponent($(By.xpath("//a[@title='" + language + "']")));
    }

    public CheckboxImpl getCompactChatCheckbox() {
        return createCheckboxComponent($("#setting__compactchat"));
    }

    public CustomComponent getStartButton() {
        return createComponent($(By.xpath("//div[text()='Play']")));
    }

    public BoardMap getLeftBoardMap() {
        return createBoardMap($(By.xpath("(//table[@class='battlefield-table'])[1]")));
    }

    public BoardMap getRightBoardMap() {
        return createBoardMap($(By.xpath("(//table[@class='battlefield-table'])[2]")));
    }

    public int getShipStatus(int user) {
        int result = 10;
        ElementsCollection array = $$(new By.ByXPath("//div[@class='ship-types']//span[@class='ship']"));
        SelenideElement elem = null;
        int minValue = user == 1 ? 1:10;
        int maxValue = user == 1 ? 10:20;
        //log.error("Array count {}", array.size());
        for (int index = minValue; index <= maxValue; index++ ) {
            elem = array.get(index -1);
            String[] classes = elem.getAttribute("class").split(" ");
            if(classes != null){
                for(int j = 0; j < classes.length; j++){
                    if(classes[j].equals("ship__killed")){
                        result--;
                        log.info(" result-- {}", result);
                    }
                }
            }
            elem = null;
        }
        return result;
    }

    public String[] getCellClasses(SelenideElement cell){
        String[] classes = cell.getAttribute("class").split(" ");
        for(int i = 0; i < classes.length; i++) {
            log.debug("Show table cell classes: {}, Number {}", classes[i], (i+1));
        }
        return classes;
    }


    public GameStatus detectGameStatus() {
        if ($(byText("Place the ships.")).isDisplayed()) {
            return GameStatus.GAME_INIT;
        } else if ($(byText("Connecting to server.")).isDisplayed()) {
            return GameStatus.CONNECTING_SERVER;
        } else if ($(byText("Waiting for opponent.")).isDisplayed()) {
            return GameStatus.WAITING_FOR_OPPONENT;
        } else if ($(byText("The game started, your turn.")).isDisplayed()) {
            return GameStatus.STARTED_YOUR_TURN;
        } else if ($(byText("The game began, opponent's turn.")).isDisplayed()) {
            return GameStatus.STARTED_OPPONENT_TURN;
        } else if ($(byText("Your turn.")).isDisplayed()) {
            return GameStatus.YOUR_TURN;
        } else if ($(byText("Opponent's turn, please wait.")).isDisplayed()) {
            return GameStatus.OPPONENT_TURN;
        } else if ($(byText("Your opponent has left the game.")).isDisplayed()) {
            return GameStatus.OPPONENT_LEFT;
        } else if ($(byText("Game over. Congratulations, you won!")).isDisplayed()) {
            return GameStatus.GAME_OVER_WIN;
        } else if ($(byText("Game over. You lose.")).isDisplayed()) {
            return GameStatus.GAME_OVER_LOSE;
        } else if ($(byText("Server is unavailable.")).isDisplayed()) {
            return GameStatus.SERVER_ERROR;
        } else if ($(byText("Unexpected error. Further play is impossible.")).isDisplayed()) {
            return GameStatus.GAME_ERROR;
        } else if ($(byText("If you like this game, please write a review in the Chrome Web Store (it takes less than one minute).")).isDisplayed()) {
            return GameStatus.NOTIFICATION_REVIEW;
        } else {
            final String errorMessage = "Cannot find the current GameStatus!";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    public GameStatus waitUntilOpponentFinish() throws InterruptedException {
        boolean isWaiting = true;
        boolean isFirstWait = true;
        GameStatus status = GameStatus.GAME_INIT;
        while (isWaiting) {
            if (!isFirstWait) {
                Thread.sleep(opponentWaitDelay);
            }
            isFirstWait = false;
            status = detectGameStatus();
            if (status == GameStatus.GAME_INIT ||
                    status == GameStatus.CONNECTING_SERVER ||
                    status == GameStatus.WAITING_FOR_OPPONENT ||
                    status == GameStatus.STARTED_OPPONENT_TURN ||
                    status == GameStatus.OPPONENT_TURN) {
                log.debug("Waiting opponent to finish!");
            } else {
                isWaiting = false;
            }
        }
        log.debug("Game status: {}", status);
        return status;
    }

    public boolean isThereAnError(GameStatus status) {
        if (status == GameStatus.SERVER_ERROR ||
                status == GameStatus.GAME_ERROR ||
                status == GameStatus.NOTIFICATION_REVIEW ||
                status == GameStatus.OPPONENT_LEFT) {
            log.error("Error: {}", status);
            return true;
        }
        return false;
    }

}
