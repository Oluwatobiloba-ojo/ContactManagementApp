package org.example.utils;

import org.example.data.model.Contact;
import org.example.data.model.ContactApp;
import org.example.dtos.request.RegisterRequest;

public class Mapper {

    public static Contact mapToContact(Long userId, String name, String phoneNumber){
        Contact newContact = new Contact();
        newContact.setUserId(userId);
        newContact.setName(name);
        newContact.setPhoneNumber(phoneNumber);
        return newContact;
    }

    public static ContactApp mapToContactApp(RegisterRequest registerRequest) {
        ContactApp contactApp = new ContactApp();
        contactApp.setEmail(registerRequest.getEmail());
        contactApp.setFirstName(registerRequest.getFirstName());
        contactApp.setLastName(registerRequest.getLastName());
        contactApp.setPhoneNumber(registerRequest.getPhoneNumber());
        contactApp.setPassword(EncryptPassword.generateHashPassword(registerRequest.getPassword(), EncryptPassword.getSaltValue()));
        return contactApp;
    }
}
