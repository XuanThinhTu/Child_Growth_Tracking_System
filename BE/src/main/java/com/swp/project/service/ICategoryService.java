package com.swp.project.service;

import com.swp.project.dto.request.CategoryCreationRequest;
import com.swp.project.dto.response.CategoryDTO;
import java.util.List;

public interface ICategoryService {
    CategoryDTO createCategory(CategoryCreationRequest request);
    CategoryDTO updateCategory(int id, CategoryCreationRequest updateRequest);
    List<CategoryDTO> getAllCategory();
    void deleteCategory(int id);
}
