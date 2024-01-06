package org.example.controller;

import org.example.dtos.request.*;
import org.example.dtos.response.*;
import org.example.exceptions.ContactAppException;
import org.example.services.ContactAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/request")
public class ContactAppController {

    @Autowired
    private ContactAppService contactAppService;

    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            Long userId = contactAppService.register(registerRequest);
            registerResponse.setUserId(userId);
            registerResponse.setMessage("Account has been created");
            return new ResponseEntity<>(new ApiResponse(registerResponse, true), HttpStatus.CREATED);
        } catch (ContactAppException contactAppException) {
            return new ResponseEntity<>(new ApiResponse(contactAppException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> login(@PathVariable("userId") Long userId, @RequestParam(name = "password") String password) {
        LoginResponse loginResponse = new LoginResponse();
        LoginRequest loginRequest = new LoginRequest(userId, password);
        try {
            contactAppService.logIn(loginRequest);
            loginResponse.setMessage("You na don Login !!!!!!");
            return new ResponseEntity<>(new ApiResponse(loginResponse, true), HttpStatus.ACCEPTED);
        } catch (ContactAppException contactAppException) {
            loginResponse.setMessage(contactAppException.getMessage());
            return new ResponseEntity<>(new ApiResponse(loginResponse, false), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/contact")
    public ResponseEntity<?> createContact(@RequestBody CreateContactRequest createContactRequest) {
        CreateContactResponse createContactResponse = new CreateContactResponse();
        try {
            contactAppService.createContact(createContactRequest);
            createContactResponse.setMessage("Contact already created");
            return new ResponseEntity<>(new ApiResponse(createContactResponse, true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            createContactResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(createContactResponse, false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/contact")
    public ResponseEntity<?> editContact(@RequestBody EditContactRequest editContactRequest) {
        EditContactResponse editContactResponse = new EditContactResponse();
        try {
            contactAppService.edit(editContactRequest);
            editContactResponse.setMessage("Contact Has been Updated");
            return new ResponseEntity<>(new ApiResponse(editContactResponse, true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            editContactResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(editContactResponse, false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<?> editProfile(@RequestBody EditProfile editProfile) {
        EditProfileResponse editProfileResponse = new EditProfileResponse();
        try {
            contactAppService.editProfile(editProfile);
            editProfileResponse.setMessage("Profile Of User Has been updated");
            return new ResponseEntity<>(new ApiResponse(editProfileResponse, true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            editProfileResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(editProfileResponse, false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contacts/{userId}")
    public ResponseEntity<?> viewAllContact(@PathVariable("userId") Long userId) {
        ViewContactsResponse allContactResponse = new ViewContactsResponse();
        try {
            allContactResponse.setContact(contactAppService.findAllContactFor(userId));
            return new ResponseEntity<>(new ApiResponse(allContactResponse, true), HttpStatus.CREATED);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/contact/{userId}")
    public ResponseEntity<?> viewAContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName) {
        ViewContactResponse contactResponse = new ViewContactResponse();
        try {
            contactResponse.setContact(contactAppService.findContactFor(userId, contactName));
            return new ResponseEntity<>(new ApiResponse(contactResponse, true), HttpStatus.OK);
        } catch (ContactAppException contactAppException) {
            return new ResponseEntity<>(new ApiResponse(contactAppException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> viewUser(@PathVariable("userId") Long userId){
        ViewProfileResponse viewProfileResponse = new ViewProfileResponse();
        try{
            viewProfileResponse.setUser(contactAppService.viewProfile(userId));
            return new ResponseEntity<>(new ApiResponse(viewProfileResponse, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/password/{userId}")
    public ResponseEntity<?> resetPassword(@PathVariable("userId") Long userId, @RequestBody ResetPasswordRequest request){
       ResetPasswordResponse response = new ResetPasswordResponse();
        try {
           contactAppService.resetPassword(userId, request.getOldPassword(), request.getNewPassword());
           response.setMessage("Password has been updated");
           return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
       }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/email/{userId}")
    public ResponseEntity<?> resetEmail(@PathVariable("userId") Long userId, @RequestBody ResetEmailRequest request){
        ResetEmailResponse response = new ResetEmailResponse();
        try {
            contactAppService.resetEmail(userId, request.getOldEmail(), request.getNewEmail());
            response.setMessage("Email has been reset");
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/contact/{userId}")
    public ResponseEntity<?> deleteContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName){
        DeleteContactResponse response = new DeleteContactResponse();
        try{
            contactAppService.deleteContact(userId, contactName);
            response.setMessage(contactName + " has been deleted from your account");
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/contacts/{userId}")
    public ResponseEntity<?> deleteAllContact(@PathVariable("userId") Long userId){
        DeleteAllContactResponse response = new DeleteAllContactResponse();
        try {
            contactAppService.deleteAll(userId);
            response.setMessage("All Contact Deleted successfully");
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") Long userId){
        DeleteAccountResponse response = new DeleteAccountResponse();
        try {
            contactAppService.deleteAccount(userId);
            response.setMessage("Account has been deleted......");
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/contact/{userId}")
    public ResponseEntity<?> blockContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName){
        BlockContactResponse response = new BlockContactResponse();
        try {
          contactAppService.blockContact(userId, contactName);
          response.setMessage(contactName + " is blocked");
          return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/contact/{userId}")
    public ResponseEntity<?> unBlockContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName){
        UnBlockContactResponse response = new UnBlockContactResponse();
        try {
            contactAppService.unBlockContact(userId, contactName);
            response.setMessage(contactName + " has been unblocked");
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        }catch (ContactAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response, false), HttpStatus.BAD_REQUEST);
        }
    }

}
