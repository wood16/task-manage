package com.example.taskmanage.repository;

import com.example.taskmanage.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findAll(Pageable pageable);
    Page<TaskEntity> findByNameContaining(String name, Pageable pageable);
}
