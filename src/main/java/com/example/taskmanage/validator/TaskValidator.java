package com.example.taskmanage.validator;

import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskValidator {

    @Autowired
    private TaskRepository taskRepository;

    public void validateForAdd(TaskDto taskDto) {

        validateRequireField(taskDto);
        validateDate(Optional.empty(), taskDto);
    }

    public void validateForUpdate(long taskId, TaskDto taskDto) {

        validateExist(taskId);
        validateRequireField(taskDto);

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

                validateStartAndEndDate(taskEntity.getStartDate(), taskDto.getEndDate());
            }
        });
    }

    private void validateStartAndEndDate(Date startDate, Date endDate) {

        if (startDate.after(endDate)) {

            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Start date need before end date!");
        }
    }

    private void validateRequireField(TaskDto taskDto){

        if(Objects.isNull(taskDto.getName())){
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Name of task is required!");
        }

        if(Objects.isNull(taskDto.getAssigneeId())){
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Assignee of task is required!");
        }
    }

}
