package com.example.taskmanage.service;

import com.example.taskmanage.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void createNotification(Long creatorId,
                            Long receiverId,
                            Long objectId,
                            String type,
                            String content);

    List<NotificationDto> getNotificationOfUser(long userId);

    void patchNotification(Long id, NotificationDto dto);

    void reindexAllNotification();
}
