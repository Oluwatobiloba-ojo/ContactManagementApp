package org.example.services;

import org.example.data.model.Contact;
import org.example.dtos.request.CreateContactRequest;
import org.example.dtos.request.EditContactRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.request.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


public interface UserService {
    Long register(RegisterRequest registerRequest);
    void logIn(LoginRequest loginRequest);
    void createContact(CreateContactRequest createContactRequest);
    void edit(EditContactRequest editContactRequest);
    Contact findContactFor(long userId, String contactName);
    List<Contact> findAllContactFor(Long userId);
}
