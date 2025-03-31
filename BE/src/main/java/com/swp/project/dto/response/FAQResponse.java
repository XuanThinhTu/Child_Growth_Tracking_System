package com.swp.project.dto.response;

import lombok.Builder;

@Builder
public record FAQResponse(
        int id,
        String question,
        String answer,
        Category category,
        boolean isDeleted
) {
    @Builder
    public record Category(
            int id,
            String title,
            String description
    ) {

    }
}
