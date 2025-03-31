package com.swp.project.mapper;

import com.swp.project.dto.response.CategoryDTO;
import com.swp.project.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toCategoryDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .isDeleted(category.isDeleted())
                .build();
    }
}
