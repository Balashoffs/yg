package org.example.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YandexGameType {
    private  String gameTypeTitle;
    private    String href;
}
