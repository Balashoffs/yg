package org.example.types;

import org.checkerframework.checker.units.qual.A;
import org.example.AbstractReceiver;
import org.example.model.YandexGame;
import org.example.model.YandexGameType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YandexGameHrefReceiver extends AbstractReceiver<YandexGameType, List<YandexGame>> {
    final String regex = "https\\:\\/\\/yandex\\.ru\\/games\\/app\\/(\\d+)";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    public YandexGameHrefReceiver(WebDriver webDriver) {
        super(webDriver);
    }


    @Override
    protected void receive(YandexGameType input, Consumer<List<YandexGame>> output) {
        Set<Integer> uniqs = new HashSet<>();
        WebElement adaptiveWidth1 = driver.findElement(By.xpath("//*[@id=\"feeds\"]"));
        int lastSize = 0;
        do {
            try {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {

                }

                List<YandexGame> foundGames = new ArrayList<>();
                var webElements = adaptiveWidth1.findElements(By.className("adaptive-width"));
                if (webElements.size()<= lastSize) {
                    break;
                }
                System.out.printf("last size: %s\n", lastSize);
                System.out.printf("webElements size: %s\n", webElements.size());

                List<WebElement> newWebElements = webElements.subList(lastSize, webElements.size());
                for (WebElement webElement : newWebElements) {
                    List<WebElement> gridLists = webElement.findElements(By.tagName("ul"));
                    if (!gridLists.isEmpty()) {
                        List<WebElement> gridListGames = gridLists.get(0).findElements(By.tagName("li"));
                        if (!gridListGames.isEmpty()) {
                            for (WebElement gridListGame : gridListGames) {
                                WebElement gameCard = gridListGame.findElement(By.tagName("a"));
                                String href = gameCard.getAttribute("href");
                                System.out.println(href);
                                Matcher matcher = pattern.matcher(href);
                                if (matcher.find()) {
                                    int id = Integer.parseInt(matcher.group(1));
                                    if (!uniqs.contains(id)) {
                                        WebElement gameCardInfo = gameCard.findElement(By.tagName("img"));
                                        String title = gameCardInfo.getAttribute("alt");
                                        String avatar = gameCardInfo.getAttribute("src");
                                        YandexGame yandexGame = YandexGame
                                                .builder()
                                                .id(id)
                                                .gameImageHref(avatar)
                                                .gameHref(href)
                                                .gameType(input.getGameTypeTitle())
                                                .gameTitle(title)
                                                .build();
                                        foundGames.add(yandexGame);
                                        uniqs.add(id);
                                    }

                                }

                            }

                        }
                    }

                }
                lastSize = webElements.size();
                System.out.printf("add games size: %s\n", uniqs.size());

                output.accept(foundGames);

                new Actions(driver)
                        .scrollToElement(webElements.get(webElements.size() - 1))
                        .perform();
            } catch (NumberFormatException e) {
                break;
            }


        } while (true);
    }
}
