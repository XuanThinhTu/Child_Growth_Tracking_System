package com.swp.project.mapper;

import com.swp.project.dto.response.BlogImageDTO;
import com.swp.project.entity.BlogImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogImageMapper {

    public BlogImageDTO toBlogImageDTO(BlogImage blogImage) {
        return BlogImageDTO.builder()
                .id(blogImage.getId())
                .url(blogImage.getUrl())
                .publicId(blogImage.getPublicId())
                .build();
    }

    public List<BlogImageDTO> toBlogImageDTOs(List<BlogImage> blogImages) {
        return blogImages.stream().map(this::toBlogImageDTO).collect(Collectors.toList());
    }
}
