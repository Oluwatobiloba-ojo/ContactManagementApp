package org.example.services;

import org.example.data.model.Contact;
import org.example.data.repository.ContactRepository;
import org.example.dtos.request.EditContactRequest;
import org.example.exceptions.ActionDoneException;
import org.example.exceptions.ContactExistException;
import org.example.exceptions.InvalidContactDetail;
import org.example.exceptions.InvalidFormatDetails;
import org.example.utils.Mapper;
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
        Contact newContact = Mapper.mapToContact(userId, name, phoneNumber);
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
        editPhoneNumber(editContactRequest, contact);
        if (editContactRequest.getNewContactName() != null) contact.setName(editContactRequest.getName());
        contactRepository.save(contact);
    }

    private static void editPhoneNumber(EditContactRequest editContactRequest, Contact contact) {
        if (editContactRequest.getNewContactNumber() != null){
            if (!Validation.validatePhoneNumber(editContactRequest.getNewContactNumber())) throw new InvalidFormatDetails("Invalid format for phone number");
            contact.setPhoneNumber(editContactRequest.getNewContactNumber());
        }
    }

    @Override
    public Contact findContact(Long userId, String name) {
       List<Contact> contactList = findAllContactFor(userId);
       for (Contact contact: contactList){
           if (contact.getName().equals(name)) return contact;
       }
       return null;
    }
    @Override
    public void delete(Long userId, String contactName) {
        Contact contact = findContact(userId, contactName);
        if (contact == null) throw new InvalidContactDetail("Contact does not exist");
        contactRepository.delete(contact);
    }
    @Override
    public void deleteAllFor(Long id) {
       List<Contact> contactList = findAllContactFor(id);
       if (contactList.isEmpty()) throw new InvalidContactDetail("No Contact yet");
       contactRepository.deleteAll(contactList);
    }
    @Override
    public void blockContact(Long userId, String contactName) {
        Contact contact = findContact(userId, contactName);
        if (contact == null) throw new InvalidContactDetail("Contact does not exist");
        if (contact.isBlock()) throw new ActionDoneException("The contact has been blocked already");
        contact.setBlock(true);
        contactRepository.save(contact);
    }
    @Override
    public void unBlockContact(Long userId, String contactName) {
          Contact contact = findContact(userId, contactName);
          if (contact == null) throw new InvalidContactDetail("Contact does not exist");
          if (!contact.isBlock()) throw new ActionDoneException("The contact is not blocked");
          contact.setBlock(false);
          contactRepository.save(contact);
    }
}
