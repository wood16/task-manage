package com.example.taskmanage.validator;

import com.example.taskmanage.dto.response.TaskResponse;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskValidator {

    TaskRepository taskRepository;

    public void validateForAdd(TaskResponse taskResponse) {

        validateRequireField(taskResponse);
        validateDate(Optional.empty(), taskResponse);
    }

    public void validateForUpdate(long taskId, TaskResponse taskResponse) {

        validateExist(taskId);
        validateRequireField(taskResponse);

        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        validateDate(optionalTask, taskResponse);

        validateCircularRelationship(taskId, taskResponse);
    }

    public void validateForPatch(long taskId, TaskResponse taskResponse) {

        validateExist(taskId);

        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        validateDate(optionalTask, taskResponse);
    }

    public void validateExist(long taskId) {

        if (taskRepository.findById(taskId).isPresent()) {

            return;
        }

        throw new BaseException(HttpStatus.NOT_FOUND.value(), "Task not found!");
    }

    private void validateDate(Optional<TaskEntity> optionalTask, TaskResponse taskResponse) {

        if (Objects.nonNull(taskResponse.getStartDate()) && Objects.nonNull(taskResponse.getEndDate())) {

            validateStartAndEndDate(taskResponse.getStartDate(), taskResponse.getEndDate());
        }

        optionalTask.ifPresent(taskEntity -> {

            if (Objects.isNull(taskResponse.getStartDate()) && Objects.nonNull(taskResponse.getEndDate())) {

                validateStartAndEndDate(taskEntity.getStartDate(), taskResponse.getEndDate());
            }

            if (Objects.nonNull(taskResponse.getStartDate()) && Objects.isNull(taskResponse.getEndDate())) {

                validateStartAndEndDate(taskResponse.getStartDate(), taskEntity.getEndDate());
            }
        });
    }

    private void validateStartAndEndDate(Date startDate, Date endDate) {

        if (startDate.after(endDate)) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Start date need before end date!");
        }
    }

    private void validateRequireField(TaskResponse taskResponse) {

        if (Objects.isNull(taskResponse.getName())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Name of task is required!");
        }

        if (Objects.isNull(taskResponse.getAssigneeId())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Assignee of task is required!");
        }
    }

    private void validateCircularRelationship(long id, TaskResponse taskResponse) {

        AtomicBoolean isCircular = new AtomicBoolean();

        if(Objects.nonNull(taskResponse.getParentId())){

            checkCircularRelationship(id, taskResponse.getParentId(), isCircular);
        }

        if(isCircular.get()){

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Task have circular relationship, please check again!");
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
