package com.example.taskmanage.controller;

import com.example.taskmanage.dto.UserContextDto;
import com.example.taskmanage.entity.NotificationEntity;
import com.example.taskmanage.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user")
    public ResponseEntity<List<NotificationEntity>> getNotificationOfUser() {

        return ResponseEntity.ok(notificationService.getNotificationOfUser(getUserContext().getUserId()));
    }

    private UserContextDto getUserContext(){

        return (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
