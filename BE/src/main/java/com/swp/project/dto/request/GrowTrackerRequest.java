package com.swp.project.dto.request;

import jakarta.validation.constraints.NotNull;

public record GrowTrackerRequest(
        @NotNull Double height,
        @NotNull Double weight,
        @NotNull Double headCircumference,
        String measuredAt
) {
}
