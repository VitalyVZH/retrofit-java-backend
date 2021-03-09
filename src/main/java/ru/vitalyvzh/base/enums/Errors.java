package ru.vitalyvzh.base.enums;

import lombok.Getter;

public enum Errors {
    CODE400NULL("Id must be null for new entity"),
    CODE400NOEXIST("doesn't exist"),
    CODE404PROD("Unable to find product with id: "),
    CODE404CAT("Unable to find category with id: "),
    CODE405("Method Not Allowed"),
    CODE500("Internal Server Error");

    @Getter
    private final String message;

    Errors(String message) {
        this.message = message;
    }
}
