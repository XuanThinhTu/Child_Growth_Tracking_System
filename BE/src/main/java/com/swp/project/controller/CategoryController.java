package com.swp.project.controller;

import com.swp.project.dto.request.CategoryCreationRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.CategoryDTO;
import com.swp.project.service.ICategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<CategoryDTO> createCategory(@RequestBody CategoryCreationRequest request) {
        return ApiResponse.<CategoryDTO>builder()
                .message("Successfully created category")
                .data(categoryService.createCategory(request))
                .build();
    }


    @PutMapping("/update/{id}")

    @SecurityRequirement(name = "bearerAuth")

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ApiResponse<CategoryDTO> update(@PathVariable int id,@RequestBody CategoryCreationRequest updateRequest) {
        return ApiResponse.<CategoryDTO>builder()
                .message("Successfully update category")
                .data(categoryService.updateCategory(id, updateRequest))
                .build();
    }




}
