package com.swp.project.controller;

import com.swp.project.dto.request.AuthenticationRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.AuthenticationResponse;
import com.swp.project.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(authenticationService.login(request))
                .build();
    }

}
