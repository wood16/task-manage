package com.example.taskmanage.controller;

import com.example.taskmanage.dto.request.RoleRequest;
import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleController {

    RoleService roleService;

    @GetMapping
    public BaseResponse<Map<String, Object>> getAllRole(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int pageSize
    ) {
        Page<RoleEntity> roleModels = roleService.getAllRole(page, pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("roles", roleModels.getContent());
        response.put("currentPage", roleModels.getNumber());
        response.put("totalItems", roleModels.getTotalElements());
        response.put("totalPages", roleModels.getTotalPages());

        return BaseResponse.<Map<String, Object>>builder().result(response).build();
    }

    @PostMapping(produces = "application/json")
    public BaseResponse<?> createRole(@RequestBody RoleRequest roleRequest) {

        roleService.createRole(roleRequest);

        return BaseResponse.builder().message("Create role success").build();
    }

    @DeleteMapping(value = "/{id}")
    public BaseResponse<?> deleteRole(@PathVariable long id) {

        roleService.deleteRole(id);

        return BaseResponse.builder().message("Delete role success").build();
    }
}
