package com.swp.project.service;

import com.swp.project.dto.response.ChildrenDTO;
import org.springframework.data.domain.Page;
import java.util.List;


public interface IChildrenService {
    List<ChildrenDTO> getChildrenByAuthenticatedUser();
    ChildrenDTO addChildren(String name, String birthDate, String gender);
    ChildrenDTO updateChildren(int id, String name, String birthDate, String gender);
    ChildrenDTO getChildrenById(int id);
    List<ChildrenDTO> getChildrenByParentId(int id);
    void deleteChildren(int id);
    Page<ChildrenDTO> getAllChildren(int page, int size);
}
