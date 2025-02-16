package com.swp.project.service;

import com.swp.project.dto.request.UserCreationRequest;
import com.swp.project.dto.response.UserDTO;
import com.swp.project.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    UserDTO register(UserCreationRequest request) throws MessagingException;

    UserDTO verifyEmail(String token);

    UserDTO getAuthenticatedUserDTO();

    User getAuthenticatedUser();

    UserDTO updateUserProfile(
            String firstName,
            String lastName,
            String phone,
            String address,
            MultipartFile file
    ) throws IOException;

    UserDTO updatePassword(String oldPassword, String newPassword);

    List<UserDTO> getAllUsers();

    void deactivateByUserId(int id);
    void activateByUserId(int id);
    void banByUserId(int id);
    void unbanByUserId(int id);

    void forgotPassword(String email) throws MessagingException;

    UserDTO resetPassword(String token, String newPassword);
}
