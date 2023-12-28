package org.example.exceptions;

public class UserExistException extends ContactAppException {
    public UserExistException(String message){
        super(message);
    }

}
