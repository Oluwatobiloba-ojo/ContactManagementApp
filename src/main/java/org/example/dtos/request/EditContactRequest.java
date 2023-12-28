package org.example.dtos.request;

import lombok.Data;

@Data
public class EditContactRequest {
    private Long userId;
    private String name;
    private String newContactName;
    private String newContactNumber;
}
