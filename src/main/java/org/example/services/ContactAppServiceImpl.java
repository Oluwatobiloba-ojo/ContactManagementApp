package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactApp;
import org.example.data.repository.ContactAppRepository;
import org.example.dtos.request.*;
import org.example.dtos.response.*;
import org.example.exceptions.*;
import org.example.utils.EncryptPassword;
import org.example.utils.Mapper;
import org.example.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactAppServiceImpl implements ContactAppService {
    @Autowired
    private ContactAppRepository contactAppRepository;
    @Autowired
    private ContactService contactService;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userDoesNotExist(registerRequest.getEmail())) throw new UserExistException("User already exist");
        if (!Validation.validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetails("Invalid email");
        if (!Validation.validatePhoneNumber(registerRequest.getPhoneNumber())) throw new InvalidFormatDetails("invalid phone number phone format is +(country code),(national number)");
        if (!Validation.validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetails("Weak password");
       ContactApp contactApp = Mapper.mapToContactApp(registerRequest);
       contactAppRepository.save(contactApp);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserId(contactApp.getId());
        registerResponse.setMessage("Account has been created");
        return registerResponse;
    }
    @Override
    public LoginResponse logIn(LoginRequest loginRequest) {
        if (userExist(loginRequest.getId()).isEmpty()) throw new InvalidLoginDetails("Invalid Details");
        ContactApp contactApp = userExist(loginRequest.getId()).get();
        String existingPassword = contactApp.getPassword();
        if (!EncryptPassword.verifyPassword(loginRequest.getPassword(), existingPassword)) throw new InvalidLoginDetails("Invalid Details");
        if (!contactApp.isLogOut()) throw new ActionDoneException("User has login already");
        contactApp.setLogOut(false);
        contactAppRepository.save(contactApp);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("You na don Login !!!!!!");
        return loginResponse;
    }

    @Override
    public CreateContactResponse createContact(CreateContactRequest createContactRequest) {
        if (userExist(createContactRequest.getUserId()).isEmpty()) throw new InvalidLoginDetails("Invalid Details");
        ContactApp contactApp = userExist(createContactRequest.getUserId()).get();
        if (contactApp.isLogOut()) throw  new InvalidLoginDetails("Have not logIn");
        if (!Validation.validatePhoneNumber(createContactRequest.getPhoneNumber())) throw new InvalidFormatDetails("Invalid format fortor phone number");
        contactService.create(contactApp.getId(), createContactRequest.getPhoneNumber(), createContactRequest.getName());
        CreateContactResponse createContactResponse = new CreateContactResponse();
        createContactResponse.setMessage("Contact already created");
        return createContactResponse;
    }

    @Override
    public EditContactResponse edit(EditContactRequest editContactRequest) {
        if (userExist(editContactRequest.getUserId()).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(editContactRequest.getUserId()).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not Login");
       contactService.editContact(editContactRequest);
       EditContactResponse editContactResponse = new EditContactResponse();
       editContactResponse.setMessage("Contact Has been Updated");
       return editContactResponse;
    }

    @Override
    public ViewContactResponse findContactFor(long userId, String contactName) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not Login");
        Contact contact = contactService.findContact(userId, contactName);
        if (contact == null) throw new InvalidFormatDetails("Contact does not exist");
        ViewContactResponse response = new ViewContactResponse();
        response.setContact(contact);
        response.setMessage("Successfully !!!!!!!!!!!");
        return response;
    }

    @Override
    public ViewContactsResponse findAllContactFor(Long userId) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not login");
        ViewContactsResponse response = new ViewContactsResponse();
        response.setMessage("Successfully !!!!!!!!!");
        response.setContact(contactService.findAllContactFor(userId));
        return response;
    }
    @Override
    public EditProfileResponse editProfile(EditProfile editProfile) {
        if (userExist(editProfile.getUserId()).isEmpty()) throw new ContactExistException("User does not exist");
        ContactApp contactApp = userExist(editProfile.getUserId()).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User has not login into application");
        if (editProfile.getFirstName() != null) contactApp.setFirstName(editProfile.getFirstName());
        if (editProfile.getLastName() != null) contactApp.setLastName(editProfile.getLastName());
        if (editProfile.getPhoneNumber() != null){
            if (!Validation.validatePhoneNumber(editProfile.getPhoneNumber())) throw new InvalidFormatDetails("Invalid format for phone number");
            contactApp.setPhoneNumber(editProfile.getPhoneNumber());
        }
        contactAppRepository.save(contactApp);
        EditProfileResponse editProfileResponse = new EditProfileResponse();
        editProfileResponse.setMessage("Profile Of User Has been updated");
        return editProfileResponse;
    }

    @Override
    public ViewProfileResponse viewProfile(Long userId) {
       if (userExist(userId).isEmpty())throw new UserExistException("User does not exist");
       ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User has not login into application");
        ViewProfileResponse response = new ViewProfileResponse();
        response.setUser(contactApp);
        response.setMessage("Successfully !!!!!!!!!!");
        return response;
    }
    private Optional<ContactApp> userExist(Long userId){
        return contactAppRepository.findById(userId);
    }

    @Override
    public void deleteContact(Long userId, String contactName) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login into application");
        contactService.delete(userId, contactName);
    }

    @Override
    public void deleteAll(Long userId) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login into application");
        contactService.deleteAllFor(contactApp.getId());

    }
    @Override
    public void deleteAccount(Long userId) {
    if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
    ContactApp contactApp = userExist(userId).get();
    if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
    contactAppRepository.delete(contactApp);
    }

    @Override
    public ResetPasswordResponse resetPassword(Long userId, String oldPassword, String newPassword) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
        if (!EncryptPassword.verifyPassword(oldPassword, contactApp.getPassword())) throw new InvalidFormatDetails("Password verification was wrong");
        if (!Validation.validatePassword(newPassword)) throw new InvalidFormatDetails("Password is weak");
        contactApp.setPassword(EncryptPassword.generateHashPassword(newPassword, EncryptPassword.getSaltValue()));
        contactAppRepository.save(contactApp);
        ResetPasswordResponse response = new ResetPasswordResponse();
        response.setMessage("Password has been updated");
        return response;
    }

    @Override
    public ResetEmailResponse resetEmail(Long userId, String oldEmail, String newMail) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
        if (!contactApp.getEmail().equalsIgnoreCase(oldEmail)) throw new InvalidFormatDetails("Email verification went wrong");
        if (!Validation.validateEmail(newMail)) throw new InvalidFormatDetails("Invalid format details for email");
        contactApp.setEmail(newMail);
        contactAppRepository.save(contactApp);
        ResetEmailResponse response = new ResetEmailResponse();
        response.setMessage("Email has been reset");
        return response;

    }
    @Override
    public void blockContact(Long userId, String contactName) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
        contactService.blockContact(userId, contactName);
    }
    @Override
    public void unBlockContact(Long userId, String contactName) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
        contactService.unBlockContact(userId, contactName);
    }
    private boolean userDoesNotExist(String email) {
        ContactApp contactApp = contactAppRepository.findByEmail(email);
        return contactApp != null;
    }
}
