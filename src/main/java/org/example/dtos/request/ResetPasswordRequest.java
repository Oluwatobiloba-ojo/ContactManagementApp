package org.example.dtos.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
