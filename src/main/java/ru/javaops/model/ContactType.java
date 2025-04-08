package ru.javaops.model;

public enum ContactType {

    MOBILE_PHONE("Мобильный телефон"),
    TEAMS("Teams"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACK_OVERFLOW("Профиль StackOverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
