package org.example.services;

import jakarta.transaction.Transactional;
import org.example.data.repository.UserRepository;
import org.example.dtos.request.RegisterRequest;
import org.example.exceptions.InvalidFormatDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private RegisterRequest registerRequest;
    @AfterEach
    public void doThisAfterEachTest(){
        userRepository.deleteAll();
    }
    @BeforeEach
    public void startAllTestBeforeThisTest(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("oluwatobi");
        registerRequest.setLastName("Opeoluwa");
        registerRequest.setEmail("opeOgungbeni@gmail.com");
        registerRequest.setPassword("Oluwatobi23+");
        registerRequest.setPhoneNumber("+2348129810794");
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
        assertEquals(1, userService.register(registerRequest));
        assertEquals(1, userRepository.count());
    }


}