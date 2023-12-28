package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactApp;
import org.example.data.repository.ContactAppRepository;
import org.example.dtos.request.CreateContactRequest;
import org.example.dtos.request.EditContactRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.request.RegisterRequest;
import org.example.exceptions.InvalidFormatDetails;
import org.example.exceptions.InvalidLoginDetails;
import org.example.exceptions.UserExistException;
import org.example.utils.EncryptPassword;
import org.example.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ContactAppRepository contactAppRepository;
    @Autowired
    private ContactService contactService;

    @Override
    public Long register(RegisterRequest registerRequest) {
        if (!Validation.validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetails("Invalid email");
        if (!Validation.validatePhoneNumber(registerRequest.getPhoneNumber())) throw new InvalidFormatDetails("invalid phone number phone format is +(country code),(national number)");
        if (!Validation.validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetails("Weak password");
        if (userExist(registerRequest.getEmail())) throw new UserExistException("User already exist");
        ContactApp contactApp = new ContactApp();
        contactApp.setEmail(registerRequest.getEmail());
        contactApp.setFirstName(registerRequest.getFirstName());
        contactApp.setLastName(registerRequest.getLastName());
        contactApp.setPhoneNumber(registerRequest.getPhoneNumber());
        contactApp.setPassword(EncryptPassword.generateHashPassword(registerRequest.getPassword(), EncryptPassword.getSaltValue()));
        contactAppRepository.save(contactApp);
        return contactApp.getId();
    }

    @Override
    public void logIn(LoginRequest loginRequest) {
        Optional<ContactApp> savedUser = contactAppRepository.findById(loginRequest.getId());
        if (savedUser.isEmpty()) throw new InvalidLoginDetails("Invalid Details");
        String existingPassword = savedUser.get().getPassword();
        if (!EncryptPassword.verifyPassword(loginRequest.getPassword(), existingPassword)) throw new InvalidLoginDetails("Invalid Details");
        savedUser.get().setLogOut(false);
        contactAppRepository.save(savedUser.get());
    }

    @Override
    public void createContact(CreateContactRequest createContactRequest) {
        Optional<ContactApp> savedUser = contactAppRepository.findById(createContactRequest.getUserId());
        if (savedUser.get().isLogOut()) throw  new InvalidLoginDetails("Have not logIn");
        if (savedUser.isEmpty()) throw new InvalidLoginDetails("Invalid Details");
        if (!Validation.validatePhoneNumber(createContactRequest.getPhoneNumber())) throw new InvalidFormatDetails("Invalid format foe phone number");
        contactService.create(savedUser.get().getId(), createContactRequest.getPhoneNumber(), createContactRequest.getName());
    }

    @Override
    public void edit(EditContactRequest editContactRequest) {
        Optional<ContactApp> savedUser = contactAppRepository.findById(editContactRequest.getUserId());
        if (savedUser.isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = savedUser.get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not Login");
       contactService.editContact(editContactRequest);
    }

    @Override
    public Contact findContactFor(long userId, String contactName) {
        Optional<ContactApp> savedUser = contactAppRepository.findById(userId);
        if (savedUser.isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = savedUser.get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not Login");
        Contact contact = contactService.findContact(userId, contactName);
        if (contact == null) throw new InvalidFormatDetails("Contact does not exist");
        return contact;
    }

    @Override
    public List<Contact> findAllContactFor(Long userId) {
        Optional<ContactApp> savedUser = contactAppRepository.findById(userId);
        if (savedUser.isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = savedUser.get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not login");
        return contactService.findAllContactFor(userId);
    }

    private boolean userExist(String email) {
        ContactApp contactApp = contactAppRepository.findByEmail(email);
        return contactApp != null;
    }
}
