package com.example.taskmanage.controller;

import com.example.taskmanage.dto.request.TaskRequest;
import com.example.taskmanage.dto.response.TaskResponse;
import com.example.taskmanage.dto.response.UserContextResponse;
import com.example.taskmanage.service.TaskService;
import com.example.taskmanage.validator.TaskValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;
    TaskValidator taskValidator;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortOrder
    ) {

        Page<TaskResponse> taskModels = taskService.getAllTask(
                getUserContext().getUserId(), filter, page, pageSize, search, sortBy, sortOrder);

        Map<String, Object> response = new HashMap<>();
        response.put("tasks", taskModels.getContent());
        response.put("currentPage", taskModels.getNumber());
        response.put("totalItems", taskModels.getTotalElements());
        response.put("totalPages", taskModels.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> postTask(@RequestBody TaskRequest taskRequest) {

//        taskValidator.validateForAdd(taskModel);

        return ResponseEntity.ok(taskService.addTask(getUserContext().getUserId(), taskRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> putTask(@RequestBody TaskRequest taskRequest, @PathVariable long id) {

//        taskValidator.validateForUpdate(id, taskModel);

        return ResponseEntity.ok(taskService.putTask(getUserContext().getUserId(), id, taskRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> patchTask(@RequestBody TaskRequest taskRequest, @PathVariable long id) {

//        taskValidator.validateForPatch(id, taskModel);

        return ResponseEntity.ok(taskService.patchTask(getUserContext().getUserId(), id, taskRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getOne(@PathVariable long id) {

        taskValidator.validateExist(id);

        return ResponseEntity.ok(taskService.getTask(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {

        taskValidator.validateExist(id);

        taskService.deleteTaskById(getUserContext().getUserId(), id);
    }

    @GetMapping("/childTasks/{id}")
    public Page<TaskResponse> getChildTasks(@PathVariable long id,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "1") int pageSize,
                                            @RequestParam(required = false) String search,
                                            @RequestParam(required = false) String sortBy,
                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortOrder) {

        return taskService.getChildTasks(id, page, pageSize, search, sortBy, sortOrder);
    }

    @GetMapping("/export")
    public byte[] getExportTask() {

        return taskService.exportTask(getUserContext().getUserId());
    }

    private UserContextResponse getUserContext() {

        return (UserContextResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/reindex")
    public ResponseEntity<String> reindexAllTask() {

        taskService.reindexAllTask();

        return ResponseEntity.ok("Success");
    }

}
