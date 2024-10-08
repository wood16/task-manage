package com.example.taskmanage.controller;

import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.dto.response.HistoryResponse;
import com.example.taskmanage.service.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryController {

    HistoryService historyService;

    @GetMapping("/object")
    public BaseResponse<List<HistoryResponse>> getHistoryObject(
            @RequestParam(required = true) String type,
            @RequestParam(required = true) long objectId
    ) {

        return BaseResponse.<List<HistoryResponse>>builder()
                .result(historyService.findByTypeAndObjectId(type, objectId))
                .build();
    }

    @GetMapping("/reindex")
    public BaseResponse<?> reindexAllHistory() {

        historyService.reindexAllHistory();

        return BaseResponse.builder().message("Success").build();
    }

    @GetMapping("/date")
    public BaseResponse<List<HistoryResponse>> getHistoryDate(
            @RequestParam(required = true) String date
    ) {

        return BaseResponse.<List<HistoryResponse>>builder()
                .result(historyService.findByDate(date))
                .build();
    }
}
