package com.river.training.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Укажите логин (e-mail)")
        String email,

        @NotBlank(message = "Укажите пароль")
        String password
) {
}
