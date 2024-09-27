package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.response.NotificationResponse;
import com.example.taskmanage.elasticsearch.elasticrepository.NotificationElasticRepository;
import com.example.taskmanage.elasticsearch.service.NotificationElasticSearch;
import com.example.taskmanage.entity.NotificationEntity;
import com.example.taskmanage.mapper.NotificationMapper;
import com.example.taskmanage.repository.NotificationRepository;
import com.example.taskmanage.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    NotificationElasticRepository notificationElasticRepository;
    NotificationRepository notificationRepository;
    NotificationElasticSearch notificationElasticSearch;
    NotificationMapper notificationMapper;

    @Override
    public void createNotification(Long creatorId, Long receiverId, Long objectId, String type, String content) {

        if (!Objects.equals(creatorId, receiverId)) {

            NotificationEntity entity = new NotificationEntity();

            entity.setCreatorId(creatorId);
            entity.setCreateDate(new Date());
            entity.setReceiverId(receiverId);
            entity.setObjectId(objectId);
            entity.setType(type);
            entity.setContent(content);
            entity.setStatus("noRead");

            saveEntity(entity);
        }
    }

    @Override
    public List<NotificationResponse> getNotificationOfUser(long userId) {

        return notificationMapper.mapFromEntities(notificationElasticSearch.getNotifyOfUser(userId));
    }

    @Override
    public void patchNotification(Long id, NotificationResponse dto) {
        notificationRepository.findById(id)
                .ifPresent(entity -> {

                    entity.setStatus(dto.getStatus());

                    saveEntity(entity);
                });
    }

    @Override
    public void reindexAllNotification() {

        notificationElasticSearch.reindexAllNotification();
    }

    private void saveEntity(NotificationEntity entity) {

        NotificationEntity saved = notificationRepository.save(entity);

        notificationElasticRepository.save(saved);
    }
}
