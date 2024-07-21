package com.example.taskmanage.repository;

import com.example.taskmanage.entity.ProgressHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressHistoryRepository extends JpaRepository<ProgressHistoryEntity, Long> {
}
