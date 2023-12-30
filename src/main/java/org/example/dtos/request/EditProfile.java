package org.example.dtos.request;

import lombok.Data;

@Data
public class EditProfile {
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
