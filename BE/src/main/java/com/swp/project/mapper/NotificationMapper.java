package com.swp.project.mapper;

import com.swp.project.dto.response.NotificationDTO;
import com.swp.project.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .type(notification.getType().name())
                .build();
    }
}
