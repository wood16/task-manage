package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.NotificationDto;
import com.example.taskmanage.elasticsearch.elasticrepository.NotificationElasticRepository;
import com.example.taskmanage.elasticsearch.service.NotificationElasticSearch;
import com.example.taskmanage.entity.NotificationEntity;
import com.example.taskmanage.mapper.NotificationMapper;
import com.example.taskmanage.repository.NotificationRepository;
import com.example.taskmanage.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationElasticRepository notificationElasticRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationElasticSearch notificationElasticSearch;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public void createNotification(Long creatorId, Long receiverId, String type, String content) {

        if(!Objects.equals(creatorId, receiverId)){

            NotificationEntity entity = new NotificationEntity();

            entity.setCreatorId(creatorId);
            entity.setCreateDate(new Date());
            entity.setReceiverId(receiverId);
            entity.setType(type);
            entity.setContent(content);
            entity.setStatus("noRead");

            saveEntity(entity);
        }
    }

    @Override
    public List<NotificationDto> getNotificationOfUser(long userId) {

        return notificationMapper.mapFromEntities(notificationElasticSearch.getNotifyOfUser(userId));
    }

    @Override
    public void patchNotification(Long id, NotificationDto dto) {
        notificationRepository.findById(id)
                .ifPresent(entity -> {

                    entity.setStatus(dto.getStatus());

                    saveEntity(entity);
                });
    }

    private NotificationEntity saveEntity(NotificationEntity entity) {

        NotificationEntity saved = notificationRepository.save(entity);

        notificationElasticRepository.save(saved);

        return saved;
    }
}
