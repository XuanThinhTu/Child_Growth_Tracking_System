package com.swp.project.mapper;

import com.swp.project.dto.response.BlogDTO;
import com.swp.project.entity.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogMapper {

    private final BlogImageMapper blogImageMapper;

    public BlogDTO toBlogDTO(Blog blog){
        return BlogDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .authorId(blog.getUser().getId())
                .authorName(blog.getUser().getFirstName() + " " + blog.getUser().getLastName())
                .authorImage(blog.getUser().getAvatar())
                .categoryId(blog.getCategory().getId())
                .categoryName(blog.getCategory().getTitle())
                .blogImages(blogImageMapper.toBlogImageDTOs(blog.getBlogImages()))
                .build();
    }
}
