package com.river.training.dto;

public record ApiResponse(String message, Object data) {

    public static ApiResponse ok(String message) {
        return new ApiResponse(message, null);
    }

    public static ApiResponse ok(String message, Object data) {
        return new ApiResponse(message, data);
    }
}
