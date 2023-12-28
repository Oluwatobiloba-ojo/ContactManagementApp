package org.example.dtos.request;

import lombok.Data;

@Data
public class LoginRequest {
    private Long id;
    private String password;
}
