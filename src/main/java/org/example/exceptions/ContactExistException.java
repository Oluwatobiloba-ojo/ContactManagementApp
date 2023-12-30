package org.example.exceptions;

public class ContactExistException extends ContactAppException{
    public ContactExistException(String message){
        super(message);
    }
}
