package com.swp.project.service.Impl;

import com.swp.project.entity.Notification;
import com.swp.project.entity.User;
import com.swp.project.enums.NotificationType;
import com.swp.project.mapper.NotificationMapper;
import com.swp.project.repository.NotificationRepository;
import com.swp.project.repository.UserRepository;
import com.swp.project.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final NotificationMapper notificationMapper;

    @Override
    public void sendNotification(int userId, String title, String message, NotificationType type) {
        User user = userRepository.findById(userId).get();
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setType(type);
        notification.setUser(user);
        notification.setCreatedAt(new Date(LocalDateTime.now().getNano()));
        notificationRepository.save(notification);
    }
}
