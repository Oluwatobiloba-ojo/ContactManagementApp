package org.example.exceptions;

public class ContactAppException extends RuntimeException{
    public ContactAppException(String message){
        super(message);
    }
}
