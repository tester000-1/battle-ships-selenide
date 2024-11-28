package com.test.page;

import com.codeborne.selenide.SelenideElement;
import com.test.components.CheckboxImpl;
import com.test.components.Component;
import com.test.strategy.GameStatus;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class GamePage {

    private Component createComponent(SelenideElement locator){
        return new Component(locator);
    }

    private CheckboxImpl createCheckboxComponent(SelenideElement locator){
        return new CheckboxImpl(locator);
    }

    public Component getLanguageDropdown(){
        return createComponent($(By.xpath("//ul[@class='langs']")));
    }

    public Component getLanguageSelect(String language){
        return createComponent($(By.xpath("//a[@title='" + language + "']")));
    }

    public CheckboxImpl getCompactChatCheckbox(){
        return createCheckboxComponent($("#setting__compactchat"));
    }

    public SelenideElement getPageHeader(){
        return $(By.xpath("//h1[@class='logo']"));
    }

    public GameStatus detectGameStatus(){
        if($(byText("Place the ships.")).isDisplayed()){
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

}
