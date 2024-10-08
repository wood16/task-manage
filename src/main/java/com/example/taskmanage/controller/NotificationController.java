package com.example.taskmanage.controller;

import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.dto.response.NotificationResponse;
import com.example.taskmanage.dto.response.UserContextResponse;
import com.example.taskmanage.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @GetMapping("/user")
    public BaseResponse<List<NotificationResponse>> getNotificationOfUser() {

        return BaseResponse.<List<NotificationResponse>>builder()
                .result(notificationService.getNotificationOfUser(getUserContext().getUserId()))
                .build();
    }

    @PatchMapping("/{id}")
    public void patchNotification(@PathVariable long id,
                                  @RequestBody NotificationResponse dto) {

        notificationService.patchNotification(id, dto);
    }

    @GetMapping("/reindex")
    public BaseResponse<?> reindexAllNotification() {

        notificationService.reindexAllNotification();

        return BaseResponse.builder().message("Success").build();
    }

    private UserContextResponse getUserContext() {

        return (UserContextResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
