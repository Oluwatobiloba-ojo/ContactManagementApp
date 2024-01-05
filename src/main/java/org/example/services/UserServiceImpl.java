package org.example.services;
import org.example.data.model.Contact;
import org.example.data.model.ContactApp;
import org.example.data.repository.ContactAppRepository;
import org.example.dtos.request.*;
import org.example.exceptions.*;
import org.example.utils.EncryptPassword;
import org.example.utils.Mapper;
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
        if (userDoesNotExist(registerRequest.getEmail())) throw new UserExistException("User already exist");
        if (!Validation.validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetails("Invalid email");
        if (!Validation.validatePhoneNumber(registerRequest.getPhoneNumber())) throw new InvalidFormatDetails("invalid phone number phone format is +(country code),(national number)");
        if (!Validation.validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetails("Weak password");
       ContactApp contactApp = Mapper.mapToContactApp(registerRequest);
       contactAppRepository.save(contactApp);
       return contactApp.getId();
    }

    @Override
    public void logIn(LoginRequest loginRequest) {
        if (userExist(loginRequest.getId()).isEmpty()) throw new InvalidLoginDetails("Invalid Details");
        ContactApp contactApp = userExist(loginRequest.getId()).get();
        String existingPassword = contactApp.getPassword();
        if (!EncryptPassword.verifyPassword(loginRequest.getPassword(), existingPassword)) throw new InvalidLoginDetails("Invalid Details");
        if (!contactApp.isLogOut()) throw new ActionDoneException("User has login already");
        contactApp.setLogOut(false);
        contactAppRepository.save(contactApp);
    }

    @Override
    public void createContact(CreateContactRequest createContactRequest) {
        if (userExist(createContactRequest.getUserId()).isEmpty()) throw new InvalidLoginDetails("Invalid Details");
        ContactApp contactApp = userExist(createContactRequest.getUserId()).get();
        if (contactApp.isLogOut()) throw  new InvalidLoginDetails("Have not logIn");
        if (!Validation.validatePhoneNumber(createContactRequest.getPhoneNumber())) throw new InvalidFormatDetails("Invalid format foe phone number");
        contactService.create(contactApp.getId(), createContactRequest.getPhoneNumber(), createContactRequest.getName());
    }

    @Override
    public void edit(EditContactRequest editContactRequest) {
        if (userExist(editContactRequest.getUserId()).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(editContactRequest.getUserId()).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not Login");
       contactService.editContact(editContactRequest);
    }

    @Override
    public Contact findContactFor(long userId, String contactName) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not Login");
        Contact contact = contactService.findContact(userId, contactName);
        if (contact == null) throw new InvalidFormatDetails("Contact does not exist");
        return contact;
    }

    @Override
    public List<Contact> findAllContactFor(Long userId) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User Have not login");
        return contactService.findAllContactFor(userId);
    }
    @Override
    public void editProfile(EditProfile editProfile) {
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
    }

    @Override
    public ContactApp viewProfile(Long userId) {
       if (userExist(userId).isEmpty())throw new UserExistException("User does not exist");
       ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User has not login into application");
        return contactApp;
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
    public void resetPassword(Long userId, String oldPassword, String newPassword) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
        if (!EncryptPassword.verifyPassword(oldPassword, contactApp.getPassword())) throw new InvalidFormatDetails("Password verification was wrong");
        if (!Validation.validatePassword(newPassword)) throw new InvalidFormatDetails("Password is weak");
        contactApp.setPassword(newPassword);
        contactAppRepository.save(contactApp);
    }

    @Override
    public void resetEmail(Long userId, String oldEmail, String newMail) {
        if (userExist(userId).isEmpty()) throw new UserExistException("User does not exist");
        ContactApp contactApp = userExist(userId).get();
        if (contactApp.isLogOut()) throw new InvalidLoginDetails("User have not login");
        if (!contactApp.getEmail().equalsIgnoreCase(oldEmail)) throw new InvalidFormatDetails("Email verification went wrong");
        if (!Validation.validateEmail(newMail)) throw new InvalidFormatDetails("Invalid format details for email");
        contactApp.setEmail(newMail);
        contactAppRepository.save(contactApp);
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
