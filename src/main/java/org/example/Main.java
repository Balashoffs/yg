package org.example;

import org.example.types.YandexGamesCollector;
import org.example.utils.FileUtils;
import org.example.utils.JsonUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        YandexGamesCollector yandexGamesCollector = new YandexGamesCollector();
        yandexGamesCollector.collect(driver);

        driver.quit();
    }


}