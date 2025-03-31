package com.swp.project.dto.response;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationDTO {

    private int id;
    private String title;
    private String message;
    private Date createdAt;
    private boolean isRead;
    private String type;
}
