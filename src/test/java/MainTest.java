import com.codeborne.selenide.Configuration;
import com.test.components.CheckboxImpl;
import com.test.components.Component;
import com.test.page.GamePage;
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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class MainTest {

    private final CustomUtils utils = new CustomUtils();
    private AppConfiguration configuration;
    private final GamePage page = new GamePage();
    private final Strategy strategy = StrategyImpl.getInstance();
    private GameStatus gameStatus;

    @BeforeClass
    public void setup(){
        log.info("Setup the test ---> ");
        configuration = new AppConfiguration();
        Configuration.browser = configuration.getBrowserName();
        Configuration.timeout = configuration.getTimeout();
    }

    @BeforeTest
    public void beforeTest() {
        log.info("Start test ---> ");
        gameStatus = GameStatus.GAME_INIT;
    }

    @AfterTest
    public void afterTest() {
        log.info("End test ---> ");
        String result = strategy.gameResult(gameStatus) ? "Win":"Lose";
        log.info("Game status ---> {}", result);
    }

    @Test(description = "Battle ships game execution")
    public void battleTest() {
//        Allure.step("text", () -> {
//
//        });
        Allure.step("Navigate to the battle ships website", () -> {
            open(configuration.getBaseUrl());
        });

        Allure.step("Change the language to English and check compact chat", () -> {
            Component langDropdown = page.getLanguageDropdown();
            langDropdown.getLocator().shouldBe(visible);
            langDropdown.doClick();
            Component language = page.getLanguageSelect("English");
            language.getLocator().shouldBe(visible);
            language.doClick();
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
            assert gameStatus == GameStatus.GAME_INIT;
            log.info("Current status: {}", gameStatus);
        });


        utils.sleep(4000L);

    }


}
