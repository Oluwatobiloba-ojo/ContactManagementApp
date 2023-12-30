package org.example.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ContactApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean isLogOut = true;
    @Override
    public String toString(){
        return String.format("""
                Full Name: %s_%s
                Email: %s
                PhoneNumber: %s
                """, firstName, lastName, email, phoneNumber);
    }

}
