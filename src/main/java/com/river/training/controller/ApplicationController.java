package com.river.training.controller;

import com.river.training.dto.ApiResponse;
import com.river.training.dto.ApplicationRequest;
import com.river.training.dto.ReviewRequest;
import com.river.training.service.ApplicationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listMine() {
        List<?> apps = applicationService.getMyApplications();
        return ResponseEntity.ok(ApiResponse.ok("Заявки пользователя", apps));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ApplicationRequest request) {
        var created = applicationService.createApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Заявка создана", created));
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<ApiResponse> review(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request) {
        var updated = applicationService.addReview(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Отзыв сохранён", updated));
    }
}
