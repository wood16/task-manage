package com.example.taskmanage.service.impl;

import com.example.taskmanage.dto.request.RoleRequest;
import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.exception.ErrorCode;
import com.example.taskmanage.repository.RoleRepository;
import com.example.taskmanage.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    ModelMapper modelMapper;


    @Override
    public Page<RoleEntity> getAllRole(int page, int pageSize) {

        Pageable paging = PageRequest.of(Math.max(page - 1, 0), pageSize);

        return roleRepository.findAll(paging);
    }

    @Override
    public void createRole(RoleRequest roleRequest) {
        RoleEntity roleEntity = modelMapper.map(roleRequest, RoleEntity.class);

        roleRepository.save(roleEntity);
    }

    @Override
    public void deleteRole(Long id) {

        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ROLE_NOT_FOUND));

        roleRepository.delete(role);
    }
}
