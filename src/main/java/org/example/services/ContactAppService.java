package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactApp;
import org.example.dtos.request.*;

import java.util.List;


public interface ContactAppService {
    Long register(RegisterRequest registerRequest);
    void logIn(LoginRequest loginRequest);
    void createContact(CreateContactRequest createContactRequest);
    void edit(EditContactRequest editContactRequest);
    Contact findContactFor(long userId, String contactName);
    List<Contact> findAllContactFor(Long userId);
    void editProfile(EditProfile editProfile);
    ContactApp viewProfile(Long userId);
    void deleteContact(Long userId, String contactName);
    void deleteAll(Long userId);
    void deleteAccount(Long userId);
    void resetPassword(Long userId, String oldPassword, String newPassword);
    void resetEmail(Long userId, String oldEmail, String newMail);
    void blockContact(Long userId, String contactName);
    void unBlockContact(Long userId, String contactName);
}
