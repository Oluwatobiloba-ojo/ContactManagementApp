package org.example.dtos.request;

import lombok.Data;

@Data
public class CreateContactRequest {
    private Long userId;
    private String name;
    private String phoneNumber;
}
