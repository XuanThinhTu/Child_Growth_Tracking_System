package com.swp.project.dto.request;

public record FAQRequest(
        String question,
        String answer,
        int categoryId
) {

}
