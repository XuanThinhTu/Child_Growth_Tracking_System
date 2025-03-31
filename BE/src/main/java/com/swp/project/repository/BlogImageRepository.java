package com.swp.project.repository;

import com.swp.project.entity.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogImageRepository extends JpaRepository<BlogImage, Integer> {
    List<BlogImage> findByBlogId(int blogId);
    void deleteByPublicId(String publicId);
}
