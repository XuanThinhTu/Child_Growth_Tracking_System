package com.swp.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogDTO {

    int id;
    String title;
    String content;
    Date createdAt;
    Date updatedAt;
    int authorId;
    String authorName;
    String authorImage;
    int categoryId;
    String categoryName;
    List<BlogImageDTO> blogImages;
}
