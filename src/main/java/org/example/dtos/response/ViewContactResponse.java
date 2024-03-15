package org.example.dtos.response;

import lombok.Data;
import org.example.data.model.Contact;

@Data
public class ViewContactResponse {
    private Contact contact;
    private String message;
}
