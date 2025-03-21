package com.swp.project.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GrowthTrackerResponse(
        int id,
        Double height,
        Double weight,
        Double headCircumference,
        Double bmi,
        LocalDateTime measuredAt,
        int childrenId
) {
}
