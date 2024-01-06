package org.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class YandexGameData {
    private String href;
    private String type;
    private String title;
    private String token;
    private YandexGameAdittionalData data;
}

/*
https://app-279232.games.s3.yandex.net/
279232
/n9zh3vsobal03pi4y0am7am0c2k1cm5d/
index.html
?sdk=%2Fsdk%2F_%2Fv2.e56c428bfac25913fb13.js
#origin=https%3A%2F%2Fyandex.ru
&app-id=279232
&device-type=desktop
 */