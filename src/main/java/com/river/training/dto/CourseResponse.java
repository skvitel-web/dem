package com.river.training.dto;

import com.river.training.entity.Course;

public record CourseResponse(Long id, String name, String description) {

    public static CourseResponse from(Course course) {
        return new CourseResponse(course.getId(), course.getName(), course.getDescription());
    }
}
