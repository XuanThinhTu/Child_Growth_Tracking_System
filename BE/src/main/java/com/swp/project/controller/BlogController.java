package com.swp.project.controller;

import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.BlogDTO;
import com.swp.project.service.IBlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final IBlogService blogService;
    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ApiResponse<BlogDTO> createBlog(@RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("categoryId") int categoryId,
                                           @RequestParam(name = "images", required = false) MultipartFile[] images) throws IOException {
        return ApiResponse.<BlogDTO>builder()
                .message("Blog created")
                .data(blogService.createBlog(title, content, categoryId, images))
                .build();
    }

    @GetMapping("/get/{blogId}")
    public ApiResponse<BlogDTO> getBlog(@PathVariable int blogId) {

        return ApiResponse.<BlogDTO>builder()

                .message("Blog")

                .data(blogService.getBlogById(blogId))

                .build();

    }

}
