package org.example.exceptions;

public class InvalidLoginDetails extends ContactAppException {

    public InvalidLoginDetails(String message) {
        super(message);
    }
}
