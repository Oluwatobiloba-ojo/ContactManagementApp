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
@RequestMapping("/api/request")
public class ContactAppController {

    @Autowired
    private ContactAppService contactAppService;

    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.register(registerRequest), true), HttpStatus.CREATED);
        } catch (ContactAppException contactAppException) {
            return new ResponseEntity<>(new ApiResponse(contactAppException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> login(@PathVariable("userId") Long userId, @RequestParam String password) {
        LoginRequest loginRequest = new LoginRequest(userId, password);
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.logIn(loginRequest), true), HttpStatus.ACCEPTED);
        } catch (ContactAppException contactAppException) {
            return new ResponseEntity<>(new ApiResponse(contactAppException.getMessage(),
                    false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<?> createContact(@RequestBody CreateContactRequest createContactRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.createContact(createContactRequest), true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/contact")
    public ResponseEntity<?> editContact(@RequestBody EditContactRequest editContactRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.edit(editContactRequest), true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<?> editProfile(@RequestBody EditProfile editProfile) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.editProfile(editProfile), true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(),
                    false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contacts/{userId}")
    public ResponseEntity<?> viewAllContact(@PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.findAllContactFor(userId), true), HttpStatus.CREATED);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(),
                    false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contact/{userId}")
    public ResponseEntity<?> viewAContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.findContactFor(userId, contactName),
                    true), HttpStatus.OK);
        } catch (ContactAppException contactAppException) {
            return new ResponseEntity<>(new ApiResponse(contactAppException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> viewUser(@PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.viewProfile(userId), true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(),
                    false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity<?> resetPassword(@PathVariable("userId") Long userId, @RequestBody ResetPasswordRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.resetPassword(userId, request.getOldPassword(),
                    request.getNewPassword()), true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/email/{userId}")
    public ResponseEntity<?> resetEmail(@PathVariable("userId") Long userId, @RequestBody ResetEmailRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(contactAppService.resetEmail(userId, request.getOldEmail(),
                    request.getNewEmail()), true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/contact/{userId}")
    public ResponseEntity<?> deleteContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName) {
        try {
            contactAppService.deleteContact(userId, contactName);
            return new ResponseEntity<>(new ApiResponse(contactName + " has been deleted from your account", true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(),
                    false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/contacts/{userId}")
    public ResponseEntity<?> deleteAllContact(@PathVariable("userId") Long userId) {
        try {
            contactAppService.deleteAll(userId);
            return new ResponseEntity<>(new ApiResponse("All Contact Deleted successfully", true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") Long userId) {
        try {
            contactAppService.deleteAccount(userId);
            return new ResponseEntity<>(new ApiResponse("Account has been deleted......", true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/contact/{userId}")
    public ResponseEntity<?> blockContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName) {
        try {
            contactAppService.blockContact(userId, contactName);
            return new ResponseEntity<>(new ApiResponse(contactName + " is blocked", true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/contact/{userId}")
    public ResponseEntity<?> unBlockContact(@PathVariable("userId") Long userId, @RequestParam(name = "contactName") String contactName) {
        try {
            contactAppService.unBlockContact(userId, contactName);
            return new ResponseEntity<>(new ApiResponse(contactName + " has been unblocked", true), HttpStatus.OK);
        } catch (ContactAppException exception) {
            return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

}
