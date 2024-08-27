package com.example.taskmanage.service;

import com.example.taskmanage.entity.NotificationEntity;

import java.util.List;

public interface NotificationService {

    void createNotification(Long creatorId,
                            Long receiverId,
                            String type,
                            String content);

    List<NotificationEntity> getNotificationOfUser(long userId);
}
