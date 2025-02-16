package com.swp.project.service.Impl;

import com.swp.project.config.security.jwt.JwtUtils;
import com.swp.project.dto.request.UserCreationRequest;
import com.swp.project.dto.response.UserDTO;
import com.swp.project.entity.Role;
import com.swp.project.entity.User;
import com.swp.project.exception.ResourceAlreadyExistException;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.UserMapper;
import com.swp.project.repository.RoleRepository;
import com.swp.project.repository.UserRepository;
import com.swp.project.service.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @Override
    public UserDTO register(UserCreationRequest request) throws MessagingException {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ResourceAlreadyExistException("Email already in used");
        }else if(userRepository.findByPhone(request.getPhone()).isPresent()){
            throw new ResourceAlreadyExistException("Phone already in used");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAvatar(null);
        user.setActive(false);
        user.setBanned(false);
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        emailService.sendEmail(
                savedUser.getEmail(),
                emailService.subjectRegister(),
                emailService.bodyRegister(
                        savedUser.getEmail(),
                        savedUser.getFirstName(),
                        savedUser.getLastName(),
                        savedUser.getPhone(),
                        savedUser.getAddress())
                );
        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO verifyEmail(String token) {
        String email = jwtUtils.getEmailFromJwtToken(token);
        Date expirationDate = jwtUtils.getExpDateFromToken(token);
        if(!expirationDate.before(new Date())){
            User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            user.setActive(true);
            User savedUser = userRepository.save(user);
            return userMapper.toUserDTO(savedUser);
        }else{
            throw new RuntimeException("Time to verify email is expired");
        }
    }

    @Override
    public UserDTO getAuthenticatedUserDTO() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDTO updatePassword(String oldPassword, String newPassword) {
        User user = getAuthenticatedUser();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            User savedUser = userRepository.save(user);
            return userMapper.toUserDTO(savedUser);
        }else{
            throw new RuntimeException("Old password is incorrect");
        }
    }

    @Override
    public UserDTO updateUserProfile(String firstName,
                                     String lastName,
                                     String phone,
                                     String address,
                                     MultipartFile file) throws IOException {
        User user = getAuthenticatedUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);
        if(!file.isEmpty()){
            Map url = cloudinaryService.upload(file);
            String avatarUrl = (String) url.get("url");
            user.setAvatar(avatarUrl);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateByUserId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void activateByUserId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void banByUserId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setBanned(true);
        userRepository.save(user);
    }

    @Override
    public void unbanByUserId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setBanned(false);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(user.isBanned()){
            throw new RuntimeException("User is banned");
        }else if(!user.isActive()){
            throw new RuntimeException("User is not active");
        }
        emailService.sendEmail(email, emailService.subjectResetPassword(), emailService.bodyResetPassword(email));
    }

    @Override
    public UserDTO resetPassword(String token, String newPassword) {
        String email = jwtUtils.getEmailFromJwtToken(token);
        Date expirationDate = jwtUtils.getExpDateFromToken(token);
        if(!expirationDate.before(new Date())){
            User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            user.setPassword(passwordEncoder.encode(newPassword));
            User savedUser = userRepository.save(user);
            return userMapper.toUserDTO(savedUser);
        }else{
            throw new RuntimeException("Time to reset password is expired");
        }
    }
}
