package com.swp.project.service.Impl;

import com.swp.project.dto.request.CategoryCreationRequest;
import com.swp.project.dto.response.CategoryDTO;
import com.swp.project.entity.Category;
import com.swp.project.mapper.CategoryMapper;
import com.swp.project.repository.CategoryRepository;
import com.swp.project.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryCreationRequest request) {
        Category category = new Category();
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return categoryMapper.toCategoryDTO(category);
    }



    @Override
    public CategoryDTO updateCategory(int id, CategoryCreationRequest updateRequest) {


        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)

                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setTitle(updateRequest.getTitle());

        category.setDescription(updateRequest.getDescription());


        categoryRepository.save(category);


        return categoryMapper.toCategoryDTO(category);


    }












}
