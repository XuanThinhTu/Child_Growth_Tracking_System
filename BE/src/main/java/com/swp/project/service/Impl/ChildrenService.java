package com.swp.project.service.Impl;

import com.swp.project.dto.response.ChildrenDTO;
import com.swp.project.entity.Children;
import com.swp.project.entity.User;
import com.swp.project.mapper.ChildrenMapper;
import com.swp.project.repository.*;
import com.swp.project.service.IChildrenService;
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














}
