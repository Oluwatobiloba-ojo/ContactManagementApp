package org.example.services;

import jakarta.transaction.Transactional;
import org.example.data.repository.ContactAppRepository;
import org.example.data.repository.ContactRepository;
import org.example.dtos.request.*;
import org.example.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class ContactAppServiceTest {
    @Autowired
    private UserService userService;
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
        assertThrows(InvalidFormatDetails.class, () -> userService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserRegisterWithWrongPhoneNumberDetailsThrowsExceptions(){
        registerRequest.setPhoneNumber("080646358i");
        assertThrows(InvalidFormatDetails.class, ()-> userService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserRegisterWithWrongPasswordValidateThrowsException(){
        registerRequest.setPassword("baby");
        assertThrows(InvalidFormatDetails.class, ()-> userService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserRegisterWithCorrectDetailsUserRepositoryIsOne(){
        userService.register(registerRequest);
        assertEquals(1, contactAppRepository.count());
    }
    @Test
    public void testThatWeCanRegisterUserWithSameEmailItThrowsException(){
        userService.register(registerRequest);
        assertThrows(UserExistException.class, ()-> userService.register(registerRequest));
        assertEquals(1, contactAppRepository.count());
    }
    @Test
    public void testThatWhenUserRegisterCanAlsoLoginWithWrongPasswordThrowException(){
        userService.register(registerRequest);
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidLoginDetails.class, ()-> userService.logIn(loginRequest));
    }
    @Test
    public void testThatWhenUserRegister_LoginWithWrongIdThoseNotExist(){
        userService.register(registerRequest);
        loginRequest.setId(6L);
        assertThrows(InvalidLoginDetails.class, ()-> userService.logIn(loginRequest));
    }
    @Test
    public void testThatContactAppCanAddContactAndContactRepositoryIsOne(){
        Long id = userService.register(registerRequest);
        loginRequest.setId(id);
        userService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(id);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        userService.createContact(createContactRequest);
        assertEquals(1, contactRepository.count());
    }
    @Test
    public void testThatWhenWeRegister_LogIn_TryToAddTwoContactWithSameNameThrowsException(){
       Long id = userService.register(registerRequest);
       loginRequest.setId(id);
       userService.logIn(loginRequest);
       CreateContactRequest createContactRequest = new CreateContactRequest();
       createContactRequest.setUserId(id);
       createContactRequest.setPhoneNumber("+2348032389457");
       createContactRequest.setName("Ola wale");
       userService.createContact(createContactRequest);
       assertThrows(ContactExistException.class, () -> userService.createContact(createContactRequest));
    }
    @Test
    public void testThatWhenAUserWantToEditPhoneNumberInSavedContactItSetThePhoneNumberToNewValue(){
        Long id = userService.register(registerRequest);
        loginRequest.setId(id);
        userService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(id);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        userService.createContact(createContactRequest);
        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setUserId(id);
        editContactRequest.setName("Ola wale");
        editContactRequest.setNewContactNumber("+2348029810794");
        userService.edit(editContactRequest);
        assertEquals(editContactRequest.getNewContactNumber(), userService.findContactFor(id, "Ola wale").getPhoneNumber());
    }
    @Test
    public void testThatWhenTwoUserFindContactBelongingToThemTheSizeIsAmountOfWhatTheySaveOnTheirContact(){
        Long firstUserId = userService.register(registerRequest);
        registerRequest.setEmail("oluwatobi23@gmail.com");
        Long secondUserId = userService.register(registerRequest);
        loginRequest.setId(firstUserId);
        userService.logIn(loginRequest);
        loginRequest.setId(secondUserId);
        userService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(firstUserId);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        userService.createContact(createContactRequest);
        createContactRequest.setUserId(secondUserId);
        userService.createContact(createContactRequest);
        createContactRequest.setUserId(firstUserId);
        createContactRequest.setName("Oluwatobi");
        userService.createContact(createContactRequest);
        assertEquals(3, contactRepository.findAll().size());
        assertEquals(1, userService.findAllContactFor(secondUserId).size());
        assertEquals(2, userService.findAllContactFor(firstUserId).size());
    }
    @Test
    public void testThatUserCanEditTheirProfile(){
       Long firstUserId = userService.register(registerRequest);
       loginRequest.setId(firstUserId);
       userService.logIn(loginRequest);
       EditProfile editProfile = new EditProfile();
       editProfile.setUserId(firstUserId);
       editProfile.setLastName("Olamiposi");
       editProfile.setPhoneNumber("08129810794");
       userService.editProfile(editProfile);
       assertEquals("Olamiposi", userService.viewProfile(firstUserId).getLastName());
       assertEquals("08129810794", userService.viewProfile(firstUserId).getPhoneNumber());
    }
    @Test
    public void testThatUserSaveTwoContactDeleteOneOfContact_FindAllContactForUserSizeIsOne(){
        Long firstUserId = userService.register(registerRequest);
        loginRequest.setId(firstUserId);
        userService.logIn(loginRequest);
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUserId(firstUserId);
        createContactRequest.setPhoneNumber("+2348032389457");
        createContactRequest.setName("Ola wale");
        userService.createContact(createContactRequest);
        createContactRequest.setName("Ope");
        userService.createContact(createContactRequest);
        assertEquals(2, userService.findAllContactFor(firstUserId).size());
        userService.deleteContact(firstUserId, "Ola wale");
        assertEquals(1, userService.findAllContactFor(firstUserId).size());
        assertThrows(InvalidContactDetail.class, ()-> userService.deleteContact(firstUserId, "Ola wale"));
    }
    @Test
    public void testThatWhenAUserDoesNotHaveContactButDeleteAllContactThrowsException(){
        Long firstUserId = userService.register(registerRequest);
        loginRequest.setId(firstUserId);
        userService.logIn(loginRequest);
        assertThrows(InvalidContactDetail.class, ()-> userService.deleteAll(firstUserId));
    }
    @Test
    public void testThatUserCanDeleteAccountAndWhenUserWantToViewProfileThrowException(){
        Long firstUserId = userService.register(registerRequest);
        loginRequest.setId(firstUserId);
        userService.logIn(loginRequest);
        userService.deleteAccount(firstUserId);
        assertThrows(UserExistException.class, ()-> userService.viewProfile(firstUserId));
    }
    @Test
    public void testThatWhenUserProvideWrongOldPasswordToResetPasswordThrowException(){
        Long firstUserId = userService.register(registerRequest);
        loginRequest.setId(firstUserId);
        userService.logIn(loginRequest);
        assertThrows(InvalidFormatDetails.class,()-> userService.resetPassword(firstUserId, "wrongPassword", "newPassword"));
    }
    @Test
    public void testThatWhenUserProvideWrongOldEmailToResetEmailThrowsException(){
        Long firstUserId = userService.register(registerRequest);
        loginRequest.setId(firstUserId);
        userService.logIn(loginRequest);
        assertThrows(InvalidFormatDetails.class, ()-> userService.resetEmail(firstUserId, "wrongEmail", "oluwatobi@gmail.com"));
    }
}