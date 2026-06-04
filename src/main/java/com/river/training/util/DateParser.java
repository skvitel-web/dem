package com.river.training.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateParser {

    private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private DateParser() {
    }

    public static LocalDate parseDdMmYyyy(String value) {
        try {
            return LocalDate.parse(value.trim(), DD_MM_YYYY);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Некорректная дата. Используйте формат ДД.ММ.ГГГГ");
        }
    }
}
