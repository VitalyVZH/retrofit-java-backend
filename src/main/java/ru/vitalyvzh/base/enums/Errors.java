package ru.vitalyvzh.base.enums;

import lombok.Getter;

public enum Errors {
    CODE400("Id must be null for new entity"),
    CODE401("airplane");

    @Getter
    private final String message;

    Errors(String message) {
        this.message = message;
    }
}
