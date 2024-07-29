package com.example.taskmanage.repository;

import com.example.taskmanage.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

    List<HistoryEntity> findByTypeAndObjectId(String type, Long objectId);
    List<HistoryEntity> findByCreatorId(Long creatorId);
}
