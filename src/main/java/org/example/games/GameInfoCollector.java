package org.example.games;

import lombok.AllArgsConstructor;
import org.example.model.YandexGame;
import org.example.model.YandexGameAdittionalData;
import org.example.model.YandexGameData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.function.Consumer;

@AllArgsConstructor
public class GameInfoCollector {

    final WebDriver driver;

    void collect(YandexGame yandexGame , Consumer<YandexGameData> consumer) {
        driver.navigate().to(yandexGame.getGameHref());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        YandexGameData.YandexGameDataBuilder ygmBuilder = YandexGameData.builder();
        ygmBuilder.type(yandexGame.getGameType());
        ygmBuilder.title(yandexGame.getGameTitle());
        collectYandexGameData(ygmBuilder);
        try {
            YandexGameAdittionalData.YandexGameAdittionalDataBuilder ygadBuilder = YandexGameAdittionalData.builder();
            Thread.sleep(500);
            collectAvatar(ygadBuilder);
            Thread.sleep(500);
            collectTitle(ygadBuilder);
            Thread.sleep(500);
            collectAgeRate(ygadBuilder);
            Thread.sleep(500);
            collectRate(ygadBuilder);
            Thread.sleep(500);
            collectPlayersQnt(ygadBuilder);
            Thread.sleep(500);
            collectPublisher(ygadBuilder);
            Thread.sleep(500);
            collectAppData(ygadBuilder);
            collectPlayPageData(ygadBuilder);
            Thread.sleep(500);
            ygmBuilder.data(ygadBuilder.build());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        YandexGameData data = ygmBuilder.build();
        consumer.accept(data);
    }

    private void collectYandexGameData(YandexGameData.YandexGameDataBuilder builder) {
        try {
            WebElement iframe = driver.findElement(By.id("game-frame"));
            String hrefRaw = iframe.getAttribute("src");
            String href = hrefRaw.substring(0, hrefRaw.indexOf('?'));
            System.out.println("href: " + href);
            builder.href(href);

            builder.token()
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void collectAvatar(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement image = driver.findElement(By.cssSelector("#mount > div > div > div > div.stack > div.play-guard-dialog.play-guard-dialog_with-blur.play-guard-dialog_with-adv.play-guard-dialog_with-similar > div.play-guard-dialog__content > div.play-guard-dialog__description > div.image.image_state_loaded.play-guard-dialog__description-icon > img"));
        String avatarHref = image.getAttribute("src");
        System.out.println("avatarHref: " + avatarHref);
        builder.gameAvatar92x92(avatarHref);
    }

    private void collectTitle(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement image = driver.findElement(By.cssSelector("#mount > div > div > div > div.stack > div.play-guard-dialog.play-guard-dialog_with-blur.play-guard-dialog_with-adv.play-guard-dialog_with-similar > div.play-guard-dialog__content > div.play-guard-dialog__description > div.image.image_state_loaded.play-guard-dialog__description-icon > img"));
        String title = image.getAttribute("alt");
        System.out.println("title: " + title);
        builder.gameTitle(title);
    }

    private void collectAgeRate(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement ageRateWE = driver.findElement(By.cssSelector("#mount > div > div > div > div.stack > div.play-guard-dialog.play-guard-dialog_with-blur.play-guard-dialog_with-adv.play-guard-dialog_with-similar > div.play-guard-dialog__content > div.play-guard-dialog__description > div.play-guard-dialog__description-details > div.play-guard-dialog__description-details-header > div.play-guard-dialog__description-title-wrapper > div"));
        String ageRate = ageRateWE.getText();
        System.out.println("ageRate: " + ageRate);
        builder.ageRate(ageRate);
    }

    private void collectPublisher(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement publisherWE = driver.findElement(By.cssSelector("#mount > div > div > div > div.stack > div.play-guard-dialog.play-guard-dialog_with-blur.play-guard-dialog_with-adv.play-guard-dialog_with-similar > div.play-guard-dialog__content > div.play-guard-dialog__description > div.play-guard-dialog__description-details > div.play-guard-dialog__description-details-header > div.play-guard-dialog__description-publisher"));
        String publisher = publisherWE.getText();
        System.out.println("publisher: " + publisher);
        builder.publisher(publisher);
    }

    private void collectPlayersQnt(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement playerQntWE = driver.findElement(By.cssSelector("#mount > div > div > div > div.stack > div.play-guard-dialog.play-guard-dialog_with-blur.play-guard-dialog_with-adv.play-guard-dialog_with-similar > div.play-guard-dialog__content > div.play-guard-dialog__description > div.play-guard-dialog__description-details > div.play-guard-dialog__description-players > div.play-guard-dialog__description-playersCount-wrapper > div.play-guard-dialog__description-playersCount"));
        String playerQnt = playerQntWE.getText().replace("&nbsp;", " ");
        System.out.println("playerQnt: " + playerQnt);
        builder.playersQnt(playerQnt);
    }

    private void collectRate(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement rateWE = driver.findElement(By.cssSelector("#mount > div > div > div > div.stack > div.play-guard-dialog.play-guard-dialog_with-blur.play-guard-dialog_with-adv.play-guard-dialog_with-similar > div.play-guard-dialog__content > div.play-guard-dialog__description > div.play-guard-dialog__description-details > div.play-guard-dialog__description-players > div.play-guard-dialog__description-rating-wrapper > div:nth-child(1) > span.play-guard-dialog__description-rating"));
        String rate = rateWE.getText();
        System.out.println("rate: " + rate);
        builder.rate(rate);
    }

    private void collectPlayPageData(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement detailedWE = driver.findElement(By.cssSelector("#__playPageData__"));
        String detailed = detailedWE.getAttribute("innerHTML");
        builder.playPageData(detailed);
        System.out.println("detailed: " + detailed);
    }

    private void collectAppData(YandexGameAdittionalData.YandexGameAdittionalDataBuilder builder) {
        WebElement detailedWE = driver.findElement(By.cssSelector("#__appData__"));
        String detailed = detailedWE.getAttribute("innerHTML");
        builder.appData(detailed);
        System.out.println("detailed: " + detailed);
    }
}
