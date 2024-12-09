import com.codeborne.selenide.Configuration;
import com.test.browser.Browser;
import com.test.components.BoardMap;
import com.test.components.CheckboxImpl;
import com.test.components.CustomComponent;
import com.test.components.User;
import com.test.page.GamePageObject;
import com.test.strategy.GameStatus;
import com.test.strategy.Strategy;
import com.test.strategy.StrategyImpl;
import com.test.utils.AppConfiguration;
import com.test.utils.CustomUtils;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class MainTest {

    private final CustomUtils utils = new CustomUtils();
    private AppConfiguration configuration;
    private final GamePageObject page = new GamePageObject();
    private final Strategy strategy = StrategyImpl.getInstance();
    private GameStatus gameStatus;
    private final Browser browser = new Browser();
    private static final int MAX_SHIPS_COUNT = 10;

    @BeforeClass
    public void setup() {
        log.info("Setup the test ---> ");
        configuration = new AppConfiguration();
        Configuration.browser = configuration.getBrowserName();
        Configuration.timeout = configuration.getTimeout();
        page.setOpponentWaitDelay(configuration.getOpponentWaitDelay());
        log.info("Start test ---> ");
    }

    @BeforeTest
    public void beforeTest() {
        log.info("Before test ---> ");
        gameStatus = GameStatus.GAME_INIT;
    }

    @AfterTest
    public void afterTest() {
        log.info("End test ---> ");
        String result = strategy.gameResult(gameStatus) ? "Win" : "Lose";
        log.info("Game status ---> {}", result);
    }

    @Test(description = "Battle ships game execution")
    public void battleTest() {
        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Navigate to the battle ships website", () -> {
            browser.openPage(configuration.getBaseUrl());
        });

        Allure.step("Change the language to English and check compact chat", () -> {
            CustomComponent langDropdown = page.getLanguageDropdown();
            langDropdown.getLocator().shouldBe(visible);
            langDropdown.click();
            CustomComponent language = page.getLanguageSelect("English");
            language.getLocator().shouldBe(visible);
            language.click();
            CheckboxImpl compactChatCheckbox = page.getCompactChatCheckbox();
            compactChatCheckbox.checkElement();
            compactChatCheckbox.getLocator().shouldBe(checked);
            page.getPageHeader().shouldHave(text("Battleship"));
        });

        Allure.step("Generate random ships", () -> {
            final int randomNumber = utils.generateRandomNumber();
            log.info("Random generated number: {}", randomNumber);
            for (int i = 0; i < randomNumber; i++) {
                log.info("Randomise ships {}", i + 1);
                $(byText("Randomise")).click();
            }
            gameStatus = strategy.updateGameStatus(page);
            softAssert.assertEquals(gameStatus, GameStatus.GAME_INIT,
                    "The game state should be GAME_INIT");
            log.info("Current status: {}", gameStatus);
        });

        Allure.step("Start a new game", () -> {
            CustomComponent startBtn = page.getStartButton();
            log.info("Start the game");
            softAssert.assertTrue(startBtn.isDisplayed(),
                    "Button 'Start' should be visible");
            startBtn.click();
            gameStatus = page.waitUntilOpponentFinish();
        });

        Allure.step("Try to defeat the opponent", () -> {
            //Define enemy BoardMap location
            BoardMap enemyMap = page.getRightBoardMap();
            log.info("Map table {}", enemyMap.getLocator().isDisplayed());

            //Declare reusable variables
            String newCell;
            String[] coordinates;
            String[] classAttributes;
            Boolean isTargetCellIsShipBool;
            boolean isGameInProgress = true;
            //Game loop
            while (isGameInProgress) {
                gameStatus = page.waitUntilOpponentFinish();
                isGameInProgress = !page.isThereAnError(gameStatus);
                //log.info("Left user Ships status: {} from {}", (page.getShipStatus(User.LEFT_USER.getValue())), MAX_SHIPS_COUNT);
                //log.info("Right user Ships status: {} from {}", (page.getShipStatus(User.RIGHT_USER.getValue())), MAX_SHIPS_COUNT);

                newCell = strategy.getRandomCell();

//                if(strategy.getInitialAttack() == InitialAttack.NONE){
//                    newCell = strategy.getRandomCell();
//                }else{
//                    //newCell = strategy.calculateStrategicLocation();
//                }


                coordinates = newCell.split("-");
                //Attack random UNDISCOVERED cell
                enemyMap.getCellByTableAndCoordinates(User.RIGHT_USER.getValue(), Integer.parseInt(coordinates[0]),
                        Integer.parseInt(coordinates[1]))
                        .click();
                classAttributes = page.getCellClasses(enemyMap.getCellByTableAndCoordinates(User.RIGHT_USER.getValue(), 2, 5));
                isTargetCellIsShipBool = strategy.checkIfCellIsShip(classAttributes);
                if (isTargetCellIsShipBool){

                }

                //Check if previous cell is hit
                //updateCellStatus

                //Enemy table represented by Map
                //log.error(strategy.getEnemyTable().toString());


                //Clean variables
                newCell = null;
                coordinates = null;
                classAttributes = null;
                isTargetCellIsShipBool = null;
            }
        });


        utils.sleep(7000L);
        //Assert all the soft assertions
        softAssert.assertEquals(GameStatus.GAME_OVER_WIN, gameStatus,
                "Check the result of the game. Expected result: WIN");
        //Screenshot
        softAssert.assertAll();
    }


}
