package com.river.training.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ApplicationRequest(
        @NotNull(message = "Выберите вид транспорта")
        Long courseId,

        @NotBlank(message = "Укажите дату начала")
        @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$", message = "Дата в формате ДД.ММ.ГГГГ")
        String startDate,

        @NotBlank(message = "Выберите способ оплаты")
        String paymentMethod
) {
}
