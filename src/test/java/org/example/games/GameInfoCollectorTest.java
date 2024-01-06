package org.example.games;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.YandexGame;
import org.example.model.YandexGameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameInfoCollectorTest {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    /*
    "gameType": "Аркады",
    "gameHref": "https://yandex.ru/games/app/260097",
    "gameTitle": "Телекинез Атака!",
    "gameImageHref": "https://avatars.mds.yandex.net/get-games/11374519/2a0000018ca6837906f686570ddef9f3efbc/pjpg350x209"
     */
    @Test
    void collectTest() {
        YandexGame game = YandexGame
                .builder()
                .gameTitle("Телекинез Атака!")
                .gameType("Аркады")
                .gameHref("https://yandex.ru/games/app/275515")
                .gameImageHref("https://avatars.mds.yandex.net/get-games/11374519/2a0000018ca6837906f686570ddef9f3efbc/pjpg350x209")
                .build();
        GameInfoCollector gameInfoCollector = new GameInfoCollector(driver);
        gameInfoCollector.collect(game, yandexGameData -> {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(yandexGameData, YandexGameData.class);
            String type = yandexGameData.getType();
            String title = yandexGameData.getTitle();
            try {
                Path saveFolder = Path.of("games", type);
                if (!Files.exists(saveFolder)) {
                    Files.createDirectories(saveFolder);
                }
                Path saveJsonPath = Path.of(saveFolder.toString(), title+".json");
                Files.writeString(saveJsonPath, json);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}