package org.example.services;

import org.example.data.model.Contact;
import org.example.dtos.request.CreateContactRequest;
import org.example.dtos.request.EditContactRequest;

import java.util.List;

public interface ContactService {
    void create(Long userId, String phoneNumber, String name);
    List<Contact> findAllContactFor(Long id);
    void editContact(EditContactRequest editContactRequest);
    Contact findContact(Long userId, String contactName);
    void delete(Long userId, String contactName);
    void deleteAllFor(Long id);
    void blockContact(Long userId, String contactName);
    void unBlockContact(Long userId, String contactName);
}
