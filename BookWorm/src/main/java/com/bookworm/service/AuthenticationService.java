package com.bookworm.service;

import com.bookworm.model.AuthenticationRequest;
import com.bookworm.model.AuthenticationResponse;
import com.bookworm.model.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
} 