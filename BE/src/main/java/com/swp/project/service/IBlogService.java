package com.swp.project.service;


import com.swp.project.dto.response.BlogDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IBlogService {

    BlogDTO createBlog(String title, String content, int categoryId, MultipartFile[] images) throws IOException;




}
