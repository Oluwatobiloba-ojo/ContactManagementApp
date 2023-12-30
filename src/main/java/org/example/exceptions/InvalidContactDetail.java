package org.example.exceptions;

public class InvalidContactDetail extends ContactAppException{
    public InvalidContactDetail(String message){
        super(message);
    }
}
