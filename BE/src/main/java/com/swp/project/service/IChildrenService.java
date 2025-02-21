package com.swp.project.service;

import com.swp.project.dto.response.ChildrenDTO;

import java.util.List;

public interface IChildrenService {
    ChildrenDTO addChildren(String name, String birthDate, String gender);

    ChildrenDTO getChildrenById(int id);

    List<ChildrenDTO> getChildrenByParentId(int parentId);
}
