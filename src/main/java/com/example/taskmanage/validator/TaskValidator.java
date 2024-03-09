package com.example.taskmanage.validator;

import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskValidator {

    @Autowired
    private TaskRepository taskRepository;

    public void validateForAdd() {

    }

    public void validateForUpdate() {

    }

    public void validateExist(long taskId) {

        if (taskRepository.findById(taskId).isPresent()) {

            return;
        }

        throw new BaseException(HttpStatus.NOT_FOUND.value(), "Task not found!");
    }

}
