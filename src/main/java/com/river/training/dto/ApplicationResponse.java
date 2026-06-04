package com.river.training.dto;

import com.river.training.entity.Application;
import java.time.format.DateTimeFormatter;

public record ApplicationResponse(
        Long id,
        String courseName,
        String startDate,
        String paymentMethod,
        String status,
        String createdAt,
        String userFullName,
        String userEmail,
        boolean hasReview
) {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATETIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static ApplicationResponse from(Application app, boolean hasReview) {
        return new ApplicationResponse(
                app.getId(),
                app.getCourse().getName(),
                app.getStartDate().format(DATE),
                app.getPaymentMethod(),
                app.getStatus(),
                app.getCreatedAt().format(DATETIME),
                app.getUser() != null ? app.getUser().getFullName() : null,
                app.getUser() != null ? app.getUser().getEmail() : null,
                hasReview
        );
    }

    public static ApplicationResponse forUser(Application app, boolean hasReview) {
        return new ApplicationResponse(
                app.getId(),
                app.getCourse().getName(),
                app.getStartDate().format(DATE),
                app.getPaymentMethod(),
                app.getStatus(),
                app.getCreatedAt().format(DATETIME),
                null,
                null,
                hasReview
        );
    }
}
