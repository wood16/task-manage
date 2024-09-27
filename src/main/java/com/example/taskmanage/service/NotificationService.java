package com.example.taskmanage.service;

import com.example.taskmanage.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    void createNotification(Long creatorId,
                            Long receiverId,
                            Long objectId,
                            String type,
                            String content);

    List<NotificationResponse> getNotificationOfUser(long userId);

    void patchNotification(Long id, NotificationResponse dto);

    void reindexAllNotification();
}
