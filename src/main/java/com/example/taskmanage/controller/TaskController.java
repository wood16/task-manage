package com.example.taskmanage.controller;

import com.example.taskmanage.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskmanage.service.TaskService;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks/getAll")
    public List<TaskModel> getAll() {

        return taskService.getAllTask();
    }

}
