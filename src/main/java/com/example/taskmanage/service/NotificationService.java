package com.example.taskmanage.service;

public interface NotificationService {

    void createNotification(Long creatorId,
                            Long receiverId,
                            String type,
                            String content);
}
