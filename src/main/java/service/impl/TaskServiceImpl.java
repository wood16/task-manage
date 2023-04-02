package service.impl;

import mapper.TaskMapper;
import model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TaskRepository;
import service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskModel> getAllTask() {

        return taskMapper.mapModelsFromEntities(taskRepository.findAll());
    }
}
