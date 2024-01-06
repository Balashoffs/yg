package org.example.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class YandexGame implements Comparable<YandexGame> {
    private String gameType;
    private String gameHref;
    private String gameTitle;
    private String gameImageHref;
    private int id;

    @Override
    public int compareTo(YandexGame other) {
        return this.gameHref.compareTo(other.gameHref) ^ this.gameTitle.compareTo(other.gameTitle);
    }
}
