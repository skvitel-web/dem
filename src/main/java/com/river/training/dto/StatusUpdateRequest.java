package com.river.training.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusUpdateRequest(
        @NotBlank(message = "Укажите статус")
        String status
) {
}
