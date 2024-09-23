package com.example.taskmanage.service.impl;

import com.example.taskmanage.common.constant.HistoryAction;
import com.example.taskmanage.dto.TaskDto;
import com.example.taskmanage.dto.TaskExportDto;
import com.example.taskmanage.elasticsearch.elasticrepository.TaskElasticRepository;
import com.example.taskmanage.elasticsearch.keys.TaskKeys;
import com.example.taskmanage.elasticsearch.model.TaskElasticModel;
import com.example.taskmanage.elasticsearch.service.TaskElasticSearch;
import com.example.taskmanage.entity.TaskEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.mapper.TaskExportMapper;
import com.example.taskmanage.mapper.TaskMapper;
import com.example.taskmanage.repository.TaskRepository;
import com.example.taskmanage.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    TaskMapper taskMapper;
    ModelMapper modelMapper;
    TaskElasticRepository taskElaRepository;
    TaskElasticSearch taskElasticSearch;
    ProgressHistoryService progressHistoryService;
    HistoryService historyService;
    NotificationService notificationService;
    ImportExportService importExportService;
    TaskExportMapper taskExportMapper;

    @Override
    public Page<TaskDto> getAllTask(long userId,
                                    String filter,
                                    int page,
                                    int pageSize,
                                    String search,
                                    String sortBy,
                                    Sort.Direction sortOrder) {
        Sort sort = Objects.isNull(sortBy) ?
                Sort.by(Sort.Direction.DESC, TaskKeys.MODIFIED_DATE) : Sort.by(sortOrder, sortBy);

        Pageable paging = PageRequest.of(Math.max(page - 1, 0), pageSize, sort);

        Page<TaskElasticModel> resultSearch = taskElasticSearch.getMyTask(filter, userId, search, paging);

        long total = resultSearch.getTotalElements();

        List<TaskDto> taskModels = taskMapper.mapFromElasticModels(resultSearch.getContent());

//        taskElasticSearch.searchByName("Task", paging);
//        taskElasticSearch.searchByStartDate(new Date(), paging);
//
//        taskElasticSearch.getChildTasks(4, paging, "021");

        return new PageImpl<>(taskModels, paging, total);
    }

    @Override
    public TaskDto addTask(long userId, TaskDto taskDto) {

        TaskEntity taskEntity = taskMapper.mapEntityFromModel(taskDto, new TaskEntity());
        setCreateInfo(userId, taskEntity);

        taskEntity.setStatus("pending");
        taskEntity.setProgress(0L);

        TaskEntity saved = saveEntity(taskEntity);

        historyService.addHistoryTask(userId, "task", saved.getId(),
                HistoryAction.CREATE.getValue(), "", null, null);

        notificationService.createNotification(userId, saved.getAssigneeId(), taskEntity.getId(),
                "", "Bạn được giao xử lý công việc  " + saved.getName());

        return taskMapper.mapModelFromEntity(saved);
    }

    @Override
    public TaskDto patchTask(long userId,
                             long taskId,
                             TaskDto taskDto) {

        return taskMapper.mapModelFromEntity(
                taskRepository.findById(taskId)
                        .map(taskEntity -> {

                            if (Objects.nonNull(taskDto.getName())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "name",
                                        taskEntity.getName(), taskDto.getName());
                                taskEntity.setName(taskDto.getName());
                            }
                            if (Objects.nonNull(taskDto.getDescription())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "description",
                                        taskEntity.getDescription(), taskDto.getDescription());
                                taskEntity.setDescription(taskDto.getDescription());
                            }
                            if (Objects.nonNull(taskDto.getStartDate())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "startDate",
                                        taskEntity.getStartDate(), taskDto.getStartDate());
                                taskEntity.setStartDate(taskDto.getStartDate());

                                updateDateOfParent(taskEntity);
                            }
                            if (Objects.nonNull(taskDto.getEndDate())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "endDate",
                                        taskEntity.getEndDate(), taskDto.getEndDate());
                                taskEntity.setEndDate(taskDto.getEndDate());

                                updateDateOfParent(taskEntity);
                            }
                            if (Objects.nonNull(taskDto.getProgress())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "progress",
                                        taskEntity.getProgress(), taskDto.getProgress());
                                updateProgress(userId, taskEntity, taskDto.getProgress(), "");
                            }
                            if (Objects.nonNull(taskDto.getAssigneeId())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "assigneeId",
                                        taskEntity.getAssigneeId(), taskDto.getAssigneeId());
                                taskEntity.setAssigneeId(taskDto.getAssigneeId());
                            }
                            if (Objects.nonNull(taskDto.getStatus())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "status",
                                        taskEntity.getStatus(), taskDto.getStatus());
                                taskEntity.setStatus(taskDto.getStatus());
                            }
                            if (Objects.nonNull(taskDto.getPriority())) {
                                historyService.addHistoryTask(userId, "task", taskEntity.getId(),
                                        HistoryAction.UPDATE.getValue(), "priority",
                                        taskEntity.getPriority(), taskDto.getPriority());
                                taskEntity.setPriority(taskDto.getPriority());
                            }

                            setModifiedInfo(userId, taskEntity);

                            return saveEntity(taskEntity);
                        })
                        .orElseThrow(
                                () -> new BaseException(HttpStatus.NOT_FOUND.value(), "Task not found!")
                        )
        );
    }

    @Override
    public TaskDto putTask(long userId, long taskId, TaskDto taskDto) {

        return taskRepository.findById(taskId)
                .map(entity -> {
                    TaskEntity newTaskEntity = taskMapper.mapEntityFromModel(taskDto, entity);

                    historyService.addHistoryTask(userId, "task", newTaskEntity.getId(),
                            HistoryAction.UPDATE.getValue(), "", null, null);

                    setModifiedInfo(userId, newTaskEntity);

                    updateDateOfParent(newTaskEntity);

                    return modelMapper.map(saveEntity(newTaskEntity), TaskDto.class);
                })
                .orElseThrow(() ->
                        new BaseException(HttpStatus.NOT_FOUND.value(), "Task not found"));
    }

    @Override
    public TaskDto getTask(long taskId) {

        return taskRepository.findById(taskId)
                .map(taskMapper::mapModelFromEntity)
                .orElseThrow(() ->
                        new BaseException(HttpStatus.NOT_FOUND.value(), "Task not found"));
    }

    @Override
    public Page<TaskDto> getChildTasks(long taskId,
                                       int page,
                                       int pageSize,
                                       String search,
                                       String sortBy,
                                       Sort.Direction sortOrder) {

        Sort sort = Objects.isNull(sortBy) ? Sort.unsorted() : Sort.by(sortOrder, sortBy);

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return taskElasticSearch.getChildTasks(taskId, pageable, search);

//        return new PageImpl<>(taskMapper.mapModelsFromEntities(taskRepository.findByParentTask_Id(taskId)));
    }

    @Override
    public void deleteTaskById(long userId, long taskId) {

        historyService.addHistoryTask(userId, "task", taskId,
                HistoryAction.DELETE.getValue(), "", null, null);

        deleteEntity(taskId);
    }

    @Override
    public void reindexAllTask() {

        taskElasticSearch.reindexAllTask();
    }

    @Override
    public byte[] exportTask(long userId) {

        List<TaskElasticModel> taskElasticModels = taskElasticSearch.getMyTaskForExport("", userId);

        TaskExportDto[] data = taskExportMapper.mapFromModels(taskElasticModels);

        return importExportService.exportObject(data);
    }

    public TaskEntity saveEntity(TaskEntity taskEntity) {

//        save and index task
        TaskEntity saved = taskRepository.save(taskEntity);

        taskElaRepository.save(taskMapper.mapForIndex(saved));

        return saved;
    }

    public void deleteEntity(long taskId) {

//        delete and index task
        taskElaRepository.deleteById(taskId);
        taskRepository.deleteById(taskId);
    }

    public String getTaskById(Long id) {

        return getTaskEntity(id, TaskEntity::getName);
    }

    private <T> T getTaskEntity(Long id, Function<TaskEntity, T> taskFunction) {

        return taskRepository.findById(id)
                .map(taskFunction)
                .orElse(null);
    }

    private void updateProgress(long userId,
                                TaskEntity taskEntity,
                                long progress,
                                String description) {

        long fromProgress = taskEntity.getProgress();

        taskEntity.setProgress(progress);

        progressHistoryService.addProgressHistory(
                userId, taskEntity.getId(), fromProgress, progress, description);
    }

    private void updateDateOfParent(TaskEntity taskEntity) {

        Optional.ofNullable(taskEntity.getParentTask())
                .ifPresent(parentEntity -> {
                    List<TaskEntity> listTaskChild = taskRepository.findByParentTask_Id(parentEntity.getId());

                    Date minStartDate = listTaskChild
                            .stream()
                            .min(Comparator.comparing(TaskEntity::getStartDate))
                            .map(TaskEntity::getStartDate)
                            .orElse(null);

                    if (Objects.nonNull(minStartDate)) {
                        parentEntity.setStartDate(minStartDate);

                        saveEntity(parentEntity);
                    }

                    Date maxEndDate = listTaskChild
                            .stream()
                            .max(Comparator.comparing(TaskEntity::getEndDate))
                            .map(TaskEntity::getEndDate)
                            .orElse(null);

                    if (Objects.nonNull(maxEndDate)) {
                        parentEntity.setEndDate(maxEndDate);

                        saveEntity(parentEntity);
                    }
                });
    }

    private void setCreateInfo(long creatorId, TaskEntity taskEntity) {
        taskEntity.setCreatorId(creatorId);
        taskEntity.setCreateDate(new Date());

        setModifiedInfo(creatorId, taskEntity);
    }

    private void setModifiedInfo(long modifiedId, TaskEntity taskEntity) {
        taskEntity.setModifiedId(modifiedId);
        taskEntity.setModifiedDate(new Date());
    }
}
