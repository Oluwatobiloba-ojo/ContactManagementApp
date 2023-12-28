package org.example.exceptions;

public class ContactExistException extends RuntimeException{
    public ContactExistException(String message){
        super(message);
    }
}
