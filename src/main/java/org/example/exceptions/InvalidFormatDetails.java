package org.example.exceptions;

public class InvalidFormatDetails extends RuntimeException{

    public InvalidFormatDetails(String message) {
        super(message);
    }
}
