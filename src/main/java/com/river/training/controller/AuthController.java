package com.river.training.controller;

import com.river.training.dto.ApiResponse;
import com.river.training.dto.LoginRequest;
import com.river.training.dto.RegisterRequest;
import com.river.training.dto.UserResponse;
import com.river.training.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Регистрация успешна", user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        UserResponse user = authService.login(request, httpRequest);
        return ResponseEntity.ok(ApiResponse.ok("Вход выполнен", user));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.ok("Выход выполнен"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> me() {
        return ResponseEntity.ok(ApiResponse.ok("Текущий пользователь", authService.currentUser()));
    }
}
