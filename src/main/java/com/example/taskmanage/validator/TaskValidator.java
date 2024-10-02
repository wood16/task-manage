package com.example.taskmanage.validator;

import com.example.taskmanage.dto.request.TaskRequest;
import com.example.taskmanage.dto.response.TaskResponse;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.exception.ErrorCode;
import com.example.taskmanage.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskValidator {

    TaskRepository taskRepository;

    public void validateForAdd(TaskRequest request) {

        validateDate(0, request);
    }

    public void validateForUpdate(long taskId, TaskRequest request) {

        validateExist(taskId);
        validateDate(taskId, request);
        validateCircularRelationship(taskId, request);
    }

    public void validateForPatch(long taskId, TaskRequest request) {

        validateExist(taskId);
        validateDate(taskId, request);
    }

    public void validateExist(long taskId) {

        if (taskRepository.findById(taskId).isPresent()) {

            return;
        }

        throw new BaseException(ErrorCode.TASK_NOT_FOUND);
    }

    private void validateDate(long taskId, TaskRequest request) {

        if (Objects.nonNull(request.getStartDate()) && Objects.nonNull(request.getEndDate())) {

            validateStartAndEndDate(request.getStartDate(), request.getEndDate());
        }

        taskRepository.findById(taskId).ifPresent(taskEntity -> {

            if (Objects.isNull(request.getStartDate()) && Objects.nonNull(request.getEndDate())) {

                validateStartAndEndDate(taskEntity.getStartDate(), request.getEndDate());
            }

            if (Objects.nonNull(request.getStartDate()) && Objects.isNull(request.getEndDate())) {

                validateStartAndEndDate(request.getStartDate(), taskEntity.getEndDate());
            }
        });
    }

    private void validateStartAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {

            throw new BaseException(ErrorCode.START_END_DATE);
        }
    }

    private void validateRequireField(TaskResponse taskResponse) {

        if (Objects.isNull(taskResponse.getName())) {

            throw new BaseException(ErrorCode.TASK_NAME_NULL);
        }

        if (Objects.isNull(taskResponse.getAssigneeId())) {

            throw new BaseException(ErrorCode.TASK_ASSIGN_NULL);
        }
    }

    private void validateCircularRelationship(long id, TaskRequest request) {

        AtomicBoolean isCircular = new AtomicBoolean();

        if (Objects.nonNull(request.getParentId())) {

            checkCircularRelationship(id, request.getParentId(), isCircular);
        }

        if (isCircular.get()) {

            throw new BaseException(ErrorCode.CIRCULAR_RELATIONSHIP);
        }
    }

    private void checkCircularRelationship(long id, long parentId, AtomicBoolean isCircular) {

        for (TaskEntity task : taskRepository.findByParentTask_Id(id)) {
            if (Objects.equals(task.getId(), parentId)) {

                isCircular.set(true);
                break;
            }

            checkCircularRelationship(task.getId(), parentId, isCircular);
        }
    }

}
