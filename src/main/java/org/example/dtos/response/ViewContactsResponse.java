package org.example.dtos.response;

import lombok.Data;
import org.example.data.model.Contact;

import java.util.List;

@Data
public class ViewContactsResponse {
private List<Contact> contact;
private String message;
}
