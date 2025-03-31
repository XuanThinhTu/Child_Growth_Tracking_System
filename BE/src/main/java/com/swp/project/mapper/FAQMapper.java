package com.swp.project.mapper;

import com.swp.project.dto.response.FAQResponse;
import com.swp.project.entity.FAQ;
import org.springframework.stereotype.Component;

@Component
public class FAQMapper {
    public FAQResponse toDto(FAQ faq) {
        if (faq == null) {
            return null;
        }

        return FAQResponse.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .category(
                        FAQResponse.Category.builder()
                                .id(faq.getCategory().getId())
                                .title(faq.getCategory().getTitle())
                                .description(faq.getCategory().getDescription())
                        .build()
                )
                .isDeleted(faq.isDeleted())
                .build();
    }
}
