package com.example.taskmanage.controller;

import com.example.taskmanage.dto.NotificationDto;
import com.example.taskmanage.dto.UserContextDto;
import com.example.taskmanage.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user")
    public ResponseEntity<List<NotificationDto>> getNotificationOfUser() {

        return ResponseEntity.ok(notificationService.getNotificationOfUser(getUserContext().getUserId()));
    }

    @PatchMapping("/{id}")
    public void patchNotification(@PathVariable long id,
                                  @RequestBody NotificationDto dto) {

        notificationService.patchNotification(id, dto);
    }

    @GetMapping("/reindex")
    public ResponseEntity<String> reindexAllNotification(){

        notificationService.reindexAllNotification();

        return ResponseEntity.ok("Success");
    }

    private UserContextDto getUserContext() {

        return (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
