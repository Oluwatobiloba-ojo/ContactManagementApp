package org.example.services;

import org.example.dtos.request.*;
import org.example.dtos.response.*;


public interface ContactAppService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse logIn(LoginRequest loginRequest);
    CreateContactResponse createContact(CreateContactRequest createContactRequest);
    EditContactResponse edit(EditContactRequest editContactRequest);
    ViewContactResponse findContactFor(long userId, String contactName);
    ViewContactsResponse findAllContactFor(Long userId);
    EditProfileResponse editProfile(EditProfile editProfile);
    ViewProfileResponse viewProfile(Long userId);
    void deleteContact(Long userId, String contactName);
    void deleteAll(Long userId);
    void deleteAccount(Long userId);
    ResetPasswordResponse resetPassword(Long userId, String oldPassword, String newPassword);
    ResetEmailResponse resetEmail(Long userId, String oldEmail, String newMail);
    void blockContact(Long userId, String contactName);
    void unBlockContact(Long userId, String contactName);
}
