package com.swp.project.service.Impl;

import com.swp.project.dto.response.PermissionDTO;
import com.swp.project.mapper.PermissionMapper;
import com.swp.project.repository.PermissionRepository;
import com.swp.project.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream().map(
                permissionMapper::toPermissionDTO
        ).toList();
    }
}
