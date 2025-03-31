package com.swp.project.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultationResponseDTO {

    int id;
    String content;
    LocalDateTime createdAt;
    UserDTO user;
    int consultationRequestId;
}
