package com.example.taskmanage.controller;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.dto.UserContextDto;
import com.example.taskmanage.service.TaskService;
import com.example.taskmanage.validator.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskValidator taskValidator;

    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int pageSize
    ) {

        Pageable paging = PageRequest.of(page, pageSize);

        Page<TaskDto> taskModels = taskService.getAllTask(paging, search);

        Map<String, Object> response = new HashMap<>();
        response.put("tasks", taskModels.getContent());
        response.put("currentPage", taskModels.getNumber());
        response.put("totalItems", taskModels.getTotalElements());
        response.put("totalPages", taskModels.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TaskDto> postTask(@RequestBody TaskDto taskModel) {

        taskValidator.validateForAdd(taskModel);

        return ResponseEntity.ok(taskService.addTask(getUserContext().getUserId(), taskModel));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<TaskDto> putTask(@RequestBody TaskDto taskModel, @PathVariable long id) {

        taskValidator.validateForUpdate(id, taskModel);

        return ResponseEntity.ok(taskService.putTask(getUserContext().getUserId(), id, taskModel));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<TaskDto> patchTask(@RequestBody TaskDto taskModel, @PathVariable long id) {

        taskValidator.validateForUpdate(id, taskModel);

        return ResponseEntity.ok(taskService.patchTaskUpdate(getUserContext().getUserId(), id, taskModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getOne(@PathVariable long id) {

        taskValidator.validateExist(id);

        return ResponseEntity.ok(taskService.getTask(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){

        taskValidator.validateExist(id);

        taskService.deleteTaskById(id);
    }

    private UserContextDto getUserContext() {

        return (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
