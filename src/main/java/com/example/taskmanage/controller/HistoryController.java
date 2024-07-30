package com.example.taskmanage.controller;

import com.example.taskmanage.dto.HistoryDto;
import com.example.taskmanage.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/histories")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/object")
    public ResponseEntity<List<HistoryDto>> getHistoryObject(
            @RequestParam(required = true) String type,
            @RequestParam(required = true) long objectId
    ) {

        return ResponseEntity.ok(historyService.findByTypeAndObjectId(type, objectId));
    }

    @GetMapping("/reindex")
    public ResponseEntity<String> reindexAllHistory(){

        historyService.reindexAllHistory();

        return ResponseEntity.ok("Success");
    }
}
