package com.swp.project.mapper;

import com.swp.project.dto.response.UserDTO;
import com.swp.project.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole().getName());
        userDTO.setActive(user.isActive());
        userDTO.setBanned(user.isBanned());
        userDTO.setAvatar(user.getAvatar());
        return userDTO;
    }
}
