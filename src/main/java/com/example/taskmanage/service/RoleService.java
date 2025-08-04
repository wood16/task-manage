package com.example.taskmanage.service;

import com.example.taskmanage.dto.request.RoleRequest;
import com.example.taskmanage.entity.RoleEntity;
import org.springframework.data.domain.Page;

public interface RoleService {

    Page<RoleEntity> getAllRole(int page, int pageSize);

    void createRole(RoleRequest roleRequest);

    void deleteRole(Long id);
}
