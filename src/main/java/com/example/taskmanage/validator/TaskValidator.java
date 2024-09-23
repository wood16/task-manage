package com.example.taskmanage.validator;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskValidator {

    TaskRepository taskRepository;

    public void validateForAdd(TaskDto taskDto) {

        validateRequireField(taskDto);
        validateDate(Optional.empty(), taskDto);
    }

    public void validateForUpdate(long taskId, TaskDto taskDto) {

        validateExist(taskId);
        validateRequireField(taskDto);

        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        validateDate(optionalTask, taskDto);

        validateCircularRelationship(taskId, taskDto);
    }

    public void validateForPatch(long taskId, TaskDto taskDto) {

        validateExist(taskId);

        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        validateDate(optionalTask, taskDto);
    }

    public void validateExist(long taskId) {

        if (taskRepository.findById(taskId).isPresent()) {

            return;
        }

        throw new BaseException(HttpStatus.NOT_FOUND.value(), "Task not found!");
    }

    private void validateDate(Optional<TaskEntity> optionalTask, TaskDto taskDto) {

        if (Objects.nonNull(taskDto.getStartDate()) && Objects.nonNull(taskDto.getEndDate())) {

            validateStartAndEndDate(taskDto.getStartDate(), taskDto.getEndDate());
        }

        optionalTask.ifPresent(taskEntity -> {

            if (Objects.isNull(taskDto.getStartDate()) && Objects.nonNull(taskDto.getEndDate())) {

                validateStartAndEndDate(taskEntity.getStartDate(), taskDto.getEndDate());
            }

            if (Objects.nonNull(taskDto.getStartDate()) && Objects.isNull(taskDto.getEndDate())) {

                validateStartAndEndDate(taskDto.getStartDate(), taskEntity.getEndDate());
            }
        });
    }

    private void validateStartAndEndDate(Date startDate, Date endDate) {

        if (startDate.after(endDate)) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Start date need before end date!");
        }
    }

    private void validateRequireField(TaskDto taskDto) {

        if (Objects.isNull(taskDto.getName())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Name of task is required!");
        }

        if (Objects.isNull(taskDto.getAssigneeId())) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Assignee of task is required!");
        }
    }

    private void validateCircularRelationship(long id, TaskDto taskDto) {

        AtomicBoolean isCircular = new AtomicBoolean();

        if(Objects.nonNull(taskDto.getParentId())){

            checkCircularRelationship(id, taskDto.getParentId(), isCircular);
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
