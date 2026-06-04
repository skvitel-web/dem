package com.river.training.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Укажите ФИО")
        @Size(max = 150, message = "ФИО не длиннее 150 символов")
        String fullName,

        @NotBlank(message = "Укажите e-mail")
        @Email(message = "Некорректный e-mail")
        String email,

        @NotBlank(message = "Укажите телефон")
        @Pattern(regexp = "^\\+?[0-9\\s\\-()]{10,20}$", message = "Некорректный телефон")
        String phone,

        @NotBlank(message = "Укажите дату рождения")
        @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$", message = "Дата в формате ДД.ММ.ГГГГ")
        String birthDate,

        @NotBlank(message = "Укажите пароль")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
                message = "Пароль: минимум 6 символов, только латиница и цифры")
        String password
) {
}
