package org.example.services;

import org.example.data.model.User;
import org.example.data.repository.UserRepository;
import org.example.dtos.request.RegisterRequest;
import org.example.exceptions.InvalidFormatDetails;
import org.example.utils.EncryptPassword;
import org.example.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long register(RegisterRequest registerRequest) {
        if (!Validation.validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetails("Invalid email");
        if (!Validation.validatePhoneNumber(registerRequest.getPhoneNumber())) throw new InvalidFormatDetails("invalid phone number phone format is +(country code),(national number)");
        if (!Validation.validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetails("Weak password");
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(EncryptPassword.generateHashPassword(registerRequest.getPassword(), EncryptPassword.getSaltValue()));
        userRepository.save(user);
        return user.getId();
    }
}
