package com.swp.project.service;

import com.swp.project.dto.response.PermissionDTO;

import java.util.List;

public interface IPermissionService {

    List<PermissionDTO> getAllPermissions();
}
