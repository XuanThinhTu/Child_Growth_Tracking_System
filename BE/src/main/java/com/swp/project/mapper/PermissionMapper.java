package com.swp.project.mapper;

import com.swp.project.dto.response.PermissionDTO;
import com.swp.project.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public PermissionDTO toPermissionDTO(Permission permission) {
        return PermissionDTO.builder()
                .permissionName(permission.getPermissionName())
                .build();
    }
}
