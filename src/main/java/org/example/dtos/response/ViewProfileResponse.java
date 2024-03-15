package org.example.dtos.response;

import lombok.Data;
import org.example.data.model.ContactApp;

@Data
public class ViewProfileResponse {
    private ContactApp User;
    private String message;
}
