package com.swp.project.service.Impl;

import com.swp.project.dto.response.BlogDTO;
import com.swp.project.entity.Blog;
import com.swp.project.entity.BlogImage;
import com.swp.project.entity.Category;
import com.swp.project.entity.User;
import com.swp.project.mapper.BlogMapper;
import com.swp.project.repository.BlogImageRepository;
import com.swp.project.repository.BlogRepository;
import com.swp.project.repository.CategoryRepository;
import com.swp.project.service.IBlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService implements IBlogService {

    private final BlogRepository blogRepository;
    private final BlogImageRepository blogImageRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;
    private final BlogMapper blogMapper;

    @Override
    public BlogDTO createBlog(String title, String content, int categoryId, MultipartFile[] images) throws IOException {
        User user = userService.getAuthenticatedUser();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setCreatedAt(Date.valueOf(LocalDate.now()));
        blog.setUpdatedAt(Date.valueOf(LocalDate.now()));
        blog.setCategory(category);
        List<BlogImage> blogImages = new ArrayList<>();
        if(images != null) {
            for (MultipartFile image : images) {
                Map map = cloudinaryService.upload(image);
                String url = (String) map.get("secure_url");
                String publicId = (String) map.get("public_id");
                BlogImage blogImage = new BlogImage();
                blogImage.setUrl(url);
                blogImage.setPublicId(publicId);
                blogImage.setBlog(blog);
                blogImages.add(blogImage);
            }
        }
        blog.setBlogImages(blogImages);
        blogRepository.save(blog);
        for(BlogImage blogImage : blogImages){
            blogImageRepository.save(blogImage);
        }
        return blogMapper.toBlogDTO(blog);
    }

    @Override
    public Page<BlogDTO> getAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogs = blogRepository.findAll(pageable);
        return blogs.map((blogMapper::toBlogDTO));
    }

    @Override
    public void deleteBlog(int blogId) throws IOException {
        User currentUser = userService.getAuthenticatedUser();
        List<BlogImage> blogImages = blogImageRepository.findByBlogId(blogId);
        for(BlogImage blogImage : blogImages){
            log.info("Deleting image with public id {}", blogImage.getPublicId());
            cloudinaryService.delete(blogImage.getPublicId());
        }
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        if(blog.getUser().getId() != currentUser.getId()){
            log.warn("User {} is not allowed to delete blog {}", currentUser.getId(), blogId);
            throw new RuntimeException("You are not allowed to delete this blog");
        }
        blogRepository.delete(blog);
    }

    @Override
    public BlogDTO getBlogById(int blogId) {



        Blog blog = blogRepository.findById(blogId)


                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return blogMapper.toBlogDTO(blog);


    }


}
