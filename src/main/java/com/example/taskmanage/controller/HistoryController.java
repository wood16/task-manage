package com.example.taskmanage.controller;

import com.example.taskmanage.dto.HistoryDto;
import com.example.taskmanage.service.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<HistoryDto>> getHistoryObject(
            @RequestParam(required = true) String type,
            @RequestParam(required = true) long objectId
    ) {

        return ResponseEntity.ok(historyService.findByTypeAndObjectId(type, objectId));
    }

    @GetMapping("/reindex")
    public ResponseEntity<String> reindexAllHistory() {

        historyService.reindexAllHistory();

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/date")
    public ResponseEntity<List<HistoryDto>> getHistoryDate(
            @RequestParam(required = true) String date
    ) {

        return ResponseEntity.ok(historyService.findByDate(date));
    }
}
