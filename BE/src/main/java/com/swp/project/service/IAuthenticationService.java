package com.swp.project.service;

import com.swp.project.dto.request.AuthenticationRequest;
import com.swp.project.dto.response.AuthenticationResponse;

public interface IAuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);
}
