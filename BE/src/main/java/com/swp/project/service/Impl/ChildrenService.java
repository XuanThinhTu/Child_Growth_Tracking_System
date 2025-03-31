package com.swp.project.service.Impl;

import com.swp.project.dto.response.ChildrenDTO;
import com.swp.project.entity.Children;
import com.swp.project.entity.User;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.ChildrenMapper;
import com.swp.project.repository.*;
import com.swp.project.service.IChildrenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<ChildrenDTO> getChildrenByAuthenticatedUser() {
        User user = userService.getAuthenticatedUser();
        List<Children> childrenList = childrenRepository.findByUserId(user.getId());
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
    public ChildrenDTO updateChildren(int id, String name, String birthDate, String gender) {
        Children children = childrenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Children not found"));
        User currentUser = userService.getAuthenticatedUser();
        if(children.getUser().getId() != currentUser.getId()){
            throw new RuntimeException("You can only update your own children");
        }
        children.setName(name);
        children.setBirthDate(Date.valueOf(birthDate));
        children.setGender(gender);
        children = childrenRepository.save(children);
        return childrenMapper.toChildrenDTO(children);
    }

    @Override
    public ChildrenDTO getChildrenById(int id) {
        Children children = childrenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Children not found"));
        return childrenMapper.toChildrenDTO(children);
    }

    @Override
    public List<ChildrenDTO> getChildrenByParentId(int id) {
        List<Children> childrenList = childrenRepository.findByUserId(id);
        return childrenList.stream().map(childrenMapper::toChildrenDTO).toList();

    }
    @Override
    public void deleteChildren(int id) {
        User user = userService.getAuthenticatedUser();
        Children children = childrenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Children not found"));
        if(children.getUser().getId() != user.getId()){
            throw new RuntimeException("You can only delete your own children");
        }
        childrenRepository.delete(children);
    }

    @Override
    public Page<ChildrenDTO> getAllChildren(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Children> children = childrenRepository.findAll(pageable);
        return children.map(childrenMapper::toChildrenDTO);
    }


}
