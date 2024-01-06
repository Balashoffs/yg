package org.example.types;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.fix.AppPosition;
import org.example.fix.AppPositionSaver;
import org.example.games.GameInfoCollector;
import org.example.model.YandexGame;
import org.example.model.YandexGameType;
import org.example.utils.FileUtils;
import org.example.utils.JsonUtils;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class YandexGamesCollector {

    public void collect(WebDriver driver) {



        int pos = 0;
        AppPositionSaver appPositionSaver = new AppPositionSaver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        final List<YandexGameType> types = new ArrayList<>();
        Path path = Path.of("games", "types.json");
        if (Files.exists(path)) {
            String json = FileUtils.readFile("games", "types.json");
            List<YandexGameType> found = JsonUtils.fromJson(json, ArrayList.class, YandexGameType.class);
            types.addAll(found);

            AppPosition currentPosition = appPositionSaver.getCurrentGameType();
            pos = currentPosition.getPos();
        } else {

            YandexGameTypeReceiver typeReceiver = new YandexGameTypeReceiver(driver);
            typeReceiver.receive(new Object(), yandexGameTypes -> {
                String json = JsonUtils.toJson(yandexGameTypes, ArrayList.class, YandexGameType.class);
                FileUtils.saveToFile(json, "games", "types.json");
                types.addAll(yandexGameTypes);
            });
        }

        AtomicInteger appGamesPos = new AtomicInteger(0);
        driver.get("https://yandex.ru/games/");

        for (int i = pos; i < types.size(); i++) {
            YandexGameType gameType = types.get(i);
            driver.navigate().to(gameType.getHref());
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            YandexGameHrefReceiver gameHrefReceiver = new YandexGameHrefReceiver(driver);
            Path saveFolderPath = Path.of("games", "types", gameType.getGameTypeTitle());
            if (!Files.exists(saveFolderPath)) {
                try {
                    Files.createDirectories(saveFolderPath);
                } catch (IOException e) {
                }
            }
            System.out.println("game type: " + gameType.getGameTypeTitle());
            gameHrefReceiver.receive(gameType, yandexGames -> {
                Gson gson = new GsonBuilder().serializeNulls().create();
                String json = gson.toJson(yandexGames);
                int gamePos = appGamesPos.getAndAdd(1);
                Path newJsonFiles = Path.of(saveFolderPath.toString(), String.format("%d.json", gamePos));
                try {
                    Files.writeString(newJsonFiles, json);
                } catch (IOException e) {

                }
            });
            appGamesPos.set(0);
            int nextPos = i + 1;
            if (nextPos < types.size()) {
                YandexGameType next = types.get(nextPos);
                appPositionSaver.setNextGameType(next.getGameTypeTitle(), nextPos);
            } else {
                appPositionSaver.setNextGameType("", -1);
            }

        }

    }
}
