package com.river.training.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewRequest(
        @NotBlank(message = "Введите текст отзыва")
        @Size(min = 10, max = 2000, message = "Отзыв от 10 до 2000 символов")
        String text
) {
}
