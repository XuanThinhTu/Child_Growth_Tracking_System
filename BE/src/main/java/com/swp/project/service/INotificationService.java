package com.swp.project.service;

import com.swp.project.enums.NotificationType;

public interface INotificationService {

    void sendNotification(int userId, String title, String message, NotificationType type);
}
