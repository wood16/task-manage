package com.example.taskmanage.mapper;


import com.example.taskmanage.dto.request.UserRequest;
import com.example.taskmanage.entity.RoleEntity;
import com.example.taskmanage.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserMapper {

    public UserRequest mapFromEntry(UserEntity from) {
        UserRequest to = new UserRequest();

        to.setId(from.getId());
        to.setUsername(from.getUsername());
        to.setPassword(from.getPassword());
        to.setRoles(getRoles(from.getRoles()));

        return to;
    }

    public List<UserRequest> mapFromEntries(List<UserEntity> from) {

        return from.stream().map(this::mapFromEntry).toList();
    }

    public String[] getRoles(Set<RoleEntity> roleEntitySet) {

        return roleEntitySet.stream().map(RoleEntity::getName).toArray(String[]::new);
    }
}
