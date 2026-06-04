package com.river.training.entity;

public final class ApplicationStatus {

    public static final String NEW = "Новая";
    public static final String IN_PROGRESS = "Идет обучение";
    public static final String COMPLETED = "Обучение завершено";

    private ApplicationStatus() {
    }

    public static boolean isValid(String status) {
        return NEW.equals(status) || IN_PROGRESS.equals(status) || COMPLETED.equals(status);
    }

    public static String nextStatus(String current) {
        if (NEW.equals(current)) {
            return IN_PROGRESS;
        }
        if (IN_PROGRESS.equals(current)) {
            return COMPLETED;
        }
        return current;
    }
}
