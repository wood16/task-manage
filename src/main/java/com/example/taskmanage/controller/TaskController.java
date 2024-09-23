package com.example.taskmanage.controller;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.dto.UserContextDto;
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

        Page<TaskDto> taskModels = taskService.getAllTask(
                getUserContext().getUserId(), filter, page, pageSize, search, sortBy, sortOrder);

        Map<String, Object> response = new HashMap<>();
        response.put("tasks", taskModels.getContent());
        response.put("currentPage", taskModels.getNumber());
        response.put("totalItems", taskModels.getTotalElements());
        response.put("totalPages", taskModels.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskDto> postTask(@RequestBody TaskDto taskModel) {

        taskValidator.validateForAdd(taskModel);

        return ResponseEntity.ok(taskService.addTask(getUserContext().getUserId(), taskModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> putTask(@RequestBody TaskDto taskModel, @PathVariable long id) {

        taskValidator.validateForUpdate(id, taskModel);

        return ResponseEntity.ok(taskService.putTask(getUserContext().getUserId(), id, taskModel));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> patchTask(@RequestBody TaskDto taskModel, @PathVariable long id) {

        taskValidator.validateForPatch(id, taskModel);

        return ResponseEntity.ok(taskService.patchTask(getUserContext().getUserId(), id, taskModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getOne(@PathVariable long id) {

        taskValidator.validateExist(id);

        return ResponseEntity.ok(taskService.getTask(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {

        taskValidator.validateExist(id);

        taskService.deleteTaskById(getUserContext().getUserId(), id);
    }

    @GetMapping("/childTasks/{id}")
    public Page<TaskDto> getChildTasks(@PathVariable long id,
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

    private UserContextDto getUserContext() {

        return (UserContextDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/reindex")
    public ResponseEntity<String> reindexAllTask() {

        taskService.reindexAllTask();

        return ResponseEntity.ok("Success");
    }

}
