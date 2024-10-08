package com.example.taskmanage.controller;

import com.example.taskmanage.dto.request.TaskRequest;
import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.dto.response.TaskResponse;
import com.example.taskmanage.dto.response.UserContextResponse;
import com.example.taskmanage.service.TaskService;
import com.example.taskmanage.validator.TaskValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public BaseResponse<Map<String, Object>> getAll(
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

        return BaseResponse.<Map<String, Object>>builder()
                .result(response).build();
    }

    @PostMapping
    public BaseResponse<TaskResponse> postTask(@RequestBody @Valid TaskRequest taskRequest) {

        taskValidator.validateForAdd(taskRequest);

        return BaseResponse.<TaskResponse>builder()
                .result(taskService.addTask(getUserContext().getUserId(), taskRequest))
                .build();
    }

    @PutMapping("/{id}")
    public BaseResponse<TaskResponse> putTask(@RequestBody @Valid TaskRequest taskRequest, @PathVariable long id) {

        taskValidator.validateForUpdate(id, taskRequest);

        return BaseResponse.<TaskResponse>builder()
                .result(taskService.putTask(getUserContext().getUserId(), id, taskRequest))
                .build();
    }

    @PatchMapping("/{id}")
    public BaseResponse<TaskResponse> patchTask(@RequestBody TaskRequest taskRequest, @PathVariable long id) {

        taskValidator.validateForPatch(id, taskRequest);

        return BaseResponse.<TaskResponse>builder()
                .result(taskService.patchTask(getUserContext().getUserId(), id, taskRequest))
                .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<TaskResponse> getOne(@PathVariable long id) {

        taskValidator.validateExist(id);

        return BaseResponse.<TaskResponse>builder()
                .result(taskService.getTask(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<?> deleteById(@PathVariable long id) {

        taskValidator.validateExist(id);

        taskService.deleteTaskById(getUserContext().getUserId(), id);

        return BaseResponse.builder()
                .message("Delete success")
                .build();
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
    public BaseResponse<?> reindexAllTask() {

        taskService.reindexAllTask();

        return BaseResponse.builder().message("Success").build();
    }

}
