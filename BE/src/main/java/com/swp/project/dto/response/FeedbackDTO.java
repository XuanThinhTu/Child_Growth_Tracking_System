package com.swp.project.dto.response;

import com.swp.project.enums.FeedbackType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class FeedbackDTO {

    private int id;
    private double rating;
    private String comment;
    private FeedbackType feedbackType;
    private LocalDateTime createdAt;
    private UserDTO member;
    private UserDTO doctor;
}
