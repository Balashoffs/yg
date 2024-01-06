package org.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class YandexGameAdittionalData {
    private String gameAvatar92x92;
    private String gameTitle;
    private String publisher;
    private String playersQnt;
    private String rate;
    private String ageRate;
    private String playPageData;
    private String appData;
}
