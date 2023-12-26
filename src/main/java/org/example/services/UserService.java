package org.example.services;

import org.example.dtos.request.RegisterRequest;
import org.springframework.stereotype.Service;


public interface UserService {
    Long register(RegisterRequest registerRequest);
}
