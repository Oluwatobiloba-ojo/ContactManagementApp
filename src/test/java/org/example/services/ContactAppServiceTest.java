package org.example.services;

import org.example.data.repository.ContactAppRepository;
import org.example.data.repository.ContactRepository;
import org.example.dtos.request.*;
import org.example.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ContactAppServiceTest {
    @Autowired
    private ContactAppService contactAppService;
    @Autowired
    private ContactAppRepository contactAppRepository;
    @Autowired
    private ContactRepository contactRepository;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    @AfterEach
    public void doThisAfterEachTest(){
        contactAppRepository.deleteAll();
        contactRepository.deleteAll();
    }
    @BeforeEach
    public void startAllTestBeforeThisTest(){
        registerRequest = new RegisterRequest();
        loginRequest = new LoginRequest();
        registerRequest.setFirstName("oluwatobi");
        registerRequest.setLastName("Opeoluwa");
        registerRequest.setEmail("opeOgungbeni@gmail.com");
        registerRequest.setPassword("Oluwatobi23+");
        registerRequest.setPhoneNumber("+2348129810794");
        loginRequest.setId(1L);
        loginRequest.setPassword(registerRequest.getPassword());
    }
    @Test
    public void testThatUserRegisterWithInvalidEmailThrowsException(){
        registerRequest.setEmail("opeOgungbeni");
        assertThrows(InvalidFormatDetails.class, () -> contactAppService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserRegisterWithWrongPhoneNumberDetailsThrowsExceptions(){
        registerRequest.setPhoneNumber("080646358i");
        assertThrows(InvalidFormatDetails.class, ()-> contactAppService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserRegisterWithWrongPasswordValidateThrowsException(){
        registerRequest.setPassword("baby");
        assertThrows(InvalidFormatDetails.class, ()-> contactAppService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserRegisterWithCorrectDetailsUserRepositoryIsOne(){
        contactAppService.register(registerRequest);
        assertEquals(1, contactAppRepository.count());
    }
    @Test
    public void testThatWeCanRegisterUserWithSameEmailItThrowsException(){
        contactAppService.register(registerRequest);
        assertThrows(UserExistException.class, ()-> contactAppService.register(registerRequest));
        assertEquals(1, contactAppRepository.count());
    }
    @Test
    public void testThatWhenUserRegisterCanAlsoLoginWithWrongPasswordThrowException(){
        contactAppService.register(registerRequest);
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidLoginDetails.class, ()-> contactAppService.logIn(loginRequest));
    }
    @Test
    public void testThatWhenUserRegister_LoginWithWrongIdThoseNotExist(){
        contactAppService.register(registerRequest);
        loginRequest.setId(6L);
        assertThrows(InvalidLoginDetails.class, ()-> contactAppService.logIn(loginRequest));
    }
    @Test
    public void testThatContactAppCanAddContactAndContactRepositoryIsOne(){
        Long id = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(id);
        contactAppService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(id);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        contactAppService.createContact(createContactRequest);
        assertEquals(1, contactRepository.count());
    }
    @Test
    public void testThatWhenWeRegister_LogIn_TryToAddTwoContactWithSameNameThrowsException(){
       Long id = contactAppService.register(registerRequest).getUserId();
       loginRequest.setId(id);
       contactAppService.logIn(loginRequest);
       CreateContactRequest createContactRequest = new CreateContactRequest();
       createContactRequest.setUserId(id);
       createContactRequest.setPhoneNumber("+2348032389457");
       createContactRequest.setName("Ola wale");
       contactAppService.createContact(createContactRequest);
       assertThrows(ContactExistException.class, () -> contactAppService.createContact(createContactRequest));
    }
    @Test
    public void testThatWhenAUserWantToEditPhoneNumberInSavedContactItSetThePhoneNumberToNewValue(){
        Long id = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(id);
        contactAppService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(id);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        contactAppService.createContact(createContactRequest);
        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setUserId(id);
        editContactRequest.setName("Ola wale");
        editContactRequest.setNewContactNumber("+2348029810794");
        contactAppService.edit(editContactRequest);
        assertEquals(editContactRequest.getNewContactNumber(), contactAppService.findContactFor(id, "Ola wale").getContact().getPhoneNumber());
    }
    @Test
    public void testThatWhenTwoUserFindContactBelongingToThemTheSizeIsAmountOfWhatTheySaveOnTheirContact(){
        Long firstUserId = contactAppService.register(registerRequest).getUserId();
        registerRequest.setEmail("oluwatobi23@gmail.com");
        Long secondUserId = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(firstUserId);
        contactAppService.logIn(loginRequest);
        loginRequest.setId(secondUserId);
        contactAppService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(firstUserId);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        contactAppService.createContact(createContactRequest);
        createContactRequest.setUserId(secondUserId);
        contactAppService.createContact(createContactRequest);
        createContactRequest.setUserId(firstUserId);
        createContactRequest.setName("Oluwatobi");
        contactAppService.createContact(createContactRequest);
        assertEquals(3, contactRepository.findAll().size());
        assertEquals(1, contactAppService.findAllContactFor(secondUserId).getContact().size());
        assertEquals(2, contactAppService.findAllContactFor(firstUserId).getContact().size());
    }
    @Test
    public void testThatUserCanEditTheirProfile(){
       Long firstUserId = contactAppService.register(registerRequest).getUserId();
       loginRequest.setId(firstUserId);
       contactAppService.logIn(loginRequest);
       EditProfile editProfile = new EditProfile();
       editProfile.setUserId(firstUserId);
       editProfile.setLastName("Olamiposi");
       editProfile.setPhoneNumber("08129810794");
       contactAppService.editProfile(editProfile);
       assertEquals("Olamiposi", contactAppService.viewProfile(firstUserId).getUser().getLastName());
       assertEquals("08129810794", contactAppService.viewProfile(firstUserId).getUser().getPhoneNumber());
    }
    @Test
    public void testThatUserSaveTwoContactDeleteOneOfContact_FindAllContactForUserSizeIsOne(){
        Long firstUserId = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(firstUserId);
        contactAppService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(firstUserId);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        contactAppService.createContact(createContactRequest);
        createContactRequest.setName("Ope");
        contactAppService.createContact(createContactRequest);
        assertEquals(2, contactAppService.findAllContactFor(firstUserId).getContact().size());
        contactAppService.deleteContact(firstUserId, "Ola wale");
        assertEquals(1, contactAppService.findAllContactFor(firstUserId).getContact().size());
        assertThrows(InvalidContactDetail.class, ()-> contactAppService.deleteContact(firstUserId, "Ola wale"));
    }
    @Test
    public void testThatWhenAUserDoesNotHaveContactButDeleteAllContactThrowsException(){
        Long firstUserId = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(firstUserId);
        contactAppService.logIn(loginRequest);
        assertThrows(InvalidContactDetail.class, ()-> contactAppService.deleteAll(firstUserId));
    }
    @Test
    public void testThatUserCanDeleteAccountAndWhenUserWantToViewProfileThrowException(){
        Long firstUserId = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(firstUserId);
        contactAppService.logIn(loginRequest);
        contactAppService.deleteAccount(firstUserId);
        assertThrows(UserExistException.class, ()-> contactAppService.viewProfile(firstUserId));
    }
    @Test
    public void testThatWhenUserProvideWrongOldPasswordToResetPasswordThrowException(){
        Long firstUserId = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(firstUserId);
        contactAppService.logIn(loginRequest);
        assertThrows(InvalidFormatDetails.class,()-> contactAppService.resetPassword(firstUserId, "wrongPassword", "newPassword"));
    }
    @Test
    public void testThatWhenUserProvideWrongOldEmailToResetEmailThrowsException(){
        Long firstUserId = contactAppService.register(registerRequest).getUserId();
        loginRequest.setId(firstUserId);
        contactAppService.logIn(loginRequest);
        assertThrows(InvalidFormatDetails.class, ()-> contactAppService.resetEmail(firstUserId, "wrongEmail", "oluwatobi@gmail.com"));
    }
}