package org.example.services;

import org.example.data.model.Contact;
import org.example.data.repository.ContactRepository;
import org.example.dtos.request.EditContactRequest;
import org.example.exceptions.ContactExistException;
import org.example.exceptions.InvalidFormatDetails;
import org.example.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    private ContactRepository contactRepository;
    @Override
    public void create(Long userId, String phoneNumber, String name) {
        if (contactExist(name, userId)) throw new ContactExistException("Contact Already Exist");
        Contact newContact = new Contact();
        newContact.setUserId(userId);
        newContact.setName(name);
        newContact.setPhoneNumber(phoneNumber);
        contactRepository.save(newContact);
    }
    private boolean contactExist(String name, Long id) {
        List<Contact> contactList = findAllContactFor(id);
       for (Contact contact : contactList){
           if (contact.getName().equals(name)){
               return true;
           }
       }
       return false;
    }
    @Override
    public List<Contact> findAllContactFor(Long id) {
       List<Contact> contactList = new ArrayList<>();
        for (Contact contact: contactRepository.findAll()){
            if (contact.getUserId().equals(id)) contactList.add(contact);
        }
        return contactList;
    }
    @Override
    public void editContact(EditContactRequest editContactRequest) {
        if (!contactExist(editContactRequest.getName(), editContactRequest.getUserId()))throw new ContactExistException("Contact does not exist");
        Contact contact = findContact(editContactRequest.getUserId(), editContactRequest.getName());
        if (editContactRequest.getNewContactNumber() != null){
            if (!Validation.validatePhoneNumber(editContactRequest.getNewContactNumber())) throw new InvalidFormatDetails("Invalid format for phone number");
            contact.setPhoneNumber(editContactRequest.getNewContactNumber());}
        if (editContactRequest.getNewContactName() != null) contact.setName(editContactRequest.getName());
        contactRepository.save(contact);
    }
    @Override
    public Contact findContact(Long userId, String name) {
       List<Contact> contactList = findAllContactFor(userId);
       for (Contact contact: contactList){
           if (contact.getName().equals(name)) return contact;
       }
       return null;
    }
}
