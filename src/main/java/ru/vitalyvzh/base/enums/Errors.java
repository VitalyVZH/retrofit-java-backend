package ru.vitalyvzh.base.enums;

import lombok.Getter;

public enum Errors {
    CODE400NULL("Id must be null for new entity"),
    CODE400NOTNULL("Id must be not null for new entity"),
    CODE404PROD("Unable to find product with id: "),
    CODE404CAT("Unable to find category with id: "),
    CODE405("Method Not Allowed");

    @Getter
    private final String message;

    Errors(String message) {
        this.message = message;
    }
}
