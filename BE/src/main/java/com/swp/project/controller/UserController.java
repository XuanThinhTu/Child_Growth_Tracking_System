package com.swp.project.controller;


import com.swp.project.dto.request.ForgotPasswordRequest;
import com.swp.project.dto.request.ResetPasswordRequest;
import com.swp.project.dto.request.UserCreationRequest;
import com.swp.project.dto.request.VerifyUserRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.UserDTO;
import com.swp.project.service.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDTO> registerUser(@RequestBody UserCreationRequest request) throws MessagingException {
        UserDTO userDTO = userService.register(request);
        return ApiResponse.<UserDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered")
                .data(userDTO)
                .build();
    }

    @PostMapping("/verify")
    public ApiResponse<UserDTO> verifyUser(@RequestBody VerifyUserRequest request){
        UserDTO userDTO = userService.verifyEmail(request.getToken());
        return ApiResponse.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User verified")
                .data(userDTO)
                .build();
    }

    @GetMapping("/p")
    public ApiResponse<UserDTO> getAuthUser(){
        UserDTO userDTO = userService.getAuthenticatedUserDTO();
        return ApiResponse.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User profile retrieved")
                .data(userDTO)
                .build();
    }

    @PostMapping("/p/update")
    public ApiResponse<UserDTO> updateUserProfile(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("avatar") MultipartFile avatar
    ) throws IOException {
        UserDTO userDTO = userService.updateUserProfile(firstName, lastName, phone, address, avatar);
        return ApiResponse.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User profile updated")
                .data(userDTO)
                .build();
    }

    @PostMapping("/p/update/password")
    public ApiResponse<UserDTO> updatePassword(@RequestParam("oldPassword") String oldPassword,
                                               @RequestParam("newPassword") String newPassword){
        UserDTO userDTO = userService.updatePassword(oldPassword, newPassword);
        return ApiResponse.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Password updated")
                .data(userDTO)
                .build();
    }


    @GetMapping("/admin/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOs = userService.getAllUsers();
        return ApiResponse.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Show all users")
                .data(userDTOs)
                .build();
    }

    @DeleteMapping("/admin/deactivate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> deactivateUser(@RequestParam("user") int id){
        userService.deactivateByUserId(id);
        return ApiResponse.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deactivate user successfully")
                .build();
    }

    @PutMapping("/admin/activate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> activateUser(@RequestParam("user") int id){
        userService.activateByUserId(id);
        return ApiResponse.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Activate user successfully")
                .build();
    }

    @PutMapping("/admin/ban")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> banUser(@RequestParam("user") int id){
        userService.banByUserId(id);
        return ApiResponse.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deactivate user successfully")
                .build();
    }

    @PutMapping("/admin/unban")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> unbanUser(@RequestParam("user") int id){
        userService.unbanByUserId(id);
        return ApiResponse.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deactivate user successfully")
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) throws MessagingException {
        userService.forgotPassword(request.getEmail());
        return ApiResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Email sent")
                .data(null)
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<UserDTO> resetPassword(@RequestBody ResetPasswordRequest request){
        return ApiResponse.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Password reset")
                .data(userService.resetPassword(request.getToken(), request.getNewPassword()))
                .build();
    }

}
