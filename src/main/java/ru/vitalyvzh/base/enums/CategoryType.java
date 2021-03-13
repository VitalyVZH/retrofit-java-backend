package ru.vitalyvzh.base.enums;

import lombok.Getter;

public enum CategoryType {
    FOOD(1, "Food"),
    ELECTRONICS(2, "Electronics"),
    AUTOANDINDUSTRIAL(38, "Auto & industrial");

    @Getter
    private final int id;
    @Getter
    private final String title;

    CategoryType(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
