package org.example.types;

import org.example.AbstractReceiver;
import org.example.model.YandexGameType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class YandexGameTypeReceiver extends AbstractReceiver<Object, List<YandexGameType>> {

    public YandexGameTypeReceiver(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void receive(Object input, Consumer<List<YandexGameType>> output) {
        List<YandexGameType> types = new ArrayList<>();
        WebElement categoriesList = driver.findElement(By.xpath("//*[@id=\"mount\"]/div/div/div/main/section/div/div/div[1]/div[1]/ul"));
        List<WebElement> categories = categoriesList.findElements(By.tagName("li"));
        if (!categories.isEmpty()) {
            List<WebElement> subcategories = categories.subList(3, categories.size() - 1);
            for (WebElement subcategory : subcategories) {
                WebElement listItem = subcategory.findElement(By.tagName("a"));
                String type = listItem.getAttribute("title");
                String href = listItem.getAttribute("href");
                YandexGameType yandexGameType = YandexGameType.builder().gameTypeTitle(type).href(href).build();
                types.add(yandexGameType);
            }
        }
        output.accept(types);
    }

}
