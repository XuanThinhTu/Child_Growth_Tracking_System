package com.swp.project.service;

import com.swp.project.dto.response.ChildrenDTO;
import com.swp.project.entity.Children;
import com.swp.project.entity.User;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.ChildrenMapper;
import com.swp.project.repository.ChildrenRepository;
import com.swp.project.service.Impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildrenService implements IChildrenService {
    private final ChildrenMapper childrenMapper;
    private final ChildrenRepository childrenRepository;
    private final UserService userService;

    @Override
    public List<ChildrenDTO> getChildrenByParentId(int parentId) {
        List<Children> childrenList = childrenRepository.findByUserId(parentId);
        return childrenList.stream().map(childrenMapper::toChildrenDTO).toList();
    }

    @Override
    public ChildrenDTO addChildren(String name, String birthDate, String gender) {
        User user = userService.getAuthenticatedUser();
        Children children = new Children();
        children.setName(name);
        children.setBirthDate(Date.valueOf(birthDate));
        children.setGender(gender);
        children.setUser(user);
        Children savedChildren = childrenRepository.save(children);
        return childrenMapper.toChildrenDTO(savedChildren);
    }

    @Override
    public ChildrenDTO getChildrenById(int id) {
        Children children = childrenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Children not found"));
        return childrenMapper.toChildrenDTO(children);
    }
}
