package org.example.dtos.request;

import lombok.Data;

@Data
public class ResetEmailRequest {
    private String oldEmail;
    private String newEmail;
}
