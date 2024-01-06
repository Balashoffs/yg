package org.example;

import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractReceiver<T,R> {
    public AbstractReceiver(WebDriver driver) {
        this.driver = driver;
    }

    final  protected WebDriver driver;

    protected abstract void receive(T input, Consumer<R> output);
}
